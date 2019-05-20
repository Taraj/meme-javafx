package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.out.ConfirmResetPassword;
import sample.dto.out.Login;
import sample.services.AuthService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;


@Page(resource = "/pages/confirm_reset_password.fxml")
public class ConfirmResetPasswordPage extends SuperPage {
    @FXML
    private TextField loginOrEmailField;

    @FXML
    private TextField codeField;

    private AuthService authService = RetrofitInstance.getInstance().create(AuthService.class);

    @FXML
    private void reset() {
        int code = -1;
        try {
            code = Integer.parseInt(codeField.getText());
        } catch (NumberFormatException e) {
            AlertsFactory.inputError("Podano niepoprawy kod.");
        }

        ConfirmResetPassword confirmResetPasswordDto = ConfirmResetPassword.builder()
                .code(code)
                .usernameOrEmail(loginOrEmailField.getText())
                .build();
        authService.confirmResetPassworEmail(confirmResetPasswordDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Nowe Hasło zostało wysłane");
                router.accept(LoginPage.class,null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });

    }
}
