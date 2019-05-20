package sample.util;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.controllers.components.PostController;
import sample.dto.in.Post;

import java.io.IOException;

public class ComponentFactory {
/*
    public static Pane createPane(Post post, ) {
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
    }*/
}
