package sample.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddPost {
    private String title;
    private String url;
    private List<String> tags;
}
