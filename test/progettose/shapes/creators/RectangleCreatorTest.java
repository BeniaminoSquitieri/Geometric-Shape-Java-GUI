package progettose.shapes.creators;

import progettose.shapes.wrappers.ShapeInterface;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class RectangleCreatorTest {

    @Test
    public void testCreateShape() {
        String type = "rectangle";
        double clickX = Math.random();
        double clickY = Math.random();
        Color interiorColor = Color.WHITE;
        Color borderColor = Color.BLUE;
        double width = Math.random();
        double height = Math.random();
        int z = (int) Math.random();
        RectangleCreator instance = new RectangleCreator();
        ShapeInterface expResult = null;
        ShapeInterface result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals((int) clickX, (int) result.getX());
        assertEquals((int) clickY, (int) result.getY());
        assertEquals(z, result.getZ());
        assertEquals(interiorColor, Color.web(result.getShape().getFill().toString()));
        assertEquals(borderColor, Color.web(result.getShape().getStroke().toString()));
        assertEquals(true, result.getShape() instanceof Rectangle);
        assertEquals((int) clickX, (int) ((Rectangle) result.getShape()).getX());
        assertEquals((int) clickY, (int) ((Rectangle) result.getShape()).getY());
        assertEquals((int) Math.abs(width - clickX), (int) ((Rectangle) result.getShape()).getWidth());
        assertEquals((int) Math.abs(height - clickY), (int) ((Rectangle) result.getShape()).getHeight());
    }
}
