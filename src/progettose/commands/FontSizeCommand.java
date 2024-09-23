package progettose.commands;

import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import progettose.application.CanvasClass;
/**
 * Concrete command for changing the FontSize of all selected text on the canvas.
 *
 */
public class FontSizeCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public FontSizeCommand(CanvasClass app, Receiver receiver) {
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
        for (Group a : this.app.getSelectedShapes()) {
            Shape s = (Shape) a.getChildren().get(0);
            if (s instanceof Text) {
                ((Text) s).setFont(new Font(((Text) s).getFont().getName(), (double) this.app.getFontSize()));
                a.getChildren().remove(1);
                this.app.getShapesOnCanvas().get(s).getBoundingBox().getBox().setWidth(this.app.getShapesOnCanvas().get(s).bound().getBox().getWidth());
                this.app.getShapesOnCanvas().get(s).getBoundingBox().getBox().setHeight(this.app.getShapesOnCanvas().get(s).bound().getBox().getHeight());
                this.app.getShapesOnCanvas().get(s).getBoundingBox().getBox().setX(this.app.getShapesOnCanvas().get(s).getX());
                this.app.getShapesOnCanvas().get(s).getBoundingBox().getBox().setY(((Text) this.app.getShapesOnCanvas().get(s).getShape()).getY() - ((Text) this.app.getShapesOnCanvas().get(s).getShape()).getFont().getSize());
                a.getChildren().add(this.app.getShapesOnCanvas().get(s).getBoundingBox().getBox());
            }
        }
        return true;
    }
}
