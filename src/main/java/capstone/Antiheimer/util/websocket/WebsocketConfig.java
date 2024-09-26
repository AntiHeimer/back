package capstone.Antiheimer.util.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {

    private final ProtectorHandler protectorHandler;
    private final WardHandler wardHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(protectorHandler, "/protector-request").setAllowedOrigins("antiheimer.com");
        registry.addHandler(wardHandler, "/ward-request").setAllowedOrigins("antiheimer.com");
    }
}
