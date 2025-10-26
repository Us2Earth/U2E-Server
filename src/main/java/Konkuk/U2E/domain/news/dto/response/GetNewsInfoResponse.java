package Konkuk.U2E.domain.news.dto.response;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.service.mapper.NewsMappingResult;

import java.util.List;

public record GetNewsInfoResponse(
        List<ClimateProblem> climateList,
        List<String> regionList,
        String newsTitle,
        String newsUrl,
        String newsImageUrl,
        String newsBody,
        String newsDate
) {
    public static GetNewsInfoResponse of(NewsMappingResult newsMappingResult) {
        News news = newsMappingResult.news();
        return new GetNewsInfoResponse(newsMappingResult.climateProblems(), newsMappingResult.regionNames(), news.getNewsTitle(), news.getNewsUrl(), news.getImageUrl(), news.getNewsBody(), news.getNewsDate().toString());
    }
}
