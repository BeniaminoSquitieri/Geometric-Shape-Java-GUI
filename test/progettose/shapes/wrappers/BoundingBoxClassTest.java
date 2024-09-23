package progettose.shapes.wrappers;

import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoundingBoxClassTest {

    private BoundingBoxClass instance;

    @Before
    @Test
    public void BoundingBoxTest() {
        this.instance = new BoundingBoxClass(Math.random(), Math.random(), Math.random(), Math.random());
        assertNotNull(this.instance);
        this.instance = new BoundingBoxClass(0, 0, 0, 0);
    }

    @After
    public void tearDown() {
        this.instance = null;
        assertNull(this.instance);
    }

    @Test
    public void testGetBox() {
        Rectangle result = this.instance.getBox();
        Rectangle expectedRectangle = new Rectangle(0, 0, 0, 0);
        assertEquals(expectedRectangle.getWidth(), result.getWidth(), 0);
        assertEquals(expectedRectangle.getHeight(), result.getHeight(), 0);
        assertEquals(expectedRectangle.getX(), result.getX(), 0);
        assertEquals(expectedRectangle.getY(), result.getY(), 0);
    }
}
