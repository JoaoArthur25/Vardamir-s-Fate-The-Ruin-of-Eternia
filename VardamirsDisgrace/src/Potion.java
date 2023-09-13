public class Potion {

    public static final String SMALL = "Small Potion";
    public static final String MEDIUM = "Medium Potion";
    public static final String LARGE = "Large Potion";

    private int healing;
    private String potionCategory;

    public Potion(String potionCategory) {
        this.potionCategory = potionCategory;
        this.healing = setHealing(potionCategory);
    }

    private int setHealing(String potionCategory) {
        switch (potionCategory) {
            case SMALL:
                return 7;
            case  MEDIUM:
                return 10;
            case LARGE:
                return 15;
            default:
                return 0;
        }
    }

    public int getHealing() {
        return healing;
    }

    public String getPotionCategory() {
        return potionCategory;
    }

    @Override
    public String toString() {
        return potionCategory;
    }
}
