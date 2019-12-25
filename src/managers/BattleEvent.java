package managers;


import Pokemons.Capacity;
import Pokemons.PokemonCreature;
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




    private void updatePokemonImage(int playerId){
        try {
            Image image = new Image(getClass().getResource("/resources/"+players[playerId].getCurrentPokemon().getDescriptor().getImage()).toString());
            pageController.setPlayerImage(image, playerId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }





void startFight(Player player1,Player player2 ) {
    players = new Player[]{ player1,player2};
    GameManager.GetInstance().switchPage("fightPage");
}


    public void fightInitCallback(PageFightController currentController) {
        this.pageController = currentController;
        updatePokemonImage(0);
        updatePokemonImage(1);
        new TextPopupArea( () -> nextTurn(), "Début du combat !");
    }


    private void nextTurn() {
        currentPlayerTurn = (currentPlayerTurn+1)%2;
        new TextPopupArea( () -> timeToPlay(), "Au tour du joueur " +  (currentPlayerTurn+1));
    }


private void timeToPlay()
{
    var selectedPlayer = players[currentPlayerTurn];
    pageController.updateLists(selectedPlayer);
    pageController.setGameButtonsVisibility(true);
}




public void playerTurnCapacity(Capacity atk) {
    pageController.setGameButtonsVisibility(false);
    var ennemyPok = players[(currentPlayerTurn+1)%2].getCurrentPokemon();
    ennemyPok.receiveAttack(atk);
    pageController.setPlayerHp(ennemyPok.getHp(), ennemyPok.getStartHp(), (currentPlayerTurn+1)%2);
    new TextPopupArea( () -> nextTurn(), "Le joueur "+ (currentPlayerTurn+1) + " utilise " + atk.getName(), "C'est trés efficace.", "Si l'on veut perdre...");
}



    public void playerTurnSwitchPokemon(PokemonCreature newPokemon) {
        pageController.setGameButtonsVisibility(false);
        players[currentPlayerTurn].setSelectedPokemonId(newPokemon);
        updatePokemonImage(currentPlayerTurn);
        pageController.setPlayerHp(newPokemon.getHp(), newPokemon.getStartHp(), currentPlayerTurn);

        new TextPopupArea( () -> nextTurn(), "Le joueur "+ (currentPlayerTurn+1) + " range son pokemon et laisse place a "+newPokemon.getDescriptor().getName());
    }




}
