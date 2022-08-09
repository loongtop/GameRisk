package risk.models.game;

import lombok.*;

import risk.models.impl.Countries;
import risk.models.impl.Player;

import java.io.Serializable;

/**
 * This class represents the first phase of the game
 * It updates current status
 */

@Setter
@Getter
public final class Deploy implements Serializable {

    private static final long serialVersionUID = -4322187166841713660L;

    private Player currentPlayer;

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
        private final Deploy deploy;

        @Getter
        private final int soldierLeft;
    }
}
