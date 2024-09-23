package progettose.strategy;

import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResizeBSStrategyTest {

    private ResizeContext context1 = new ResizeContext();
    private ResizeContext context2 = new ResizeContext();
    private ResizeContext context3 = new ResizeContext();
    private ResizeContext context4 = new ResizeContext();
    private double minx1, maxy1, minx2, maxy2, minx3, maxy3, minx4, maxy4 = Math.random();

    @Before
    @Test
    public void setUp() {
        context1.setStrategy(new ResizeBSStrategy());
        context1.setContext(minx1, 10.00, 5.00, maxy1, 5.00, 10.00);
        context2.setStrategy(new ResizeBSStrategy());
        context2.setContext(minx2, 15.00, 5.00, maxy2, 110.00, 10.00);
        context3.setStrategy(new ResizeBSStrategy());
        context3.setContext(minx3, 10, 210.00, maxy3, 5.00, 25.00);
        context4.setStrategy(new ResizeBSStrategy());
        context4.setContext(minx4, 35, 310.00, maxy4, 35.00, 35.00);
    }

    @After
    public void tearDown() throws Exception {
        context1 = null;
        context2 = null;
        context3 = null;
        context4 = null;
    }

    @Test
    public void testGetExclusivity() {
        assertEquals(2, context1.getExclusivity());
        assertEquals(2, context2.getExclusivity());
        assertEquals(2, context3.getExclusivity());
        assertEquals(2, context4.getExclusivity());
    }

    @Test
    public void testSetCoordinates() {
        assertEquals(context1.getMaxX(), 10.0);
        assertEquals(context1.getMinX(), 5.0);
        assertEquals(context1.getMaxY(), 10.0);
        assertEquals(context1.getMinY(), 5.0);
        assertEquals(context2.getMaxX(), 110.0);
        assertEquals(context2.getMinX(), 15.0);
        assertEquals(context2.getMaxY(), 10.0);
        assertEquals(context2.getMinY(), 5.0);
        assertEquals(context3.getMaxX(), 10.0);
        assertEquals(context3.getMinX(), 5.0);
        assertEquals(context3.getMaxY(), 210.0);
        assertEquals(context3.getMinY(), 25.0);
        assertEquals(context4.getMaxX(), 35.0);
        assertEquals(context4.getMinX(), 35.0);
        assertEquals(context4.getMaxY(), 310.0);
        assertEquals(context4.getMinY(), 35.0);
    }
}
