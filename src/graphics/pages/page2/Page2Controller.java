package graphics.pages.page2;

import graphics.utilities.dialogArea.TextPopupArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import managers.GameManager;

public class Page2Controller {

    @FXML
    private Button but_inventory;

    @FXML
    private Button but_pokedex;

    @FXML
    private Button but_fight;

    @FXML
    void but_fight_action(ActionEvent event) {
        GameManager.GetInstance().StartFight();
    }

    @FXML
    void but_inventory_action(ActionEvent event) {
        new TextPopupArea("ahahah", "un super texte !");
    }

    @FXML
    void but_pokedex_action(ActionEvent event) {
        GameManager.GetInstance().SwitchPage("TeamBuilder");
    }

}
