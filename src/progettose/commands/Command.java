package progettose.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import progettose.application.CanvasClass;
import progettose.shapes.creators.ShapeCreator;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.ShapeZComparator;

/**
 * Abstract class for command implementation, it has a reference to the invoker
 * and to the receiver and saves a backup for the Undo fuctonality.
 *
 */
public abstract class Command {

    /**
     * Reference to the invoker
     */
    protected CanvasClass app;

    /**
     * Reference to the receiver
     */
    protected Receiver receiver;

    /**
     * Backup oft the previous state of the application
     */
    protected Map<Shape, ShapeInterface> backup;

    /**
     * Class constructor, used by all its subclasses.
     *
     * @param app, command invoker
     * @param receiver, command receivers
     */
    public Command(CanvasClass app, Receiver receiver) {
        this.app = app;
        this.receiver = receiver;
        backup = new HashMap<>();
    }

    /**
     * Method that saves the current state of the application in the Backup
     * field.
     */
    public void saveBackup() {
        backup.clear();
        ShapeInterface temp = null;
        ShapeCreator shapecreator = new ShapeCreator();
        ArrayList<ShapeInterface> backupValues = new ArrayList<>();
        backupValues.addAll(receiver.getPrevContext().values());
        backupValues.sort(new ShapeZComparator());
        for (ShapeInterface a : backupValues) {
            if (a.getShape() instanceof Rectangle) {
                temp = shapecreator.createShape("rectangle", ((Rectangle) (a.getShape())).getX(), ((Rectangle) (a.getShape())).getY(), Color.web(a.getShape().getFill().toString()), Color.web(a.getShape().getStroke().toString()), ((Rectangle) (a.getShape())).getWidth() + ((Rectangle) (a.getShape())).getX(), ((Rectangle) (a.getShape())).getHeight() + ((Rectangle) (a.getShape())).getY(), a.getZ());
            } else if (a.getShape() instanceof Line) {
                temp = shapecreator.createShape("line", ((Line) (a.getShape())).getStartX(), ((Line) (a.getShape())).getStartY(), Color.web(a.getShape().getStroke().toString()), Color.web(a.getShape().getStroke().toString()), ((Line) (a.getShape())).getEndX(), ((Line) (a.getShape())).getEndY(), a.getZ());
            } else if (a.getShape() instanceof Ellipse) {
                temp = shapecreator.createShape("ellipse", ((Ellipse) (a.getShape())).getCenterX() - ((Ellipse) (a.getShape())).getRadiusX(), ((Ellipse) (a.getShape())).getCenterY() - ((Ellipse) (a.getShape())).getRadiusY(), Color.web(a.getShape().getFill().toString()), Color.web(a.getShape().getStroke().toString()), ((Ellipse) (a.getShape())).getCenterX() + ((Ellipse) (a.getShape())).getRadiusX(), ((Ellipse) (a.getShape())).getCenterY() + ((Ellipse) (a.getShape())).getRadiusY(), a.getZ());
            } else if (a.getShape() instanceof Text) {
                temp = shapecreator.createShape(((Text) a.getShape()).getText() + "/", ((Text) a.getShape()).getX(), ((Text) a.getShape()).getY(), Color.BLUEVIOLET, Color.POWDERBLUE, ((Text) a.getShape()).getFont().getSize(), ((Text) a.getShape()).getFont().getSize(), a.getZ());
            }
            backup.put(temp.getShape(), temp);
        }
    }

    /**
     * Method that revert the previous command executed.
     */
    public void undo() {
        receiver.setPrevContext(backup);
    }

    /**
     * Abstract method for the execution of the command.
     *
     * @return true if the command changed the state of the application, false
     * otherwise.
     */
    public abstract boolean execute();
}
