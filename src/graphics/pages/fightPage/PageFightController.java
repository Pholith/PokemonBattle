package graphics.pages.fightPage;

import Pokemons.*;
import base.IController;
import base.Player;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import managers.GameManager;
import utils.ISetStage;


public class PageFightController implements IController, ISetStage {



    public void updateLists(Player player) {
player.fillUiList(list_capacity, list_swichPokemon);
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

        list_capacity.setVisible(false);
        list_capacity.getSelectionModel().setSelectionMode(null);
        list_swichPokemon.setVisible(false);
        list_swichPokemon.getSelectionModel().setSelectionMode(null);
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

    // I need the stage for the image weight...
    private Stage myStage;
    public void setStage(Stage stage) {
        myStage = stage;
        System.out.println(stage);
    }

    @Override
    public void onInitialized() {
        initListViews();
        setGameButtonsVisibility(false);
        GameManager.getBattleEvent().fightInitCallback(this);

        assert myStage != null;
        try {
            imageBackground.setImage(new Image(getClass().getResource("/resources/backgrounds_img/fightBg1.png").toString()));
            imageBackground.fitWidthProperty().bind(myStage.widthProperty());
            imageBackground.setPreserveRatio(true);
        } catch (RuntimeException e) {
            System.err.println(e.toString());
        }
    }



    private void initListViews(){
        list_capacity.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(list_capacity.getSelectionModel().getSelectedItem() == null) return;
                GameManager.getBattleEvent().playerTurnCapacity(list_capacity.getSelectionModel().getSelectedItem());
            }
        });
        list_swichPokemon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                var pok = list_swichPokemon.getSelectionModel().getSelectedItem();
                if(pok == null || pok.IsDead() ) return;
                GameManager.getBattleEvent().playerTurnSwitchPokemon(list_swichPokemon.getSelectionModel().getSelectedItem());
            }
        });
    }


    @FXML
    private ImageView img_pok1;
    @FXML
    private ImageView imageBackground;
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
    private ImageView but_capacity;

    @FXML
    private ImageView but_switchPokemon;

    @FXML
    private ListView<Capacity> list_capacity;

    @FXML
    private ListView<PokemonCreature> list_swichPokemon;




    private void onPressImageButton(ListView<?> linkedList){
        var actVisible = !linkedList.isVisible();

        TranslateTransition tr = new TranslateTransition(Duration.millis(200),linkedList);
        GameManager.getSoundManager().playBip();
        tr.setCycleCount(1);

        if(actVisible){

            tr.setFromY(400f);
            tr.setToY(0f);
            linkedList.setVisible(actVisible);

            if(linkedList != list_capacity && list_capacity.isVisible()) onPressImageButton(list_capacity);
            if(linkedList != list_swichPokemon && list_swichPokemon.isVisible()) onPressImageButton(list_swichPokemon);

        }else{
            tr.setFromY(0f);
            tr.setToY(400f);

            tr.setOnFinished((e) ->  linkedList.setVisible(actVisible));
        }
        tr.play();
    }

    private void onDragImageButton(ImageView view){
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-0.5f);
        view.setEffect(blackout);
    }

    private void onReleaseImageButton(ImageView view){
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(0f);
        view.setEffect(blackout);
    }



    @FXML
    void onClickAttack(MouseEvent event) {
        onPressImageButton(list_capacity);
    }

    @FXML
    void onAttackDrag(MouseEvent event) {
    onDragImageButton(but_capacity);
    }

    @FXML
    void onAttackRelease(MouseEvent event) {
        onReleaseImageButton(but_capacity);
    }



    @FXML
    void onClickChangePokemon(MouseEvent event) {
        onPressImageButton(list_swichPokemon);
    }

    @FXML
    void onChangePokemonRelease(MouseEvent event) {
        onReleaseImageButton(but_switchPokemon);
    }

    @FXML
    void onChangePokemonDrag(MouseEvent event) {
        onDragImageButton (but_switchPokemon);
    }

}
