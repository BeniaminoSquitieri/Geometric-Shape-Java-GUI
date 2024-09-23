package progettose.strategy;
/**
 * Strategy for the new x,y coordinates of the shape to be resized starting from the vertex in the left bottom corner
 */
import javafx.scene.Cursor;

public class ResizeBSStrategy implements ResizeStrategy {

    private double minx;
    private double miny;
    private double maxx;
    private double maxy;
    private double minlinex;
    private double minliney;
    private double maxlinex;
    private double maxliney;

    @Override
    public int getExclusivity() {
        return 2;
    }

    @Override
    public void setCoordinates(double minx, double maxx, double miny, double maxy, double eventx, double eventy) {
        this.minlinex = eventx;
        this.minliney = eventy;
        this.maxlinex = maxx;
        this.maxliney = miny;

        if (eventx > maxx) {
            minx = maxx;
            maxx = eventx;

        } else {
            minx = eventx;
        }
        if (eventy < miny) {
            maxy = miny;
            miny = eventy;

        } else {
            maxy = eventy;
        }
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;
    }

    @Override
    public double getMinX() {
        return this.minx;
    }

    @Override
    public double getMaxX() {
        return this.maxx;
    }

    @Override
    public double getMinY() {
        return this.miny;
    }

    @Override
    public double getMaxY() {
        return this.maxy;
    }

    @Override
    public double getLineMinX() {
        return this.minlinex;
    }

    @Override
    public double getLineMaxX() {
        return this.maxlinex;
    }

    @Override
    public double getLineMinY() {
        return this.minliney;
    }

    @Override
    public double getLineMaxY() {
        return this.maxliney;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.SW_RESIZE;
    }

}
