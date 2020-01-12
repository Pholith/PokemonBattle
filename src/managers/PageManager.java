package managers;

import base.CONSTANTS;
import base.IController;
import javafx.scene.text.Font;
import utils.ISetStage;
import utils.PageNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.UnkownPageException;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class PageManager extends Application {


        private static PageManager instance;
        private Scene main;
        private Pane currentPanel;


    public PageManager() {
        super();


        if(instance == null)
            instance = this;



    }




    @Override
    public void stop(){
        System.exit(0);
    }



        @Override
        public void start(Stage primaryStage) throws Exception {
            currentPanel = FXMLLoader.load( getClass().getResource(CONSTANTS.pagesDir + "/mainPage/Page.fxml"));
            //currentPanel = FXMLLoader.load(getClass().getResource(Constants.pagesDir + "/mainPage/Page.fxml"));
            primaryStage.setTitle("Pokemon");
            main = new Scene(currentPanel);
            Font f = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Pokemon Classic.ttf"), 16);
            System.out.println("Loading font: " + f.getName());
            main.getStylesheets().add(getClass().getResource("/resources/fonts/pokemon.css").toString());
            primaryStage.setScene(main);
            primaryStage.show();
        }




       //creation de la fenettre sur un thread parrallele, histoire de pas etre dépendant de sa boucle
       static PageManager startWindow() throws InterruptedException {

           Thread t1 = new Thread(() -> launch());
           t1.start();


               while (instance == null)//attendre que la fenettre soit construite
                   Thread.sleep(100);

           return instance;
       }




        void switchPage(String name) {

            try {
        var res = getClass().getResource(CONSTANTS.pagesDir + "/"+name+"/Page.fxml");

            if(res == null)
                throw new UnkownPageException(name);

            System.out.println("Load page "+name);

                FXMLLoader loader = new FXMLLoader(res);
                currentPanel = loader.load();
                var controller = loader.getController();

                if(controller instanceof ISetStage)
                    ((ISetStage)controller).setStage((Stage) main.getWindow());
                if(controller instanceof IController)
                    ((IController)controller).onInitialized();

            } catch (Exception e) {
                e.printStackTrace();
            }
            main.setRoot(currentPanel);


        }


    public void AddPopupPanel(Pane pane) {
        currentPanel.getChildren().add(pane);
    }
}



