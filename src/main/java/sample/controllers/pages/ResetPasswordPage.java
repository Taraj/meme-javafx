package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.out.ResetPassword;
import sample.services.AuthService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

@Page(resource = "/pages/reset_password.fxml")
public class ResetPasswordPage extends SuperPage {
    @FXML
    private TextField loginOrEmailField;

    private AuthService authService = RetrofitInstance.getInstance().create(AuthService.class);

    @FXML
    private void reset() {
        ResetPassword resetPasswordDto = ResetPassword.builder()
                .usernameOrEmail(loginOrEmailField.getText())
                .build();
        authService.sendResetPassworEmail(resetPasswordDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Wysłano");
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
