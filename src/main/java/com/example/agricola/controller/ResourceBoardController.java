package com.example.agricola.controller;

import com.example.agricola.message.GameResourceBoardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ResourceBoardController {

    // Resource 클래스를 정의합니다.
    public static class Resource {
        private String name;
        private int count;

        public Resource(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 주기적으로 리소스를 업데이트할 수 있도록 설정합니다.
    private Resource[] resources = {
            new Resource("Wood", 1),
            new Resource("Soil", 2),
            new Resource("Rock", 3),
            new Resource("Food", 4),
            new Resource("Sheep", 5),
            new Resource("Grain", 0),
            new Resource("Adult", 0),
            new Resource("Newborn", 0),
            new Resource("Fence", 0),
            new Resource("Barn", 0)
    };

    @MessageMapping("/room/{roomId}/resource")
    @SendTo("/topic/room/{roomId}")
    public GameResourceBoardMessage handleAction(@DestinationVariable String roomId) {
        return new GameResourceBoardMessage("Action performed in room " + roomId + "!", resources);
    }

    @Scheduled(fixedRate = 5000)  // 5초마다 실행
    public void sendPeriodicUpdates() {
        // 예시로 roomId를 "1"로 설정하였습니다. 필요시 다른 roomId로 변경 가능
        String roomId = "1";
        sendResourceUpdate(roomId);
    }

    private void sendResourceUpdate(String roomId) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, new GameResourceBoardMessage("Periodic update in room " + roomId + "!", resources));
    }
}
