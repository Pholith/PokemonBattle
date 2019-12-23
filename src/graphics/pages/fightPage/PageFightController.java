package graphics.pages.fightPage;

import Pokemons.Attack;
import base.IController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import managers.GameManager;

public class PageFightController implements IController {


    public void updteAttackList() {



       addNewAttack(new Attack("Attack 1 :","Ultra powerfull vomit."));
        addNewAttack(new Attack("Attack 2 :","Ultra powerfull laser."));
       addNewAttack(new Attack("Attack 3 :","billard insult."));
       addNewAttack(new Attack("Attack 4 :","ak47 deflagration."));
       addNewAttack(new Attack("Attack 5 :","general métal action."));
    }


    private void addNewAttack(Attack atk) {
        list_atk.getItems().add(atk);

    }

    private void initAttackList(){

        list_atk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameManager.getBattleEvent().attack(list_atk.getSelectionModel().getSelectedItem());
            }
        });
    }



    public void setGameButtonsVisibility(boolean val){
        hbox_action.setVisible(val);
        list_atk.setVisible(false);
        list_atk.getSelectionModel().setSelectionMode(null);
    }





@Override
    public void onInitialized() {


         initAttackList();
        setGameButtonsVisibility(false);
        updteAttackList();
        GameManager.getBattleEvent().startFight(this);
   }





    @FXML
    private ImageView img_pok1;

    @FXML
    private ImageView img_pok2;

    @FXML
    private ProgressBar bar1;

    @FXML
    private ProgressBar bar2;

    @FXML
    private Label bar1_txt;

    @FXML
    private Label bar2_txt;

    @FXML
    private HBox hbox_action;

    @FXML
    private ImageView but_attack;

    @FXML
    private ListView<Attack> list_atk;



    @FXML
    void onClickAttack(MouseEvent event) {
        var actVisible = !list_atk.isVisible();

        TranslateTransition tr = new TranslateTransition(Duration.millis(400),list_atk);

        tr.setCycleCount(1);

        if(actVisible){

            tr.setFromY(400f);
            tr.setToY(0f);
            list_atk.setVisible(actVisible);
        }else{
            tr.setFromY(0f);
            tr.setToY(400f);

            tr.setOnFinished((e) ->  list_atk.setVisible(actVisible));
        }

        tr.play();

    }


    @FXML
    void onAttackDrag(MouseEvent event) {
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-0.5f);
        but_attack.setEffect(blackout);
    }

    @FXML
    void onAttackEnd(MouseEvent event) {
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(0f);
        but_attack.setEffect(blackout);
    }



}
