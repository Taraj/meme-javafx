package sample.controllers.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import sample.util.SuperProps;

import java.io.IOException;
import java.util.List;


@Page(name = "Głowna", resource = "/pages/main.fxml")
public class MainPage extends SuperPage {

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int pageNumber;
    }


    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private VBox mainContainer;

    @FXML
    private void openNextPage() {
        router.accept(MainPage.class, new Props(getPageNumber() + 1));
    }

    @FXML
    private void openPreviousPage() {
        router.accept(MainPage.class, new Props(Math.max(getPageNumber() - 1, 1)));
    }

    private int getPageNumber() {
        if (superProps == null) {
            return 1;
        }
        return ((Props) superProps).pageNumber;

    }

    private int getOffset() {
        return (getPageNumber() - 1) * 10;
    }

    @Override
    public void init() {
        postsService.getPosts(getOffset(), true).enqueue(new Callback<List<Post>>() {
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
}
