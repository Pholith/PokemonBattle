package Pokemons;

import utils.ISerializableFunction;

import java.io.Serializable;

public class PokemonObject implements Serializable {

    private String name;
    private ISerializableFunction<PokemonCreature, Integer> effect;
    private boolean used;
    private String message;
    private boolean effectOnActivePokemon;

    private static class OBJECT_MESSAGE {
        public static final String POTION = "%s regagne %dHPs !";
        public static final String RAPPEL = "%s est réanimé ! Il regagne %dHPs !";
        public static final String DEFENSE_BOOST = "%s gagne %d points de défense et de défense spé !";
    }

    private PokemonObject(String name, ISerializableFunction<PokemonCreature, Integer> effect, String message, boolean effectOnActivePokemon) {
        this.name = name;
        this.effect = effect;
        this.used = false;
        this.message = message;
        this.effectOnActivePokemon = effectOnActivePokemon;
    }

    public boolean isEffectOnActivePokemon() {
        return effectOnActivePokemon;
    }
    public String getName() {
        return name;
    }
    public String applyEffect(PokemonCreature target) {
        if (used) throw new IllegalStateException();
        used = true;
        return String.format(message, target.getDescriptor().getName(), effect.apply(target));
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public String toString() {
        return (isEffectOnActivePokemon()) ? name  : name +" >";
    }

    // FACTORY OF OBJECTS
    public static PokemonObject createPotion() {
        return new PokemonObject("potion", pokemonCreature -> {
            int heal = 30;
            pokemonCreature.heal(heal);
            return heal;
        }, OBJECT_MESSAGE.POTION, true);
    }

    public static PokemonObject createSuperPotion() {
        return new PokemonObject("super potion", pokemonCreature -> {
            int heal = 60;
            pokemonCreature.heal(heal);
            return heal;
        }, OBJECT_MESSAGE.POTION, true);
    }

    public static PokemonObject createRappel() {
        return new PokemonObject("rappel", PokemonCreature::reviveAndHeal, OBJECT_MESSAGE.POTION, false);
    }

    public static PokemonObject createDefenseBoost() {
        return new PokemonObject("defense boost", pokemonCreature -> {
            int amount = 30;
            pokemonCreature.boostDefenses(amount);
            return amount;
        }, OBJECT_MESSAGE.DEFENSE_BOOST, true);
    }
}
