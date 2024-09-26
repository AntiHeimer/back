package capstone.Antiheimer.feature.dementia_result.service;

import capstone.Antiheimer.feature.dementia_result.entity.Result;
import capstone.Antiheimer.feature.dementia_result.repository.ResultRepository;
import capstone.Antiheimer.util.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final CheckService checkService;

    /**
     * 진단 결과 리스트 조회
     * @param memberUuid
     * @return
     */
    public List<Result> findResultList(String memberUuid) {

        log.info("[Service] 회원 존재 확인");
        checkService.checkMemberExists(memberUuid);

        log.info("[Service] 진단 결과 리스트 조회");
        return resultRepository.findResultList(memberUuid);
    }
}
