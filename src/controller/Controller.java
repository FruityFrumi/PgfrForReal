package controller;

import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import graphics.rasterizer.PolygonRasterizer;
import graphics.rasterizer.TrivialLineRasterizer;
import model.Line;
import model.Point;
import model.Polygon;
import view.Canvas;

import javax.swing.*;
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
    private Line previewLine;

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
        this.polygon = new Polygon(lines);

        this.PolygonOutLineRasterizer = new PolygonOutlineRasterizer(rasterizer);

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public void init() {
        canvas.clear();
        // Obsluha vstupu z myši
        PolygonOutLineRasterizer.rasterize(new Polygon(lines));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Pokud polygon ještě není dokončený, zahájit kreslení úsečky
                    if (!polygon.isFinished()) {
                        starLine(e);
                    }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // 1. Ověřit, zda existuje previewLine.
                if (previewLine != null) {
                    Point endPoint = getPoint(e);
                    Line newLine = new Line(previewLine.getPoint1(), endPoint, LINE_COLOR);
                    polygon.add(newLine);
                    previewLine = null;


                    render();
                }
            }
        });

        // Obsluha vstupu z klávesnice
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (previewLine != null) {
                    previewLine = new Line(previewLine.getPoint1(), getPoint(e), PREVIEW_COLOR);
                    render();
                }
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Při stisknutí klávesy C nebo c vymazat polygon.
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    polygon.clear();
                    previewLine = null;
                    polygon.setFinished(false);
                    render();
                }
            }
        });
        canvas.repaint();



    }



    private void render() {
        canvas.clear();
        // Vykreslení dosud vytvořených úseček polygonu (uzavírací se vykreslí jen u dokončeného)
        PolygonOutLineRasterizer.rasterize(polygon);
        // Vykreslení náhledu rozpracované úsečky
        if (previewLine != null) {
            rasterizer.rasterize(previewLine);
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
        if (polygon.isFinished()) return;

        Point p = getPoint(e);
        Point start;

        List<Line> polygonLines = polygon.getLines();
        if (polygonLines != null && !polygonLines.isEmpty()) {
            start = polygonLines.get(polygonLines.size() - 1).getPoint2();
        } else {
            start = p;
        }

        previewLine = new Line(start, p, PREVIEW_COLOR);
        render();
    }
    private void finishPolygon (MouseEvent e) {
        List<Line> polygonLines = polygon.getLines();
        if (polygonLines != null && polygonLines.size() >= 3) {
            polygon.setFinished(true);
        }
        previewLine = null;
        render();
    }

}