package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BubbleElementsDTO {

    private int x;
    private int y;
    private int r;

    public BubbleElementsDTO(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public String toString() {
        return "{" +
                "x:" + x +
                ", y:" + y +
                ", r:" + r +
                '}';
    }
}
