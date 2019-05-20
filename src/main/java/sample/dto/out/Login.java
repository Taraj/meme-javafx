package sample.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
    private String username;
    private String password;
}
