package riskgame.utils;

import javafx.scene.control.Alert;

public class RiskAlert {
    String message;
    Alert alert;

    public RiskAlert(String message) {
        this.alert = new Alert(Alert.AlertType.ERROR);
        this.message = message;
    }

    public void showAlert() {
        alert.setContentText(this.message);
        alert.show();
    }

}
