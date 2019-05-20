package sample.dto.in;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private boolean admin;
    private boolean active;
    private String nickname;
    private String tokenType;
}
