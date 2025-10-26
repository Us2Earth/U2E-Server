package Konkuk.U2E.domain.news.controller;

import Konkuk.U2E.domain.news.dto.response.GetLatelyNewsResponse;
import Konkuk.U2E.domain.news.dto.response.GetNewsInfoResponse;
import Konkuk.U2E.domain.news.service.NewsInfoService;
import Konkuk.U2E.domain.news.service.NewsLatelyService;
import Konkuk.U2E.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsLatelyService newsLatelyService;
    private final NewsInfoService newsInfoService;

    @GetMapping("/lately")
    public BaseResponse<GetLatelyNewsResponse> viewLatelyNewsList() {
        return BaseResponse.ok(newsLatelyService.getLatelyNews());
    }

    @GetMapping("/{newsId}")
    public BaseResponse<GetNewsInfoResponse> viewNewsInfo(@PathVariable("newsId") final Long newsId) {
        return BaseResponse.ok(newsInfoService.getNewsInfo(newsId));
    }
}
