package base;

import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;
import javafx.scene.control.ListView;


import java.util.List;

public class Player {


    private final String name;

    public String getName() {
        return name;
    }

    private final List<PokemonCreature> pokemons;
    private PokemonCreature selectedPokemon;

    public Player(List<PokemonCreature> pokemons, String playerName) {
        this.pokemons = pokemons;
        this.name = playerName;
        this.selectedPokemon = pokemons.get(0);
    }

    protected List<PokemonCreature> getPokemons(){return pokemons;}


    public void setSelectedPokemonId(PokemonCreature pok) {
        selectedPokemon = pok;
    }



    public PokemonCreature getCurrentPokemon() {
        return selectedPokemon;
    }
    public PokemonCreature getFirstPokemonAlive() {

        for (var pok : pokemons) {
            if(!pok.IsDead())
                return  pok;
        }

        return null;
    }


    public boolean isAutomaticPlayer(){
        return false;
    }

    public void fillUiList(ListView<Capacity> list_capacity, ListView<PokemonCreature> list_swichPokemon) {

        var atckList = selectedPokemon.getCapacities();


        list_capacity.getItems().clear();
        for (var act : atckList)
            list_capacity.getItems().add(act);



        list_swichPokemon.getItems().clear();
        for (var act : pokemons) {
            if (act != selectedPokemon)
                list_swichPokemon.getItems().add(act);
        }
    }


    public void onPlayTurn() {
    }

}
