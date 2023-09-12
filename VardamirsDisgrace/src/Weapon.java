public class Weapon {
    public static final String DAGGER = "Dagger";
    public static final String LONG_SWORD = "Long Sword";
    public static final String BOW = "Bow";
    public static final String CROSSBOW = "Crossbow";

    private String category;
    private int damageConstant;

    public Weapon(String category) {
        if (isValidCategory(category)) {
            this.category = category;
            this.damageConstant = setDamageConstant(category);
        } else {
            throw new IllegalArgumentException("Invalid weapon category: " + category);
        }
    }

    private boolean isValidCategory(String category) {
        return category.equals(DAGGER) || category.equals(LONG_SWORD) || category.equals(BOW) || category.equals(CROSSBOW);
    }

    private int setDamageConstant(String category) {
        switch (category) {
            case DAGGER:
                return 4;
            case LONG_SWORD:
                return 6; 
            case BOW:
                return 3; 
            case CROSSBOW:
                return 5; 
            default:
                return 0;
        }
    }

    public String getCategory() {
        return category;
    }

    public int getDamageConstant() {
        return damageConstant;
    }
    
    @Override
    public String toString() {
        return category;
    }

}
