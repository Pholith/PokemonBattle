package Pokemons;

/**
 * 
 */
public class PokemonType implements Comparable<PokemonType> {

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