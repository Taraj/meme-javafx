package sample.dto.in;

import lombok.Data;

@Data
public class Tag {
    private long id;
    private String name;
    private int postsCount;
}
