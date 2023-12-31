import java.util.ArrayList;
import java.util.Random;

class Character {
    private String name;
    int hp;
    int originalHp;
    private int strength;
    private int constitution;
    private int agility;
    private int dexterity;
    private Weapon weapon;
    private Armor armor;
    private ArrayList<Magic> magic;
    private Potion potion;
    private int fireEffectTurns = 0;
    private int poisonEffectTurns = 0;
    private int coldEffectTurns = 3;
    private int shockEffectTurns = 3;
    private int necroticEffectTurns = 3;

    private int healUses = -1;

    public Character(String name, int strength, int constitution, int agility, int dexterity, Weapon weapon,
            Armor armor, Potion potion) {
        this.name = name;
        this.strength = strength;
        this.constitution = constitution;
        this.agility = agility;
        this.dexterity = dexterity;
        this.weapon = weapon;
        this.armor = armor;
        this.potion = potion;
        this.hp = calculateHitPoints();
        this.originalHp = hp;
        this.magic = new ArrayList<Magic>();
    }

    private int calculateHitPoints() {
        return rollD12() + rollD6() + rollD6() + constitution;
    }

    public int calculateDamage() {
        int weaponDamage = weapon.getDamageConstant();

        switch (weapon.getCategory()) {
            case Weapon.LONG_SWORD:
            weaponDamage += rollD12() - 2;
            weaponDamage += (int) (0.5 * strength);
                break;
            case Weapon.DAGGER:
            weaponDamage += rollD6() + rollD6() + 2;
            weaponDamage += (int) (0.4 * dexterity);
                break;
            case Weapon.BOW:
            weaponDamage += rollD6() + rollD6() + 2;
            weaponDamage += (int) (0.4 * agility);
                break;
            case Weapon.CROSSBOW:
            weaponDamage += rollD12() - 2;
            weaponDamage += (int) (0.5 * dexterity);
                break;
            case Weapon.ELVEN_BLADE:
            weaponDamage += rollD12() + rollD6() - 2;
            weaponDamage += ((int) (0.5 * strength) + (int) (0.3 * dexterity)) + (int) (0.3 * agility);
                break;
            case Weapon.SPEAR:
            weaponDamage += rollD6() + rollD6() + 2;
            weaponDamage += (int) (0.6 * agility);
                break;
            case Weapon.AXE:
            weaponDamage += rollD12() + 2;
            weaponDamage += (int) (0.6 * strength);
                break;
            case Weapon.LONG_BOW:
            weaponDamage += rollD12() - 2;
            weaponDamage += (int) (0.5 * agility);
                break;
        }

        return Math.max(0, weaponDamage);
    }

    public int heal() {
        if (healUses < 3) {
            healUses++;
            int healingAmount = potion.getHealing();
            hp += healingAmount;
            return healingAmount;
        } else {
            return 0;
        }
    }

    public int getHeal() {
        if (healUses < 3) {
            return potion.getHealing();
        } else {
            return 0;
        }
    }

    public int getHealUses() {
        return healUses;
    }

    public int setHealUses(int amount) {
        return healUses = amount;
    }

    public Magic[] getMagics() {
        return magic.toArray(new Magic[magic.size()]);
    }

    public String getMagic(int index) {
        return magic.get(index).toString();
    }

    public void castMagic(Character target, int index, String magicCategory) {
        magic.get(index).cast(target, magicCategory);
    }

    public void addMagic(Magic magic) {
        this.magic.add(magic);
    }

    private int rollD12() {
        Random rand = new Random();
        return rand.nextInt(12) + 1;
    }

    private int rollD6() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public int getHitPoints() {
        return hp;
    }

    public int getOriginalHitPoints(){
        return originalHp;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    public void setHp(int amount){
        this.hp = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getTotalDamage() {
        int weaponDamage = calculateDamage();
        return weaponDamage;
    }

    public void reduceAgility(int amount) {
        agility -= amount;
    }

    public void reduceDexterity(int amount) {
        dexterity -= amount;
    }

    public void addAgility(int amount) {
        agility += amount;
    }

    public void addDexterity(int amount) {
        dexterity += amount;
    }

    public void addConstitution(int amount) {
        constitution += amount;
    }

    public void addStrength(int amount) {
        strength += amount;
    }

    public void addOriginalHitPoints(int amount) {
        originalHp += amount;
    }

    public void reduceConstitution(int amount) {
        constitution -= amount;
    }

    public void reduceStrength(int amount) {
        strength -= amount;
    }

    public int getFireEffectTurns() {
        return fireEffectTurns;
    }

    public void setFireEffectTurns() {
        fireEffectTurns = 4;
    }

    public int getPoisonEffectTurns() {
        return poisonEffectTurns;
    }

    public void setPoisonEffectTurns() {
        poisonEffectTurns = 6;
    }

    public int getColdEffectTurns() {
        return coldEffectTurns;
    }

    public void setColdEffectTurns() {
        coldEffectTurns = 3;
    }

     public void removeColdEffectTurns() {
        coldEffectTurns--;
    }

    public int getNecroticEffectTurns() {
        return necroticEffectTurns;
    }

    public void setNecroticEffectTurns() {
        necroticEffectTurns = 3;
    }

    public void removeNecroticEffectTurns() {
        necroticEffectTurns--;
    }
   
    public int getShockEffectTurns() {
        return shockEffectTurns;
    }

    public void setShockEffectTurns() {
        shockEffectTurns = 3;
    }

    public void removeShockEffectTurns() {
        shockEffectTurns--;
    }

    public void applyFireEffect() {
        int fireTurnDamage = 3;
        receiveDamage(fireTurnDamage);
        fireEffectTurns--;
    }

    public void applyPoisonEffect() {
        int poisonTurnDamage = 2;
        receiveDamage(poisonTurnDamage);
        poisonEffectTurns--;
    }

    public void applyColdEffect() {
        reduceAgility(2);
        reduceDexterity(2);
        reduceConstitution(1);
    }

    public void applyShockEffect() {
        reduceDexterity(2);
    }

    public void removeColdEffect() {
        addAgility(2);
        addDexterity(2);
        addConstitution(1);
    }

    public void applyNecroticEffect() {
        reduceStrength(2);
        necroticEffectTurns--;
    }

    public void removeNecroticEffect() {
        addStrength(2);
    }

    @Override
    public String toString() {
        return name + "\nHP: " + getHitPoints() + "\nStrength: " + getStrength() + "\nConstitution: "
                + getConstitution()
                + "\nAgility: " + getAgility() + "\nDexterity: " + getDexterity();
    }

    public String getCharacter(){
         return name + "\nHP: " + getHitPoints() + "\nStrength: " + getStrength() + "\nConstitution: "
                + getConstitution()
                + "\nAgility: " + getAgility() + "\nDexterity: " + getDexterity() + "\nWeapon: "
                + getWeapon().toString()
                + "\nArmor: " + getArmor().toString() + "\n";
    }
}