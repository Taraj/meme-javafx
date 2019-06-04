package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private String content;
    private Post post;
    private User author;
    private LocalDateTime createdAt;
    private long likes;
    private long dislikes;
}
