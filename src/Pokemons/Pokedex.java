package Pokemons;

import managers.GameManager;
import utils.Collections;
import utils.InvalidFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 
 */
public class Pokedex {

    /**
     * Default constructor
     */
    public Pokedex(Stats stats, Capacities capacities) {
        buildPokemons();
        this.stats = stats;
        this.capacities = capacities;
    }

    private Stats stats;
    private Capacities capacities;
    public ArrayList<PokemonDescriptor> getPokemons() {
        return pokemons;
    }
    public PokemonDescriptor getFromName(String name) {
        return pokemonMapName.get(name);
    }
    public PokemonDescriptor getFromId(int id) {
        return pokemonMapId.get(id);
    }
    private ArrayList<PokemonDescriptor> pokemons;
    private static HashMap<String, PokemonDescriptor> pokemonMapName;
    private static HashMap<Integer, PokemonDescriptor> pokemonMapId;

    public ArrayList<PokemonCreature> getRandomTeam(int numberOfPokemons) {
        ArrayList<PokemonCreature> team = new ArrayList<>();
        for (int i = 0; i < numberOfPokemons; i++) {
            PokemonDescriptor desc = getRandomDescriptor();
            team.add(new PokemonCreature(
                    desc,
                    stats.getBaseStat(desc.getID()),
                    capacities.getRandomCapacities(Collections.getRandom(desc.getTypes()), 2)));
        }
        return team;
    }

    private PokemonDescriptor getRandomDescriptor() {
        return pokemons.get((int) (Math.random()*pokemons.size()));
    }

    private void buildPokemons() {
        pokemons = new ArrayList<>();
        pokemonMapName = new HashMap<>();
        pokemonMapId = new HashMap<>();

        int lineNumber = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/pokedex.csv"))))
        {
            String currLine;
            bufferedReader.readLine(); // zap first line : id,identifier,picture,height,weight,type1,type2
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");
                lineNumber ++;
                if (splitedLine.length < 5) throw new InvalidFormatException();

                PokemonDescriptor descriptor = new PokemonDescriptor(
                        Integer.parseInt(splitedLine[0]),
                        splitedLine[1],
                        "pokemons_img/"+splitedLine[2],
                        Float.parseFloat(splitedLine[3]),
                        Float.parseFloat(splitedLine[4]),
                        Types.getType(splitedLine[5]),
                        (splitedLine.length > 6) ? Types.getType(splitedLine[6]) : null
                );
                pokemons.add(descriptor);
                pokemonMapName.put(splitedLine[1], descriptor);
                pokemonMapId.put(Integer.parseInt(splitedLine[0]), descriptor);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (NumberFormatException | InvalidFormatException e) {
            System.err.println("Error line " + lineNumber + " : " + e.toString());
        }
    }
}