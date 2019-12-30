package base;

import Pokemons.PokemonCreature;
import managers.GameManager;

import java.util.List;
import java.util.Random;

public class PlayerBot extends Player{

    public PlayerBot(List<PokemonCreature> pokemons, String name) {
        super(pokemons, name);
    }

    @Override
    public boolean isAutomaticPlayer(){
        return true;
    }

    @Override
    public void onPlayTurn() {

        var capacity = getCurrentPokemon().getCapacities();
        var pokemons = getPokemons();


        if (new Random().nextFloat() < 0.8d) {
            GameManager.getBattleEvent().playerTurnCapacity(capacity.get(new Random().nextInt(capacity.size())));
    }else{
            GameManager.getBattleEvent().playerTurnSwitchPokemon(pokemons.get(new Random().nextInt(pokemons.size())));
        }
    }


}
