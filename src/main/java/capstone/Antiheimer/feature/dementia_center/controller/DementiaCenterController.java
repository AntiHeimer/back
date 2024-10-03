package capstone.Antiheimer.feature.dementia_center.controller;

import capstone.Antiheimer.feature.dementia_center.entity.DementiaCenter;
import capstone.Antiheimer.feature.dementia_center.service.DementiaCenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/dementia-center")
public class DementiaCenterController {

    private final DementiaCenterService dementiaCenterService;

    @GetMapping("")
    public Page<DementiaCenter> getDementiaCenter(@RequestParam("page") int page) {
        int pageSize = 5;

        log.info("[Controller] 치매센터 페이지 별 반환 시작");
        return dementiaCenterService.getCenterByPage(page, pageSize);
    }
}
