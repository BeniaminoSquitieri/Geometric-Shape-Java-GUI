package progettose.shapes.creators;

import javafx.scene.paint.Color;
import progettose.shapes.wrappers.ShapeInterface;

/**
 *
 * Class to manage the creation of a generic Shape
 *
 */
public class ShapeCreator {

    public ShapeInterface createShape(String type, double clickX, double clickY, Color interiorColor, Color borderColor, double endX, double endY, int z) {
        switch (type) {
            case "rectangle":
                return (new RectangleCreator()).createShape(type, clickX, clickY, interiorColor, borderColor, endX, endY, z);
            case "line":
                return (new LineCreator()).createShape(type, clickX, clickY, interiorColor, borderColor, endX, endY, z);
            case "ellipse":
                return (new EllipseCreator()).createShape(type, clickX, clickY, interiorColor, borderColor, endX, endY, z);
            default:
                return (new TextCreator()).createShape(type.substring(0, type.length() - 1), clickX, clickY, interiorColor, borderColor, endX, endY, z);
        }
    }
}
