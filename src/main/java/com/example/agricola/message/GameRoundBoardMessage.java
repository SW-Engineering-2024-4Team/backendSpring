package com.example.agricola.message;

public class GameRoundBoardMessage {
    private String message;
    private int[] clickedActionCards;

    public GameRoundBoardMessage(String message, int[] clickedActionCards) {
        this.message = message;
        this.clickedActionCards = clickedActionCards;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int[] getClickedActionCards() {
        return clickedActionCards;
    }

    public void setClickedActionCards(int[] clickedActionCards) {
        this.clickedActionCards = clickedActionCards;
    }
}
