package progettose.tool;

import javafx.scene.paint.Color;
import progettose.application.CanvasClass;
import progettose.observers.ColorSubscriberInterface;
/**
 * Class to manage the creation of a generic tool that add a shape on the canvas
 */
public abstract class Tool implements ColorSubscriberInterface {

    private Color interiorColor;
    private Color borderColor;

    public abstract void mouseDown(CanvasClass canvas, double x, double y);

    public abstract void mouseUp(CanvasClass canvas, double x, double y);

    public Color getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public abstract void setSelected(boolean selected);

    @Override
    public void update(String event, Color color) {
        switch (event) {
            case "INTERIOR_COLOR":
                this.setInteriorColor(color);
                break;
            case "BORDER_COLOR":
                this.setBorderColor(color);
                break;
            default:
                throw new AssertionError();
        }
    }
}
