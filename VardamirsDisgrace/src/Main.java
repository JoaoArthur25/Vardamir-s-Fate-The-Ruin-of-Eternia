import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.lang.Thread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int playerAction = 0;
        int computerAction = 0;

        beforeFirstBattleTale();

        Character player = createCharacter(scanner);

        System.out.println(player.getCharacter());

        Character diabrete = new Character("Diabrete", 2, 33, 5, 5, new Weapon(Weapon.DAGGER), null,
                new Potion(Potion.SMALL));
        Character aranha = new Character("Aranha", 5, 34, 3, 3, new Weapon(Weapon.DAGGER), null,
                new Potion(Potion.SMALL));
        Character esqueleto = new Character("Esqueleto", 36, 5, 2, 2, new Weapon(Weapon.LONG_SWORD), null,
                new Potion(Potion.MEDIUM));

        int enemyChoice = random.nextInt(3);
        Character enemy = null;
        System.out.println(enemyChoice);
        if (enemyChoice == 0) {
            enemy = diabrete;
        } else if (enemyChoice == 1) {
            enemy = aranha;
        } else {
            enemy = esqueleto;
        }

        System.out.println("\n" + enemy.toString() + "\n\n");
        boolean coldUsed = false;
        boolean shockUsed = false;
        boolean necroticUsed = false;

        // clrscr();

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println(player.toString());
            System.out.println(enemy.toString());
            System.out.println(player.getArmor().getDefence());
            player.getArmor().setDefence();
            System.out.println("Fire Effect turns:" + enemy.getFireEffectTurns());
            if (enemy.getFireEffectTurns() > 0) {
                    enemy.applyFireEffect();
                }

                if (enemy.getPoisonEffectTurns() >= 0) {
                    enemy.applyPoisonEffect();
                }

                if (player.getColdEffectTurns() <= 0) {
                    enemy.removeColdEffect();
                    coldUsed = false;
                    player.setColdEffectTurns();
                }

                if (enemy.getFireEffectTurns() > 0) {
                    player.applyFireEffect();
                }

                if (enemy.getPoisonEffectTurns() > 0) {
                    player.applyPoisonEffect();
                }

                if (enemy.getColdEffectTurns() <= 0) {
                    player.removeColdEffect();
                    coldUsed = false;
                    enemy.setColdEffectTurns();
                }

                if (player.getShockEffectTurns() <= 0) {
                    enemy.addDexterity(2);
                    shockUsed = false;
                    player.setShockEffectTurns();
                }

                if (enemy.getShockEffectTurns() <= 0) {
                    player.addDexterity(2);
                    shockUsed = false;
                    enemy.setShockEffectTurns();
                }
            if (player.getAgility() > enemy.getAgility()) {
                playerAction = getPlayerAction(scanner);
                
                switch (playerAction) {
                    case 0:
                        System.out.println();
                        int playerDamage = player.calculateDamage();
                        int damageDealt = playerDamage;

                        if (damageDealt > 0) {
                            System.out.println(
                                    player.getName() + " attacks " + enemy.getName() + " for " + playerDamage
                                            + " damage.");
                            enemy.receiveDamage(damageDealt);
                        } else {
                            System.out
                                    .println(player.getName() + " attacks " + enemy.getName() + " but does no damage.");
                        }
                        // System.out.println(enemy.toString() + "\n\n");

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
                            System.out.println(coldUsed);
                        }

                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                        // System.out.println(enemy.toString() + "\n\n");

                        break;

                    case 2:
                        player.getArmor().doubleDefence();
                        System.out.println(player.getName() + " doubles their defense for 1 round.");
                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                        // System.out.println(enemy.toString() + "\n\n");

                        break;

                    case 3:
                        player.heal();
                        System.out
                                .println(player.getName() + " uses a potion and recovers " + player.getHeal() + " HP.");
                        System.out.println(player.getArmor().getDefence());
                        // System.out.println(enemy.toString() + "\n\n");

                        break;
                }

                if (shockUsed) {
                    player.removeShockEffectTurns();
                }
                if (coldUsed) {
                    player.removeColdEffectTurns();

                }
                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                System.out.println(coldUsed);
                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                System.out.println("------");

                if (enemy.isAlive()) {
                    computerAction = random.nextInt(2);
                    
                    switch (computerAction) {

                        case 0:
                            int enemyDamage = enemy.calculateDamage();
                            int playerDefense = player.getArmor().setDefence();
                            int damageDealt = enemyDamage - playerDefense;

                            if (damageDealt > 0) {
                                System.out.println(
                                        enemy.getName() + " attacks " + player.getName() + " for " + enemyDamage
                                                + " damage.");
                                player.receiveDamage(damageDealt);
                            } else {
                                System.out.println(
                                        enemy.getName() + " attacks " + player.getName() + " but does no damage.");
                            }
                            // System.out.println(player.toString() + "\n\n");
                            break;

                        case 1:
                            enemy.heal(enemy.getHeal());
                            System.out.println(
                                    enemy.getName() + " uses a potion and recovers " + enemy.getHeal() + " HP.");
                            // System.out.println(player.toString());
                            break;

                    }

                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                    System.out.println("------");
                }
            } else {
                computerAction = random.nextInt(2);
                
                switch (computerAction) {

                    case 0:
                        int enemyDamage = enemy.calculateDamage();
                        int playerDefense = player.getArmor().setDefence();
                        int damageDealt = enemyDamage - playerDefense;

                        if (damageDealt > 0) {
                            System.out.println(
                                    enemy.getName() + " attacks " + player.getName() + " for " + enemyDamage
                                            + " damage.");
                            player.receiveDamage(damageDealt);
                        } else {
                            System.out
                                    .println(enemy.getName() + " attacks " + player.getName() + " but does no damage.");
                        }
                        // System.out.println(player.toString() + "\n\n");
                        break;

                    case 1:
                        enemy.heal(enemy.getHeal());
                        System.out.println(enemy.getName() + " uses a potion and recovers " + enemy.getHeal() + " HP.");
                        // System.out.println(player.toString());
                        break;

                }

                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                System.out.println("------");

                if (player.isAlive()) {
                    playerAction = getPlayerAction(scanner);
                    System.out.println(player.getArmor().getDefence());
                    player.getArmor().setDefence();
                   
                    switch (playerAction) {
                        case 0:
                            System.out.println();
                            int playerDamage = player.calculateDamage();
                            int damageDealt = playerDamage;

                            if (damageDealt > 0) {
                                System.out.println(
                                        player.getName() + " attacks " + enemy.getName() + " for " + playerDamage
                                                + " damage.");
                                enemy.receiveDamage(damageDealt);
                            } else {
                                System.out
                                        .println(player.getName() + " attacks " + enemy.getName()
                                                + " but does no damage.");
                            }
                            // System.out.println(enemy.toString() + "\n\n");

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
                                System.out.println(coldUsed);
                            }

                            System.out.println(player.getMagic(magicChoice).toString() + " used.");

                            player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                            // System.out.println(enemy.toString() + "\n\n");

                            break;

                        case 2:
                            player.getArmor().doubleDefence();
                            System.out.println(player.getName() + " doubles their defense for 1 round.");
                            System.out.println("Defesa atual: " + player.getArmor().getDefence());
                            // System.out.println(enemy.toString() + "\n\n");

                            break;

                        case 3:
                            player.heal();
                            System.out
                                    .println(player.getName() + " uses a potion and recovers " + player.getHeal()
                                            + " HP.");
                            System.out.println(player.getArmor().getDefence());
                            // System.out.println(enemy.toString() + "\n\n");

                            break;
                    }

                    if (shockUsed) {
                        player.removeShockEffectTurns();
                    }
                    if (coldUsed) {
                        player.removeColdEffectTurns();

                    }
                    System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                    System.out.println(coldUsed);
                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                    System.out.println("------");
                }
            }
        }

        // Thread.sleep(2000);

        // clrscr();

        if (player.isAlive()) {
            firstChoiceText();
            System.out.println("1. Go to your house");
            System.out.println("2. Go to the outpost");
            int choice = getIntInput(scanner);
            while (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please choose a valid action (1-2).");
                System.out.print("Enter your choice (1-2): ");
                choice = getIntInput(scanner);
            }
            if (choice == 1) {
                searchWifeChoice();
                System.out.println("1. Save your wife");
                System.out.println("2. Save the triplets");
                choice = getIntInput(scanner);

                while (choice < 1 || choice > 2) {
                    System.out.println("Invalid choice. Please choose a valid action (1-2).");
                    System.out.print("Enter your choice (1-2): ");
                    choice = getIntInput(scanner);
                }

                if (choice == 1) {
                    Character nidere = new Character("Nidere", 5, 5, 5, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    Character specter = new Character("Specter", 4, 8, 3, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    createMagic(5, specter);
                    createMagic(5, nidere);

                    enemyChoice = random.nextInt(2);
                    enemy = null;
                    System.out.println(enemyChoice);
                    if (enemyChoice == 0) {
                        enemy = nidere;
                    } else {
                        enemy = specter;
                    }

                    System.out.println("\n" + enemy.toString() + "\n\n");
                    coldUsed = false;
                    shockUsed = false;
                    necroticUsed = false;

                    while (player.isAlive() && enemy.isAlive()) {
                        if (enemy.getFireEffectTurns() > 0) {
                            enemy.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            enemy.applyPoisonEffect();
                        }

                        if (player.getColdEffectTurns() <= 0) {
                            enemy.removeColdEffect();
                            coldUsed = false;
                            player.setColdEffectTurns();
                        }

                        if (enemy.getFireEffectTurns() > 0) {
                            player.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            player.applyPoisonEffect();
                        }

                        if (enemy.getColdEffectTurns() <= 0) {
                            player.removeColdEffect();
                            coldUsed = false;
                            enemy.setColdEffectTurns();
                        }

                        if (enemy.getNecroticEffectTurns() <= 0) {
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            enemy.setNecroticEffectTurns();
                        }

                        if (player.getShockEffectTurns() <= 0) {
                            enemy.addDexterity(2);
                            shockUsed = false;
                            player.setShockEffectTurns();
                        }

                        if (enemy.getShockEffectTurns() <= 0) {
                            player.addDexterity(2);
                            shockUsed = false;
                            enemy.setShockEffectTurns();
                        }
                        System.out.println(player.toString());
                        System.out.println(enemy.toString());
                        System.out.println(player.getArmor().getDefence());
                        player.getArmor().setDefence();
                        if (player.getAgility() > enemy.getAgility()) {
                            playerAction = getPlayerAction(scanner);
                            switch (playerAction) {
                                case 0:
                                    System.out.println();
                                    int playerDamage = player.calculateDamage();
                                    int damageDealt = playerDamage;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                player.getName() + " attacks " + enemy.getName() + " for "
                                                        + playerDamage
                                                        + " damage.");
                                        enemy.receiveDamage(damageDealt);
                                    } else {
                                        System.out
                                                .println(player.getName() + " attacks " + enemy.getName()
                                                        + " but does no damage.");
                                    }
                                    // System.out.println(enemy.toString() + "\n\n");

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
                                        System.out.println(coldUsed);
                                    }

                                    System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                    player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 2:
                                    player.getArmor().doubleDefence();
                                    System.out.println(player.getName() + " doubles their defense for 1 round.");
                                    System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 3:
                                    player.heal();
                                    System.out
                                            .println(player.getName() + " uses a potion and recovers "
                                                    + player.getHeal() + " HP.");
                                    System.out.println(player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;
                            }

                            if (shockUsed) {
                                player.removeShockEffectTurns();
                            }
                            if (coldUsed) {
                                player.removeColdEffectTurns();

                            }
                            System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                            System.out.println(coldUsed);
                            System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (enemy.isAlive()) {
                                computerAction = random.nextInt(3);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            System.out.println("Dano dado: " + damageDealt);
                                            System.out.println("Defesa atual: " + playerDefense);
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 2:

                                        necroticUsed = true;

                                        System.out.println(enemy.getMagic(0).toString() + " used.");

                                        player.castMagic(player, 0, enemy.getMagic(0));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");
                                }

                                System.out.println(player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                            }
                        } else {
                            computerAction = random.nextInt(3);
                            switch (computerAction) {
                                case 0:
                                    int enemyDamage = enemy.calculateDamage();
                                    int playerDefense = player.getArmor().setDefence();
                                    int damageDealt = enemyDamage - playerDefense;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                enemy.getName() + " attacks " + player.getName() + " for "
                                                        + enemyDamage + " damage.");
                                        System.out.println("Dano dado: " + damageDealt);
                                        System.out.println("Defesa atual: " + playerDefense);
                                        player.receiveDamage(damageDealt);
                                    } else {
                                        System.out.println(enemy.getName() + " attacks " + player.getName()
                                                + " but does no damage.");
                                    }
                                    break;

                                case 1:
                                    enemy.heal(enemy.getHeal());
                                    System.out.println(enemy.getName() + " uses a potion and recovers "
                                            + enemy.getHeal() + " HP.");
                                    break;

                                case 2:

                                    necroticUsed = true;

                                    System.out.println(enemy.getMagic(0).toString() + " used.");

                                    player.castMagic(player, 0, enemy.getMagic(0));
                                    System.out.println(player.toString() + "\n\n");
                                    System.out.println(enemy.toString() + "\n\n");
                            }

                            System.out.println(player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (necroticUsed) {
                                enemy.removeNecroticEffectTurns();
                            }
                            if (player.isAlive()) {

                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out.println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");
                            }
                        }
                    }
                    if (player.isAlive()) {
                        saveWifeChoice();
                        findingVardamir();
                        Character vardamir = new Character("Vardamir", 10, 10, 5, 5, new Weapon(Weapon.LONG_SWORD),
                                null, new Potion(Potion.LARGE));
                        createMagic(1, vardamir);
                        createMagic(2, vardamir);
                        createMagic(3, vardamir);
                        createMagic(4, vardamir);
                        createMagic(5, vardamir);
                        createMagic(6, vardamir);
                        vardamir.setArmor(new Armor(Armor.LIGHT, vardamir));

                        enemy = vardamir;

                        System.out.println("\n" + enemy.toString() + "\n\n");
                        coldUsed = false;
                        shockUsed = false;
                        necroticUsed = false;

                        while (player.isAlive() && enemy.isAlive()) {
                            if (enemy.getFireEffectTurns() > 0) {
                                enemy.applyFireEffect();
                            }

                            if (enemy.getPoisonEffectTurns() > 0) {
                                enemy.applyPoisonEffect();
                            }

                            if(player.getPoisonEffectTurns() > 0){
                                player.applyPoisonEffect();
                            }

                            if(player.getFireEffectTurns() > 0){
                                player.applyFireEffect();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                enemy.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (enemy.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                enemy.setColdEffectTurns();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (player.getShockEffectTurns() <= 0) {
                                enemy.addDexterity(2);
                                shockUsed = false;
                                player.setShockEffectTurns();
                            }

                            if (enemy.getShockEffectTurns() <= 0) {
                                player.addDexterity(2);
                                shockUsed = false;
                                enemy.setShockEffectTurns();
                            }

                            if (player.getNecroticEffectTurns() <= 0) {
                                enemy.removeNecroticEffect();
                                necroticUsed = false;
                                player.setNecroticEffectTurns();
                            }

                            if (enemy.getNecroticEffectTurns() <= 0) {
                                player.removeNecroticEffect();
                                necroticUsed = false;
                                enemy.setNecroticEffectTurns();
                            }

                            System.out.println(player.toString());
                            System.out.println(enemy.toString());
                            System.out.println(player.getArmor().getDefence());
                            player.getArmor().setDefence();
                            if (player.getAgility() > enemy.getAgility()) {
                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out
                                                .println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (enemy.isAlive()) {
                                    computerAction = random.nextInt(4);
                                    switch (computerAction) {
                                        case 0:
                                            int enemyDamage = enemy.calculateDamage();
                                            int playerDefense = player.getArmor().setDefence();
                                            int damageDealt = enemyDamage - playerDefense;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        enemy.getName() + " attacks " + player.getName() + " for "
                                                                + enemyDamage + " damage.");
                                                player.receiveDamage(damageDealt);
                                            } else {
                                                System.out.println(enemy.getName() + " attacks " + player.getName()
                                                        + " but does no damage.");
                                            }
                                            break;

                                        case 1:

                                            int magicChoice = random.nextInt(6);

                                            String magicName = player.getMagic(magicChoice).toString();

                                            if (magicName.equals("Eletric Magic")) {
                                                shockUsed = true;
                                            }

                                            if (magicName.equals("Ice Magic")) {
                                                coldUsed = true;
                                            }

                                            if (magicName.equals("Necrotic Magic")) {
                                                necroticUsed = true;
                                            }

                                            System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                            System.out.println(player.toString() + "\n\n");
                                            System.out.println(enemy.toString() + "\n\n");

                                        case 2:
                                            enemy.heal(enemy.getHeal());
                                            System.out.println(enemy.getName() + " uses a potion and recovers "
                                                    + enemy.getHeal() + " HP.");
                                            break;

                                        case 3:

                                            enemy.getArmor().doubleDefence();
                                            System.out.println(
                                                    enemy.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                            System.out.println(player.toString() + "\n\n");

                                            break;

                                    }

                                    if (necroticUsed) {
                                        enemy.removeNecroticEffectTurns();
                                    }
                                    if (shockUsed) {
                                        enemy.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        enemy.removeColdEffectTurns();
                                    }
                                }
                            } else {
                                computerAction = random.nextInt(4);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:

                                        int magicChoice = random.nextInt(6);

                                        String magicName = player.getMagic(magicChoice).toString();

                                        if (magicName.equals("Eletric Magic")) {
                                            shockUsed = true;
                                        }

                                        if (magicName.equals("Ice Magic")) {
                                            coldUsed = true;
                                        }

                                        if (magicName.equals("Necrotic Magic")) {
                                            necroticUsed = true;
                                        }

                                        System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");

                                    case 2:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 3:

                                        enemy.getArmor().doubleDefence();
                                        System.out.println(enemy.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                        System.out.println(player.toString() + "\n\n");

                                        break;

                                }

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                                if (shockUsed) {
                                    enemy.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    enemy.removeColdEffectTurns();
                                }
                                if (player.isAlive()) {

                                    playerAction = getPlayerAction(scanner);
                                    switch (playerAction) {
                                        case 0:
                                            System.out.println();
                                            int playerDamage = player.calculateDamage();
                                            int damageDealt = playerDamage;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        player.getName() + " attacks " + enemy.getName() + " for "
                                                                + playerDamage
                                                                + " damage.");
                                                enemy.receiveDamage(damageDealt);
                                            } else {
                                                System.out
                                                        .println(player.getName() + " attacks " + enemy.getName()
                                                                + " but does no damage.");
                                            }
                                            // System.out.println(enemy.toString() + "\n\n");

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
                                                System.out.println(coldUsed);
                                            }

                                            System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 2:
                                            player.getArmor().doubleDefence();
                                            System.out
                                                    .println(player.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 3:
                                            player.heal();
                                            System.out
                                                    .println(player.getName() + " uses a potion and recovers "
                                                            + player.getHeal() + " HP.");
                                            System.out.println(player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;
                                    }

                                    if (shockUsed) {
                                        player.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        player.removeColdEffectTurns();

                                    }
                                    System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                    System.out.println(coldUsed);
                                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                    System.out.println("------");
                                }
                            }
                        }

                        if (player.isAlive()) {
                            afterFinalBattleText();
                            vardamirPerspectiveFinal();
                            ifTheWifeWasSavedFinal();
                        } else if (enemy.isAlive()) {
                            System.out.println("You are dead!");
                        } else {
                            System.out.println("You are dead!");
                        }

                    } else if (enemy.isAlive()) {
                        System.out.println("You are dead!");
                    } else {
                        System.out.println("You are dead!");
                    }

                } else if (choice == 2) {
                    Character nidere = new Character("Nidere", 5, 5, 5, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    Character specter = new Character("Specter", 4, 8, 3, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    createMagic(5, specter);
                    createMagic(5, nidere);

                    enemyChoice = random.nextInt(2);
                    enemy = null;
                    System.out.println(enemyChoice);
                    if (enemyChoice == 0) {
                        enemy = nidere;
                    } else {
                        enemy = specter;
                    }

                    System.out.println("\n" + enemy.toString() + "\n\n");
                    coldUsed = false;
                    shockUsed = false;
                    necroticUsed = false;

                    while (player.isAlive() && enemy.isAlive()) {
                        if (enemy.getFireEffectTurns() > 0) {
                            enemy.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            enemy.applyPoisonEffect();
                        }

                        if (player.getColdEffectTurns() <= 0) {
                            enemy.removeColdEffect();
                            coldUsed = false;
                            player.setColdEffectTurns();
                        }

                        if (enemy.getColdEffectTurns() <= 0) {
                            player.removeColdEffect();
                            coldUsed = false;
                            enemy.setColdEffectTurns();
                        }

                        if (enemy.getNecroticEffectTurns() <= 0) {
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            enemy.setNecroticEffectTurns();
                        }

                        if (player.getNecroticEffectTurns() <= 0) {
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            enemy.setNecroticEffectTurns();
                        }

                        if (player.getShockEffectTurns() <= 0) {
                            enemy.addDexterity(2);
                            shockUsed = false;
                            player.setShockEffectTurns();
                        }

                        if (enemy.getShockEffectTurns() <= 0) {
                            player.addDexterity(2);
                            shockUsed = false;
                            enemy.setShockEffectTurns();
                        }
                        System.out.println(player.toString());
                        System.out.println(enemy.toString());
                        System.out.println(player.getArmor().getDefence());
                        player.getArmor().setDefence();
                        if (player.getAgility() > enemy.getAgility()) {
                            playerAction = getPlayerAction(scanner);
                            switch (playerAction) {
                                case 0:
                                    System.out.println();
                                    int playerDamage = player.calculateDamage();
                                    int damageDealt = playerDamage;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                player.getName() + " attacks " + enemy.getName() + " for "
                                                        + playerDamage
                                                        + " damage.");
                                        enemy.receiveDamage(damageDealt);
                                    } else {
                                        System.out
                                                .println(player.getName() + " attacks " + enemy.getName()
                                                        + " but does no damage.");
                                    }
                                    // System.out.println(enemy.toString() + "\n\n");

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
                                        System.out.println(coldUsed);
                                    }

                                    System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                    player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 2:
                                    player.getArmor().doubleDefence();
                                    System.out.println(player.getName() + " doubles their defense for 1 round.");
                                    System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 3:
                                    player.heal();
                                    System.out
                                            .println(player.getName() + " uses a potion and recovers "
                                                    + player.getHeal() + " HP.");
                                    System.out.println(player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;
                            }

                            if (shockUsed) {
                                player.removeShockEffectTurns();
                            }
                            if (coldUsed) {
                                player.removeColdEffectTurns();

                            }
                            System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                            System.out.println(coldUsed);
                            System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (enemy.isAlive()) {
                                computerAction = random.nextInt(3);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            System.out.println("Dano dado: " + damageDealt);
                                            System.out.println("Defesa atual: " + playerDefense);
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 2:

                                        necroticUsed = true;

                                        System.out.println(enemy.getMagic(0).toString() + " used.");

                                        player.castMagic(player, 0, enemy.getMagic(0));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");
                                }

                                System.out.println(player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                            }
                        } else {
                            computerAction = random.nextInt(3);
                            switch (computerAction) {
                                case 0:
                                    int enemyDamage = enemy.calculateDamage();
                                    int playerDefense = player.getArmor().setDefence();
                                    int damageDealt = enemyDamage - playerDefense;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                enemy.getName() + " attacks " + player.getName() + " for "
                                                        + enemyDamage + " damage.");
                                        System.out.println("Dano dado: " + damageDealt);
                                        System.out.println("Defesa atual: " + playerDefense);
                                        player.receiveDamage(damageDealt);
                                    } else {
                                        System.out.println(enemy.getName() + " attacks " + player.getName()
                                                + " but does no damage.");
                                    }
                                    break;

                                case 1:
                                    enemy.heal(enemy.getHeal());
                                    System.out.println(enemy.getName() + " uses a potion and recovers "
                                            + enemy.getHeal() + " HP.");
                                    break;

                                case 2:

                                    necroticUsed = true;

                                    System.out.println(enemy.getMagic(0).toString() + " used.");

                                    player.castMagic(player, 0, enemy.getMagic(0));
                                    System.out.println(player.toString() + "\n\n");
                                    System.out.println(enemy.toString() + "\n\n");
                            }

                            System.out.println(player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (necroticUsed) {
                                enemy.removeNecroticEffectTurns();
                            }

                            if (player.isAlive()) {

                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out.println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");
                            }
                        }
                    }

                    if (player.isAlive()) {
                        saveTripletsChoice();
                        findingVardamir();

                        Character vardamir = new Character("Vardamir", 10, 10, 5, 5, new Weapon(Weapon.LONG_SWORD),
                                null, new Potion(Potion.LARGE));
                        vardamir.setArmor(new Armor(Armor.LIGHT, vardamir));
                        createMagic(1, vardamir);
                        createMagic(2, vardamir);
                        createMagic(3, vardamir);
                        createMagic(4, vardamir);
                        createMagic(5, vardamir);
                        createMagic(6, vardamir);

                        enemy = vardamir;

                        System.out.println("\n" + enemy.toString() + "\n\n");
                        coldUsed = false;
                        shockUsed = false;
                        necroticUsed = false;

                        while (player.isAlive() && enemy.isAlive()) {
                            if (enemy.getFireEffectTurns() > 0) {
                                enemy.applyFireEffect();
                            }

                            if (enemy.getPoisonEffectTurns() > 0) {
                                enemy.applyPoisonEffect();
                            }

                            if(player.getPoisonEffectTurns() > 0){
                                player.applyPoisonEffect();
                            }

                            if(player.getFireEffectTurns() > 0){
                                player.applyFireEffect();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                enemy.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (enemy.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                enemy.setColdEffectTurns();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (player.getShockEffectTurns() <= 0) {
                                enemy.addDexterity(2);
                                shockUsed = false;
                                player.setShockEffectTurns();
                            }

                            if (enemy.getShockEffectTurns() <= 0) {
                                player.addDexterity(2);
                                shockUsed = false;
                                enemy.setShockEffectTurns();
                            }

                           if(player.getNecroticEffectTurns() <= 0){
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            player.setNecroticEffectTurns();
                        }
                            System.out.println(player.toString());
                            System.out.println(enemy.toString());
                            System.out.println(player.getArmor().getDefence());
                            player.getArmor().setDefence();
                            if (player.getAgility() > enemy.getAgility()) {
                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out
                                                .println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (enemy.isAlive()) {
                                    computerAction = random.nextInt(4);
                                    switch (computerAction) {
                                        case 0:
                                            int enemyDamage = enemy.calculateDamage();
                                            int playerDefense = player.getArmor().setDefence();
                                            int damageDealt = enemyDamage - playerDefense;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        enemy.getName() + " attacks " + player.getName() + " for "
                                                                + enemyDamage + " damage.");
                                                player.receiveDamage(damageDealt);
                                            } else {
                                                System.out.println(enemy.getName() + " attacks " + player.getName()
                                                        + " but does no damage.");
                                            }
                                            break;

                                        case 1:

                                            int magicChoice = random.nextInt(6);

                                            String magicName = player.getMagic(magicChoice).toString();

                                            if (magicName.equals("Eletric Magic")) {
                                                shockUsed = true;
                                            }

                                            if (magicName.equals("Ice Magic")) {
                                                coldUsed = true;
                                            }

                                            if (magicName.equals("Necrotic Magic")) {
                                                necroticUsed = true;
                                            }

                                            System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                            System.out.println(player.toString() + "\n\n");
                                            System.out.println(enemy.toString() + "\n\n");

                                        case 2:
                                            enemy.heal(enemy.getHeal());
                                            System.out.println(enemy.getName() + " uses a potion and recovers "
                                                    + enemy.getHeal() + " HP.");
                                            break;

                                        case 3:

                                            enemy.getArmor().doubleDefence();
                                            System.out.println(
                                                    enemy.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                            System.out.println(player.toString() + "\n\n");

                                            break;

                                    }

                                    if (necroticUsed) {
                                        enemy.removeNecroticEffectTurns();
                                    }
                                    if (shockUsed) {
                                        enemy.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        enemy.removeColdEffectTurns();
                                    }
                                }
                            } else {
                                computerAction = random.nextInt(4);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:

                                        int magicChoice = random.nextInt(6);

                                        String magicName = player.getMagic(magicChoice).toString();

                                        if (magicName.equals("Eletric Magic")) {
                                            shockUsed = true;
                                        }

                                        if (magicName.equals("Ice Magic")) {
                                            coldUsed = true;
                                        }

                                        if (magicName.equals("Necrotic Magic")) {
                                            necroticUsed = true;
                                        }

                                        System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");

                                    case 2:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 3:

                                        enemy.getArmor().doubleDefence();
                                        System.out.println(enemy.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                        System.out.println(player.toString() + "\n\n");

                                        break;

                                }

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                                if (shockUsed) {
                                    enemy.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    enemy.removeColdEffectTurns();
                                }

                                if (player.isAlive()) {

                                    playerAction = getPlayerAction(scanner);
                                    switch (playerAction) {
                                        case 0:
                                            System.out.println();
                                            int playerDamage = player.calculateDamage();
                                            int damageDealt = playerDamage;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        player.getName() + " attacks " + enemy.getName() + " for "
                                                                + playerDamage
                                                                + " damage.");
                                                enemy.receiveDamage(damageDealt);
                                            } else {
                                                System.out
                                                        .println(player.getName() + " attacks " + enemy.getName()
                                                                + " but does no damage.");
                                            }
                                            // System.out.println(enemy.toString() + "\n\n");

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
                                                System.out.println(coldUsed);
                                            }

                                            System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 2:
                                            player.getArmor().doubleDefence();
                                            System.out
                                                    .println(player.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 3:
                                            player.heal();
                                            System.out
                                                    .println(player.getName() + " uses a potion and recovers "
                                                            + player.getHeal() + " HP.");
                                            System.out.println(player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;
                                    }

                                    if (shockUsed) {
                                        player.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        player.removeColdEffectTurns();

                                    }
                                    System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                    System.out.println(coldUsed);
                                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                    System.out.println("------");
                                }
                            }
                        }

                        if (player.isAlive()) {
                            afterFinalBattleText();
                            vardamirPerspectiveFinal();
                            ifTheTripletsWereSavedFinal();
                        } else if (enemy.isAlive()) {
                            System.out.println("You are dead!");
                        } else {
                            System.out.println("You are dead!");
                        }

                    } else if (enemy.isAlive()) {
                        System.out.println("You are dead!");
                    } else {
                        System.out.println("You are dead!");
                    }

                }
            } else if (choice == 2) {
                goToOutpostChoice();
                System.out.println("1. Go to the master's room");
                System.out.println("2. Go directaly the heart of the city");
                choice = getIntInput(scanner);

                while (choice < 1 || choice > 2) {
                    System.out.println("Invalid choice. Please choose a valid action (1-2).");
                    System.out.print("Enter your choice (1-2): ");
                    choice = getIntInput(scanner);
                }

                if (choice == 1) {
                    goSearchTheMasterChoice();
                    Character nidere = new Character("Nidere", 5, 5, 5, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    Character specter = new Character("Specter", 4, 8, 3, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    createMagic(5, specter);
                    createMagic(5, nidere);

                    enemyChoice = random.nextInt(2);
                    enemy = null;
                    System.out.println(enemyChoice);
                    if (enemyChoice == 0) {
                        enemy = nidere;
                    } else {
                        enemy = specter;
                    }

                    System.out.println("\n" + enemy.toString() + "\n\n");
                    coldUsed = false;
                    shockUsed = false;
                    necroticUsed = false;

                    while (player.isAlive() && enemy.isAlive()) {
                        if (enemy.getFireEffectTurns() > 0) {
                            enemy.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            enemy.applyPoisonEffect();
                        }

                        if (player.getColdEffectTurns() <= 0) {
                            enemy.removeColdEffect();
                            coldUsed = false;
                            player.setColdEffectTurns();
                        }

                        if (enemy.getFireEffectTurns() > 0) {
                            player.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            player.applyPoisonEffect();
                        }

                        if (enemy.getColdEffectTurns() <= 0) {
                            player.removeColdEffect();
                            coldUsed = false;
                            enemy.setColdEffectTurns();
                        }

                        if(player.getNecroticEffectTurns() <= 0){
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            player.setNecroticEffectTurns();
                        }

                        if (player.getShockEffectTurns() <= 0) {
                            enemy.addDexterity(2);
                            shockUsed = false;
                            player.setShockEffectTurns();
                        }

                        if (enemy.getShockEffectTurns() <= 0) {
                            player.addDexterity(2);
                            shockUsed = false;
                            enemy.setShockEffectTurns();
                        }
                        System.out.println(player.toString());
                        System.out.println(enemy.toString());
                        System.out.println(player.getArmor().getDefence());
                        player.getArmor().setDefence();
                        if (player.getAgility() > enemy.getAgility()) {
                            playerAction = getPlayerAction(scanner);
                            switch (playerAction) {
                                case 0:
                                    System.out.println();
                                    int playerDamage = player.calculateDamage();
                                    int damageDealt = playerDamage;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                player.getName() + " attacks " + enemy.getName() + " for "
                                                        + playerDamage
                                                        + " damage.");
                                        enemy.receiveDamage(damageDealt);
                                    } else {
                                        System.out
                                                .println(player.getName() + " attacks " + enemy.getName()
                                                        + " but does no damage.");
                                    }
                                    // System.out.println(enemy.toString() + "\n\n");

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
                                        System.out.println(coldUsed);
                                    }

                                    System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                    player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 2:
                                    player.getArmor().doubleDefence();
                                    System.out.println(player.getName() + " doubles their defense for 1 round.");
                                    System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 3:
                                    player.heal();
                                    System.out
                                            .println(player.getName() + " uses a potion and recovers "
                                                    + player.getHeal() + " HP.");
                                    System.out.println(player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;
                            }

                            if (shockUsed) {
                                player.removeShockEffectTurns();
                            }
                            if (coldUsed) {
                                player.removeColdEffectTurns();

                            }
                            System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                            System.out.println(coldUsed);
                            System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (enemy.isAlive()) {
                                computerAction = random.nextInt(3);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            System.out.println("Dano dado: " + damageDealt);
                                            System.out.println("Defesa atual: " + playerDefense);
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 2:

                                        necroticUsed = true;

                                        System.out.println(enemy.getMagic(0).toString() + " used.");

                                        player.castMagic(player, 0, enemy.getMagic(0));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");
                                }

                                System.out.println(player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                            }
                        } else {
                            computerAction = random.nextInt(3);
                            switch (computerAction) {
                                case 0:
                                    int enemyDamage = enemy.calculateDamage();
                                    int playerDefense = player.getArmor().setDefence();
                                    int damageDealt = enemyDamage - playerDefense;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                enemy.getName() + " attacks " + player.getName() + " for "
                                                        + enemyDamage + " damage.");
                                        System.out.println("Dano dado: " + damageDealt);
                                        System.out.println("Defesa atual: " + playerDefense);
                                        player.receiveDamage(damageDealt);
                                    } else {
                                        System.out.println(enemy.getName() + " attacks " + player.getName()
                                                + " but does no damage.");
                                    }
                                    break;

                                case 1:
                                    enemy.heal(enemy.getHeal());
                                    System.out.println(enemy.getName() + " uses a potion and recovers "
                                            + enemy.getHeal() + " HP.");
                                    break;

                                case 2:

                                    necroticUsed = true;

                                    System.out.println(enemy.getMagic(0).toString() + " used.");

                                    player.castMagic(player, 0, enemy.getMagic(0));
                                    System.out.println(player.toString() + "\n\n");
                                    System.out.println(enemy.toString() + "\n\n");
                            }

                            System.out.println(player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (necroticUsed) {
                                enemy.removeNecroticEffectTurns();
                            }
                            if (player.isAlive()) {

                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out.println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");
                            }
                        }
                    }
                    if (player.isAlive()) {
                        afterReachingMaster();
                        findingVardamir();
                        Character vardamir = new Character("Vardamir", 10, 10, 5, 5, new Weapon(Weapon.LONG_SWORD),
                                null, new Potion(Potion.LARGE));
                        vardamir.setArmor(new Armor(Armor.LIGHT, vardamir));
                        createMagic(1, vardamir);
                        createMagic(2, vardamir);
                        createMagic(3, vardamir);
                        createMagic(4, vardamir);
                        createMagic(5, vardamir);
                        createMagic(6, vardamir);

                        enemy = vardamir;

                        System.out.println("\n" + enemy.toString() + "\n\n");
                        coldUsed = false;
                        shockUsed = false;
                        necroticUsed = false;

                        while (player.isAlive() && enemy.isAlive()) {
                             if (enemy.getFireEffectTurns() > 0) {
                                enemy.applyFireEffect();
                            }

                            if (enemy.getPoisonEffectTurns() > 0) {
                                enemy.applyPoisonEffect();
                            }

                            if(player.getPoisonEffectTurns() > 0){
                                player.applyPoisonEffect();
                            }

                            if(player.getFireEffectTurns() > 0){
                                player.applyFireEffect();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                enemy.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (enemy.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                enemy.setColdEffectTurns();
                            }

                            if (player.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                player.setColdEffectTurns();
                            }

                            if (player.getShockEffectTurns() <= 0) {
                                enemy.addDexterity(2);
                                shockUsed = false;
                                player.setShockEffectTurns();
                            }

                            if (enemy.getShockEffectTurns() <= 0) {
                                player.addDexterity(2);
                                shockUsed = false;
                                enemy.setShockEffectTurns();
                            }

                            if(player.getNecroticEffectTurns() <= 0){
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            player.setNecroticEffectTurns();
                        }
                            System.out.println(player.toString());
                            System.out.println(enemy.toString());
                            System.out.println(player.getArmor().getDefence());
                            player.getArmor().setDefence();
                            if (player.getAgility() > enemy.getAgility()) {
                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out
                                                .println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (enemy.isAlive()) {
                                    computerAction = random.nextInt(4);
                                    switch (computerAction) {
                                        case 0:
                                            int enemyDamage = enemy.calculateDamage();
                                            int playerDefense = player.getArmor().setDefence();
                                            int damageDealt = enemyDamage - playerDefense;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        enemy.getName() + " attacks " + player.getName() + " for "
                                                                + enemyDamage + " damage.");
                                                player.receiveDamage(damageDealt);
                                            } else {
                                                System.out.println(enemy.getName() + " attacks " + player.getName()
                                                        + " but does no damage.");
                                            }
                                            break;

                                        case 1:

                                            int magicChoice = random.nextInt(6);

                                            String magicName = player.getMagic(magicChoice).toString();

                                            if (magicName.equals("Eletric Magic")) {
                                                shockUsed = true;
                                            }

                                            if (magicName.equals("Ice Magic")) {
                                                coldUsed = true;
                                            }

                                            if (magicName.equals("Necrotic Magic")) {
                                                necroticUsed = true;
                                            }

                                            System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                            System.out.println(player.toString() + "\n\n");
                                            System.out.println(enemy.toString() + "\n\n");

                                        case 2:
                                            enemy.heal(enemy.getHeal());
                                            System.out.println(enemy.getName() + " uses a potion and recovers "
                                                    + enemy.getHeal() + " HP.");
                                            break;

                                        case 3:

                                            enemy.getArmor().doubleDefence();
                                            System.out.println(
                                                    enemy.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                            System.out.println(player.toString() + "\n\n");

                                            break;

                                    }

                                    if (necroticUsed) {
                                        enemy.removeNecroticEffectTurns();
                                    }
                                    if (shockUsed) {
                                        enemy.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        enemy.removeColdEffectTurns();
                                    }
                                }
                            } else {
                                computerAction = random.nextInt(4);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:

                                        int magicChoice = random.nextInt(6);

                                        String magicName = player.getMagic(magicChoice).toString();

                                        if (magicName.equals("Eletric Magic")) {
                                            shockUsed = true;
                                        }

                                        if (magicName.equals("Ice Magic")) {
                                            coldUsed = true;
                                        }

                                        if (magicName.equals("Necrotic Magic")) {
                                            necroticUsed = true;
                                        }

                                        System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");

                                    case 2:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 3:

                                        enemy.getArmor().doubleDefence();
                                        System.out.println(enemy.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                        System.out.println(player.toString() + "\n\n");

                                        break;

                                }

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                                if (shockUsed) {
                                    enemy.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    enemy.removeColdEffectTurns();
                                }
                                if (player.isAlive()) {

                                    playerAction = getPlayerAction(scanner);
                                    switch (playerAction) {
                                        case 0:
                                            System.out.println();
                                            int playerDamage = player.calculateDamage();
                                            int damageDealt = playerDamage;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        player.getName() + " attacks " + enemy.getName() + " for "
                                                                + playerDamage
                                                                + " damage.");
                                                enemy.receiveDamage(damageDealt);
                                            } else {
                                                System.out
                                                        .println(player.getName() + " attacks " + enemy.getName()
                                                                + " but does no damage.");
                                            }
                                            // System.out.println(enemy.toString() + "\n\n");

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
                                                System.out.println(coldUsed);
                                            }

                                            System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 2:
                                            player.getArmor().doubleDefence();
                                            System.out
                                                    .println(player.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 3:
                                            player.heal();
                                            System.out
                                                    .println(player.getName() + " uses a potion and recovers "
                                                            + player.getHeal() + " HP.");
                                            System.out.println(player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;
                                    }

                                    if (shockUsed) {
                                        player.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        player.removeColdEffectTurns();

                                    }
                                    System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                    System.out.println(coldUsed);
                                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                    System.out.println("------");
                                }
                            }
                        }

                        if (player.isAlive()) {
                            afterFinalBattleText();
                            vardamirPerspectiveFinal();
                            ifNoOneWasSavedFinal();
                        } else if (enemy.isAlive()) {
                            System.out.println("You are dead!");
                        } else {
                            System.out.println("You are dead!");
                        }
                    } else if (enemy.isAlive()) {
                        System.out.println("You are dead!");
                    } else {
                        System.out.println("You are dead!");
                    }
                } else if (choice == 2) {
                    goAwayChoice();
                    Character nidere = new Character("Nidere", 5, 5, 5, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    Character specter = new Character("Specter", 4, 8, 3, 5, new Weapon(Weapon.DAGGER), null,
                            new Potion(Potion.SMALL));
                    createMagic(5, specter);
                    createMagic(5, nidere);

                    enemyChoice = random.nextInt(2);
                    enemy = null;
                    System.out.println(enemyChoice);
                    if (enemyChoice == 0) {
                        enemy = nidere;
                    } else {
                        enemy = specter;
                    }

                    System.out.println("\n" + enemy.toString() + "\n\n");
                    coldUsed = false;
                    shockUsed = false;
                    necroticUsed = false;

                    while (player.isAlive() && enemy.isAlive()) {
                        if (enemy.getFireEffectTurns() > 0) {
                            enemy.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            enemy.applyPoisonEffect();
                        }

                        if (player.getColdEffectTurns() <= 0) {
                            enemy.removeColdEffect();
                            coldUsed = false;
                            player.setColdEffectTurns();
                        }

                        if (enemy.getFireEffectTurns() > 0) {
                            player.applyFireEffect();
                        }

                        if (enemy.getPoisonEffectTurns() > 0) {
                            player.applyPoisonEffect();
                        }

                        if (enemy.getColdEffectTurns() <= 0) {
                            player.removeColdEffect();
                            coldUsed = false;
                            enemy.setColdEffectTurns();
                        }

                        if(player.getNecroticEffectTurns() <= 0){
                            player.removeNecroticEffect();
                            necroticUsed = false;
                            player.setNecroticEffectTurns();
                        }

                        if (player.getShockEffectTurns() <= 0) {
                            enemy.addDexterity(2);
                            shockUsed = false;
                            player.setShockEffectTurns();
                        }

                        if (enemy.getShockEffectTurns() <= 0) {
                            player.addDexterity(2);
                            shockUsed = false;
                            enemy.setShockEffectTurns();
                        }
                        System.out.println(player.toString());
                        System.out.println(enemy.toString());
                        System.out.println(player.getArmor().getDefence());
                        player.getArmor().setDefence();
                        if (player.getAgility() > enemy.getAgility()) {
                            playerAction = getPlayerAction(scanner);
                            switch (playerAction) {
                                case 0:
                                    System.out.println();
                                    int playerDamage = player.calculateDamage();
                                    int damageDealt = playerDamage;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                player.getName() + " attacks " + enemy.getName() + " for "
                                                        + playerDamage
                                                        + " damage.");
                                        enemy.receiveDamage(damageDealt);
                                    } else {
                                        System.out
                                                .println(player.getName() + " attacks " + enemy.getName()
                                                        + " but does no damage.");
                                    }
                                    // System.out.println(enemy.toString() + "\n\n");

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
                                        System.out.println(coldUsed);
                                    }

                                    System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                    player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 2:
                                    player.getArmor().doubleDefence();
                                    System.out.println(player.getName() + " doubles their defense for 1 round.");
                                    System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;

                                case 3:
                                    player.heal();
                                    System.out
                                            .println(player.getName() + " uses a potion and recovers "
                                                    + player.getHeal() + " HP.");
                                    System.out.println(player.getArmor().getDefence());
                                    // System.out.println(enemy.toString() + "\n\n");

                                    break;
                            }

                            if (shockUsed) {
                                player.removeShockEffectTurns();
                            }
                            if (coldUsed) {
                                player.removeColdEffectTurns();

                            }
                            System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                            System.out.println(coldUsed);
                            System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (enemy.isAlive()) {
                                computerAction = random.nextInt(3);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            System.out.println("Dano dado: " + damageDealt);
                                            System.out.println("Defesa atual: " + playerDefense);
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 2:

                                        necroticUsed = true;

                                        System.out.println(enemy.getMagic(0).toString() + " used.");

                                        player.castMagic(player, 0, enemy.getMagic(0));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");
                                }

                                System.out.println(player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                            }
                        } else {
                            computerAction = random.nextInt(3);
                            switch (computerAction) {
                                case 0:
                                    int enemyDamage = enemy.calculateDamage();
                                    int playerDefense = player.getArmor().setDefence();
                                    int damageDealt = enemyDamage - playerDefense;

                                    if (damageDealt > 0) {
                                        System.out.println(
                                                enemy.getName() + " attacks " + player.getName() + " for "
                                                        + enemyDamage + " damage.");
                                        System.out.println("Dano dado: " + damageDealt);
                                        System.out.println("Defesa atual: " + playerDefense);
                                        player.receiveDamage(damageDealt);
                                    } else {
                                        System.out.println(enemy.getName() + " attacks " + player.getName()
                                                + " but does no damage.");
                                    }
                                    break;

                                case 1:
                                    enemy.heal(enemy.getHeal());
                                    System.out.println(enemy.getName() + " uses a potion and recovers "
                                            + enemy.getHeal() + " HP.");
                                    break;

                                case 2:

                                    necroticUsed = true;

                                    System.out.println(enemy.getMagic(0).toString() + " used.");

                                    player.castMagic(player, 0, enemy.getMagic(0));
                                    System.out.println(player.toString() + "\n\n");
                                    System.out.println(enemy.toString() + "\n\n");
                            }

                            System.out.println(player.getName() + " HP: " + player.getHitPoints());
                            System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                            System.out.println("------");

                            if (necroticUsed) {
                                enemy.removeNecroticEffectTurns();
                            }
                            if (player.isAlive()) {

                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out.println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");
                            }
                        }
                    }
                    if (player.isAlive()) {
                        findingVardamir();
                        Character vardamir = new Character("Vardamir", 10, 10, 5, 5, new Weapon(Weapon.LONG_SWORD),
                                null, new Potion(Potion.LARGE));
                        vardamir.setArmor(new Armor(Armor.LIGHT, vardamir));
                        createMagic(1, vardamir);
                        createMagic(2, vardamir);
                        createMagic(3, vardamir);
                        createMagic(4, vardamir);
                        createMagic(5, vardamir);
                        createMagic(6, vardamir);

                        enemy = vardamir;

                        System.out.println("\n" + enemy.toString() + "\n\n");
                        coldUsed = false;
                        shockUsed = false;
                        necroticUsed = false;

                        while (player.isAlive() && enemy.isAlive()) {
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

                            if (enemy.getFireEffectTurns() > 0) {
                                player.applyFireEffect();
                            }

                            if (enemy.getPoisonEffectTurns() > 0) {
                                player.applyPoisonEffect();
                            }

                            if (enemy.getColdEffectTurns() <= 0) {
                                player.removeColdEffect();
                                coldUsed = false;
                                enemy.setColdEffectTurns();
                            }

                            if (enemy.getNecroticEffectTurns() <= 0) {
                                player.removeNecroticEffect();
                                necroticUsed = false;
                                enemy.setNecroticEffectTurns();
                            }

                            if (player.getShockEffectTurns() <= 0) {
                                enemy.addDexterity(2);
                                shockUsed = false;
                                player.setShockEffectTurns();
                            }

                            if (enemy.getShockEffectTurns() <= 0) {
                                player.addDexterity(2);
                                shockUsed = false;
                                enemy.setShockEffectTurns();
                            }
                            System.out.println(player.toString());
                            System.out.println(enemy.toString());
                            System.out.println(player.getArmor().getDefence());
                            player.getArmor().setDefence();
                            if (player.getAgility() > enemy.getAgility()) {
                                playerAction = getPlayerAction(scanner);
                                switch (playerAction) {
                                    case 0:
                                        System.out.println();
                                        int playerDamage = player.calculateDamage();
                                        int damageDealt = playerDamage;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    player.getName() + " attacks " + enemy.getName() + " for "
                                                            + playerDamage
                                                            + " damage.");
                                            enemy.receiveDamage(damageDealt);
                                        } else {
                                            System.out
                                                    .println(player.getName() + " attacks " + enemy.getName()
                                                            + " but does no damage.");
                                        }
                                        // System.out.println(enemy.toString() + "\n\n");

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
                                            System.out.println(coldUsed);
                                        }

                                        System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 2:
                                        player.getArmor().doubleDefence();
                                        System.out
                                                .println(player.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;

                                    case 3:
                                        player.heal();
                                        System.out
                                                .println(player.getName() + " uses a potion and recovers "
                                                        + player.getHeal() + " HP.");
                                        System.out.println(player.getArmor().getDefence());
                                        // System.out.println(enemy.toString() + "\n\n");

                                        break;
                                }

                                if (shockUsed) {
                                    player.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    player.removeColdEffectTurns();

                                }
                                System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                System.out.println(coldUsed);
                                System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                System.out.println("------");

                                if (enemy.isAlive()) {
                                    computerAction = random.nextInt(4);
                                    switch (computerAction) {
                                        case 0:
                                            int enemyDamage = enemy.calculateDamage();
                                            int playerDefense = player.getArmor().setDefence();
                                            int damageDealt = enemyDamage - playerDefense;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        enemy.getName() + " attacks " + player.getName() + " for "
                                                                + enemyDamage + " damage.");
                                                player.receiveDamage(damageDealt);
                                            } else {
                                                System.out.println(enemy.getName() + " attacks " + player.getName()
                                                        + " but does no damage.");
                                            }
                                            break;

                                        case 1:

                                            int magicChoice = random.nextInt(6);

                                            String magicName = player.getMagic(magicChoice).toString();

                                            if (magicName.equals("Eletric Magic")) {
                                                shockUsed = true;
                                            }

                                            if (magicName.equals("Ice Magic")) {
                                                coldUsed = true;
                                            }

                                            if (magicName.equals("Necrotic Magic")) {
                                                necroticUsed = true;
                                            }

                                            System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                            System.out.println(player.toString() + "\n\n");
                                            System.out.println(enemy.toString() + "\n\n");

                                        case 2:
                                            enemy.heal(enemy.getHeal());
                                            System.out.println(enemy.getName() + " uses a potion and recovers "
                                                    + enemy.getHeal() + " HP.");
                                            break;

                                        case 3:

                                            enemy.getArmor().doubleDefence();
                                            System.out.println(
                                                    enemy.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                            System.out.println(player.toString() + "\n\n");

                                            break;

                                    }

                                    if (necroticUsed) {
                                        enemy.removeNecroticEffectTurns();
                                    }
                                    if (shockUsed) {
                                        enemy.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        enemy.removeColdEffectTurns();
                                    }
                                }
                            } else {
                                computerAction = random.nextInt(4);
                                switch (computerAction) {
                                    case 0:
                                        int enemyDamage = enemy.calculateDamage();
                                        int playerDefense = player.getArmor().setDefence();
                                        int damageDealt = enemyDamage - playerDefense;

                                        if (damageDealt > 0) {
                                            System.out.println(
                                                    enemy.getName() + " attacks " + player.getName() + " for "
                                                            + enemyDamage + " damage.");
                                            player.receiveDamage(damageDealt);
                                        } else {
                                            System.out.println(enemy.getName() + " attacks " + player.getName()
                                                    + " but does no damage.");
                                        }
                                        break;

                                    case 1:

                                        int magicChoice = random.nextInt(6);

                                        String magicName = player.getMagic(magicChoice).toString();

                                        if (magicName.equals("Eletric Magic")) {
                                            shockUsed = true;
                                        }

                                        if (magicName.equals("Ice Magic")) {
                                            coldUsed = true;
                                        }

                                        if (magicName.equals("Necrotic Magic")) {
                                            necroticUsed = true;
                                        }

                                        System.out.println(enemy.getMagic(magicChoice).toString() + " used.");

                                        player.castMagic(player, magicChoice, enemy.getMagic(magicChoice));
                                        System.out.println(player.toString() + "\n\n");
                                        System.out.println(enemy.toString() + "\n\n");

                                    case 2:
                                        enemy.heal(enemy.getHeal());
                                        System.out.println(enemy.getName() + " uses a potion and recovers "
                                                + enemy.getHeal() + " HP.");
                                        break;

                                    case 3:

                                        enemy.getArmor().doubleDefence();
                                        System.out.println(enemy.getName() + " doubles their defense for 1 round.");
                                        System.out.println("Defesa atual: " + enemy.getArmor().getDefence());
                                        System.out.println(player.toString() + "\n\n");

                                        break;

                                }

                                if (necroticUsed) {
                                    enemy.removeNecroticEffectTurns();
                                }
                                if (shockUsed) {
                                    enemy.removeShockEffectTurns();
                                }
                                if (coldUsed) {
                                    enemy.removeColdEffectTurns();
                                }
                                if (player.isAlive()) {

                                    playerAction = getPlayerAction(scanner);
                                    switch (playerAction) {
                                        case 0:
                                            System.out.println();
                                            int playerDamage = player.calculateDamage();
                                            int damageDealt = playerDamage;

                                            if (damageDealt > 0) {
                                                System.out.println(
                                                        player.getName() + " attacks " + enemy.getName() + " for "
                                                                + playerDamage
                                                                + " damage.");
                                                enemy.receiveDamage(damageDealt);
                                            } else {
                                                System.out
                                                        .println(player.getName() + " attacks " + enemy.getName()
                                                                + " but does no damage.");
                                            }
                                            // System.out.println(enemy.toString() + "\n\n");

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
                                                System.out.println(coldUsed);
                                            }

                                            System.out.println(player.getMagic(magicChoice).toString() + " used.");

                                            player.castMagic(enemy, magicChoice, player.getMagic(magicChoice));
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 2:
                                            player.getArmor().doubleDefence();
                                            System.out
                                                    .println(player.getName() + " doubles their defense for 1 round.");
                                            System.out.println("Defesa atual: " + player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;

                                        case 3:
                                            player.heal();
                                            System.out
                                                    .println(player.getName() + " uses a potion and recovers "
                                                            + player.getHeal() + " HP.");
                                            System.out.println(player.getArmor().getDefence());
                                            // System.out.println(enemy.toString() + "\n\n");

                                            break;
                                    }

                                    if (shockUsed) {
                                        player.removeShockEffectTurns();
                                    }
                                    if (coldUsed) {
                                        player.removeColdEffectTurns();

                                    }
                                    System.out.println("Numero de turnos restantes:" + player.getColdEffectTurns());
                                    System.out.println(coldUsed);
                                    System.out.println("\n" + player.getName() + " HP: " + player.getHitPoints());
                                    System.out.println(enemy.getName() + " HP: " + enemy.getHitPoints());
                                    System.out.println("------");
                                }
                            }
                        }

                        if (player.isAlive()) {
                            afterFinalBattleText();
                            vardamirPerspectiveFinal();
                            ifNoOneWasSavedFinal();
                        } else if (enemy.isAlive()) {
                            System.out.println("You are dead!");
                        } else {
                            System.out.println("You are dead!");
                        }
                    } else if (enemy.isAlive()) {
                        System.out.println("You are dead!");
                    } else {
                        System.out.println("You are dead!");
                    }
                }
            }

        } else if (enemy.isAlive()) {
            System.out.println("You are dead!");
        } else {
            System.out.println("You are dead!");
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
                    character.addMagic(new Magic(Magic.NECROTIC));
                    break;
                case 6:
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

        slowPrint(" After a lengthy patrol along the distant borders of your city,\n"
                + "you and your steadfast friend conclude your watch, opting to embark on the journey back home.\n"
                + "The path home stretches out, a seemingly endless ribbon through the untamed wilderness.\n"
                + "The air carries a sense of freshness, soothing your senses as you drink in the tranquil surroundings.\n"
                + "\n"
                + " Nature's symphony surrounds you, the harmonious chorus of Eternia's vibrant and thriving kingdom filling the air.\n"
                + "The songbirds sing their melodious tunes, while the gentle rustling of leaves adds a soft,\n"
                + "soothing backdrop to the tranquil ambiance. But for how long...? \n"
                + "After an hour of walking, you and Finrod finally arrive close to the capital, and he says with an eager grin,\n"
                + "We're finally getting close to it, I can't wait to eat my wife's beef stew! \n"
                + "\n"
                + " But something is amiss, the air, once so fresh and rejuvenating, has taken on an ominous heaviness.\n");
        slowPrint(" Before you can reply, Finrod's voice trembles as he calls you urgently.\n"
                + "His usually resolute demeanor now betrays a deep, primal fear, something he has never experienced before.\n");
        slowPrint(" Following his frantic gaze, you look towards the city, and your heart plummets.\n"
                + "The city, your beloved capital, is engulfed in a raging inferno.\n"
                + "The towering flames lick the night sky, casting an eerie,\n"
                + "malevolent glow that defies the tranquility that once enveloped the kingdom. \n");
        slowPrint(
                " A shiver runs down your spine as you witness a horrifying sight, a colossal tornado of dark energy descends from the heavens,\n"
                        + "its ominous presence creating an otherworldly tempest. The once-clear skies are now obscured by thick, foreboding clouds,\n"
                        + "and the once-proud city is plunged into chaos.\n\n"
                        + " Piercing screams of terror pierce the air as innocent citizens flee for their lives, their homes reduced to ashes before their eyes.\n"
                        + "You and Finrod stand at the edge of the darkened forest, frozen in dread as the malevolent force continues its relentless descent upon"
                        + "the heart of your kingdom.\n"
                        + "\n"
                        + " In the midst of the chaos and devastation, you sense that something unspeakable has been awakened, and your fate,\n"
                        + "along with that of Eternia, hangs precariously in the balance.\n");

    }

    private static void firstChoiceText() {
        slowPrint("\n\n After triumphing over these malevolent creatures, \n"
                + "you look at your lost friend's body and realise a hard choice lies ahead. \n"
                + "Will you venture to your residence, where your beloved wife Elwyn may be in grave danger, \n"
                + "in a valiant attempt to render assistance? \n"
                + "Or will you follow the rational course of action, as prescribed by your guard protocol, \n"
                + "by heading to the outpost to seek your commander's orders on protecting the city from these vile threats? \n");

    }

    private static void searchWifeChoice() {
        slowPrint(
                " You find yourself at a crossroads, torn between the urgent need to reach your home and assist your beloved wife. \n"
                        + "Your residence lies a substantial 20-minute journey from the city's entrance, yet a cautious approach could secure your safe arrival. \n"
                        + "\n"
                        + " Understanding the turmoil unfolding around you proves a daunting task. \n"
                        + "What was once your immaculate realm now stands in the middle of total chaos and agony, \n"
                        + "overwhelmed by relentless flames and peril lurking at every corner. Passing through the majestic gates of Eternia, \n"
                        + "you realize that nothing remains as it once was.\n"
                        + "\n"
                        + " After navigating through the city's streets, your heart heavy with despair as you witness your fellow citizens \n"
                        + "falling prey to unimaginable creatures and powers, \n"
                        + "you finally arrive at your neighborhood. Drawing near to your own house, a sobering truth dawns upon you: \n"
                        + "the decisions you make in the moments ahead will shape not only your own fate \n"
                        + "but that of your cherished wife and the destiny of your kingdom.\n"
                        + "\n"
                        + " You spot the Finland triplets, the elven blacksmith who has always been like a father to you since your own father's death, \n"
                        + "in grave danger, \n"
                        + "surrounded by giant wolves twisted by all the chaos, like wolves of darkness, with pitch-black fur, \n"
                        + "hollow red eyes and sharp teeth, ready to rip apart the lifes of that children, in addition to the night wraiths, creatures drawn from the depths of darkness, insatiable in their thirst for souls and terror..\n"
                        + "\n"
                        + " Simultaneously, your gaze falls upon a face most dear, Elwyn, the person you hold closest to your heart, besieged by the same creature. \n"
                        + "\n"
                        + " The weight of your choice presses upon you: to rescue your wife or to safeguard the lives of the three innocent children. \n");
    }

    private static void saveWifeChoice() {
        slowPrint(" You made your choice, unsure if it's right, but determined to save your beloved Elwyn. \n"
                + "After battling the darkness, you rescue your wife and take her to safety, knowing it might be your last chance if you both don't escape. \n"
                + "\n"
                + " Leaving her in the woods for safety, you head towards the heart of the town, where the darkness is strongest. \n"
                + "This time, things are worse. There are no guards with you, and thick mist limits your vision to just ten steps. \n"
                + "All you hear are screams and see blood; hope, freedom, and peace seem distant. \n"
                + "\n"
                + " As you go, you encounter more of those terrible creatures and manage to clear a path. \n"
                + "But then an unsettling silence falls, followed by chaos approaching from behind. \n"
                + "A horde of creatures is determined to kill you and every elf in the city. You start running, the town's center your only option. \n"
                + "You have a purpose - you're a soldier, a warrior, with a mission. \n");
    }

    private static void saveTripletsChoice() {
        slowPrint(" You made your choice, your resolve unwavering, even though uncertainty lingers in your mind. \n"
                + "You were determined to save the children, even if it meant the possibility of never reuniting with your beloved wife. \n"
                + "In your relentless battle against the encroaching darkness, you successfully rescued the triplets. \n"
                + "However, a devastating sight awaited you – Elwyn, once full of life and warmth, now lay lifeless and soulless, \n"
                + "consumed by the horrifying specters. The woman you had known, once happy, cheerful, and kind since your childhood, was gone, \n"
                + "leaving only emptiness. \n"
                + "\n"
                + " With the triplets safe, you embarked on a journey into the heart of the town, where the darkness held its strongest grip. \n"
                + "This tine, things are worse. There are no guards with you, and thick mist limits your vision to just ten steps. \n"
                + "All you hear are screams and see blood; hope, freedom, and peace seem distant. \n"
                + "\n"
                + " As you go, you encounter more of those terrible creatures and manage to clear a path. \n"
                + "But then an unsettling silence falls, followed by chaos approaching from behind. \n"
                + "A horde of creatures is determined to kill you and every elf in the city. \n"
                + "You start running, the town's center your only option. You have a purpose – you're a soldier, a warrior, with a mission. \n");
    }

    private static void goToOutpostChoice() {
        slowPrint(" You choose to adhere to the decision dictated by your guard protocol, \n"
                + "even though you never thought you'd have to face such fear and darkness. \n"
                + "\n"
                + " Comprehending the chaos unfurling around you is a formidable challenge. \n"
                + "What was once your pristine realm now lies ensnared in complete disorder and agony, \n"
                + "engulfed by unrelenting flames and danger lurking at every turn. As you pass through the grand gates of Eternia, \n"
                + "the stark reality dawns upon you that nothing remains as it once was. \n"
                + "\n"
                + " Having traversed the city's tumultuous streets, \n"
                + "your heart laden with despair as you bear witness to your fellow citizens falling victim to unimaginable creatures and powers, \n"
                + "you eventually reach the city's outpost. Stepping into the command center, \n"
                + "a grim scene unfolds before you - a mound of disarrayed bodies sprawls across the first floor. \n"
                + "A massacre has occurred here, your comrades ruthlessly felled by the horrors that now plague the city. \n"
                + "You scan the area, but your commander's lifeless form is not among them. \n"
                + "You contemplate whether to search in his room on the second floor or press on with your mission at the heart of the city, \n"
                + "amidst the ongoing chaos. \n");

    }

    private static void goSearchTheMasterChoice() {
        slowPrint(
                " You opt to search for your commander, driven by the need for guidance in this nightmarish situation.\n"
                        + "Climbing the stairs to the second floor, a sense of trepidation grips you, intensified by the eerie silence that pervades the outpost.\n"
                        + "\n"
                        + " As you reach the upper level, a chilling sight awaits you. \n"
                        + "Your commander lies on the floor, grievously wounded, with a monstrous creature looming over him. \n"
                        + "The creature's grotesque features and malevolent aura leave no doubt that it's responsible for the carnage in the outpost. \n"
                        + "With grim determination, you engage the monster in a fierce battle, your skill and bravery pitted against its unholy might. \n"
                        + "\n");
    }

    private static void afterReachingMaster() {
        slowPrint(
                " After a grueling struggle, you manage to defeat the creature, but your commander's injuries are severe, \n"
                        + "and his time is running out. \n"
                        + "As you kneel beside him, he weakly imparts his final words of wisdom and guidance, \n"
                        + "entrusting you with his legendary two-edged sword, a symbol of honor and strength. \n"
                        + "\n"
                        + " With his last breath, your commander passes away, leaving you with the weighty responsibility of protecting Eternia and its people. \n"
                        + "The legendary sword in your possession, you know that the fate of the city now rests on your shoulders. \n"
                        + "The darkness may have consumed your commander, but his legacy lives on through you, the last hero of Eternia. \n");
    }

    private static void goAwayChoice() {
        slowPrint(" You choose to turn away from the outpost and head toward the heart of the city, \n"
                + "where the surging and malevolent dark energy emanates from the skies. \n"
                + "It seems to be the epicenter of this unrelenting chaos. \n"
                + "As you make your way towards the city center, an ominous realization dawns upon you: \n"
                + "a horde of sinister creatures is tailing your every step, relentlessly pursuing you towards the epicenter. \n"
                + "\n"
                + " With each passing moment, the dark energy intensifies, and the sounds of the pursuing horde grows louder. \n"
                + "The weight of responsibility presses heavily on you, knowing that the city's fate rests upon your shoulders. \n"
                + "As you approach the city's core, your heart races, and the unknown dangers that lie ahead become ever more daunting. \n"
                + "Yet, you are resolute, a solitary warrior determined to confront the source of this malevolent force \n"
                + "and bring an end to the chaos that has befallen Eternia. \n"
                + "\n"
                + " Despite your best efforts to avoid engaging the horde, a few relentless creatures manage to close the gap, \n"
                + "forcing you into fierce and unforgiving battles before you can proceed with your mission. \n");
    }

    private static void findingVardamir() {
        slowPrint(" After an endless journey of suffering and agony, you finally reach the heart of the kingdom, \n"
                + "Eternia's central square, where heroes are honored and the eternal Eternia Tree stands, \n"
                + "now shrouded in a whirlwind of dark magic. \n"
                + "An eerie attraction to this malevolent air grips you, and your body struggles to withstand such power. \n"
                + "Fatigue courses through your bones, and your mind begins to wither after bearing witness to so much malevolence. \n"
                + "However, you have a mission, and you will not falter here. You forge ahead toward the tree, \n"
                + "but something peculiar transpires; the creatures that once pursued you now merely observe from a distance, \n"
                + "intrigued by what is about to unfold. \n"
                + "\n"
                + " Here, your ultimate battle against the darkness is set to begin. \n"
                + "Amidst the obscurity and magic, you discern a figure – a familiar one, an elf, tall and youthful, \n"
                + "with long silver hair, emerging from the direction of the tree. Eyes consumed by darkness, arcane markings adorning his body, \n"
                + "emanating a malevolent purple glow. \n"
                + "You recognize Vardamir, a noble who had often stood by your side during your missions. \n"
                + "During your training, the guards trained alongside magic students to learn to defend Eternia together. \n"
                + "Vardamir, your former training companion, could he be at the heart of all this? \n"
                + "Of all the suffering, pain, and darkness? \n"
                + "Will this nightmare never end? \n"
                + "What happened to everything you once knew, all the happiness and camaraderie in your home? \n"
                + "Why would Vardamir do this? The kingdom's finest mage, now the source of your downfall? \n"
                + "Your family, your friends, your home—all in ruins? \n"
                + "\n"
                + " It doesn't matter. You are a soldier. You have a mission, and if that mission is to stop him, you must do it. \n"
                + "You move towards Vardamir, who appears to be standing still, eyes closed. \n"
                + "But as you draw near, he opens his unrecognizable eyes and moves toward you. \n"
                + "\n"
                + " As you draw closer to Vardamir, the air grows thick with tension, and the malevolent aura surrounding him becomes even more palpable. \n"
                + "His once-familiar features are now distorted by the dark magic that courses through his veins. \n"
                + "\n"
                + " You stop a few paces away, your legendary sword at the ready, and confront Vardamir. \n"
                + "'Vardamir', you begin, your voice laden with a mix of disbelief and determination, \n"
                + "'what have you done? Why have you unleashed this terrible darkness upon our fate?' \n"
                + "\n"
                + " Vardamir's eyes, now pools of abyssal blackness, meet yours, and a twisted smile curls upon his lips. \n"
                + "\n"
                + " Your heart aches at the transformation of your once-loyal companion. \n"
                + "'You were the kingdom's finest apprentice, Vardamir, and now you seek its destruction?' \n"
                + "'I won't let your ambition blind you to the pain you're causing.' \n"
                + "\n"
                + " With a sudden burst of dark magic, Vardamir hurls a torrent of malevolent energy towards you. \n"
                + "You deftly evade, engaging in a fierce battle of wills and steel, each strike and parry a testament to your commitment to protect Eternia. \n"
                + "Between the clashes, you attempt to reason with him, \n"
                + "'Vardamir, there is still goodness within you. We can find a way to break free from this darkness.' \n"
                + "\n"
                + " But Vardamir's laughter is chilling. \n"
                + "'It's too late for me, old friend. Darkness has consumed my soul, and I revel in its power.' \n"
                + "The battle rages on, as you and Vardamir, once comrades, \n"
                + "now find yourselves on opposing ends of a conflict that threatens to engulf Eternia in an eternal night. \n");
    }

    private static void afterFinalBattleText() {
        slowPrint(" After a relentless battle, \n"
                + "your strength wanes as you grapple with the pure malevolence that has consumed your once friend. \n"
                + "You're on the brink of collapse, your homeland's darkness now seeping into your very soul. \n"
                + "\n"
                + " Dark magic courses through your mind, usurping control over your body. \n"
                + "You sink to your knees, powerless, while Vardamir stands before you, transformed beyond recognition. \n"
                + "With your head bowed, you brace for the creature's final blow. \n"
                + "\n"
                + " But then, in a moment of unexpected providence, the blade in your hand, no longer under your control, \n"
                + "flickers with a radiant, golden light, from Eternia three, freeing your grasp. \n"
                + "It's your one chance to purge this darkness from Eternia. With unwavering resolve, \n"
                + "you strike Vardamir's belly with your weapon, unleashing a surge of blinding light that engulfs the surroundings. \n"
                + "\n"
                + " Time passes, and as the brilliance subsides, you regain your vision. \n"
                + "Vardamir now lies unconscious beside you, the darkness tamed. \n"
                + "The creatures that once watched the duel have vanished, and the tumultuous vortex of dark energy has dissipated. \n"
                + "The town is shrouded in an eerie silence, devoid of life, joy, or love, perhaps indefinitely. \n"
                + "\n"
                + " Yet, you've fulfilled your mission; the chaos has been quelled. \n"
                + "You can now rest from your pain, hoping that the lives you saved will preserve your existence and legacy in their memories. \n");
    }

    private static void ifTheWifeWasSavedFinal() {
        slowPrint(" Eternia, once a prosperous kingdom, now lay in ruins, reduced to smoldering ashes. \n"
                + "However, the kingdom's last Hero had succeeded in sealing the malevolence, preventing it from escaping and further ravaging the land. \n"
                + "\n"
                + " Now, Elwyn, having awoken from the trauma, finds herself in a desolate world, her heart heavy with the absence of her beloved husband. \n"
                + "She makes her way to their home, her mind flooded with memories of their happiness before the devastation unfolded. \n"
                + "The house holds fragments of their past, each room a repository of cherished moments—a well-worn bookshelf where they shared stories, \n"
                + "a cozy fireplace where they sought solace during the bitter winters, and a garden where they had lovingly nurtured vibrant flowers. \n"
                + "\n"
                + " Driven by a relentless determination to find her beloved husband, Elwyn embarks on a quest that leads her to Eternia's sacred tree. \n"
                + "There, amidst the now dry and withered branches, she discovers a heart-wrenching sight—her husband, \n"
                + "lifeless on the floor, having made the ultimate sacrifice. \n"
                + "\n"
                + " As she kneels beside him, her tears fall freely, her anguish echoing through the silent ruins. \n"
                + "She clings to his lifeless form, whispering words of love and loss, unable to fathom the emptiness that has consumed her world. \n"
                + "Beside him lies a curious object—an insignia from the Scoia'tael house, a once-prominent and noble family of Eternia. \n"
                + "\n"
                + " As the weight of betrayal and the depth of her sorrow crash down upon her, \n"
                + "Elwyn's heart fills with a profound sense of loss and a burning desire for justice. \n"
                + "It's a revelation that etches a vow into her very soul: she will never forget, and Eternia will never forget. \n"
                + "The Last Hero must be avenged. \n");
    }

    private static void ifTheTripletsWereSavedFinal() {
        slowPrint(" Eternia, once a thriving kingdom, now lay in ruins, reduced to smoldering ashes. \n"
                + "However, the kingdom's last Hero had succeeded in sealing the malevolence, preventing it from escaping and further ravaging the land. \n"
                + "\n"
                + " Now, the Finland triplets, having awoken from the trauma of their near-death experience, find themselves in a desolate world, \n"
                + "their hearts heavy with the absence of both their father and the Last Hero. \n"
                + "They make their way to their family home, their minds flooded with memories of their father's warmth and guidance, \n"
                + "and the hero who had saved them. \n"
                + "\n"
                + " The house holds fragments of their past, \n"
                + "each room a repository of cherished moments—a well-worn workbench where their father had crafted wonders, \n"
                + "a cozy hearth where they had gathered to share stories, and a garden where they had spent countless hours together. \n"
                + "\n"
                + " Driven by a relentless determination to understand what has befallen their family, \n"
                + "the triplets embark on a quest that leads them to their family home. \n"
                + "There, amidst the now silent rooms, they discover a heart-wrenching sight—their father, Finland, \n"
                + "lifeless on the ground, having made the ultimate sacrifice. \n"
                + "\n"
                + " As they kneel beside their father's lifeless form, their tears fall freely, their anguish echoing through the silent ruins. \n"
                + "They embrace their father, whispering words of love and loss, unable to fathom the emptiness that has consumed their world. \n"
                + "\n"
                + " Filled with an unyielding determination to uncover the truth behind the hero's fate, \n"
                + "the Farcloud brothers embark on a quest that takes them to Eternia's sacred tree. \n"
                + "There, amidst the now dry and withered branches, they discover a harrowing sight—the man who had saved them, \n"
                + "lifeless on the floor, having made the ultimate sacrifice. \n"
                + "\n"
                + " Beside him lies a curious object—an insignia from the Scoia'tael house, a once-prominent and noble family of Eternia. \n"
                + "\n"
                + " The weight of betrayal and the depth of their sorrow crash down upon them, \n"
                + "etching a vow into their very souls: they will never forget, and Eternia will never forget. \n"
                + "The Last Hero must be avenged, and the Scoia'tael will answer for their deeds. \n");
    }

    private static void ifNoOneWasSavedFinal() {
        slowPrint(" Eternia, once a flourishing kingdom, now lay in ruins, its grandeur reduced to smoldering ashes. \n"
                + "The malevolence that had threatened to engulf the land was sealed away, but at a tremendous cost—the life of the kingdom's last Hero. \n"
                + "\n"
                + " In the heart of this desolation, the Hero stands lifeless on the floor, a symbol of unparalleled valor and sacrifice. \n"
                + "Beside him rests the long sword of his commander, an emblem of duty and honor. \n"
                + "His mission is complete, and he has upheld his solemn pledge to protect his family and comrades. \n"
                + "Yet, the price paid is immeasurable. \n"
                + "\n"
                + " The once-vibrant kingdom is now steeped in silence and sorrow. \n"
                + "There are no survivors left to bear witness to the hero's legacy, and the land itself seems to mourn the loss of its people. \n"
                + "Eternia, once teeming with life, is now a barren expanse, a haunting testament to the relentless forces of darkness. \n"
                + "\n"
                + " In this bleak aftermath, there is no hope of rebuilding, for there is no one left to rebuild. \n"
                + "The kingdom's history, once rich and vibrant, now ends in tragedy and desolation, \n"
                + "a somber reminder of the cost of heroism and the relentless march of malevolence. \n");
    }

    private static void vardamirPerspectiveFinal() {
        slowPrint(
                " In the aftermath of the cataclysmic battle, the malevolent shroud that had ensnared Vardamir's being begins to unravel, \n"
                        + "unveiling a figure left battered and shattered by the relentless grip of dark magic. \n"
                        + "Slowly, as if rousing from an agonizing and endless slumber, Vardamir stirs. \n"
                        + "\n"
                        + " His eyes, once eclipsed by an abyssal darkness, gradually reclaim their natural radiance, \n"
                        + "and the sinister imprints of malevolent sorcery that had scarred his form gradually wane. \n"
                        + "He awakens to a world marred by unimaginable devastation, \n"
                        + "and his gaze descends upon the Scoia'tael's insignia—the symbol of his family that isnow gone because of his actions. \n"
                        + "\n"
                        + " Vardamir remember his master, the one who had made him do all this tragedy, Velyar. \n"
                        + "\n"
                        + " With trembling hands, Vardamir reaches out, liberating the insignia from his grasp, \n"
                        + "as if relinquishing the lingering vestiges of the malevolence that had entrapped him. \n"
                        + "The emblem falls to the ground with a weighty thud, \n"
                        + "echoing the gravity of his past transgressions as it makes contact with the ashen earth. \n"
                        + "\n"
                        + " As he watches the insignia tumble away, an overwhelming surge of remorse washes over him. \n"
                        + "Vardamir now stands emancipated from the malevolent influences that once propelled him to unleash such destruction upon Eternia. \n"
                        + "The burden of guilt, coupled with the colossal scale of destruction he has wrought, bears down upon him, \n"
                        + "and he collapses to his knees, tears down his cheeks. \n"
                        + "\n"
                        + " His gaze sweeps across the desolation surrounding him—the kingdom in ruins, streets devoid of life, \n"
                        + "and an overwhelming sense of sorrow etched into every fend. \n"
                        + "Vardamir comprehends the magnitude of his actions and the irreparable harm he has inflicted. \n"
                        + "In that profound moment, as he weeps amidst the remnants of his once-beloved homeland, \n"
                        + "he is forced to confront the harrowing consequences of his choices and begin a quest for redemption. \n"
                        + "\n"
                        + " Haunted by the past, he departs for the kingdom of Kriften, determined to forge a new life. \n"
                        + "With the weight of his transgressions as his constant companion, \n"
                        + "Vardamir seeks to atone for his misdeeds by hunting down dark magic to contain and rectify the wrongs he has committed, \n"
                        + "hoping that one day he will be able to find Velyar and avenge all that he destroied. \n");
    }

    private static void slowPrint(String text) {
        int delay = 0;

        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        System.out.println();
    }

    public static Character createCharacter(Scanner scanner) {
        System.out.println("Character Creation");
        System.out.print("Enter character name: ");
        String playerName = scanner.nextLine();

        int pointsRemaining = 50;
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
        System.out.println("1. Dagger");
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

        return player;
    }

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }
}
