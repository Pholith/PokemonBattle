package managers;


import Pokemons.*;
import base.CONSTANTS;
import base.Player;
import base.PlayerBot;
import graphics.pages.fightPage.PageFightController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BattleEvent  implements Serializable {

    private Player[] players;
    private int currentPlayerTurn = -1;
    transient private PageFightController pageController;
    transient private DamageManager damageManager;


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
        players = new Player[] {player1, player2};

        if(players[1].getSelectedPokemon().getSpeed() > players[0].getSelectedPokemon().getSpeed())
            currentPlayerTurn = 0;
        else
            currentPlayerTurn = 1;


        GameManager.GetInstance().switchPage("fightPage");
    }


    void onSaveLoaded() {
        currentPlayerTurn -= 1;
        players[0].updateCloneTeam(GameManager.GetInstance().getTeamPlayer1());
       if( !(players[1] instanceof PlayerBot))
           players[1].updateCloneTeam(GameManager.GetInstance().getTeamPlayer2());

        GameManager.GetInstance().switchPage("fightPage");
    }


    public void fightInitCallback(PageFightController currentController) {
        this.pageController = currentController;
        UpdatePokemonUi();
        if (damageManager == null) damageManager = new DamageManager();
        GameManager.getSoundManager().getFightMusic().play();

        new TextPopupArea(this::nextTurn, "Début du combat !");
    }


    private void nextTurn() {
        currentPlayerTurn = (currentPlayerTurn + 1) % 2;
        new TextPopupArea(this::timeToPlay, "Au tour de " + players[currentPlayerTurn].getName());
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

        //System.out.println(currentPlayerTurn);
        var pok = players[0].getSelectedPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 0);
         pok = players[1].getSelectedPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 1);

        updatePokemonImage(0);
        updatePokemonImage(1);
    }

    /**
     * Make the player end his turn using a object on a choiced pokemon
     * @param object the choiced object
     * @param target the choiced pokemon
     */
    public void playerTurnObject(PokemonObject object, PokemonCreature target) {
        pageController.setGameButtonsVisibility(false);
        String message = object.applyEffect(target);
        UpdatePokemonUi();
        new TextPopupArea(this::endTurnAction, players[currentPlayerTurn].getName() + " utilise " + object.getName(),  message);
    }

    /**
     * Make the player end his turn using a object on the current pokemon
     * @param object the choiced object
     */
    public void playerTurnObject(PokemonObject object) {
        var allyPok = players[(currentPlayerTurn) % 2].getSelectedPokemon();
        playerTurnObject(object, allyPok);
    }

    /**
     * Make the player end his turn by use a capacity of the current pokemon
     * @param atk the choiced capacity
     */
    public void playerTurnCapacity(Capacity atk) {
        pageController.setGameButtonsVisibility(false);
        var allyPok = players[(currentPlayerTurn) % 2].getSelectedPokemon();
        var ennemyPok = players[(currentPlayerTurn + 1) % 2].getSelectedPokemon();
        var lostPv = ennemyPok.getHp();
        String efficiency = damageManager.applyCapacity(atk, allyPok, ennemyPok, new DamageCalculator());
        lostPv = lostPv - ennemyPok.getHp();

        UpdatePokemonUi();
        if (efficiency.equals(DamageManager.EFFECTIVITY.NONE)) {
            new TextPopupArea(this::endTurnAction, players[currentPlayerTurn].getName() + " utilise " + atk.getName());
            return;
        }
        new TextPopupArea(this::endTurnAction, players[currentPlayerTurn].getName() + " utilise " + atk.getName(),  efficiency + "L'ennemi à perdu " + lostPv + " Pvs.");
    }


    public void playerTurnSwitchPokemon(PokemonCreature newPokemon ) {
        playerTurnSwitchPokemon(newPokemon, players[currentPlayerTurn]);
    }

    private void playerTurnSwitchPokemon(PokemonCreature newPokemon, Player currentPlayer) {
        pageController.setGameButtonsVisibility(false);
        currentPlayer.setSelectedPokemonId(newPokemon);

        UpdatePokemonUi();

        if(newPokemon != null)
        new TextPopupArea(this::endTurnAction, currentPlayer.getName() + " range son pokemon et laisse place a " + newPokemon.getDescriptor().getName());
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

    /**
     * Exit the fight from the menu
     */
    public void exitFight() {
        GameManager.GetInstance().finishFight(players);
    }

    /**
     * End the fight with a winner and a looser
     */
    private void endFight() {
        Player winner = players[0];
        Player looser = players[1];
        if(players[0].getSelectedPokemon() == null) {
            winner = players[1];
            looser = players[0];
        }
        System.out.println("Fight ended! Looser is: " + looser + " And winner is: " + winner);

        looser.defeat();
        players[0].resetPokemons();
        players[1].resetPokemons();

        GameManager.getSoundManager().getFightMusic().stop();
        GameManager.getSoundManager().getVictoryMusic().play();

        new TextPopupArea(()-> GameManager.GetInstance().finishFight(players),  winner.getName() + " remporte le duel !");

    }
    public int getIndexBackground() {
        if (players[1] == null) throw new IllegalStateException();
        if (players[1] instanceof LigueDresser) return 3;
        if (!players[1].isAutomaticPlayer()) return 2;
        return 1;
    }
    public void SaveGame(){

        String fileName = "Fight_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss'.txt'").format(new Date());

        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(CONSTANTS.saveDir+fileName);
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
