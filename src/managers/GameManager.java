package managers;


import Pokemons.Capacities;
import Pokemons.Pokedex;
import Pokemons.PokemonCreature;
import base.Player;
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
    }


    private PageManager pageManager;
    private BattleEvent battleEvent = new BattleEvent();
    public static BattleEvent getBattleEvent(){return Instance.battleEvent;}

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

        var p1 = new Player(team);
        var p2 = new Player(pokedex.getRandomTeam(team.size()));

        battleEvent.startFight(p1, p2);
    }


    public void switchPage(String pageName){
        assert(pageManager != null);
        pageManager.switchPage(pageName);
    }

    private ArrayList<PokemonCreature> team;

    public ArrayList<PokemonCreature> getTeam() {
        return team;
    }
    public void setTeam(List<PokemonCreature> team) {
        this.team = new ArrayList<>(team);
    }

    private Pokedex pokedex;
    private Capacities capacities;

    public Pokedex getPokedex() {
        return pokedex;
    }
    public Capacities getCapacities() {
        return capacities;
    }
}