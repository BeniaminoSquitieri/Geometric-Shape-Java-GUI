package progettose.shapes.creators;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.shapes.wrappers.ShapeInterface;

public class TextCreatorTest {

    @Test
    public void testCreateShape() {
        JFXPanel jfxpanel = new JFXPanel();
        String type = "ellipse";
        double clickX = Math.random();
        double clickY = Math.random();
        Color interiorColor = Color.WHITE;
        Color borderColor = Color.BLUE;
        double width = Math.random();
        double height = Math.random();
        int z = (int) Math.random();
        TextCreator instance = new TextCreator();
        ShapeInterface expResult = null;
        ShapeInterface result = instance.createShape(type, clickX, clickY, interiorColor, borderColor, width, height, z);
        assertNotEquals(expResult, result);
        assertEquals((int) clickX, (int) result.getX());
        assertEquals((int) clickY, (int) result.getY());
        assertEquals(z, result.getZ());
        assertEquals(true, result.getShape() instanceof Text);
        assertEquals(type, ((Text) result.getShape()).getText());
        assertEquals((int) width, (int) ((Text) result.getShape()).getFont().getSize());
    }

}
