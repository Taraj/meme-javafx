package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.in.Post;
import sample.dto.in.User;
import sample.dto.out.AddFeedback;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;
import sample.util.SuperProps;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Page(resource = "/pages/user.fxml")
public class UserPage extends SuperPage {

    @FXML
    ImageView avatar;
    @FXML
    Label feedback;
    @FXML
    Label postCount;
    @FXML
    Label commentCount;
    @FXML
    Label registerDate;
    @FXML
    Label nickname;
    @FXML
    HBox feedbackAction;
    @FXML
    private VBox mainContainer;
    private User user;

    @FXML
    private void openNextPage() {
        router.accept(UserPage.class, new Props(getPageNumber() + 1, getNickname()));
    }

    @FXML
    private void openPreviousPage() {
        router.accept(UserPage.class, new Props(Math.max(getPageNumber() - 1, 1), getNickname()));
    }

    @FXML
    private void like() {
        userService.addFeedback(getNickname(), new AddFeedback(
                true
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                setUserInfo();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @FXML
    private void dislike() {
        userService.addFeedback(getNickname(), new AddFeedback(
                false
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                setUserInfo();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private int getPageNumber() {
        return ((Props) props).pageNumber;
    }

    private String getNickname() {
        return ((Props) props).nickname;
    }

    private int getOffset() {
        return (getPageNumber() - 1) * 10;
    }

    private void setUserInfo() {
        userService.getUsersByNickname(getNickname()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                if (response.body() != null) {
                    user = response.body();
                    Platform.runLater(UserPage.this::setData);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private void setData() {
        avatar.setImage(new Image(user.getAvatar()));
        nickname.setText(user.getNickname());
        postCount.setText(Long.toString(user.getPostsCount()));
        commentCount.setText(Long.toString(user.getCommentsCount()));
        String feedbackString = "+" + user.getLikes() + "/-" + user.getDislikes();
        feedback.setText(feedbackString);
        registerDate.setText(user.getCreatedAt().toString());
    }

    private void setUserPosts() {
        userService.getPosts(getNickname(), getOffset()).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }

                if (response.body() != null) {
                    List<Pane> panes = response.body()
                            .parallelStream()
                            .map(UserPage.this::createPostItem)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    Platform.runLater(() -> mainContainer.getChildren().setAll(panes));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    @Override
    public void init() {
        setUserPosts();
        setUserInfo();
        if (State.isActiveAccount()) {
            feedbackAction.setDisable(false);
        }
    }

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int pageNumber;
        private String nickname;
    }

}
