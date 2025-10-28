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

    @Lob
    @Column(name = "ai_solution", columnDefinition = "LONGTEXT")
    private String aiSolution;

    @Column(name = "ai_related1_title", length = 300)
    private String aiRelated1Title;
    @Column(name = "ai_related1_url", columnDefinition = "TEXT")
    private String aiRelated1Url;

    @Column(name = "ai_related2_title", length = 300)
    private String aiRelated2Title;
    @Column(name = "ai_related2_url", columnDefinition = "TEXT")
    private String aiRelated2Url;

    @Column(name = "ai_related3_title", length = 300)
    private String aiRelated3Title;
    @Column(name = "ai_related3_url", columnDefinition = "TEXT")
    private String aiRelated3Url;

    @Lob
    @Column(name = "ai_summary", columnDefinition = "LONGTEXT")
    private String aiSummary;

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

    public void applyAiResult(String solution,
                              String t1, String u1,
                              String t2, String u2,
                              String t3, String u3) {
        this.aiSolution = solution;
        this.aiRelated1Title = t1; this.aiRelated1Url = u1;
        this.aiRelated2Title = t2; this.aiRelated2Url = u2;
        this.aiRelated3Title = t3; this.aiRelated3Url = u3;
    }

    public void applyAiSummary(String summary) {
        this.aiSummary = summary;
    }
}
