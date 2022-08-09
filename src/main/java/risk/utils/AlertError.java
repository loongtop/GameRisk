package risk.utils;

import javafx.scene.control.Alert;

public class AlertError {
    String message;
    Alert alert;

    public AlertError(String message) {
        this.alert = new Alert(Alert.AlertType.ERROR);
        this.message = message;
    }

    public void showAlert() {
        alert.setContentText(this.message);
        alert.show();
    }
}
