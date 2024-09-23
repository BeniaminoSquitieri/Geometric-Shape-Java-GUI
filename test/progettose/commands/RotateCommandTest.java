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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.LineShape;
import progettose.shapes.wrappers.RectangleShape;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.ShapeZComparator;
import progettose.shapes.wrappers.TextShape;

public class RotateCommandTest {

    private RotateCommand command;
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
            Shape shape1 = new Rectangle(100, 100, Math.random() * 10, Math.random() * 10);
            Shape shape2 = new Ellipse(10, 10, Math.random() * 100, Math.random() * 10);
            Shape shape3 = new Line(10, 10, Math.random() * 10, Math.random() * 10);
            Shape shape4 = new Text("test");
            ShapeInterface selected1 = new RectangleShape((Rectangle) shape1, 100, 100, z);
            z++;
            ShapeInterface selected2 = new EllipseShape((Ellipse) shape2, 10, 10, z);
            z++;
            prevContext.put(shape1, selected1);
            prevContext.put(shape2, selected2);
            prevContext.put(shape3, new LineShape((Line) shape3, 10, 10, z));
            z++;
            prevContext.put(shape4, new TextShape((Text) shape4, 10, 10, z));

            ArrayList<Group> selectedShapes = new ArrayList<>();
            selectedShapes.add(new Group(selected1.getShape(), selected1.getBoundingBox().getBox()));
            selectedShapes.add(new Group(selected2.getShape(), selected2.getBoundingBox().getBox()));
            selectedShapes.add(new Group(shape4, prevContext.get(shape4).getBoundingBox().getBox()));
            shape1.setStroke(Color.BLACK);
            shape2.setStroke(Color.BLACK);
            shape3.setStroke(Color.BLACK);
            shape4.setStroke(Color.BLACK);
            selected.add(selected1.getShape());
            selected.add(selected2.getShape());
            selected.add(shape4);

            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(app, prevContext);
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(app, selectedShapes);
            this.receiver = new Receiver(prevContext, selectedShapes, app);
            this.command = new RotateCommand(app, this.receiver);
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
        this.selected = null;
    }

    @Test
    public void testExecute() {
        ArrayList<ShapeInterface> shapesOnCanvasBefore = new ArrayList(this.app.getShapesOnCanvas().values());
        boolean expResult = false;
        boolean result = true;
        try{
            result = this.command.execute();
        }catch(IllegalStateException e){
            result = true;
        }catch(Exception e){
            result = false;
        }
        assertEquals(expResult, result);
        assertNotNull(this.command.backup);
        assertNotEquals(0, this.command.backup.size());
        ArrayList<ShapeInterface> shapesOnCanvasAfter = new ArrayList(this.app.getShapesOnCanvas().values());
        shapesOnCanvasBefore.sort(new ShapeZComparator());
        shapesOnCanvasAfter.sort(new ShapeZComparator());
        for (ShapeInterface before : shapesOnCanvasBefore) {
            for (ShapeInterface after : shapesOnCanvasAfter) {
                if (before.getZ() == after.getZ() && this.app.getSelectedShapes().get(0).getChildren().contains(after.getShape())) {
                    assertNotEquals(after.getHeight(), before.getWidth(), 0.1);
                    assertNotEquals(before.getHeight(), after.getWidth(), 0.1);
                }
            }
        }
    }

    @Test
    public void testUndo() {
        Map<Shape, ShapeInterface> beforeExecute = new HashMap<>(this.app.getShapesOnCanvas());
        this.command.execute();
        assertNotEquals(this.command.backup, app.getShapesOnCanvas());
        Map<Shape, ShapeInterface> beforeUndo = new HashMap<>(this.app.getShapesOnCanvas());
        this.command.undo();
        assertEquals(this.command.backup, this.app.getShapesOnCanvas());
        assertEquals(this.app.getShapesOnCanvas().size(), beforeExecute.size());
    }

    @Test
    public void saveBackup() {
        Map<Shape, ShapeInterface> prevBackup = new HashMap<>();
        prevBackup.putAll(this.command.backup);
        this.command.saveBackup();
        assertNotEquals(prevBackup, this.command.backup);
    }
}
