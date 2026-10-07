package graphics.rasterizer;

import graphics.Raster;
import model.Line;
//hgr
public class TrivialLineRasterizer implements LineRasterizer {

    private final Raster raster;

    public TrivialLineRasterizer(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {
        rasterize(
                line.getPoint1().getX(),
                line.getPoint1().getY(),
                line.getPoint2().getX(),
                line.getPoint2().getY(),
                line.getColor()
        );
    }

    private void rasterize(int x1, int y1, int x2, int y2, int color) {
        // TODO: Dokončit implementaci
        if (x2 == x1)
        {
            if (y1 > y2){
                for (int y = y2; y <= y1; y++){
                    raster.setPixel(x1,y,color);
                }
            }
            else for (int y = y1; y <= y2; y++){
                raster.setPixel(x1,y,color);
            }
        }
        else if (Math.abs(x2 - x1) >= Math.abs(y2 - y1))
        {
            if (x1 > x2)
                drawLineDomX(x2,x1,y2,y1,color);

            else
                drawLineDomX(x1,x2,y1,y2,color);

        }
        else {
            if (y1 > y2) drawLineDomY(x2,x1,y2,y1,color);

                else drawLineDomY(x1,x2,y1,y2,color);
            }
    }
    // smaller X cords first
    private void drawLineDomX(int x1, int x2, int y1, int y2,int color){
        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;
        for (int x = x1; x <= x2; x++) {
            float y = k * x + q;
            raster.setPixel(x, Math.round(y), color);
        }
    }
    // smaller y cords first
    private void drawLineDomY (int x1, int x2, int y1, int y2,int color){
        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;
        for (int y = y1; y <= y2; y++){
            float x = (y-q)/k;
            raster.setPixel(Math.round(x),y, color);
        }
    }
}
