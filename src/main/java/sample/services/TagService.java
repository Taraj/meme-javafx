package sample.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sample.dto.in.Post;

import java.util.List;

public interface TagService {

    @GET("tags/{name}/posts")
    Call<List<Post>> getPosts(@Path("name") String name, @Query("offset") int offset);

}
