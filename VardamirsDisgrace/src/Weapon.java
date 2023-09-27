public class Weapon {
    public static final String DAGGER = "Dagger";
    public static final String LONG_SWORD = "Long Sword";
    public static final String BOW = "Bow";
    public static final String CROSSBOW = "Crossbow";
    public static final String ELVEN_BLADE = "Elven Blade";
    public static final String SPEAR = "Spear";
    public static final String AXE = "Axe";
    public static final String LONG_BOW = "Long Bow";

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
        return category.equals(DAGGER) || category.equals(LONG_SWORD) || category.equals(BOW) || category.equals(CROSSBOW) || category.equals(ELVEN_BLADE) || category.equals(SPEAR) || category.equals(AXE) || category.equals(LONG_BOW);
    }

    private int setDamageConstant(String category) {
        switch (category) {
            case DAGGER:
            return 2;
            case LONG_SWORD:
                return 5; 
            case BOW:
                return 2; 
            case CROSSBOW:
                return 4; 
            case ELVEN_BLADE:
                return 10;
            case SPEAR:
                return 4;
            case AXE:
                return 6;
            case LONG_BOW:
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
