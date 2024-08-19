package capstone.Antiheimer.repository;

import capstone.Antiheimer.domain.Active;
import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.domain.MemberData;
import capstone.Antiheimer.dto.ActiveReqDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDataRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public void saveActive(ActiveReqDto request) {

        Active active = new Active();

        active.setDate(request.getDate());
        active.setActiveEnergyBurned(request.getActiveEnergyBurned());
        active.setActiveEnergyBurnedGoal(request.getActiveEnergyBurnedGoal());
        active.setAppleExerciseTime(request.getAppleExerciseTime());
        active.setAppleExerciseTimeGoal(request.getAppleExerciseTimeGoal());
        active.setAppleStandHours(request.getAppleStandHours());
        active.setAppleStandHoursGoal(request.getAppleStandHoursGoal());

        em.persist(active);

        Member findMember = memberRepository.findOneByUuid(request.getMemberUuid());

        // MemberData에서 findMember와 uuid가 같고 date가 같은
        List<MemberData> memberDataList =
                em.createQuery("select m from MemberData m where m.date = :date and m.member = :findMember", MemberData.class)
                .setParameter("findMember", findMember).setParameter("date", active.getDate())
                .getResultList();

        if (memberDataList.isEmpty()) {

//            MemberData newMemberData = new MemberData();
//            newMemberData.setActiveList();
        }



        /*
        * 만약 memberData에서 findMember와 date가 동시에 일치하는 객체가 존재한다면 해당 memberData 객체에 추가
        * 없으면 새로운 memberData 객체 만들기
        * */

        for (MemberData memberData : memberDataList) {

        }
    }
}
