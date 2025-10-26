package Konkuk.U2E.domain.news.domain;

import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news_pin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsPin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_pin_id")
    private Long newsPinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id", nullable = false)
    private Pin pin;

    @Builder
    public NewsPin(News news, Pin pin) {
        this.news = news;
        this.pin = pin;
    }
}
