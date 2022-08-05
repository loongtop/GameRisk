package riskgame.controllers.game.impl;

import lombok.*;

import riskgame.model.IPlayer;
import riskgame.model.impl.Map;
import riskgame.utils.CircularQueue;

import java.io.Serializable;
import java.util.Locale;

@NoArgsConstructor
public enum GameContext implements IGameContext {

    INSTANCE;
    private static final long serialVersionUID = 7913483276084823656L;

    private Map map = Map.INSTANCE;
    private GameStatus gameStatus;
    private CircularQueue<IPlayer> players = new CircularQueue<>();

    public void addPlayer(IPlayer player) {
        players.add(player);
    }

    /**
     * Get the current playing player.
     *
     * @return the current playing player.
     */
    public IPlayer getCurrentPlayer() {
        return gameStatus.getCurrentPlayingPlayer();
    }

    public String getCurrentPhase() {
        return gameStatus.getCurrentPhase().name().toLowerCase(Locale.ROOT);
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

    @Getter
    @Setter
    public static class GameStatus implements Serializable {

        private static final long serialVersionUID = 4986587502971802407L;

        public enum Phase {DEPLOY, ATTACK, REINFORCE;}

        private boolean isInitialized;
        private boolean isStart = false;
        private boolean isPause = false;
        private boolean isCountryClicked = false;
        private Phase currentPhase = Phase.DEPLOY;
        private IPlayer currentPlayingPlayer;
        /* Game Runtime info */
        private boolean isInitialPlacementComplete;
    }
}
