package Konkuk.U2E.domain.pin.service;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.dto.response.GetPinInfoResponse;
import Konkuk.U2E.domain.pin.dto.response.NewsInfo;
import Konkuk.U2E.domain.pin.exception.PinNewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class PinInfoService {
    private final NewsPinRepository newsPinRepository;
    private final ClimateRepository climateRepository;

    public GetPinInfoResponse getPinInfo(Long pinId) {
        List<News> newsList = newsPinRepository.findNewsByPinId(pinId);
        if (newsList.isEmpty()) {
            throw new PinNewsNotFoundException(PINNEWS_NOT_FOUND);
        }
        return GetPinInfoResponse.of(newsList.stream()
                .map(news -> NewsInfo.of(climateRepository.findClimatesByNews(news).stream()
                            .map(Climate::getClimateProblem)
                            .toList(), news)
                )
                .toList()
        );
    }

}
