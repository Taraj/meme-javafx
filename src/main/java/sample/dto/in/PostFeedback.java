package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostFeedback {
    private long id;
    private String ip;
    private boolean isPositive;
    private LocalDateTime createdAt;
}
