package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.Comment;
import sample.dto.out.AddFeedback;

public interface CommentService {

    @GET("comments/{id}")
    Call<Comment> getCommentById(@Path("id") long id);


    @POST("comments/{id}/feedback")
    Call<Void> addFeedback(@Path("id") long id, @Body AddFeedback feedback, @Header("Authorization") String token);

}
