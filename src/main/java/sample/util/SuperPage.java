package sample.util;


import java.util.function.Consumer;

public abstract class SuperPage {

    protected Consumer<Class<?>> router;

    public void setRouter(Consumer<Class<?>> router) {
        this.router = router;
    }


}
