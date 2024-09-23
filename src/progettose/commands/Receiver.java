package progettose.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import progettose.application.CanvasClass;
import progettose.shapes.creators.ShapeCreator;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.ShapeZComparator;
import progettose.strategy.ResizeADStrategy;
import progettose.strategy.ResizeASStrategy;
import progettose.strategy.ResizeBDStrategy;
import progettose.strategy.ResizeBSStrategy;
import progettose.strategy.ResizeContext;

/**
 * Receiver class for all the concrete commands. It has methods that invoke
 * methods in the controller for adding, deleting and modifying the shapes.
 *
 */
public class Receiver {

    private Map<Shape, ShapeInterface> prevContext = new HashMap();
    private ArrayList<Group> selectedShapes;
    private CanvasClass app;

    private double mouseX;
    private double mouseY;
    private boolean getBigCircle;
    private boolean checklineab;
    private double minimumX;
    private double minimumY;
    private double maximumX;
    private double maximumY;
    private int exclusiveVertex = 0;
    private boolean exclusiveMoveResize = true;

    /**
     * Class constructor.
     *
     * @param prevContext, current state of the canvas.
     * @param selectedShapes, list of shapes on the canvas, if any, on which the
     * command is executed.
     * @param app, controller.
     */
    public Receiver(Map<Shape, ShapeInterface> prevContext, ArrayList<Group> selectedShapes, CanvasClass app) {
        this.prevContext.putAll(prevContext);

        this.selectedShapes = selectedShapes;
        this.app = app;
    }

    /**
     * Method that returns the previous context saved when created the receiver.
     *
     * @return a Map<Shape, ShapeInterface>.
     */
    public Map<Shape, ShapeInterface> getPrevContext() {
        return prevContext;
    }

    /**
     * Method for setting the a new context in the prevContext field; used by
     * the undo operation in the Command Class
     *
     * @param prevContext
     */
    public void setPrevContext(Map<Shape, ShapeInterface> prevContext) {
        this.prevContext = prevContext;
        ArrayList<ShapeInterface> temp = new ArrayList();
        for (ShapeInterface a : app.getShapesOnCanvas().values()) {
            temp.add(a);
        }
        for (ShapeInterface a : temp) {
            this.deleteShape(a);
        }
        temp.clear();
        for (ShapeInterface a : this.prevContext.values()) {
            temp.add(a);
        }
        temp.sort(new ShapeZComparator());
        for (ShapeInterface a : temp) {
            this.addShape(a);
        }
    }

    /**
     * Method for getting the list of selected shapes.
     *
     * @return an ArrayList<Group>.
     */
    public ArrayList<Group> getSelectedShape() {
        return selectedShapes;
    }

    /**
     * Method for adding a shape to the canvas, it invokes the addShape of the
     * controller. It also add the listener for managing the selection,movement and resizing of the shape.
     *
     * @param shape to add to the canvas.
     */
    public void addShape(ShapeInterface shape) {
        Group group = new Group(shape.getShape());
        this.app.addToCanvas(shape);
        HashMap<Shape, ShapeInterface> prevContext = this.app.getShapesOnCanvas();
        prevContext.put(shape.getShape(), shape);

        group.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getButton() == MouseButton.PRIMARY && e.isControlDown() && selectedShapes.contains(group)) {
                    group.getChildren().remove(group.getChildren().get(1));

                    selectedShapes.remove(group);

                } else if (e.isControlDown() && this.app.getSelected() == null) {
                    selectedShapes.add(group);
                    group.getChildren().add((Shape) prevContext.get((Shape) group.getChildren().get(0)).getBoundingBox().getBox());
                } else if (this.app.getSelected() == null) {
                    for (Group a : selectedShapes) {
                        a.getChildren().remove(a.getChildren().get(1));

                    }
                    selectedShapes.clear();
                    selectedShapes.add(group);
                    group.getChildren().add(prevContext.get((Shape) group.getChildren().get(0)).getBoundingBox().getBox());

                }

            }
        });

        /*
        This listener setup some variable needed for the movement and resize, mouseX and mouseY are the current clicked coordinates on the last group
        clicked by the user. The variable getBigCircle is setted to false, because the resize is not started yet and the vertex circle to control the
        resize needs to be small. It also set the variable for manage the starting coordinates of the group before movement or resize on the canvas.
        Furthermore it check if the group contains a line, if it's true it checks its orientation and set the checklineab variable
         */
        group.setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            getBigCircle = false;

            minimumX = this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getX();
            minimumY = this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getY();
            maximumX = this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getX() + this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getWidth();
            maximumY = this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getY() + this.app.getShapesOnCanvas().get(group.getChildren().get(0)).getHeight();

            checklineab = ((group.getChildren().get(0) instanceof Line) && ((Line) (group.getChildren().get(0))).getEndX() > ((Line) (group.getChildren().get(0))).getStartX() && ((Line) (group.getChildren().get(0))).getEndY() > ((Line) (group.getChildren().get(0))).getStartY()) || ((shape.getShape() instanceof Line) && ((Line) (group.getChildren().get(0))).getEndX() < ((Line) (group.getChildren().get(0))).getStartX() && ((Line) (group.getChildren().get(0))).getEndY() < ((Line) (group.getChildren().get(0))).getStartY());
        });

        /*
        This listener manage both the move and the resize action. First of all it checks if the group is selected by clicking the left-button on the mouse,
        then he create the circle on the four vertex of the group, if the resize is not started the circle is small, if the resize is started it gets bigger
        for help the user to not lose the resize action due to fast movements. At the start it checks if the mouse click is in one of the four vertex of the group,
        if it's true it checks which of the vertex is selected and it resize the shape based on the drag event. This phase is managed by removing the current shape selected 
        from the group, and adding the changed one. If the resize is started, the exlusiveVertex variable is used to mantaing the same vertex all the time so that during the resize just
        that vertex is enabled to be changed. This variable is also used to check if the resize is not already started so that the ResizeCommand is called
        just one time. Instead, if no vertex is selected, checked using the eclusiveMoveResize variable, this listener manage the move action of the groups.
        It calculate the offset X and Y coordinate based on the current group selected and the clicked mouse, then it change the inner coordinates of shapes and boxes
        in the groups to bring them in the right position. This listener also manage the changing of the cursor based on the action.
         */
        group.setOnMouseDragged(e -> {

            if (group.getChildren().size() == 2 && e.getButton().equals(MouseButton.PRIMARY)) {
                ShapeCreator shapeCreator = new ShapeCreator();

                double circleSize = 0.1 * (group.getLayoutBounds().getHeight() < group.getLayoutBounds().getWidth() ? group.getLayoutBounds().getHeight() : group.getLayoutBounds().getWidth());
                if (getBigCircle == false) {
                    circleSize = 0.1 * (group.getLayoutBounds().getHeight() < group.getLayoutBounds().getWidth() ? group.getLayoutBounds().getHeight() : group.getLayoutBounds().getWidth());
                } else {
                    circleSize = this.app.getCanvas().getWidth();
                }
                Circle cerchioBd = new Circle(group.getBoundsInLocal().getMaxX(), group.getBoundsInLocal().getMaxY(), circleSize);
                Circle cerchioBs = new Circle(group.getBoundsInLocal().getMinX(), group.getBoundsInLocal().getMaxY(), circleSize);
                Circle cerchioAs = new Circle(group.getBoundsInLocal().getMinX(), group.getBoundsInLocal().getMinY(), circleSize);
                Circle cerchioAd = new Circle(group.getBoundsInLocal().getMaxX(), group.getBoundsInLocal().getMinY(), circleSize);
                ResizeContext context = new ResizeContext();
                if (cerchioBd.contains(e.getX(), e.getY()) && (exclusiveVertex == 0 || exclusiveVertex == 1) && (!((group.getChildren().get(0)) instanceof Line) || (group.getChildren().get(0)) instanceof Line && checklineab == true)) {
                    context.setStrategy(new ResizeBDStrategy());
                    exclusiveMoveResize = false;
                } else if (cerchioBs.contains(e.getX(), e.getY()) && (exclusiveVertex == 0 || exclusiveVertex == 2) && (!((group.getChildren().get(0)) instanceof Line) || (group.getChildren().get(0)) instanceof Line && checklineab == false)) {
                    context.setStrategy(new ResizeBSStrategy());
                    exclusiveMoveResize = false;
                } else if (cerchioAs.contains(e.getX(), e.getY()) && (exclusiveVertex == 0 || exclusiveVertex == 3) && (!((group.getChildren().get(0)) instanceof Line) || (group.getChildren().get(0)) instanceof Line && checklineab == true)) {
                    context.setStrategy(new ResizeASStrategy());
                    exclusiveMoveResize = false;
                } else if (cerchioAd.contains(e.getX(), e.getY()) && (exclusiveVertex == 0 || exclusiveVertex == 4) && (!((group.getChildren().get(0)) instanceof Line) || (group.getChildren().get(0)) instanceof Line && checklineab == false)) {
                    context.setStrategy(new ResizeADStrategy());
                    exclusiveMoveResize = false;
                }
                if (exclusiveMoveResize == false) {
                    if (!(group.getChildren().get(0) instanceof Text)) {
                        group.setOpacity(0.5);
                        getBigCircle = true;
                        if (exclusiveVertex == 0) {
                            this.app.executeCommand(new ResizeCommand(this.app, new Receiver(prevContext, selectedShapes, this.app)));
                        }
                        exclusiveVertex = context.getExclusivity();
                        group.setCursor(context.getCursor());
                        exclusiveMoveResize = false;
                        ShapeInterface s = null;
                        context.setContext(minimumX, maximumX, minimumY, maximumY, e.getX(), e.getY());
                        double minX = context.getMinX();
                        double minY = context.getMinY();
                        double maxX = context.getMaxX();
                        double maxY = context.getMaxY();
                        if (shape.getShape() instanceof Ellipse) {
                            s = shapeCreator.createShape("ellipse", minX < 0 ? 0 : minX, minY < 0 ? 0 : minY, Color.web(shape.getShape().getFill().toString()), Color.web(shape.getShape().getStroke().toString()), maxX > app.getCanvas().getWidth() ? app.getCanvas().getWidth() : maxX, maxY > app.getCanvas().getHeight() ? app.getCanvas().getHeight() : maxY, shape.getZ());
                        } else if (shape.getShape() instanceof Rectangle) {
                            s = shapeCreator.createShape("rectangle", minX < 0 ? 0 : minX, minY < 0 ? 0 : minY, Color.web(shape.getShape().getFill().toString()), Color.web(shape.getShape().getStroke().toString()), maxX > app.getCanvas().getWidth() ? app.getCanvas().getWidth() : maxX, maxY > app.getCanvas().getHeight() ? app.getCanvas().getHeight() : maxY, shape.getZ());
                        } else if (shape.getShape() instanceof Line) {
                            double lineMinX = context.getLineMinX();
                            double lineMinY = context.getLineMinY();
                            double lineMaxX = context.getLineMaxX();
                            double lineMaxY = context.getLineMaxY();
                            if (lineMaxX < 0) {
                                lineMaxX = 0;
                            }
                            if (lineMaxY < 0) {
                                lineMaxY = 0;
                            }
                            if (lineMinX < 0) {
                                lineMinX = 0;
                            }
                            if (lineMinY < 0) {
                                lineMinY = 0;
                            }
                            s = shapeCreator.createShape("line", lineMinX > app.getCanvas().getWidth() ? app.getCanvas().getWidth() : lineMinX, lineMinY > app.getCanvas().getHeight() ? app.getCanvas().getHeight() : lineMinY, Color.web(shape.getShape().getStroke().toString()), Color.web(shape.getShape().getStroke().toString()), lineMaxX > app.getCanvas().getWidth() ? app.getCanvas().getWidth() : lineMaxX, lineMaxY > app.getCanvas().getHeight() ? app.getCanvas().getHeight() : lineMaxY, shape.getZ());
                        } else if (shape.getShape() instanceof Text) {
                            s = shapeCreator.createShape(((Text) shape.getShape()).getText(), context.getMinX(), context.getMinY(), Color.BLUEVIOLET, Color.POWDERBLUE, ((Text) shape.getShape()).getFont().getSize(), context.getMaxY(), shape.getZ());
                        }
                        for (Group a : selectedShapes) {
                            a.getChildren().remove(1);
                        }
                        selectedShapes.clear();
                        prevContext.remove((Shape) group.getChildren().get(0));

                        group.getChildren().remove(0);
                        group.getChildren().add(s.getShape());

                        selectedShapes.add(group);
                        prevContext.put(s.getShape(), s);
                        group.getChildren().add(prevContext.get((Shape) group.getChildren().get(0)).getBoundingBox().getBox());
                    }
                } else if (exclusiveMoveResize == true) {
                    group.setCursor(Cursor.HAND);

                    if (exclusiveVertex == 0) {
                        this.app.executeCommand(new MoveCommand(this.app, new Receiver(prevContext, selectedShapes, this.app)));
                    }
                    exclusiveVertex = 5;

                    double coefficient = app.getCanvas().getTransforms().isEmpty() ? 1 : 1 / (((Scale) app.getCanvas().getTransforms().get(0)).getX() / app.getCanvas().getScaleX());
                    double offsetX = (e.getSceneX() - mouseX) * coefficient;
                    double offsetY = (e.getSceneY() - mouseY) * coefficient;

                    for (Group a : selectedShapes) {
                        ShapeInterface temp = (ShapeInterface) prevContext.get(a.getChildren().get(0));
                        if (temp.getBoundingBox().getXChange() + offsetX >= 0 && temp.getBoundingBox().getXChange() + offsetX + temp.getBoundingBox().getBox().getWidth() < this.app.getCanvas().getWidth()) {
                            temp.setX(offsetX + temp.getX());
                            temp.getBoundingBox().setXChange(temp.getBoundingBox().getXChange() + offsetX);
                        }
                        if (temp.getBoundingBox().getYChange() + offsetY >= 0 && temp.getBoundingBox().getYChange() + offsetY + temp.getBoundingBox().getBox().getHeight() < this.app.getCanvas().getHeight()) {
                            temp.setY(offsetY + temp.getY());
                            temp.getBoundingBox().setYChange(temp.getBoundingBox().getYChange() + offsetY);
                        }
                        a.setOpacity(0.5);
                    }

                    mouseX = e.getSceneX();
                    mouseY = e.getSceneY();
                }
            }
        });

        /*
        This listener is used to reset the cursor after a movement or a resize action. It also reset the opacity previously setted in both actions,
        and it resets the variable used for the mutual exclusive.
         */
        group.setOnMouseReleased(e -> {
            if (this.app.getSelected() == null) {
                group.setCursor(Cursor.DEFAULT);
            }
            for (Group a : selectedShapes) {
                a.setOpacity(1);
            }
            exclusiveMoveResize = true;
            exclusiveVertex = 0;

        });

    }

    /**
     * Method for deleting a shape form the canvas, it invokes the deleteShape
     * of the controller.
     *
     * @param shape to delete form the canvas.
     */
    public void deleteShape(ShapeInterface shape) {
        this.app.getShapesOnCanvas().remove(shape.getShape());
        selectedShapes.remove((Group) shape.getShape().getParent());
        this.app.removeFromCanvas(shape);
    }

    /**
     * Method for modifying a shape on the canvas, it invokes the
     * changeInteriorColor or changeBorderColor of the controller.
     *
     * @param shape to modify.
     * @param mode that specify what to change, it can be CHANGE_INTERIOR or
     * CAHNGE_BORDER.
     */
    public void modifyShape(ShapeInterface shape, String mode) {
        switch (mode) {
            case "CHANGE_INTERIOR": {
                shape.getShape().setFill(this.app.getInteriorColor());
                break;
            }
            case "CHANGE_BORDER": {
                shape.getShape().setStroke(this.app.getBorderColor());
                break;
            }
        }
    }

     /**
     * Method for add a shape wrapped by the ShapeInterface in a group of the canvas
     *
     * @param shape to be added
     * @param group where the shape will be added
     */
    public void addInGroup(ShapeInterface shape, Group group) {
        group.getChildren().add(shape.getShape());
        this.app.getShapesOnCanvas().put(shape.getShape(), shape);
        group.getChildren().add(this.app.getShapesOnCanvas().get((Shape) group.getChildren().get(0)).getBoundingBox().getBox());
    }
/**
     * Method for clearing a group
     *
     * @param group The group to be cleared
     */
    public void removeFromGroup(Group group) {
        this.app.getShapesOnCanvas().remove((Shape) group.getChildren().get(0));
        group.getChildren().remove(1);
        group.getChildren().remove(0);
    }
}
