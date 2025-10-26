package Konkuk.U2E.domain.pin.controller;

import Konkuk.U2E.domain.pin.dto.response.GetPinInfoResponse;
import Konkuk.U2E.domain.pin.dto.response.GetPinListResponse;
import Konkuk.U2E.domain.pin.service.PinInfoService;
import Konkuk.U2E.domain.pin.service.PinListService;
import Konkuk.U2E.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pin")
@RequiredArgsConstructor
public class PinController {
    private final PinInfoService pinInfoService;
    private final PinListService pinListService;

    @GetMapping("/{pinId}")
    public BaseResponse<GetPinInfoResponse> viewPinInfo(
            @PathVariable("pinId") final Long pinId
    ) {
        return BaseResponse.ok(pinInfoService.getPinInfo(pinId));
    }

    @GetMapping
    public BaseResponse<GetPinListResponse> viewPinList(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "climate", required = false) String climate,
            @RequestParam(value = "newsId", required = false) Long newsId
            ) {
        return BaseResponse.ok(pinListService.getPinList(region, climate, newsId));
    }
}
