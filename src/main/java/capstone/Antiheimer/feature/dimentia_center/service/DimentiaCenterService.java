package capstone.Antiheimer.feature.dimentia_center.service;

import capstone.Antiheimer.feature.dimentia_center.entity.DimentiaCenter;
import capstone.Antiheimer.feature.dimentia_center.repository.DimentiaCenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DimentiaCenterService {

    @Autowired
    private DimentiaCenterRepository dimentiaCenterRepository;

    public Page<DimentiaCenter> getCenterByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return dimentiaCenterRepository.findAll(pageable);
    }
}
