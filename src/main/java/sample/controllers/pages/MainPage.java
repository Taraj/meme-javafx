package sample.controllers.pages;

import javafx.fxml.FXML;

import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

@Page(name = "Głowna", resource = "/pages/main.fxml")
public class MainPage extends SuperPage {

    @FXML
    private void totoj(){
        AlertsFactory.success("Qweeqweq");
    }

}
