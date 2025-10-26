package Konkuk.U2E.domain.news.domain;

import Konkuk.U2E.domain.comment.domain.Comment;
import Konkuk.U2E.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "news_url", nullable = false, columnDefinition = "TEXT")
    private String newsUrl;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "news_title", nullable = false, length = 200)
    private String newsTitle;

    @Lob
    @Column(name = "news_body", nullable = false, columnDefinition = "LONGTEXT")
    private String newsBody;

    @Column(name = "news_date", nullable = false)
    private LocalDate newsDate;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Climate> climateList = new ArrayList<>();

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public News(String newsUrl, String imageUrl, String newsTitle, String newsBody, LocalDate newsDate, List<Climate> climateList) {
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
        this.newsTitle = newsTitle;
        this.newsBody = newsBody;
        this.newsDate = newsDate;
        this.climateList = climateList;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setNews(this);
    }
}
