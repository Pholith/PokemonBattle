package managers;


import Pokemons.*;
import base.CONSTANTS;
import base.Player;
import base.PlayerBot;
import graphics.pages.TeamBuilder.TeamBuilderController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameManager  {


    private static final GameManager Instance = new GameManager();

    public static GameManager GetInstance(){return Instance;}
    private GameManager(){
        super();
        teamPlayer1 = new ArrayList<>();
        teamPlayer2 = new ArrayList<>();
        capacities = new Capacities();
        stats = new Stats();
        soundManager = new SoundManager();
        pokedex = new Pokedex(stats, capacities);
        ligue = new Ligue();
    }

    private SoundManager soundManager;
    private PageManager pageManager;
    private BattleEvent battleEvent = null;
    public static BattleEvent getBattleEvent(){return Instance.battleEvent;}
    public static SoundManager getSoundManager(){return Instance.soundManager;}

    //public static PageManager GetPageManager(){return pageManager;}


    public static void main(String[] args) throws InterruptedException {
        Instance.Init();
    }

    private void Init() throws InterruptedException {
        pageManager = pageManager.startWindow();

        while (true) {

        }
    }

    public void AddSreenPane(Pane pane){
        pageManager.AddPopupPanel(pane);
    }

    public void StartGame() {
        System.out.println(pageManager == null);

        try {
            if (!Files.exists(Paths.get(CONSTANTS.saveDir)))
                Files.createDirectories(Paths.get(CONSTANTS.saveDir));
        }catch (Exception e){
            e.printStackTrace();
        }

        soundManager.playBip();
        pageManager.switchPage("page2");
    }

    public void startFightSave(BattleEvent battle) {
        battleEvent = battle;
        battleEvent.onSaveLoaded();
    }

    public void startFight(Player p1, Player p2) {

        soundManager.playBip();

        if (teamPlayer1 == null || teamPlayer1.size() == 0){
            new TextPopupArea("You can't start a battle without a team.");
            return;
        } else if (p2 != null && !p2.isAutomaticPlayer() && (teamPlayer2 == null || teamPlayer2.size() == 0)) {
            new TextPopupArea("Player2 can't start a battle without a team.");
            return;
        }


        battleEvent = new BattleEvent();
        if (p1 == null) p1 = new Player(teamPlayer1, "Joueur 1");
        if (p2 == null) p2 = new PlayerBot(pokedex.getRandomTeam(teamPlayer1.size()), "L'ordinateur");


        battleEvent.startFight(p1, p2);
    }
    public void startFightIA(){
        startFight(null,null);
    }

    public void startFightPlayer() {
        startFight(null, new Player(teamPlayer2, "Joueur 2"));
    }


    public void finishFight(Player[] players) {
        for (Player player: players) {
            player.resetPokemons();
        }
        battleEvent = null;
        soundManager.getVictoryMusic().stop();
        soundManager.getFightMusic().stop();
        switchPage("mainPage");

        if (getLigue().isDefeated()) new TextPopupArea("Félicitation ! Vous avez vaincu la ligue pokemon !");
    }


    public void switchPage(String pageName){
        assert(pageManager != null);
        soundManager.playBip();
        pageManager.switchPage(pageName);
    }

    /// ====== Model objects
    private ArrayList<PokemonCreature> teamPlayer1;
    public ArrayList<PokemonCreature> getTeamPlayer1() {
        return teamPlayer1;
    }

    private ArrayList<PokemonCreature> teamPlayer2;
    public ArrayList<PokemonCreature> getTeamPlayer2() {
        return teamPlayer2;
    }


    private final Pokedex pokedex;
    private final Capacities capacities;
    private final Stats stats;
    private final Ligue ligue;

    public Pokedex getPokedex() {
        return pokedex;
    }
    public Capacities getCapacities() {
        return capacities;
    }
    public Stats getStats() {
        return stats;
    }
    public Ligue getLigue() {
        ligue.initialize();
        return ligue;
    }


}