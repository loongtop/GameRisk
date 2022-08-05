package riskgame.controllers.dice;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class Dice {

    private ArrayList<Integer> arrayList;

    private Dice(int numberOfDice) {
        arrayList = new ArrayList<>(numberOfDice);
    }

    public static final Dice ATTACK_1_TIMES = new Dice(1);
    public static final Dice ATTACK_2_TIMES = new Dice(2);
    public static final Dice ATTACK_3_TIMES = new Dice(3);

    public static final Dice DEFENCE_1_TIMES = new Dice(1);
    public static final Dice DEFENCE_2_TIMES = new Dice(2);

    public static Dice randomlyGenerate(int numberOfDice) {
        Dice d = new Dice(numberOfDice);
        d.rollAll();
        return d;
    }

    public ArrayList<Integer> GetAllPoints() {
        return rollAll();
    }

    public int getValueAt(int index) {
        if (index < 0 || index >= arrayList.size()) {
            return -1;
        }
        return arrayList.get(index);
    }

    public void rollOnce(int index) {
        arrayList.set(index, ThreadLocalRandom.current().nextInt(6) + 1);
    }

    public ArrayList<Integer> rollAll() {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i,ThreadLocalRandom.current().nextInt(6) + 1);
        }
        Arrays.sort(arrayList.toArray());
        return arrayList;
    }
}
