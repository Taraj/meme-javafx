package sample.util;


import lombok.Setter;

import java.util.function.BiConsumer;


public abstract class SuperPage {

    @Setter
    protected BiConsumer<Class<? extends SuperPage>, SuperProps> router;

    @Setter
    protected SuperProps superProps;

    public void init(){}
}
