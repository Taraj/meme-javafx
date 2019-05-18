package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.Comment;
import sample.dto.in.CommentFeedback;
import sample.dto.out.AddFeedback;

public interface CommentService {

    @GET("comments/{id}")
    Call<Comment> getCommentById(@Path("id") long id);

    @DELETE("comments/{id}")
    Call<Comment> deleteComment(@Path("id") long id, @Header("Authorization") String token);

    @POST("comments/{id}/feedback")
    Call<Void> addFeedback(@Path("id") long id, @Body AddFeedback feedback, @Header("Authorization") String token);

    @GET("comments/{id}/feedback")
    Call<CommentFeedback> getFeedback(@Path("id") long id, @Header("Authorization") String token);
}
