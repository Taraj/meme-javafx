package sample;

import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Synchronized;


public class State {

    @Getter
    private static SimpleBooleanProperty isAuthenticated = new SimpleBooleanProperty(false);
    @Getter
    private static String token = null;

    @Synchronized
    public static void setToken(String token) {
        if (token == null) {
            isAuthenticated.set(false);
            State.token = null;
        } else {
            isAuthenticated.set(true);
            State.token = "Bearer " + token;
        }
    }

}
