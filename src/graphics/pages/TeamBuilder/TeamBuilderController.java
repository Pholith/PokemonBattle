package graphics.pages.TeamBuilder;

import Pokemons.PokemonCreature;
import Pokemons.PokemonDescriptor;
import Pokemons.PokemonDescriptorBean;

import base.IController;
import graphics.pages.capacityBuilder.CapacityBuilderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.GameManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamBuilderController implements IController {

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
    private Button but_selection;

    @FXML
    private void onClickAddPokemon(ActionEvent event) {
        if (pokedexList.getSelectionModel().getSelectedItem() == null) return;
        GameManager.getSoundManager().playBip();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graphics/pages/capacityBuilder/Page.fxml"));
            PokemonDescriptorBean selectedBean = pokedexList.getSelectionModel().getSelectedItem();
            PokemonDescriptor descriptor = GameManager.GetInstance().getPokedex().getFromName(selectedBean.getName());

            loader.setController(new CapacityBuilderController(this, descriptor));
            root = loader.load();
            Stage stage = new Stage();
            root.getStylesheets().add(getClass().getResource("/resources/fonts/pokemon.css").toString());

            stage.setTitle("Select Capacities");
            stage.setScene(new Scene(root, 900, 500));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickMenu(ActionEvent event) {
        GameManager.GetInstance().switchPage("page2");
    }

    // This is call by the child controller (CapacityController)
    public void addClickAddPokemon(PokemonCreature pokemon) {
        if (currentSelecetdTeam.size() < 6) currentSelecetdTeam.add(pokemon);
        updateManagerTeam();
    }

    @FXML
    private void removePokemon(MouseEvent event) {
        PokemonCreature pokemon = teamList.getSelectionModel().getSelectedItem();
        currentSelecetdTeam.remove(pokemon);
        GameManager.getSoundManager().playBip();
        updateManagerTeam();
    }

    @FXML
    private void filterPokedex(ActionEvent event) {
        filteredList.setPredicate(pokemonDescriptorBean -> pokemonDescriptorBean.getName().startsWith(pokemonSearch.getText()));
    }

    private void deselectPokemon(){
        imageView.setImage(null);
        pokemonName.setText("Selected a pokemon in the list.");
        types.setText("-");
        hpLabel.setText("-");
        attackLabel.setText("-");
        defenseLabel.setText("-");
        speAttackLabel.setText("-");
        speDefenseLabel.setText("-");
        speedLabel.setText("-");
    }

    @FXML
    private void selectPokemon(MouseEvent event) {
        PokemonDescriptorBean selectedBean = pokedexList.getSelectionModel().getSelectedItem();
        if (selectedBean == null) return;
        GameManager.getSoundManager().playBip();
        PokemonDescriptor descriptor = GameManager.GetInstance().getPokedex().getFromName(selectedBean.getName());
        pokemonName.setText(descriptor.getName());

        try {
            Image image = new Image(getClass().getResource("/resources/"+descriptor.getImage()).toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        types.setText(descriptor.typesToString());
        PokemonCreature pokemon = new PokemonCreature(descriptor, GameManager.GetInstance().getStats().getBaseStat(descriptor.getID()), null);
        hpLabel.setText(        String.valueOf(pokemon.getHp()));
        attackLabel.setText(    String.valueOf(pokemon.getAttack()));
        defenseLabel.setText(   String.valueOf(pokemon.getDefense()));
        speAttackLabel.setText( String.valueOf(pokemon.getSpecialAttack()));
        speDefenseLabel.setText(String.valueOf(pokemon.getSpecialDefense()));
        speedLabel.setText(     String.valueOf(pokemon.getSpeed()));
    }

   // private ObservableList<PokemonCreature> team1;
  //  private ObservableList<PokemonCreature> team2;

    private ObservableList<PokemonCreature> currentSelecetdTeam;

private int selectedPlayer = 0;

    private FilteredList<PokemonDescriptorBean> filteredList; // list of pokemons in the pokedex



    @Override
    public void onInitialized() {

        deselectPokemon();

        ObservableList<PokemonDescriptorBean> observableList = FXCollections.observableArrayList();
        for (PokemonDescriptor poke: GameManager.GetInstance().getPokedex().getPokemons()) {
            observableList.add(poke.createBean());
        }
        filteredList = new FilteredList<>(observableList);
        pokedexList.setItems(filteredList);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTypes.setCellValueFactory(new PropertyValueFactory<>("types"));

        ArrayList<PokemonCreature> arrayList = new ArrayList<>();


        if(selectedPlayer == 0) {
            if (GameManager.GetInstance().getTeamPlayer1() != null && GameManager.GetInstance().getTeamPlayer1().size() > 0) {
                arrayList = GameManager.GetInstance().getTeamPlayer1();
            }
            currentSelecetdTeam = FXCollections.observableArrayList(arrayList);
        }else {
            if (GameManager.GetInstance().getTeamPlayer2() != null && GameManager.GetInstance().getTeamPlayer2().size() > 0) {
                arrayList = GameManager.GetInstance().getTeamPlayer2();
            }
        }
        currentSelecetdTeam = FXCollections.observableArrayList(arrayList);
        teamList.setItems(currentSelecetdTeam);

    }

    @FXML
    void onChangePlayer(ActionEvent event) {
    GameManager.getSoundManager().playBip();
        selectedPlayer=(selectedPlayer+1)%2;
        but_selection.setText("Player "+(selectedPlayer+1));
        onInitialized();
    }


    private void updateManagerTeam() {

        if(selectedPlayer == 0) {
            var t1 = GameManager.GetInstance().getTeamPlayer1();
            t1.clear();
            t1.addAll(currentSelecetdTeam);
            return;
        }

            var t2 = GameManager.GetInstance().getTeamPlayer2();
            t2.clear();
            t2.addAll(currentSelecetdTeam);

    }


}
