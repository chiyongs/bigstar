package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Fooding {

    @Id
    private String id;

    private String food_name;
    private String content;
    private LocalDate date;
    private int like;
    private String place;
    private List<String> tags = new ArrayList<>();
}
