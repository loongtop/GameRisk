package riskgame.controllers;

import javafx.application.Platform;
import riskgame.MainApplication;
import riskgame.controllers.players.InitPlayers;
import riskgame.controllers.players.NumberOfPlayers;
import riskgame.model.impl.Map;
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

@Setter
public class FrontPage {

    @FXML
    private Button loadProgress;
    @FXML
    private Button Exit;

    private Scene gameScene;
    private MainApplication mainApplication;
    private NumberOfPlayers numberOfPlayers;
    private InitPlayers initPlayers;
    private int players;

    /**
     * This method is used to load the game map
     *
     * @param  :event to do ActionEvent.
     * @throws IOException :
     */
    @FXML
    void start(ActionEvent event) throws IOException {
        initialGameMap();
        getPlayersNumber(event);
        initialPlayersNames(event);
        openGameScene(event);
    }

    private void initialGameMap() {
        // choose map for the new game
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select one GameMap File for Loading");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("map file", "*.map"));
        File fileSelected = fileChooser.showOpenDialog(null);

        if (fileSelected != null) {
            try {
                Map.INSTANCE.mapLoader(fileSelected);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to get the Number of Players
     *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    private void getPlayersNumber(ActionEvent event) throws IOException {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Please select the number of players.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NumberOfPlayers.fxml"));
        AnchorPane content = loader.load();
        numberOfPlayers = loader.getController();
        dialogPane.setContent(content);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setDialogPane(dialogPane);
        alert.showAndWait();
        players = numberOfPlayers.getPlayers();
        alert.close();
    }

    /**
     * This method is used to initial Players Names
     *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    private void initialPlayersNames(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("InitPlayers.fxml"));
        AnchorPane playersPane = fxmlLoader.load();

        initPlayers = fxmlLoader.getController();
        initPlayers.setSetupPane(playersPane);
        initPlayers.setNumberOfPlayers(players);
        initPlayers.initial();

        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Input players name.");
        dialogPane.setContent(initPlayers.getSetupPane());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.onShownProperty().addListener(e -> {
            Platform.runLater(() -> alert.setResizable(true));
        });
        alert.setDialogPane(dialogPane);
        alert.showAndWait();
    }

    /**
     * This method is used to open the Game Scene
     *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    private void openGameScene(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainApplication.getGamePage().renderMap();
        primaryStage.setScene(gameScene);
    }

    /**
     * This method is used to get data from last restored game.
      *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    @FXML
    void loadProgress(ActionEvent event) {

    }

    @FXML
    void Exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

