package sample.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Warn {
    private long id;
    private String reason;
    private User target;
    private User invoker;
    private LocalDateTime createdAt;
}
