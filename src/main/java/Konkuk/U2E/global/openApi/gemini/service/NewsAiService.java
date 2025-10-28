package Konkuk.U2E.global.openApi.gemini.service;

import Konkuk.U2E.global.openApi.gemini.GeminiClient;
import Konkuk.U2E.global.openApi.gemini.dto.request.AiNewsRequest;
import Konkuk.U2E.global.openApi.gemini.dto.response.AiResponse;
import Konkuk.U2E.global.openApi.gemini.dto.response.RegionCandidate;
import Konkuk.U2E.global.openApi.gemini.dto.response.RelatedArticle;
import Konkuk.U2E.global.openApi.gemini.exception.GeminiInvalidResponseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsAiService {

    private final GeminiClient geminiClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public AiResponse analyzeAll(AiNewsRequest req) {
        String locale = (req.locale() == null || req.locale().isBlank()) ? "ko" : req.locale();

        String systemPrompt = """
        You are a climate-news assistant. Return ONLY valid JSON exactly like:
        {
          "summary": "string (3-5 sentences, concise, keep who/what/when/where/why, same language as requested)",
          "solution": "string (one practical, actionable measure tailored to the article, 2-4 sentences)",
          "related": [
            {"title": "string", "url": "string"},
            {"title": "string", "url": "string"},
            {"title": "string", "url": "string"}
          ],
          "regions": [
            {"name": "string", "latitude": number, "longitude": number}
          ],
          "climate_problems": [
            "ENUM_NAME", "ENUM_NAME", "ENUM_NAME"
          ]
        }
        Rules:
        - Language: %s
        - STRICT: Output MUST be a single valid JSON object. No preface, no extra text, no code fences.
        - For "related", prefer reputable sources; it's okay to provide fewer than 3.
        - For "regions", include up to 3 places from the article (WGS84 decimals).
        - For "climate_problems": choose ALL that apply from THIS EXACT ENUM LIST (use EXACT enum names, not translations):
            ["TEMPERATURE_RISE","HEAVY_RAIN_OR_FLOOD","FINE_DUST","DROUGHT_OR_DESERTIFICATION",
             "SEA_LEVEL_RISE","TYPHOON_OR_TORNADO","WILDFIRE","EARTHQUAKE","DEFORESTATION","BIODIVERSITY_LOSS"]
          If none apply, return an empty array [].
        """.formatted(locale);

        String userPrompt = """
        Article body:
        ---
        %s
        ---
        Produce the JSON now.
        """.formatted(req.body());

        String jsonText = geminiClient.generateContentJson(systemPrompt, userPrompt);

        try {
            JsonNode root = mapper.readTree(jsonText);

            String summary = textOrThrow(root, "summary");
            String solution = textOrThrow(root, "solution");

            // related
            List<RelatedArticle> related = new ArrayList<>();
            JsonNode rel = root.path("related");
            if (rel.isArray()) {
                for (int i = 0; i < Math.min(3, rel.size()); i++) {
                    JsonNode it = rel.get(i);
                    String title = safeText(it, "title");
                    String url   = safeText(it, "url");
                    if (!title.isBlank() && !url.isBlank()) {
                        related.add(new RelatedArticle(title, url));
                    }
                }
            }

            // regions
            List<RegionCandidate> regions = new ArrayList<>();
            JsonNode regs = root.path("regions");
            if (regs.isArray()) {
                for (int i = 0; i < Math.min(3, regs.size()); i++) {
                    JsonNode it = regs.get(i);
                    String name = safeText(it, "name");
                    if (name.isBlank() || !it.hasNonNull("latitude") || !it.hasNonNull("longitude")) continue;
                    BigDecimal lat = it.path("latitude").decimalValue();
                    BigDecimal lon = it.path("longitude").decimalValue();
                    regions.add(new RegionCandidate(name, lat, lon));
                }
            }

            // climate_problems
            List<String> climateProblems = new ArrayList<>();
            JsonNode cps = root.path("climate_problems");
            if (cps.isArray()) {
                for (JsonNode it : cps) {
                    if (it.isTextual() && !it.asText().isBlank()) {
                        climateProblems.add(it.asText().trim());
                    }
                }
            }

            return new AiResponse(summary, solution, related, regions, climateProblems);

        } catch (GeminiInvalidResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GeminiInvalidResponseException("단일 JSON 파싱 실패: " + e.getMessage() + " | text=" + jsonText);
        }
    }

    private String textOrThrow(JsonNode root, String field) {
        JsonNode n = root.path(field);
        if (n.isMissingNode() || !n.isTextual() || n.asText().isBlank()) {
            throw new GeminiInvalidResponseException(field + " 누락/빈값");
        }
        return n.asText();
    }
    private String safeText(JsonNode root, String field) {
        if (root == null) return "";
        JsonNode n = root.path(field);
        return (n.isTextual()) ? n.asText() : "";
    }
}