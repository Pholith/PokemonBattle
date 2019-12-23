package graphics.pages.mainPage;

import base.IController;
import managers.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainPageController implements IController {

    @FXML
    private Button but_start;

    @FXML
    void but_start_action(ActionEvent event) {
        GameManager.GetInstance().StartGame();
    }

    public void but_inventory_action(ActionEvent actionEvent) {
    }
}
