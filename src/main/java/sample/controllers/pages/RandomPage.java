package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.in.Post;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

@Page(name = "Losowe", resource = "/pages/random.fxml")
public class RandomPage extends SuperPage {

    @FXML
    private VBox mainContainer;

    @FXML
    private void nextPost() {
        randomPost();
    }

    private void randomPost() {
        postsService.getRandomPost().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    Pane pane = createPostItem(response.body());
                    Platform.runLater(() -> mainContainer.getChildren().setAll(pane));
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
