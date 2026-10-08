package model;

import java.awt.*;
import java.util.List;

public class Polygon {
    private final List<Line> Lines;

    public Polygon() {
        this(List.of());
    }
    public Polygon(List<Line> Lines) {
        this.Lines = Lines;
    }
    public List<Line> getLines() {
        return Lines;
    }
}


