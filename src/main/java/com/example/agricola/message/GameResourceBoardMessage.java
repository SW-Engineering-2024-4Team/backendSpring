package com.example.agricola.message;

import com.example.agricola.controller.ResourceBoardController.Resource;

public class GameResourceBoardMessage {
    private String message;
    private Resource[] resources;

    public GameResourceBoardMessage(String message, Resource[] resources) {
        this.message = message;
        this.resources = resources;
    }

    public String getMessage() {
        return message;
    }

    public Resource[] getResources() {
        return resources;
    }
}
