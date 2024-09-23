package progettose.strategy;

import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResizeASStrategyTest {

    private ResizeContext context1 = new ResizeContext();
    private ResizeContext context2 = new ResizeContext();
    private ResizeContext context3 = new ResizeContext();
    private ResizeContext context4 = new ResizeContext();
    private double minx1, miny1, minx2, miny2, minx3, miny3, minx4, miny4 = Math.random();

    @Before
    @Test
    public void setUp() {
        context1.setStrategy(new ResizeASStrategy());
        context1.setContext(minx1, 10.00, miny1, 5.00, 5.00, 10.00);
        context2.setStrategy(new ResizeASStrategy());
        context2.setContext(minx2, 15.00, miny2, 5.00, 110.00, 10.00);
        context3.setStrategy(new ResizeASStrategy());
        context3.setContext(minx3, 10, miny3, 210.00, 5.00, 25.00);
        context4.setStrategy(new ResizeASStrategy());
        context4.setContext(minx4, 35, miny4, 310.00, 35.00, 35.00);
    }

    @Test
    public void testGetExclusivity() {
        assertEquals(3, context1.getExclusivity());
        assertEquals(3, context2.getExclusivity());
        assertEquals(3, context3.getExclusivity());
        assertEquals(3, context4.getExclusivity());
    }

    @After
    public void tearDown() throws Exception {
        context1 = null;
        context2 = null;
        context3 = null;
        context4 = null;
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
