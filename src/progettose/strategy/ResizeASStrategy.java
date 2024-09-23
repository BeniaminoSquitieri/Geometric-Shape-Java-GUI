package progettose.strategy;

/**
 * Strategy for the new x,y coordinates of the shape to be resized starting from the vertex in the right left corner
 */
import javafx.scene.Cursor;

public class ResizeASStrategy implements ResizeStrategy {

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
        return 3;
    }

    @Override
    public void setCoordinates(double minx, double maxx, double miny, double maxy, double eventx, double eventy) {
        this.minlinex = eventx;
        this.minliney = eventy;
        this.maxlinex = maxx;
        this.maxliney = maxy;
        if (eventx > maxx) {
            minx = maxx;
            maxx = eventx;

        } else {
            minx = eventx;
        }
        if (eventy > maxy) {
            miny = maxy;
            maxy = eventy;

        } else {
            miny = eventy;
        }
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;

    }

    @Override
    public double getMinX() {
        return minx;
    }

    @Override
    public double getMaxX() {
        return maxx;
    }

    @Override
    public double getMinY() {
        return miny;
    }

    @Override
    public double getMaxY() {
        return maxy;
    }

    @Override
    public double getLineMinX() {
        return minlinex;
    }

    @Override
    public double getLineMaxX() {
        return maxlinex;
    }

    @Override
    public double getLineMinY() {
        return minliney;
    }

    @Override
    public double getLineMaxY() {
        return maxliney;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.NW_RESIZE;
    }
}
