package progettose.commands;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class used to maintain a stack of executed command as an ArrayDeque of
 * Commands.
 *
 */
public class CommandHistory {

    private Deque<Command> stack;

    /**
     * Class constructor.
     */
    public CommandHistory() {
        this.stack = new ArrayDeque<>();
    }

    /**
     * Method for inserting a command in the stack.
     *
     * @param command
     */
    public void push(Command command) {
        stack.push(command);
    }

    /**
     * Method for getting the last added command in the stack.
     *
     * @return the last command if exist, otherwise null.
     */
    public Command pop() {
        return !stack.isEmpty() ? stack.pop() : null;
    }

    /**
     * Method for deleting all the commands in the stack.
     */
    public void clear() {
        stack.clear();
    }

    /**
     * Method for verifying if the stack is empty.
     *
     * @return true if there are no commands in the stack, false otherwise.
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
