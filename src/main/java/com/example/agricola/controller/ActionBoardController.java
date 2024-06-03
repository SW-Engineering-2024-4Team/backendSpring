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
        // 예제 클릭된 액션 카드 상태
        int[] clickedActionCards = {0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 0};

        System.out.println("Action performed in room " + roomId);
        System.out.println(message);

        return new GameActionBoardMessage("Action performed in room " + roomId + "!", clickedActionCards);
    }
}
