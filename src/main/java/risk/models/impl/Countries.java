package risk.models.impl;

import javafx.scene.paint.Color;
import lombok.*;
import risk.models.Coordinator;
import risk.models.DataMapBase;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is to handle the information of country and manage various
 * behaviour associated with the country
 */

public final class Countries extends DataMapBase<Countries.Country> {

    private static final long serialVersionUID = -4965148830228631368L;

    @Getter
    @Setter
    private String isSelected = "";

    public Countries(HashMap<String, Country> hashMap) {
        super(hashMap);
    }

    /**
     * Method to validate every country and their adjacent countries.
     *
     * @return : boolean true means validate passed
     */
    public boolean validate() {
        return validateCountry() && validateConnected();
    }

    private boolean validateCountry() {

        if (getDataMap().isEmpty()) throw new IllegalArgumentException("No territory in the map file.");

        Country country = getAllValues()
                .stream()
                .filter(i -> i.getCoordinator() != null)
                .findAny().orElse(null);
        if (country == null) {
            throw new IllegalArgumentException("Illegal adjacent country: " + country.getName());
        }
        return true;
    }

    /**
     * Validate the map is a connected graph.
     */
    private boolean validateConnected() {
        String path = Map.INSTANCE.getMapData().getPathConfigFile();
        Object[] mapFile = null;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            mapFile = bf.lines().toArray();

            AtomicBoolean state = new AtomicBoolean(false);
            for (Object str: mapFile) {
                String strNew = String.valueOf(str).trim();
                if (str.equals("[Territories]")) state.set(true);

                if (state.get() && !str.equals("[Territories]")) {
                    String[] territory = strNew.split(",");
                    Countries.Country country = get(territory[0]);

                    for (int i = 4; i < territory.length; ++i) {
                        HashMap<String, Country> adjacentCountry = get(territory[i]).getAdjacentCountry();
                        if (!adjacentCountry.keySet().contains(country.getName())) {
                            return false;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Builder
    @Getter
    @Setter
    public static class Country implements Serializable {

        private static final long serialVersionUID = 6023908073259121859L;

        @NonNull
        private String name;
        private Color color;

        private Coordinator coordinator;
        private int army;

        @EqualsAndHashCode.Exclude
        private HashMap<String, Country> adjacentCountry;

        @EqualsAndHashCode.Exclude
        private Continents.Continent continent;
        private Player player;

        private boolean beDefender;
        private boolean beAttacker;

        @Override
        public String toString() {
            return "Country{" +
                    "name='" + name + '\'' +
                    ", player=" + player +
                    ", army=" + army +
                    '}';
        }
    }
}
