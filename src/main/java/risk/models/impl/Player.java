package risk.models.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import risk.controllers.dice.Dice;
import risk.controllers.game.GameContext;
import risk.models.Card;
import risk.models.IPlayer;

import java.util.Set;

/**
 * This class represents all the data and functionality that a player would
 * have.
 */

@Builder
@Setter
@Getter
public class Player implements IPlayer {

    private static final long serialVersionUID = 1506114708731038012L;

    private int id;
    private String username;
    private int totalArmy;
    private int leftArmy;
    private String picName;
    private GameContext gameContext;

    private Dice dice;
    private Set<Card> cards;
    private Countries countries;
    private Continents.Continent continent;

    public String playerInfo() {
        StringBuilder info = new StringBuilder();

        info.append("Player information:").append("\n\n")
                .append("Username :" ).append(username).append("\n\n")
                .append("TotalArmy : ").append(totalArmy).append("\n\n")
                .append("Army : ").append(leftArmy).append("\n\n");

        if ( continent != null && countries != null) {
            info.append("Continents : ").append(continent.getName()).append("\n\n")
                    .append("Countries : ").append(countries.getAllValues().toArray().toString()).toString();
        }

        return info.toString();
    }

    public void deploy() {
    }

    public void attack(Countries.Country attacker, Countries.Country defender) {
    }

    public void reinforce() {
    }

    public void endTurn() {
    }
}
