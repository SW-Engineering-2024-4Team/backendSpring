//import cards.action.AccumulativeActionCard;
//import cards.common.ActionRoundCard;
//import controllers.GameController;
//import controllers.RoomController;
//import models.Player;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class PlayerTest {
//    private GameController gameController;
//    private Player player;
//    private ActionRoundCard actionCard;
//
//    @BeforeEach
//    public void setUp() {
//        List<Player> players = createMockPlayers();
//        RoomController roomController = new RoomController();
//        roomController.handleGameStart("TestRoom123", players);
//        gameController = roomController.getGameController();
//        player = gameController.getPlayers().get(0);
//
//        actionCard = new AccumulativeActionCard(1, "Test Card", "This is a test card.");
//        gameController.getMainBoard().getActionCards().add(actionCard); // 액션 카드를 메인보드에 추가
//    }
//
//    private List<Player> createMockPlayers() {
//        List<Player> players = new ArrayList<>();
//        GameController mockGameController = new GameController("TestRoom123", new RoomController(), players);
//        for (int i = 1; i <= 4; i++) {
//            players.add(new Player("player" + i, "Player " + i, mockGameController));
//        }
//        return players;
//    }
//
//    @Test
//    public void testPlaceFamilyMemberOnCard() {
//        player.placeFamilyMember(actionCard);
//
//        // 카드가 점유되었는지 확인
//        assertTrue(gameController.getMainBoard().isCardOccupied(actionCard), "Card should be occupied after placing a family member.");
//
//        // 카드에 다른 가족 구성원을 다시 배치하려고 시도
//        player.placeFamilyMember(actionCard);
//
//        // 카드가 여전히 점유된 상태인지 확인
//        assertTrue(gameController.getMainBoard().isCardOccupied(actionCard), "Card should still be occupied after attempting to place another family member.");
//    }
//
//    @Test
//    public void testPlaceFamilyMemberOnOccupiedCard() {
//        // 첫 번째 가족 구성원을 배치
//        player.placeFamilyMember(actionCard);
//        assertTrue(gameController.getMainBoard().isCardOccupied(actionCard), "Card should be occupied after placing a family member.");
//
//        // 두 번째 가족 구성원을 동일한 카드에 배치하려고 시도 (실패해야 함)
//        player.placeFamilyMember(actionCard);
//        assertTrue(gameController.getMainBoard().isCardOccupied(actionCard), "Card should remain occupied after attempting to place another family member on it.");
//    }
//}
