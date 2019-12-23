package Pokemons;

public class Attack {


    ///TEMPORAIRE/A REFAIRE

    private int damages = 10;
    private DamageClass type = DamageClass.physical;
    private String name;
    private String descript;

    public DamageClass getType() {
        return type;
    }
    public int getDamages() {
        return damages;
    }
    public String getDescript() {
            return descript;
    }
    public String getName() {
        return name;
    }

    public Attack(String name, String descript) {

        this.name = name;
        this.descript = descript;
    }

    @Override
    public String toString() {
        return name + "\n" + descript;
    }
}
