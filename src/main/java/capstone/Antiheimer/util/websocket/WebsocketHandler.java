package capstone.Antiheimer.util.websocket;

import capstone.Antiheimer.feature.member.MemberRepository;
import capstone.Antiheimer.feature.notification.dto.NotificationReqDto;
import capstone.Antiheimer.feature.notification.dto.NotificationResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession socketSession) {
        sessions.put(socketSession.getId(), socketSession);

        log.info("websocket 연결 성공");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info("message 수신 성공");
        System.out.println("message = " + message.getPayload());

        NotificationReqDto reqDto = objectMapper.readValue(message.getPayload(), NotificationReqDto.class);
        System.out.println("reqDto = " + reqDto);

        String toMemberId = reqDto.getToMemberId();
        String toMemberUuid = memberRepository.findOneById(toMemberId).getUuid();
        String fromMemberName = memberRepository.findOneByUuid(reqDto.getFromMemberUuid()).getName();
        System.out.println("toMemberUuid = " + toMemberUuid);

        // 사용자 ID를 세션과 매핑
        sessions.put(toMemberUuid, session); // 세션을 사용자 ID로 저장
        System.out.println("session = " + session);

        WebSocketSession recipientSession = sessions.get(toMemberUuid);
        System.out.println("recipientSession = " + recipientSession);

        if (recipientSession != null && recipientSession.isOpen()) {
            NotificationResDto resDto = new NotificationResDto(reqDto.getFromMemberUuid(), fromMemberName, toMemberUuid, reqDto.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(resDto);
            recipientSession.sendMessage(new TextMessage(jsonResponse));
        }
    }
}
