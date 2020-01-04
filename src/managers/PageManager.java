package managers;

import base.IController;
import com.sun.webkit.network.URLs;
import javafx.animation.ScaleTransition;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.util.Duration;
import utils.Constants;
import utils.ISetStage;
import utils.PageNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.UnkownPageException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.HashMap;


public class PageManager extends Application {


        private static PageManager instance;
        private HashMap<String, URI> screenMap = new HashMap<>();
        private Scene main;
        private Pane currentPanel;


    public PageManager() {
        super();


        if(instance == null)
            instance = this;


            InitPageList();

    }



    private void InitPageList() {


        File[] directories = new File(Constants.pagesDir).listFiles(File::isDirectory);
        Path path;


        for (var d : directories) {

            path = Paths.get(Constants.pagesDir + "/" + d.getName() + "/Page.fxml");


            if (!Files.exists(path))
                throw new PageNotFoundException("Page " + path + " not found !");


            screenMap.put(d.getName(), path.toUri());


            continue;
        }
    }



    @Override
    public void stop(){
        System.exit(0);
    }



        @Override
        public void start(Stage primaryStage) throws Exception {
            currentPanel = FXMLLoader.load( Paths.get(Constants.pagesDir + "/mainPage/Page.fxml").toUri().toURL());
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
            if(!screenMap.containsKey(name))
                throw new UnkownPageException(name);

            System.out.println("Load page "+name);

            try {
                FXMLLoader loader = new FXMLLoader(screenMap.get(name).toURL());
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



