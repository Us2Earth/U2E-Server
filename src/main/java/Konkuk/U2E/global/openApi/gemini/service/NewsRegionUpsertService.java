package Konkuk.U2E.global.openApi.gemini.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.domain.NewsPin;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.domain.pin.domain.Region;
import Konkuk.U2E.domain.pin.repository.PinRepository;
import Konkuk.U2E.domain.pin.repository.RegionRepository;
import Konkuk.U2E.global.openApi.gemini.dto.response.RegionCandidate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsRegionUpsertService {

    private final RegionRepository regionRepository;
    private final PinRepository pinRepository;
    private final NewsPinRepository newsPinRepository;

    @Transactional
    public void linkFromCandidates(News news, List<RegionCandidate> candidates) {
        if (candidates == null || candidates.isEmpty()) return;

        for (RegionCandidate rc : candidates) {

            Region region = regionRepository
                    .findByNameIgnoreCase(rc.name())
                    .orElseGet(() -> regionRepository.save(
                            Region.builder()
                                    .name(rc.name())
                                    .latitude(rc.latitude())
                                    .longitude(rc.longitude())
                                    .build()
                    ));

            Pin pin = pinRepository.findPinByRegion(region);
            if (pin == null) {
                pin = pinRepository.save(Pin.builder().region(region).build());
            }

            // 이미 연결되어 있으면 skip
            final Long targetPinId = pin.getPinId();
            boolean alreadyLinked = newsPinRepository.findPinsByNews(news).stream()
                    .anyMatch(p -> p.getPinId().equals(targetPinId));

            if (!alreadyLinked) {
                newsPinRepository.save(NewsPin.builder()
                        .news(news)
                        .pin(pin)
                        .build());
            }
        }
    }

}
