package test;

import Pokemons.*;
import managers.DamageManager;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        System.out.println(Types.getTypesList());

        Pokedex poke = new Pokedex();

        for (PokemonDescriptor descriptor : poke.getPokemons()) {
            System.out.println(descriptor);
        }

        Capacities capacities = new Capacities();
        for (Capacity capacity : capacities.getCapacities()) {
            System.out.println(capacity);
        }

        DamageManager damageManager = new DamageManager();

        for (AttackAndTargetTypes t : damageManager.getGrid_type().keySet()) {
            System.out.println(t);
            System.out.println(damageManager.getGrid_type().get(t));
        }
    }
}
