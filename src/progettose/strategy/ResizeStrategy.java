package progettose.strategy;

import javafx.scene.Cursor;

public interface ResizeStrategy {

    public int getExclusivity();

    public void setCoordinates(double minx, double maxx, double miny, double maxy, double eventx, double eventy);

    public double getMinX();

    public double getMaxX();

    public double getMinY();

    public double getMaxY();

    public double getLineMinX();

    public double getLineMaxX();

    public double getLineMinY();

    public double getLineMaxY();

    public Cursor getCursor();
}
