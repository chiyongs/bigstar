package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class LikesByDate {

    private String foodName;
    private LocalDate date;
    private int totalLikes;
}
