package Pokemons;

import utils.Collections;
import utils.InvalidFormatException;
import utils.Strings;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Capacities implements Serializable {

    /**
     * Default constructor
     */
    public Capacities() {
        buildCapacities();
    }

    private ArrayList<Capacity> capacities;
    private HashMap<Integer, Capacity> capacitiesMap;
    private HashMap<PokemonType, ArrayList<Capacity>> capacitiesMapByType;

    public Capacity getRandomCapacity(PokemonType type) {
        return Collections.getRandom(getCapacities(type));
    }
    public ArrayList<Capacity> getRandomCapacities(PokemonType type, int amount) {
        return Collections.getRandoms(getCapacities(type), amount);
    }
    /**
     * Return a list of capacities
     * @return List containing all the capacities
     */
    public ArrayList<Capacity> getCapacities() { return capacities; }
    public ArrayList<Capacity> getCapacities(PokemonType type) {
        return capacitiesMapByType.get(type);
    }
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
        capacitiesMapByType = new HashMap<>();
        int lineNumber = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/moves.csv"))))
        {
            String currLine;
            bufferedReader.readLine(); // zap first line : id,identifier,type,power,pp,accuracy,damage_class
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");
                lineNumber ++;

                if (splitedLine.length < 6) throw new InvalidFormatException();

                Capacity capacity = new Capacity(
                        Integer.parseInt(splitedLine[0]),
                        splitedLine[1],
                        Types.getType(splitedLine[2]),
                        (splitedLine[3].equals("")) ? 0: Integer.parseInt(splitedLine[3]),
                        Integer.parseInt(splitedLine[4]),
                        (splitedLine[5].equals("")) ? 100: Integer.parseInt(splitedLine[5]),
                        DamageClass.valueOf(Strings.noSpaces(splitedLine[6]))
                );

                // add the capacity to collections
                capacitiesMapByType.computeIfAbsent(capacity.getType(), k -> new ArrayList<Capacity>());
                capacitiesMapByType.get(capacity.getType()).add(capacity);
                capacities.add(capacity);
                capacitiesMap.put(capacity.getId(), capacity);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (InvalidFormatException | IllegalArgumentException e) {
            System.err.println("Error line " + lineNumber + " : " + e.toString());
        }
    }
}