package MonPackage;

import java.util.ArrayList;

public class Character {
    private String name;
    private String characterClass;
    private double health;
    private double mana;
    private double money;
    private double xp;
    private int level;
    private int potions;
    private ArrayList<Weapon> inventory;

    public Character(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.health = 100;
        this.mana = 50;
        this.money = 100;
        this.xp = 0;
        this.level = 1;
        this.potions = 3; // Initialisation avec 3 potions
        this.inventory = new ArrayList<>();
        
        switch (characterClass) {
            case "Sorcier":
                this.mana += 30; // Bonus de mana pour le Sorcier
                break;
            case "Guerrier":
                this.health += 50; // Bonus de santé pour le Guerrier
                break;
            case "Elfe":
                this.mana += 20;
                this.health += 10; // L'Elfe obtient un bonus équilibré
                break;
            default:
                break;
        }
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void displayStatus() {
        System.out.println("Nom : " + name + " | Classe : " + characterClass + 
                           " | Santé : " + health + " ❤️ | Mana : " + mana + " 🔮 | Niveau : " + level + 
                           " | XP : " + xp + " ⭐ | Argent : " + money + " 💰 | Potions : " + potions + " 🧪");
        System.out.println("Armes en inventaire :");
        if (inventory.isEmpty()) {
            System.out.println("   - Aucun arme.");
        } else {
            for (Weapon weapon : inventory) {
                System.out.println("   - " + weapon.getName() + " (Dégâts : " + weapon.getDamage() + ")");
            }
        }
    }

    public void addWeapon(Weapon weapon) {
        inventory.add(weapon);
    }

    public void reduceMoney(double amount) {
        money -= amount;
    }

    public double getMoney() {
        return money;
    }

    public ArrayList<Weapon> getInventory() {
        return inventory;
    }

    public void addXP(double amount) {
        xp += amount;
        checkLevelUp();
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void hit(double damage) {
        this.health -= damage;
    }

    public double getHealth() {
        return health;
    }

    public boolean usePotion() {
        if (potions > 0) {
            health = Math.min(health + 20, 100); // Limite la santé maximale à 100
            mana = Math.min(mana + 10, 100);     // Limite le mana maximal à 100
            potions--;
            System.out.println("Potion utilisée ! Santé augmentée de 20 points et Mana de 10 points.");
            return true;
        } else {
            System.out.println("Vous n'avez plus de potions.");
            return false;
        }
    }

    public String asciiArt() {
        return "  🧍\n /|\\ \n / \\ \n";
    }

 // Classe Character.java

    private void checkLevelUp() {
        if (xp >= 50) {
            level++;
            xp -= 100;
            health = Math.min(health + 20, 100);
            mana = Math.min(mana + 10, 100);
            System.out.println("🎉 Niveau augmenté ! Vous êtes maintenant niveau " + level + " ! 🎉");

            unlockNewAbilities();

           
            Game.nextLevel(); // Appel pour signaler le changement de niveau
        }
    }

    

    private void unlockNewAbilities() {
        if (characterClass.equals("Sorcier") && level == 2) {
            System.out.println("Nouvelle capacité débloquée : 'Tempête de Glace' pour geler les ennemis !");
        } else if (characterClass.equals("Guerrier") && level == 2) {
            System.out.println("Nouvelle capacité débloquée : 'Bouclier de Fer' pour augmenter la défense !");
        } else if (characterClass.equals("Elfe") && level == 2) {
            System.out.println("Nouvelle capacité débloquée : 'Invisibilité' pour échapper aux ennemis !");
        }
    }

    // Méthode pour les Sorciers : Lancer un sort
    public void castSpell(Monster monster) {
        if (!characterClass.equals("Sorcier")) {
            System.out.println("Seuls les Sorciers peuvent lancer des sorts.");
            return;
        }

        if (mana >= 20) {
            double spellDamage = (level >= 2) ? 40 : 30; // Sort plus puissant au niveau 2
            mana -= 20; // Coût en mana pour lancer le sort
            monster.hit(spellDamage);
            System.out.println("✨ Vous lancez un sort de Boule de Feu ! 🔥 Le monstre subit " + spellDamage + " points de dégâts.");
        } else {
            System.out.println("Mana insuffisant pour lancer un sort.");
        }
    }

    // Méthode pour les Guerriers : Activer la Rage
    public void useRage() {
        if (!characterClass.equals("Guerrier")) {
            System.out.println("Seuls les Guerriers peuvent utiliser la Rage.");
            return;
        }

        double rageBonus = (level >= 2) ? 1.5 : 1.2; // Bonus de rage amélioré au niveau 2
        System.out.println("💢 Vous entrez en Rage ! Dégâts augmentés pour ce tour de combat.");
        // Appliquer le bonus de rage (à gérer dans le combat)
    }

    // Méthode pour les Elfes : Utiliser l'Invisibilité
    public void useInvisibility() {
        if (!characterClass.equals("Elfe")) {
            System.out.println("Seuls les Elfes peuvent devenir invisibles.");
            return;
        }

        if (level >= 2) {
            System.out.println("🕵️‍♂️ Vous utilisez votre invisibilité et échappez aux ennemis !");
            // Effet d’invisibilité (à gérer dans le combat ou déplacement)
        } else {
            System.out.println("Votre niveau est trop bas pour utiliser l'invisibilité.");
        }
    }

    public void resetCharacter() {
        this.health = 100;
        this.mana = 50;
        this.money = 100;
        this.xp = 0;
        this.level = 1;
        this.potions = 3;
        this.inventory.clear(); // Vider l'inventaire

        // Réinitialisation des bonus en fonction de la classe de personnage
        switch (characterClass) {
            case "Sorcier":
                this.mana += 30; // Bonus de mana pour le Sorcier
                break;
            case "Guerrier":
                this.health += 50; // Bonus de santé pour le Guerrier
                break;
            case "Elfe":
                this.mana += 20;
                this.health += 10; // Bonus équilibré pour l'Elfe
                break;
            default:
                break;
        }
       
    }

}
