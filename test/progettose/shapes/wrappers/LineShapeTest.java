package progettose.shapes.wrappers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progettose.shapes.creators.ShapeCreator;

public class LineShapeTest {

    private LineShape instance;
    private Line line;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    @Before
    @Test
    public void LineShapeTest() {
        ShapeCreator creator = new ShapeCreator();
        this.startX = Math.random();
        this.startY = Math.random();
        this.endX = Math.random();
        this.endY = Math.random();
        this.instance = (LineShape) creator.createShape("line", this.startX, this.startY, Color.BLUEVIOLET, Color.POWDERBLUE, this.endX, this.endY, 0);
        assertNotNull(this.instance);
        this.line = this.instance.getShape();
        assertNotNull(this.line);
    }

    @Test
    public void boundTest() {
        assertNotNull(this.instance.bound());
    }

    @Test
    public void getShapeTest() {
        assertEquals(this.instance.getShape(), this.line);
    }

    @Test
    public void getBoundingBoxTest() {
        Rectangle result = new Rectangle(this.line.getLayoutBounds().getMinX(), this.line.getLayoutBounds().getMinY(), this.line.getLayoutBounds().getWidth(), this.line.getLayoutBounds().getHeight());
        assertNotNull(this.instance.getBoundingBox());
        Rectangle expectedRectangle = this.instance.getBoundingBox().getBox();
        assertEquals(expectedRectangle.getWidth(), result.getWidth(), 0);
        assertEquals(expectedRectangle.getHeight(), result.getHeight(), 0);
        assertEquals(expectedRectangle.getX(), result.getX(), 0);
        assertEquals(expectedRectangle.getY(), result.getY(), 0);
    }

    @Test
    public void setXTest() {
        double oldX = this.instance.getX();
        double oldEndX = this.instance.getShape().getEndX();
        double newX = Math.random();
        this.instance.setX(newX);
        assertEquals(newX, this.instance.getX(), 0);
        assertEquals(newX, this.instance.getShape().getStartX(), 0);
        assertEquals(oldEndX - oldX + newX, this.instance.getShape().getEndX(), 0);
    }

    @Test
    public void setYTest() {
        double oldY = this.instance.getY();
        double oldEndY = this.instance.getShape().getEndY();
        double newY = Math.random();
        this.instance.setY(newY);
        assertEquals(newY, this.instance.getY(), 0);
        assertEquals(newY, this.instance.getShape().getStartY(), 0);
        assertEquals(oldEndY - oldY + newY, this.instance.getShape().getEndY(), 0);
    }

    @After
    public void tearDown() {
        this.instance = null;
        assertNull(this.instance);
        this.line = null;
        assertNull(this.line);
    }
}
