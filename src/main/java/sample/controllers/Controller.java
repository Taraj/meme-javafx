package sample.controllers;

import javafx.fxml.FXML;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.in.Post;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import javafx.scene.control.ListView;

import java.util.List;


public class Controller implements Callback<List<Post>> {

    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private ListView<Post> list;

    @FXML
    private void test() {
        postsService.getPosts(0, true).enqueue(this);
    }

    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful()) {
            list.getItems().setAll(response.body());
        } else {
            try {
                if (response.errorBody() != null) {
                    System.out.println(response.errorBody().string());
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void onFailure(Call<List<Post>> call, Throwable throwable) {
        throwable.printStackTrace();
    }
}
