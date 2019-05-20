package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.in.AuthResponse;
import sample.dto.out.Register;
import sample.services.AuthService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

@Page(resource = "/pages/register.fxml")
public class RegisterPage extends SuperPage {

    @FXML
    private TextField loginField;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    private void login() {
        router.accept(LoginPage.class,null);
    }

    private AuthService authService = RetrofitInstance.getInstance().create(AuthService.class);

    @FXML
    private void register() {
        if (!passwordField.getText().equals(rePasswordField.getText())){
            AlertsFactory.inputError("Hasła są różne");
            return;
        }

        Register registerDto = Register.builder()
                .password(passwordField.getText())
                .username(loginField.getText())
                .nickname(nicknameField.getText())
                .email(emailField.getText())
                .build();

        authService.register(registerDto).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    State.setToken(response.body().getAccessToken());
                    router.accept(AccountPage.class,null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }
}
