package Pokemons;

import utils.InvalidFormatException;
import utils.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Capacities {

    /**
     * Default constructor
     */
    public Capacities() {
        buildCapacities();
    }

    private ArrayList<Capacity> capacities;
    private HashMap<Integer, Capacity> capacitiesMap;

    /**
     * Return a list of capacities
     * @return List containing all the capacities
     */
    public List<Capacity> getCapacities() { return capacities; }

    /**
     * Get a Capacity searching by his id
     * @param id the name of the type
     * @return corresponding PokemonType
     */
    public Capacity getCapacity(int id) {
        return capacitiesMap.get(id);
    }

    private void buildCapacities() {
        capacities = new ArrayList<>();
        capacitiesMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/moves.csv"))))
        {
            String currLine;
            bufferedReader.readLine(); // zap first line : id,identifier,type,power,pp,accuracy,damage_class
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");

                if (splitedLine.length < 6) throw new InvalidFormatException();

                System.out.println(Arrays.toString(splitedLine));
                Capacity capacity = new Capacity(
                        Integer.parseInt(splitedLine[0]),
                        splitedLine[1],
                        Types.getType(splitedLine[2]),
                        (splitedLine[3].equals("")) ? 0: Integer.parseInt(splitedLine[3]),
                        Integer.parseInt(splitedLine[4]),
                        (splitedLine[5].equals("")) ? 100: Integer.parseInt(splitedLine[5]),
                        DamageClass.valueOf(Strings.noSpaces(splitedLine[6]))
                );
                capacities.add(capacity);
                capacitiesMap.put(capacity.getId(), capacity);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



}