package managers;


import Pokemons.Attack;
import base.Player;
import graphics.pages.fightPage.PageFightController;
import graphics.utilities.dialogArea.TextPopupArea;

//Serialisable
public class BattleEvent {

    BattleEvent(){
        super();
    }



private Player player1;
private Player player2;
private int currentPlayerTurn = 1;
private PageFightController currentController;

public void startFight(PageFightController currentController) {
    this.currentController = currentController;
    player1 = new Player();
    player2 = new Player();

    new TextPopupArea( () -> nextTurn(), "Début du combat !");
}


    private void nextTurn() {
        currentPlayerTurn = (currentPlayerTurn+1)%2;
        new TextPopupArea( () -> timeToPlay(), "Au tour du joueur " +  (currentPlayerTurn+1));
    }

private void timeToPlay(){
    currentController.setGameButtonsVisibility(true);
}


public void attack(Attack atk) {
    currentController.setGameButtonsVisibility(false);
    new TextPopupArea( () -> nextTurn(), "Le joueur "+ (currentPlayerTurn+1) + " utilise " + atk.getName(), "C'est trés efficace.", "Si l'on veut perdre...");
}





}
