package example.gamerisk.model;

import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is to handle the information of country and manage various
 * behaviour associated with the country
 */

public class Country {

    private Color color;

    private String name;

    private Set<Country> adjacentCountry;

//    private Coordinator coordinator;
//
//    private Continent continent;

    private Player player;


    private int army = 1;

    public Country(String s, Coordinator coordinator) {
    }

    public Country(String s) {
    }

    public void setPlayer(Player player) {
    }

    public void setArmy(int armyAssigned) {
    }

    public void setCoordinator(Coordinator coordinator) {
    }

    public void setContinent(Continent continent) {
    }

    public Collection<Country> getAdjacentCountry() {
        return null;
    }


//    /**
//     * Constructor method to initial the attributes
//     *
//     * @param name        country name with String type
//     * @param coordinator coordinator with coordinator type
//     */
//
//    public Country(String name, Coordinator coordinator) {
//
//        this.name = name;
//
//        this.coordinator = coordinator;
//
//        this.adjacentCountry = new HashSet<>();
//    }
//
//    /**
//     * Constructor method to initial the attributes
//     *
//     * @param name country name with String type
//     */
//    public Country(String name) {
//
//        this.name = name;
//
//        this.adjacentCountry = new HashSet<>();
//    }
//
//    /**
//     * To get the color
//     *
//     * @return the color
//     */
//    public Color getColor() {
//        return color;
//    }
//
//    /**
//     * To set the color
//     *
//     * @param color desired player's color
//     */
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    /**
//     * To get the name of the country
//     *
//     * @return the name of the country with String type
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * To set a name to the country
//     *
//     * @param name the desired name that want to set to the country with String type
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * To get the list of the adjacent countries
//     *
//     * @return the list of the adjacent countries with List type
//     */
//    public Set<Country> getAdjacentCountry() {
//        return adjacentCountry;
//    }
//
//    /**
//     * To set the list of the adjacent countries
//     *
//     * @param adjacentCountry the list of the adjacent countries with List type
//     */
//    public void setAdjacentCountry(Set<Country> adjacentCountry) {
//        this.adjacentCountry = adjacentCountry;
//    }
//
//    /**
//     * To get the coordinate location of the country
//     *
//     * @return the coordinate location of the country
//     */
//    public Coordinator getCoordinator() {
//        return coordinator;
//    }
//
//    /**
//     * To set the coordinate postion of the country
//     *
//     * @param coordinator the desired coordinate postion of the country
//     */
//    public void setCoordinator(Coordinator coordinator) {
//        this.coordinator = coordinator;
//    }
//
//    /**
//     * To get the player
//     *
//     * @return the player with Player type
//     */
//    public Player getPlayer() {
//        return player;
//    }
//
//    /**
//     * To set the player to the continent
//     *
//     * @param player the player the want to set to the contient with Player type
//     */
//    public void setPlayer(Player player) {
//        this.player = player;
//    }
//
//    /**
//     * To get the continent
//     *
//     * @return continent with Continent type
//     */
//    public Continent getContinent() {
//        return continent;
//    }
//
//    /**
//     * To set the continent that will contain the country
//     *
//     * @param continent the desired continent want to be set with Continent type
//     */
//    public void setContinent(Continent continent) {
//
//        this.continent = continent;
//    }
//
//    public int getArmy() {
//
//        return army;
//    }
//
//    public void setArmy(int army) {
//
//        this.army = army;
//    }
//
//    @Override
//    public String toString() {
//
//        return name + " " + army;
//    }
}
