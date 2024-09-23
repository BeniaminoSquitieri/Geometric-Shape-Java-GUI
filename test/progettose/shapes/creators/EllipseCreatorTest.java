package progettose.shapes.creators;

import progettose.shapes.wrappers.ShapeInterface;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.Test;
import static org.junit.Assert.*;

public class EllipseCreatorTest {

    @Test
    public void testCreateShape() {
        String type = "ellipse";
        double clickX = Math.random();
        double clickY = Math.random();
        Color interiorColor = Color.WHITE;
        Color borderColor = Color.BLUE;
        double width = Math.random();
        double height = Math.random();
        int z = (int) Math.random();
        EllipseCreator instance = new EllipseCreator();
        ShapeInterface expResult = null;
        ShapeInterface result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals((int) clickX, (int) result.getX());
        assertEquals((int) clickY, (int) result.getY());
        assertEquals(z, result.getZ());
        assertEquals(interiorColor, Color.web(result.getShape().getFill().toString()));
        assertEquals(borderColor, Color.web(result.getShape().getStroke().toString()));
        assertEquals(true, result.getShape() instanceof Ellipse);
        assertEquals((int) Math.abs(clickX - width) / 2, (int) ((Ellipse) result.getShape()).getRadiusX());
        assertEquals((int) Math.abs(clickY - height) / 2, (int) ((Ellipse) result.getShape()).getRadiusY());
        assertEquals((int) (clickX + width) / 2, (int) ((Ellipse) result.getShape()).getCenterX());
        assertEquals((int) (clickY + height) / 2, (int) ((Ellipse) result.getShape()).getCenterY());
    }
}
