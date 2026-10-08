package controller;

import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import graphics.rasterizer.PolygonRasterizer;
import graphics.rasterizer.TrivialLineRasterizer;
import model.Line;
import model.Point;
import model.Polygon;
import view.Canvas;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles user input and controls the application flow related to the {@link Canvas}.
 * The controller coordinates input events, canvas operations, and rendering updates.
 *
 * @author PGRF FIM UHK
 * @version 2026
 */
public class Controller {

    private Point startPoint;
    private Point currentPoint;

    private final Canvas canvas;
    private final LineRasterizer rasterizer;
    private final PolygonRasterizer PolygonOutLineRasterizer;

    private final Polygon polygon;
    private final List<Line> lines = new ArrayList<Line>();
    private static final int LINE_COLOR = Color.WHITE.getRGB();
    private static final int PREVIEW_COLOR = Color.RED.getRGB();

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas) {
        this.canvas = canvas;
        this.rasterizer = new TrivialLineRasterizer(canvas.getRaster());
        this.PolygonOutLineRasterizer = new PolygonOutlineRasterizer(rasterizer);

        lines.add(new Line(new Point(100,100),new Point(300, 100), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(300,100),new Point(350, 250), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(350,250),new Point(200, 350), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(200,350),new Point(100, 250), Color.WHITE.getRGB()));
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public void init() {
        canvas.clear();
        // Obsluha vstupu z myši
        PolygonOutLineRasterizer.rasterize(new Polygon(lines));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Todo: mouse cords - when mouse pressed - add new point to polygon
                // when released preview dissapears
                startPoint = getPoint(e);
                currentPoint = startPoint;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //End
                lines.add(new Line(startPoint, getPoint(e), LINE_COLOR));
                currentPoint = null;
                startPoint = null;
                render();
            }
        });

        // Obsluha vstupu z klávesnice
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // update preview of line
                if (startPoint == null) {
                    return;
                }
                currentPoint = getPoint(e);
                render();
            }
        });
        canvas.repaint();
    }

    private void render() {
        canvas.clear();
        for (Line line : lines) {
            rasterizer.rasterize(line);
        }
        if (startPoint != null) {
            rasterizer.rasterize(new Line(startPoint, currentPoint, PREVIEW_COLOR));
        }
        canvas.repaint();
    }

    private Point getPoint(MouseEvent e) {
        return new Point(e.getX(), e.getY());
    }
    private void renderPolygon() {
        canvas.clear();
        PolygonOutLineRasterizer.rasterize(polygon);
        canvas.repaint();
    }
    private void starLine (MouseEvent e) {

    }
    private void finishPolygon (MouseEvent e) {

    }

}