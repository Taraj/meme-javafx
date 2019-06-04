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
    private long commentsCount;
    private long likes;
    private long dislikes;
    private LocalDateTime createdAt;
}
