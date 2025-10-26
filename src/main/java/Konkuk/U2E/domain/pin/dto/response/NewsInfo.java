package Konkuk.U2E.domain.pin.dto.response;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;

import java.util.List;

public record NewsInfo(
        List<ClimateProblem> climateList,
        Long newsId,
        String newsTitle,
        String newsBody,
        String newsDate
) {
    public static NewsInfo of(List<ClimateProblem> climateList, News news) {
        return new NewsInfo(climateList, news.getNewsId(), news.getNewsTitle(), news.getNewsBody(), news.getNewsDate().toString());
    }
}
