package graphics.pages.TeamBuilder;

import Pokemons.Pokedex;
import Pokemons.PokemonDescriptor;
import Pokemons.PokemonDescriptorBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import managers.GameManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamBuilderController implements Initializable {

    @FXML
    private TextField pokemonSearch;
    @FXML
    private TableView<PokemonDescriptorBean> pokedexList;
    @FXML private TableColumn<PokemonDescriptorBean, String> columnName;
    @FXML private TableColumn<PokemonDescriptorBean, String> columnTypes;

    @FXML
    private Label pokemonName;
    @FXML
    private ImageView imageView;
    @FXML
    private Button addTeamButton;
    @FXML
    private Label types;
    @FXML
    private Label hpLabel;
    @FXML
    private Label attackLabel;
    @FXML
    private Label defenseLabel;
    @FXML
    private Label speAttackLabel;
    @FXML
    private Label speDefenseLabel;
    @FXML
    private Label speedLabel;
    @FXML
    private ListView<?> teamList;

    @FXML
    void addPokemon(ActionEvent event) {

    }
    @FXML
    void filterPokedex(ActionEvent event) {

    }
    @FXML
    void selectPokemon(MouseEvent event) {
        PokemonDescriptorBean selectedBean = pokedexList.getSelectionModel().getSelectedItem();
        PokemonDescriptor descriptor = pokedex.getFromName(selectedBean.getName());
        pokemonName.setText(descriptor.getName());

        try {
            Image image = new Image(getClass().getResource("/resources/"+descriptor.getImage()).toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        types.setText(descriptor.typesToString());
        /*hpLabel;
        attackLabel;
        defenseLabel;
        speAttackLabel;
        speDefenseLabel;
        speedLabel;*/
    }
    private Pokedex pokedex;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pokedex = new Pokedex();
        ObservableList<PokemonDescriptorBean> observableList = FXCollections.observableArrayList();
        for (PokemonDescriptor poke: pokedex.getPokemons()) {
            observableList.add(poke.createBean());
        }
        pokedexList.setItems(observableList);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTypes.setCellValueFactory(new PropertyValueFactory<>("types"));
    }
}
