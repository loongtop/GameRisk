package riskgame.model.impl;

import lombok.*;
import riskgame.model.DataMapBase;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class create methods to add/remove countries to Continents, the number of army to Continents
 * the toString method lists the countries on the continent.
 */

public final class Continents extends DataMapBase<Continents.Continent> {

    private static final long serialVersionUID = 6714708201174941610L;

    public Continents(HashMap<String, Continent> hashMap) {
        super(hashMap);
    }

    public boolean validate() {
        if (this.getDataMap().isEmpty()) {
            throw new IllegalArgumentException("No continent in the map file");
        }

        return validateContinent();
    }

    private boolean validateContinent() {
        AtomicBoolean status = new AtomicBoolean(true);
        getAllValues().forEach(s -> {
            if (s.getCountries() == null) {
                status.set(false);
            }
        });
        return status.get();
    }

    @Builder
    @Getter
    public static class Continent implements Serializable {

        private static final long serialVersionUID = -5423682653065849962L;

        @Getter
        private String name;
        private int army;

        private HashSet<Countries.Country> countries;
        //    private final int captureBonus;
    }
}
