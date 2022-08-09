package risk.models.game;

import javafx.util.Pair;
import lombok.*;

import risk.utils.Dice;
import risk.models.impl.Countries.Country;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

@Builder
public final class Battle implements Serializable {

    private static final long serialVersionUID = 7928038356685522717L;

    @NonNull
    private Country attacker;
    @NonNull
    private Country defender;

    private Result result;

    /////////method/////////
    public Result startAttack(boolean once) {
        if (result != null) return result;

        int attackerLeft = 0;
        int defenderLeft = 0;
        Country winner = null;
        boolean isFinish = false;
        StringBuilder message = new StringBuilder(attacker.getName()).append("===ATTACK==>").append(defender.getName()).append("\n");

        do {
            Pair<Integer, Integer> armies = CalculateArmies();

            attacker.setArmy(attacker.getArmy() - armies.getKey());
            defender.setArmy(defender.getArmy() - armies.getValue());

            attackerLeft = attacker.getArmy();
            defenderLeft = defender.getArmy();

            if (attackerLeft == 0 || defenderLeft == 0) {
                if (attackerLeft == 0) {
                    winner = defender;
                }
                if(defenderLeft == 0) {
                    winner = attacker;
                }
                break;
            }
        }while (!once);

        if (winner != null) {
            message.append("Winner is ").append(winner.getName()).toString();
        }else {
            message.append("Attacker Army Left").append(attackerLeft).append("\n")
                    .append("Defender Army Left").append(defenderLeft).append("\n").toString();
        }

        result = new Result(this, attackerLeft, defenderLeft, isFinish, winner, message.toString());
        return result;
    }

    private Pair<ArrayList<Integer>, ArrayList<Integer>> rollDice() {

        Dice dice = (attacker.getArmy() >= 2) ? Dice.ATTACK_TWICE : Dice.ATTACK_ONCE;
        ArrayList<Integer> attackerPoints = dice.getSortedPoints(true);

        dice = (defender.getArmy() >= 2) ? Dice.ATTACK_TWICE : Dice.ATTACK_ONCE;
        ArrayList<Integer> defenderPoints = dice.getSortedPoints(true);

        return new Pair<>(attackerPoints, defenderPoints);
    }

    private Pair<Integer, Integer> CalculateArmies() {

        Pair<ArrayList<Integer>, ArrayList<Integer>> points = rollDice();

        ArrayList<Integer> attackers = points.getKey();
        ArrayList<Integer> defenders = points.getValue();

        int attackerLose = 0;
        int defenderLose = 0;
        for(int i = 0; i <  Math.min(attackers.size(), defenders.size()); i++) {
            if(attackers.get(i) <= defenders.get(i)) {
                attackerLose++;
            } else {
                defenderLose++;
            }
        }

        return new Pair<>(attackerLose, defenderLose);
    }

    @Value
    @ToString(includeFieldNames = true)
    public static final class Result {

        @Getter
        @NonNull
        private final Battle battle;
        @Getter
        private final int attackerArmyLeft;
        @Getter
        private final int defenderArmyLeft;
        @Getter
        private final boolean isFinish;

        private final Country winner;

        private final String message;

        public Optional<Country> tryGetWinner() {
            if (!isFinish) return Optional.empty();
            return Optional.of(winner);
        }
    }
}
