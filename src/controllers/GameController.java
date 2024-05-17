package controllers;

import cards.Card;
import controllers.CardController;
import controllers.RoomController;
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

    // 생성자
    public GameController(String gameID, RoomController roomController, List<Player> players) {
        this.gameID = gameID;
        this.players = players;
        this.mainBoard = new MainBoard();
        this.cardController = CardController.getInstance();
        this.roomController = roomController;
        this.currentRound = 1;

        // 플레이어 순서를 랜덤하게 섞기
        this.turnOrder = new ArrayList<>(players);
        Collections.shuffle(this.turnOrder);

        // 플레이어 초기화
        initializePlayers();
    }

    // 플레이어 초기화 (초기 자원 설정)
    private void initializePlayers() {
        for (int i = 0; i < turnOrder.size(); i++) {
            Player player = turnOrder.get(i);
            if (i == 0) {
                player.addResource("food", 2); // 첫 번째 플레이어는 음식 자원 2개
            } else {
                player.addResource("food", 3); // 나머지 플레이어는 음식 자원 3개
            }
        }
    }

    // 게임 초기화 메서드
    public void initializeGame() {
        setupPlayers();
        setupMainBoard();
        notifyInitializationComplete();
    }

    // 플레이어 설정
    private void setupPlayers() {
        List<Card> occupationDeck = cardController.getDeck("occupationCards");
        List<Card> minorImprovementDeck = cardController.getDeck("minorImprovementCards");
        cardController.shuffleDeck(occupationDeck);
        cardController.shuffleDeck(minorImprovementDeck);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            for (int j = 0; j < 7; j++) {
                player.addCard(occupationDeck.get(i * 7 + j), "occupation");
                player.addCard(minorImprovementDeck.get(i * 7 + j), "minorImprovement");
            }
        }
    }

    // 메인 보드 설정
    private void setupMainBoard() {
        List<Card> actionCards = cardController.getDeck("actionCards");
        List<Card> roundCards = new ArrayList<>();
        roundCards.addAll(cardController.shuffleDeck(cardController.getDeck("roundCycle1")));
        roundCards.addAll(cardController.shuffleDeck(cardController.getDeck("roundCycle2")));
        // 다른 사이클 추가...
        List<Card> majorImprovementCards = cardController.getDeck("majorImprovement");

        mainBoard.initializeBoard(actionCards, roundCards, majorImprovementCards);
    }

    // 초기화 완료 알림
    private void notifyInitializationComplete() {
        // 클라이언트에게 초기화 완료 메시지 전송
        // WebSocketService.sendMessageToClient(gameID, "initializationComplete", null);
    }

    // 게임 시작 메서드
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


    // 라운드 준비
    private void prepareRound() {
        mainBoard.revealRoundCard(currentRound);
        mainBoard.accumulateResources();
    }

    // 라운드 플레이
    private void playRound() {
        boolean roundFinished = false;
        while (!roundFinished) {
            for (Player player : turnOrder) {
                if (player.hasAvailableFamilyMembers()) {
                    playerTurn(player.getId());
                    // 플레이어의 턴 진행 로직
                    // 플레이어가 가족 구성원을 배치하고 카드를 실행
                    // 다음 플레이어로 이동
                }
            }
            roundFinished = checkIfRoundFinished();
        }
        resetFamilyMembers(); // 라운드가 끝난 후 가족 구성원을 집으로 돌려보냄
    }


    // 플레이어 턴 처리
    private void playerTurn(String playerID) {
        Player player = getPlayerByID(playerID);
        if (player != null) {
            // 현재 턴인 플레이어 정보를 프론트엔드에 전달
            // WebSocketService.sendMessageToClient(playerID, "Your turn to place a family member.");

            // 플레이어의 가족 구성원 배치와 카드 실행 로직
            // 좌표와 카드 정보는 프론트엔드에서 제공
            // int x = ...; // 프론트엔드에서 받은 x 좌표
            // int y = ...; // 프론트엔드에서 받은 y 좌표
            // Card card = ...; // 프론트엔드에서 받은 카드 객체

            // player.placeFamilyMember(x, y, card);
        }
    }

    // 라운드 종료 확인
    private boolean checkIfRoundFinished() {
        for (Player player : players) {
            if (player.hasAvailableFamilyMembers()) {
                return false;
            }
        }
        return true;
    }

    // 수확 단계 메서드
    private void harvestPhase() {
        farmPhase();
        feedFamilyPhase();
        breedAnimalsPhase();
    }

    // 농장 단계
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

    // 가족 먹여살리기 단계
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

    // 필요한 음식 계산
    private int calculateFoodNeeded(Player player) {
        int foodNeeded = 0;
        for (FamilyMember[] row : player.getPlayerBoard().getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null) {
                    foodNeeded += member.isAdult() ? 2 : 1; // 성인은 2, 신생아는 1의 음식 필요
                }
            }
        }
        return foodNeeded;
    }

    // 가축 번식 단계
    private void breedAnimalsPhase() {
        for (Player player : players) {
            List<Animal> newAnimals = new ArrayList<>();
            Map<String, Integer> animalCounts = countAnimals(player);

            for (Map.Entry<String, Integer> entry : animalCounts.entrySet()) {
                if (entry.getValue() >= 2) {
                    newAnimals.add(new Animal(entry.getKey()));
                }
            }

            // 프론트엔드에서 새로 추가된 가축의 위치를 받아 처리하는 로직 필요
            // ...

            // 방생 처리 로직 필요
            // ...
        }
        notifyPlayers("가축 번식 단계 완료. 수확 단계 종료.");
    }

    // 가축 수 세기
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

    // 수확 라운드 확인
    private boolean isHarvestRound(int round) {
        return round == 4 || round == 7 || round == 9 || round == 11 || round == 13 || round == 14;
    }

    // 라운드 종료 메서드
    private void endRound() {
        for (Player player : players) {
            player.convertBabiesToAdults(); // 신생아를 성인으로 변환
        }
        calculateAndRecordScores();
    }

    // 가족 구성원 리셋
    private void resetFamilyMembers() {
        for (Player player : players) {
            player.resetFamilyMembers();
        }
    }

    // 점수 계산 및 기록
    private void calculateAndRecordScores() {
        for (Player player : players) {
            int score = calculateScoreForPlayer(player);
            player.setScore(score);
        }
        // WebSocketService.sendMessageToClient(gameID, "gameScores", getScores());
    }

    // 플레이어 점수 계산
    private int calculateScoreForPlayer(Player player) {
        return 0; // 예시 점수 계산
    }

    // 점수 리스트 가져오기
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

    // 게임 종료 메서드
    public void endGame() {
        roomController.handleGameEnd();
    }

    // 플레이어 및 카드 관리 메서드
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void executeCard(String playerID, String cardID) {
        Player player = getPlayerByID(playerID);
        Card card = getCardByID(cardID);
        card.execute(player);
    }

    // 플레이어 ID로 플레이어 찾기
    private Player getPlayerByID(String playerID) {
        for (Player player : players) {
            if (player.getId().equals(playerID)) {
                return player;
            }
        }
        return null;
    }

    // 카드 ID로 카드 찾기
    private Card getCardByID(String cardID) {
        // 카드 ID로부터 카드 검색 로직
        // 여기서는 예시로 간단히 null 반환
        return null;
    }

    // 플레이어에게 메시지 알림
    private void notifyPlayers(String message) {
        // 프론트엔드에 메시지를 보내는 로직 필요 (예: WebSocket 사용)
        // WebSocketService.sendMessageToAllClients(gameID, message);
    }
}
