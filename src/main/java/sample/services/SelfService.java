package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.User;
import sample.dto.out.ActiveAccount;
import sample.dto.out.ChangePassword;
import sample.dto.out.SetAvatar;

public interface SelfService {

    @GET("self")
    Call<User> getSelfInfo(@Header("Authorization") String token);

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
