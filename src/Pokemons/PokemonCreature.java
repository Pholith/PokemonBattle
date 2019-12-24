package Pokemons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 */
public class PokemonCreature {

    /**
     * test
     * @param descriptor
     * @param capacities
     */
    public PokemonCreature(PokemonDescriptor descriptor, List<Capacity> capacities) {
        this.descriptor = descriptor;
        this.capacities = new ArrayList<>();
        //this.capacities.addAll(capacities);
        hp = 100;
        attack = Objects.hash(descriptor.getID()) % 100;
        defense = Objects.hash(attack) % 100;
        specialAttack = Objects.hash(defense) % 100;
        specialDefense = Objects.hash(specialAttack) % 100;
        speed = Objects.hash(specialDefense) % 100;
    }

    private final PokemonDescriptor descriptor;
    private ArrayList<Capacity> capacities;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public PokemonDescriptor getDescriptor() {
        return descriptor;
    }
    public ArrayList<Capacity> getCapacities() {
        return capacities;
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
}