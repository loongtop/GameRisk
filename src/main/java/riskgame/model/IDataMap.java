package riskgame.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public interface IDataMap<T> extends Serializable {
    boolean validate();

    void add(String name, Object t);

    T get(String name);

    HashMap<String, T> getDataMap();

    Collection<T> getAllValues();
}
