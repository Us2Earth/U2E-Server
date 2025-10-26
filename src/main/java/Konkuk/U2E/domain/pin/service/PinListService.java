package Konkuk.U2E.domain.pin.service;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.domain.pin.domain.Region;
import Konkuk.U2E.domain.pin.dto.response.GetPinListResponse;
import Konkuk.U2E.domain.pin.dto.response.PinInfo;
import Konkuk.U2E.domain.pin.exception.InvalidParamException;
import Konkuk.U2E.domain.pin.exception.NewsPinNotFoundException;
import Konkuk.U2E.domain.pin.exception.PinNewsNotFoundException;
import Konkuk.U2E.domain.pin.repository.PinRepository;
import Konkuk.U2E.domain.pin.repository.RegionRepository;
import Konkuk.U2E.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PinListService {

    private final ClimateRepository climateRepository;
    private final NewsPinRepository newsPinRepository;
    private final PinRepository pinRepository;
    private final RegionRepository regionRepository;

    private final DateUtil dateUtil;

    public GetPinListResponse getPinList(String region, String climate, Long newsId) {
        //경우 나누기
        //1. 핀 전체 조회 (모든 파라미터 null)
        //2. 지역으로 핀 검색 (region)
        //3. 기후로 핀 필터링 (climate)
        //4. 최신 뉴스 카드 핀 조회 (newsId)
        if (region == null && climate == null && newsId == null) {
            return GetPinListResponse.of(getPinListByAll());
        }
        if (region != null && climate == null && newsId == null) {
            return GetPinListResponse.of(getPinListByRegion(region));
        }
        if (region == null && climate != null && newsId == null) {
            ClimateProblem climateProblem = ClimateProblem.fromString(climate);
            return GetPinListResponse.of(getPinListByClimate(climateProblem));
        }
        if (region == null && climate == null && newsId != null) {
            return GetPinListResponse.of(getPinListByNewsId(newsId));
        }

        throw new InvalidParamException(INVALID_PARAM);
    }

    private List<PinInfo> getPinListByAll() {
        return pinRepository.findAll().stream()
                .map(this::createPinInfo)
                .toList();
    }

    private List<PinInfo> getPinListByRegion(String regionName) {
        List<Region> regionList = regionRepository.findRegionsByName(regionName.trim());
        return regionList.stream()
                .map(pinRepository::findPinByRegion)
                .map(this::createPinInfo)
                .toList();
    }

    private List<PinInfo> getPinListByClimate(ClimateProblem climateProblem) {
        return climateRepository.findNewsByClimateProblem(climateProblem).stream()
                .map(newsPinRepository::findPinsByNews)
                .flatMap(pinList -> pinList.stream()
                            .map(pin -> this.createPinInfo(pin, climateProblem))
                ).distinct()
                .toList();
    }

    private List<PinInfo> getPinListByNewsId(Long newsId) {
        List<Pin> pins = newsPinRepository.findPinsByNewsId(newsId);
        if (pins.isEmpty()) {
            throw new NewsPinNotFoundException(NEWSPIN_NOT_FOUND);
        }

        return pins.stream()
                .map(this::createPinInfo)
                .toList();
    }

    private PinInfo createPinInfo(Pin pin) {
        List<News> newsList = newsPinRepository.findNewsByPinId(pin.getPinId());
        if (newsList.isEmpty()) {   // 뉴스가 없는 핀은 존재할 수 없으므로 예외처리
            throw new PinNewsNotFoundException(PINNEWS_NOT_FOUND);
        }

        News latestNews = dateUtil.getLatestNews(newsList);
        boolean isLately = dateUtil.checkLately(latestNews.getNewsDate());

        List<ClimateProblem> climateProblems = climateRepository.findClimatesByNews(latestNews).stream()
                .map(Climate::getClimateProblem)
                .toList();
        return PinInfo.of(pin, pin.getRegion(), isLately, climateProblems);
    }

    private PinInfo createPinInfo(Pin pin, ClimateProblem climateProblem) {
        List<News> newsList = newsPinRepository.findNewsByPinId(pin.getPinId());
        if (newsList.isEmpty()) {   // 뉴스가 없는 핀은 존재할 수 없으므로 예외처리
            throw new PinNewsNotFoundException(PINNEWS_NOT_FOUND);
        }

        News latestNews = dateUtil.getLatestNews(
                newsList.stream()
                .filter(news -> climateRepository.existsByNewsAndClimateProblem(news, climateProblem)).toList()     //사용자가 필터링을 요청한 ClimateProblem을 포함하지 않는 경우 제거
        );
        boolean isLately = dateUtil.checkLately(latestNews.getNewsDate());

        List<ClimateProblem> climateProblems = climateRepository.findClimatesByNews(latestNews).stream()
                .map(Climate::getClimateProblem)
                .toList();
        return PinInfo.of(pin, pin.getRegion(), isLately, climateProblems);
    }

}
