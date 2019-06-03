package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.in.Comment;
import sample.dto.in.Post;
import sample.dto.out.AddComment;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperProps;
import sample.util.SuperPage;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Page(resource = "/pages/meme.fxml")
public class MemePage extends SuperPage {

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int memeId;
    }


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
    private void addComment(){

        postsService.addComment(getMemeId(),new AddComment(
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
        if(State.isActiveAccount()){
            addCommentCointainer.setDisable(false);
        }
        loadMeme();
        loadComments();
    }
}
