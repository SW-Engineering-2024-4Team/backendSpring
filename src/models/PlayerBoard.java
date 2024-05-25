package models;

import enums.RoomType;

import java.util.*;

public class PlayerBoard {
    private Tile[][] tiles;
//    private boolean[][] fences;
    private boolean[][][] fences; // 3차원 배열로 변경
    private FamilyMember[][] familyMembers;
    private Animal[][] animals;
    private List<FenceArea> fenceAreas;
    private Set<FenceArea> managedFenceAreas; // 추가된 컬렉션

    public PlayerBoard() {
        this.tiles = new Tile[3][5];
        this.fences = new boolean[3][5][4];
        this.familyMembers = new FamilyMember[3][5];
        this.animals = new Animal[3][5];
        this.fenceAreas = new ArrayList<>();
        this.managedFenceAreas = new HashSet<>(); // 초기화
        initializeBoard();
    }

    private void initializeBoard() {
        tiles[1][0] = new Room(RoomType.WOOD, 1, 0);
        tiles[2][0] = new Room(RoomType.WOOD, 2, 0);
        familyMembers[1][0] = new FamilyMember(1, 0, true);
        familyMembers[2][0] = new FamilyMember(2, 0, true);
    }

    public boolean canBuildHouse(int x, int y, RoomType type, Map<String, Integer> resources) {
        // 1. 타일이 비어 있는지 확인
        if (tiles[x][y] != null) {
            return false;
        }

        // 2. 위치가 기존의 집과 이웃해 있는지 확인
        boolean adjacentToExistingHouse = false;
        if (x > 0 && tiles[x - 1][y] instanceof Room) adjacentToExistingHouse = true;
        if (x < tiles.length - 1 && tiles[x + 1][y] instanceof Room) adjacentToExistingHouse = true;
        if (y > 0 && tiles[x][y - 1] instanceof Room) adjacentToExistingHouse = true;
        if (y < tiles[0].length - 1 && tiles[x][y + 1] instanceof Room) adjacentToExistingHouse = true;
        if (!adjacentToExistingHouse) {
            return false;
        }

        // 3. 같은 타입의 집을 지으려는지 확인
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room && ((Room) tile).getType() != type) {
                    return false;
                }
            }
        }

        // 4. 자원이 충분한지 확인
        return true;
    }

    public void buildHouse(int x, int y, RoomType type) {
        if (tiles[x][y] == null) {
            tiles[x][y] = new Room(type, x, y);
            updateFenceAreas();
        }
    }

    public Set<int[]> getValidHousePositions() {
        Set<int[]> validPositions = new HashSet<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null && isAdjacentToExistingHouse(i, j)) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
        return validPositions;
    }

    private boolean isAdjacentToExistingHouse(int x, int y) {
        if (x > 0 && tiles[x - 1][y] instanceof Room) return true;
        if (x < tiles.length - 1 && tiles[x + 1][y] instanceof Room) return true;
        if (y > 0 && tiles[x][y - 1] instanceof Room) return true;
        if (y < tiles[0].length - 1 && tiles[x][y + 1] instanceof Room) return true;
        return false;
    }

    // 기존에 지어진 집의 타입을 반환하는 메서드
    public RoomType getExistingRoomType() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room) {
                    return ((Room) tile).getType();
                }
            }
        }
        return null; // 집이 없으면 null 반환
    }

    public boolean canBuildBarn(int x, int y) {
        if (tiles[x][y] != null) {
            return false; // 타일이 비어있지 않으면 외양간을 지을 수 없음
        }
        return true;
    }

//    public void buildBarn(int x, int y) {
//        if (tiles[x][y] == null) {
//            tiles[x][y] = new Barn(x, y);
//        }
//    }
    public void buildBarn(int x, int y) {
        if (tiles[x][y] == null) {
            tiles[x][y] = new Barn(x, y);
            updateFenceAreas(); // 울타리 영역 업데이트
        } else if (isFenceArea(x, y)) {
            tiles[x][y] = new Barn(x, y);
            addBarnToFenceArea(x, y);
        }
    }

    private void addBarnToFenceArea(int x, int y) {
        for (FenceArea area : fenceAreas) {
            if (area.containsTile(x, y)) {
                area.addTile(x, y, tiles[x][y]);
            }
        }
    }

    // 유효한 밭 위치를 반환하는 메서드
    public Set<int[]> getValidPlowPositions() {
        Set<int[]> validPositions = new HashSet<>();
        boolean hasExistingField = hasExistingField();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null && (!hasExistingField || isAdjacentToSameTypeTile(i, j, FieldTile.class))) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
        return validPositions;
    }

    // 유효한 외양간 위치를 반환하는 메서드
    public Set<int[]> getValidBarnPositions() {
        Set<int[]> validPositions = new HashSet<>();
        boolean hasExistingBarn = hasExistingBarn();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null && (!hasExistingBarn || isAdjacentToSameTypeTile(i, j, Barn.class))) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
        return validPositions;
    }

    // 기물이 인접한지 확인하는 메서드
    private boolean isAdjacentToSameTypeTile(int x, int y, Class<?> type) {
        if (x > 0 && type.isInstance(tiles[x - 1][y])) return true;
        if (x < tiles.length - 1 && type.isInstance(tiles[x + 1][y])) return true;
        if (y > 0 && type.isInstance(tiles[x][y - 1])) return true;
        if (y < tiles[0].length - 1 && type.isInstance(tiles[x][y + 1])) return true;
        return false;
    }

    // 기존 기물이 있는지 확인하는 메서드들
    private boolean hasExistingField() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof FieldTile) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasExistingBarn() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Barn) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasExistingHouse() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room) {
                    return true;
                }
            }
        }
        return false;
    }
//    public void buildFences(List<int[]> coordinates, Player player) {
//        if (coordinates == null || coordinates.isEmpty()) {
//            System.out.println("No coordinates provided.");
//            return;
//        }
//
//        int woodRequired = calculateRequiredWoodForFences(coordinates);
//        if (player.getResource("wood") < woodRequired) {
//            System.out.println("Not enough wood to build fences.");
//            return;
//        }
//
//        if (!validateCoordinates(coordinates)) {
//            System.out.println("Invalid coordinates provided.");
//            return;
//        }
//
//        // 울타리 건설
//        for (int[] coord : coordinates) {
//            int x = coord[0];
//            int y = coord[1];
//
//            // 각 좌표에 대한 상하좌우 울타리를 업데이트
//            updateFence(x, y, coordinates);
//        }
//
//        // 인접한 타일들 업데이트
//        for (int[] coord : coordinates) {
//            int x = coord[0];
//            int y = coord[1];
//
//            // 인접한 타일의 울타리 정보 업데이트
//            updateAdjacentFences(x, y, coordinates);
//        }
//
//        // 자원 차감
//        player.addResource("wood", -woodRequired);
//
//        // 울타리 영역 업데이트
//        updateFenceAreas();
//    }

    public void buildFences(List<int[]> coordinates, Player player) {
        if (coordinates == null || coordinates.isEmpty()) {
            System.out.println("No coordinates provided.");
            return;
        }

        int woodRequired = calculateRequiredWoodForFences(coordinates);
        if (player.getResource("wood") < woodRequired) {
            System.out.println("Not enough wood to build fences.");
            return;
        }

        if (!validateCoordinates(coordinates)) {
            System.out.println("Invalid coordinates provided.");
            return;
        }

        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];

            // 울타리가 외양간이 있는 타일에 지어질 경우
            if (tiles[x][y] instanceof Barn) {
                Barn barn = (Barn) tiles[x][y];
                if (barn.hasAnimal()) {
                    // 외양간에 동물이 있는 경우, 울타리 영역에 동물을 추가
                    Animal animal = barn.getAnimal();
                    addAnimalToFenceArea(animal, x, y);
                    barn.setAnimal(null); // 외양간에서 동물 제거
                }
            }

            updateFence(x, y, coordinates);
        }

        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];

            updateAdjacentFences(x, y, coordinates);
        }

        player.addResource("wood", -woodRequired);
        mergeFenceAreas(coordinates); // 새로운 메서드 호출
    }

    // 새로운 울타리 영역을 생성하거나 기존 영역에 병합하는 메서드
    private void mergeFenceAreas(List<int[]> coordinates) {
        Set<FenceArea> affectedAreas = new HashSet<>();
        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];
            for (FenceArea area : fenceAreas) {
                if (area.containsTile(x, y)) {
                    affectedAreas.add(area);
                }
            }
        }

        FenceArea newArea = new FenceArea();
        for (int[] coord : coordinates) {
            newArea.addTile(coord[0], coord[1], tiles[coord[0]][coord[1]]);
        }

        for (FenceArea area : affectedAreas) {
            for (int[] tile : area.getTiles()) {
                newArea.addTile(tile[0], tile[1], tiles[tile[0]][tile[1]]);
            }
            fenceAreas.remove(area);
        }

        fenceAreas.add(newArea);
        managedFenceAreas.add(newArea); // 새로운 컬렉션에 추가
    }



    private boolean validateCoordinates(List<int[]> coordinates) {
        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];
            if (!isValidFencePosition(x, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFencePosition(int x, int y) {
        return (tiles[x][y] == null || tiles[x][y] instanceof Barn) && (x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length);
    }


    private void updateFence(int x, int y, List<int[]> coordinates) {
        boolean isLeftEdge = coordinates.stream().noneMatch(coord -> coord[0] == x && coord[1] == y - 1);
        boolean isRightEdge = coordinates.stream().noneMatch(coord -> coord[0] == x && coord[1] == y + 1);
        boolean isTopEdge = coordinates.stream().noneMatch(coord -> coord[0] == x - 1 && coord[1] == y);
        boolean isBottomEdge = coordinates.stream().noneMatch(coord -> coord[0] == x + 1 && coord[1] == y);

        // 상단 울타리
        if (!fences[x][y][0] && isTopEdge) {
            fences[x][y][0] = true;
        }
        // 하단 울타리
        if (!fences[x][y][1] && isBottomEdge) {
            fences[x][y][1] = true;
        }
        // 좌측 울타리
        if (!fences[x][y][2] && isLeftEdge) {
            fences[x][y][2] = true;
        }
        // 우측 울타리
        if (!fences[x][y][3] && isRightEdge) {
            fences[x][y][3] = true;
        }
    }

    private void updateAdjacentFences(int x, int y, List<int[]> coordinates) {
        // 상단 인접 타일의 하단 울타리
        if (x > 0 && fences[x][y][0]) {
            fences[x - 1][y][1] = true;
        }
        // 하단 인접 타일의 상단 울타리
        if (x < tiles.length - 1 && fences[x][y][1]) {
            fences[x + 1][y][0] = true;
        }
        // 좌측 인접 타일의 우측 울타리
        if (y > 0 && fences[x][y][2]) {
            fences[x][y - 1][3] = true;
        }
        // 우측 인접 타일의 좌측 울타리
        if (y < tiles[0].length - 1 && fences[x][y][3]) {
            fences[x][y + 1][2] = true;
        }
    }

    public int calculateRequiredWoodForFences(List<int[]> coordinates) {
        Set<String> uniqueFences = new HashSet<>();

        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];

            // 상단 울타리
            if (fences[x][y][0] == false && (x == 0 || coordinates.stream().noneMatch(c -> c[0] == x - 1 && c[1] == y))) {
                uniqueFences.add(x + "," + y + ",0");
            }
            // 하단 울타리
            if (fences[x][y][1] == false && (x == tiles.length - 1 || coordinates.stream().noneMatch(c -> c[0] == x + 1 && c[1] == y))) {
                uniqueFences.add(x + "," + y + ",1");
            }
            // 좌측 울타리
            if (fences[x][y][2] == false && (y == 0 || coordinates.stream().noneMatch(c -> c[0] == x && c[1] == y - 1))) {
                uniqueFences.add(x + "," + y + ",2");
            }
            // 우측 울타리
            if (fences[x][y][3] == false && (y == tiles[0].length - 1 || coordinates.stream().noneMatch(c -> c[0] == x && c[1] == y + 1))) {
                uniqueFences.add(x + "," + y + ",3");
            }
        }

        return uniqueFences.size();
    }


//
//    // 울타리 영역을 업데이트하는 메서드
//    private void updateFenceAreas() {
//        fenceAreas.clear();
//        boolean[][] visited = new boolean[fences.length][fences[0].length];
//        for (int i = 0; i < fences.length; i++) {
//            for (int j = 0; j < fences[i].length; j++) {
//                for (int k = 0; k < 4; k++) {
//                    if (fences[i][j][k] && !visited[i][j]) {
//                        FenceArea area = new FenceArea();
//                        exploreFenceArea(i, j, k, visited, area);
//                        fenceAreas.add(area);
//                    }
//                }
//            }
//        }
//    }
//
//    private void exploreFenceArea(int x, int y, int direction, boolean[][] visited, FenceArea area) {
//        Queue<int[]> queue = new LinkedList<>();
//        queue.add(new int[]{x, y, direction});
//        visited[x][y] = true;
//        while (!queue.isEmpty()) {
//            int[] pos = queue.poll();
//            int px = pos[0];
//            int py = pos[1];
//            area.addTile(px, py, tiles[px][py]);
//            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
//            for (int[] dir : directions) {
//                int nx = px + dir[0];
//                int ny = py + dir[1];
//                if (nx >= 0 && ny >= 0 && nx < tiles.length && ny < tiles[0].length && !visited[nx][ny]) {
//                    if ((dir[0] == -1 && fences[px - 1][py][0]) ||
//                            (dir[0] == 1 && fences[px + 1][py][0]) ||
//                            (dir[1] == -1 && fences[px][py - 1][2]) ||
//                            (dir[1] == 1 && fences[px][py + 1][2])) {
//                        queue.add(new int[]{nx, ny});
//                        visited[nx][ny] = true;
//                    }
//                }
//            }
//        }
//    }

    private void updateFenceAreas() {
        fenceAreas.clear();
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (!visited[i][j] && isFenceArea(i, j)) {
                    FenceArea area = new FenceArea();
                    exploreFenceArea(i, j, visited, area);
                    fenceAreas.add(area);
                    managedFenceAreas.add(area); // 새로운 컬렉션에 추가
                }
            }
        }
    }

    private void exploreFenceArea(int x, int y, boolean[][] visited, FenceArea area) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int px = pos[0];
            int py = pos[1];
            area.addTile(px, py, tiles[px][py]);
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int nx = px + dir[0];
                int ny = py + dir[1];
                if (nx >= 0 && ny >= 0 && nx < tiles.length && ny < tiles[0].length && !visited[nx][ny] && isFenceArea(nx, ny)) {
                    queue.add(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
    }









    //    public Set<int[]> getValidFencePositions() {
//        Set<int[]> validPositions = new HashSet<>();
//        if (noFencesBuilt()) {
//            // 처음 울타리를 지을 때: 빈 타일이 유효한 위치
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles[0].length; j++) {
//                    if (tiles[i][j] == null || tiles[i][j] instanceof Barn) {
//                        validPositions.add(new int[]{i, j});
//                    }
//                }
//            }
//        } else {
//            // 이후 울타리를 지을 때: 기존 울타리와 인접한 빈 타일이 유효한 위치
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles[0].length; j++) {
//                    if (tiles[i][j] == null || tiles[i][j] instanceof Barn) {
//                        if ((i > 0 && fences[i - 1][j][0]) ||
//                                (i < tiles.length - 1 && fences[i + 1][j][0]) ||
//                                (j > 0 && fences[i][j - 1][2]) ||
//                                (j < tiles[0].length - 1 && fences[i][j + 1][2])) {
//                            validPositions.add(new int[]{i, j});
//                        }
//                    }
//                }
//            }
//        }
//        return validPositions;
//    }
public Set<int[]> getValidFencePositions() {
    Set<int[]> validPositions = new HashSet<>();
    if (noFencesBuilt()) {
        // 처음 울타리를 지을 때: 빈 타일 또는 외양간이 있는 타일이 유효한 위치
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null || tiles[i][j] instanceof Barn) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
    } else {
        // 이후 울타리를 지을 때: 기존 울타리와 인접한 빈 타일 또는 외양간이 있는 타일이 유효한 위치
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if ((tiles[i][j] == null || tiles[i][j] instanceof Barn) && !visited[i][j]) {
                    if (isAdjacentToFence(i, j) && !isEnclosedByFences(i, j, visited)) {
                        validPositions.add(new int[]{i, j});
                    }
                }
                visited[i][j] = true;
            }
        }
    }
    return validPositions;
}

    private boolean isAdjacentToFence(int x, int y) {
        if (x > 0 && fences[x - 1][y][1]) return true; // 상단 인접 타일의 하단 울타리
        if (x < tiles.length - 1 && fences[x + 1][y][0]) return true; // 하단 인접 타일의 상단 울타리
        if (y > 0 && fences[x][y - 1][3]) return true; // 좌측 인접 타일의 우측 울타리
        if (y < tiles[0].length - 1 && fences[x][y + 1][2]) return true; // 우측 인접 타일의 좌측 울타리
        return false;
    }




    private boolean noFencesBuilt() {
        for (boolean[][] row : fences) {
            for (boolean[] tile : row) {
                for (boolean hasFence : tile) {
                    if (hasFence) return false;
                }
            }
        }
        return true;
    }

    private boolean isEnclosedByFences(int x, int y, boolean[][] visited) {
        if (visited[x][y]) {
            return false;
        }

        visited[x][y] = true;
        boolean isEnclosed = true;

        // 위쪽
        if (x > 0) {
            if (!fences[x - 1][y][1]) {
                isEnclosed = false;
            } else if (!visited[x - 1][y]) {
                isEnclosed = isEnclosed && isEnclosedByFences(x - 1, y, visited);
            }
        }
        // 아래쪽
        if (x < tiles.length - 1) {
            if (!fences[x + 1][y][0]) {
                isEnclosed = false;
            } else if (!visited[x + 1][y]) {
                isEnclosed = isEnclosed && isEnclosedByFences(x + 1, y, visited);
            }
        }
        // 왼쪽
        if (y > 0) {
            if (!fences[x][y - 1][3]) {
                isEnclosed = false;
            } else if (!visited[x][y - 1]) {
                isEnclosed = isEnclosed && isEnclosedByFences(x, y - 1, visited);
            }
        }
        // 오른쪽
        if (y < tiles[0].length - 1) {
            if (!fences[x][y + 1][2]) {
                isEnclosed = false;
            } else if (!visited[x][y + 1]) {
                isEnclosed = isEnclosed && isEnclosedByFences(x, y + 1, visited);
            }
        }

        return isEnclosed;
    }


//    private void addAdjacentPositions(Set<int[]> positions, int x, int y) {
//        if (x > 0 && !hasFence(x - 1, y) && (tiles[x - 1][y] == null || tiles[x - 1][y] instanceof Barn)) {
//            positions.add(new int[]{x - 1, y});
//        }
//        if (x < tiles.length - 1 && !hasFence(x + 1, y) && (tiles[x + 1][y] == null || tiles[x + 1][y] instanceof Barn)) {
//            positions.add(new int[]{x + 1, y});
//        }
//        if (y > 0 && !hasFence(x, y - 1) && (tiles[x][y - 1] == null || tiles[x][y - 1] instanceof Barn)) {
//            positions.add(new int[]{x, y - 1});
//        }
//        if (y < tiles[0].length - 1 && !hasFence(x, y + 1) && (tiles[x][y + 1] == null || tiles[x][y + 1] instanceof Barn)) {
//            positions.add(new int[]{x, y + 1});
//        }
//    }



    public int getAnimalCapacity() {
        int totalCapacity = 0;
        for (FenceArea area : fenceAreas) {
            totalCapacity += area.calculateCapacity();
        }
        return totalCapacity;
    }

    // 밭 위치 확인 메서드
    public Set<int[]> getValidFieldPositions() {
        Set<int[]> validPositions = new HashSet<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof FieldTile) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
        return validPositions;
    }

    // 곡식 또는 야채 심기 메서드
    public void plantField(int x, int y, int initialCrops, String cropType) {
        if (tiles[x][y] instanceof FieldTile) {
            ((FieldTile) tiles[x][y]).setCrops(initialCrops, cropType);
        }
    }

    public void plowField(int x, int y) {
        if (tiles[x][y] == null) {
            tiles[x][y] = new FieldTile(x, y, 0, null);
        }
    }

    public void resetFamilyMembers() {
        for (int i = 0; i < familyMembers.length; i++) {
            for (int j = 0; j < familyMembers[i].length; j++) {
                if (familyMembers[i][j] != null) {
                    familyMembers[i][j].resetPosition();
                    Room room = (Room) tiles[familyMembers[i][j].getX()][familyMembers[i][j].getY()];
                    room.setFamilyMember(familyMembers[i][j]);
                }
            }
        }
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean[][][] getFences() {
        return fences;
    }

    public FamilyMember[][] getFamilyMembers() {
        return familyMembers;
    }

    public Animal[][] getAnimals() {
        return animals;
    }

    public void removeFamilyMember(int x, int y) {
        familyMembers[x][y] = null;
    }

    public void changeHouse() {
    }

    // 빈 방이 있는지 확인하는 메서드
    public boolean hasEmptyRoom() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room && !((Room) tile).hasFamilyMember()) {
                    return true;
                }
            }
        }
        return false;
    }

    // 방이 비어 있는지 확인하는 메서드
    public boolean isEmptyRoom(int x, int y) {
        return tiles[x][y] instanceof Room && !((Room) tiles[x][y]).hasFamilyMember();
    }

    // 빈 방의 위치를 반환하는 메서드
    public List<int[]> getEmptyRoomPositions() {
        List<int[]> emptyRooms = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (isEmptyRoom(i, j)) {
                    emptyRooms.add(new int[]{i, j});
                }
            }
        }
        return emptyRooms;
    }

    // 보드에 가족 구성원을 추가하는 메서드
    public void addFamilyMemberToBoard(FamilyMember familyMember, int x, int y) {
        if (isEmptyRoom(x, y)) {
            ((Room) tiles[x][y]).setFamilyMember(familyMember);
            familyMember.setX(x);
            familyMember.setY(y);
        }
    }

    // 특정 위치에 동물을 배치할 수 있는지 확인하는 메서드
    public boolean canPlaceAnimal(int x, int y, String animalType) {
        if (tiles[x][y] instanceof Room) {
            Room room = (Room) tiles[x][y];
            return !room.hasAnimal() || room.getAnimal().getType().equals(animalType);
        } else if (tiles[x][y] instanceof Barn) {
            Barn barn = (Barn) tiles[x][y];
            if (isFenceArea(x, y)) {
                int capacity = calculateFenceCapacity();
                int animalCount = countAnimalsInsideFences(animalType);
                return animalCount < capacity;
            } else {
                return !barn.hasAnimal() || barn.getAnimal().getType().equals(animalType);
            }
        } else if (isFenceArea(x, y)) {
            int capacity = calculateFenceCapacity();
            int animalCount = countAnimalsInsideFences(animalType);
            return animalCount < capacity;
        } else {
            return false;
        }
    }


    // 보드에 동물을 추가하는 메서드
    public void addAnimalToBoard(Animal animal, int x, int y) {
        if (canPlaceAnimal(x, y, animal.getType())) {
            if (tiles[x][y] instanceof Room) {
                ((Room) tiles[x][y]).setAnimal(animal);
            } else if (tiles[x][y] instanceof Barn) {
                if (isFenceArea(x, y)) {
                    addAnimalToFenceArea(animal, x, y);
                } else {
                    ((Barn) tiles[x][y]).setAnimal(animal);
                }
            } else if (isFenceArea(x, y)) {
                addAnimalToFenceArea(animal, x, y);
            } else {
                animals[x][y] = animal;
            }
            animal.setX(x);
            animal.setY(y);
        } else {
            System.out.println("해당 위치에는 동물을 배치할 수 없습니다.");
        }
    }

    // 유효한 동물 배치 위치를 반환하는 메서드
    public Set<int[]> getValidAnimalPositions(String animalType) {
        Set<int[]> validPositions = new HashSet<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (canPlaceAnimal(i, j, animalType)) {
                    validPositions.add(new int[]{i, j});
                }
            }
        }
        return validPositions;
    }

    // 해당 위치가 울타리 영역인지 확인하는 메서드
    public boolean isFenceArea(int x, int y) {
        for (FenceArea area : fenceAreas) {
            if (area.containsTile(x, y)) {
                return true;
            }
        }
        return false;
    }

//    private int calculateFenceCapacity() {
//        int capacity = 0;
//        for (FenceArea area : fenceAreas) {
//            int fenceAreaCapacity = 1;
//            for (int[] tile : area.getTiles()) {
//                if (tiles[tile[0]][tile[1]] instanceof Barn) {
//                    fenceAreaCapacity *= 4;
//                } else {
//                    fenceAreaCapacity *= 2;
//                }
//            }
//            capacity += fenceAreaCapacity;
//        }
//        return capacity;
//    }
public int calculateFenceCapacity() {
    int capacity = 0;
    for (FenceArea area : fenceAreas) {
        capacity += area.calculateCapacity();
    }
    return capacity;
}


    private int countAnimalsInsideFences(String animalType) {
        int count = 0;
        for (Animal[] row : animals) {
            for (Animal animal : row) {
                if (animal != null && animal.getType().equals(animalType)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void printFenceAreas() {
        System.out.println("Printing all fence areas:");
        for (FenceArea area : managedFenceAreas) {
            area.printFenceAreaDetails();
        }
    }

    public int getAnimalCount(String animalType, FenceArea area) {
        int count = 0;
        for (Animal animal : area.getAnimals()) {
            if (animal.getType().equals(animalType)) {
                count++;
            }
        }
        return count;
    }

    public List<FenceArea> getFenceAreas() {
        return fenceAreas;
    }

    // 동물 번식 단계 구현
    public List<Animal> breedAnimals() {
        List<Animal> newAnimals = new ArrayList<>();
        for (FenceArea area : fenceAreas) {
            int animalCount = getAnimalCount("sheep", area);
            if (animalCount >= 2) {
                newAnimals.add(new Animal(-1, -1, "sheep")); // 새끼 동물 추가
            }
        }
        return newAnimals;
    }

    // 보드에 동물을 추가하는 메서드
    // 특정 위치에 동물을 추가하는 메서드
    public void addAnimalToFenceArea(Animal animal, int x, int y) {
        for (FenceArea area : fenceAreas) {
            if (area.containsTile(x, y)) {
                area.addAnimal(animal);
                animals[x][y] = animal;
                animal.setX(x);
                animal.setY(y);
                return;
            }
        }
        System.out.println("해당 위치에 동물을 추가할 수 없습니다.");
    }

    public void addAnimal(Animal animal) {
        // 초기에는 보드 밖에 배치
        // 실제 게임에서는 플레이어가 특정 위치에 동물을 배치하게 될 것
        animal.setX(-1);
        animal.setY(-1);
        // 동물을 리스트나 맵에 추가하는 로직 필요
    }

    public void renovateRooms(RoomType newType, Player player) {
        int roomCount = 0;
        RoomType currentType;

        // 1. 현재 방의 타입을 확인하고 업그레이드할 수 있는 방의 수를 계산
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room) {
                    Room room = (Room) tile;
                    currentType = room.getType();
                    if (currentType == RoomType.WOOD && newType == RoomType.CLAY) {
                        roomCount++;
                    } else if (currentType == RoomType.CLAY && newType == RoomType.STONE) {
                        roomCount++;
                    }
                }
            }
        }

        if (roomCount == 0) {
            System.out.println("업그레이드할 수 있는 방이 없습니다.");
            return;
        }

        // 2. 자원이 충분한지 확인
        Map<String, Integer> cost = new HashMap<>();
        cost.put(newType.name().toLowerCase(), roomCount); // 방 수만큼 자원 필요
        if (player.checkResources(cost)) {
            player.payResources(cost);

            // 3. 방 타입 업그레이드
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile instanceof Room) {
                        Room room = (Room) tile;
                        currentType = room.getType();
                        if (currentType == RoomType.WOOD && newType == RoomType.CLAY) {
                            room.setType(newType);
                        } else if (currentType == RoomType.CLAY && newType == RoomType.STONE) {
                            room.setType(newType);
                        }
                    }
                }
            }
        } else {
            System.out.println("자원이 부족하여 집을 고칠 수 없습니다.");
        }
    }

    // getter and setter methods
}
