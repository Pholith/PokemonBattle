package graphics.pages.page2;

import base.IController;
import graphics.utilities.dialogArea.TextPopupArea;
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
    void but_fight_action(ActionEvent event) {
        GameManager.GetInstance().startFight();
    }

    @FXML
    void but_inventory_action(ActionEvent event) {
            GameManager.GetInstance().switchPage("loadSave");
    }

    @FXML
    void but_pokedex_action(ActionEvent event) {
        GameManager.GetInstance().switchPage("TeamBuilder");
    }


}
