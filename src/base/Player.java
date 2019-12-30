package base;

import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import javafx.scene.control.ListView;


import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Serializable {


    private final String name;

    public String getName() {
        return name;
    }

    private final ArrayList<PokemonCreature> pokemons;
    private int selectedPokemon;


    public Player(ArrayList<PokemonCreature> pokemons, String playerName) {
        this.pokemons = new ArrayList<>();
        this.pokemons.addAll(pokemons);
        this.name = playerName;
        this.selectedPokemon = 0;
    }


    protected ArrayList<PokemonCreature> getPokemons(){return pokemons;}


    public void setSelectedPokemonId(PokemonCreature pok) {
        selectedPokemon =   pokemons.indexOf(pok);
    }




    public PokemonCreature getSelectedPokemon() {
        if(selectedPokemon == -1)
            return null;
        return pokemons.get(selectedPokemon);
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

        var pok =  getSelectedPokemon();
        var atckList = pok.getCapacities();


        list_capacity.getItems().clear();
        for (var act : atckList)
            list_capacity.getItems().add(act);



        list_swichPokemon.getItems().clear();
        for (var act : pokemons) {
            if (act != pok)
                list_swichPokemon.getItems().add(act);
        }
    }


    public void onPlayTurn() {
    }

}
