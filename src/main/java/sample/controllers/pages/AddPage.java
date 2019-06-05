package sample.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.State;
import sample.dto.out.AddPost;
import sample.util.AlertsFactory;
import sample.util.Page;
import sample.util.SuperPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Page(name = "Dodaj", resource = "/pages/add.fxml")
public class AddPage extends SuperPage {

    @FXML
    private TextField url;

    @FXML
    private TextField title;

    @FXML
    private TextField tag1;

    @FXML
    private TextField tag2;

    @FXML
    private TextField tag3;

    @FXML
    private void add() {
        postsService.addPost(new AddPost(
                title.getText(),
                url.getText(),
                getTagList()
        ), State.getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    AlertsFactory.responseStatusError(response.errorBody());
                    return;
                }
                AlertsFactory.success("Dodano.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                AlertsFactory.apiCallError(throwable);
            }
        });
    }

    private List<String> getTagList() {
        return Stream.of(tag1.getText(), tag2.getText(), tag3.getText())
                .filter(s -> !"".equals(s))
                .collect(Collectors.toList());
    }
}
