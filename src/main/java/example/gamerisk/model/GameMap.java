package example.gamerisk.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
public class GameMap {
    private static final GameMap instance = new GameMap();

    /**
     * The method of class GameMap to  get instance
     * @return instance
     */
    public static GameMap getInstance() {
        return instance;
    }

    private File image;
    private Coordinator coordinator;
    private Boolean wrap;
    private String author;
    private String scroll;
    private Boolean warn;
    private List<Continent> continents;
    private List<Country> territories;
}
