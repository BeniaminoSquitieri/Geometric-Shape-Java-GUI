package progettose.tool;

import java.util.Map;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.RectangleShape;
import progettose.shapes.wrappers.ShapeInterface;

public class RectangleToolTest {

    private RectangleTool rectTool;
    private CanvasClass canvas;

    @Before
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        this.rectTool = new RectangleTool(new Pane());
        this.canvas = CanvasClass.getInstance(new Pane());
    }

    @After
    public void tearDown() {
        this.rectTool = null;
        this.canvas = null;
    }

    @Test
    public void testMouseUp() {
        this.rectTool.mouseDown(this.canvas, Math.random(), Math.random());
        this.rectTool.mouseUp(this.canvas, Math.random(), Math.random());
        Map<Shape, ShapeInterface> shapesOnCanvas = this.canvas.getShapesOnCanvas();
        assertEquals(shapesOnCanvas.size(), 1);
        for (ShapeInterface shape : shapesOnCanvas.values()) {
            if (!(shape instanceof RectangleShape)) {
                fail("Inserting of a rectangle failed");
            }
        }
    }
}
