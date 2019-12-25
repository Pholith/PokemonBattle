package base;

import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;

import java.util.ArrayList;
import java.util.List;

public class Player {


    private final String name = "aplayer";
public ArrayList<PokemonCreature> pokemons = new ArrayList<PokemonCreature>();


    public PokemonCreature getCurrentPokemon() {
    return pokemons.get(0);
    }
}
