package com.example.agricola.controller;

import com.example.agricola.message.GameActionBoardMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ActionBoardController {

    @MessageMapping("/room/{roomId}/actionCardClick")
    @SendTo("/topic/room/{roomId}")
    public GameActionBoardMessage handleAction(String message, @DestinationVariable String roomId) {

        // 0: 사람없음, 1~4: 플레이어 -> 14개 카드
        int[] clickedActionCards = {0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 0};

        // 자원누적이 필요한 카드: 1,2,4,6,11,12,13 번
        int[] resourceActionCards = {};

        System.out.println("Action performed in room " + roomId);
        System.out.println(message);

        return new GameActionBoardMessage("Action performed in room " + roomId + "!", clickedActionCards, resourceActionCards);
    }
}
