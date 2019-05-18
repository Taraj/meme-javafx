package sample.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Register {
    private String nickname;
    private String username;
    private String email;
    private String password;
}
