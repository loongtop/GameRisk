package example.gamerisk.frontpage;

import example.gamerisk.GameStatus;
import example.gamerisk.model.GameMap;
import example.gamerisk.model.Player;
import example.gamerisk.utils.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashSet;


public class GameSetupController {

    private static final int[] initialArmy = {35, 30, 25, 20};

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private AnchorPane setupPane;

    @FXML
    private TextField numberField;

    /**
     * This method is used to confirm the event
     *
     * @param : The  ActionEvent.
     */
    @FXML
    void confirm(ActionEvent event) {

        NumberTextField numberTextField = initTextField(Integer.valueOf(numberField.getText()));

        if (numberTextField.isOutRange()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(numberTextField.getNumber() + " is invalid, please enter number between " + numberTextField.getRange());
            alert.show();
        } else {
            System.out.println(numberTextField.getNumber());
            GameStatus.getInstance().reset();
            for (int i = 0; i < numberTextField.getNumber(); i++) {
                int n = i;

                Player player = new Player(i + 1, initialArmy[numberTextField.getNumber() - 3]);
                player.setTerritory(new HashSet<>());
                while (n < GameMap.getInstance().getTerritories().size()) {
                    player.addTerritory(GameMap.getInstance().getTerritories().get(n), 1);
                    n += numberTextField.getNumber();
                }
                GameStatus.getInstance().addPlayer(player);
            }
        }
        closeStage(event);
    }

    /**
     * This method is used to cancel the event
     *
     * @param : The actionEvent ActionEvent.
     */

    public void cancel(ActionEvent event) {
        closeStage(event);
    }


    private void closeStage(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getParent().getScene().getWindow()).close();
    }

    private NumberTextField initTextField(Integer number) {

        NumberTextField numberTextField = new NumberTextField(number);
        try{
            numberTextField.setRange(3, 6);
        }
        catch (IllegalArgumentException e){
            this.confirm.setDisable(true);
        }
        return numberTextField;
    }
}


