package risk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

import risk.controllers.StartPage;
import risk.controllers.GamePage;

import java.io.IOException;

/**
 * The main entrance of the application
 */

@Getter
public class MainApp extends Application {

    private Stage stage;
    private GamePage gamePage;
    private StartPage startPage;

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

        stage.setResizable(true);
        stage.setTitle("Risk Main Menu");

        String stylesheet = getClass().getResource("/risk/css/style.css").toExternalForm();

        try {
            FXMLLoader frontPageLoader = new FXMLLoader(getClass().getResource("/risk/fxml/FrontPage.fxml"));
            AnchorPane frontPagePane = frontPageLoader.load();
            Scene frontPageScene = new Scene(frontPagePane);
            frontPageScene.getStylesheets().addAll(stylesheet);

            //get frontPage Controller
            startPage = frontPageLoader.getController();
            startPage.initialGamePage();

            stage.setScene(frontPageScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}