package progettose.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.ShapeInterface;

/**
 * Concrete command for cutting all selected shapes on the canvas.
 *
 */
public class CutCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public CutCommand(CanvasClass app, Receiver receiver) {
        super(app, receiver);
    }

    /**
     * Method for the execution of the command. It saves the selected shapes
     * into the clipboard of the invoker. It saves the previous context.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        ArrayList<Group> shapeSelected = receiver.getSelectedShape();
        Map<Shape, ShapeInterface> temp = receiver.getPrevContext();
        HashMap<Shape, ShapeInterface> clip = new HashMap();
        for (Group a : shapeSelected) {
            clip.put((Shape) a.getChildren().get(0), temp.get(a.getChildren().get(0)));
        }
        this.app.clearClipboard();
        this.app.setClipboard(clip);
        saveBackup();
        ArrayList<ShapeInterface> temp2 = new ArrayList();
        for (Group a : shapeSelected) {
            temp2.add(temp.get(a.getChildren().get(0)));
        }
        for (ShapeInterface a : temp2) {
            receiver.deleteShape(a);
        }
        return true;
    }
}
