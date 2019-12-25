package base;

import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;

import java.util.ArrayList;
import java.util.List;

public class Player {


    private final String name = "player";
    private final ArrayList<PokemonCreature> pokemons;
    private int selectedPokemon = 0;

    public Player(ArrayList<PokemonCreature> pokemons) {
        this.pokemons = pokemons;
    }

public void setSelectedPokemonId(PokemonCreature pok){
    selectedPokemon = pokemons.indexOf(pok);
    }
    public PokemonCreature getCurrentPokemon() {
    return pokemons.get(selectedPokemon);
    }

    public ArrayList<PokemonCreature> getPokemons() {
        return pokemons;
    }
}
