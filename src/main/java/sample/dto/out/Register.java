package sample.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Register {
    private String nickname;
    private String username;
    private String email;
    private String password;
}
