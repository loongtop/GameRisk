package example.gamerisk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class create methods to add/remove countries to Continents, the number of army to Continents
 * the toString method lists the countries on the continent.
 */
public class Continent {

    private String name;

    private int army;

    private List<Country> countries;

    /**
     * Construction method with incoming parameters.
     *
     * @param name continent name with String type
     * @param army  number of army can get after conquest the continent
     */

    public Continent(String name, int army) {

        if ("".equals(name.trim()) || army == 0) {

            throw new IllegalArgumentException("Invalid data for continent: " + name + ", army:" + army);
        }
        this.name = name;

        this.army = army;

        this.countries = new ArrayList<>();
    }

    /**
     * To get the name of the continent
     *
     * @return the name of the continent with String type
     */
    public String getName() {
        return name;
    }

    /**
     * To set a name to  the continent
     *
     * @param name the desired name that user wants to set to the continent with String type
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To get the number of army
     *
     * @return army
     */

    public int getArmy() {
        return army;
    }

    /**
     * To set the army
     *
     * @param army, the number of army with int type
     */
    public void setArmy(int army) {
        this.army = army;
    }

    /**
     * To get the country list
     *
     * @return the list of countried with ArrayList type
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * To set the country list
     *
     * @param countries, the list of countries with ArrayList type
     */

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
