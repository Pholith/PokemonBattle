package graphics.pages.loadSave;


import base.CONSTANTS;
import base.IController;
import graphics.utilities.dialogArea.TextPopupArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import managers.BattleEvent;
import managers.GameManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadSaveController implements IController {





    @Override
    public void onInitialized() {
        File[] directories = new File(CONSTANTS.saveDir).listFiles(File::isFile);
        Path path;

        list_saves.getItems().clear();
        for (var d : directories) {

            list_saves.getItems().add(d.getName());
        }
    }


    @FXML
    private Button but_play;

    @FXML
    private Button but_remove;


        @FXML
        private Button but_back;

        @FXML
        private ListView<String> list_saves;

        @FXML
        void onClickBack(ActionEvent event) {
            GameManager.GetInstance().switchPage("page2");
        }

    @FXML
    void onClickPlay(ActionEvent event) {
        if (list_saves.getSelectionModel().getSelectedItem() != null) {
        var filename = "src/saves/"+list_saves.getSelectionModel().getSelectedItem();
            // Deserialization
            try {

                // Reading the object from a file
                FileInputStream file = new FileInputStream
                        (filename);
                ObjectInputStream in = new ObjectInputStream
                        (file);

                // Method for deserialization of object
                BattleEvent object = (BattleEvent) in.readObject();

                in.close();
                file.close();


            if(object == null){
               new TextPopupArea("Error when loading save "+filename);
               return;
            }

                System.out.println("Deserialisation OK");

                GameManager.GetInstance().startFightSave(object);

                // System.out.println("z = " + object1.z);
            } catch (IOException ex) {
                System.out.println("IOException is caught");
                ex.printStackTrace();
                new TextPopupArea("Error when loading save "+filename);
            } catch (Exception ex) {
                new TextPopupArea("Error when loading save "+filename);
            }

        }
    }

    @FXML
    void onClickRemove(ActionEvent event) throws IOException {
        if (list_saves.getSelectionModel().getSelectedItem() != null) {
            Files.delete(Paths.get("src/saves/" + list_saves.getSelectionModel().getSelectedItem()));
            onInitialized();
        }


    }

}
