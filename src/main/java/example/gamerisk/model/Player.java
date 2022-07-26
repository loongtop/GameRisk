package example.gamerisk.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


/**
 * This class represents all of the data and funcionality that a player would
 * have.
 */
@Setter
@Getter
public class Player {

    private int id;
    private String username = "";
    private int army;
    private int spentArmy = 0;

    private Set<Card> cards;
    private Set<Country> territory;
    private Set<Continent> continents = new HashSet<>();

    /**
     * Constructor method
     *
     * @param :id   the player id with int type
     * @param : army the numer of army with int type
     * <p>
     * the set of countries set to player in the begin
     */
    public Player(int id, int army) {
        this.id = id;
        this.army = army;
    }

    /**
     * To set the countries list to belong to the player
     *
     * @param : territory the countries list desired to be set to belong to the player
     *                  with HashSet type
     */
    public void setTerritory(Set<Country> territory) {
        this.territory = territory;
        updateContinent();
    }

    /**
     * Method to gain the number of Army
     *
     * @return the number of Army gained with type int
     */
    public void gainArmy() {

    	this.spentArmy = 0;
        int gainedArmy = Math.max(getTerritory().size() / 3, 3);

        if (getContinents() != null && !getContinents().isEmpty()) {

            for (Continent continent : getContinents()) {

                gainedArmy += continent.getArmy();
            }
        }
        setArmy(gainedArmy);
    }

    /**
     * Method to get Continent String
     *
     * @return the get Continent String with type String
     */
    public String getContinentString() {

        if (continents.isEmpty()) return "";

        StringBuilder stringBuilder = new StringBuilder();

        territory.forEach(i -> {
            stringBuilder.append(i);
            stringBuilder.append(" ");
        });

        return stringBuilder.toString();
    }


    /**
     * Add a territory.
     * @param country is the country to be added.
     * @param armyAssigned assign armies to the country.
     */
    public void addTerritory(Country country, int armyAssigned) {

        country.setPlayer(this);

        country.setArmy(armyAssigned);

        this.army -= armyAssigned;

        this.territory.add(country);

        updateContinent();

    }

    /**
     * update the continent.
     */
    private void updateContinent() {

        for (Continent c : GameMap.getInstance().getContinents()) {

            if (!this.continents.contains(c) && this.territory.containsAll(c.getCountries())) {

                this.continents.add(c);
            }

        }
    }

    /**
     * Show the information of the player.
     * @return the information of the player.
     */
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", army=" + army +
                " Available Army : " + (army - spentArmy) +
                '}';
    }
}
