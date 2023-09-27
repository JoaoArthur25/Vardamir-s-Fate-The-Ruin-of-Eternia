public class Armor {
    public static final String LIGHT = "Light Armor";
    public static final String HEAVY = "Heavy Armor";
    public static final String MEDIUM = "Medium Armor";
    public static final String ELVEN_ARMOR = "Elven Armor";

    private String category;
    private int defenceConstant;
    private Character character;
    private int defence = 0;

    public Armor(String category, Character character) {
        if (isValidCategory(category)) {
            this.category = category;
            this.character = character; 
            this.defenceConstant = setDefenceConstant(category);
            this.defence = this.defenceConstant;
        } else {
            throw new IllegalArgumentException("Invalid armor category: " + category);
        }
    }

    private boolean isValidCategory(String category) {
        return category.equals(LIGHT) || category.equals(HEAVY) || category.equals(MEDIUM) || category.equals(ELVEN_ARMOR);
    }

    private int setDefenceConstant(String category) {
        switch (category) {
            case LIGHT:
            return ((int) (0.4 * character.getConstitution()) + (int) (0.2 * character.getAgility()));
            case HEAVY:
                return ((int) (character.getConstitution()) + (int) (0.2 * character.getStrength()));
            case MEDIUM:
            return (int) (0.6 * character.getConstitution() + (int) (0.2 * character.getStrength()));
            case ELVEN_ARMOR:
                return (int) (1.2 * character.getConstitution() + (int) (0.2 * character.getStrength()) + (int) (0.2 * character.getAgility()));
            default:
                return 0;
        }
    }

    public String getCategory() {
        return category;
    }

    public int doubleDefence(){
        return defence *= 2;
    }
    
    public int setDefence(){
    	return defence = defenceConstant;
    }
    
    public int getDefence() {
    	return defence;
    }
    
    @Override
    public String toString() {
        return category;
    }

}