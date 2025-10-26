package Konkuk.U2E.global.config.initData;

import Konkuk.U2E.domain.comment.domain.Comment;
import Konkuk.U2E.domain.comment.repository.CommentRepository;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.user.domain.User;
import Konkuk.U2E.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentUserInitializer {
    private final NewsInitializer newsInitializer;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final List<String> usernames = List.of(
            "Gil-dong Hong",
            "Chul-soo Kim",
            "Young-hee Lee",
            "Ji-min Park",
            "Woo-sung Jung",
            "Ji-min Han",
            "Min-ho Lee",
            "Tae-hee Kim",
            "Jae-suk Yoo",
            "IU",

            "Soo-jin Choi",
            "Hyun-woo Kang",
            "Seul-gi Yoon",
            "Hye-jin Jang",
            "Dae-hoon Lim",
            "Jin-young Bae",
            "Yuna Kim",
            "Hae-jin Lee",
            "Bo-gum Park",
            "Na-yeon Son",

            "John Smith",
            "Emily Johnson",
            "Michael Brown",
            "Olivia Davis",
            "David Wilson",
            "Sophia Martinez",
            "James Anderson",
            "Isabella Garcia",
            "Daniel Thomas",
            "Emma Rodriguez",

            "William Lee",
            "Ava Kim",
            "Henry Nguyen",
            "Charlotte Chen",
            "Liam Patel",
            "Amelia Park",
            "Noah Zhao",
            "Mia Wang",
            "Ethan Choi",
            "Grace Jeong"
    );

    private final List<String> comments = List.of(
            "This is truly shocking news.",
            "I hope something like this never happens again.",
            "I didn't realize climate change was this serious.",
            "Thanks for the helpful information!",
            "The government's response is disappointing.",
            "It seems like more measures are needed.",
            "I'm worried about the local residents.",
            "More people need to be aware of news like this.",
            "This really highlights the importance of environmental protection.",
            "Stay safe, everyone!"
    );

    public void initializeUsersAndComments() {
        if (commentRepository.count() > 0 && userRepository.count() > 0) return;

        List<News> newsList = newsInitializer.getNewsList();

        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);

            User user1 = userRepository.save(
                    User.builder()
                            .name(usernames.get((i * 2) % usernames.size()))
                            .password("test1234")
                            .build()
            );

            User user2 = userRepository.save(
                    User.builder()
                            .name(usernames.get((i * 2 + 1) % usernames.size()))
                            .password("test1234")
                            .build()
            );

            commentRepository.save(
                    Comment.builder()
                            .contents(comments.get((i * 2) % comments.size()))
                            .news(news)
                            .user(user1)
                            .build()
            );

            commentRepository.save(
                    Comment.builder()
                            .contents(comments.get((i * 2 + 1) % comments.size()))
                            .news(news)
                            .user(user2)
                            .build()
            );
        }
    }
}
