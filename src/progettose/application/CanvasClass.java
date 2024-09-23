package progettose.application;

import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.creators.ShapeCreator;
import progettose.observers.KeyboardSubscriberInterface;
import progettose.observers.ColorSubscriberInterface;
import progettose.tool.Tool;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import progettose.commands.*;

public class CanvasClass implements ColorSubscriberInterface, KeyboardSubscriberInterface {

    private static CanvasClass instance;

    private Pane canvas; // Actual node of the interface representing our drawing area
    private double initialPaneX; // scaleX of the canvas
    private double initialPaneY; // scaleY of the canvas
    private HashMap<Shape, ShapeInterface> shapesOnCanvas; // Map of shapes on the canvas storing also the ShapeInterface that wraps it
    private ArrayList<Group> selectedShapes; // List of groups on canvas that are selected by the user
    private ShapeCreator shapeCreator; // Creator of the shapes
    private ShapeInterface newShape; // Next shape to be added
    private int amount = 0; // Z amount for the creation of shape
    private CommandHistory commandHistory; // CommandHistory for storing commands in the stack
    private HashMap<Shape, ShapeInterface> clipboard; // Clipboard of the canvas
    private Tool selectedItem; // Item selected by the user
    private ContextMenu canvasMenu; // ContextMenu associated with the canvas
    private Color borderColor; // Border Color matching the status of the border palette
    private Color interiorColor; // Interior Color matching the status of the interior palette
    private int fontSize; // Current fontSize for the Text Shapes on the canvas

    /**
    * This is a static method used to create a single instance of the CanvasClass.
    * It takes a Pane as an argument and if an instance of the CanvasClass has not been created yet, it will create one and return it.
     * @param canvas This is the first parameter that represents the javaFX node
     * @return CanvasClass it returns the instance of this class
    */
    public static CanvasClass getInstance(Pane canvas) {
        if (instance == null) {
            instance = new CanvasClass(canvas);
        }
        return instance;
    }

    /**
     * This method is the constructor of the CanvasClass.
     * This constructor initialize all the variables needed for managing the canvas, we also add a listener to check if the canvas is clicked with
     * the left-click by the user to deselect all the shapes,or with the right-click to show his context menu. If is a shape that is focused by the
     * right-click, it creates and shows his context menu with all the options: delete,cut,copy,paste,send to front,send forward, send to back, send backward
     *
     * @param canvas This is the first parameter that represents the javaFX node
     */
    private CanvasClass(Pane canvas) {
        this.canvas = canvas;
        this.initialPaneX = this.canvas.getScaleX();
        this.initialPaneY = this.canvas.getScaleY();
        this.shapesOnCanvas = new HashMap<>();
        this.selectedShapes = new ArrayList<>();
        this.shapeCreator = new ShapeCreator();
        this.commandHistory = new CommandHistory();
        this.clipboard = new HashMap<>();
        this.initialPaneX = this.canvas.getScaleX();
        this.initialPaneY = this.canvas.getScaleY();
        MenuItem paste = new MenuItem("Paste");
        this.canvasMenu = new ContextMenu(paste);
        paste.setOnAction(ev -> {
            Command r = new PasteCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this));
            this.executeCommand(r);
            amount++;
        });
        this.canvas.setOnMouseClicked(event -> {
            if ((this.selectedItem == null) && (event.getButton() == MouseButton.PRIMARY) && !event.isControlDown()) {
                boolean check = true;
                if (event.getTarget().toString().contains("Grid") || event.getTarget().toString().contains("canvas")) {
                    check = false;
                }
                if (!check) {
                    this.deselectAll();
                    this.canvasMenu.hide();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                boolean check = true;
                if (event.getTarget().toString().contains("Grid") || event.getTarget().toString().contains("canvas")) {
                    check = false;
                }

                if (!check) {
                    this.canvasMenu.show(canvas, event.getScreenX(), event.getScreenY());
                } else {
                    ContextMenu c = new ContextMenu();
                    MenuItem deleteMI = new MenuItem();
                    deleteMI.setText("Delete");
                    deleteMI.setOnAction(ev -> {
                        Command r = new DeleteCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem SendToBackMI = new MenuItem();
                    SendToBackMI.setText("Send to back");
                    SendToBackMI.setOnAction(ev -> {
                        Command r = new SendToBackCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem SendToFrontMI = new MenuItem();
                    SendToFrontMI.setText("Send to front");
                    SendToFrontMI.setOnAction(ev -> {
                        Command r = new SendToFrontCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem SendForwardMI = new MenuItem();
                    SendForwardMI.setText("Send forward");
                    SendForwardMI.setOnAction(ev -> {
                        Command r = new SendForwardCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem SendBackwardMI = new MenuItem();
                    SendBackwardMI.setText("Send backward");
                    SendBackwardMI.setOnAction(ev -> {
                        Command r = new SendBackwardCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem pasteMI = new MenuItem();
                    pasteMI.setText("Paste");
                    pasteMI.setOnAction(ev -> {
                        Command r = new PasteCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                        amount++;
                    });
                    MenuItem copyMI = new MenuItem();
                    copyMI.setText("Copy");
                    copyMI.setOnAction(ev -> {
                        Command r = new CopyCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    MenuItem cutMI = new MenuItem();
                    cutMI.setText("Cut");
                    cutMI.setOnAction(ev -> {
                        Command r = new CutCommand(this, new Receiver(getShapesOnCanvas(), selectedShapes, this));
                        executeCommand(r);
                    });
                    c.getItems().add(deleteMI);
                    c.getItems().add(cutMI);
                    c.getItems().add(copyMI);
                    c.getItems().add(pasteMI);
                    c.getItems().add(SendToFrontMI);
                    c.getItems().add(SendForwardMI);
                    c.getItems().add(SendToBackMI);
                    c.getItems().add(SendBackwardMI);
                    c.show((Node) event.getTarget(), Side.RIGHT, 5, 5);
                }
            }
        });
    }

     /**
     * This method is used to create a new Shape using the creator and the InsertCommand, it also add this shapes to the shapesOnCanvas map.
     * 
     * @param type This is the first parameter that represents the type of shapes to be created
     * @param startX This is the second parameter that represents the starting X coordinate of the shape to be created
     * @param endY This is the third parameter that represents the ending Y coordinate of the shape to be created
     * @param endX This is the fourth parameter that represents the ending X coordinate of the shape to be created
     * @param startY This is the fifth parameter that represents the starting Y coordinate of the shape to be created
     * @param fill This is the sixth parameter that represents the interior color of the shape to be created
     * @param stroke This is the seventh parameter that represents the border color of the shape to be created
     */
    public void createShape(String type, double startX, double startY, double endX, double endY, Color fill, Color stroke) {
        this.newShape = shapeCreator.createShape(type, startX, startY, fill, stroke, endX, endY, this.amount);
        this.amount++;
        this.executeCommand(new InsertCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
        this.shapesOnCanvas.put(this.newShape.getShape(), this.newShape);
    }

     /**
     * This method is used to create a new Shape using the creator and the InsertCommand, it also add this shapes to the shapesOnCanvas map.
     * 
     * @param type This is the first parameter that represents the type of shapes to be created
     * @param startX This is the second parameter that represents the starting X coordinate of the shape to be created
     * @param endY This is the third parameter that represents the ending Y coordinate of the shape to be created
     * @param endX This is the fourth parameter that represents the ending X coordinate of the shape to be created
     * @param startY This is the fifth parameter that represents the starting Y coordinate of the shape to be created
     * @param fill This is the sixth parameter that represents the interior color of the shape to be created
     * @param stroke This is the seventh parameter that represents the border color of the shape to be created
     * @param z This is the eight parameter that represents the Z coordinate of the shape to be created
     */
    public void createShape(String type, double startX, double startY, double endX, double endY, Color fill, Color stroke, int z) {
        this.newShape = shapeCreator.createShape(type, startX, startY, fill, stroke, endX, endY, z);
        this.executeCommand(new InsertCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
        this.shapesOnCanvas.put(this.newShape.getShape(), this.newShape);
        this.amount = z + 1;
        this.commandHistory.clear();
    }
    
    /**
     * @return HashMap<Shape, ShapeInterface> This returns the map of shapes on canvas
     */
    public HashMap<Shape, ShapeInterface> getShapesOnCanvas() {
        return shapesOnCanvas;
    }

     /**
     * @return ArrayList<Group>  This returns the list of selected shapes
     */
    public ArrayList<Group> getSelectedShapes() {
        return selectedShapes;
    }
    
    /**
     * @return ShapeInterface This returns the last shapes added
     */
    public ShapeInterface getNewShape() {
        return newShape;
    }

    /**
     * @return Pane This returns the javafx node canvas
     */
    public Pane getCanvas() {
        return canvas;
    }

     /**
     * @return HashMap<Shape, ShapeInterface> This returns the clipboard of the canvas containing the shapes copied
     */
    public HashMap<Shape, ShapeInterface> getClipboard() {
        return clipboard;
    }

    /**
     * 
     * @param clipboard This is the first parameter representing the clipboard to be setted on the canvas
     */
    public void setClipboard(HashMap<Shape, ShapeInterface> clipboard) {
        this.clipboard = clipboard;
    }

    /**
     * This method clear the clipboard of the canvas
     */
    public void clearClipboard() {
        this.clipboard.clear();
    }

    /** This method execute a command given, and if the command is correctly executed, it is added to the stack 
     * @param command This is the first parameter representing the command to be executed and added to the stack
     */
    public void executeCommand(Command command) {
        if (command.execute()) {
            this.commandHistory.push(command);
        }
    }

    /** This method take the last command from the stack of command.
     * Then it calls the undo method on it, restoring the previous state of the application
     */
    public void undoCommand() {
        Command command = commandHistory.pop();
        if (command != null) {
            command.undo();
        }
    }

    /**
     * @return boolean This returns True if the stack is empty, so no command is in the stack
     */
    public boolean historyIsEmpty() {
        return this.commandHistory.isEmpty();
    }

    /**
     * This method deselect all the shapes selected on the canvas, by removing their box
     */
    public void deselectAll() {
        for (Group group : this.selectedShapes) {
            group.getChildren().remove(1);
        }
        this.selectedShapes.clear();
    }

    /**
     * This method is used to reset the status of the canvas, clearing it and all the variables used to mantain his state
     */
    public void reset() {
        this.shapesOnCanvas.clear();
        this.canvas.getChildren().remove(1, this.canvas.getChildren().size());
        this.amount = 0;
        this.selectedShapes.clear();
        this.commandHistory.clear();
    }

    /**
     * 
     * @param item This is the first parameter representing the tool selected by the user (null if no tool is selected)
     */
    public void setSelected(Tool item) {
        this.selectedItem = item;
    }

    /**
     * 
     * @return Tool This returns the selected Tool
     */
    public Tool getSelected() {
        return this.selectedItem;
    }

    /**
     * 
     * @return Color This returns the border color setted
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * This method change the status of borderColor, and it change every selected shapes color according to the new one
     * calling the ChangeBorderCommand
     * @param borderColor This is the first parameter representing the borderColor to be updated
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        if (!selectedShapes.isEmpty()) {
            executeCommand(new ChangeBorderCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
        }
    }

     /**
     * 
     * @return Color This returns the interior color setted
     */
    public Color getInteriorColor() {
        return interiorColor;
    }

    /**
     * This method change the status of interiorColor, and it change every selected shapes color according to the new one
     * calling the InteriorBorderCommand
     * @param borderColor This is the first parameter representing the borderColor to be updated
     */
    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
        if (!selectedShapes.isEmpty()) {
            executeCommand(new ChangeInteriorCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
        }
    }

    /**
     * This method receive update from the Event Manager of two events and based on the one that is updated, it change the status
     * of the InteriorColor or BorderColor
     * @param event This is the first parameter representing the update event called by the Event Manager
     * @param color This is the second parameter representing the update color received by the Event Manager
     */
    @Override
    public void update(String event, Color color) {
        switch (event) {
            case "INTERIOR_COLOR":
                this.setInteriorColor(color);
                break;
            case "BORDER_COLOR":
                this.setBorderColor(color);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * This method receive update from the Event Manager of events: COPY,CUT,PASTE,DELETE,UNDO, then it call the correct command
     * @param eventType This is the first parameter representing the update event called by the Event Manager
     * 
     */
    @Override
    public void update(String eventType) {
        switch (eventType) {
            case "COPY":
                executeCommand(new CopyCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
                break;
            case "CUT":
                executeCommand(new CutCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
                break;
            case "PASTE":
                executeCommand(new PasteCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
                break;
            case "DELETE":
                executeCommand(new DeleteCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
                break;
            case "UNDO":
                undoCommand();
                break;
            default:
                throw new AssertionError();
        }
    }

     /**
     * This method scale the canvas according to the Zoom selected
     * 
     * @param newZoom This is the first parameter that represent the Zoom applied to the canvas
     */
    public void setZoom(double newZoom) {
        Scale newScale = new Scale();
        newScale.setX(this.initialPaneX * newZoom);
        newScale.setY(this.initialPaneY * newZoom);
        newScale.setPivotX(canvas.getScaleX());
        newScale.setPivotY(canvas.getScaleY());
        canvas.getTransforms().clear();
        canvas.getTransforms().add(newScale);
    }

    /**
     * This method update the FontSize of text selected and the status of the fontSize variable
     * 
     * @param fontSize This is the first parameter representing the fontSize that will be updated to all the selected Text on the canvas
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        this.executeCommand(new FontSizeCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
    }

    /**
     * 
     * @return int This return the current fontSize 
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * This method call the RotateCommand to execute a rotate on the selected shapes on canvas
     * 
     */
    public void rotate() {
        this.executeCommand(new RotateCommand(this, new Receiver(shapesOnCanvas, selectedShapes, this)));
    }

    /**
     * This method remove the group of the shape wrapped in the ShapeInterface 
     * 
     * @param shape This is the first parameter representing the ShapeInterface that wraps the shape on the canvas
     */
    public void removeFromCanvas(ShapeInterface shape) {
        canvas.getChildren().remove(shape.getShape().getParent());
    }

    /**
     * This method add a group of the shape wrapped in the ShapeInterface 
     * 
     * @param shape This is the first parameter representing the ShapeInterface that wraps the shape on the canvas
     */
    public void addToCanvas(ShapeInterface shape) {
        canvas.getChildren().add(shape.getShape().getParent());
    }
}
