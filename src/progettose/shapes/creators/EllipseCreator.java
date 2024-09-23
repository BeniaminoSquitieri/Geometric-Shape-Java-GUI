package progettose.shapes.creators;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.ShapeInterface;

/**
 *
 * Class to manage the creation of an EllipseShape
 *
 *
 */
public class EllipseCreator extends ShapeCreator {

    /**
     * This method is used to create an ellipse Shape.
     *
     * @param type This is the first parameter that represents the category of
     * the ShapeInterface that needs to be created
     * @param clickX This is the second parameter that represents the starting X
     * coordinate of the boundingbox of the ellipse
     * @param clickY This is the third parameter that represents the starting Y
     * coordinate of the boundingbox of the ellipse
     * @param interiorColor This is the fourth parameter that represents the
     * interior colour of the ellipse
     * @param borderColor This is the fifth parameter that represents the border
     * colour of the ellipse
     * @param endX This is the sixth parameter that represents the ending X
     * coordinate of the boundingbox of the ellipse
     * @param endY This is the seventh parameter that represents the ending Y
     * coordinate of the boundingbox of the ellipse
     * @param z This is the eight parameter that represent the Z coordinate of
     * the shape 
     * In this method we create an EllipseShape of standard stroke
     *
     * @return ShapeInterface This return the EllipseShape created
     */
    @Override
    public ShapeInterface createShape(String type, double clickX, double clickY, Color interiorColor, Color borderColor, double endX, double endY, int z) {
        Ellipse shape = new Ellipse();
        shape.setFill(interiorColor);
        shape.setStroke(borderColor);
        shape.setStrokeWidth(5);
        shape.setRadiusX(Math.abs(clickX - endX) / 2);
        shape.setRadiusY(Math.abs(clickY - endY) / 2);
        shape.setCenterX((clickX + endX) / 2);
        shape.setCenterY((clickY + endY) / 2);
        EllipseShape ls = new EllipseShape(shape, clickX, clickY, z);
        return ls;
    }

}
