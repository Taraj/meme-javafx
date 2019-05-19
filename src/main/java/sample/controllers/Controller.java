package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sample.controllers.pages.*;
import sample.util.Page;
import sample.util.SuperPage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Pane mainContainer;

    @FXML
    private VBox mainMenu;

    private List<Class<?>> pages = new ArrayList<>();

    private Class<?> initPage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pages.add(MainPage.class);
        pages.add(QueuePage.class);
        pages.add(RandomPage.class);
        if (false) {
            pages.add(LoginPage.class);
        } else {
            pages.add(AddPage.class);
            pages.add(AccountPage.class);
            pages.add(LogoutPage.class);
        }


        initPage = MainPage.class;

        initializeMenu();
        openInitPage();
    }

    private void openInitPage() {
        loadNewPage(initPage);
    }

    private void buttonClicked(ActionEvent event) {
        Button source = ((Button) event.getSource());
        Class<?> clazz = (Class<?>) source.getUserData();
        loadNewPage(clazz);
    }


    private void loadNewPage(Class<?> clazz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(clazz.getAnnotation(Page.class).resource()));
            Pane pane = loader.load();
            SuperPage controller = loader.getController();
            controller.setRouter(this::loadNewPage);
            mainContainer.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMenu() {
        for (Class<?> page : pages) {
            createMenuButton(page.getAnnotation(Page.class).name(), page);
        }
    }

    private void createMenuButton(String text, Class<?> page) {
        Button button = new Button(text);
        VBox.setVgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setUserData(page);
        VBox.setMargin(button, new Insets(0, 0, 10, 0));
        button.setOnAction(this::buttonClicked);
        mainMenu.getChildren().add(button);
    }
}
