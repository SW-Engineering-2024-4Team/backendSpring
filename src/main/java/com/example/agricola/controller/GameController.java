package com.example.agricola.controller;

import com.example.agricola.domain.Player;
import org.springframework.messaging.handler.annotation.DestinationVariable; // 해당 매개변수가 websocket 메시지의 목적지에 대한 변수임을 나타냄
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {
    private List<Player> players;

    @MessageMapping("/room/{roomId}/start")
    @SendTo("/topic/room/{roomId}")
    public void gameStart(@DestinationVariable String roomId) {
        //플레이어 목록 초기화


        // Here, add any logic you need to initialize the game state.
        //System.out.println("Game started in room " + roomId);
        //return new GameInitialMessage("Game has started in room " + roomId + "!");
    }
}
