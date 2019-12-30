package Pokemons;

import java.io.Serializable;

/**
 * 
 */
public class PokemonType implements Comparable<PokemonType>, Serializable {

    PokemonType(String name) {
        this.name = name;
    }

    private String name;

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(PokemonType o) {
        return name.compareTo(o.name);
    }
}