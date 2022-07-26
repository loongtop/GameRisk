package example.gamerisk.gampage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GamePageController {

    @FXML
    private ImageView gameMapPane;

    @FXML
    private Label playerInfo;

    @FXML
    private MenuItem Save;

    @FXML
    private AnchorPane statusPane;

    @FXML
    private MenuItem MainMenu;

    private Scene frontPage;

    @FXML
    void goToMainMenu(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    public void renderMap() {
    }
}
