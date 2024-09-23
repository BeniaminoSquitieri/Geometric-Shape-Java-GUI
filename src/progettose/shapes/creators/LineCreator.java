package progettose.shapes.creators;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import progettose.shapes.wrappers.LineShape;
import progettose.shapes.wrappers.ShapeInterface;

/**
 *
 * Class to manage the creation of a LineShape
 *
 */
public class LineCreator extends ShapeCreator {

    /**
     * This method is used to create an ellipse Shape.
     *
     * @param type This is the first parameter that represents the category of
     * the ShapeInterface that needs to be created
     * @param clickX This is the second parameter that represents the starting X
     * coordinate of the line
     * @param clickY This is the third parameter that represents the starting Y
     * coordinate of the line
     * @param interiorColor This is the fourth parameter that represents the
     * interior colour of the line
     * @param borderColor This is the fifth parameter that represents the border
     * colour of the line
     * @param endX This is the sixth parameter that represents the ending X
     * coordinate of the line
     * @param endY This is the sixth parameter that represents the ending Y
     * coordinate of the line
     * @param z This is the eight parameter that represent the Z coordinate of
     * the shape 
     * In this method we create a LineShape of standard stroke
     *
     * @return ShapeInterface This return the LineShape created
     */
    @Override
    public ShapeInterface createShape(String type, double clickX, double clickY, Color interiorColor, Color borderColor, double endX, double endY, int z) {
        double temp;

        Line shape = new Line(clickX, clickY, endX, endY);

        shape.setStrokeWidth(5);
        shape.setStroke(borderColor);
        LineShape ls = new LineShape(shape, clickX, clickY, z);
        return ls;
    }

}
