package sample.dto.in;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private boolean isAdmin;
    private boolean isActive;
    private String nickname;
    private String tokenType;
}
