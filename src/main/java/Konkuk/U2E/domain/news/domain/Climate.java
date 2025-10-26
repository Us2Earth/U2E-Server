package Konkuk.U2E.domain.news.domain;

import Konkuk.U2E.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "climates")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Climate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "climate_id")
    private Long climateId;

    @Column(name = "climate_problem", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ClimateProblem climateProblem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Builder
    public Climate(ClimateProblem climateProblem, News news) {
        this.climateProblem = climateProblem;
        this.news = news;
    }
}
