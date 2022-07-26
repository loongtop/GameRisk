package example.gamerisk;

import example.gamerisk.model.Player;
import example.gamerisk.utils.GameUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class maintains all information and status of the game.
 */
@Setter
@Getter
public class GameStatus {

    private static GameStatus instance = new GameStatus();

    /**
     * Get an instance of the GameStatus.
     * @return an instance of the GameStatus.
     */
    public static GameStatus getInstance() { return instance; }

    private List<Player> players;
    private int phase = 1;
    private int currentPlayerIndex = 0;
    private boolean isStart = false;
    private boolean countryClicked = false;

    /**
     * Add a player in the game.
     * @param : player is the player to be added in the game.
     */
    public void addPlayer(Player player) {

        players.add(player);
    }

    /**
     * Get the current playing player.
     * @return the current playing player.
     */
    public Player getCurrentPlayer() {

        return players.get(currentPlayerIndex);
    }

    /**
     * Turns to next player.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if (!isStart && currentPlayerIndex == 0) {
            isStart = true;
            getCurrentPlayer().gainArmy();
        }

        GameUtil.initTempMap(getCurrentPlayer());
    }

    /**
     * Turns to next phase.
     */
    public void nextPhase() {
        phase = (phase + 1) % 4;
        if (phase == 0) phase = 1;
        if (isStart && phase == 1) {
            nextPlayer();
            getCurrentPlayer().gainArmy();
        }
    }

    /**
     * Reset information of the status of the game.
     */
    public void reset() {
        phase = 1;
        currentPlayerIndex = 0;
        players = new ArrayList<>();
    }
}
