//package capstone.Antiheimer.firebase;
//
//import capstone.Antiheimer.member.Member;
//import capstone.Antiheimer.dto.TokenReqDto;
//import capstone.Antiheimer.exception.NotExistException;
//import capstone.Antiheimer.member.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class FcmService {
//
//    private final MemberRepository memberRepository;
//
//    /**
//     *
//     * @param tokenReqDto
//     */
//    @Transactional
//    public void updateDeviceToken(TokenReqDto tokenReqDto) {
//
//        Member findMember = memberRepository.findOneByUuid(tokenReqDto.getMemberUuid());
//
//        if (findMember == null) {
//
//            log.warn("존재하지 않는 회원입니다");
//            throw new NotExistException();
//        }
//        memberRepository.updateDeviceToken(tokenReqDto);
//        log.info("디바이스 토큰 저장 성공");
//    }
//
//    public void sendNotification(String targetToken, String title, String body) {
//
//        Notification notification = Notification.builder()
//                .setTitle(title)
//                .setBody(body)
//                .build();
//
//        Message message = Message.builder()
//                .setToken(targetToken)
//                .setNotification(notification)
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("Successfully sent message: " + response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
