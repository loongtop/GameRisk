package riskgame.controllers.players;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import riskgame.controllers.game.impl.GameContext;
import riskgame.model.IPlayer;
import riskgame.model.impl.Player;
import riskgame.utils.RiskAlert;

import java.util.HashMap;
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
    private HashMap<String, TextField> playerNames;

    public void initialize() {
        numberOfPlayers = 0;
        playerNames = new HashMap<String, TextField>();
    }

    public void initial() {

        for (int i = 0; i < numberOfPlayers; ++i) {
            String labelName = String.format("Players %s : ", String.valueOf(i + 1));
            Label label = new Label(labelName);

            AnchorPane.setLeftAnchor(label, 60.0);
            AnchorPane.setTopAnchor(label, 50.0 + i * 40.0);

            TextField textField = new TextField();
            textField.setPrefWidth(200);

            AnchorPane.setLeftAnchor(textField, 140.0);
            AnchorPane.setTopAnchor(textField, 50.0 + i * 40.0);

            setupPane.getChildren().addAll(label, textField);
            playerNames.put(String.valueOf(i + 1), textField);
        }
    }

    @FXML
    void confirm(ActionEvent event) {
//        if (!nameValidate()) return;

        for (int i = 0; i < playerNames.size(); ++i) {
            TextField t = playerNames.get(String.valueOf(i + 1));
            IPlayer player = Player.builder()
                    .id(i + 1)
                    .username(t.getText().toString())
                    .army(initialArmy[numberOfPlayers - 3])
                    .spentArmy(0)
                    .build();

            GameContext.INSTANCE.addPlayer(player);
        }
        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getParent().getScene().getWindow();
        stage.close();
    }

    private boolean nameValidate() {
        AtomicBoolean status = new AtomicBoolean(true);

        playerNames.values().stream().forEach(s->{
            if ("".equals(s.getText().toString().trim())) {
                status.set(false);
                RiskAlert riskAlert = new RiskAlert("Please make sure all names are not null!");
                riskAlert.showAlert();
            }
        });
        return status.get();
    }
}

