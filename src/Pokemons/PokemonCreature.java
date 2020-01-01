package Pokemons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 */
public class PokemonCreature  implements Serializable {

    private final PokemonDescriptor descriptor;
    private final ArrayList<Capacity> capacities;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private boolean isDead;
    private BaseStat baseStat;

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
    public boolean IsDead() {
        return isDead;
    }
    public BaseStat getBaseStat() {return baseStat; }
    public int getStartHp() { return baseStat.getHp(); }

    /**
     * A PokemonCreature is a mob
     * @param descriptor the descriptor of this pokemon
     * @param stat the normal base stats of this pokemon
     * @param capacities the choiced capacities of this pokemon
     */
    public PokemonCreature(PokemonDescriptor descriptor, BaseStat stat, List<Capacity> capacities) {
        this.descriptor = descriptor;
        this.capacities = new ArrayList<>();
        if(capacities != null)
        this.capacities.addAll(capacities);
        baseStat = stat;
        //this.capacities.addAll(capacities);
        hp = stat.getHp();
        attack = stat.getAttack();
        defense = stat.getDefense();
        specialAttack = stat.getSpecialAttack();
        specialDefense = stat.getSpecialDefense();
        speed = stat.getSpeed();
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if(hp <= 0){
            isDead = true;
            hp = 0;
        }
    }
    // Reset stats of a pokemon after a fight
    public void reset() {
        hp = baseStat.getHp();
        attack = baseStat.getAttack();
        defense = baseStat.getDefense();
        specialAttack = baseStat.getSpecialAttack();
        specialDefense = baseStat.getSpecialDefense();
        speed = baseStat.getSpeed();
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(descriptor.getName());

        sb.append("\n").append(hp).append("/").append(baseStat.getHp()).append("Hp");
        return sb.toString();
    }
}