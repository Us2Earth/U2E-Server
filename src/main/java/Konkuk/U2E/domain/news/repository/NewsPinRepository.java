package Konkuk.U2E.domain.news.repository;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.domain.NewsPin;
import Konkuk.U2E.domain.pin.domain.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsPinRepository extends JpaRepository<NewsPin, Long> {
    @Query("SELECT p.region.name FROM NewsPin np " +
            "JOIN np.pin p " +
            "WHERE np.news.newsId = :newsId")
    List<String> findRegionNameByNews(@Param("newsId") Long newsId);

    @Query("SELECT np.news FROM NewsPin np WHERE np.pin.pinId = :pinId")
    List<News> findNewsByPinId(@Param("pinId") Long pinId);

    @Query("SELECT np.pin FROM NewsPin np WHERE np.news.newsId = :newsId")
    List<Pin> findPinsByNewsId(@Param("newsId") Long newsId);

    @Query("SELECT np.pin FROM NewsPin np WHERE np.news = :news")
    List<Pin> findPinsByNews(@Param("news") News news);
}
