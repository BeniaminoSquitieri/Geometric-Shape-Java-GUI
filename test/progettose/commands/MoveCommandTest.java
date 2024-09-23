package progettose.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.LineShape;
import progettose.shapes.wrappers.RectangleShape;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.TextShape;

public class MoveCommandTest {

    private MoveCommand command;
    private Receiver receiver;
    private CanvasClass app;
    private ArrayList<Shape> selected = new ArrayList<>();

    @Before
    @Test
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        try {
            this.app = CanvasClass.getInstance(new Pane());
            Map<Shape, ShapeInterface> prevContext = new HashMap<>();
            int z = 0;
            Shape shape1 = new Rectangle(0, 0, Math.random() * 100, Math.random() * 100);
            Shape shape2 = new Ellipse(0, 0, Math.random() * 100, Math.random() * 100);
            Shape shape3 = new Line(0, 0, Math.random() * 100, Math.random() * 100);
            Shape shape4 = new Text("test");
            ShapeInterface selected1 = new RectangleShape((Rectangle) shape1, 0, 0, z);
            z++;
            ShapeInterface selected2 = new EllipseShape((Ellipse) shape2, 0, 0, z);
            z++;
            prevContext.put(shape1, selected1);
            prevContext.put(shape2, selected2);
            prevContext.put(shape3, new LineShape((Line) shape3, 0, 0, z));
            z++;
            prevContext.put(shape4, new TextShape((Text) shape4, 0, 0, z));

            ArrayList<Group> selectedShapes = new ArrayList<>();
            selectedShapes.add(new Group(selected1.getShape(), selected1.getBoundingBox().getBox()));
            selectedShapes.add(new Group(selected2.getShape(), selected2.getBoundingBox().getBox()));
            shape1.setStroke(Color.BLACK);
            shape2.setStroke(Color.BLACK);
            shape3.setStroke(Color.BLACK);
            shape4.setStroke(Color.BLACK);
            selected.add(selected1.getShape());
            selected.add(selected2.getShape());

            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(app, prevContext);
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(app, selectedShapes);
            this.receiver = new Receiver(prevContext, selectedShapes, app);
            this.command = new MoveCommand(app, this.receiver);
            assertNotNull(this.command);
            assertNotNull(this.command.backup);
            assertEquals(0, this.command.backup.size());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(CutCommandTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        this.command = null;
        this.app = null;
        this.receiver = null;
    }

    @Test
    public void testExecute() {
        MoveCommand move = new MoveCommand(app, receiver);
        move.execute();
        boolean expResult = true;
        boolean result = this.command.execute();
        assertEquals(expResult, result);
        assertNotNull(this.command.backup);
        assertNotEquals(0, this.command.backup.size());
    }

    @Test
    public void testUndo() {
        command.execute();
        Map<Shape, ShapeInterface> beforeExecute = new HashMap<>(this.app.getShapesOnCanvas());
        this.command.execute();
        assertNotEquals(this.command.backup, app.getShapesOnCanvas());
        this.command.undo();
        assertEquals(this.command.backup, app.getShapesOnCanvas());
        Map<Shape, ShapeInterface> shapesOnCanvas = new HashMap<>(this.app.getShapesOnCanvas());
        assertEquals(shapesOnCanvas.size(), beforeExecute.size());
    }
}
