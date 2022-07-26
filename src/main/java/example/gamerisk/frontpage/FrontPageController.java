package example.gamerisk.frontpage;

import example.gamerisk.GameStatus;
import example.gamerisk.MainApplication;
import example.gamerisk.utils.FileOP;
import example.gamerisk.utils.LoadUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
     * @param  event :   to do ActionEvent.
     * @throws IOException :
     */
    @FXML
    void loadMap(ActionEvent event) throws IOException {
        // choose map for the new game
        Boolean bstatus = true;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select one GameMap File for Loading");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("map file", "*.map"));
        File fileSelected = fileChooser.showOpenDialog(null);

        if (fileSelected != null) {
            try {
                FileOP.readFile(fileSelected);
            } catch (Exception e) {
                e.printStackTrace();
                bstatus = false;
            }
        }
        if (bstatus) {
            choosePlayerNumber(event);
        }
    }

    void choosePlayerNumber(ActionEvent event) throws IOException{
        DialogPane setup = new DialogPane();
        setup.setHeaderText("Please select how many players from 3 to 6. ");
        FXMLLoader loader = new FXMLLoader(FrontPageController.class.getResource("/example/gamerisk/GameSetup.fxml"));
        AnchorPane chooseNumberPane = loader.load();
        setup.setContent(chooseNumberPane);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setDialogPane(setup);
        alert.showAndWait();

        if ((GameStatus.getInstance ().getPlayers() != null)
                && (!GameStatus.getInstance ().getPlayers ().isEmpty ())) {
            openGameScene (event);
        }
    }

    /**
     * This method is used to open the Game Scene
     *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    private void openGameScene(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainApplication.getGamePageController().renderMap();
        primaryStage.setScene(gameScene);
    }

    @FXML
    void loadProgress(ActionEvent event) {

    }

    @FXML
    void Exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

