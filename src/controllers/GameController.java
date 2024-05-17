package controllers;

import cards.common.Card;
import models.*;
import java.util.*;

public class GameController {
    private String gameID;
    private List<Player> players;
    private List<Player> turnOrder;
    private MainBoard mainBoard;
    private CardController cardController;
    private RoomController roomController;
    private int currentRound;

    public GameController(String gameID, RoomController roomController, List<Player> players) {
        this.gameID = gameID;
        this.players = players;
        this.mainBoard = new MainBoard();
        this.cardController = CardController.getInstance();
        this.roomController = roomController;
        this.currentRound = 1;

        this.turnOrder = new ArrayList<>(players);
        Collections.shuffle(this.turnOrder);

        initializeFisrtFoods();
    }

    private void initializeFisrtFoods() {
        for (int i = 0; i < turnOrder.size(); i++) {
            Player player = turnOrder.get(i);
            if (i == 0) {
                player.addResource("food", 2);
                player.setFirstPlayer(true);
            } else {
                player.addResource("food", 3);
            }
        }
    }

    public void initializeGame() {
        setupPlayers();
        setupMainBoard();
        notifyInitializationComplete();
    }

    private void setupPlayers() {
        List<Card> occupationDeck = cardController.getDeck("occupationCards");
        List<Card> minorImprovementDeck = cardController.getDeck("minorImprovementCards");
        cardController.shuffleDeck(occupationDeck);
        cardController.shuffleDeck(minorImprovementDeck);

        for (Player player : players) {
            for (int j = 0; j < 7; j++) {
                player.addCard(occupationDeck.get(j), "occupation");
                player.addCard(minorImprovementDeck.get(j), "minorImprovement");
            }
        }
    }

    private void setupMainBoard() {
        List<Card> actionCards = cardController.getDeck("actionCards");
        List<List<Card>> roundCycles = cardController.getShuffledRoundCardsByCycle();
        List<Card> majorImprovementCards = cardController.getDeck("majorImprovementCards");

        mainBoard.initializeBoard(actionCards, roundCycles, majorImprovementCards);
    }

    private void notifyInitializationComplete() {
        // 클라이언트에게 초기화 완료 메시지 전송
    }

    public void startGame() {
        while (currentRound <= 14) {
            prepareRound();
            playRound();
            if (isHarvestRound(currentRound)) {
                harvestPhase();
            }
            endRound();
            currentRound++;
        }
        endGame();
    }

    private void prepareRound() {
        mainBoard.revealRoundCard(currentRound);
        mainBoard.accumulateResources();
    }

    private void playRound() {
        boolean roundFinished = false;
        while (!roundFinished) {
            for (Player player : turnOrder) {
                if (player.hasAvailableFamilyMembers()) {
                    playerTurn(player.getId());
                }
            }
            roundFinished = checkIfRoundFinished();
        }
        resetFamilyMembers();
    }

    private void playerTurn(String playerID) {
        Player player = getPlayerByID(playerID);
        if (player != null) {
            // WebSocketService.sendMessageToClient(playerID, "Your turn to place a family member.");
        }
    }

    private boolean checkIfRoundFinished() {
        for (Player player : players) {
            if (player.hasAvailableFamilyMembers()) {
                return false;
            }
        }
        return true;
    }

    private void harvestPhase() {
        farmPhase();
        feedFamilyPhase();
        breedAnimalsPhase();
    }

    private void farmPhase() {
        for (Player player : players) {
            PlayerBoard board = player.getPlayerBoard();
            for (Tile[] row : board.getTiles()) {
                for (Tile tile : row) {
                    if (tile instanceof FieldTile) {
                        FieldTile field = (FieldTile) tile;
                        if (field.getCrops() > 0) {
                            player.addResource("grain", 1);
                            field.removeCrop(1);
                        }
                    }
                }
            }
        }
        notifyPlayers("농장 단계 완료. 가족 먹여살리기 단계로 진행하세요.");
    }

    private void feedFamilyPhase() {
        for (Player player : players) {
            int foodNeeded = calculateFoodNeeded(player);
            if (player.getResource("food") >= foodNeeded) {
                player.addResource("food", -foodNeeded);
            } else {
                int foodShortage = foodNeeded - player.getResource("food");
                player.addResource("food", -player.getResource("food"));
                player.addResource("beggingCard", foodShortage);
            }
        }
        notifyPlayers("가족 먹여살리기 단계 완료. 가축 번식 단계로 진행하세요.");
    }

    private int calculateFoodNeeded(Player player) {
        int foodNeeded = 0;
        for (FamilyMember[] row : player.getPlayerBoard().getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null) {
                    foodNeeded += member.isAdult() ? 2 : 1;
                }
            }
        }
        return foodNeeded;
    }

    private void breedAnimalsPhase() {
        for (Player player : players) {
            List<Animal> newAnimals = new ArrayList<>();
            Map<String, Integer> animalCounts = countAnimals(player);

            for (Map.Entry<String, Integer> entry : animalCounts.entrySet()) {
                if (entry.getValue() >= 2) {
                    newAnimals.add(new Animal(entry.getKey()));
                }
            }
        }
        notifyPlayers("가축 번식 단계 완료. 수확 단계 종료.");
    }

    private Map<String, Integer> countAnimals(Player player) {
        Map<String, Integer> animalCounts = new HashMap<>();
        for (Animal[] row : player.getPlayerBoard().getAnimals()) {
            for (Animal animal : row) {
                if (animal != null) {
                    animalCounts.put(animal.getType(), animalCounts.getOrDefault(animal.getType(), 0) + 1);
                }
            }
        }
        return animalCounts;
    }

    private boolean isHarvestRound(int round) {
        return round == 4 || round == 7 || round == 9 || round == 11 || round == 13 || round == 14;
    }

    private void endRound() {
        for (Player player : players) {
            player.convertBabiesToAdults();
        }
        calculateAndRecordScores();
    }

    private void resetFamilyMembers() {
        for (Player player : players) {
            player.resetFamilyMembers();
        }
    }

    private void calculateAndRecordScores() {
        for (Player player : players) {
            int score = calculateScoreForPlayer(player);
            player.setScore(score);
        }
        // WebSocketService.sendMessageToClient(gameID, "gameScores", getScores());
    }

    private int calculateScoreForPlayer(Player player) {
        return 0;
    }

    private List<Map<String, Object>> getScores() {
        List<Map<String, Object>> scores = new ArrayList<>();
        for (Player player : players) {
            Map<String, Object> playerScore = new HashMap<>();
            playerScore.put("playerID", player.getId());
            playerScore.put("score", player.getScore());
            scores.add(playerScore);
        }
        return scores;
    }

    public void endGame() {
        roomController.handleGameEnd();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void executeCard(String playerID, String cardID) {
        Player player = getPlayerByID(playerID);
        Card card = getCardByID(cardID);
        card.execute(player);
    }

    private Player getPlayerByID(String playerID) {
        for (Player player : players) {
            if (player.getId().equals(playerID)) {
                return player;
            }
        }
        return null;
    }

    private Card getCardByID(String cardID) {
        return null;
    }

    private void notifyPlayers(String message) {
        // 프론트엔드에 메시지를 보내는 로직 필요
    }
}
