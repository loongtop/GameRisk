package risk.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public interface IDataMapBase<T> extends Serializable {
    boolean validate();

    void add(String name, Object t);

    T get(String name);

    HashMap<String, T> getDataMap();

    Collection<T> getAllValues();
}
