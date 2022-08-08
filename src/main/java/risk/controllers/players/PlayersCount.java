package risk.controllers.players;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.List;

public class PlayersCount {

    private final List<Integer> initialArmy = List.of(35, 30, 25, 20);

    @FXML
    private ChoiceBox numberOfPlayers;

    @Setter
    @Getter
    private Integer players;

    /**
     * This method is for initializatioon
     *
     */
    public void initialize() {

        numberOfPlayers.getItems().add("3");
        numberOfPlayers.getItems().add("4");
        numberOfPlayers.getItems().add("5");
        numberOfPlayers.getItems().add("6");

        numberOfPlayers.getSelectionModel().select(0);
        numberOfPlayers.show();
    }

    /**
     * This method is used to confirm the event
     *
     * @param : event is to confirm
     */
    @FXML
    void confirm(ActionEvent event) {
        final int[] players = {0};
        numberOfPlayers.getSelectionModel()
                .selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        players[0] = new_value.intValue();                    }
                });
        setPlayers(Integer.valueOf(numberOfPlayers.getSelectionModel().getSelectedItem().toString()));

        cancel(event);
    }

    /**
     * This method is used to cancel the event
     *
     * @param event : The actionEvent ActionEvent.
     */

    public void cancel(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getParent().getScene().getWindow()).close();
    }
}


