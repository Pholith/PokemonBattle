package managers;


import javafx.scene.layout.Pane;

public class GameManager  {


    private static final GameManager Instance = new GameManager();

    public static GameManager GetInstance(){return Instance;}
    private GameManager(){
        super();
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


        while(true){

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
        switchPage("fightPage");
    }





    public void switchPage(String pageName){
        assert(pageManager != null);
        pageManager.switchPage(pageName);
    }

}



