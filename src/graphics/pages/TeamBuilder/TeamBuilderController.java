package graphics.pages.TeamBuilder;

import Pokemons.Pokedex;
import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;
import Pokemons.PokemonDescriptorBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import managers.GameManager;

import java.net.URL;
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
    private ListView<PokemonCreature> teamList;

    @FXML
    void onSelectCapacities(ActionEvent event) {

    }

    @FXML
    void onClickMenu(ActionEvent event) {
        GameManager.GetInstance().switchPage("page2");
    }

    @FXML
    void addPokemon(ActionEvent event) {
        PokemonDescriptorBean selectedBean = pokedexList.getSelectionModel().getSelectedItem();
        PokemonDescriptor descriptor = pokedex.getFromName(selectedBean.getName());
        PokemonCreature pokemon = new PokemonCreature(descriptor, null);
        if (team.size() < 6) team.add(pokemon);
        updateManagerTeam();
    }
    @FXML
    void removePokemon(MouseEvent event) {
        PokemonCreature pokemon = teamList.getSelectionModel().getSelectedItem();
        team.remove(pokemon);
        updateManagerTeam();
    }

    @FXML
    void filterPokedex(ActionEvent event) {
        filteredList.setPredicate(pokemonDescriptorBean -> pokemonDescriptorBean.getName().startsWith(pokemonSearch.getText()));
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
        PokemonCreature pokemon = new PokemonCreature(descriptor, null);
        hpLabel.setText(        String.valueOf(pokemon.getHp()));
        attackLabel.setText(    String.valueOf(pokemon.getAttack()));
        defenseLabel.setText(   String.valueOf(pokemon.getDefense()));
        speAttackLabel.setText( String.valueOf(pokemon.getSpecialAttack()));
        speDefenseLabel.setText(String.valueOf(pokemon.getSpecialDefense()));
        speedLabel.setText(     String.valueOf(pokemon.getSpeed()));
    }
    private Pokedex pokedex;

    private ObservableList<PokemonCreature> team;
    private FilteredList<PokemonDescriptorBean> filteredList; // list of pokemons in the pokedex

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pokedex = new Pokedex();
        ObservableList<PokemonDescriptorBean> observableList = FXCollections.observableArrayList();
        for (PokemonDescriptor poke: pokedex.getPokemons()) {
            observableList.add(poke.createBean());
        }
        filteredList = new FilteredList<>(observableList);
        pokedexList.setItems(filteredList);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTypes.setCellValueFactory(new PropertyValueFactory<>("types"));

        team = FXCollections.observableArrayList();
        teamList.setItems(team);
    }




    private void updateManagerTeam() {
        GameManager.GetInstance().setTeam(team);
    }
}
