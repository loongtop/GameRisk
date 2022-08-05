package riskgame.controllers.game.phase;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.*;

import riskgame.model.IPlayer;
import riskgame.model.impl.Countries;

/**
 * This class represents the first phase of the game
 * It updates current status
 */

@Setter
@Getter
public final class PhaseDeploy {

    @FXML
    private AnchorPane pane;
    @FXML
    private TextFlow territory;
    @FXML
    private TextFlow continent;
    @FXML
    private Text stage;
    @FXML
    private Text log;

    private IPlayer currentPlayer;

    @Getter
    @NonNull
    private Countries.Country destination;

    @Getter
    private int amount;

    private Result result;

    public Result deploy() {
        if (result != null) return result;

        int army = destination.getArmy();
        if (army < 0) {
            army = 0;
        }
        army += amount;

        Result rv = new Result(this, army);
        result = rv;
        return result;
    }

    @Value
    @ToString(includeFieldNames = true)
    public static final class Result {

        @Getter
        @NonNull
        private final PhaseDeploy deploy;

        @Getter
        private final int soldierLeft;
    }
}
