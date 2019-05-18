package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String nickname;
    private String avatar;
    private boolean isBaned;
    private LocalDateTime joinedAt;
    private int commentsCount;
    private int postsCount;
    private int likes;
    private int dislikes;
}
