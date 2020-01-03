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

public class BattleEvent  implements Serializable {


    private Player[] players;
    private int currentPlayerTurn = -1;
    transient private PageFightController pageController;
    private DamageManager damageManager;


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
        if (damageManager == null) damageManager = new DamageManager();

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

    public void playerTurnCapacity(Capacity atk) {
        pageController.setGameButtonsVisibility(false);
        var allyPok = players[(currentPlayerTurn) % 2].getSelectedPokemon();
        var ennemyPok = players[(currentPlayerTurn + 1) % 2].getSelectedPokemon();
        var lostPv = ennemyPok.getHp();
        String efficiency = damageManager.applyCapacity(atk, allyPok, ennemyPok);
        lostPv = lostPv - ennemyPok.getHp();

        var ratio = (float) lostPv / (float) ennemyPok.getStartHp();
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


    private void endFight() {
        Player winner = players[0];
        players[1].defeat();
        players[0].resetPokemons();
        players[1].resetPokemons();

        GameManager.getSoundManager().getFightMusic().stop();
        GameManager.getSoundManager().getVictoryMusic().play();

        if(players[0].getSelectedPokemon() == null)
            winner = players[1];
        new TextPopupArea(()-> GameManager.GetInstance().finishFight(),  winner.getName() + " remporte le duel !");

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
