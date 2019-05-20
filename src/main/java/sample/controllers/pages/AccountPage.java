package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.out.ActiveAccount;
import sample.dto.out.ChangePassword;
import sample.dto.out.SetAvatar;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;


@Page(name = "Konto", resource = "/pages/account.fxml")
public class AccountPage extends SuperPage {

    @FXML
    private VBox activeAccountContainer;

    @FXML
    private AnchorPane resendActivationEmailContainer;

    @FXML
    private Label title;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField reNewPassword;

    @FXML
    private TextField avatarUrl;

    @FXML
    private TextField activationCode;


    @FXML
    private void changePassword() {
        if (!newPassword.getText().equals(reNewPassword.getText())) {
            AlertsFactory.inputError("Hasła są różne.");
            return;
        }

        selfService.changePassword(new ChangePassword(
                oldPassword.getText(),
                newPassword.getText()
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Nowe Hasło zostało ustawione.");
                State.setCredential(null);
                router.accept(LoginPage.class, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });

    }

    @FXML
    private void setAvatar() {
        selfService.setAvatar(new SetAvatar(
                avatarUrl.getText()
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Awatar został zmieniony.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void logoutFromAll() {
        selfService.logout(State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Wylogowano.");
                State.setCredential(null);
                router.accept(LoginPage.class, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void resendActivationEmail() {
        selfService.resendActivationEmail(State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Email aktywacyjny został wysłany.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void activeAccount() {
        selfService.activeAccount(new ActiveAccount(
                activationCode.getText()
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Konto zostało aktywowane.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @Override
    public void init() {
        activeAccountContainer.setDisable(State.isActiveAccount());
        resendActivationEmailContainer.setDisable(State.isActiveAccount());
        title.setText("Witaj " + State.getNickname());
    }
}
