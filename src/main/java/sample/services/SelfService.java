package sample.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sample.dto.out.ActiveAccount;
import sample.dto.out.ChangePassword;
import sample.dto.out.SetAvatar;

public interface SelfService {

    @POST("self/active")
    Call<Void> activeAccount(@Body ActiveAccount activeAccount, @Header("Authorization") String token);

    @POST("self/active/resend")
    Call<Void> resendActivationEmail(@Header("Authorization") String token);

    @POST("self/logout")
    Call<Void> logout(@Header("Authorization") String token);

    @POST("self/avatar")
    Call<Void> setAvatar(@Body SetAvatar setAvatar, @Header("Authorization") String token);

    @POST("self/password")
    Call<Void> changePassword(@Body ChangePassword changePassword, @Header("Authorization") String token);

}
