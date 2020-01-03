package Pokemons;

import base.PlayerBot;

import java.util.ArrayList;
import java.util.Objects;

public class LigueDresser extends PlayerBot {

    private boolean defeated;
    private final int level;

    LigueDresser(ArrayList<PokemonCreature> pokemons, int level) {
        super(pokemons, "Pokemon dresser " + level);
        this.level = level;
        this.defeated = false;
    }
    @Override
    public void defeat() {
        super.defeat();
        System.out.println("defeat");
        defeated = true;
    }
    public boolean isDefeated() {
        return defeated;
    }
    public int getLevel() {
        return level;
    }

    public String getName() {
        return "Pokemon dresser " + level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getName());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigueDresser ligueDresser = (LigueDresser) o;
        return defeated == ligueDresser.defeated &&
                level == ligueDresser.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(defeated, level);
    }
}
