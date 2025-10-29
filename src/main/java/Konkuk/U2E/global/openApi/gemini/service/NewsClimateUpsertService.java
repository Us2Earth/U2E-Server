package Konkuk.U2E.global.openApi.gemini.service;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NewsClimateUpsertService {

    private final ClimateRepository climateRepository;

    @Transactional
    public void replaceWithEnums(News news, List<String> enumNames) {
        if (news == null) return;

        climateRepository.deleteAllByNews(news);

        if (enumNames == null || enumNames.isEmpty()) return;

        Set<String> unique = new LinkedHashSet<>();
        for (String name : enumNames) {
            if (name != null && !name.isBlank()) unique.add(name.trim());
        }

        for (String name : unique) {
            try {
                ClimateProblem problem = ClimateProblem.valueOf(name); // EXACT name
                Climate c = Climate.builder()
                        .climateProblem(problem)
                        .news(news)
                        .build();
                climateRepository.save(c);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}