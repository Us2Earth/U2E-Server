package Konkuk.U2E.global.util;

import Konkuk.U2E.domain.news.domain.News;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DateUtil {

    //인자로 넘어온 LocalDate와 현재 날짜를 비교해서 차이가 1달 이하이면 true, 아니면 false
    public boolean checkLately(LocalDate date) {
        LocalDate now = LocalDate.now();
        return !date.isBefore(now.minusMonths(1)) && !date.isAfter(now);
    }

    //뉴스 리스트가 인자로 넘어오면 가장 최신 뉴스 하나를 반환
    public News getLatestNews(List<News> news) {
        News latestNews = news.getFirst();
        for (News n : news) {
            if (n.getNewsDate().isAfter(latestNews.getNewsDate())) {
                latestNews = n;
            }
        }
        return latestNews;
    }

}
