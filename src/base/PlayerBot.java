package base;

import Pokemons.PokemonCreature;
import managers.GameManager;

import java.util.ArrayList;
import java.util.Random;

public class PlayerBot extends Player{

    public PlayerBot(ArrayList<PokemonCreature> pokemons, String name) {
        super(pokemons, name);
    }

    @Override
    public boolean isAutomaticPlayer(){
        return true;
    }

    @Override
    public void onPlayTurn() {


        boolean turnPassed = false;

        while (!turnPassed) {


            if (new Random().nextFloat() < 0.8d) {//80% de chance
                turnPassed = UseCapacity();
            } else {
                ChangePokemon();
            }
        }
    }


    boolean UseCapacity(){
        var capacity = getSelectedPokemon().getCapacities();
        GameManager.getBattleEvent().playerTurnCapacity(capacity.get(new Random().nextInt(capacity.size())));
        return true;
    }


    boolean ChangePokemon() {
        var pokemons = getPokemons();
        var newPokemon = pokemons.get(new Random().nextInt(pokemons.size()));

        if (newPokemon.IsDead() || newPokemon == getSelectedPokemon())
            return false;

        GameManager.getBattleEvent().playerTurnSwitchPokemon(newPokemon);


        return true;
    }



}
