package risk.models;

import java.util.Collection;
import java.util.HashMap;

public abstract class DataMapBase<T> implements IDataMapBase {

    private final HashMap<String, T>  dataMap;

    public DataMapBase(HashMap<String, T>  dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public abstract boolean validate();

    @Override
    public void add(String name, Object t) {
        dataMap.put(name, (T) t);
    }

    @Override
    public T get(String name) {
        return dataMap.get(name);
    }

    public HashMap<String, T> getDataMap() {
        return dataMap;
    }

    public Collection<T> getAllValues() {
        return dataMap.values();
    }
}
