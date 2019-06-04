package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private long id;
    private String nickname;
    private String avatar;
    private LocalDateTime createdAt;
    private long commentsCount;
    private long postsCount;
    private long likes;
    private long dislikes;
}
