package sample.services;

import retrofit2.Call;
import retrofit2.http.*;
import sample.dto.in.Comment;
import sample.dto.in.Post;
import sample.dto.out.AddComment;
import sample.dto.out.AddFeedback;
import sample.dto.out.AddPost;

import java.util.List;

public interface PostsService {

    @GET("posts")
    Call<List<Post>> getPosts(@Query("offset") int offset, @Query("confirmed") boolean confirmed);

    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") long id);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") long id);

    @GET("posts/random")
    Call<Post> getRandomPost();

    @POST("posts/")
    Call<Void> addPost(@Body AddPost post, @Header("Authorization") String token);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") long id, @Header("Authorization") String token);

    @PUT("posts/{id}")
    Call<Void> confirmPost(@Path("id") long id, @Header("Authorization") String token);

    @POST("posts/{id}/comments")
    Call<Void> addComment(@Path("id") long id, @Body AddComment comment, @Header("Authorization") String token);

    @POST("posts/{id}/feedback")
    Call<Void> addFeedback(@Path("id") long id, @Body AddFeedback feedback);

}
