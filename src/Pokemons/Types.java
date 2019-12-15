package Pokemons;

import Pokemons.Utils.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Types {

    private static List<PokemonType> typesList;
    private static HashMap<String, PokemonType> typesMap;
    /**
     * Lazy getter of types
     * @return List containing all the types
     */
    public static List<PokemonType> getTypesList() {
        if (typesList == null) buildTypes();
        return typesList;
    }

    /**
     * Get a PoekmonType searching by his name
     * @param name the name of the type
     * @return corresponding PokemonType
     */
    public static PokemonType getType(String name) {
        if (typesMap == null) buildTypes();
        return typesMap.get(Strings.noSpaces(name));
    }

    /**
     * Build the types from the resources/grid_types.csv file
     * @return list containing all the pokemon types
     */
    private static void buildTypes() {
        typesList = new ArrayList<>();
        typesMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/grid_types.csv")))
        {
            String currLine;
            String typeName = "";
            currLine = bufferedReader.readLine();

            String[] splitedLine = currLine.split(",");
            for (int i = 2; i < splitedLine.length; i++) {
                typeName = Strings.noSpaces(splitedLine[i]);
                PokemonType pokeType = new PokemonType(typeName);
                typesList.add(pokeType);
                typesMap.put(typeName, pokeType);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
