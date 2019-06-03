package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.*;
import sample.dto.out.AddFeedback;

import java.util.List;

public interface UserService {

    @GET("users/{nickname}")
    Call<User> getUsersByNickname(@Path("nickname") String nickname);

    @GET("users/{nickname}/posts")
    Call<List<Post>> getPosts(@Path("nickname") String nickname, @Query("offset") int offset);

    @POST("users/{nickname}/feedback")
    Call<Void> addFeedback(@Path("nickname") String nickname, @Body AddFeedback feedback, @Header("Authorization") String token);

}
