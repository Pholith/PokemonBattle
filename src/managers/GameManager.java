package managers;


import Pokemons.Capacities;
import Pokemons.Pokedex;
import Pokemons.PokemonCreature;
import Pokemons.Stats;
import base.Player;
import base.PlayerBot;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameManager  {


    private static final GameManager Instance = new GameManager();

    public static GameManager GetInstance(){return Instance;}
    private GameManager(){
        super();
        team = new ArrayList<>();
        capacities = new Capacities();
        pokedex = new Pokedex();
        stats = new Stats();
        soundManager = new SoundManager();
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

    public void StartGame(){
        System.out.println(pageManager == null);
        pageManager.switchPage("page2");
    }


    public void startFight(){

        if(team == null || team.size() == 0){
            new TextPopupArea("You can't start a battle without a team.");
            return;
        }

        battleEvent = new BattleEvent();
        var p1 = new Player(team, "Joueur 1");
        var p2 = new PlayerBot(pokedex.getRandomTeam(team.size()), "L'ordinateur");
        soundManager.getFightMusic().play();

        battleEvent.startFight(p1, p2);
    }
    public void finishFight() {
        battleEvent = null;
        soundManager.getVictorySound().stop();
        switchPage("mainPage");
    }


    public void switchPage(String pageName){
        assert(pageManager != null);
        soundManager.playBip();
        pageManager.switchPage(pageName);
    }

    /// ====== Model objects
    private ArrayList<PokemonCreature> team;

    public ArrayList<PokemonCreature> getTeam() {
        return team;
    }
    public void setTeam(List<PokemonCreature> team) {
        this.team = new ArrayList<>(team);
    }

    private final Pokedex pokedex;
    private final Capacities capacities;
    private final Stats stats;

    public Pokedex getPokedex() {
        return pokedex;
    }
    public Capacities getCapacities() {
        return capacities;
    }
    public Stats getStats() {
        return stats;
    }


}