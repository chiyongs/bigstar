package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class LikesByDate {

    private String food_name;
    private String date;
    private int totalLikes;
}
