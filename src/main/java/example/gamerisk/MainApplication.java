package example.gamerisk;

import example.gamerisk.frontpage.FrontPageController;
import example.gamerisk.gampage.GamePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

/**
 * The main entrance of the application
 */

@Getter
public class MainApplication extends Application {

    private Stage stage;
    private GamePageController gamePageController;
    private FrontPageController frontPageController;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Initialize and show the window.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        mainWindow();
    }

    private void mainWindow() {

        Class currentClass = getClass();

        stage.setResizable(true);
        stage.setTitle("Risk Main Menu");

        String stylesheet = currentClass.getResource("style.css").toExternalForm();

        try {
            FXMLLoader frontPageLoader = new FXMLLoader(currentClass.getResource("FrontPage.fxml"));
            AnchorPane frontPagePane = frontPageLoader.load();
            Scene frontPageScene = new Scene(frontPagePane);
            frontPageScene.getStylesheets().addAll(stylesheet);

            FXMLLoader gamePageLoader = new FXMLLoader(currentClass.getResource("GamePage.fxml"));
            AnchorPane gamePagePane = gamePageLoader.load();
            Scene gamePageScene = new Scene(gamePagePane);
            gamePagePane.getStylesheets().addAll(stylesheet);

            //get frontPage Controller
            frontPageController = frontPageLoader.getController();
            frontPageController.setMainApplication(this);
            frontPageController.setGameScene(gamePageScene);

            gamePageController = gamePageLoader.getController();
            gamePageController.setFrontPage(frontPageScene);

            stage.setScene(frontPageScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}