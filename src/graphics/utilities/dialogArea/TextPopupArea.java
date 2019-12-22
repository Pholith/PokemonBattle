package graphics.utilities.dialogArea;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import managers.GameManager;
import utils.Constants;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Function;

public class TextPopupArea {


    private final String[] toPrint;
    private Pane thisPanel;
    private int textId = 0;
    private Function actionOnFinish;

    public TextPopupArea(Function onFinishAction, String... txt) {
        this(txt);
        actionOnFinish = onFinishAction;
    }

    public TextPopupArea(String... txt) {
        toPrint = txt;

        try {

            FXMLLoader loader = new FXMLLoader(Paths.get(Constants.utilsGraphicDir + "/dialogArea/dialogArea.fxml").toUri().toURL());
            loader.setController(this);
            thisPanel = loader.load();
            GameManager.GetInstance().AddSreenPane(thisPanel);
            thisPanel.setLayoutX(150.0);
            thisPanel.setLayoutY(300.0);

            ScaleTransition st = new ScaleTransition(Duration.millis(300), thisPanel);
            st.setFromX(0f);
            st.setToX(1f);
            st.setCycleCount(1);
            st.play();
            nextText();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private void nextText(){
        if(textId >= toPrint.length){
            closePanel();
            return;
        }
        text_zone.setText(toPrint[textId]);

        textId++;
    }


    private void closePanel(){
        ScaleTransition st = new ScaleTransition(Duration.millis(300), thisPanel);
        st.setFromX(1f);
        st.setToX(0f);
        st.setCycleCount(1);
        st.play();
        st.setOnFinished(actionEvent -> {
            ((Pane)thisPanel.getParent()).getChildren().remove(thisPanel);
            if(actionOnFinish != null)
            actionOnFinish.apply(null);
        });
    }




    @FXML
    private Label text_zone;

    @FXML
    private Button but_ok;

    @FXML
    void but_ok_action(ActionEvent event) {
        nextText();
    }


}
