package Konkuk.U2E.global.openApi.gemini;

import Konkuk.U2E.global.openApi.gemini.exception.GeminiCallFailedException;
import Konkuk.U2E.global.openApi.gemini.exception.GeminiInvalidResponseException;
import Konkuk.U2E.global.openApi.gemini.exception.GeminiMissingApiKeyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Value("${gemini.endpoint}")
    private String endpoint;

    private WebClient webClient() {
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

    public String generateContentJson(String systemPrompt, String userPrompt) {
        if (!StringUtils.hasText(apiKey)) throw new GeminiMissingApiKeyException();

        String payload = """
        {
          "system_instruction": {
            "role": "system",
            "parts": [{ "text": %s }]
          },
          "contents": [{
            "parts": [{ "text": %s }]
          }],
          "generation_config": {
            "temperature": 0.3,
            "top_k": 32,
            "top_p": 0.9,
            "max_output_tokens": 2048,
            "response_mime_type": "application/json"
          }
        }
        """.formatted(jsonEscape(systemPrompt), jsonEscape(userPrompt));

        String path = "/models/%s:generateContent".formatted(model);

        String raw = webClient()
                .post()
                .uri(u -> u.path(path).queryParam("key", apiKey).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> Mono.error(new GeminiCallFailedException(
                                        "status=" + resp.statusCode() + ", body=" + body)))
                )
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode root = objectMapper.readTree(raw);
            JsonNode candidates = root.path("candidates");
            if (!candidates.isArray() || candidates.isEmpty()) {
                throw new GeminiInvalidResponseException("candidates 비어있음. raw=" + safePreview(raw));
            }

            StringBuilder sb = new StringBuilder();
            for (JsonNode cand : candidates) {
                JsonNode parts = cand.path("content").path("parts");
                if (parts.isArray()) {
                    for (JsonNode p : parts) {
                        JsonNode t = p.path("text");
                        if (t.isTextual()) sb.append(t.asText());
                    }
                }
            }
            String text = sb.toString();
            if (text.isBlank()) throw new GeminiInvalidResponseException("parts.text 없음. raw=" + safePreview(raw));

            if (canParseJson(text)) return text;

            String s2 = stripPrologueAndExtractObject(text);
            if (canParseJson(s2)) return s2;

            String unwrapped = tryUnwrapJsonString(s2);
            if (canParseJson(unwrapped)) return unwrapped;

            String unwrappedRaw = tryUnwrapJsonString(text);
            if (canParseJson(unwrappedRaw)) return unwrappedRaw;

            throw new GeminiInvalidResponseException("JSON 파싱 실패(복구 실패). preview=" + safePreview(text));

        } catch (GeminiInvalidResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GeminiInvalidResponseException("JSON 파싱 실패: " + e.getMessage());
        }
    }

    private boolean canParseJson(String s) {
        if (s == null || s.isBlank()) return false;
        try {
            objectMapper.readTree(s);
            return true;
        } catch (Exception e) {
            log.warn("[GeminiClient] parse fail: {}", e.toString());
            return false;
        }
    }

    private static String stripPrologueAndExtractObject(String s) {
        if (s == null) return "";
        String v = s.replace("```json", "")
                .replace("```JSON", "")
                .replace("```", "");
        v = v.replaceFirst("(?i)^\\s*here is the json requested:\\s*", "");
        int start = v.indexOf('{');
        int end   = v.lastIndexOf('}');
        if (start >= 0 && end > start) v = v.substring(start, end + 1);
        return v.trim();
    }

    private String tryUnwrapJsonString(String s) {
        if (s == null || s.isBlank()) return s;
        try {
            String inner = objectMapper.readValue(s, String.class);
            return inner.replace("\\\"", "\"");
        } catch (JsonProcessingException ignore) {
            if (s.contains("\\\"")) return s.replace("\\\"", "\"");
            String t = s.trim();
            if ((t.startsWith("\"") && t.endsWith("\"")) || (t.startsWith("'") && t.endsWith("'"))) {
                return t.substring(1, t.length() - 1);
            }
            return s;
        }
    }

    private static String jsonEscape(String s) {
        return objectToJsonString(s);
    }
    private static String objectToJsonString(Object o) {
        try { return new ObjectMapper().writeValueAsString(o); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
    private static String safePreview(String s) {
        if (s == null) return "null";
        String t = s.replaceAll("\\s+", " ");
        return t.length() > 300 ? t.substring(0, 300) + "..." : t;
    }
}