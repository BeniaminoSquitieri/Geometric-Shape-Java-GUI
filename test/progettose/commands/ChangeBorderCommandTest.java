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
import progettose.shapes.wrappers.*;

public class ChangeBorderCommandTest {

    private ChangeBorderCommand command;
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
            Shape shape4 = new Text("text");

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
            selectedShapes.add(new Group(shape4, prevContext.get(shape4).getBoundingBox().getBox()));
            shape1.setStroke(Color.BLACK);
            shape2.setStroke(Color.BLACK);
            shape3.setStroke(Color.BLACK);
            shape4.setStroke(Color.BLACK);
            shape1.setFill(Color.WHITE);
            shape2.setFill(Color.WHITE);
            shape2.setFill(Color.WHITE);
            shape4.setFill(Color.BLACK);
            selected.add(selected1.getShape());
            selected.add(selected2.getShape());
            selected.add(shape4);

            this.app.setBorderColor(Color.BLUEVIOLET);

            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(app, prevContext);
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(app, selectedShapes);
            this.receiver = new Receiver(prevContext, selectedShapes, app);
            this.command = new ChangeBorderCommand(app, this.receiver);
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
        Color choosenColor = this.app.getBorderColor();
        ArrayList<Color> prevColor = new ArrayList<>();
        for (int i = 0; i < this.selected.size(); i++) {
            prevColor.add((Color) this.selected.get(i).getStroke());
        }
        boolean result = this.command.execute();
        assertTrue(result);
        for (int i = 0; i < this.selected.size(); i++) {
            if (this.selected.get(i) instanceof Text) {
                assertEquals(prevColor.get(i), ((Text) this.selected.get(i)).getStroke());
            } else {
                assertNotEquals(prevColor.get(i), this.selected.get(i).getStroke());
                assertEquals(choosenColor, this.selected.get(i).getStroke());
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
