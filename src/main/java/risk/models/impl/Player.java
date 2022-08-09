package risk.models.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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

    private Set<Card> cards;
    private Countries countries;
    private Continents continents;

    public String playerInfo() {
        StringBuilder info = new StringBuilder();

        info.append("Player information:").append("\n\n")
                .append("Username :" ).append(username).append("\n\n")
                .append("Total Army : ").append(totalArmy).append("\n\n")
                .append("Left Army : ").append(leftArmy).append("\n\n");

         if ( continents != null) {
             info.append("Continents : ");
             for (Continents.Continent c : continents.getAllValues()){
                 info.append(c.getName()).append(" ");
             }
             info.append("\n\n");
         }
         if ( countries != null) {
             info.append("Countries : ");
             for (Countries.Country c : countries.getAllValues()){
                 info.append(c.getName()).append(" ");
             }
         }

        return info.toString();
    }


    public void endTurn() {
    }
}
