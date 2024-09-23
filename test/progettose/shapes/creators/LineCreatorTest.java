package progettose.shapes.creators;

import progettose.shapes.wrappers.ShapeInterface;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.junit.Test;
import static org.junit.Assert.*;

public class LineCreatorTest {

    @Test
    public void testCreateShape() {
        String type = "line";
        double clickX = Math.random();
        double clickY = Math.random();
        Color interiorColor = Color.WHITE;
        Color borderColor = Color.BLUE;
        double width = Math.random();
        double height = Math.random();
        int z = (int) Math.random();
        LineCreator instance = new LineCreator();
        ShapeInterface expResult = null;
        ShapeInterface result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals((int) clickX, (int) result.getX());
        assertEquals((int) clickY, (int) result.getY());
        assertEquals(z, result.getZ());
        assertEquals(borderColor, Color.web(result.getShape().getStroke().toString()));
        assertEquals(true, result.getShape() instanceof Line);
        assertEquals((int) clickX, (int) ((Line) result.getShape()).getStartX());
        assertEquals((int) clickY, (int) ((Line) result.getShape()).getStartY());
        assertEquals((int) width, (int) ((Line) result.getShape()).getEndX());
        assertEquals((int) height, (int) ((Line) result.getShape()).getEndY());
    }
}
