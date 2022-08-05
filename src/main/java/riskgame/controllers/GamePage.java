package riskgame.controllers;

import javafx.geometry.Dimension2D;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

import riskgame.controllers.game.impl.GameContext;
import riskgame.model.impl.Countries;
import riskgame.model.impl.Map;
import riskgame.utils.ColorUtil;
import riskgame.utils.RiskAlert;


@Getter
public class GamePage implements Serializable {

    private static final long serialVersionUID = -8360020867886787131L;

    @FXML
    private AnchorPane gameMapPane;
    @FXML
    private ImageView playerImage;
    @FXML
    private Text playerName;
    @FXML
    private Button deploy;
    @FXML
    private Button attack;
    @FXML
    private Button reinforce;
    @FXML
    private MenuItem Save;
    @FXML
    private Button finish;
    @FXML
    private MenuItem MainMenu;
    @FXML
    private ImageView imageView;

    @Setter
    private Scene frontPage;

    private GameContext gameContext = GameContext.INSTANCE;

    public boolean manualPlacement;
    public boolean isManualPlacementDone = false;

    public Countries.Country attacker = null;
    public Countries.Country defender = null;

    public Countries.Country reinforcingProvince = null;
    public Countries.Country clickedCountry;

    public void initialize() {
        if (!manualPlacement) {
            randomPlacement();
        }
    }

    private void randomPlacement() {
    }

    @FXML
    void deploy(ActionEvent event) {
        String phase = gameContext.getCurrentPhase();
        if (phase.equals("deploy")) {
            gameContext.getCurrentPlayer().deploy();
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("Current phase is " + phase);
            alert.show();
        }
    }

    @FXML
    void attack(ActionEvent event) {
        String phase = gameContext.getCurrentPhase();

        if (phase.equals("attack")) {
            gameContext.getCurrentPlayer().attack(attacker, defender);
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("Current phase is " + phase);
            alert.show();
        }
    }

    @FXML
    void reinforce(ActionEvent event) {
        String phase = gameContext.getCurrentPhase();
        if (phase.equals("reinforce")) {
            gameContext.getCurrentPlayer().reinforce();
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText("Current phase is " + phase);
            alert.show();
        }
    }

    /**
     * render the map
     */
    public void renderMap() throws IOException {

        setGameMap();

        for (Countries.Country country : Map.INSTANCE.getCountriesList()) {
            RadioButton button = new RadioButton();
            button.setText("0");
            Background background = new Background(
                    new BackgroundFill(ColorUtil.getContinentColor(
                            Map.INSTANCE.getContinentList().indexOf(country.getContinent())),
                            CornerRadii.EMPTY,
                            Insets.EMPTY));
            button.setBackground(background);
            double x = ((double) country.getCoordinator().getX() / imageView.getImage().getWidth()) * imageView.getFitWidth();
            double y = ((double) country.getCoordinator().getY() / imageView.getImage().getHeight()) * imageView.getFitHeight();

            AnchorPane.setLeftAnchor(button, x);
            AnchorPane.setTopAnchor(button, y);
            buttonAction(button, country.getName());
            gameMapPane.getChildren().add(button);
        }
    }

    private void buttonAction(RadioButton button, String name) {
        button.setOnAction((ActionEvent event) -> {
            if (gameContext.getCurrentPhase().equals("deply")) {
                //Countries.INSTANCE.setIsSelected(name);
                try {
                    DialogPane setup = new DialogPane();
                    setup.setHeaderText("Country Information");
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("CountryStatus.fxml"));
                    AnchorPane countryPane = loader.load();
                    setup.setContent(countryPane);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setDialogPane(setup);
                    alert.showAndWait();
                } catch (IOException e) {
                    RiskAlert alert = new RiskAlert(e.getMessage());
                    alert.showAlert();
                }
            } else if (gameContext.getCurrentPhase().equals("attack")) {

            } else if (gameContext.getCurrentPhase().equals("reinnforce")) {

            } else {

            }
        });
    }

    private void setGameMap() {
        BufferedImage readImage;
        Dimension2D dimension2D = null;

        try {
            readImage = ImageIO.read(Map.INSTANCE.getMapData().getImageFile());
            int h = readImage.getHeight();
            int w = readImage.getWidth();
            dimension2D = new Dimension2D(w, h);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

        int width = (int) dimension2D.getWidth();
        int height = (int) dimension2D.getHeight();

        Image image = null;
        try {
            image = new Image(Map.INSTANCE.getMapData().getImageFile().toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);
        imageView.setFitWidth((width * imageView.getFitHeight() / height));
    }

    @FXML
    void save(ActionEvent event) throws IOException {

        Map.INSTANCE.saveMap("save/");
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        Stage stage = (Stage) this.gameMapPane.getScene().getWindow();
        stage.setScene(frontPage);
    }

    @FXML
    void finish(ActionEvent event) {
        String phaseName = gameContext.getCurrentPhase();
        String message = "Confirm to finish phase" + phaseName;
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                message,
                new ButtonType("No", ButtonBar.ButtonData.NO),
                new ButtonType("Yes", ButtonBar.ButtonData.YES));

        alert.setTitle("确认");
        alert.setHeaderText("FINISH");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            gameContext.goToNextPhase();
            buttonDisabled(gameContext.getCurrentPhase());
        } else {
            alert.close();
        }
    }

    private void buttonDisabled(String phase) {
        if (phase.equals("deply")) {
            attack.setDisable(true);
            reinforce.setDisable(true);
        }

        if (phase.equals("attack")) {
            deploy.setDisable(true);
            reinforce.setDisable(true);
        }

        if (phase.equals("reinforce")) {
            deploy.setDisable(true);
            attack.setDisable(true);
        }
    }
}

