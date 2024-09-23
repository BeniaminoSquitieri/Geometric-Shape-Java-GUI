package progettose.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import progettose.application.CanvasClass;
import progettose.shapes.wrappers.ShapeInterface;
import progettose.shapes.wrappers.ShapeZComparator;

/**
 * Concrete command for moving all selected shapes down a layer on the canvas.
 *
 */
public class SendBackwardCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public SendBackwardCommand(CanvasClass app, Receiver receiver) {
        super(app, receiver);
    }

    /**
     * Method for the execution of the command. It saves the previous context.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        int i = 0;
        ArrayList<ShapeInterface> fix = new ArrayList<>();
        fix.addAll(this.app.getShapesOnCanvas().values());
        fix.sort(new ShapeZComparator());
        for (ShapeInterface s : fix) {
            s.setZ(i);
            i++;
        }
        saveBackup();
        ArrayList<Group> selectedshapes = this.receiver.getSelectedShape();
        Map<Shape, ShapeInterface> shapesoncanvas = new HashMap();
        shapesoncanvas.putAll(this.receiver.getPrevContext());
        ArrayList<ShapeInterface> temp = new ArrayList();
        for (Group a : selectedshapes) {
            temp.add(shapesoncanvas.get((Shape) a.getChildren().get(0)));
        }
        for (ShapeInterface a : temp) {
            shapesoncanvas.remove(a.getShape());
        }
        for (ShapeInterface s : temp) {
            if (s.getZ() - 1 >= 0) {
                s.setZ(s.getZ() - 1);
            }
        }
        for (ShapeInterface s : temp) {
            for (ShapeInterface a : shapesoncanvas.values()) {
                if (a.getZ() == s.getZ()) {
                    a.setZ(a.getZ() + 1);
                }
            }
        }
        for (ShapeInterface a : temp) {
            shapesoncanvas.put(a.getShape(), a);
        }
        temp.clear();
        temp.addAll(shapesoncanvas.values());
        temp.sort(new ShapeZComparator());
        for (ShapeInterface s : temp) {
            this.receiver.deleteShape(s);
        }
        for (ShapeInterface s : temp) {
            this.receiver.addShape(s);
        }
        return true;
    }
}
