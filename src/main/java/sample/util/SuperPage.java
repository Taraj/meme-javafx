package sample.util;


import java.util.function.Consumer;

public abstract class SuperPage {

    protected Consumer<Class<? extends SuperPage>> router;

    public void setRouter(Consumer<Class<? extends SuperPage>> router) {
        this.router = router;
    }


}
