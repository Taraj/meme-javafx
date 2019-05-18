package sample.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetAvatar {
    private String avatarURL;
}
