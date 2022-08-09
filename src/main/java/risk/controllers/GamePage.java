package risk.controllers;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

import risk.controllers.game.GameContext;
import risk.controllers.game.phase.PhaseBattle;
import risk.controllers.game.status.CountryInfo;
import risk.models.Coordinator;
import risk.models.impl.Countries;
import risk.models.impl.Map;
import risk.models.impl.Player;
import risk.utils.ColorUtil;
import risk.utils.RiskAlert;

import risk.controllers.game.GameContext.GameStatus.Phase;


@Getter
public class GamePage implements Serializable {

    private static final long serialVersionUID = -8360020867886787131L;

    @FXML
    private ComboBox playersChoice;

    @FXML
    private AnchorPane gameMapPane;
    @FXML
    private ImageView playerImage;
    @FXML
    private Text playerName;
    @FXML
    private TextArea playerInfo;
    @FXML
    private Button deployBtn;
    @FXML
    private Button attackBtn;
    @FXML
    private Button reinforceBtn;
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
    private Map gameMap = Map.INSTANCE;

    private boolean manualPlacement;
    private boolean isManualPlacementDone = false;

    @Setter
    private Countries.Country attacker = null;
    @Setter
    private Countries.Country defender = null;

    private Countries.Country reinforcingProvince = null;
    private Countries.Country clickedCountry;

    public void initialize() {
        if (!manualPlacement) {
            randomPlacement();
        }

        gameContext.setCurrentPhase(GameContext.GameStatus.Phase.DEPLOY);
    }

    public void initGamePage() {
        // initial players
        gameContext.getPlayers().forEach((k,v)->{
            playersChoice.getItems().add(k);
        });
        playersChoice.getSelectionModel().select(0);
        playersChoice.show();

        String name = playersChoice.getItems().get(0).toString();
        gameContext.setCurrentPlayer(name);
        refreshPlayerInfo();
    }

    @FXML
    void deploy(ActionEvent event) {
        buttonDisabled();
        if (gameContext.isSamePhase(Phase.DEPLOY)) {
            gameContext.getCurrentPlayer().deploy();
        } else {
            RiskAlert alert = new RiskAlert("Current phase is " + gameContext.getGameStatus().toString());
            alert.showAlert();
        }
    }

    @FXML
    void attack(ActionEvent event) {
        buttonDisabled();
        if (gameContext.isSamePhase(Phase.ATTACK)) {
            gameContext.getCurrentPlayer().attack(attacker, defender);
        } else {
            RiskAlert alert = new RiskAlert("Current phase is " + gameContext.getGameStatus().toString());
            alert.showAlert();
        }
    }

    @FXML
    void reinforce(ActionEvent event) {
        buttonDisabled();
        if (gameContext.isSamePhase(Phase.REINFORCE)) {
            gameContext.getCurrentPlayer().reinforce();
        } else {
            RiskAlert alert = new RiskAlert("Current phase is " + gameContext.getGameStatus().toString());
            alert.showAlert();
        }
    }

    /**
     * render the map
     */
    public void renderMap() throws IOException {

        setGameMap();

        for (Countries.Country country : Map.INSTANCE.getCountriesMap().getAllValues()) {
            RadioButton btn = new RadioButton();
            btn.setText("0");
            Background background = new Background(
                    new BackgroundFill(ColorUtil.getContinentColor(1),
//                            gameMap.getCountriesMap().getAllValues().indexOf(country.getContinent())),
                            CornerRadii.EMPTY,
                            Insets.EMPTY));
            btn.setBackground(background);

            Coordinator cor = calCoordinator(country.getCoordinator());

            btn.setOnAction((s)->{
                if (gameContext.isSamePhase(Phase.DEPLOY)) {
                    actionDeploy(country.getName(), btn);
                }else if(gameContext.isSamePhase(Phase.ATTACK)) {
                    actionAttack(country.getName(), btn);
                } else if (gameContext.isSamePhase(Phase.REINFORCE)) {
                    actionReinforce(country.getName(), btn);
                }

            });
            AnchorPane.setLeftAnchor(btn, cor.getX());
            AnchorPane.setTopAnchor(btn, cor.getY());
            gameMapPane.getChildren().add(btn);
        }
    }

    private Coordinator calCoordinator(Coordinator cor) {
        return new Coordinator( ((double)cor.getX() / imageView.getImage().getWidth()) * imageView.getFitWidth(),
                ((double) cor.getY() / imageView.getImage().getHeight()) * imageView.getFitHeight());
    }

    public void actionDeploy(String name, RadioButton btn) {
        try {
            DialogPane setup = new DialogPane();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/risk/fxml/CountryInfo.fxml"));
            //initialize
            AnchorPane countryPane = loader.load();
            CountryInfo countryInfo = loader.getController();
            countryInfo.setGamePage(this);
            countryInfo.setBtn(btn);
            countryInfo.setCountrySelected(Map.INSTANCE.getCountriesMap().get(name));
            countryInfo.initInfo();

            setup.setContent(countryPane);
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setDialogPane(setup);
            alert.showAndWait();
        } catch (IOException e) {
            RiskAlert alert = new RiskAlert(e.getMessage());
            alert.showAlert();
        }
    }

    public void actionAttack(String name, RadioButton btn) {
        if (attacker == null) {
            setAttacker( Map.INSTANCE.getCountriesMap().get(name));

            Coordinator startPoint = calCoordinator(attacker.getCoordinator());
            for (Countries.Country c : attacker.getAdjacentCountry().values()) {
                Coordinator endPoint = calCoordinator(c.getCoordinator());
                Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                line.setStroke(Color.RED);
                ((AnchorPane)btn.getParent()).getChildren().add(line);
            }
            return;
        }

        defender = Map.INSTANCE.getCountriesMap().get(name);
        if (!attacker.getAdjacentCountry().values().contains(defender)) {
            RiskAlert RiskAlert = new RiskAlert("Please choose right country");
            RiskAlert.showAlert();
            return;
        }

        StringBuilder message = new StringBuilder(attacker.getName()).append("====== ATTACK ====>").append(defender.getName());
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                message.toString(),
                new ButtonType("No", ButtonBar.ButtonData.NO),
                new ButtonType("Yes", ButtonBar.ButtonData.YES));

        Optional<ButtonType> btnAttack = alert.showAndWait();
        if (btnAttack.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {

            PhaseBattle phaseBattle = PhaseBattle.builder()
                    .attacker(attacker)
                    .defender(defender)
                    .result(null)
                    .build();
            PhaseBattle.Result result = phaseBattle.attack();

            int size = attacker.getAdjacentCountry().values().size();
            int childrenSize = ((AnchorPane)btn.getParent()).getChildren().size();
            for (int i = 1; i < size + 1; i++){
                ((AnchorPane)btn.getParent()).getChildren().remove(childrenSize-i);
            }
            attacker = null;
        }
        defender = null;
    }

    public void actionReinforce(String name, RadioButton btn) {}

    private void setGameMap() {
        BufferedImage readImage;
        Dimension2D dimension2D = null;

        try {
            readImage = ImageIO.read(Map.INSTANCE.getMapData().getImageFile());
            dimension2D = new Dimension2D(readImage.getWidth(), readImage.getHeight());
        } catch (Exception e) {
            RiskAlert alert = new RiskAlert(e.getMessage());
            alert.showAlert();
        }

        Image image = null;
        try {
            image = new Image(Map.INSTANCE.getMapData().getImageFile().toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);
        imageView.setFitWidth((int) dimension2D.getWidth() * 1.5);
        imageView.setFitHeight((int) dimension2D.getHeight() * 1.5);
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
        Phase phase = gameContext.getCurrentPhase();
        String message = "Confirm to finish phase" + phase.toString();
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                message,
                new ButtonType("No", ButtonBar.ButtonData.NO),
                new ButtonType("Yes", ButtonBar.ButtonData.YES));

        alert.setTitle("Confirm");
        alert.setHeaderText("Finish");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            gameContext.goToNextPhase();
            attackBtn.setDisable(false);
            deployBtn.setDisable(false);
            reinforceBtn.setDisable(false);
        } else {
            alert.close();
        }
    }

    private void buttonDisabled() {
        if (gameContext.isSamePhase(Phase.DEPLOY)) {
            attackBtn.setDisable(true);
            reinforceBtn.setDisable(true);
        }

        if (gameContext.isSamePhase(Phase.ATTACK)) {
            deployBtn.setDisable(true);
            reinforceBtn.setDisable(true);
        }

        if (gameContext.isSamePhase(Phase.REINFORCE)) {
            deployBtn.setDisable(true);
            attackBtn.setDisable(true);
        }
    }

    private void randomPlacement() {
    }

    @FXML
    public void playersChoice(ActionEvent event) {
        String name = playersChoice.getSelectionModel().getSelectedItem().toString();
        gameContext.setCurrentPlayer(name);
        refreshPlayerInfo();
    }

    public void refreshPlayerInfo() {
        Player player= gameContext.INSTANCE.getCurrentPlayer();

        String picPath = "/risk/pic/";
        playerName.setText(player.getUsername());
        playerImage.setImage(new Image(getClass().getResource(picPath + player.getPicName()).toExternalForm()));
        playerInfo.setText(player.playerInfo());
    }
}

