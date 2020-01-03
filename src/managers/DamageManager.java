package managers;

import Pokemons.*;
import utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class DamageManager {

    public DamageManager() {
        buildGrid();
    }

    static class EFFECTIVITY {
        static final String NONE = "";
        static final String FAIL = "L'attaque est loupée !\n";
        static final String NOT_EFFECTIVE = "Ce n'est pas très efficace.\n";
        static final String EFFECTIVE = "";
        static final String SUPER_EFFECTIVE = "C'est super efficace !\n";
    }

    public HashMap<AttackAndTargetTypes, Double> getGrid_type() {
        return grid_type;
    }

    private HashMap<AttackAndTargetTypes, Double> grid_type;

    /**
     * Apply a capacty
     * @param capacity the capacity used
     * @param user the pokemon who uses the capacity
     * @param target the enemy pokemon
     * @return return the effectivity of the attack
     */
    String applyCapacity(Capacity capacity, PokemonCreature user, PokemonCreature target) {

        if (capacity.getDamageClass() == DamageClass.statut) {
            user.receiveDamage(-(capacity.getPower()/2));
            return EFFECTIVITY.NONE;
        }
        int defense;
        int attack;
        int level = 10;
        if (capacity.getDamageClass() == DamageClass.physical) {
            defense = target.getDefense();
            attack = user.getAttack();
        } else {
            defense = target.getSpecialDefense();
            attack = user.getSpecialAttack();
        }

        if (Math.random() * 100 > capacity.getAccuracy()) return EFFECTIVITY.FAIL;

        assert grid_type != null;
        assert capacity.getType() != null;
        assert target.getDescriptor() != null;
        assert target.getDescriptor().getTypes() != null;

        AttackAndTargetTypes key = new AttackAndTargetTypes(capacity.getType(), target.getDescriptor().getTypes());
        double multiplier;
        try {
            multiplier = grid_type.get(key);
        } catch (NullPointerException e) {
            System.err.println("KEY " + key + "NOT FOUND");
            multiplier = 1;
        }

        // damage calcul https://www.pokepedia.fr/Calcul_des_d%C3%A9g%C3%A2ts
        int damage = (int) ((((level * 0.4 + 2) * capacity.getPower()* attack ) / ( defense * 50 ) + 2 ) * multiplier);
        target.receiveDamage(damage);

        if (multiplier <= 0.5) return EFFECTIVITY.NOT_EFFECTIVE;
        if (multiplier >= 2) return EFFECTIVITY.SUPER_EFFECTIVE;
        return EFFECTIVITY.EFFECTIVE;
    }

    private void buildGrid() {
        grid_type = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Types.class.getResourceAsStream("/resources/grid_types.csv")))) {
            String currLine;
            bufferedReader.readLine();

            while ((currLine = bufferedReader.readLine()) != null) {
                String[] splitedLine = currLine.split(",");

                HashSet<PokemonType> types = new HashSet<>();
                PokemonType type1 = Types.getType(Strings.noSpaces(splitedLine[0]));
                PokemonType type2 = Types.getType(Strings.noSpaces(splitedLine[1]));
                if (type1 != null) types.add(type1);
                if (type2 != null) types.add(type2);

                String[] typesLine = new String[] {"steel", "fighting", "dragon", "water", "electric", "fairy", "fire", "ice", "bug", "normal", "grass", "poison", "psychic", "rock", "ground", "ghost", "dark", "flying"};
                for (int i = 0; i < typesLine.length; i++) {
                    grid_type.put(new AttackAndTargetTypes(Types.getType(typesLine[i]), types), Double.parseDouble(splitedLine[i+2]));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
