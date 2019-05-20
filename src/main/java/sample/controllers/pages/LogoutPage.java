package sample.controllers.pages;

import sample.State;
import sample.util.Page;
import sample.util.SuperPage;

@Page(name = "Wyloguj", resource = "/pages/logout.fxml")
public class LogoutPage extends SuperPage {

    @Override
    public void init() {
        State.setCredential(null);
    }
}
