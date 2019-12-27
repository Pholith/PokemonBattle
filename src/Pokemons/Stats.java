package Pokemons;

import managers.GameManager;
import utils.InvalidFormatException;
import utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Stats {

    public Stats() {
        buildBasicStats();
    }
    private HashMap<Integer, BaseStat> statMapId;
    public BaseStat getBaseStat(int id) {
        BaseStat stat = statMapId.get(id);
        if (stat == null ) {
            System.out.println("BaseStat of this pokemon dont't exist... Generating one random.");
            return getRandomStat(id);
        }
        return stat;
    }

    public BaseStat getRandomStat(int pokemonId) {
        PokemonDescriptor desc = GameManager.GetInstance().getPokedex().getFromId(pokemonId);
        //    public BaseStat(int id, String name, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed, int generation, boolean isLegendary) {
        return new BaseStat(
                pokemonId,
                desc.getName(),
                Objects.hash(pokemonId) % 100,
                Objects.hash(pokemonId) % 100,
                Objects.hash(Objects.hash(pokemonId)) % 100,
                Objects.hash(Objects.hash(pokemonId)) % 100,
                Objects.hash(Objects.hash(Objects.hash(pokemonId))) % 100,
                Objects.hash(Objects.hash(Objects.hash(Objects.hash(pokemonId)))) % 100,
                7,
                false);
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
