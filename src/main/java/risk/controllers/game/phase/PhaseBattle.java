package risk.controllers.game.phase;

import javafx.util.Pair;
import lombok.*;

import risk.controllers.dice.Dice;
import risk.models.impl.Countries.Country;

import java.util.ArrayList;
import java.util.Optional;

@Builder
@Getter
public final class PhaseBattle {

    @NonNull
    private Country attacker;

    @NonNull
    private Country defender;

    private Result result;

    public Result attack() {
        if (result != null) return result;

        Pair<Integer, Integer> soldiers = CalculateSoldiers();

        int attackerSoldierLeft = attacker.getArmy() - soldiers.getKey();
        int defenderSoldierLeft = defender.getArmy() - soldiers.getValue();

        Country winner = null;

        boolean isFinish = attackerSoldierLeft == 0 || defenderSoldierLeft == 0;
        if (isFinish) {
            if (attackerSoldierLeft == 0) {
                winner = defender;
            }
            if(defenderSoldierLeft == 0) {
                winner = attacker;
            }
        }

        result = new Result(this, attackerSoldierLeft, defenderSoldierLeft, isFinish, winner );
        return result;
    }

    private Pair<ArrayList<Integer>, ArrayList<Integer>> rollDice() {
        Dice dice;
        if ( defender.getArmy()>= 2) {
            dice = Dice.DEFENCE_2_TIMES;
        } else {
            dice = Dice.DEFENCE_1_TIMES;
        }

        ArrayList<Integer> attackerPoints = Dice.ATTACK_3_TIMES.GetAllPoints();
        ArrayList<Integer> defenderPoints = dice.GetAllPoints();

        return new Pair<>(attackerPoints, defenderPoints);
    }

    private Pair<Integer, Integer> CalculateSoldiers() {

        ArrayList<Integer> attackers = rollDice().getKey();
        ArrayList<Integer> defenders = rollDice().getValue();

        int attackerLeft = 0;
        int defenderLeft = 0;

        int diceCount = Math.min(attackers.size(), defenders.size());
        for(int i = 0; i < diceCount; i++) {
            if(attackers.get(i) > defenders.get(i)) {
                defenderLeft++;
            } else {
                attackerLeft++;
            }
        }

        return new Pair<>(attackerLeft, defenderLeft);
    }

    @Value
    @ToString(includeFieldNames = true)
    public static final class Result {

        @Getter
        @NonNull
        private final PhaseBattle battle;
        @Getter
        private final int attackerSoldierLeft;
        @Getter
        private final int defenderSoldierLeft;
        @Getter
        private final boolean isFinish;

        private final Country winner;

        public Optional<Country> tryGetWinner() {
            if (!isFinish) return Optional.empty();
            return Optional.of(winner);
        }
    }

}
