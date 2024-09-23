
package progettose.application;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.commands.Command;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.tool.Tool;
import progettose.commands.CommandHistory;
import progettose.commands.CopyCommand;
import progettose.commands.CutCommand;
import progettose.commands.DeleteCommand;
import progettose.commands.FontSizeCommand;
import progettose.commands.InsertCommand;
import progettose.commands.MoveCommand;
import progettose.commands.PasteCommand;
import progettose.commands.Receiver;
import progettose.commands.ResizeCommand;
import progettose.commands.SendBackwardCommand;
import progettose.commands.SendForwardCommand;
import progettose.commands.SendToBackCommand;
import progettose.commands.SendToFrontCommand;
import progettose.shapes.creators.ShapeCreator;
import progettose.tool.RectangleTool;


public class CanvasClassTest {
    private static CanvasClass instance;
    private ShapeCreator creator= new ShapeCreator();
    private CommandHistory commandHistory;
    @Before
    public void setUp() {
        JFXPanel fxPanel = new JFXPanel();
        Pane canvas = new Pane();
        this.instance=CanvasClass.getInstance(canvas);
        this.commandHistory = new CommandHistory();
    }
    

    @After
    public void tearDown() {
        this.instance = null;
        this.commandHistory = null;
    }

    /**
     * Test of getInstance method, of class CanvasClass.
     */
    @Test
    public void testGetInstance() {
        Pane canvas = new Pane();
        CanvasClass expResult = CanvasClass.getInstance(canvas);
        CanvasClass result = CanvasClass.getInstance(canvas);
        assertEquals(expResult, result);

    }

    /**
     * Test of createShape method, of class CanvasClass.
     */
    @Test
    public void testCreateShape_7args() {
        String type1 = "rectangle";
        String type2 =  "ellipse";
        String type3 = "line";
        double startX = 10.0;
        double startY = 20.0;
        double endX = 30.0;
        double endY = 40.0;
        Color fill = Color.GOLD;
        Color stroke = Color.BROWN;
        instance.createShape(type1, startX, startY, endX, endY, fill, stroke);
        instance.createShape(type2, startX, startY, endX, endY, fill, stroke);
        instance.createShape(type3, startX, startY, endX, endY, fill, stroke);

    }

    /**
     * Test of createShape method, of class CanvasClass.
     */
    @Test
    public void testCreateShape_8args() {
        String type1 = "rectangle";
        String type2 =  "ellipse";
        String type3 = "line";
        double startX = 10.0;
        double startY = 20.0;
        double endX = 30.0;
        double endY = 40.0;
        Color fill = Color.GOLD;
        Color stroke = Color.BROWN;
        int z1=1;
        int z2=2;
        int z3=3;
        instance.createShape(type1, startX, startY, endX, endY, fill, stroke,z1);
        instance.createShape(type2, startX, startY, endX, endY, fill, stroke,z2);
        instance.createShape(type3, startX, startY, endX, endY, fill, stroke,z3);
    }

    /**
     * Test of getShapesOnCanvas method, of class CanvasClass.
     */
    @Test
    public void testGetShapesOnCanvas() {
        instance.createShape("rectangle", 10.0, 20.0,30.0, 40.0, Color.GOLD, Color.BROWN,1);
        assertNotEquals(null, instance.getShapesOnCanvas()); 
        assertEquals(12, instance.getShapesOnCanvas().size()); 
        instance.createShape("ellipse", 10.0, 20.0,30.0, 40.0, Color.GOLD, Color.ALICEBLUE,1);
        assertEquals(13, instance.getShapesOnCanvas().size()); 
        
    } 

    /**
     * Test of getSelectedShapes method, of class CanvasClass.
     */
    @Test
    public void testGetSelectedShapes() {
        assertEquals(0, instance.getSelectedShapes().size());

    }

    /**
     * Test of getNewShape method, of class CanvasClass.
     */
    @Test
    public void testGetNewShape() {
        instance.createShape("ellipse", 10, 20, 30, 40, Color.GOLD, Color.BROWN, 10);
        ShapeInterface expResult1=creator.createShape("ellipse", 10, 20, Color.GOLD, Color.BROWN, 30, 40, 10);
        ShapeInterface result1 = instance.getNewShape();
        assertEquals(expResult1.getX(), 0.000000001,result1.getX());
        assertEquals(expResult1.getY(), 0.000000001,result1.getY());
        assertEquals(expResult1.getZ(), 0.000000001,result1.getZ());
        instance.createShape("rectangle", 30, 40, 50, 60, Color.GOLD, Color.BROWN, 11);
        ShapeInterface expResult2=creator.createShape("rectangle", 10, 20, Color.RED, Color.YELLOW, 30, 40, 10);
        ShapeInterface result2 = instance.getNewShape();
        assertEquals(expResult2.getX(), 0.000000001,result2.getX());
        assertEquals(expResult2.getY(), 0.000000001,result2.getY());
        assertEquals(expResult2.getZ(), 0.000000001,result2.getZ());
    }

    /**
     * Test of getCanvas method, of class CanvasClass.
     */
    @Test
    public void testGetCanvas() {
        assertTrue(instance.getCanvas() instanceof Pane);

    }

    /**
     * Test of getClipboard method, of class CanvasClass.
     */
    @Test
    public void testGetClipboard() {
        assertTrue(instance.getClipboard() instanceof HashMap);
    } 

    /**
     * Test of setClipboard method, of class CanvasClass.
     */
    @Test
    public void testSetClipboard() {
        HashMap<Shape, ShapeInterface> clipboard = new HashMap();
        instance.setClipboard(clipboard);
        assertTrue(instance.getClipboard() instanceof HashMap);
    }

    /**
     * Test of clearClipboard method, of class CanvasClass.
     */
    @Test
    public void testClearClipboard() {
        instance.getClipboard().clear();
        assertTrue(instance.getClipboard().size()==0);
    }

    /**
     * Test of executeCommand method, of class CanvasClass.
     */
    @Test
    public void testExecuteCommand() {
        try {
            HashMap<Shape,ShapeInterface> context= new HashMap<>();
            String types[] = {"ellipse","text","rectangle","line"};
            ArrayList<Group> selected = new ArrayList<>();
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                context.put(s.getShape(), s);
            }
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(instance, selected);
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(instance, context);
            CommandHistory commandHistory = new CommandHistory();
            Field commandHistoryField = CanvasClass.class.getDeclaredField("commandHistory");
            commandHistoryField.setAccessible(true);
            commandHistoryField.set(instance, commandHistory);
            Command command1 = new CutCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command1);
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, context.size());
            assertEquals(5, instance.getClipboard().size());
            Command command2 = new CopyCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command2);
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, context.size());
            assertEquals(0, instance.getClipboard().size());
            Command command3 = new PasteCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command3);
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, context.size());
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                context.put(s.getShape(), s);
            }
            command2 = new CopyCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command2);
            assertFalse(commandHistory.isEmpty());
            assertEquals(5, context.size());
            assertEquals(5, instance.getClipboard().size());
            instance.executeCommand(command3);
            assertFalse(commandHistory.isEmpty());
            assertEquals(10, context.size());
            selected.clear();
            for(Shape s : instance.getShapesOnCanvas().keySet()){
                selected.add(new Group(s,instance.getShapesOnCanvas().get(s).getBoundingBox().getBox()));
                context.put(s,instance.getShapesOnCanvas().get(s));
            }
            Command command4 = new MoveCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command4);
            assertFalse(commandHistory.isEmpty());
            assertEquals(10, context.size());
            Command command5 = new CutCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command5);
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, context.size());
            selected.clear();
            instance.createShape("rectangle", 0, 0, 0, 0, Color.GOLD, Color.BROWN);
            Command command6 = new InsertCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command6);
            assertFalse(commandHistory.isEmpty());
            assertEquals(1, context.size());
            ArrayList<ShapeInterface> util = new ArrayList<>();
            util.addAll(instance.getShapesOnCanvas().values());
            selected.add(new Group(util.get(0).getShape(),util.get(0).getBoundingBox().getBox()));
            Command command7 = new FontSizeCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command7);
            assertFalse(commandHistory.isEmpty());
            assertEquals(1, context.size());
            Command command8 = new ResizeCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command8);
            assertFalse(commandHistory.isEmpty());
            assertEquals(1, context.size());
            Command command9 = new DeleteCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command9);
            Command command10 = new SendBackwardCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command6);
            Command command11 = new SendForwardCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command7);
            Command command12 = new SendToBackCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command8);
            Command command13 = new SendToFrontCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command9);
            assertFalse(this.instance.historyIsEmpty());
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of undoCommand method, of class CanvasClass.
     */
    @Test
    public void testUndoCommand() {
        try {
            HashMap<Shape,ShapeInterface> context= new HashMap<>();
            String types[] = {"ellipse","text","rectangle","line"};
            ArrayList<Group> selected = new ArrayList<>();
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                context.put(s.getShape(), s);
            }
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(instance, selected);
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(instance, context);
            Command command1 = new CutCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command1);
            this.instance.undoCommand();
            Command command2 = new CopyCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command2);
            this.instance.undoCommand();
            Command command3 = new PasteCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command3);
            this.instance.undoCommand();
            Command command4 = new MoveCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command4);
            this.instance.undoCommand();
            Command command5 = new CutCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command5);
            this.instance.undoCommand();
            instance.createShape("rectangle", 0, 0, 0, 0, Color.GOLD, Color.BROWN);
            Command command6 = new InsertCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command6);
            this.instance.undoCommand();
            this.instance.undoCommand();
            Command command7 = new FontSizeCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command7);
            this.instance.undoCommand();
            Command command8 = new ResizeCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command8);
            this.instance.undoCommand();
            Command command9 = new DeleteCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command9);
            this.instance.undoCommand();
            Command command10 = new SendBackwardCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command6);
            this.instance.undoCommand();
            Command command11 = new SendForwardCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command7);
            this.instance.undoCommand();
            Command command12 = new SendToBackCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command8);
            this.instance.undoCommand();
            Command command13 = new SendToFrontCommand(instance,new Receiver(context,instance.getSelectedShapes(),instance));
            instance.executeCommand(command9);
            instance.undoCommand();
            assertTrue(this.instance.historyIsEmpty());
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of historyIsEmpty method, of class CanvasClass.
     */
    @Test
    public void testHistoryIsEmpty() {
        boolean result = instance.historyIsEmpty();
        assertTrue( result);
    }

    /**
     * Test of deselectAll method, of class CanvasClass.
     */
    @Test
    public void testDeselectAll() {
        instance.deselectAll();
        assertTrue(instance.getSelectedShapes().isEmpty());
    }
    /**
     * Test of deselectAll method, of class CanvasClass.
     */
    @Test
    public void testReset() {
        try {
            HashMap<Shape,ShapeInterface> context= new HashMap<>();
            String types[] = {"ellipse","text","rectangle","line"};
            ArrayList<Group> selected = new ArrayList<>();
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                context.put(s.getShape(), s);
            }
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(instance, selected);
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(instance, context);
            CommandHistory commandHistory = new CommandHistory();
            Field commandHistoryField = CanvasClass.class.getDeclaredField("commandHistory");
            commandHistoryField.setAccessible(true);
            commandHistoryField.set(instance, commandHistory);
            assertFalse(instance.getSelectedShapes().isEmpty());
            assertFalse(instance.getShapesOnCanvas().isEmpty());
            instance.reset();
            assertTrue(instance.getSelectedShapes().isEmpty());
            assertTrue(instance.getShapesOnCanvas().isEmpty());
            assertTrue(commandHistory.isEmpty());
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Test of setSelected method getSelected method ,and  of class CanvasClass.

     */
    @Test
    public void testSetGetSelected() {
        Tool item = new RectangleTool(instance.getCanvas());
        instance.setSelected(item);
        Tool item2=instance.getSelected();
        assertEquals(item.getBorderColor(),item2.getBorderColor());
        assertEquals(item.getClass(),item2.getClass());
        assertEquals(item.getInteriorColor(),item2.getInteriorColor());

    }

    /**
     * Test of getBorderColor and setBorderColor method, of class CanvasClass.
     */
    @Test
    public void testGetSetBorderColor() {
        instance.setBorderColor(Color.GREEN);
        Color expResult = Color.GREEN;
        Color result = instance.getBorderColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInteriorColor method, of class CanvasClass.
     */
    @Test
    public void testGetSetInteriorColor() {
        instance.setInteriorColor(Color.GREEN);
        Color expResult = Color.GREEN;
        Color result = instance.getInteriorColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class CanvasClass.
     */
    @Test
    public void testUpdate_String_Color() {
        instance.update("INTERIOR_COLOR",Color.BEIGE);
        assertEquals(Color.BEIGE,instance.getInteriorColor());
        instance.update("BORDER_COLOR",Color.ORANGE);
        assertEquals(Color.ORANGE,instance.getBorderColor());
    }

    /**
     * Test of update method, of class CanvasClass.
     */
    @Test
    public void testUpdate_String() {
        try {
            HashMap<Shape,ShapeInterface> context= new HashMap<>();
            String types[] = {"ellipse","text","rectangle","line"};
            ArrayList<Group> selected = new ArrayList<>();
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                context.put(s.getShape(), s);
            }
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(instance, selected);
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(instance, context);
            CommandHistory commandHistory = new CommandHistory();
            Field commandHistoryField = CanvasClass.class.getDeclaredField("commandHistory");
            commandHistoryField.setAccessible(true);
            commandHistoryField.set(instance, commandHistory);
            assertTrue(commandHistory.isEmpty());
            instance.update("COPY");
            assertTrue(commandHistory.isEmpty());
            instance.update("UNDO");
            assertTrue(commandHistory.isEmpty());
            assertEquals(5, instance.getShapesOnCanvas().size());
            instance.update("CUT");
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, instance.getShapesOnCanvas().size());
            assertEquals(5, instance.getClipboard().size());
            instance.update("UNDO");
            assertTrue(commandHistory.isEmpty());
            assertEquals(5, instance.getShapesOnCanvas().size());
            instance.update("PASTE");
            assertFalse(commandHistory.isEmpty());
            assertEquals(10, instance.getShapesOnCanvas().size());
            assertEquals(5, instance.getClipboard().size());
            instance.update("UNDO");
            assertTrue(commandHistory.isEmpty());
            assertEquals(5, instance.getShapesOnCanvas().size());
            for(Shape s : instance.getShapesOnCanvas().keySet()){
                selected.add(new Group(s,instance.getShapesOnCanvas().get(s).getBoundingBox().getBox()));
                context.put(s,instance.getShapesOnCanvas().get(s));
            }
            instance.update("DELETE");
            assertFalse(commandHistory.isEmpty());
            assertEquals(0, instance.getShapesOnCanvas().size());
            instance.update("UNDO");
            assertTrue(commandHistory.isEmpty());
            assertEquals(5, instance.getShapesOnCanvas().size());
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Test of setZoom method, of class CanvasClass.
     */
    @Test
    public void testSetZoom(){
        try {
            double value = Math.random();
            double iPX = Math.random();
            double iPY = Math.random();
            Field initialPaneX = CanvasClass.class.getDeclaredField("initialPaneX");
            initialPaneX.setAccessible(true);
            initialPaneX.set(instance,iPX);
            Field initialPaneY = CanvasClass.class.getDeclaredField("initialPaneY");
            initialPaneY.setAccessible(true);
            initialPaneY.set(instance,iPY);
            instance.setZoom(value);
            
            assertEquals(value, ((Scale)instance.getCanvas().getTransforms().get(0)).getX()/iPX, 0.001);
            assertEquals(value, ((Scale)instance.getCanvas().getTransforms().get(0)).getY()/iPY, 0.001);

        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Test of setFontSize method, of class CanvasClass.
     */
    @Test
    public void testSetFontSize(){
        try {
            int fontSize = (int)Math.random()+1;
            String types[] = {"ellipse","text","rectangle","line"};
            ArrayList<Group> selected = new ArrayList<>();
            HashMap<Shape, ShapeInterface> shapesOnCanvas = new HashMap<>();
            for(int i = 0; i<5; i++){
                ShapeInterface s = this.creator.createShape(types[(i==4 ? 1 : ((int)Math.random()%4))], Math.random(), Math.random(), Color.BLUEVIOLET, Color.POWDERBLUE, Math.random(), Math.random(), (int)Math.random());
                selected.add(new Group(s.getShape(),s.getBoundingBox().getBox()));
                shapesOnCanvas.put(s.getShape(), s);
            }
            Field selectedShapesField = CanvasClass.class.getDeclaredField("selectedShapes");
            selectedShapesField.setAccessible(true);
            selectedShapesField.set(instance, selected);
            Field shapesOnCanvasField = CanvasClass.class.getDeclaredField("shapesOnCanvas");
            shapesOnCanvasField.setAccessible(true);
            shapesOnCanvasField.set(instance, shapesOnCanvas);
            instance.setFontSize(fontSize);
            assertEquals(fontSize, instance.getFontSize());
            for(int i = 0; i<selected.size(); i++){
                if(instance.getSelectedShapes().get(i).getChildren().get(0) instanceof Text){
                    if(((Text)instance.getSelectedShapes().get(i).getChildren().get(0)).getFont().getSize() != instance.getFontSize()){

                        fail("Set font size not working for selected text");
                    }
                }
            }
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanvasClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}

