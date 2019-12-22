package managers;

import utils.Constants;
import utils.PageNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class PageManager extends Application {




        private static PageManager instance;
        private HashMap<String, Pane> screenMap = new HashMap<>();
        private Scene main;



    public PageManager() {
        super();


        if(instance == null)
            instance = this;


            InitPageList();

    }



    private void InitPageList(){


        File[] directories = new File(Constants.pagesDir).listFiles(File::isDirectory);
        Path path;


        for (var d : directories) {

            path = Paths.get(Constants.pagesDir + "/" + d.getName() + "/Page.fxml");

            try {
            if(!Files.exists(path)){
                throw new PageNotFoundException("Page " + path + " not found !");
            }
            screenMap.put(d.getName(), FXMLLoader.load(path.toUri().toURL()));
            } catch (Exception e) {
                System.out.println(d.getName());
                e.printStackTrace();
                continue;
            }
        }
    }


    @Override
    public void stop(){
        System.exit(0);
    }



        @Override
        public void start(Stage primaryStage) throws Exception {
            Pane root = FXMLLoader.load( Paths.get(Constants.pagesDir + "/mainPage/Page.fxml").toUri().toURL() );
            primaryStage.setTitle("Pokemon");
            main = new Scene(root);
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
            main.setRoot(screenMap.get(name));
        }


    }



