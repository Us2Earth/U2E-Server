package Konkuk.U2E.domain.news.dto.response;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.service.mapper.NewsMappingResult;
import Konkuk.U2E.global.openApi.gemini.dto.response.RelatedArticle;

import java.util.List;

public record GetNewsInfoResponse(
        List<ClimateProblem> climateList,
        List<String> regionList,
        String newsTitle,
        String newsUrl,
        String newsImageUrl,
        String newsBody,
        String newsDate,
        String aiSolution,
        List<RelatedArticle> aiRelated
) {
    public static GetNewsInfoResponse of(NewsMappingResult newsMappingResult, String aiSolution, List<RelatedArticle> aiRelated) {
        News news = newsMappingResult.news();
        return new GetNewsInfoResponse(
                newsMappingResult.climateProblems(),
                newsMappingResult.regionNames(),
                news.getNewsTitle(),
                news.getNewsUrl(),
                news.getImageUrl(),
                news.getAiSummary(),
                news.getNewsDate().toString(),
                aiSolution,
                aiRelated
        );
    }
}
