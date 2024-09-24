package capstone.Antiheimer.feature.dementia_center.service;

import capstone.Antiheimer.feature.dementia_center.entity.DementiaCenter;
import capstone.Antiheimer.feature.dementia_center.repository.DementiaCenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DementiaCenterService {


    private final DementiaCenterRepository dementiaCenterRepository;

    public Page<DementiaCenter> getCenterByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        log.info("[Service] 치매센터 페이지 반환");
        return dementiaCenterRepository.findAll(pageable);
    }
}
