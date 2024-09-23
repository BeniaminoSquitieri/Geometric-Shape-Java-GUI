package progettose.shapes.creators;

import javafx.embed.swing.JFXPanel;
import progettose.shapes.wrappers.LineShape;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.RectangleShape;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.shapes.wrappers.TextShape;

public class ShapeCreatorTest {

    @Test
    public void testCreateShape() {
        JFXPanel jfxpanel = new JFXPanel();
        String type = "rectangle";
        double clickX = Math.random();
        double clickY = Math.random();
        Color interiorColor = Color.WHITE;
        Color borderColor = Color.BLUE;
        double width = Math.random();
        double height = Math.random();
        int z = (int) Math.random();
        ShapeCreator instance = new ShapeCreator();
        ShapeInterface expResult = null;
        ShapeInterface result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals(true, result instanceof RectangleShape);
        type = "line";
        result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals(true, result instanceof LineShape);
        type = "ellipse";
        result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals(true, result instanceof EllipseShape);
        type = "ellipse/";
        result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals(true, result instanceof TextShape);
    }
}
