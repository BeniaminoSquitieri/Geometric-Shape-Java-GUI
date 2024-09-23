package progettose.commands;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.ShapeInterface;

/**
 * Concrete command for deleting all selected shapes on the canvas.
 *
 */
public class DeleteCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public DeleteCommand(CanvasClass app, Receiver receiver) {
        super(app, receiver);
    }

    /**
     * Method for the execution of the command. It saves the previous context.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        saveBackup();
        Map<Shape, ShapeInterface> shapesoncanvas = receiver.getPrevContext();
        ArrayList<Group> shapeSelected = receiver.getSelectedShape();
        ArrayList<ShapeInterface> temp = new ArrayList();
        saveBackup();
        for (Group a : shapeSelected) {
            temp.add(shapesoncanvas.get(a.getChildren().get(0)));
        }
        for (ShapeInterface a : temp) {
            receiver.deleteShape(a);
        }
        return true;
    }
}
