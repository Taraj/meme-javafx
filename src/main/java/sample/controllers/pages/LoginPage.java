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
import sample.services.AuthService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;


@Page(name = "Zaloguj", resource = "/pages/login.fxml")
public class LoginPage extends SuperPage {

    private AuthService authService = RetrofitInstance.getInstance().create(AuthService.class);

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private void login() {
        Login loginDto = Login.builder()
                .username(loginField.getText())
                .password(passwordField.getText())
                .build();

        authService.login(loginDto).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    State.setToken(response.body().getAccessToken());
                    router.accept(AccountPage.class);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }
    @FXML
    private void register(){
        router.accept(RegisterPage.class);
    }

    @FXML
    private void reset(){
        router.accept(ResetPasswordPage.class);
    }
}
