package riskgame.model;

import riskgame.model.impl.Countries;

import java.io.Serializable;
import java.util.Set;

public interface IPlayer extends Serializable {

    void setTerritory(Set<Countries> territory);

    void gainArmy();

    String getContinentString();

    void addTerritory(Countries country, int armyAssigned);

    void updateContinent();

    String playerInfo();

    void endTurn();

    void deploy();

    void reinforce();

    void attack(Countries.Country attacker, Countries.Country defender);
}
