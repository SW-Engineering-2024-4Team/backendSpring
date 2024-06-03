package com.example.agricola.controller;

import com.example.agricola.message.GameActionBoardMessage;
import com.example.agricola.message.GameRoundBoardMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RoundBoardController {

    @MessageMapping("/room/{roomId}/roundCardClick")
    @SendTo("/topic/room/{roomId}")
    public GameRoundBoardMessage handleAction(String message, @DestinationVariable String roomId) {
        // 예제 클릭된 액션 카드 상태
        int[] clickedRoundCards = {0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 0};

        System.out.println("Round performed in room " + roomId);
        System.out.println(message);

        return new GameRoundBoardMessage("Round performed in room " + roomId + "!", clickedRoundCards);
    }
}
