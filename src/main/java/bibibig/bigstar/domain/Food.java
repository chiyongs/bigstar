package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("foodings")
@Getter @Setter
public class Food {

    @Id
    private String id;

    private List<Fooding> foodings = new ArrayList<>();
}
