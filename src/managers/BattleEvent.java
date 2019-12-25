package managers;


import Pokemons.Attack;
import Pokemons.Capacity;
import base.Player;
import graphics.pages.fightPage.PageFightController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.image.Image;

//Serialisable
public class BattleEvent {

    BattleEvent(){
        super();
    }



private Player[] players;
private int currentPlayerTurn = 1;
private PageFightController pageController;

void startFight(Player player1,Player player2 ) {

    players = new Player[]{ player1,player2};

    GameManager.GetInstance().switchPage("fightPage");
}


    public void fightInitCallback(PageFightController currentController) {
        this.pageController = currentController;

        var pok1 = players[0].getCurrentPokemon();
        var pok2 = players[1].getCurrentPokemon();

        try {
            Image image = new Image(getClass().getResource("/resources/"+pok1.getDescriptor().getImage()).toString());
            pageController.setPlayerImage(image, 0);
            image = new Image(getClass().getResource("/resources/"+pok2.getDescriptor().getImage()).toString());
            pageController.setPlayerImage(image, 1);


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        new TextPopupArea( () -> nextTurn(), "Début du combat !");
    }




    private void nextTurn() {
        currentPlayerTurn = (currentPlayerTurn+1)%2;
        new TextPopupArea( () -> timeToPlay(), "Au tour du joueur " +  (currentPlayerTurn+1));
    }

private void timeToPlay()
{
    var selectedPlayer = players[currentPlayerTurn];
    var selectedPok = selectedPlayer.getCurrentPokemon();
    var capacities = selectedPok.getCapacities();

    pageController.updateCapacityList(capacities);
    pageController.setGameButtonsVisibility(true);
}



public void playerTurnCapacity(Capacity atk) {
    pageController.setGameButtonsVisibility(false);

    var ennemyPok = players[(currentPlayerTurn+1)%2].getCurrentPokemon();
    ennemyPok.receiveAttack(atk);
pageController.setPlayerHp(ennemyPok.getHp(), ennemyPok.getStartHp(), (currentPlayerTurn+1)%2);

    new TextPopupArea( () -> nextTurn(), "Le joueur "+ (currentPlayerTurn+1) + " utilise " + atk.getName(), "C'est trés efficace.", "Si l'on veut perdre...");
}





}
