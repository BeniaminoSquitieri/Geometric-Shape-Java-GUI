package progettose.commands;

import progettose.application.CanvasClass;

/**
 * Concrete command for moving all selected shapes on the canvas.
 *
 */
public class MoveCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public MoveCommand(CanvasClass app, Receiver receiver) {
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
        return true;
    }
}
