package progettose.shapes.creators;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import progettose.shapes.wrappers.TextShape;
import progettose.shapes.wrappers.ShapeInterface;

/**
 *
 * Class to manage the creation of an EllipseShape
 *
 *
 */
public class TextCreator extends ShapeCreator {

    /**
     * This method is used to create an ellipse Shape.
     *
     * @param type This is the first parameter that represents the category of
     * the ShapeInterface that needs to be created
     * @param clickX This is the second parameter that represents the starting X
     * coordinate (left) of the first letter of the text
     * @param clickY This is the third parameter that represents the starting Y
     * coordinate (bottom) of the first letter of the text
     * @param interiorColor Unused.
     * @param borderColor Unused.
     * @param endX This is the sixth parameter that represents the fontsize
     * @param endY Unused.
     * @param z This is the eight parameter that represent the Z coordinate of
     * the shape  In this method we create a TextShape 
     *
     * @return ShapeInterface This return the EllipseShape created
     */

    @Override
    public ShapeInterface createShape(String type, double clickX, double clickY, Color interiorColor, Color borderColor, double endX, double endY, int z) {
        //Rectangle r = (Rectangle)super.createShape("rectangle", clickX, clickY, interiorColor, borderColor, endX, endY, z).getShape();
        Text text = new Text(type);
        text.setFill(Color.BLACK);
        text.setFont(new Font(text.getFont().getName(), endX));
        text.setX(clickX);
        text.setY(clickY);
        TextShape ls = new TextShape(text, text.getX(), text.getY() - text.getFont().getSize(), z);
        return ls;
    }

}
