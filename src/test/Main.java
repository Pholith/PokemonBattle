package test;

import Pokemons.*;

public class Main {

    public static void main1(String[] args) {

        System.out.println(Types.getTypesList());

        Pokedex poke = new Pokedex();

        for (PokemonDescriptor descriptor : poke.getPokemons()) {
            System.out.println(descriptor);
        }

        Capacities capacities = new Capacities();
        for (Capacity capacity : capacities.getCapacities()) {
            System.out.println(capacity);
        }
    }
}
