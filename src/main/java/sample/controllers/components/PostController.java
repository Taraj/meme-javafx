package sample.controllers.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.dto.in.Post;
import sample.dto.in.Tag;
import sample.dto.out.AddFeedback;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.SuperPage;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


public class PostController {


    @FXML
    private Label title;

    @FXML
    private Label author;

    @FXML
    private Label date;

    @FXML
    private ImageView image;

    @FXML
    private Button likeButton;

    @FXML
    private Button dislikeButton;


    private Post post;

    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private void like() {
        addFeedback(true);
    }

    @FXML
    private void dislike() {
        addFeedback(false);
    }

    @FXML
    private HBox tagContainer;

    private BiConsumer<Class<? extends SuperPage>, Object> router;

    public void setRouter(BiConsumer<Class<? extends SuperPage>, Object> router) {
        this.router = router;
    }


    private void addFeedback(boolean isLike) {
        AddFeedback addFeedbackDto = AddFeedback.builder()
                .like(isLike)
                .build();
        postsService.addFeedback(post.getId(), addFeedbackDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                reLoadPost();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private void reLoadPost() {
        postsService.getPostById(post.getId()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    post = response.body();
                    Platform.runLater(PostController.this::setPostData);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private void setPostData() {
        title.setText(post.getTitle());
        author.setText(post.getAuthor().getNickname());
        Image img = new Image(post.getUrl());
        date.setText(post.getCreatedAt().toString());
        likeButton.setText("+" + post.getLikes());
        dislikeButton.setText("-" + post.getDislikes());
        image.setImage(img);
        setTags(post.getTags());
        image.setFitHeight(img.getHeight() * 2);
    }

    private void setTags(List<Tag> tags) {
        List<Label> tagList = tags.stream().map(this::addTag)
                .collect(Collectors.toList());
        Platform.runLater(() -> tagContainer.getChildren().setAll(tagList));
    }

    private Label addTag(Tag tag) {
        Label label = new Label();
        label.setText("#" + tag.getName());
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        label.setFont(Font.font(15));
        label.setUserData(tag);
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(this::openTag);
        return label;
    }

    public void load(Post post) {
        this.post = post;
        setPostData();
    }


    @FXML
    private void openMeme() {
        System.out.println("Open meme" + post.getId());
    }

    @FXML
    private void openUser() {
        System.out.println("Open user" + post.getAuthor().getNickname());
    }

    private void openTag(MouseEvent event) {
        Label label = (Label) event.getSource();
        Tag tag = (Tag) label.getUserData();
        System.out.println("Open Tag" + tag.getName());
    }
}
