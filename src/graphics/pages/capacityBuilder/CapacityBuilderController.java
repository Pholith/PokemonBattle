package graphics.pages.capacityBuilder;

import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;
import Pokemons.PokemonType;
import graphics.pages.TeamBuilder.TeamBuilderController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.GameManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CapacityBuilderController implements Initializable {

    @FXML
    private Label pokemonName;
    @FXML
    private ListView<Capacity> capacityListUi;
    @FXML
    private ListView<Capacity> allCapacityListUi;
    @FXML
    private ImageView imagePreview;

    private TeamBuilderController parentController;
    // Build the pokemon with it given descriptor and choiced capacities
    private final PokemonDescriptor desc;
    private PokemonCreature getPokemon() {
        return new PokemonCreature(desc, capacityListUi.getItems());
    }

    @FXML
    private void onClickExit(ActionEvent event) {
        Stage stage = (Stage) pokemonName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickAddPokemon(ActionEvent event) {
        parentController.addClickAddPokemon(getPokemon());
        Stage stage = (Stage) pokemonName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCapacityAdd(MouseEvent event) {
        Capacity selected = allCapacityListUi.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        if (capacityListUi.getItems().size() > 3) return;
        capacityListUi.getItems().add(selected);
    }

    @FXML
    private void onCapaciyRemove(MouseEvent event) {
        Capacity selected = capacityListUi.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        capacityListUi.getItems().remove(selected);
    }

    /**
     * This Constructor enable to manually instanciate the constructor and to give it arguments
     * @param desc the pokemon descriptor
     */
    public CapacityBuilderController(TeamBuilderController parentController, PokemonDescriptor desc) {
        this.desc = desc;
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert desc != null;

        pokemonName.setText(desc.getName());

        // load the capacities of the pokemon type
        ArrayList<Capacity> capacityList = new ArrayList<>();
        for (PokemonType type : desc.getTypes()) {
            capacityList.addAll(GameManager.GetInstance().getCapacities().getCapacities(type));
        }
        allCapacityListUi.setItems(FXCollections.observableList(capacityList));

        // Set the image
        try {
            Image image = new Image(getClass().getResource("/resources/"+desc.getImage()).toString());
            imagePreview.setImage(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        capacityListUi.setItems(FXCollections.observableArrayList(new ArrayList<>()));

    }
}
