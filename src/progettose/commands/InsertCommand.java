package progettose.commands;

import progettose.application.CanvasClass;

/**
 * Concrete command for inserting all selected shapes on the canvas.
 *
 */
public class InsertCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public InsertCommand(CanvasClass app, Receiver receiver) {
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
        this.receiver.addShape(this.app.getNewShape());
        return true;
    }
}
