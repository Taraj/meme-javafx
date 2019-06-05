package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.controllers.components.PostController;
import sample.dto.in.Comment;
import sample.dto.in.Post;
import sample.dto.out.AddComment;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;
import sample.util.SuperProps;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Page(resource = "/pages/meme.fxml")
public class MemePage extends SuperPage {

    @FXML
    private VBox memeContainer;
    @FXML
    private Label commentTitle;
    @FXML
    private VBox commentContainer;
    @FXML
    private VBox addCommentCointainer;
    @FXML
    private TextArea comment;

    @FXML
    private void addComment() {

        postsService.addComment(getMemeId(), new AddComment(
                comment.getText()
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                loadComments();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private int getMemeId() {
        return ((Props) props).memeId;
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
                    Pane item = createPostItem(response.body());
                    Platform.runLater(() -> memeContainer.getChildren().setAll(item));
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
                    List<Pane> panes = response.body()
                            .stream()
                            .map(MemePage.this::createCommentItem)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    Platform.runLater(() -> {
                        commentContainer.getChildren().setAll(panes);
                        commentTitle.setVisible(true);
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
        if (State.isActiveAccount()) {
            addCommentCointainer.setDisable(false);
        }
        loadMeme();
        loadComments();
    }

    public Pane createPostItem(Post post) {
        try {

            String path = "/components/postItem.fxml";

            if (State.isAdmin()) {
                path = "/components/postItemAdmin.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();
            PostController controller = loader.getController();
            controller.load(post);
            controller.setRouter(router);
            VBox.setMargin(pane, new Insets(50, 0, 50, 0));
            return pane;
        } catch (IOException e) {
            AlertsFactory.unknownError(e.getMessage());
            return null;
        }
    }

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int memeId;
    }
}
