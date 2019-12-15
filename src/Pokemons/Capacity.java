package Pokemons;

/**
 *
 */
public class Capacity {

    /** Represent a Pokemon Capacity
     *
     * @param id
     * @param name
     * @param type
     * @param power
     * @param pp
     * @param accuracy
     * @param damageClass
     */
    public Capacity(int id, String name, PokemonType type, int power, int pp, int accuracy, DamageClass damageClass) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
        this.damageClass = damageClass;
    }
    private final int id;
    private final String name;
    private final PokemonType type;
    private final int power;
    private final int pp;
    private final int accuracy;
    private final DamageClass damageClass;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public PokemonType getType() {
        return type;
    }
    public int getPower() {
        return power;
    }
    public int getPp() {
        return pp;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public DamageClass getDamageClass() {
        return damageClass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Capacity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", power=").append(power);
        sb.append(", pp=").append(pp);
        sb.append(", accuracy=").append(accuracy);
        sb.append(", damageClass=").append(damageClass);
        sb.append('}');
        return sb.toString();
    }
}