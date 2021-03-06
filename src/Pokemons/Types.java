package Pokemons;

import utils.InvalidFormatException;
import utils.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Types implements Serializable {

    private static ArrayList<PokemonType> typesList;
    private static HashMap<String, PokemonType> typesMap;
    /**
     * Lazy getter of types
     * @return List containing all the types
     */
    public static ArrayList<PokemonType> getTypesList() {
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
        PokemonType type = typesMap.get(Strings.noSpaces(name));
        if  (name.equals("")) return null;
        if (type == null) throw new InvalidFormatException("This type was not found:"+name+"\"");
        return typesMap.get(Strings.noSpaces(name));
    }

    /**
     * Build the types from the resources/grid_types.csv file
     * @return list containing all the pokemon types
     */
    private static void buildTypes() {
        typesList = new ArrayList<>();
        typesMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Types.class.getResourceAsStream("/resources/grid_types.csv"))))
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
