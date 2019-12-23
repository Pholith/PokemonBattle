package Pokemons;

import utils.InvalidFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
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
    public Pokedex() {
        buildPokemons();
    }

    public ArrayList<PokemonDescriptor> getPokemons() {
        return pokemons;
    }
    public PokemonDescriptor getFromName(String name) {
        return pokemonMapName.get(name);
    }
    private ArrayList<PokemonDescriptor> pokemons;
    private static HashMap<String, PokemonDescriptor> pokemonMapName;

    private void buildPokemons() {
        pokemons = new ArrayList<>();
        pokemonMapName = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/pokedex.csv"))))
        {
            String currLine;
            bufferedReader.readLine(); // zap first line : id,identifier,picture,height,weight,type1,type2
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");

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
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}