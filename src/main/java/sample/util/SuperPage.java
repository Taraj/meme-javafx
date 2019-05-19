package sample.util;

import javafx.event.ActionEvent;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class SuperPage {


    protected Consumer<Class<?>> router;

    public void setRouter(Consumer<Class<?>> router) {
        this.router = router;
    }


}
