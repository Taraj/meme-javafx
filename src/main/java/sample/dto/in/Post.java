package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class Post {
    private long id;
    private String title;
    private String url;
    private List<Tag> tags;
    private User author;
    private int commentsCount;
    private int likes;
    private int dislikes;
    private LocalDateTime createdAt;
}
