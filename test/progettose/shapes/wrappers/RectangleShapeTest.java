package progettose.shapes.wrappers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import progettose.shapes.creators.ShapeCreator;

public class RectangleShapeTest {

    private RectangleShape instance;
    private Rectangle rect;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    @Before
    @Test
    public void RectangleShapeTest() {
        ShapeCreator creator = new ShapeCreator();
        this.startX = Math.random();
        this.startY = Math.random();
        this.endX = Math.random();
        this.endY = Math.random();
        this.instance = (RectangleShape) creator.createShape("rectangle", this.startX, this.startY, Color.BLUEVIOLET, Color.POWDERBLUE, this.endX, this.endY, 0);
        this.rect = this.instance.getShape();
        assertNotNull(this.rect);
        assertNotNull(this.instance);
    }

    @Test
    public void boundTest() {
        assertNotNull(this.instance.bound());
    }

    @Test
    public void getBoundingBoxTest() {
        Rectangle result = new Rectangle(this.rect.getLayoutBounds().getMinX(), this.rect.getLayoutBounds().getMinY(), this.rect.getLayoutBounds().getWidth(), this.rect.getLayoutBounds().getHeight());
        assertNotNull(this.instance.getBoundingBox());
        Rectangle expectedRectangle = this.instance.getBoundingBox().getBox();
        assertEquals(expectedRectangle.getWidth(), result.getWidth(), 0);
        assertEquals(expectedRectangle.getHeight(), result.getHeight(), 0);
        assertEquals(expectedRectangle.getX(), result.getX(), 0);
        assertEquals(expectedRectangle.getY(), result.getY(), 0);
    }

    @Test
    public void getShapeTest() {
        assertEquals(this.instance.getShape(), this.rect);
    }
    
    @Test
    public void setXTest(){
        double newX = Math.random();
        this.instance.setX(newX);
        assertEquals(newX, this.instance.getX(), 0);
        assertEquals(newX, this.instance.getShape().getX(), 0);
    }
    
    @Test
    public void setYTest(){
        double newY = Math.random();
        this.instance.setY(newY);
        assertEquals(newY, this.instance.getY(), 0);
        assertEquals(newY, this.instance.getShape().getY(), 0);
    }

    @After
    public void tearDown() {
        this.instance = null;
        assertNull(this.instance);
        this.rect = null;
        assertNull(this.rect);
    }
}
