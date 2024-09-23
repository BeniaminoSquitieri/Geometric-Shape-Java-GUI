package progettose.commands;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import progettose.application.*;
import progettose.shapes.wrappers.ShapeInterface;

/**
 * Concrete command for changing the interior color of all selected shapes on
 * the canvas.
 *
 */
public class ChangeInteriorCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public ChangeInteriorCommand(CanvasClass app, Receiver receiver) {
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
        ArrayList<Group> selectedshapes = this.receiver.getSelectedShape();
        Map<Shape, ShapeInterface> shapesoncanvas = this.receiver.getPrevContext();
        for (Group a : selectedshapes) {
            if (!(a.getChildren().get(0) instanceof Text)) {
                this.receiver.modifyShape(shapesoncanvas.get(a.getChildren().get(0)), "CHANGE_INTERIOR");
            }

        }
        return true;
    }
}
