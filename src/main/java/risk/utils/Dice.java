package risk.utils;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public enum Dice implements Serializable {

    ATTACK_ONCE (NumberOfDice.ONE_DICE),
    ATTACK_TWICE (NumberOfDice.TWO_DICE),
    ATTACK_3_TIMES (NumberOfDice.THREE_DICE);

    private static final long serialVersionUID = 3162242848938299845L;

    private final NumberOfDice numberOfDice;
    private ArrayList<Integer> points;

    Dice(NumberOfDice numberOfDice) {
        this.numberOfDice = numberOfDice;
        points = new ArrayList<>();
    }

    public ArrayList<Integer> getSortedPoints(boolean sorted) {
        points = new ArrayList<>(numberOfDice.getPoints());
        numberOfDice.clear();

        if (sorted) Collections.sort(points, Collections.reverseOrder());
        return points;
    }

    private enum NumberOfDice {
        ONE_DICE {
            @Override
            int getNumberOfDice() {
                return 1;
            }
        },
        TWO_DICE {
            @Override
            int getNumberOfDice() {
                return 2;
            }
        },
        THREE_DICE {
            @Override
            int getNumberOfDice() {
                return 3;
            }
        },
        N_DICE {
            @Override
            int getNumberOfDice() {
                return 0;
            }
        };

        abstract int getNumberOfDice();
        private static ArrayList<Integer> arrayList = new ArrayList<>();

        ArrayList<Integer> getPoints() {
            for (int i = 0; i < getNumberOfDice(); i++) {
                arrayList.add(ThreadLocalRandom.current().nextInt(6) + 1);
            }

            return arrayList;
        }

        void clear() {
            arrayList.clear();
        }
    }

}
