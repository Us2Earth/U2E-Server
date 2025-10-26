package Konkuk.U2E.domain.news.service.mapper;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class NewsMapperFactory {        //news -> 관련된 기후문제 리스트 & 관련된 지역 이름 리스트 & news 로 매핑해주는 함수형 인터페이스 생성

    private final ClimateRepository climateRepository;
    private final NewsPinRepository newsPinRepository;

    public Function<News, NewsMappingResult> newsMappingFunction(
    ) {
        return news -> {
            List<String> regionNames = newsPinRepository.findRegionNameByNews(news.getNewsId());
            List<ClimateProblem> climateProblems = climateRepository.findClimatesByNews(news).stream()
                    .map(Climate::getClimateProblem)
                    .toList();

            return new NewsMappingResult(climateProblems, regionNames, news);
        };
    }
}
