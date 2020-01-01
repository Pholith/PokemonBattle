package Pokemons;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 */
public class PokemonType implements Comparable<PokemonType>, Serializable {

    public PokemonType(String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonType that = (PokemonType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}