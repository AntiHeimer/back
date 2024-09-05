package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.websocket.NotificationReqDto;
import capstone.Antiheimer.dto.websocket.NotificationResDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

//    @MessageMapping("/send-notification")
//    @SendTo("/topic/messages")
//    public NotificationResDto sendRequest(NotificationReqDto message) {
//        // 로직: 요청을 저장하거나 처리
//        return new NotificationResDto("dfjak", "jfsdklf", "수락");
//    }

//    @MessageMapping("/responseRequest")
//    @SendTo("/queue/responses")
//    public NotificationResDto responseRequest(NotificationReqDto message) {
//        // 로직: 요청에 대한 응답 처리
//        return ;
//    }
}
