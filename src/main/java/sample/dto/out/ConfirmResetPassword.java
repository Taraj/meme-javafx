package sample.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmResetPassword {
    private String usernameOrEmail;
    private int code;
}
