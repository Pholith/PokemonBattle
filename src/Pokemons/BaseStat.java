package Pokemons;

import java.io.Serializable;

class BaseStat implements Serializable {
    private final int id;
    private final String name;
    private final int hp;
    private final int attack;
    private final int defense;
    private final int specialAttack;
    private final int specialDefense;
    private final int speed;
    private final int generation;
    private final boolean isLegendary;

    public BaseStat(int id, String name, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed, int generation, boolean isLegendary) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.generation = generation;
        this.isLegendary = isLegendary;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getSpecialAttack() {
        return specialAttack;
    }
    public int getSpecialDefense() {
        return specialDefense;
    }
    public int getSpeed() {
        return speed;
    }
    public int getGeneration() {
        return generation;
    }
    public boolean isLegendary() {
        return isLegendary;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseStat{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", hp=").append(hp);
        sb.append(", attack=").append(attack);
        sb.append(", defense=").append(defense);
        sb.append(", specialAttack=").append(specialAttack);
        sb.append(", specialDefense=").append(specialDefense);
        sb.append(", speed=").append(speed);
        sb.append(", generation=").append(generation);
        sb.append(", isLegendary=").append(isLegendary);
        sb.append('}');
        return sb.toString();
    }
}
