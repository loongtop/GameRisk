package riskgame.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

public interface IMap extends Serializable {

    void mapLoader(File file) throws InterruptedException, FileNotFoundException;

    void saveMap(String PathOut);
}
