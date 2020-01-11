package Pokemons;

public class DamageCalculator {
    public DamageCalculator() {
    }

    // damage calcul https://www.pokepedia.fr/Calcul_des_d%C3%A9g%C3%A2ts
    public int calcul(int level, int power, int attack, int defense, double multiplier) {
        return (int) ((((level * 0.4 + 2) * power * attack ) / ( defense * 50 ) + 2 ) * multiplier);
    }
}
