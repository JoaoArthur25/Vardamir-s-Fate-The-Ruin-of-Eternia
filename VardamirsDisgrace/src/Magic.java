import java.util.Random;

public class Magic {
    public static final String FIRE = "Fire Magic";
    public static final String ICE = "Ice Magic";
    public static final String ELECTRIC = "Eletric Magic";
    public static final String POISON = "Poison Magic";
    public static final String DARK = "Dark Magic";
    public static final String NECROTIC = "Necrotic Magic";

    private String magicCategory;

    public Magic(String magicCategory) {
        this.magicCategory = magicCategory;
    }

    public String getMagicCategory() {
        return magicCategory;
    }

    public void cast(Character target, String magicCategory) {
        Random rand = new Random();
        int dice;
            
        switch (magicCategory) {
            case FIRE:
                int fireDamage = rollD6() + rollD6() - 2;
                target.receiveDamage(fireDamage);

                dice = rand.nextInt(4);
                if (dice == 0) {
                    target.setFireEffectTurns();
                    target.applyFireEffect();
                }
                break;

            case ICE:
                int iceDamage = rollD6();
                target.receiveDamage(iceDamage);
                target.setColdEffectTurns();
                target.applyColdEffect();
                break;

            case ELECTRIC:
                int electricDamage = rollD6() + rollD4();
                target.receiveDamage(electricDamage);
                target.setShockEffectTurns();
                target.applyShockEffect();
                break;

            case POISON:
                int poisonDamage = rollD4() + rollD4();
                target.receiveDamage(poisonDamage);

                dice = rand.nextInt(2);
                    if (dice == 0) {
                    target.setPoisonEffectTurns();
                    target.applyPoisonEffect();
                }
                break;

            case DARK:
                int darkDamage = rollD12() + rollD12();
                target.receiveDamage(darkDamage);
                target.reduceStrength(2);
                break;

            case NECROTIC:
                int necroticDamage = rollD4() + rollD6();
                target.receiveDamage(necroticDamage);
                target.reduceStrength(2);
                target.setNecroticEffectTurns();
                break;
        }
    }

    private int rollD12() {
        Random rand = new Random();
        return rand.nextInt(12) + 1;
    }

    private int rollD6() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    private int rollD4() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    @Override
    public String toString(){
        return magicCategory;
    }
}
