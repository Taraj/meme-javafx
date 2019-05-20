package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.controllers.components.CommentController;
import sample.controllers.components.PostController;
import sample.dto.in.Comment;
import sample.dto.in.Post;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperProps;
import sample.util.SuperPage;

import java.io.IOException;
import java.util.List;

@Page(resource = "/pages/meme.fxml")
public class MemePage extends SuperPage {
    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int memeId;
    }








    @FXML
    private VBox memeContainer;


    @FXML
    private VBox commentContainer;

    private int getMemeId() {
        return ((Props) superProps).memeId;
    }


    private void loadMeme() {
        postsService.getPostById(getMemeId()).enqueue(new Callback<Post>() {
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
                        Platform.runLater(() -> memeContainer.getChildren().setAll(pane));
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

    private void loadComments() {
        postsService.getComments(getMemeId()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                if (response.body() != null) {
                    response.body().forEach(comment -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/commentItem.fxml"));
                            Pane pane = loader.load();
                            CommentController controller = loader.getController();
                            controller.load(comment);
                            controller.setRouter(router);
                            VBox.setMargin(pane, new Insets(5, 0, 0, 0));
                            Platform.runLater(() -> commentContainer.getChildren().add(pane));
                        } catch (IOException e) {
                            AlertsFactory.unknownError(e.getMessage());
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @Override
    public void init() {
        loadMeme();
        loadComments();
    }
}
