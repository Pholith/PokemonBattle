package Pokemons;

import javafx.beans.property.SimpleStringProperty;

public class PokemonDescriptorBean  {
    private SimpleStringProperty name;
    private SimpleStringProperty types;

    public String getName() {
        return name.get();
    }
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }
    public String getTypes() {
        return types.get();
    }
    public void setTypes(SimpleStringProperty types) {
        this.types = types;
    }

    public PokemonDescriptorBean(String name, String types) {
        super();
        this.name = new SimpleStringProperty(name);
        this.types = new SimpleStringProperty(types);
    }
}
