package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.HealthData;
import capstone.Antiheimer.dto.SaveSleepReqDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;

@Repository
@RequiredArgsConstructor
public class SleepDataRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public void saveSleep (SaveSleepReqDto request) {

        HealthData healthData = new HealthData();


    }
}
