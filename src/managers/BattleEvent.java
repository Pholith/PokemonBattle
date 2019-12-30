package managers;


import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import base.Player;
import graphics.pages.fightPage.PageFightController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//Serialisable
public class BattleEvent  implements Serializable {




    private Player[] players;
    private int currentPlayerTurn = -1;
    transient private PageFightController pageController;





    private void updatePokemonImage(int playerId) {
        try {

            Image image = null;
            var pok = players[playerId].getSelectedPokemon();

            if(pok != null)
                    image = new Image(getClass().getResource("/resources/" + players[playerId].getSelectedPokemon().getDescriptor().getImage()).toString());
            pageController.setPlayerImage(image, playerId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

//package
    void startFight(Player player1, Player player2) {
        players = new Player[]{player1, player2};

        if(players[1].getSelectedPokemon().getSpeed() > players[0].getSelectedPokemon().getSpeed())
            currentPlayerTurn = 0;
        else
            currentPlayerTurn = 1;



        GameManager.GetInstance().switchPage("fightPage");
    }


    void onSaveLoaded() {
        currentPlayerTurn -= 1;
        GameManager.GetInstance().switchPage("fightPage");
    }



    public void fightInitCallback(PageFightController currentController) {
        this.pageController = currentController;
        UpdatePokemonUi();

        GameManager.getSoundManager().getFightMusic().play();

        new TextPopupArea(() -> nextTurn(), "Début du combat !");
    }


    private void nextTurn() {
        currentPlayerTurn = (currentPlayerTurn + 1) % 2;
        new TextPopupArea(() -> timeToPlay(), "Au tour de " + players[currentPlayerTurn].getName());
    }


    private void timeToPlay() {
        var selectedPlayer = players[currentPlayerTurn];

        if (!selectedPlayer.isAutomaticPlayer()) {
            pageController.updateLists(selectedPlayer);
            pageController.setGameButtonsVisibility(true);
        }
        selectedPlayer.onPlayTurn();
    }

    private void UpdatePokemonUi(){

        System.out.println(currentPlayerTurn);
        var pok = players[0].getSelectedPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 0);
         pok = players[1].getSelectedPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 1);

        updatePokemonImage(0);
        updatePokemonImage(1);
    }

    public void playerTurnCapacity(Capacity atk) {
        pageController.setGameButtonsVisibility(false);
        var ennemyPok = players[(currentPlayerTurn + 1) % 2].getSelectedPokemon();
        var lostPv = ennemyPok.getHp();
        ennemyPok.receiveAttack(atk);
        lostPv = lostPv - ennemyPok.getHp();

        var ratio = (float) lostPv / (float) ennemyPok.getStartHp();
        UpdatePokemonUi();
        new TextPopupArea(() -> endTurnAction(), players[currentPlayerTurn].getName() + " utilise " + atk.getName(), ratioToText(ratio) + "\nL'ennemi à perdu " + lostPv + " Pvs.");
    }

    private String ratioToText(float ratio) {
        if (ratio < 0.1f)
            return "Ce n'est pas trés efficace.";
        if (ratio < 0.3f)
            return "Legers dégats.";
        if (ratio < 0.6f)
            return "Attaque efficace !";
        return "Attaque destructrice !";
    }

    public void playerTurnSwitchPokemon(PokemonCreature newPokemon ) {
        playerTurnSwitchPokemon(newPokemon, players[currentPlayerTurn]);
    }

    public void playerTurnSwitchPokemon(PokemonCreature newPokemon, Player currentPlayer) {
        pageController.setGameButtonsVisibility(false);
        currentPlayer.setSelectedPokemonId(newPokemon);

        UpdatePokemonUi();

        if(newPokemon != null)
        new TextPopupArea(() -> endTurnAction(), currentPlayer.getName() + " range son pokemon et laisse place a " + newPokemon.getDescriptor().getName());
        else{
            endTurnAction();
        }
    }


    private void endTurnAction() {

        var nextPlayer = players[(currentPlayerTurn+1)%2];
var pok = nextPlayer.getSelectedPokemon();

        if(pok == null){
            endFight();
            return;
        }



        if (pok.IsDead()) {
            var replacement = nextPlayer.getFirstPokemonAlive();
            new TextPopupArea(() -> playerTurnSwitchPokemon(replacement, nextPlayer),
                    players[currentPlayerTurn].getSelectedPokemon().getDescriptor().getName() + " est mort ! ");
        } else {
            nextTurn();
        }
    }


    private void endFight() {
        Player winner = players[0];
        GameManager.getSoundManager().getFightMusic().stop();
        GameManager.getSoundManager().getVictoryMusic().play();

        if(players[0].getSelectedPokemon() == null)
            winner = players[1];

        new TextPopupArea(()->{
            GameManager.GetInstance().finishFight();},  winner.getName() + " remporte le duel !");

    }



    public void SaveGame(){

        String fileName = "Fight_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss'.txt'").format(new Date());


        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("src/saves/"+fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);
            out.flush();
            out.close();
            file.close();



            System.out.println("Object has been serialized");
            new TextPopupArea("Game save as " + fileName);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new TextPopupArea("Error during the save of " + fileName);

        }


    }


}
