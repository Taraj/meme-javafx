package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFeedback {
    private long id;
    private User author;
    private boolean isPositive;
    private LocalDateTime createdAt;
}
