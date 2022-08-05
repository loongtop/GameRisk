package riskgame.model.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import riskgame.controllers.dice.Dice;
import riskgame.controllers.game.impl.GameContext;
import riskgame.model.Card;
import riskgame.model.IPlayer;

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
    @NonNull
    private String username;
    private int army;
    private int spentArmy;
    private GameContext gameContext;

    private Dice dice;
    private Set<Card> cards;
    private Countries countries;

    /**
     * To set the countries list to belong to the player
     *
     * @param : territory the countries list desired to be set to belong to the player
     *          with HashSet type
     */
    public void setTerritory(Set<Countries> territory) {
//        this.territory = territory;
//        updateContinent();
    }

    /**
     * Method to gain the number of Army
     *
     * @return void
     */
    public void gainArmy() {

//        this.spentArmy = 0;
//        int gainedArmy = Math.max(getTerritory().size() / 3, 3);
//
//        if (getContinents() != null && !getContinents().isEmpty()) {
//            for (Continents continent : getContinents()) {
////                gainedArmy += continent.getArmy();
//            }
//        }
//        setArmy(gainedArmy);
    }

    /**
     * Method to get Continent String
     * @return the get Continent String with type String
     */
    public String getContinentString() {
        return "";
    }

    /**
     * Add a territory.
     *
     * @param country : country is the country to be added.
     * @param armyAssigned : armyAssigned assign armies to the country.
     */
    public void addTerritory(Countries country, int armyAssigned) {

//        country.setPlayer(this);
//        country.setArmy(armyAssigned);
//        this.army -= armyAssigned;
//        this.territory.add(country);
        updateContinent();
    }

    /**
     * update the continent.
     */
    public void updateContinent() {
//        for (Continents c : gameMap.getContinents()) {
//            if (!this.continents.contains(c) && this.territory.containsAll(c.getCountries())) {
//                this.continents.add(c);
//            }
//        }
    }

    /**
     * Show the information of the player.
     *
     * @return the information of the player.
     */
    public String playerInfo() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", army=" + army +
                " Available Army : " + (army - spentArmy) +
                '}';
    }

    @Override
    public void endTurn() {

    }

    @Override
    public void deploy() {

    }

    @Override
    public void attack(Countries.Country attack, Countries.Country defender) {

    }

    @Override
    public void reinforce() {
//        if (reinforcingProvince != null && reinforcedProvince != null) {
//            reinforcingProvince.getProvince().removeTroops(reinforcementCount);
//            reinforcedProvince.getProvince().addTroops(reinforcementCount);
//            Utils.connectedComponents(reinforcingProvince).forEach(p -> p.deemphasizeForReinforcement());
//            ProvinceConnector.getInstance().setPath();
//            reinforcingProvince.isSelected = false;
//            reinforcedProvince.isSelected = false;
//            nextPhase();
//        }
    }
}
