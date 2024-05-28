package test.actionroundcardtest;

import cards.action.AccumulativeActionCard;
import cards.action.NonAccumulativeActionCard;
import cards.action.TestAccumulativeActionCard;
import cards.common.ActionRoundCard;
import cards.factory.imp.action.WanderingTheaterActionCard;
import cards.factory.imp.action.Bush;
import cards.factory.imp.action.ClayMine;
import controllers.GameController;
import controllers.RoomController;
import enums.RoomType;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
// 나머지 import 문은 동일

public class ResourceGainTest {
    private GameController gameController;
    private Player player;
    private Player player2;
    private ActionRoundCard actionCard;
    private ActionRoundCard nonAccumulativeCard;
    private ActionRoundCard wanderingTheaterCard;
    private ActionRoundCard woodGatheringCard;
    private ActionRoundCard clayPitCard;

    @BeforeEach
    public void setUp() {
        List<Player> players = createMockPlayers();
        RoomController roomController = new RoomController();
        roomController.handleGameStart("TestRoom123", players);
        gameController = roomController.getGameController();
        player = gameController.getPlayers().get(0);
        player2 = gameController.getPlayers().get(1);

        Map<String, Integer> accumulatedAmounts = new HashMap<>();
        accumulatedAmounts.put("food", 1);
        accumulatedAmounts.put("wood", 2);

        actionCard = new TestAccumulativeActionCard(1);
        nonAccumulativeCard = new NonAccumulativeActionCard(2, "Non-Accumulative Test Card", "This is a non-accumulative test card.");
        wanderingTheaterCard = new WanderingTheaterActionCard(3);
        woodGatheringCard = new Bush(4);
        clayPitCard = new ClayMine(5);

        // Initialize MainBoard
        MainBoard mainBoard = gameController.getMainBoard();
        mainBoard.setActionCards(new ArrayList<>());

        // Add cards to MainBoard
        mainBoard.getActionCards().add(actionCard);
        mainBoard.getActionCards().add(nonAccumulativeCard);
        mainBoard.getActionCards().add(wanderingTheaterCard);
        mainBoard.getActionCards().add(woodGatheringCard);
        mainBoard.getActionCards().add(clayPitCard);

        // Reassign cards to ensure we have the same references
        actionCard = mainBoard.getActionCards().stream().filter(card -> card.getId() == 1).findFirst().orElse(null);
        nonAccumulativeCard = mainBoard.getActionCards().stream().filter(card -> card.getId() == 2).findFirst().orElse(null);
        wanderingTheaterCard = mainBoard.getActionCards().stream().filter(card -> card.getId() == 3).findFirst().orElse(null);
        woodGatheringCard = mainBoard.getActionCards().stream().filter(card -> card.getId() == 4).findFirst().orElse(null);
        clayPitCard = mainBoard.getActionCards().stream().filter(card -> card.getId() == 5).findFirst().orElse(null);

    }

    private List<Player> createMockPlayers() {
        List<Player> players = new ArrayList<>();
        GameController mockGameController = new GameController("TestRoom123", new RoomController(), players);
        for (int i = 1; i <= 4; i++) {
            players.add(new Player("player" + i, "Player " + i, mockGameController));
        }
        return players;
    }

    private void printPlayerResources(Player player, String message) {
        System.out.println(message);
        for (Map.Entry<String, Integer> resource : player.getResources().entrySet()) {
            System.out.println("  " + resource.getKey() + ": " + resource.getValue());
        }
    }

    // 나머지 테스트 메서드와 함께 새로운 테스트 메서드 추가

    @Test
    public void testAccumulateResources() {
        player.resetResources();
        printPlayerResources(player,"Resources before testAccumulateResources:");

        // 첫 번째 누적
        System.out.println("첫 번째 누적");
        gameController.getMainBoard().accumulateResources();

        // 누적 자원 확인
        Map<String, Integer> accumulatedResources = ((AccumulativeActionCard) actionCard).getAccumulatedResources();
        assertEquals(1, accumulatedResources.get("food"), "Accumulated food should be 1.");
        assertEquals(2, accumulatedResources.get("wood"), "Accumulated wood should be 2.");

        // 두 번째 누적
        System.out.println("두 번째 누적");
        gameController.getMainBoard().accumulateResources();

        // 누적 자원 확인
        accumulatedResources = ((AccumulativeActionCard) actionCard).getAccumulatedResources();
        assertEquals(2, accumulatedResources.get("food"), "Accumulated food should be 2.");
        assertEquals(4, accumulatedResources.get("wood"), "Accumulated wood should be 4.");

        // 플레이어가 자원을 얻는지 확인
        System.out.println("is card occupied?" + player.getGameController().getMainBoard().isCardOccupied(actionCard));
        player.placeFamilyMember(actionCard);
        printPlayerResources(player,"두 번 누적 후 자원 획득 음식2, 나무 4");
        assertEquals(2, player.getResource("food"), "Player should have 2 food.");
        assertEquals(4, player.getResource("wood"), "Player should have 4 wood.");
        assertTrue(actionCard.isOccupied(), "Card should be occupied after executing action card.");

        // occupied 상태에서 자원 누적 테스트
        System.out.println("세 번째 누적 (점유 상태에서)");
        gameController.getMainBoard().accumulateResources();
        accumulatedResources = ((AccumulativeActionCard) actionCard).getAccumulatedResources();
        assertEquals(1, accumulatedResources.get("food"), "Accumulated food should be reset to 1 when card is occupied.");
        assertEquals(2, accumulatedResources.get("wood"), "Accumulated wood should be reset to 2 when card is occupied.");
        assertFalse(gameController.getMainBoard().isCardOccupied(actionCard));

        printPlayerResources(player,"Accumulated resources after card is occupied and reset:");

        // 누적 자원 초기화 후 플레이어가 나무 2, 음식 1을 얻음
        player2.resetResources();
        player2.placeFamilyMember(actionCard);
        printPlayerResources(player2,"플레이어2 총 나무 2, 음식 1");
        assertEquals(1, player2.getResource("food"), "Player should have 1 food.");
        assertEquals(2, player2.getResource("wood"), "Player should have 2 wood.");
        assertTrue(actionCard.isOccupied(), "Card should be occupied after executing action card by player2.");
    }

    @Test
    public void testGainResources() {
        printPlayerResources(player,"Resources before testGainResources:");

        Map<String, Integer> resources = new HashMap<>();
        resources.put("wood", 3);
        resources.put("clay", 2);
        actionCard.gainResources(player, resources);

        printPlayerResources(player,"Resources after testGainResources:");

        assertEquals(3, player.getResource("wood"), "Player should have 3 wood.");
        assertEquals(2, player.getResource("clay"), "Player should have 2 clay.");
    }






    @Test
    public void testNonAccumulativeCardGainResources() {
        printPlayerResources(player,"Resources before testNonAccumulativeCardGainResources:");

        Map<String, Integer> resources = new HashMap<>();
        resources.put("wood", 3);
        nonAccumulativeCard.gainResources(player, resources);

        printPlayerResources(player,"Resources after testNonAccumulativeCardGainResources:");

        assertEquals(3, player.getResource("wood"), "Player should have 3 wood from non-accumulative card.");
    }

    @Test
    public void testGainResourcesWithAnimals() {
        printPlayerResources(player,"Resources before testGainResourcesWithAnimals:");

        // 울타리 및 외양간 설정
        player.addResource("wood", 20);
        player.addResource("clay", 10);

        List<int[]> fencePositions = new ArrayList<>();
        fencePositions.add(new int[]{1, 1});
        fencePositions.add(new int[]{1, 2});
        fencePositions.add(new int[]{1, 3});

        player.getPlayerBoard().buildFences(fencePositions, player);

        // 외양간을 단독 타일에 배치
        player.getPlayerBoard().buildBarn(0, 2);

        // 초기 집 배치
        player.getPlayerBoard().getTiles()[1][0] = new Room(RoomType.WOOD, 1, 0);
        player.getPlayerBoard().getTiles()[2][0] = new Room(RoomType.WOOD, 2, 0);
        player.getPlayerBoard().getFamilyMembers()[1][0] = new FamilyMember(1, 0, true);
        player.getPlayerBoard().getFamilyMembers()[2][0] = new FamilyMember(2, 0, true);

        // 양 자원 추가
        Map<String, Integer> resources = new HashMap<>();
        resources.put("sheep", 4);

//        actionCard.gainResources(player, resources);

        // 양을 집, 울타리, 외양간에 각각 배치
        System.out.println("집에 배치");
        Animal sheep1 = new Animal(1, 0, "sheep");
        player.getPlayerBoard().addAnimalToBoard(sheep1, 1, 0);

        System.out.println("울타리에 배치");
        Animal sheep2 = new Animal(1, 1, "sheep");
        player.getPlayerBoard().addAnimalToBoard(sheep2, 1, 1);

        System.out.println("외양간에 배치");
        Animal sheep3 = new Animal(0, 2, "sheep");
        player.getPlayerBoard().addAnimalToBoard(sheep3, 0, 2);

        System.out.println("집에 배치");
        Animal sheep4 = new Animal(2, 0, "sheep");
        player.getPlayerBoard().addAnimalToBoard(sheep4, 2, 0);

        player.addResource("sheep", 4);
        // 수용 능력 확인
        int capacity = player.getPlayerBoard().getAnimalCapacity();
//        System.out.println("Animal capacity after gaining resources: " + capacity);

        // 검증
        assertEquals(8, capacity, "The capacity should be 8 after gaining resources and adding animals to the board.");
//        assertEquals(4, player.getResource("sheep"), "There should be 4 sheep resources.");
        printPlayerResources(player,"Resources after testGainResourcesWithAnimals:");
    }


    @Test
    public void testClayMineCard() {

        List<ActionRoundCard> actionRoundCards =  gameController.getMainBoard().getActionCards();
        for (ActionRoundCard actionRoundCard : actionRoundCards) {
            System.out.println("actionRoundCard.getName() = " + actionRoundCard.getName());
        }

        player.resetResources();
        printPlayerResources(player, "Resources before testClayPitCard:");

        // 첫 번째 누적
        System.out.println("첫 번째 누적");
        gameController.getMainBoard().accumulateResources();

        // 누적 자원 확인
        Map<String, Integer> accumulatedResources = ((AccumulativeActionCard) clayPitCard).getAccumulatedResources();
        assertEquals(2, accumulatedResources.get("clay"), "Accumulated clay should be 3.");

        // 두 번째 누적
        System.out.println("두 번째 누적");
        gameController.getMainBoard().accumulateResources();

        // 누적 자원 확인
        accumulatedResources = ((AccumulativeActionCard) clayPitCard).getAccumulatedResources();
        assertEquals(4, accumulatedResources.get("clay"), "Accumulated clay should be 6.");

        // 동일한 객체인지 확인
//        ActionRoundCard clayPitCardFromBoard = gameController.getMainBoard().getActionCards().stream()
//                .filter(card -> "점토 채굴장".equals(card.getName()))
//                .findFirst()
//                .orElse(null);
//        assertSame(clayPitCard, clayPitCardFromBoard, "clayPitCard should be the same object as the one in the MainBoard.");


        // 플레이어가 자원을 얻는지 확인
        System.out.println("is card occupied? " + player.getGameController().getMainBoard().isCardOccupied(clayPitCard));
        player.placeFamilyMember(clayPitCard);
        printPlayerResources(player, "두 번 누적 후 자원 획득 흙 6");

       actionRoundCards =  gameController.getMainBoard().getActionCards();
       for (ActionRoundCard actionRoundCard : actionRoundCards) {
           System.out.println("actionRoundCard.getName() = " + actionRoundCard.getName());
       }

        assertEquals(4, player.getResource("clay"), "Player should have 6 clay.");
        assertTrue(clayPitCard.isOccupied(), "Card should be occupied after executing action card.");

        // occupied 상태에서 자원 누적 테스트
        System.out.println("세 번째 누적 (점유 상태에서)");
        gameController.getMainBoard().accumulateResources();
        accumulatedResources = ((AccumulativeActionCard) clayPitCard).getAccumulatedResources();
        assertEquals(2, accumulatedResources.get("clay"), "Accumulated clay should be reset to 3 when card is occupied.");
        assertFalse(gameController.getMainBoard().isCardOccupied(clayPitCard));

        printPlayerResources(player, "Accumulated resources after card is occupied and reset:");
    }


}
