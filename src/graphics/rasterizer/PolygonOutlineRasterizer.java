package graphics.rasterizer;

import model.Line;
import model.Polygon;

import java.util.List;

public class PolygonOutlineRasterizer implements PolygonRasterizer {

    private final LineRasterizer lineRasterizer;

    public PolygonOutlineRasterizer(LineRasterizer TriviallineRasterizer) {
        this.lineRasterizer = TriviallineRasterizer;
    }


    public void  rasterize(Polygon polygon) {
        List<Line> lines = polygon.getLines();
        rasterize(lines);

        if (lines.size() > 1) {
            Line last = lines.getLast();
            Line first = lines.getFirst();
            lineRasterizer.rasterize(new Line(last.getPoint2(),first.getPoint1(), last.getColor()));
        }
    }

    private void rasterize(List<Line> lines) {
        for (Line line : lines) {
            lineRasterizer.rasterize(line);
        }
    }
}
