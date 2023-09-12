public class Magic {
	public static final String FIRE = "Fire Magic";
	public static final String ICE = "Ice Magic";
	public static final String ELECTRICITY = "electricity Magic";
	public static final String POISON = "Poison Magic";
	public static final String DARK = "Dark Magic"; // Vardamir usará na batalha final.
	
	private String category;
	private Character character;
	
	public Magic(String category, Character Character){
		if (isValidCategory(category)) {
			this.category = category;
            this.character = character;
        } else {
            throw new IllegalArgumentException("Invalid magic category: " + category);
        }	
	}
	
	private boolean isValidCategory(String category) {
        return category.equals(FIRE) || category.equals(ICE) || category.equals(ELECTRICITY) || category.equals(POISON) || category.equals(DARK);
    }
	
	public String getCategory() {
		return category;
	}
	
	@Override
    public String toString() {
        return category;
    }
	
	
}
