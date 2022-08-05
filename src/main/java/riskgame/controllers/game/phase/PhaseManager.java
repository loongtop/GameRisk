package riskgame.controllers.game.phase;

import javafx.fxml.FXMLLoader;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * Switch between different game phase by loading different fxml file and return the controller.
 */
public class PhaseManager {
    private PhaseDeploy phaseOne;
    private PhaseBattle phaseTwo;
    private PhaseReinforce phaseThree;

 /**
	 * It is a constructor of Phase Controller
	 * 
	 * @throws IOException
	 */

    @SneakyThrows
    public PhaseManager() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("./phaseOne/PhaseOne.fxml"));
        loader.load();
        phaseOne = loader.getController();
        loader = new FXMLLoader(this.getClass().getResource("./phaseTwo/PhaseTwo.fxml"));
        loader.load();
        phaseTwo = loader.getController();
        loader = new FXMLLoader(this.getClass().getResource("./phaseThree/PhaseThree.fxml"));
        loader.load();
        phaseThree = loader.getController();
    }

}
