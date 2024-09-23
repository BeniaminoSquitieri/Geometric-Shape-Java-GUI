package progettose.commands;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import progettose.application.CanvasClass;

public class CommandHistoryTest {

    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        this.commandHistory = new CommandHistory();
        JFXPanel fxPanel = new JFXPanel();
    }

    @After
    public void tearDown() {
        this.commandHistory = null;
    }

    @Test
    public void testPush() {
        assertTrue(this.commandHistory.isEmpty());
        Command command = new InsertCommand(CanvasClass.getInstance(new Pane()), new Receiver(new HashMap<>(), new ArrayList<>(), CanvasClass.getInstance(new Pane())));
        this.commandHistory.push(command);
        assertFalse(this.commandHistory.isEmpty());
    }

    @Test
    public void testPop() {
        Command command = new InsertCommand(CanvasClass.getInstance(new Pane()), new Receiver(new HashMap<>(), new ArrayList<>(), CanvasClass.getInstance(new Pane())));
        this.commandHistory.push(command);
        assertFalse(this.commandHistory.isEmpty());
        assertEquals(this.commandHistory.pop(), command);
        assertTrue(this.commandHistory.isEmpty());
    }

    @Test
    public void testClear() {
        Command command = new InsertCommand(CanvasClass.getInstance(new Pane()), new Receiver(new HashMap<>(), new ArrayList<>(), CanvasClass.getInstance(new Pane())));
        this.commandHistory.push(command);
        this.commandHistory.clear();
        assertTrue(this.commandHistory.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(this.commandHistory.isEmpty());
        Command command = new InsertCommand(CanvasClass.getInstance(new Pane()), new Receiver(new HashMap<>(), new ArrayList<>(), CanvasClass.getInstance(new Pane())));
        this.commandHistory.push(command);
        assertFalse(this.commandHistory.isEmpty());
    }
}
