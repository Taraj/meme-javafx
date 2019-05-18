package sample.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sample.dto.in.AuthResponse;
import sample.dto.out.*;

public interface AuthService {

    @POST("auth/login")
    Call<AuthResponse> login(@Body Login login);

    @POST("auth/register")
    Call<AuthResponse> register(@Body Register register);

    @POST("auth/reset")
    Call<Void> sendResetPassworEmail(@Body ResetPassword resetPassword);

    @POST("auth/reset/confirm")
    Call<Void> confirmResetPassworEmail(@Body ConfirmResetPassword confirmResetPassword);
}
