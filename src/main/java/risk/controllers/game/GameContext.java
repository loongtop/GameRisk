package risk.controllers.game;

import lombok.*;

import risk.models.impl.Map;
import risk.models.impl.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

@Getter
public enum GameContext implements Serializable{

    INSTANCE;
    private static final long serialVersionUID = 7913483276084823656L;

    private Map map = Map.INSTANCE;
    private HashMap<String, Player> players = new HashMap<>();
    private GameStatus gameStatus =GameStatus.builder()
            .currentPhase(GameStatus.Phase.DEPLOY)
            .isCountryClicked(false)
            .isInitialized(false)
            .isInitialized(false)
            .isInitialPlacementComplete(false)
            .isPause(false)
            .currentPlayer("")
            .isStart(true)
            .build();

    ////////////////////Method/////////////////////////////////////////
    public GameStatus.Phase getCurrentPhase() {
        return gameStatus.getCurrentPhase();
    }

    public void setCurrentPhase(GameStatus.Phase phase) {
        gameStatus.setCurrentPhase(phase);
    }
    /**
     * Set/Get the current playing player.
     *
     * @return the current playing player.
     */
    public Player getPlayer(String name) {
        return players.get(name);
    }

    public Player getCurrentPlayer() {
        String name = gameStatus.getCurrentPlayer();
        return players.get(name);
    }

    public boolean isSamePhase(GameStatus.Phase phase) {
        return getCurrentPhase().equals(phase);
    }

    public void setCurrentPlayer(String name) {
        gameStatus.setCurrentPlayer(name);
    }

    public void addPlayer(Player player) {
        players.put(player.getUsername(), player);
    }

    public void goToNextPhase() {
        if (gameStatus.currentPhase == GameStatus.Phase.DEPLOY) {
            gameStatus.setCurrentPhase(GameStatus.Phase.ATTACK);
        } else if (gameStatus.currentPhase == GameStatus.Phase.ATTACK) {
            gameStatus.setCurrentPhase(GameStatus.Phase.REINFORCE);
            //markAttackerProvince(null);
            //markDefenderProvince(null);
        } else if (gameStatus.currentPhase == GameStatus.Phase.REINFORCE) {
            gameStatus.setCurrentPhase(GameStatus.Phase.DEPLOY);
            getCurrentPlayer().endTurn();
//            turnCount++;
//            currentPlayer = players.get(turnCount % players.size());
//            currentPlayer.turn();
//            reinforcementForThisTurn = Player.calculateReinforcementsForThisTurn(currentPlayer);
//            markReinforcingProvince(null);
//            markReinforcedProvince(null);
//            BottomPanel.updateSpinnerValues(1, reinforcementForThisTurn);
//            BottomPanel.nextPhaseButton.disable();
//            timer = 0;
        }
    }

    @Builder
    @Getter
    @Setter
    public static class GameStatus implements Serializable {

        private static final long serialVersionUID = 4986587502971802407L;

        public enum Phase {DEPLOY, ATTACK, REINFORCE;}

        private boolean isInitialized = false;
        private boolean isStart = false;
        private boolean isPause = false;
        private boolean isCountryClicked = false;
        private Phase currentPhase = Phase.DEPLOY;
        private String currentPlayer;
        /* Game Runtime info */
        private boolean isInitialPlacementComplete = false;
    }


}
