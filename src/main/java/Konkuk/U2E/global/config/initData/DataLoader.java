package Konkuk.U2E.global.config.initData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final NewsInitializer newsInitializer;
    private final RegionPinInitializer regionPinInitializer;
    private final CommentUserInitializer commentUserInitializer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        newsInitializer.initializeNews();
        regionPinInitializer.initializeRegionsAndPins();
        commentUserInitializer.initializeUsersAndComments();
    }
}
