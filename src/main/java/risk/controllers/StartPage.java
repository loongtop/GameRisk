package risk.controllers;

import javafx.application.Platform;
import risk.controllers.game.GameContext;
import risk.controllers.players.InitPlayers;
import risk.controllers.players.PlayersCount;
import risk.models.impl.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartPage {

    private Scene gamePageScene;

    private GamePage gamePage;

    private GameContext gameContext;
    private int players;

    /**
     * This method is used to load the game map
     *
     * @param  :event to do ActionEvent.
     * @throws IOException :
     */
    public void initialGamePage() throws IOException {
        gameContext = GameContext.INSTANCE;
        players = 0;

        FXMLLoader gamePageLoader = new FXMLLoader(getClass().getResource("/risk/fxml/GamePage.fxml"));
        AnchorPane gamePagePane = gamePageLoader.load();
        gamePageScene = new Scene(gamePagePane);
        gamePage = gamePageLoader.getController();
    }

    @FXML
    void start(ActionEvent event) throws IOException {
        initialGameMap();
        getPlayersNumber(event);
        initialPlayersNames(event);
        initialGameContext();
        gamePage.renderMap();
        gamePage.initGamePage();
        openGameScene(event);
    }

    private void initialGameContext() {
        gameContext.getGameStatus().setStart(true);
    }

    private void initialGameMap() {
        // choose map for the new game
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select one GameMap File for Loading");
//        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("map file", "*.map"));
//        File fileSelected = fileChooser.showOpenDialog(null);

        File fileSelected = new File("/Users/leocui/IdeaProjects/GameRisk/src/main/resources/risk/pic/Asia.map");
        Objects.requireNonNull(fileSelected, "Map is not exists!");
        Map.INSTANCE.mapLoader(fileSelected);
   }

    /**
     * This method is used to get the Number of Players
     *
     * @param : The actionEvent ActionEvent to start the game .
     * @throws : IOException
     */
    private void getPlayersNumber(ActionEvent event) throws IOException {
        PlayersCount numberOfPlayers;
        DialogPane dialogPane = new DialogPane();
//        dialogPane.setHeaderText("Please select the number of players.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/risk/fxml/PlayersCount.fxml"));
        AnchorPane content = loader.load();
        numberOfPlayers = loader.getController();
        dialogPane.setContent(content);

        Alert alert = new Alert(Alert.AlertType.NONE);
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
        InitPlayers initPlayers;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/risk/fxml/InitPlayers.fxml"));
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
        primaryStage.setScene(gamePageScene);
        primaryStage.show();
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

