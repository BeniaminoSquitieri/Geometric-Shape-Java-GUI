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
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.ShapeInterface;

public class EllipseToolTest {

    private EllipseTool ellipseTool;
    private CanvasClass canvas;

    @Before
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        this.ellipseTool = new EllipseTool(new Pane());
        this.canvas = CanvasClass.getInstance(new Pane());
    }

    @After
    public void tearDown() {
        this.ellipseTool = null;
        this.canvas = null;
    }

    @Test
    public void testMouseUp() {
        this.ellipseTool.mouseDown(this.canvas, Math.random(), Math.random());
        this.ellipseTool.mouseUp(this.canvas, Math.random(), Math.random());
        Map<Shape, ShapeInterface> shapesOnCanvas = this.canvas.getShapesOnCanvas();
        assertEquals(shapesOnCanvas.size(), 1);
        for (ShapeInterface shape : shapesOnCanvas.values()) {
            if (!(shape instanceof EllipseShape)) {
                fail("Inserting of a rectangle failed");
            }
        }
    }
}
