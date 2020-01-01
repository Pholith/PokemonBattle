package Pokemons;

import java.util.*;

//  A PokemonDescriptor is a descriptor of a PokemonCreature
public class PokemonDescriptor  implements java.io.Serializable {

    /**
     * @param ID
     * @param name
     * @param image
     * @param height
     * @param weight
     * @param types
     */
    public PokemonDescriptor(int ID, String name, String image, float height, float weight, PokemonType... types) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.types = new HashSet<PokemonType>();
        for (PokemonType type: types ) {
            if (type != null && !type.toString().equals("")) this.types.add(type);
        }
    }

    private int ID;
    private String name;
    private String image;
    private float height;
    private float weight;
    private HashSet<PokemonType> types;

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public float getHeight() {
        return height;
    }
    public float getWeight() {
        return weight;
    }
    public HashSet<PokemonType> getTypes() {
        return types;
    }

    public String typesToString() {
        final StringBuilder sb = new StringBuilder();
        for (PokemonType type : types) {
            sb.append(type.toString()).append(" ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name);
        return sb.toString();
    }

    public PokemonDescriptorBean createBean() {
        return new PokemonDescriptorBean(name, typesToString());
    }
}