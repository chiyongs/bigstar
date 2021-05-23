package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("artists")
@Getter @Setter
public class Artists {

    @Id
    private String id;

    private String artistname;
}
