package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.*;
import sample.dto.out.AddFeedback;

import java.util.List;

public interface UserService {
    @GET("users")
    Call<List<User>> getUsers(@Query("offset") int offset);

    @GET("users/{nickname}")
    Call<User> getUsersByNickname(@Path("nickname") String nickname);

    @GET("users/{nickname}/bans")
    Call<List<Ban>> getBans(@Path("nickname") String nickname, @Query("offset") int offset, @Header("Authorization") String token);

    @GET("users/{nickname}/warns")
    Call<List<Warn>> getWarns(@Path("nickname") String nickname, @Query("offset") int offset, @Header("Authorization") String token);

    @GET("users/{nickname}/posts")
    Call<List<Post>> getPosts(@Path("nickname") String nickname, @Query("offset") int offset);

    @GET("users/{nickname}/comments")
    Call<List<Comment>> getComments(@Path("nickname") String nickname, @Query("offset") int offset);

    @GET("users/{nickname}/feedback")
    Call<List<UserFeedback>> getFeedback(@Path("nickname") String nickname, @Query("offset") int offset, @Header("Authorization") String token);

    @POST("users/{nickname}/bans")
    Call<Void> addBan(@Path("nickname") String nickname);

    @POST("users/{nickname}/warns")
    Call<Void> addWarn(@Path("nickname") String nickname);

    @POST("users/{nickname}/feedback")
    Call<Void> addFeedback(@Path("nickname") String nickname, @Body AddFeedback feedback);
}
