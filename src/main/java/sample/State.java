package sample;

import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Synchronized;
import sample.dto.in.AuthResponse;


public class State {

    @Getter
    private static SimpleBooleanProperty isAuthenticated = new SimpleBooleanProperty(false);
    @Getter
    private static String token = null;
    @Getter
    private static boolean isAdmin = false;
    @Getter
    private static boolean isActiveAccount = false;
    @Getter
    private static String nickname = null;

    @Synchronized
    public static void setCredential(AuthResponse auth) {
        if (auth == null) {
            isAuthenticated.set(false);
            isAdmin = false;
            token = null;
            nickname = null;
            isActiveAccount = false;
        } else {
            isAuthenticated.set(true);
            token = "Bearer " + auth.getAccessToken();
            isAdmin = auth.isAdmin();
            isActiveAccount = auth.isActive();
            nickname = auth.getNickname();
        }
    }

}
