package sample.util;


import java.util.function.BiConsumer;


public abstract class SuperPage {

    protected BiConsumer<Class<? extends SuperPage>, Object> router;

    protected Object data;

    public void setRouter(BiConsumer<Class<? extends SuperPage>, Object> router) {
        this.router = router;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void init(){}
}
