package sample.util;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.State;
import sample.controllers.components.CommentController;
import sample.controllers.components.PostController;
import sample.dto.in.Comment;
import sample.dto.in.Post;
import java.io.IOException;


public abstract class SuperPage extends SuperComponent{

    public Pane createPostItem(Post post){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/postItem.fxml"));
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




    public Pane createCommentItem(Comment comment){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/commentItem.fxml"));
            Pane pane = loader.load();
            CommentController controller = loader.getController();
            controller.load(comment);
            controller.setRouter(router);
            VBox.setMargin(pane, new Insets(5, 0, 0, 0));
            return pane;
        } catch (IOException e) {
            AlertsFactory.unknownError(e.getMessage());
            return null;
        }
    }

}
