package MonPackage;

import java.util.Random;

public class DungeonMap {
    private final String[][] map;
    private final int size;
    private int playerX, playerY;
    private static final String EMPTY = "⬛";
    private static final String MONSTER = "👹";
    private static final String OBSTACLE = "🪨";
    private static final String TREASURE = "💰";
    private static final String TRAP = "⚠️";
    private static final String EXIT = "🚪";
    private static final String PLAYER = "🧍";

    public DungeonMap(int size) {
        this.size = size;
        this.map = new String[size][size];
        initializeMap();
        placePlayer(0, 0);
    }

    private void initializeMap() {
        Random rand = new Random();
        
        // Remplir la carte avec des cases vides
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = EMPTY;
            }
        }

        // Assurer que la position de départ du joueur (0, 0) est toujours vide
        map[0][0] = PLAYER;

        // Placement aléatoire des monstres, obstacles, trésors et pièges
        for (int i = 0; i < size; i++) {
            placeRandomEntity(MONSTER);
            placeRandomEntity(OBSTACLE);
            placeRandomEntity(TREASURE);
            placeRandomEntity(TRAP);
        }

        // Placer la sortie en bas à droite
        map[size - 1][size - 1] = EXIT;
    }

    private void placeRandomEntity(String entity) {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(size);
            y = rand.nextInt(size);
        } while (!map[x][y].equals(EMPTY) || (x == 0 && y == 0)); // Éviter la case de départ
        map[x][y] = entity;
    }

    public void placePlayer(int x, int y) {
        // Remettre l'ancienne position du joueur à vide
        if (map[playerX][playerY].equals(PLAYER)) {
            map[playerX][playerY] = EMPTY;
        }
        playerX = x;
        playerY = y;
        map[playerX][playerY] = PLAYER;
    }

    public boolean isExit(int x, int y) {
        return map[x][y].equals(EXIT);
    }

    public boolean isMonster(int x, int y) {
        return map[x][y].equals(MONSTER);
    }

    public boolean isObstacle(int x, int y) {
        return map[x][y].equals(OBSTACLE);
    }

    public boolean isTreasure(int x, int y) {
        return map[x][y].equals(TREASURE);
    }

    public boolean isTrap(int x, int y) {
        return map[x][y].equals(TRAP);
    }

    public void clearPosition(int x, int y) {
        map[x][y] = EMPTY;
    }

    public void displayMap() {
        System.out.println("Carte complète du donjon :");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void displayMiniMap() {
        System.out.println("Mini-carte du donjon :");
        for (int i = Math.max(0, playerX - 1); i <= Math.min(size - 1, playerX + 1); i++) {
            for (int j = Math.max(0, playerY - 1); j <= Math.min(size - 1, playerY + 1); j++) {
                if (i == playerX && j == playerY) {
                    System.out.print(PLAYER + " ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }
}
