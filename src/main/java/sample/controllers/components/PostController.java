package sample.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PostController {


    @FXML
    private Label title;


    @FXML
    private Label author;


    @FXML
    private ImageView image;

    public void load(String titleString, String authorString, String imageString){
        title.setText(titleString);
        author.setText(authorString);
        image.setImage(new Image(imageString));
    }
}
