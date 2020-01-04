package graphics.pages.ligue;

import Pokemons.LigueDresser;
import Pokemons.Ligue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import managers.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class PageLigueController implements Initializable {

    @FXML
    void onClickButtonMenu(ActionEvent event) {
        GameManager.GetInstance().switchPage("page2");
    }
    @FXML
    void onLigueClicked(MouseEvent event) {
        LigueDresser selected = listViewUi.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        if (selected.isDefeated()) return;
        if (!selected.equals(ligue.findFirstPlayable())) return;
        GameManager.GetInstance().startFight(null, selected);
    }

    @FXML
    private ListView<LigueDresser> listViewUi;

    private static final String STYLE_BG_COLOR = "-fx-background-color: ";
    private static final String STYLE_FT_SIZE = "-fx-font-size: ";
    private static final String COLOR_RED = "#800000;\n";
    private static final String COLOR_CYAN = "#00cc88;\n";
    private Ligue ligue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ligue = GameManager.GetInstance().getLigue();
        listViewUi.setItems(FXCollections.observableList(ligue.getLigueDresserList()));

        listViewUi.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LigueDresser> call(ListView<LigueDresser> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LigueDresser item, boolean empty) {
                        super.updateItem(item, empty);
                        setStyle(STYLE_FT_SIZE+"26;");

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            if (ligue.findFirstPlayable() != null && item.equals(ligue.findFirstPlayable())) {
                                setStyle(getStyle() + STYLE_BG_COLOR+COLOR_CYAN);
                            }
                            setText(item.toString());
                            if (item.isDefeated()) {
                                setStyle(getStyle() + STYLE_BG_COLOR +COLOR_RED);
                            }
                        }
                    }
                };
            }
        });
    }
}
