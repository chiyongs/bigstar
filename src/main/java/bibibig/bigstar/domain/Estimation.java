package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document("FoodAnalytics")
public class Estimation {

    @Id
    private String id;

    private String food_name;
    private List<EstGrade> positive = new ArrayList<>();
    private List<EstGrade> nagative = new ArrayList<>();

}
