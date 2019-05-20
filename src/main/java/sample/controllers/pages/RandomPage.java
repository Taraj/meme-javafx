package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.controllers.components.PostController;
import sample.dto.in.Post;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

import java.io.IOException;

@Page(name = "Losowe", resource = "/pages/random.fxml")
public class RandomPage extends SuperPage {
    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private VBox mainContainer;

    @FXML
    private void nextPost() {
        randomPost();
    }


    private void randomPost(){
        postsService.getRandomPost().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/postItem.fxml"));
                        Pane pane = loader.load();
                        PostController controller = loader.getController();
                        controller.load(response.body());
                        controller.setRouter(router);
                        VBox.setMargin(pane, new Insets(50, 0, 50, 0));
                        Platform.runLater(() -> mainContainer.getChildren().setAll(pane));
                    } catch (IOException e) {
                        AlertsFactory.unknownError(e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }


    @Override
    public void init() {
       randomPost();
    }
}
