package Konkuk.U2E.domain.comment.domain;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.user.domain.User;
import Konkuk.U2E.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Comment(String contents, News news, User user) {
        this.contents = contents;
        this.news = news;
        this.user = user;
    }
}
