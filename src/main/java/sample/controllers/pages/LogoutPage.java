package sample.controllers.pages;

import javafx.fxml.Initializable;
import sample.State;
import sample.util.Page;
import sample.util.SuperPage;

import java.net.URL;
import java.util.ResourceBundle;

@Page(name = "Wyloguj", resource = "/pages/logout.fxml")
public class LogoutPage extends SuperPage implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        State.setToken(null);
    }
}
