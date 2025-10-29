package Konkuk.U2E.domain.news.repository;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClimateRepository extends JpaRepository<Climate, Long> {
    List<Climate> findClimatesByNews(News news);

    @Query("SELECT c.news FROM Climate c WHERE c.climateProblem = :climateProblem")
    List<News> findNewsByClimateProblem(@Param("climateProblem") ClimateProblem climateProblem);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Climate c WHERE c.news = :news AND c.climateProblem = :climateProblem")
    boolean existsByNewsAndClimateProblem(@Param("news") News news,
                                          @Param("climateProblem") ClimateProblem climateProblem);

    void deleteAllByNews(News news);
}
