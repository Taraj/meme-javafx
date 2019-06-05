package sample.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmResetPassword {
    private String usernameOrEmail;
    private String code;
}
