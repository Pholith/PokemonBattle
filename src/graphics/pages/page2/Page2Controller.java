package graphics.pages.page2;

import base.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import managers.GameManager;

public class Page2Controller implements IController {


    @FXML
    private Button but_inventory;

    @FXML
    private Button but_pokedex;

    @FXML
    private Button but_fight;

    @FXML
    private Button but_fight_player;


    @FXML
    void but_fight_action(ActionEvent event) {
        GameManager.GetInstance().startFightIA();
    }

    @FXML
    void but_fight_action_player(ActionEvent event) {
        GameManager.GetInstance().startFightPlayer();
    }


    @FXML
    void but_inventory_action(ActionEvent event) {
        GameManager.GetInstance().switchPage("loadSave");
    }

    @FXML
    void but_pokedex_action(ActionEvent event) {
        GameManager.GetInstance().switchPage("TeamBuilder");
    }

    @FXML
    void but_ligues_action(ActionEvent event) {
        GameManager.GetInstance().switchPage("ligue");
    }




}