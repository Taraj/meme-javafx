package sample.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetAvatar {
    private String avatarURL;
}
