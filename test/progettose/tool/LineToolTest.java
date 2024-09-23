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
import progettose.shapes.wrappers.LineShape;
import progettose.shapes.wrappers.ShapeInterface;

public class LineToolTest {

    private LineTool lineTool;
    private CanvasClass canvas;

    @Before
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        this.lineTool = new LineTool(new Pane());
        this.canvas = CanvasClass.getInstance(new Pane());
    }

    @After
    public void tearDown() {
        this.lineTool = null;
        this.canvas = null;
    }

    @Test
    public void testMouseUp() {
        this.lineTool.mouseDown(this.canvas, Math.random(), Math.random());
        this.lineTool.mouseUp(this.canvas, Math.random(), Math.random());
        Map<Shape, ShapeInterface> shapesOnCanvas = this.canvas.getShapesOnCanvas();
        assertEquals(shapesOnCanvas.size(), 1);
        for (ShapeInterface shape : shapesOnCanvas.values()) {
            if (!(shape instanceof LineShape)) {
                fail("Inserting of a rectangle failed");
            }
        }
    }
}
