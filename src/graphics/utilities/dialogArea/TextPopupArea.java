package graphics.utilities.dialogArea;

import com.sun.prism.paint.Paint;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import managers.GameManager;
import org.w3c.dom.Text;
import utils.Constants;

import java.io.IOException;
import java.nio.file.Paths;


public class TextPopupArea {


    private final String[] toPrint;
    private Pane thisPanel;
    private int textId = 0;
    private Runnable actionOnFinish;
    private boolean isClosing;

    public TextPopupArea(Runnable onFinishAction, String... txt) {
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


            Rectangle rect = new Rectangle();

            thisPanel.getChildren().add(rect);

            FadeTransition fade = new FadeTransition(Duration.millis(300), thisPanel);
            fade.setFromValue(0d);
            fade.setToValue(1d);
            fade.play();

            ScaleTransition st = new ScaleTransition(Duration.millis(100), thisPanel);
            st.setFromX(0f);
            st.setToX(1f);
            st.setCycleCount(1);
            st.play();
            nextText();

            TranslateTransition tr = new TranslateTransition(Duration.millis(200), circle_nextText);

            tr.setFromY(-20);
            tr.setToY(0);
            tr.setCycleCount (Timeline.INDEFINITE);
            tr.setAutoReverse(true);
            tr.play();





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

        if(!isClosing)
            isClosing = true;
        else return;

        ScaleTransition st = new ScaleTransition(Duration.millis(200), thisPanel);
        st.setFromX(1f);
        st.setToX(0f);
        st.setInterpolator(Interpolator.LINEAR);



        st.setCycleCount(1);
        st.play();
        st.setOnFinished(actionEvent -> {
            thisPanel.setVisible(false);
            ((Pane)thisPanel.getParent()).getChildren().remove(thisPanel);
            if(actionOnFinish != null)
            actionOnFinish.run();
        });
    }



    @FXML
    private Label text_zone;


    @FXML
    private Circle circle_nextText;


    @FXML
    void onPopupClick(MouseEvent event) {
nextText();
    }


}
