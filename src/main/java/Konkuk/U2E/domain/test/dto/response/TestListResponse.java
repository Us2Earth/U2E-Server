package Konkuk.U2E.domain.test.dto.response;

import Konkuk.U2E.domain.test.dto.response.model.TestInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestListResponse {

    private List<TestInfo> testList;

    public TestListResponse(List<TestInfo> testList) {
        this.testList = testList;
    }

    public static TestListResponse of(List<TestInfo> testList) {
        return new TestListResponse(testList);
    }
}
