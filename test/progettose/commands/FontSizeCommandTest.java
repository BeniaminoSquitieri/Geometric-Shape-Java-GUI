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
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.EllipseShape;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.TextShape;

public class FontSizeCommandTest {

    private FontSizeCommand command;
    private Receiver receiver;
    private CanvasClass app;
    private ArrayList<Shape> selected = new ArrayList<>();
    private int fontSize;

    @Before
    @Test
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        try {
            this.app = CanvasClass.getInstance(new Pane());
            Map<Shape, ShapeInterface> prevContext = new HashMap<>();
            Shape shape1 = new Text("test1");
            ((Text) shape1).setFont(new Font(((Text) shape1).getFont().getName(), Math.random()));
            ((Text) shape1).setFill(Color.BLACK);
            ((Text) shape1).setX(Math.random());
            ((Text) shape1).setY(Math.random());
            Shape shape2 = new Ellipse(0, 0, Math.random(), Math.random());
            Shape shape3 = new Text("test2");
            ((Text) shape3).setFont(new Font(((Text) shape3).getFont().getName(), Math.random()));
            ((Text) shape3).setFill(Color.BLACK);
            ((Text) shape3).setX(Math.random());
            ((Text) shape3).setY(Math.random());
            Shape shape4 = new Ellipse(0, 0, Math.random(), Math.random());
            shape2.setStroke(Color.BLACK);
            shape2.setFill(Color.WHITE);
            shape4.setStroke(Color.BLACK);
            shape4.setFill(Color.WHITE);

            ShapeInterface selected1 = new TextShape((Text) shape1, 0, 0);
            ShapeInterface selected2 = new EllipseShape((Ellipse) shape2, 0, 0);
            ShapeInterface selected3 = new TextShape((Text) shape3, 0, 0);
            prevContext.put(shape1, selected1);
            prevContext.put(shape3, selected3);
            prevContext.put(shape2, selected2);
            prevContext.put(shape4, new EllipseShape((Ellipse) shape4, 0, 0));
            ArrayList<Group> selectedShapes = new ArrayList<>();
            selectedShapes.add(new Group(selected1.getShape(), selected1.getBoundingBox().getBox()));
            selectedShapes.add(new Group(selected2.getShape(), selected2.getBoundingBox().getBox()));
            selectedShapes.add(new Group(selected3.getShape(), selected3.getBoundingBox().getBox()));

            selected.add(selected1.getShape());
            selected.add(selected2.getShape());
            selected.add(selected3.getShape());

            fontSize = (int) Math.random() + 1;
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(app, prevContext);
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(app, selectedShapes);
            this.app.setFontSize(fontSize);
            this.receiver = new Receiver(prevContext, selectedShapes, app);
            this.command = new FontSizeCommand(app, this.receiver);
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
        for (Shape s : this.selected) {
            if (!this.app.getShapesOnCanvas().containsKey(s)) {
                fail("Error on checking if the selected shapes where on the canvas");
            }
        }
        boolean expResult = true;
        boolean result = this.command.execute();
        assertEquals(expResult, result);
        assertNotNull(this.command.backup);
        for (Shape s : this.selected) {

            if ((this.app.getShapesOnCanvas().get(s) instanceof Text) && !(((Text) this.app.getShapesOnCanvas().get(s).getShape()).getFont().getSize() == fontSize)) {
                fail("Delete not executed correctly");
            }
        }
    }

    @Test
    public void testUndo() {
        this.command.execute();
        assertNotEquals(this.command.backup, app.getShapesOnCanvas());
        this.command.undo();
        assertEquals(this.command.backup, app.getShapesOnCanvas());
    }

    @Test
    public void saveBackup() {
        Map<Shape, ShapeInterface> prevBackup = new HashMap<>();
        prevBackup.putAll(this.command.backup);
        this.command.saveBackup();
        assertNotEquals(prevBackup, this.command.backup);
    }

}
