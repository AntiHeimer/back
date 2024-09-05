package capstone.Antiheimer;

import capstone.Antiheimer.dto.websocket.NotificationReqDto;
import capstone.Antiheimer.dto.websocket.NotificationResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebsocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession socketSession) {
        sessions.put(socketSession.getId(), socketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {


        System.out.println("message = " + message.getPayload());

        NotificationReqDto reqDto = objectMapper.readValue(message.getPayload(), NotificationReqDto.class);

        System.out.println("reqDto = " + reqDto);

        String recipientId = reqDto.getToMemberId();
        System.out.println("recipientId = " + recipientId);

        WebSocketSession recipientSession = sessions.get(recipientId);
        System.out.println("recipientSession = " + recipientSession);

        if (recipientSession != null && recipientSession.isOpen()) {
            String jsonResponse = objectMapper.writeValueAsString(reqDto);
            recipientSession.sendMessage(new TextMessage(jsonResponse));
        }
    }
}
