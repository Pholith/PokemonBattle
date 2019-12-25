package graphics.pages.capacityBuilder;

import Pokemons.PokemonDescriptor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import managers.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CapacityBuilderController implements Initializable {

    @FXML
    private Label pokemonName;

    @FXML
    private ListView<?> capacityListUi;

    @FXML
    private ListView<?> allCapacityListUi;

    @FXML
    private ImageView imagePreview;

    @FXML
    void onClickExit(ActionEvent event) {

    }

    @FXML
    void onClickSave(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //GameManager.GetInstance().getCapacities().
    }
}
