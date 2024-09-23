package progettose.shapes.creators;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import progettose.shapes.wrappers.RectangleShape;
import progettose.shapes.wrappers.ShapeInterface;

/**
 *
 * Class to manage the creation of an RectangleShape
 *
 */
public class RectangleCreator extends ShapeCreator {

    /**
     * This method is used to create a rectangle Shape.
     *
     * @param type This is the first parameter that represents the category of
     * the ShapeInterface that needs to be created
     * @param clickX This is the first parameter that represents the X
     * coordinate of the starting of the rectangle
     * @param clickY This is the second parameter that represents the Y
     * coordinate of the starting of the rectangle
     * @param interiorColor This is the third parameter that represents the
     * interior colour of the rectangle
     * @param borderColor This is the fourth parameter that represents the
     * border colour of the rectangle
     * @param endX This is the fifth parameter that represents the ending X
     * coordinate of the rectangle
     * @param endY This is the sixth parameter that represents the ending Y
     * coordinate of the rectangle
     * @param z This is the eight parameter that represent the Z coordinate of
     * the shape 
     * In this method we create a RectangleShape of standard stroke
     *
     * @return ShapeInterface This return the RectangleShape created
     */
    @Override
    public ShapeInterface createShape(String type, double clickX, double clickY, Color interiorColor, Color borderColor, double endX, double endY, int z) {
        Rectangle shape = new Rectangle();
        shape.setFill(interiorColor);
        shape.setStroke(borderColor);
        shape.setStrokeWidth(5);
        shape.setWidth(Math.abs(endX - clickX));
        shape.setHeight(Math.abs(endY - clickY));
        //shape.setX(clickX - width / 2);
        //shape.setY(clickY - height / 2);
        if (clickX < endX) {
            shape.setX(clickX);
        } else {
            shape.setX(endX);
        }
        if (clickY < endY) {
            shape.setY(clickY);
        } else {
            shape.setY(endY);
        }
        RectangleShape ls = new RectangleShape(shape, clickX, clickY, z);
        return ls;
    }

}
