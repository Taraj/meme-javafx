package sample.dto.out;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddPost {
    private String title;
    private String url;
    private List<String> tags;
}
