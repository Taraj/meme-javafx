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
import sample.controllers.components.PostController;
import sample.dto.in.Post;
import sample.services.RetrofitInstance;
import sample.services.TagService;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperProps;
import sample.util.SuperPage;

import java.io.IOException;
import java.util.List;

@Page(resource = "/pages/tag.fxml")
public class TagPage extends SuperPage {

    @AllArgsConstructor
    public static class Props implements SuperProps {
        private int pageNumber;
        private String tag;
    }


    private TagService tagService = RetrofitInstance.getInstance().create(TagService.class);

    @FXML
    private VBox mainContainer;

    @FXML
    private void openNextPage() {
        router.accept(TagPage.class, new TagPage.Props(getPageNumber() + 1, getTagName()));
    }

    @FXML
    private void openPreviousPage() {
        router.accept(TagPage.class, new TagPage.Props(Math.max(getPageNumber() - 1, 1), getTagName()));
    }

    private int getPageNumber() {
        return ((Props) superProps).pageNumber;
    }

    private String getTagName() {
        return ((Props) superProps).tag;
    }

    private int getOffset() {
        return (getPageNumber() - 1) * 10;
    }

    @Override
    public void init() {
        tagService.getPosts(getTagName(), getOffset()).enqueue(new Callback<List<Post>>() {
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
