package Konkuk.U2E.global.config.initData;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.domain.NewsPin;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.domain.pin.domain.Region;
import Konkuk.U2E.domain.pin.repository.PinRepository;
import Konkuk.U2E.domain.pin.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RegionPinInitializer {
    private final RegionRepository regionRepository;
    private final PinRepository pinRepository;
    private final NewsPinRepository newsPinRepository;
    private final NewsInitializer newsInitializer;
    private final Random random = new Random();

    private final List<List<String>> regionNamesPerNews = List.of(
            List.of("London", "Los Angeles", "South Korea"),
            List.of("India"),
            List.of("Dorset"),
            List.of("Devon", "Cornwall", "Bampton"),
            List.of("South Korea", "Uiseong County"),
            List.of("South Korea"),
            List.of("United Kingdom"),
            List.of("United Kingdom", "Devon"),
            List.of("South Korea"),
            List.of("South Korea"),
            List.of("United Kingdom", "Marsden", "Denholme"),
            List.of("United Kingdom", "Inverness", "Culdershell"),
            List.of("United Kingdom", "Bowling"),
            List.of("United Kingdom", "Shap"),
            List.of("United Kingdom", "Dorset"),
            List.of("United Kingdom", "Wales"),
            List.of("United Kingdom", "Northern Ireland"),
            List.of("United Kingdom", "Scotland"),
            List.of("Missouri", "Texas", "Oklahoma"),
            List.of("Cumbria", "Workington")
    );

    private final Map<String, BigDecimal[]> regionCoordinates = Map.ofEntries(
            Map.entry("London", new BigDecimal[]{new BigDecimal("51.5072"), new BigDecimal("0.1276")}),
            Map.entry("Los Angeles", new BigDecimal[]{new BigDecimal("34.0522"), new BigDecimal("118.2437")}),
            Map.entry("South Korea", new BigDecimal[]{new BigDecimal("35.9078"), new BigDecimal("127.7669")}),
            Map.entry("India", new BigDecimal[]{new BigDecimal("20.5937"), new BigDecimal("78.9629")}),
            Map.entry("Dorset", new BigDecimal[]{new BigDecimal("50.7488"), new BigDecimal("2.3445")}),
            Map.entry("Devon", new BigDecimal[]{new BigDecimal("50.7184"), new BigDecimal("-3.5339")}),
            Map.entry("Cornwall", new BigDecimal[]{new BigDecimal("50.2653"), new BigDecimal("-5.0547")}),
            Map.entry("Bampton", new BigDecimal[]{new BigDecimal("51.0152"), new BigDecimal("-3.4874")}),
            Map.entry("Uiseong County", new BigDecimal[]{new BigDecimal("36.3526"), new BigDecimal("128.6977")}),
            Map.entry("United Kingdom", new BigDecimal[]{new BigDecimal("51.4590"), new BigDecimal("-2.1190")}),
            Map.entry("Marsden", new BigDecimal[]{new BigDecimal("53.6102"), new BigDecimal("-1.9552")}),
            Map.entry("Denholme", new BigDecimal[]{new BigDecimal("53.7940"), new BigDecimal("-1.8950")}),
            Map.entry("Inverness", new BigDecimal[]{new BigDecimal("57.4778"), new BigDecimal("-4.2247")}),
            Map.entry("Culdershell", new BigDecimal[]{new BigDecimal("57.4547"), new BigDecimal("-4.2252")}), // 원지명 불확실
            Map.entry("Bowling", new BigDecimal[]{new BigDecimal("55.9300"), new BigDecimal("-4.4840")}),
            Map.entry("Shap", new BigDecimal[]{new BigDecimal("54.5262"), new BigDecimal("-2.6771")}),
            Map.entry("Wales", new BigDecimal[]{new BigDecimal("51.6563"), new BigDecimal("-3.4261")}),
            Map.entry("Northern Ireland", new BigDecimal[]{new BigDecimal("54.6000"), new BigDecimal("-6.7500")}),
            Map.entry("Scotland", new BigDecimal[]{new BigDecimal("56.4907"), new BigDecimal("-4.2026")}),
            Map.entry("Missouri", new BigDecimal[]{new BigDecimal("38.5739"), new BigDecimal("-92.6038")}),
            Map.entry("Texas", new BigDecimal[]{new BigDecimal("31.9686"), new BigDecimal("-99.9018")}),
            Map.entry("Oklahoma", new BigDecimal[]{new BigDecimal("35.4676"), new BigDecimal("-97.5164")}),
            Map.entry("Cumbria", new BigDecimal[]{new BigDecimal("54.5770"), new BigDecimal("-2.7975")}),
            Map.entry("Workington", new BigDecimal[]{new BigDecimal("54.6422"), new BigDecimal("-3.5445")})
    );

    public void initializeRegionsAndPins() {
        if (pinRepository.count() > 0 && regionRepository.count() > 0 && newsPinRepository.count() > 0) return;

        List<News> newsList = newsInitializer.getNewsList();

        Map<String, Region> regionMap = new java.util.HashMap<>();
        Map<String, Pin> pinMap = new java.util.HashMap<>();

        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);

            for (String regionName : regionNamesPerNews.get(i)) {
                Region region = regionMap.computeIfAbsent(regionName, name -> {
                    BigDecimal[] coords = regionCoordinates.get(name);
                    Region newRegion = Region.builder()
                            .name(name)
                            .latitude(coords[0])
                            .longitude(coords[1])
                            .build();
                    return regionRepository.save(newRegion);
                });

                Pin pin = pinMap.computeIfAbsent(regionName, name -> {
                    Pin newPin = Pin.builder().region(region).build();
                    return pinRepository.save(newPin);
                });

                NewsPin newsPin = NewsPin.builder()
                        .news(news)
                        .pin(pin)
                        .build();
                newsPinRepository.save(newsPin);
            }
        }
    }
}
