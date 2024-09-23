package progettose.commands;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import progettose.application.CanvasClass;
import progettose.shapes.creators.ShapeCreator;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.ShapeZComparator;

/**
 * Concrete command for pasting previously copied or cutted shapes on the
 * canvas.
 *
 */
public class PasteCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public PasteCommand(CanvasClass app, Receiver receiver) {
        super(app, receiver);
    }

    /**
     * Method for the execution of the command. It use the clipboard for getting
     * the shapes to add to the canvas. It saves the previous context.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        saveBackup();
        ShapeCreator shapecreator = new ShapeCreator();
        ArrayList<ShapeInterface> selectedShapes = new ArrayList<>();
        selectedShapes.addAll(this.app.getClipboard().values());
        selectedShapes.sort(new ShapeZComparator());
        for (ShapeInterface a : selectedShapes) {
            ShapeInterface temp = null;
            if (a.getShape() instanceof Rectangle) {
                temp = shapecreator.createShape("rectangle", a.getX() + 10, a.getY() + 10, Color.web(a.getShape().getFill().toString()), Color.web(a.getShape().getStroke().toString()), a.getX() + a.getWidth() + 10, a.getY() + a.getHeight() + 10, a.getZ() + this.backup.size());
            } else if (a.getShape() instanceof Line) {
                temp = shapecreator.createShape("line", a.getX() + 10, a.getY() + 10, Color.web(a.getShape().getStroke().toString()), Color.web(a.getShape().getStroke().toString()), a.getX() + a.getWidth() + 10, a.getY() + a.getHeight() + 10, a.getZ() + this.backup.size());
            } else if (a.getShape() instanceof Ellipse) {
                temp = shapecreator.createShape("ellipse", a.getX() + 10, a.getY() + 10, Color.web(a.getShape().getFill().toString()), Color.web(a.getShape().getStroke().toString()), a.getX() + a.getWidth() + 10, a.getY() + a.getHeight() + 10, a.getZ() + this.backup.size());
            } else if (a.getShape() instanceof Text) {
                temp = shapecreator.createShape(((Text) a.getShape()).getText() + "/", ((Text) a.getShape()).getX() + 10, ((Text) a.getShape()).getY() + 10, Color.BLUEVIOLET, Color.POWDERBLUE, ((Text) a.getShape()).getFont().getSize(), ((Text) a.getShape()).getFont().getSize(), a.getZ() + this.backup.size());
            }
            this.receiver.addShape(temp);
        }
        return true;
    }
}
