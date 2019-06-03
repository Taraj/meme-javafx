package sample.controllers.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.controllers.pages.UserPage;
import sample.dto.in.Comment;
import sample.dto.out.AddFeedback;
import sample.util.AlertsFactory;
import sample.util.SuperComponent;

public class CommentController extends SuperComponent {

    @FXML
    private Label content;

    @FXML
    private Label author;

    @FXML
    private Label date;

    @FXML
    private ImageView avatar;

    @FXML
    private Button like;

    @FXML
    private Button dislike;

    private Comment comment;


    @FXML
    private HBox feedbackContainer;

    public void load(Comment comment) {
        if(State.isActiveAccount()){
            feedbackContainer.setDisable(false);
        }

        this.comment = comment;
        setCommentData();
    }

    private void setCommentData() {
        author.setText(comment.getAuthor().getNickname());
        date.setText(comment.getCreatedAt().toString());
        like.setText("+" + comment.getLikes());
        dislike.setText("-" + comment.getDislikes());
        avatar.setImage(new Image(comment.getAuthor().getAvatar()));
        content.setText(comment.getContent());
    }

    @FXML
    private void openUser() {
        router.accept(UserPage.class, new UserPage.Props(1, comment.getAuthor().getNickname()));
    }

    private void reLoadComment(){
        commentService.getCommentById(comment.getId()).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                if (response.body() != null) {
                    comment = response.body();
                    Platform.runLater(CommentController.this::setCommentData);
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void addLike() {
        commentService.addFeedback(comment.getId(), new AddFeedback(
                true
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                reLoadComment();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void addDislike() {
        commentService.addFeedback(comment.getId(), new AddFeedback(
                true
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                reLoadComment();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }
}
