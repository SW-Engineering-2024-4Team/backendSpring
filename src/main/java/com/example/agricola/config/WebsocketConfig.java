package com.example.agricola.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /*클라이언트가 WebSocket 서버에 연결하기 위한 엔드포인트*/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/agricola-service")
                //.setAllowedOrigins("http://localhost:8094")
                //.addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /* : 메시지 브로커 옵션 설정하는 메서드로 메세지 핸들러의 라우팅 설정 및 브로커가 사용할 목적지 접두사를 정의 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Spring docs 에서는 /topic, /queue로 나오나 편의상 /pub, /sub로 변경
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/agricola-service");  // 애플리케이션 목적지
    }

}
