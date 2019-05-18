package sample.controllers;

import javafx.fxml.FXML;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.Post;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import javafx.scene.control.ListView;
import java.util.List;


public class Controller implements Callback {

    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private ListView<Post> list;

    @FXML
    private void test() {
        postsService.getMainPage(1).enqueue(this);
    }

    public void onResponse(Call call, Response response) {
        list.getItems().setAll((List) response.body());
    }

    public void onFailure(Call call, Throwable throwable) {
        throwable.printStackTrace();
    }
}
