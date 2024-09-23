package progettose.shapes.wrappers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progettose.shapes.creators.ShapeCreator;

public class EllipseShapeTest {

    private EllipseShape instance;
    private Ellipse ellipse;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    @Before
    @Test
    public void EllipseShapeTest() {
        ShapeCreator creator = new ShapeCreator();
        this.startX = Math.random();
        this.startY = Math.random();
        this.endX = Math.random();
        this.endY = Math.random();
        this.instance = (EllipseShape) creator.createShape("ellipse", this.startX, this.startY, Color.BLUEVIOLET, Color.POWDERBLUE, this.endX, this.endY, 0);
        this.ellipse = this.instance.getShape();
        assertNotNull(this.ellipse);
        assertNotNull(this.instance);
    }

    @Test
    public void boundTest() {
        assertNotNull(this.instance.bound());
    }

    @Test
    public void getBoundingBoxTest() {
        Rectangle result = new Rectangle(this.ellipse.getLayoutBounds().getMinX(), this.ellipse.getLayoutBounds().getMinY(), this.ellipse.getLayoutBounds().getWidth(), this.ellipse.getLayoutBounds().getHeight());
        assertNotNull(this.instance.getBoundingBox());
        Rectangle expectedRectangle = this.instance.getBoundingBox().getBox();
        assertEquals(expectedRectangle.getWidth(), result.getWidth(), 0.00001);
        assertEquals(expectedRectangle.getHeight(), result.getHeight(), 0.00001);
        assertEquals(expectedRectangle.getX(), result.getX(), 0.00001);
        assertEquals(expectedRectangle.getY(), result.getY(), 0.00001);
    }

    @Test
    public void setXTest() {
        double oldX = this.instance.getX();
        double oldCenterX = this.ellipse.getCenterX();
        double newX = Math.random();
        this.instance.setX(newX);
        assertEquals(newX, this.instance.getX(), 0.00001);
        assertEquals(oldCenterX + newX - oldX, this.instance.getShape().getCenterX(), 0.00001);
    }

    @Test
    public void setYTest() {
        double oldY = this.instance.getY();
        double oldCenterY = this.ellipse.getCenterY();
        double newY = Math.random();
        this.instance.setY(newY);
        assertEquals(newY, this.instance.getY(), 0.00001);
        assertEquals(oldCenterY + newY - oldY, this.instance.getShape().getCenterY(), 0.00001);
    }

    @After
    public void tearDown() {
        this.instance = null;
        assertNull(this.instance);
        this.ellipse = null;
        assertNull(this.ellipse);
    }
}
