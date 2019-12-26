package Pokemons;

import utils.InvalidFormatException;
import utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Stats {

    public Stats() {
        buildBasicStats();
    }
    private HashMap<Integer, BaseStat> statMapId;
    public BaseStat getBaseStat(int id) {
        return statMapId.get(id);
    }

    private void buildBasicStats() {
        statMapId =  new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/pokemon_stats.csv"))))
        {
            String currLine;
            bufferedReader.readLine(); //#,Name,Type 1,Type 2,Total,HP,Attack,Defense,Sp. Atk,Sp. Def,Speed,Generation,Legendary
            while ((currLine = bufferedReader.readLine()) != null)
            {
                String[] splitedLine = currLine.split(",");

                if (splitedLine.length < 5) throw new InvalidFormatException();

                BaseStat stat = new BaseStat(
                        Integer.parseInt(splitedLine[0]),
                        Strings.noSpaces(splitedLine[1]),
                        Integer.parseInt(splitedLine[5]), // hp
                        Integer.parseInt(splitedLine[6]),
                        Integer.parseInt(splitedLine[7]),
                        Integer.parseInt(splitedLine[8]), //spe atk
                        Integer.parseInt(splitedLine[9]),
                        Integer.parseInt(splitedLine[10]),
                        Integer.parseInt(splitedLine[11]),
                        Boolean.parseBoolean(splitedLine[12])

                );
                statMapId.putIfAbsent(Integer.parseInt(splitedLine[0]), stat);
            }

        }
        catch (IOException e)
        {
            System.err.println(e.toString());
        }
    }
}
