package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Line> lines;
    private boolean isFinished = false;

    public Polygon() {
        this.lines = new ArrayList<>();
    }

    public Polygon(List<Line> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public List<Line> getLines() {
        return lines;
    }

    public void add(Line line) {
        this.lines.add(line);
    }

    public void clear() {
        this.lines.clear();
        this.isFinished = false;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }
}
