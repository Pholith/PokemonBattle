package Pokemons;

import java.util.*;

//  A PokemonDescriptor is a descriptor of a PokemonCreature
public class PokemonDescriptor {

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
        this.types = new ArrayList<PokemonType>();
        for (PokemonType type: types ) {
            if (type != null && !type.toString().equals("")) this.types.add(type);
        }
    }

    private int ID;
    private String name;
    private String image;
    private float height;
    private float weight;
    private ArrayList<PokemonType> types;

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
    public List<PokemonType> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PokemonDescriptor{");
        sb.append("ID=").append(ID);
        sb.append(", name='").append(name).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", types=").append(types);
        sb.append('}');
        return sb.toString();
    }
}