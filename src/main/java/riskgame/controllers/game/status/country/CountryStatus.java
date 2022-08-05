package riskgame.controllers.game.status.country;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import riskgame.model.impl.Countries;

public class CountryStatus {

    @FXML
    private Text continent;

    @FXML
    private Text army;

    @FXML
    private Text owner;

    @FXML
    private Text country;

    @FXML
    private AnchorPane countryPane;

    @FXML
    private CheckBox attacker;

    @FXML
    private Text adjacent;

    private TextField players;

    @FXML
    private CheckBox defender;

    Countries.Country countrySelected;

    public void initialize() {
        countrySelected = Countries.Country.builder().build();
        owner.setText(countrySelected.getPlayer().getUsername());
//        countrySelected.setName(countryName);
        army.setText(String.valueOf(countrySelected.getArmy()));
        continent.setText(String.valueOf(countrySelected.getContinent()));
        adjacent.setText(countrySelected.getAdjacentCountry().toString());
    }

    @FXML
    void attacker(ActionEvent event) {
        if (attacker.isSelected()) {
            countrySelected.setBeAttacker(true);
            countrySelected.setBeDefender(false);
        }
    }

    @FXML
    void defender(ActionEvent event) {
        if (defender.isSelected()) {
            countrySelected.setBeAttacker(false);
            countrySelected.setBeDefender(true);
        }
    }

    @FXML
    void addPlayers(ActionEvent event) {
        int army = countrySelected.getArmy() + Integer.valueOf(players.getText());
        countrySelected.setArmy(army);
    }
}

