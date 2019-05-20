package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.out.ResetPassword;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

@Page(resource = "/pages/reset_password.fxml")
public class ResetPasswordPage extends SuperPage {

    @FXML
    private TextField loginOrEmailField;

    @FXML
    private void reset() {
        authService.sendResetPasswordEmail(new ResetPassword(
                loginOrEmailField.getText()
        )).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Wysłano.");
                router.accept(ConfirmResetPasswordPage.class, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void confirm(){
        router.accept(ConfirmResetPasswordPage.class,null);
    }



}
