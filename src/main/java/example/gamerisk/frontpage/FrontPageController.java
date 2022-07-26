package example.gamerisk.frontpage;

import example.gamerisk.MainApplication;
import example.gamerisk.utils.LoadUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

@Setter
public class FrontPageController {

    private Scene gameScene;
    private MainApplication mainApplication;

    @FXML
    private Button newGame;

    @FXML
    private Button loadProgress;

    @FXML
    private Button Exit;

    /**
     * This method is used to load the game map
     *
     * @param  : the event to do ActionEvent.
     * @throws : IOException
     */
    @FXML
    void loadMap(ActionEvent event) throws IOException{

        DialogPane setup = new DialogPane();
        setup.setHeaderText("Please select how many players from 3 to 6. ");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("./gameSetup/gameSetup.fxml"));
        AnchorPane content = loader.load();
        setup.setContent(content);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setDialogPane(setup);
        alert.showAndWait();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GameMap File To Load");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("map file", "*.map"));
        File selected = fileChooser.showOpenDialog(null);

        if (!selected.exists()) {
            // TODO
        }
        try {
//            LoadUtil.readFile(selected);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (GameStatus.getInstance ().getPlayers()!=null&&!GameStatus.getInstance ().getPlayers ().isEmpty ()) {\
//            openGameScene (event);
//        }
    }

    @FXML
    void loadProgress(ActionEvent event) {

    }

    @FXML
    void Exit(ActionEvent event) {

    }

}

