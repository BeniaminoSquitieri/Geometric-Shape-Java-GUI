package progettose.strategy;
/**
 * Context for setting the strategy associated at the resize command, it calls the real strategy object to get the algorithm calculation
 */
import javafx.scene.Cursor;

public class ResizeContext {

    private ResizeStrategy strategy;

    public void setStrategy(ResizeStrategy strategy) {
        this.strategy = strategy;
    }

    public int getExclusivity() {
        return strategy.getExclusivity();
    }

    public Cursor getCursor() {
        return strategy.getCursor();
    }

    public void setContext(double minx, double maxx, double miny, double maxy, double eventx, double eventy) {
        strategy.setCoordinates(minx, maxx, miny, maxy, eventx, eventy);
    }

    public double getMinX() {
        return strategy.getMinX();
    }

    public double getMaxX() {
        return strategy.getMaxX();
    }

    public double getMinY() {
        return strategy.getMinY();
    }

    public double getMaxY() {
        return strategy.getMaxY();
    }

    public double getLineMinX() {
        return strategy.getLineMinX();
    }

    public double getLineMaxX() {
        return strategy.getLineMaxX();
    }

    public double getLineMinY() {
        return strategy.getLineMinY();
    }

    public double getLineMaxY() {
        return strategy.getLineMaxY();
    }
}
