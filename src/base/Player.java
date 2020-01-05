package base;

import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import Pokemons.PokemonObject;
import javafx.scene.control.ListView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class Player implements Serializable {


    private final String name;

    public String getName() {
        return name;
    }

    private final ArrayList<PokemonCreature> pokemons;
    private final ArrayList<PokemonObject> objects;

    private int selectedPokemon;


    public Player(ArrayList<PokemonCreature> pokemons, String playerName) {
        this.pokemons = new ArrayList<>();
        this.objects = new ArrayList<>();

        // Objets de départ du joueur
        Collections.addAll(objects,
                PokemonObject.createPotion(),
                PokemonObject.createPotion(),
                PokemonObject.createSuperPotion(),
                PokemonObject.createDefenseBoost(),
                PokemonObject.createRappel());

        this.pokemons.addAll(pokemons);
        this.name = playerName;
        this.selectedPokemon = 0;
    }


    public ArrayList<PokemonCreature> getPokemons() {return pokemons;}

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
    public void fillUiList(ListView<Capacity> list_capacity,
                           ListView<PokemonCreature> list_swichPokemon,
                           ListView<PokemonObject> list_objects,
                           ListView<PokemonCreature> list_pokemonsForObject
                           ) {

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
        list_objects.getItems().clear();
        for (var act : objects) {
            if (!act.isUsed())
                list_objects.getItems().add(act);
        }
        list_pokemonsForObject.getItems().clear();
        for (var act : pokemons) {
            list_pokemonsForObject.getItems().add(act);
        }
    }
    public void resetPokemons() {
        for (PokemonCreature p: pokemons) {
            p.reset();
        }
    }
    public void onPlayTurn() {
    }
    public void defeat() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("name='").append(name).append('\'');
        sb.append(", pokemons=").append(pokemons);
        sb.append(", selectedPokemon=").append(selectedPokemon);
        sb.append('}');
        return sb.toString();
    }

    public void updateCloneTeam(ArrayList<PokemonCreature> teamPlayer1) {
        teamPlayer1.clear();
        teamPlayer1.addAll(this.pokemons);
    }
}
