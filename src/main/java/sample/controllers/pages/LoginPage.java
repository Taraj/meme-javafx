package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.in.AuthResponse;
import sample.dto.out.Login;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;


@Page(name = "Zaloguj", resource = "/pages/login.fxml")
public class LoginPage extends SuperPage {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private void login() {
        authService.login(new Login(
                loginField.getText(),
                passwordField.getText()
        )).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    State.setCredential(response.body());
                    router.accept(AccountPage.class, null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void register() {
        router.accept(RegisterPage.class, null);
    }

    @FXML
    private void reset() {
        router.accept(ResetPasswordPage.class, null);
    }
}
