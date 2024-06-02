package com.example.agricola.domain;

import java.util.*;

public class Player {
    private final ArrayList<CommonCard> majorImprovementCards;
    private String id;
    private String name;
    private Map<String, Integer> resources;
    private List<CommonCard> occupationCards;
    private List<CommonCard> minorImprovementCards;
    private List<CommonCard> activeCards;
    private PlayerBoard playerBoard;
    private int score;
    private boolean isFirstPlayer;
    private GameController gameController;
    private List<FamilyMember> newFamilyMembers; // 새로 추가된 가족 구성원 리스트
    private List<Animal> newAnimals; // 새로 추가된 동물 리스트
    private boolean firstFenceBuilt = false; // 최초 울타리 여부를 저장하는 변수
    private List<int[]> fenceCoordinates = new ArrayList<>(); // 울타리 설치할 좌표 리스트
    private FamilyMember[][] familyMembers;
    private List<Effect> activeEffects;
    private boolean chooseOption;

    public Player(String id, String name, GameController gameController) {
        this.id = id;
        this.name = name;
        this.resources = new HashMap<>();
        this.occupationCards = new ArrayList<>();
        this.minorImprovementCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
        this.playerBoard = new PlayerBoard();
        this.isFirstPlayer = false;
        this.gameController = gameController;
        this.newFamilyMembers = new ArrayList<>();
        this.newAnimals = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
        initializeResources();
    }

    private void initializeResources() {
        resources.put("wood", 0);
        resources.put("clay", 0);
        resources.put("stone", 0);
        resources.put("grain", 0);
        resources.put("food", 0);
        resources.put("beggingCard", 0);
        resources.put("sheep", 0);
    }

    public List<Effect> getActiveEffects() {
        return activeEffects;
    }

    public void addActiveEffect(Effect effect) {
        activeEffects.add(effect);
    }

    public void addCard(CommonCard card, String type) {
        if (type.equals("occupation")) {
            occupationCards.add(card);
        } else if (type.equals("minorImprovement")) {
            minorImprovementCards.add(card);
        } else {
            activeCards.add(card);
        }
    }

    public List<CommonCard> getOccupationCards() {
        return occupationCards;
    }

    public List<CommonCard> getMinorImprovementCards() {
        return minorImprovementCards;
    }

    public List<CommonCard> getActiveCards() {
        return activeCards;
    }

    public List<CommonCard> getMajorImprovementCards() {
        return majorImprovementCards;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


//    public void placeFamilyMember(ActionRoundCard card) {
//        // 카드에 올려놓을 수 있는지(점유가 됐는지 확인)
//        if (!gameController.getMainBoard().canPlaceFamilyMember(card)) {
//            System.out.println("Card " + card.getName() + " is already occupied.");
//            return;
//        }
//
//        // 이 부분이 카드 효과를 발동
//        // TODO: 프론트가 가족 구성원의 좌표, 카드명주면
//        // 가족 구성원 사용됨, 카드 점유, 카드 실행
//
//        FamilyMember[][] familyMembers = playerBoard.getFamilyMembers();
//        for (int i = 0; i < familyMembers.length; i++) {
//            for (int j = 0; j < familyMembers[i].length; j++) {
//                if (familyMembers[i][j] != null && familyMembers[i][j].isAdult() && !familyMembers[i][j].isUsed()) {
//                    FamilyMember selectedMember = familyMembers[i][j];
//                    System.out.println("Placing family member at (" + i + ", " + j + ") for player " + this.id);
//
//                    // 카드가 사용되면 점유상태가 되어 있어야 함.
//                    gameController.getMainBoard().placeFamilyMember(card); // 점유 상태로 먼저 설정
//
//                    // 카드 효과를 발동
//                    card.execute(this); // 카드 실행 로직
//
//                    // 가족 구성원이 사용되었음 표시
//                    selectedMember.setUsed(true); //hasFamilyMember()
//
//                    System.out.println("Player " + this.id + " placed a family member on card: " + card.getName());
//                    System.out.println("Family member used status: " + selectedMember.isUsed());
//                    return;
//                }
//            }
//        }
//        System.out.println("No available family member found for player " + this.id);
//    }

    public void placeFamilyMember(ActionRoundCard card) {
        MainBoard mainBoard = gameController.getMainBoard();

        // 최신 카드 리스트에서 해당 카드를 찾음
        ActionRoundCard latestCard = null;
        for (ActionRoundCard actionCard : mainBoard.getActionCards()) {
            if (actionCard.getId() == card.getId()) {
                latestCard = actionCard;
                break;
            }
        }
        if (latestCard == null) {
            for (ActionRoundCard roundCard : mainBoard.getRoundCards()) {
                if (roundCard.getId() == card.getId()) {
                    latestCard = roundCard;
                    break;
                }
            }
        }

        if (latestCard == null) {
            System.out.println("Card not found on the main board.");
            return;
        }

        if (!mainBoard.canPlaceFamilyMember(latestCard)) {
            System.out.println("Card " + latestCard.getName() + " is already occupied.");
            return;
        }

        FamilyMember[][] familyMembers = playerBoard.getFamilyMembers();
        for (int i = 0; i < familyMembers.length; i++) {
            for (int j = 0; j < familyMembers[i].length; j++) {
                if (familyMembers[i][j] != null && familyMembers[i][j].isAdult() && !familyMembers[i][j].isUsed()) {
                    FamilyMember selectedMember = familyMembers[i][j];
                    System.out.println("Placing family member at (" + i + ", " + j + ") for player " + this.id);

                    mainBoard.placeFamilyMember(latestCard); // 점유 상태로 먼저 설정
                    latestCard.execute(this); // 카드 실행 로직
                    selectedMember.setUsed(true);

                    System.out.println("Player " + this.id + " placed a family member on card: " + latestCard.getName());
                    System.out.println("Family member used status: " + selectedMember.isUsed());
                    return;
                }
            }
        }
        System.out.println("No available family member found for player " + this.id);
    }


    public void resetFamilyMembers() {
        FamilyMember[][] familyMembers = playerBoard.getFamilyMembers();
        for (FamilyMember[] row : familyMembers) {
            for (FamilyMember familyMember : row) {
                if (familyMember != null) {
                    familyMember.setUsed(false);
                    familyMember.resetPosition();
                    System.out.println("Reset family member at (" + familyMember.getOriginalX() + ", " + familyMember.getOriginalY() + ") for player " + this.id);
                }
            }
        }
    }

    public boolean hasAvailableFamilyMembers() {
        for (FamilyMember[] row : playerBoard.getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null && member.isAdult() && !member.isUsed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addResource(String resource, int amount) {
        resources.put(resource, resources.getOrDefault(resource, 0) + amount);
    }

    public int getResource(String resource) {
        return resources.getOrDefault(resource, 0);
    }

    public Map<String, Integer> getResources() {
        return resources;
    }

    public void convertBabiesToAdults() {
        for (FamilyMember[] row : playerBoard.getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null && !member.isAdult()) {
                    member.setAdult(true);
                }
            }
        }
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean isFirstPlayer) {
        this.isFirstPlayer = isFirstPlayer;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void moveToActiveCards(CommonCard card) {
        if (card instanceof MajorImprovementCard) {
            majorImprovementCards.remove(card);
        } else if (card instanceof UnifiedCard) {
            occupationCards.remove(card);
            minorImprovementCards.remove(card); // UnifiedCard는 occupationCards와 minorImprovementCards 둘 다에 있을 수 있습니다.
        }
        activeCards.add(card);
    }

    public List<ExchangeableCard> getExchangeableCards(ExchangeTiming timing) {
        List<ExchangeableCard> exchangeableCards = new ArrayList<>();
        for (CommonCard card : majorImprovementCards) {
            if (card instanceof ExchangeableCard) {
                ExchangeableCard exchangeableCard = (ExchangeableCard) card;
                if (exchangeableCard.canExchange(timing)) {
                    exchangeableCards.add(exchangeableCard);
                }
            }
        }
        return exchangeableCards;
    }

    public void executeExchange(ExchangeableCard card, String fromResource, String toResource, int amount) {
        card.executeExchange(this, fromResource, toResource, amount);
    }

    public void useBakingCard(BakingCard card) {
        card.triggerBreadBaking(this);
    }

    public void addMajorImprovementCard(CommonCard card) {
        majorImprovementCards.add(card);
    }

    public void removeMajorImprovementCard(CommonCard card) {
        majorImprovementCards.remove(card);
    }

    public boolean checkResources(Map<String, Integer> cost) {
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            if (resources.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void payResources(Map<String, Integer> cost) {
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            addResource(entry.getKey(), -entry.getValue());
        }
    }

    public void useUnifiedCard(CommonCard card) {
        if (card != null) {
            card.execute(this);
            moveToActiveCards(card);
        } else {
            System.out.println("UnifiedCard is null.");
        }
    }

    public BakingCard selectBakingCard(List<BakingCard> bakingCards) {
        Random random = new Random();
        return bakingCards.get(random.nextInt(bakingCards.size()));
    }

    public int selectGrainForBaking(int maxAmount) {
        Random random = new Random();
        return random.nextInt(maxAmount + 1);
    }

    public void buildHouse(int x, int y, RoomType type) {
        if (playerBoard.canBuildHouse(x, y, type, resources)) {
            Map<String, Integer> cost = getHouseResourceCost(type);
            if (checkResources(cost)) {
                payResources(cost);
                playerBoard.buildHouse(x, y, type);
            } else {
                System.out.println("자원이 부족합니다.");
            }
        } else {
            System.out.println("집을 지을 수 없습니다.");
        }
    }

    public Map<String, Integer> getHouseResourceCost(RoomType type) {
        Map<String, Integer> cost = new HashMap<>();
        switch (type) {
            case WOOD:
                cost.put("wood", 5);
                break;
            case CLAY:
                cost.put("clay", 5);
                break;
            case STONE:
                cost.put("stone", 5);
                break;
        }
        return cost;
    }

    public boolean addFamilyMember() {
        List<int[]> emptyRooms = playerBoard.getEmptyRoomPositions();
        if (!emptyRooms.isEmpty()) {
            FamilyMember newMember = new FamilyMember(-1, -1, false);
            newFamilyMembers.add(newMember);
            System.out.println("새로운 가족 구성원이 추가되었습니다. 빈 방에 배치하세요.");
            return true;
        } else {
            System.out.println("빈 방이 없습니다.");
            return false;
        }
    }

    public void placeFamilyMemberInRoom(FamilyMember familyMember, int x, int y) {
        if (playerBoard.isEmptyRoom(x, y)) {
            playerBoard.addFamilyMemberToBoard(familyMember, x, y);
            newFamilyMembers.remove(familyMember);
            System.out.println("가족 구성원이 방에 배치되었습니다: (" + x + ", " + y + ")");
        } else {
            System.out.println("해당 방은 이미 사용 중입니다.");
        }
    }

    public FamilyMember getNewFamilyMember() {
        if (!newFamilyMembers.isEmpty()) {
            return newFamilyMembers.get(0);
        }
        return null;
    }

    public boolean addAnimal(Animal animal) {
        newAnimals.add(animal);
        System.out.println(animal.getType() + " 새끼 동물이 추가되었습니다. 울타리나 방에 배치하세요.");
        return true;
    }

    public boolean addNewAnimal(Animal animal) {
        newAnimals.add(animal);
        System.out.println(animal.getType() + " 새끼 동물이 추가되었습니다. 빈 공간에 배치하세요.");
        return true;
    }

    public void placeAnimalOnBoard(Animal animal, int x, int y) {
        if (playerBoard.canPlaceAnimal(x, y, animal.getType())) {
            playerBoard.addAnimalToBoard(animal, x, y);
            newAnimals.remove(animal);
        } else {
            System.out.println("해당 위치에는 동물을 배치할 수 없습니다.");
        }
    }

    public int placeNewAnimals() {
        int placedCount = 0;
        List<Animal> animalsToRemove = new ArrayList<>();
        Iterator<Animal> iterator = newAnimals.iterator();

        // TODO 임의로 좌표를 설정한 것
        // 더 배치할 수 없을 경우: 방생
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            Set<int[]> validPositions = playerBoard.getValidAnimalPositions(animal.getType());

            if (!validPositions.isEmpty()) {
                int[] position = validPositions.iterator().next();
                System.out.println("동물 배치 위치: (" + position[0] + ", " + position[1] + ")");

                // TODO 프론트한테 좌표 받아서 배치
                placeAnimalOnBoard(animal, position[0], position[1]);
                placedCount++;
                animalsToRemove.add(animal);
            } else {
                System.out.println(animal.getType() + " 방생됨.");
                animalsToRemove.add(animal);
            }
        }

        newAnimals.removeAll(animalsToRemove);
        return placedCount;
    }

    public void buildBarn(int x, int y) {
        if (playerBoard.canBuildBarn(x, y)) {
            Map<String, Integer> cost = Map.of("wood", 2);
            if (checkResources(cost)) {
                payResources(cost);
                playerBoard.buildBarn(x, y);
            } else {
                System.out.println("자원이 부족합니다.");
            }
        } else {
            System.out.println("외양간을 지을 수 없습니다.");
        }
    }

    public int calculateTotalAnimalCapacity() {
        return playerBoard.getAnimalCapacity();
    }

    public void renovateHouse(RoomType newType) {
        playerBoard.renovateRooms(newType, this);
    }

    public RoomType chooseRoomTypeForRenovation() {
        List<RoomType> upgradeOptions = new ArrayList<>();
        if (hasWoodRooms()) {
            upgradeOptions.add(RoomType.CLAY);
        }
        if (hasClayRooms()) {
            upgradeOptions.add(RoomType.STONE);
        }
        return upgradeOptions.isEmpty() ? null : upgradeOptions.get(0);
    }

    private boolean hasWoodRooms() {
        for (Tile[] row : playerBoard.getTiles()) {
            for (Tile tile : row) {
                if (tile instanceof Room && ((Room) tile).getType() == RoomType.WOOD) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasClayRooms() {
        for (Tile[] row : playerBoard.getTiles()) {
            for (Tile tile : row) {
                if (tile instanceof Room && ((Room) tile).getType() == RoomType.CLAY) {
                    return true;
                }
            }
        }
        return false;
    }

    public void selectFenceTile(int x, int y) {
        List<int[]> selectedPositions = new ArrayList<>();
        selectedPositions.add(new int[]{x, y});
        int requiredWood = playerBoard.calculateRequiredWoodForFences(selectedPositions);
        System.out.println("Selected position: (" + x + ", " + y + "), Wood needed: " + requiredWood);
        Map<String, Integer> cost = new HashMap<>();
        cost.put("wood", requiredWood);
        if (checkResources(cost)) {
            payResources(cost);
            playerBoard.buildFences(selectedPositions, this);
            System.out.println("Fence built at: (" + x + ", " + y + ")");
        } else {
            System.out.println("Not enough resources to build fence at: (" + x + ", " + y + ")");
        }
    }

    public boolean canContinueFenceBuilding() {
        return new Random().nextBoolean();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void resetResources() {
        for (String resource : resources.keySet()) {
            resources.put(resource, 0);
        }
    }

    public String getName() {
        return name;
    }

    public void printPlayerResources(String message) {
        System.out.println(message);
        for (Map.Entry<String, Integer> resource : getResources().entrySet()) {
            System.out.println("  " + resource.getKey() + ": " + resource.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean chooseOption() {
        return chooseOption;
    }

    public void setChooseOption(boolean chooseOption) {
        this.chooseOption = chooseOption;
    }

    private boolean woodDiscountActive = false;
    public boolean isWoodDiscountActive() {
        return woodDiscountActive;
    }

    public void setWoodDiscountActive(boolean woodDiscountActive) {
        this.woodDiscountActive = woodDiscountActive;
    }

    public Map<String, Integer> getDiscountedCost(Map<String, Integer> originalCost) {
        if (!woodDiscountActive) return originalCost;

        Map<String, Integer> discountedCost = new HashMap<>(originalCost);
        if (discountedCost.containsKey("wood")) {
            discountedCost.put("wood", Math.max(0, discountedCost.get("wood") - 1));
        }
        if (discountedCost.containsKey("stone")) {
            discountedCost.put("stone", Math.max(0, discountedCost.get("stone") - stoneDiscount));
        }
        return discountedCost;
    }

    private int stoneDiscount = 0;

    public void setStoneDiscount(int discount) {
        this.stoneDiscount = discount;
    }

}

