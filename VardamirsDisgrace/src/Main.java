import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        beforeFirstBattleTale();

        System.out.println("Character Creation");
        System.out.print("Enter character name: ");
        String playerName = scanner.nextLine();

        int pointsRemaining = 15;
        int strength = 0;
        int constitution = 0;
        int agility = 0;
        int dexterity = 0;

        while (pointsRemaining > 0) {
            System.out.println("\nPoints Remaining: " + pointsRemaining);
            System.out.println("1. Add to Strength");
            System.out.println("2. Add to Constitution");
            System.out.println("3. Add to Agility");
            System.out.println("4. Add to Dexterity");
            System.out.print("Choose an attribute to increase (1-4): ");
            int choice = getIntInput(scanner);

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please choose a valid attribute.");
                continue;
            }

            System.out.print("Enter the number of points to add: ");
            int pointsToAdd = getIntInput(scanner);

            if (pointsToAdd <= 0 || pointsToAdd > pointsRemaining) {
                System.out.println("Invalid number of points. Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    strength += pointsToAdd;
                    break;
                case 2:
                    constitution += pointsToAdd;
                    break;
                case 3:
                    agility += pointsToAdd;
                    break;
                case 4:
                    dexterity += pointsToAdd;
                    break;
            }

            pointsRemaining -= pointsToAdd;
        }

        System.out.println("\nCharacter " + playerName);
        System.out.println("Strength: " + strength);
        System.out.println("Constitution: " + constitution);
        System.out.println("Agility: " + agility);
        System.out.println("Dexterity: " + dexterity);

        System.out.println("\nChoose a weapon: ");
        System.out.println("1. Light Sword");
        System.out.println("2. Heavy Sword");
        System.out.println("3. Bow");
        System.out.println("4. Crossbow");
        int weaponChoice = getIntInput(scanner);
        Weapon weapon = createWeapon(weaponChoice);

        Character player = new Character(playerName, strength, constitution, agility, dexterity, weapon, null,
                new Potion(Potion.SMALL));

        int magicCounter = 0;
        while (magicCounter < 2) {
            System.out.println("\nChoose two magics: ");
            System.out.println("1. Fire");
            System.out.println("2. Poison");
            System.out.println("3. Ice");
            System.out.println("4. Electric");
            int magicChoice = getIntInput(scanner);
            createMagic(magicChoice, player);
            magicCounter++;
        }

        player.getMagics();

        player.setArmor(createArmor(player));

        System.out.println(player.toString() + "\n\n");

        Character enemy = new Character("Enemy", 3, 8, 2, 2, new Weapon(Weapon.LONG_SWORD), null,
                new Potion(Potion.SMALL));

        enemy.setArmor(new Armor(Armor.HEAVY, enemy));

        System.out.println("\n" + enemy.toString() + "\n\n");
        boolean coldUsed = false;
        boolean shockUsed = false;

        while (player.isAlive() && enemy.isAlive()) {
            int playerAction = getPlayerAction(scanner);
            System.out.println(player.getArmor().getDefence());
            player.getArmor().setDefence();

            if (player.getFireEffectTurns() > 0) {
                enemy.applyFireEffect();
            }

            if (player.getPoisonEffectTurns() > 0) {
                enemy.applyPoisonEffect();
            }

            if (player.getColdEffectTurns() <= 0) {
                enemy.removeColdEffect();
                coldUsed = false;
                player.setColdEffectTurns();
            }

            if (player.getShockEffectTurns() <= 0) {
                enemy.addDexterity(2);
                shockUsed = false;
                player.setShockEffectTurns();
            }

            switch (playerAction) {
                case 0:
                    System.out.println();
                    int playerDamage = player.calculateDamage();
                    int enemyDefense = enemy.getArmor().setDefence();
                    int damageDealt = playerDamage - enemyDefense;

                    if (damageDealt > 0) {
                        System.out.println(
                                player.getName() + " attacks " + enemy.getName() + " for " + playerDamage + " damage.");
                        enemy.receiveDamage(damageDealt);
                    } else {
                        System.out.println(player.getName() + " attacks " + enemy.getName() + " but does no damage.");
                    }
                    System.out.println(enemy.toString() + "\n\n");

                    break;

                case 1:
                    System.out.println("Choose a magic to use: ");

                    System.out.println("0. " + player.getMagic(0));
                    System.out.println("1. " + player.getMagic(1));

                    int magicChoice = getIntInput(scanner);

                    String magicName = player.getMagic(magicChoice).toString();

                    if (magicName.equals("Eletric Magic")) {
                        shockUsed = true;
                    }

                    if (magicName.equals("Ice Magic")) {
                        coldUsed = true;
                    }

                    System.out.println(shockUsed);

                    if (magicName.equals("Ice Magic")) {
                        coldUsed = true;
                    }

                    System.out.println(player.getMagic(magicChoice).toString() + " used.");

                    player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                    System.out.println(player.toString() + "\n\n");
                    System.out.println(enemy.toString() + "\n\n");

                    break;

                case 2:
                    player.getArmor().doubleDefence();
                    System.out.println(player.getName() + " doubles their defense for 1 round.");
                    System.out.println("Defesa atual: " + player.getArmor().getDefence());
                    System.out.println(enemy.toString() + "\n\n");

                    break;

                case 3:
                    player.heal();
                    System.out.println(player.getName() + " uses a potion and recovers " + player.getHeal() + " HP.");
                    System.out.println(player.getArmor().getDefence());
                    System.out.println(enemy.toString() + "\n\n");

                    break;
            }

            int computerAction = random.nextInt(3);
             enemy.getArmor().setDefence();

            switch (computerAction) {

                case 0:
                    int enemyDamage = enemy.calculateDamage();
                    int playerDefense = enemy.getArmor().setDefence();
                    int damageDealt = enemyDamage - playerDefense;

                    if (damageDealt > 0) {
                        System.out.println(
                                enemy.getName() + " attacks " + player.getName() + " for " + enemyDamage + " damage.");
                        player.receiveDamage(damageDealt);
                    } else {
                        System.out.println(enemy.getName() + " attacks " + player.getName() + " but does no damage.");
                    }
                    break;

                case 1:
                    enemy.getArmor().doubleDefence();
                    System.out.println(enemy.getName() + " doubles their defense for 1 round.");
                    System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                    break;

                case 2:
                    enemy.heal(enemy.getHeal());
                    System.out.println(enemy.getName() + " uses a potion and recovers " + enemy.getHeal() + " HP.");
                    break;
            }

            System.out.println(player.getName() + " HP: " + player.getHitPoints());
            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
            System.out.println("------");

            if (coldUsed) {
                player.removeColdEffectTurns();
            }
            if (shockUsed) {
                player.removeShockEffectTurns();
            }
        }


        if (player.isAlive()) {
                firstChoiceText();
        } else if (enemy.isAlive()) {
            System.out.println(enemy.getName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }

    private static int getIntInput(Scanner scanner) {
        int input = 0;
        boolean validInput = false;
        do {
            try {
                input = scanner.nextInt();
                validInput = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        } while (!validInput);
        return input;
    }

    private static Weapon createWeapon(int choice) {
        try {
            switch (choice) {
                case 1:
                    return new Weapon(Weapon.DAGGER);
                case 2:
                    return new Weapon(Weapon.LONG_SWORD);
                case 3:
                    return new Weapon(Weapon.BOW);
                case 4:
                    return new Weapon(Weapon.CROSSBOW);
                default:
                    throw new IllegalArgumentException("Invalid weapon choice.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static Armor createArmor(Character character) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose armor: ");
            System.out.println("1. Light Armor");
            System.out.println("2. Medium Armor");
            System.out.println("3. Heavy Armor");
            int armorChoice = getIntInput(scanner);

            switch (armorChoice) {
                case 1:
                    return new Armor(Armor.LIGHT, character);
                case 2:
                    return new Armor(Armor.MEDIUM, character);
                case 3:
                    return new Armor(Armor.HEAVY, character);
                default:
                    throw new IllegalArgumentException("Invalid armor choice.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static Magic createMagic(int magicChoice, Character character) {
        try {
            switch (magicChoice) {
                case 1:
                    character.addMagic(new Magic(Magic.FIRE));
                    break;
                case 2:
                    character.addMagic(new Magic(Magic.POISON));
                    break;
                case 3:
                    character.addMagic(new Magic(Magic.ICE));
                    break;
                case 4:
                    character.addMagic(new Magic(Magic.ELECTRIC));
                    break;
                case 5:
                    character.addMagic(new Magic(Magic.DARK));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid magic choice.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static int getPlayerAction(Scanner scanner) {
        System.out.println("Choose an action:");
        System.out.println("0 - Attack");
        System.out.println("1 - Use magic");
        System.out.println("2 - Defend");
        System.out.println("3 - Use Potion");
        System.out.print("Enter your choice (0-3): ");
        int choice = getIntInput(scanner);

        while (choice < 0 || choice > 3) {
            System.out.println("Invalid choice. Please choose a valid action (0-3).");
            System.out.print("Enter your choice (0-3): ");
            choice = getIntInput(scanner);
        }

        return choice;
    }

    private static void beforeFirstBattleTale() {

        System.out.println("After a lengthy patrol along the distant borders of your city,\n"
                + " you and your steadfast friend conclude your watch, opting to embark on the journey back home.\n"
                + " The path home stretches out, a seemingly endless ribbon through the untamed wilderness.\n"
                + " The air carries a sense of freshness, soothing your senses as you drink in the tranquil surroundings.\n"
                + "\n"
                + "Nature's symphony surrounds you, the harmonious chorus of Eternia's vibrant and thriving kingdom filling the air.\n"
                + " The songbirds sing their melodious tunes, while the gentle rustling of leaves adds a soft,\n"
                + " soothing backdrop to the tranquil ambiance. But for how long...?\n "
                + "After an hour of walking, you and Finrod finally arrive close to the capital, and he says with an eager grin,\n"
                + " \"We're finally getting close to it, I can't wait to eat my wife's beef stew!\"\n"
                + "\n"
                + "But something is amiss, the air, once so fresh and rejuvenating, has taken on an ominous heaviness.\n");
        System.out.println("Before you can reply, Finrod's voice trembles as he calls you urgently.\n"
                + " His usually resolute demeanor now betrays a deep, primal fear, something he has never experienced before.\n");
        System.out.println("Following his frantic gaze, you look towards the city, and your heart plummets.\n"
                + " The city, your beloved capital, is engulfed in a raging inferno.\n"
                + " The towering flames lick the night sky, casting an eerie,\n"
                + " malevolent glow that defies the tranquility that once enveloped the kingdom. \n");
        System.out.println("A shiver runs down your spine as you witness a horrifying sight, a colossal tornado of dark energy descends from the heavens,\n "
                + "its ominous presence creating an otherworldly tempest. The once-clear skies are now obscured by thick, foreboding clouds,\n"
                + " and the once-proud city is plunged into chaos.\n\n"
                + "Piercing screams of terror pierce the air as innocent citizens flee for their lives, their homes reduced to ashes before their eyes.\n"
                + " You and Finrod stand at the edge of the darkened forest, frozen in dread as the malevolent force continues its relentless descent upon"
                + " the heart of your kingdom.\n"
                + "\n"
                + "In the midst of the chaos and devastation, you sense that something unspeakable has been awakened, and your fate,\n"
                + " along with that of Eternia, hangs precariously in the balance.\n");

    }

    private static void firstChoiceText(){
        System.out.println("\n\nAfter triumphing over these malevolent creatures, \n"
               + " you look at your lost friend's body and realise a hard choice lies ahead. \n" 
               + " Will you venture to your residence, where your beloved wife Elwyn may be in grave danger, \n" 
               + " in a valiant attempt to render assistance? \n" 
               + "Or will you follow the rational course of action, as prescribed by your guard protocol, \n" 
               + " by heading to the outpost to seek your commander's orders on protecting the city from these vile threats?");

    }

}
