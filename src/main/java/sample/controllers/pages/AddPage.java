package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.out.AddPost;
import sample.services.PostsService;
import sample.services.RetrofitInstance;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

import java.util.ArrayList;
import java.util.List;


@Page(name = "Dodaj", resource = "/pages/add.fxml")
public class AddPage extends SuperPage {


    @FXML
    private TextField urlField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField tag1Field;

    @FXML
    private TextField tag2Field;

    @FXML
    private TextField tag3Field;

    private PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    @FXML
    private void add() {
        List<String> tags = new ArrayList<>();

        tags.add(tag1Field.getText());
        tags.add(tag2Field.getText());
        tags.add(tag3Field.getText());

        tags.removeIf(""::equals);
        AddPost newPost = AddPost.builder()
                .title(titleField.getText())
                .url(urlField.getText())
                .tags(tags)
                .build();
        postsService.addPost(newPost, State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Dodano");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });

    }
}
