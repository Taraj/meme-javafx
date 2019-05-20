package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.out.ConfirmResetPassword;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;


@Page(resource = "/pages/confirm_reset_password.fxml")
public class ConfirmResetPasswordPage extends SuperPage {

    @FXML
    private TextField loginOrEmail;

    @FXML
    private TextField code;

    @FXML
    private void reset() {
        authService.confirmResetPasswordEmail(new ConfirmResetPassword(
                loginOrEmail.getText(),
                code.getText()
        )).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Nowe Hasło zostało wysłane.");
                router.accept(LoginPage.class, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }
}
