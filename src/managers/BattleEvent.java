package managers;


import Pokemons.Capacity;
import Pokemons.PokemonCreature;
import base.Player;
import graphics.pages.fightPage.PageFightController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.image.Image;

//Serialisable
public class BattleEvent {

    BattleEvent() {
        super();
    }


    private Player[] players;
    private int currentPlayerTurn = 1;
    private PageFightController pageController;


    private void updatePokemonImage(int playerId) {
        try {


            Image image = null;
            var pok = players[playerId].getCurrentPokemon();

            if(pok != null)
                    image = new Image(getClass().getResource("/resources/" + players[playerId].getCurrentPokemon().getDescriptor().getImage()).toString());
            pageController.setPlayerImage(image, playerId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

//packgae
    void startFight(Player player1, Player player2) {
        players = new Player[]{player1, player2};
        GameManager.GetInstance().switchPage("fightPage");
    }


    public void fightInitCallback(PageFightController currentController) {
        this.pageController = currentController;
        UpdatePokemonUi();
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
        var pok = players[0].getCurrentPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 0);
         pok = players[1].getCurrentPokemon();
        if(pok != null)
        pageController.setPlayerHp(pok.getHp(), pok.getStartHp(), 1);

        updatePokemonImage(0);
        updatePokemonImage(1);
    }

    public void playerTurnCapacity(Capacity atk) {
        pageController.setGameButtonsVisibility(false);
        var ennemyPok = players[(currentPlayerTurn + 1) % 2].getCurrentPokemon();
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
var pok = nextPlayer.getCurrentPokemon();

        if(pok == null){
            endFight();
            return;
        }



        if (pok.IsDead()) {
            var replacement = nextPlayer.getFirstPokemonAlive();
            new TextPopupArea(() -> playerTurnSwitchPokemon(replacement, nextPlayer),
                    players[currentPlayerTurn].getCurrentPokemon().getDescriptor().getName() + " est mort ! ");
        } else {
            nextTurn();
        }
    }


    private void endFight() {
        Player winner = players[0];
        GameManager.getSoundManager().getFightMusic().stop();
        GameManager.getSoundManager().getVictorySound().play();

        if(players[0].getCurrentPokemon() == null)
            winner = players[1];

        new TextPopupArea(()->{
            GameManager.GetInstance().finishFight();},  winner.getName() + " remporte le duel !");

    }
}
