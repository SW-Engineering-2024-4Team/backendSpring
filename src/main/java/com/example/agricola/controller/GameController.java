package com.example.agricola.controller;

import com.example.agricola.domain.Player;
import com.example.agricola.message.GameInitialMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable; // 해당 매개변수가 websocket 메시지의 목적지에 대한 변수임을 나타냄
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    private List<Player> players = new ArrayList<>();

    @MessageMapping("/room/{roomId}/start")
    @SendTo("/topic/room/{roomId}")
    public GameInitialMessage gameStart(@DestinationVariable String roomId) {
        // 플레이어 목록 초기화
        players = initializePlayers();

        // Here, add any logic you need to initialize the game state.
        System.out.println("Game started in room " + roomId);

        return new GameInitialMessage("Game has started in room " + roomId + "!", players);
    }

    private List<Player> initializePlayers() {
        // Example initialization logic for players
        List<Player> initialPlayers = new ArrayList<>();
        initialPlayers.add(new Player("Player 1"));
        initialPlayers.add(new Player("Player 2"));
        // Add more players as needed
        return initialPlayers;
    }
}
