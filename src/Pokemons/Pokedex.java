package Pokemons;

import utils.InvalidFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private ArrayList<PokemonDescriptor> pokemons;

    private void buildPokemons() {
        pokemons = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/pokedex.csv")))
        {
            String currLine;
            bufferedReader.readLine(); // zap first line : id,identifier,picture,height,weight,type1,type2
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");

                if (splitedLine.length < 5) throw new InvalidFormatException();

                pokemons.add(new PokemonDescriptor(
                        Integer.parseInt(splitedLine[0]),
                        splitedLine[1],
                        splitedLine[2],
                        Float.parseFloat(splitedLine[3]),
                        Float.parseFloat(splitedLine[4]),
                        Types.getType(splitedLine[5]),
                        (splitedLine.length > 6) ? Types.getType(splitedLine[6]) : null
                ));
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}