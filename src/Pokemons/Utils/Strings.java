package Pokemons.Utils;

public class Strings {

    /**
     * Remove WhitesSpaces from a String
     * @param string
     * @return The string without whitespace
     */
    public static String noSpaces(String string) {
        return string.replaceAll("\\s","");
    }
}
