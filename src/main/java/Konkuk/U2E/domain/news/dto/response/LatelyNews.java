package Konkuk.U2E.domain.news.dto.response;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.service.mapper.NewsMappingResult;

import java.util.List;

public record LatelyNews(
        Long newsId,
        List<String> regionList,
        List<ClimateProblem> climateList,
        String newsTitle
) {
    public static LatelyNews of(NewsMappingResult newsMappingResult) {
        News news = newsMappingResult.news();
        return new LatelyNews(
                news.getNewsId(),
                newsMappingResult.regionNames(),
                newsMappingResult.climateProblems(),
                news.getNewsTitle()
        );
    }
}