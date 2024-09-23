package capstone.Antiheimer.feature.dimentia_center.controller;

import capstone.Antiheimer.feature.dimentia_center.entity.DimentiaCenter;
import capstone.Antiheimer.feature.dimentia_center.service.DimentiaCenterService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DimentiaCenterController {

    @Autowired
    private DimentiaCenterService dimentiaCenterService;

    @GetMapping("/dimentia_center")
    public Page<DimentiaCenter> getDimentiaCenter(@RequestParam("page") int page) {
        int pageSize = 5;
        return dimentiaCenterService.getCenterByPage(page, pageSize);
    }
}
