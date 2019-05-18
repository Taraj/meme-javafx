package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ban {
    private long id;
    private LocalDateTime expireAt;
    private String reason;
    private User target;
    private User invoker;
    private LocalDateTime createdAt;
}
