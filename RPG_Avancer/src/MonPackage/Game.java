package MonPackage;

import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class Game {
    private Character player;
    private WeaponStore store;
    private String[][] dungeon;
    private int playerX, playerY;
    private final int dungeonSize = 5;
    private Monster currentMonster;
    private Obstacle currentObstacle;
    private Random random = new Random();
    private boolean gameRunning = true;
    

    public Game() {
        store = new WeaponStore();
        dungeon = new String[dungeonSize][dungeonSize];
        initializeDungeon();
        playerX = 0;
        playerY = 0;
    }

    private void displayClassOptions() {
        System.out.println("\n--- Choix de la classe ---");
        System.out.println("1. Sorcier 🧙 : Maître des sorts magiques, il peut lancer des sorts puissants (au coût de mana) pour infliger des dégâts élevés.");
        System.out.println("   - Capacité initiale : 'Boule de Feu' inflige 30 points de dégâts pour 20 de mana.");
        System.out.println("   - Capacité au Niveau 2 : 'Tempête de Glace' gèle les ennemis pour un tour.");
        System.out.println("2. Guerrier 🪓 : Combattant puissant, il peut entrer en état de 'Rage' pour infliger des dégâts supplémentaires et résister aux attaques.");
        System.out.println("   - Capacité initiale : 'Rage' augmente les dégâts pour un tour.");
        System.out.println("   - Capacité au Niveau 2 : 'Bouclier de Fer' augmente la défense.");
        System.out.println("3. Elfe 🏹 : Agile et précis, il peut esquiver certaines attaques et infliger des dégâts supplémentaires avec des arcs.");
        System.out.println("   - Capacité initiale : Bonus de dégâts avec l'arc et chance d'esquive.");
        System.out.println("   - Capacité au Niveau 2 : 'Invisibilité' pour échapper aux ennemis.");
        System.out.println("\nVeuillez choisir votre classe en tapant 'Sorcier', 'Guerrier' ou 'Elfe'.");
    }

    private void displayInstructions() {
        System.out.println("\n--- Instructions du jeu ---");
        System.out.println("Bienvenue dans le donjon !");
        System.out.println("Vous incarnez un aventurier qui doit trouver la sortie du donjon tout en affrontant des monstres et en collectant des trésors.");
        System.out.println("Commandes principales :");
        System.out.println("1. Acheter des armes pour vous équiper.");
        System.out.println("2. Explorer le donjon pour découvrir de nouveaux endroits.");
        System.out.println("3. Voir l'état de votre personnage, y compris santé, mana, XP et potions.");
        System.out.println("4. Afficher la carte du donjon pour vous repérer.");
        System.out.println("5. Quitter le jeu.");
        System.out.println("Déplacez-vous dans le donjon en utilisant les commandes 'haut', 'bas', 'gauche', 'droite'.");
        System.out.println("Bonne chance, et faites preuve de bravoure ! 💪");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎮 Bienvenue dans le RPG ASCII Art ! 🎮");
        displayInstructions();
        
        while (gameRunning) {
            try {
                System.out.print("Entrez le nom de votre personnage : ");
                String name = scanner.nextLine();
                String characterClass;
                
                displayClassOptions(); // Affiche les options de classe avec les descriptions
                
                while (true) {
                    System.out.print("Choisissez une classe (Sorcier, Elfe, Guerrier) : ");
                    characterClass = scanner.nextLine();
                    if (characterClass.equals("Sorcier") || characterClass.equals("Elfe") || characterClass.equals("Guerrier")) {
                        break;
                    } else {
                        System.out.println("Classe invalide. Veuillez choisir entre Sorcier, Elfe ou Guerrier.");
                    }
                }

                player = new Character(name, characterClass);
                System.out.println("\nVotre aventure commence ! Vous êtes un(e) " + player.getCharacterClass() + " prêt(e) à explorer le donjon... 🌟");

                gameLoop(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide ! Veuillez essayer de nouveau.");
                scanner.nextLine(); 
            }
        }
    }
    


    public static void nextLevel() {
        System.out.println("🚪 Vous avez terminé ce niveau du donjon ! Félicitations !");
        
        // Propose de rejouer ou d'arrêter
      
        Game gameInstance = new Game();
        gameInstance.endGameOptions(new Scanner(System.in));
    }
    
    
    
    private void resetDungeon() {
        dungeon = new String[dungeonSize][dungeonSize]; 
        initializeDungeon(); // Réinitialise le contenu du donjon
        playerX = 0; // Repositionne le joueur en haut à gauche
        playerY = 0;
    }

    
    
    // Méthode endGameOptions mise à jour pour inclure la sortie réussie
    private void endGameOptions(Scanner scanner) {
        System.out.println("\n--- Fin du Jeu ---");
        System.out.println("1. Rejouer avec les mêmes données 🔄");
        System.out.println("2. Voir vos statistiques finales 📜");
        System.out.println("3. Quitter le jeu ❌");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    resetDungeon(); // Réinitialise uniquement le donjon
                    System.out.println("Le donjon a été réinitialisé. Bonne chance pour cette nouvelle tentative !");
                    gameLoop(scanner); // Reprend la boucle de jeu
                    break;
                case 2:
                    player.displayStatus(); // Affiche le statut final du joueur
                    break;
                case 3:
                    System.out.println("Merci d'avoir joué ! À bientôt !");
                    gameRunning = false; // Met fin à la boucle principale
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erreur d'entrée. Veuillez entrer un nombre valide.");
            scanner.nextLine(); 
        }
    }



    // Méthode resetGame() pour redémarrer le jeu
    private void resetGame() {
        dungeon = new String[dungeonSize][dungeonSize];
        initializeDungeon();
        playerX = 0;
        playerY = 0;
        
        // Réinitialise le joueur au niveau 1 avec 0 XP, santé et mana max
        player.resetCharacter();
        
        start();
    }



    private void gameLoop(Scanner scanner) {
        while (player.isAlive() && gameRunning) {
            System.out.println("\n--- Menu ---");
            System.out.println("1: Acheter une arme 🛒");
            System.out.println("2: Explorer le donjon 🏰");
            System.out.println("3: Afficher l'état du personnage 🧙");
            System.out.println("4: Afficher la carte du donjon 🗺️");
            System.out.println("5: Quitter ❌");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        buyWeapon(scanner);
                        break;
                    case 2:
                        exploreDungeon(scanner);
                        break;
                    case 3:
                        player.displayStatus();
                        System.out.println(player.asciiArt());
                        break;
                    case 4:
                        displayDungeon();
                        break;
                    case 5:
                        System.out.println("Merci d'avoir joué ! À bientôt pour de nouvelles aventures. 🎉");
                        gameRunning = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez entrer un chiffre entre 1 et 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un chiffre.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }

        if (!player.isAlive()) {
            System.out.println("☠️ Vous avez été vaincu. Fin de l'aventure. ☠️");
            player.resetCharacter(); // Réinitialise le personnage
            endGameOptions(scanner); // Propose de rejouer
            
        }

    }

    private void buyWeapon(Scanner scanner) {
        store.printWeapons();
        System.out.print("Choisissez une arme (index) : ");
        try {
            int index = scanner.nextInt();
            if (index < 0 || index >= store.getWeapons().size()) {
                System.out.println("Arme invalide.");
                return;
            }

            Weapon weapon = store.getWeapons().get(index);
            if (player.getMoney() >= weapon.getPrice()) {
                player.reduceMoney(weapon.getPrice());
                player.addWeapon(weapon);
                System.out.println("Vous avez acheté " + weapon.getName() + " ! ⚔️");
            } else {
                System.out.println("Vous n'avez pas assez d'argent. 💸");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erreur : veuillez entrer un numéro d'index valide.");
            scanner.nextLine(); 
        }
    }

    private void exploreDungeon(Scanner scanner) {
        boolean exploring = true;

        while (exploring && player.isAlive()) {
            displayDungeon();
            System.out.println("Vous êtes à la position (" + playerX + ", " + playerY + ").");

            checkInteraction(scanner);

            if ("🚪".equals(dungeon[playerX][playerY])) {
                System.out.println("🎉 Félicitations, vous avez trouvé la sortie ! Vous êtes un héros ! 🎉");
                endGameOptions(scanner); 
                exploring = false;
            }


            System.out.println("Déplacez-vous : (haut, bas, gauche, droite)");
            String direction = scanner.next().toLowerCase();
            movePlayer(direction);
        }
    }

    private void movePlayer(String direction) {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
            case "haut":
                newX = Math.max(playerX - 1, 0);
                break;
            case "bas":
                newX = Math.min(playerX + 1, dungeonSize - 1);
                break;
            case "gauche":
                newY = Math.max(playerY - 1, 0);
                break;
            case "droite":
                newY = Math.min(playerY + 1, dungeonSize - 1);
                break;
            default:
                System.out.println("Direction invalide. Veuillez choisir 'haut', 'bas', 'gauche' ou 'droite'.");
                return;
        }

        playerX = newX;
        playerY = newY;
    }

    private void checkInteraction(Scanner scanner) {
        if ("👹".equals(dungeon[playerX][playerY])) {
            currentMonster = new Monster();
            System.out.println("Un monstre apparaît ! 👹 Préparez-vous au combat !");
            combat(scanner);
            if (!currentMonster.isAlive()) {
                dungeon[playerX][playerY] = "⬛";
            }
        } else if ("🪨".equals(dungeon[playerX][playerY])) {
            currentObstacle = new Obstacle();
            System.out.println("Un obstacle bloque votre chemin ! 🪨");
            destroyObstacle(scanner);
        } else if ("💰".equals(dungeon[playerX][playerY])) {
            System.out.println("Vous avez trouvé un trésor ! 💰 +20 pièces d'or.");
            player.reduceMoney(-20); // Ajoute de l'argent
            dungeon[playerX][playerY] = "⬛";
        } else if ("⚠️".equals(dungeon[playerX][playerY])) {
            System.out.println("Aïe ! Vous êtes tombé dans un piège ! ⚠️ -10 Santé.");
            player.hit(10); // Perte de santé
            dungeon[playerX][playerY] = "⬛";
        }
    }

    private void combat(Scanner scanner) {
        while (player.isAlive() && currentMonster.isAlive()) {
            System.out.print("Attaquer (A), Défendre (D), Utiliser une potion (P), ou Fuir (F) ? ");
            String action = scanner.next().toUpperCase();

            if ("A".equals(action)) {
                if (!player.getInventory().isEmpty()) {
                    Weapon weapon = player.getInventory().get(0);
                    weapon.attack(currentMonster);
                    System.out.println("Vous attaquez le monstre avec " + weapon.getName() + "! 👹 Santé du monstre : " + currentMonster.getHealth());
                } else if (player.getCharacterClass().equals("Sorcier")) {
                    player.castSpell(currentMonster);
                } else {
                    System.out.println("Vous n'avez pas d'arme ! Le monstre vous attaque. 😱");
                    player.hit(currentMonster.attack());
                }
            } else if ("D".equals(action)) {
                System.out.println("Vous vous défendez et réduisez les dégâts de moitié.");
                player.hit(currentMonster.attack() / 2);
            } else if ("P".equals(action)) {
                if (player.usePotion()) {
                    System.out.println("Vous avez utilisé une potion pour récupérer 20 points de santé.");
                } else {
                    System.out.println("Vous n'avez pas de potion. 😢");
                }
            } else if ("F".equals(action)) {
                System.out.println("Vous fuyez le combat ! 🏃‍♂️ Vous êtes renvoyé plus loin dans le donjon.");
                movePlayerToDistantSpot();
                break;
            } else {
                System.out.println("Action invalide. Veuillez entrer 'A', 'D', 'P', ou 'F'.");
            }

            if (currentMonster.isAlive()) {
                player.hit(10);
                System.out.println("Le monstre riposte ! 😈 Votre santé : " + player.getHealth());
            }
        }

        if (!currentMonster.isAlive()) {
            System.out.println("Vous avez vaincu le monstre ! 🎉 Gagné 20 XP.");
            player.addXP(20);
        }
    }

    private void movePlayerToDistantSpot() {
        int randomX, randomY;
        do {
            randomX = random.nextInt(dungeonSize);
            randomY = random.nextInt(dungeonSize);
        } while (Math.abs(randomX - (dungeonSize - 1)) + Math.abs(randomY - (dungeonSize - 1)) < 3); // Vérifie que le joueur est loin de la sortie

        playerX = randomX;
        playerY = randomY;
        System.out.println("Vous avez été déplacé à une position éloignée : (" + playerX + ", " + playerY + ").");
    }

    private void destroyObstacle(Scanner scanner) {
        while (true) {
            System.out.print("Détruire l'obstacle (D) ou fuir (F) ? ");
            String action = scanner.next().toUpperCase();

            if ("D".equals(action)) {
                if (!player.getInventory().isEmpty()) {
                    Weapon weapon = player.getInventory().get(0);
                    weapon.attack(currentObstacle);
                    System.out.println("Vous détruisez l'obstacle avec " + weapon.getName() + " ! 🪓");
                    dungeon[playerX][playerY] = "⬛";
                } else {
                    System.out.println("Vous n'avez pas d'arme pour détruire l'obstacle. Vous perdez de la santé.");
                    player.hit(10);
                }
                break;
            } else if ("F".equals(action)) {
                System.out.println("Vous avez choisi de fuir !");
                break;
            } else {
                System.out.println("Action invalide. Veuillez choisir 'D' pour détruire ou 'F' pour fuir.");
            }
        }
    }

    private void initializeDungeon() {
        for (int i = 0; i < dungeonSize; i++) {
            for (int j = 0; j < dungeonSize; j++) {
                int rand = random.nextInt(10);
                if (rand < 2) dungeon[i][j] = "👹"; 
                else if (rand < 4) dungeon[i][j] = "🪨"; 
                else if (rand < 5) dungeon[i][j] = "💰"; 
                else if (rand < 6) dungeon[i][j] = "⚠️"; // Piège
                else dungeon[i][j] = "⬛"; // Case vide
            }
        }
        dungeon[dungeonSize - 1][dungeonSize - 1] = "🚪"; // Position de sortie
    }

    private void displayDungeon() {
        System.out.println("Carte du donjon :");
        for (int i = 0; i < dungeonSize; i++) {
            for (int j = 0; j < dungeonSize; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print("🧍 ");
                } else {
                    System.out.print(dungeon[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
 

}
