package sample.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sample.dto.Post;
import java.util.List;

public interface PostsService {

    @GET("posts")
    Call<List<Post>> getMainPage(@Query("page") int page);

    @GET("posts")
    Call<List<Post>> getQueuePage(@Query("page") int page);

}
