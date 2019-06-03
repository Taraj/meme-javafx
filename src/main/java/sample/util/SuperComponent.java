package sample.util;

import lombok.Setter;
import sample.services.*;

import java.util.function.BiConsumer;

abstract public class SuperComponent {

    @Setter
    protected BiConsumer<Class<? extends SuperPage>, SuperProps> router;

    @Setter
    protected SuperProps props;

    protected AuthService authService = RetrofitInstance.getInstance().create(AuthService.class);

    protected CommentService commentService = RetrofitInstance.getInstance().create(CommentService.class);

    protected PostsService postsService = RetrofitInstance.getInstance().create(PostsService.class);

    protected SelfService selfService = RetrofitInstance.getInstance().create(SelfService.class);

    protected TagService tagService = RetrofitInstance.getInstance().create(TagService.class);

    protected UserService userService = RetrofitInstance.getInstance().create(UserService.class);

    public void init(){}

}
