package risk.controllers.players;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import risk.controllers.game.GameContext;
import risk.models.impl.Continents;
import risk.models.impl.Countries;
import risk.models.impl.Player;
import risk.utils.AlertError;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Setter
@Getter
public class InitPlayers {

    private static final int[] initialArmy = {35, 30, 25, 20};

    @FXML
    private AnchorPane setupPane;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;

    @Setter
    private int numberOfPlayers;
    private HashMap<TextField, TextField> playerInfo;

    public void initialize() {
        numberOfPlayers = 0;
        playerInfo = new HashMap<TextField, TextField>();
    }

    public void initial() {

        numberOfPlayers = 3;
        for (int i = 0; i < numberOfPlayers; ++i) {
            String labelName = String.format("Players %s : ", String.valueOf(i + 1));
            Label label = new Label(labelName);

            AnchorPane.setLeftAnchor(label, 60.0);
            AnchorPane.setTopAnchor(label, 50.0 + i * 40.0);

            TextField nameField = new TextField();
            nameField.setPrefWidth(200);

            AnchorPane.setLeftAnchor(nameField, 140.0);
            AnchorPane.setTopAnchor(nameField, 50.0 + i * 40.0);

            RadioButton btn = new RadioButton();
            AnchorPane.setLeftAnchor(btn, 400.0);
            AnchorPane.setTopAnchor(btn, 50.0 + i * 40.0);

            TextField picField = new TextField();
            btn.setOnAction((e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select image!");
                fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("image file", "*.png"));
                File fileSelected = fileChooser.showOpenDialog(null);

                Objects.requireNonNull(fileSelected, "image is not exists!");
                picField.setText(fileSelected.getName());
            }));

            setupPane.getChildren().addAll(label, nameField, btn);

            nameField.setText("leo" + String.valueOf(i+1));
            picField.setText(String.valueOf(i+1) + ".jpg");
            playerInfo.put(picField, nameField);
        }
    }

    @FXML
    void confirm(ActionEvent event) {
//        if (!nameValidate()) return;

        playerInfo.forEach((s, k) -> {
            Player player = Player.builder()
                    .id(playerInfo.size()+1)
                    .username(k.getText())
                    .totalArmy(initialArmy[numberOfPlayers - 3])
                    .leftArmy(initialArmy[numberOfPlayers - 3])
                    .picName(s.getText())
                    .continents(new Continents(new HashMap<String, Continents.Continent >()))
                    .countries(new Countries(new HashMap<String, Countries.Country>()))
                    .cards(new HashSet<>())
                    .gameContext(GameContext.INSTANCE)
                    .build();

            GameContext.INSTANCE.addPlayer(player);
        });
        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getParent().getScene().getWindow();
        stage.close();
    }

    private boolean nameValidate() {
        AtomicBoolean status = new AtomicBoolean(true);

        playerInfo.values().stream().forEach(s->{
            if ("".equals(s.getText().toString().trim())) {
                status.set(false);
                AlertError riskAlert = new AlertError("Please make sure all names are not null!");
                riskAlert.showAlert();
            }
        });
        return status.get();
    }
}

