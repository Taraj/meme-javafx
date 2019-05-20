package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.controllers.components.PostController;
import sample.dto.in.Post;
import sample.dto.in.User;
import sample.services.RetrofitInstance;
import sample.services.UserService;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;
import sample.util.SuperProps;


import java.io.IOException;
import java.util.List;

@Page(resource = "/pages/user.fxml")
public class UserPage extends SuperPage {
    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int pageNumber;
        private String nickname;
    }

    private UserService userService = RetrofitInstance.getInstance().create(UserService.class);

    @FXML
    private VBox mainContainer;

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
    private void openNextPage() {
        router.accept(UserPage.class, new Props(getPageNumber() + 1, getNickname()));
    }

    @FXML
    private void openPreviousPage() {
        router.accept(UserPage.class, new Props(Math.max(getPageNumber() - 1, 1), getNickname()));
    }

    private int getPageNumber() {
        return ((Props) superProps).pageNumber;
    }

    private String getNickname() {
        return ((Props) superProps).nickname;
    }

    private int getOffset() {
        return (getPageNumber() - 1) * 10;
    }

    private User user;


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
        postCount.setText(Integer.toString(user.getPostsCount()));
        commentCount.setText(Integer.toString(user.getCommentsCount()));
        String feedbackString = "+"+ user.getLikes() + "/-" + user.getDislikes();
        feedback.setText(feedbackString);
        registerDate.setText(user.getJoinedAt().toString());
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
                    response.body().forEach(post -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/postItem.fxml"));
                            Pane pane = loader.load();
                            PostController controller = loader.getController();
                            controller.load(post);
                            controller.setRouter(router);
                            VBox.setMargin(pane, new Insets(50, 0, 50, 0));
                            Platform.runLater(() -> mainContainer.getChildren().add(pane));
                        } catch (IOException e) {
                            AlertsFactory.unknownError(e.getMessage());
                        }
                    });
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
    }
}
