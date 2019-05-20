package sample.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import lombok.Setter;
import sample.controllers.pages.UserPage;
import sample.dto.in.Comment;
import sample.util.SuperPage;
import sample.util.SuperProps;

import java.util.function.BiConsumer;

public class CommentController {


    @FXML
    private Label content;

    @FXML
    private Label author;

    @FXML
    private Label date;

    @FXML
    private ImageView avatar;


    private Comment comment;
    @Setter
    private BiConsumer<Class<? extends SuperPage>, SuperProps> router;


    public void load(Comment comment) {
        this.comment = comment;
        setCommentData();
    }

    private void setCommentData(){
       author.setText(comment.getAuthor().getNickname());
       date.setText(comment.getCreatedAt().toString());

       avatar.setImage(new Image(comment.getAuthor().getAvatar()));
       content.setText(comment.getContent());
    }

    @FXML
    private void openUser() {
        router.accept(UserPage.class, new UserPage.Props(1, comment.getAuthor().getNickname()));
    }

}
