package progettose.shapes.wrappers;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.shapes.creators.ShapeCreator;

public class TextShapeTest {

    private TextShape instance;
    private Text text;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private JFXPanel jfxpanel;

    @Before
    @Test
    public void TextShapeTest() {
        jfxpanel = new JFXPanel();
        ShapeCreator creator = new ShapeCreator();
        this.startX = Math.random();
        this.startY = Math.random();
        this.endX = Math.random();
        this.endY = Math.random();
        this.instance = (TextShape) creator.createShape("rectangle/", this.startX, this.startY, Color.BLUEVIOLET, Color.POWDERBLUE, this.endX, this.endY, 0);
        this.text = this.instance.getShape();
        assertNotNull(this.text);
        assertNotNull(this.instance);
    }

    @Test
    public void boundTest() {
        assertNotNull(this.instance.bound());
    }

    @Test
    public void getBoundingBoxTest() {
        Text result = this.instance.getShape();
        assertNotNull(this.instance.getBoundingBox());
        Rectangle expectedRectangle = this.instance.getBoundingBox().getBox();
        assertEquals(expectedRectangle.getWidth(), result.getLayoutBounds().getWidth() + 5, 0.001);
        assertEquals(expectedRectangle.getHeight(), result.getFont().getSize() + 5, 0.001);
        assertEquals(expectedRectangle.getX(), result.getX(), 0.001);
        assertEquals(expectedRectangle.getY(), result.getY() - result.getFont().getSize(), 0.001);
    }

    @Test
    public void getShapeTest() {
        assertEquals(this.instance.getShape(), this.text);
    }

    @Test
    public void setXTest() {
        double newX = Math.random();
        this.instance.setX(newX);
        assertEquals(newX, this.instance.getX(), 0);
        assertEquals(newX, this.instance.getBoundingBox().getBox().getX(), 0);
        assertEquals(newX, this.instance.getShape().getX(), 0);
    }

    @Test
    public void setYTest() {
        double newY = Math.random();
        this.instance.setY(newY);
        assertEquals(newY, this.instance.getY(), 0.001);
        assertEquals(newY, this.instance.getShape().getY() - this.text.getFont().getSize(), 0.001);
    }

    @After
    public void tearDown() {
        this.instance = null;
        assertNull(this.instance);
        this.text = null;
        assertNull(this.text);
    }
}
