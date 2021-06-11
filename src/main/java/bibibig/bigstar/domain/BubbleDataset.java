package bibibig.bigstar.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BubbleDataset {

    private String label;
    private String backgroundColor;
    private String borderColor;
    private List<BubbleElementsDTO> data;

    @Override
    public String toString() {
        return
                "{label:'" + label + '\'' +
                ", backgroundColor:'" + backgroundColor + '\'' +
                ", borderColor:'" + borderColor + '\'' +
                ", data:" + data +
                '}';
    }
}
