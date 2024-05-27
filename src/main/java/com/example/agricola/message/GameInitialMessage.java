package com.example.agricola.message;

import com.example.agricola.domain.Player;
import java.util.List;

public class GameInitialMessage {
    private String message;
    private List<Player> players;

    public GameInitialMessage(String message, List<Player> players) {
        this.message = message;
        this.players = players;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
