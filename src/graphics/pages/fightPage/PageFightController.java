package graphics.pages.fightPage;

import Pokemons.Attack;
import Pokemons.Capacity;
import Pokemons.DamageClass;
import Pokemons.PokemonType;
import base.IController;
import javafx.animation.Interpolator;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import managers.GameManager;

import java.util.ArrayList;

public class PageFightController implements IController {




    public void updateCapacityList(ArrayList<Capacity> atckList) {
        list_atk.getItems().clear();
        for (var act : atckList)
            list_atk.getItems().add(act);

        Capacity defaultTest = new Capacity(0, "TestCapacity", null, 5,1,4, DamageClass.physical);
        list_atk.getItems().add(defaultTest);
    }


    private void initAttackList(){

        list_atk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(list_atk.getSelectionModel().getSelectedItem() == null) return;
                GameManager.getBattleEvent().playerTurnCapacity(list_atk.getSelectionModel().getSelectedItem());
            }
        });
    }


    public void setGameButtonsVisibility(boolean val){

        TranslateTransition tr = new TranslateTransition(Duration.millis(300),hbox_action);
        tr.setInterpolator(Interpolator.LINEAR);

        if(val){
            tr.setFromY(100);
            tr.setToY(0);
            hbox_action.setVisible(val);
        }else{
            tr.setFromY(0);
            tr.setToY(100);
            tr.setOnFinished( (o) -> hbox_action.setVisible(val)  );
        }
        tr.play();

        list_atk.setVisible(false);
        list_atk.getSelectionModel().setSelectionMode(null);
    }


    public void setPlayerImage(Image img, int idPlayer){
      if((idPlayer%2)==0)
          img_pok1.setImage(img);
      else
          img_pok2.setImage(img);
    }

    public void setPlayerHp(double hp,double maxHp, int idPlayer){
        if((idPlayer%2)==0) {
            bar1.setProgress(hp/maxHp);
            bar1_txt.setText(((int)hp)+"/"+(int)maxHp+"PV");
        }
        else {
            bar2.setProgress(hp/maxHp);
            bar2_txt.setText(((int)hp)+"/"+(int)maxHp+"PV");
        }
    }





@Override
    public void onInitialized() {
         initAttackList();
        setGameButtonsVisibility(false);
        GameManager.getBattleEvent().fightInitCallback(this);
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
    private ListView<Capacity> list_atk;



    @FXML
    void onClickAttack(MouseEvent event) {
        var actVisible = !list_atk.isVisible();

        TranslateTransition tr = new TranslateTransition(Duration.millis(200),list_atk);

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
