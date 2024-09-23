package progettose.application;

import progettose.shapes.wrappers.*;
import progettose.observers.*;
import progettose.tool.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private CheckBox gridCheckBox;
    @FXML
    public ColorPicker borderPicker;
    @FXML
    public ColorPicker interiorPicker;
    @FXML
    private Pane lineShapeMenuItem;
    @FXML
    private Pane ellipseShapeMenuItem;
    @FXML
    private Pane rectangleShapeMenuItem;
    @FXML
    private Pane canvas;

    private CanvasClass canvasWrapper;

    @FXML
    private GridPane grid;
    @FXML
    private Label gridLabel1;
    @FXML
    private Slider gridSlider;
    @FXML
    private Label gridLabel2;

    private KeyCombination keyCombinationShiftC; // Variable for catch the CTRL+C event from the user keyboard
    private KeyCombination keyCombinationShiftX; // Variable for catch the CTRL+X event from the user keyboard
    private KeyCombination keyCombinationShiftZ; // Variable for catch the CTRL+Z event from the user keyboard
    private KeyCombination keyCombinationShiftV; // Variable for catch the CTRL+V event from the user keyboard
    private LineTool lineTool; // LineTool representing the tool that draw a line
    private EllipseTool ellipseTool; // EllipseTool representing the tool that draw an ellipse
    private RectangleTool rectangleTool; // RectangleTool representing the tool that draw a rectangle

    @FXML
    private MenuItem undoMenuItem;

    private ColorEventManager colorEventManager; // The EventManager for the colour events
    private KeyboardEventManager keyboardEventManager; // The EventManager for the keyboard events

    private Tool selectedTool; // The tool that is selected at the moment, it change if the user selected another tool
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem loadButton;
    @FXML
    private TextField textBox;
    @FXML
    private ComboBox<Integer> textSizeBox;

    @FXML
    private Slider zoomSlider;
    
    private ObservableList<ColumnConstraints> colList = FXCollections.observableArrayList(); // List of columns of the grid
    private ObservableList<RowConstraints> rowList = FXCollections.observableArrayList(); // List of row of the grid

    /**
     * This method initialize the application setting up GUI elements. 
     * It setup the comboBox for the fontSize, it creates a CanvasClass object representing the canvas where the user
     * draw, it initializze the Managers adding events, it add the subscriber to the ColorEventManager and KeyBoardEventManager. It also initialize the tools, the color pickers,
     * the grid and the KeyCombination used for catching user commands from keyboard.
     *
     * @param url Unused.
     * @param rb Unused.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Integer> comboBoxContent = FXCollections.observableArrayList();
        for (int i = 10; i <= 100; i = i + 5) {
            comboBoxContent.add(i);
        }
        textSizeBox.setItems(comboBoxContent);
        textSizeBox.getSelectionModel().select(0);

        canvasWrapper = CanvasClass.getInstance(canvas);

        colorEventManager = ColorEventManager.getInstance("INTERIOR_COLOR", "BORDER_COLOR");
        keyboardEventManager = KeyboardEventManager.getInstance("COPY", "CUT", "PASTE", "DELETE", "UNDO");
        lineTool = new LineTool(lineShapeMenuItem);
        ellipseTool = new EllipseTool(ellipseShapeMenuItem);
        rectangleTool = new RectangleTool(rectangleShapeMenuItem);

        interiorPicker.setValue(Color.WHITE);
        borderPicker.setValue(Color.BLACK);

        colorEventManager.subscribe("INTERIOR_COLOR", lineTool);
        colorEventManager.subscribe("INTERIOR_COLOR", ellipseTool);
        colorEventManager.subscribe("INTERIOR_COLOR", rectangleTool);
        colorEventManager.subscribe("INTERIOR_COLOR", canvasWrapper);
        colorEventManager.subscribe("BORDER_COLOR", lineTool);
        colorEventManager.subscribe("BORDER_COLOR", ellipseTool);
        colorEventManager.subscribe("BORDER_COLOR", rectangleTool);
        colorEventManager.subscribe("BORDER_COLOR", canvasWrapper);
        colorEventManager.notify("INTERIOR_COLOR", interiorPicker.getValue());
        colorEventManager.notify("BORDER_COLOR", borderPicker.getValue());

        keyboardEventManager.subscribe("COPY", canvasWrapper);
        keyboardEventManager.subscribe("PASTE", canvasWrapper);
        keyboardEventManager.subscribe("CUT", canvasWrapper);
        keyboardEventManager.subscribe("UNDO", canvasWrapper);
        keyboardEventManager.subscribe("DELETE", canvasWrapper);

        grid.visibleProperty().bind(gridCheckBox.selectedProperty());
        gridSlider.setValue(250);
        for (int i = 0; i < canvas.getPrefWidth() * 2 / gridSlider.getValue(); i++) { //double numCols = canvas.getPrefWidth()*2/gridSlider.getValue();
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(gridSlider.getValue());
            colList.add(colConst);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < canvas.getPrefHeight() * 2 / gridSlider.getValue(); i++) { //double numRows = canvas.getPrefHeight()*2/gridSlider.getValue();  
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(gridSlider.getValue());
            rowList.add(rowConst);
            grid.getRowConstraints().add(rowConst);
        }

        keyCombinationShiftC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        keyCombinationShiftX = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        keyCombinationShiftZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        keyCombinationShiftV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

        undoMenuItem.setDisable(true);
    }

    /**
     * This method handle the change of the border colour on the border picker, he notify the ColorEventManager the change
     *
     * @param event This is the first parameter that represents the click of the user on a colour on the border picker
     */
    @FXML
    private void handleBorderColour(ActionEvent event) {
        colorEventManager.notify("BORDER_COLOR", borderPicker.getValue());
//        this.executeCommand(new ChangeBorderCommand(this, new Receiver(shapesOnCanvas, selectedShape, this)));

    }

    /**
     * This method handle the change of the interior colour on the interior picker, he notify the ColorEventManager the change
     *
     * @param event This is the first parameter that represents the click of the user on a colour on the interior picker
     */
    @FXML
    private void handleInteriorColour(ActionEvent event) {
        colorEventManager.notify("INTERIOR_COLOR", interiorPicker.getValue());
//        this.executeCommand(new ChangeInteriorCommand(this, new Receiver(shapesOnCanvas, selectedShape, this)));

    }

    /**
     * This method is used to save a file. This code opens a file chooser, and
     * then creates a new file with the chosen name. If the file name is not
     * null, then the code will try to write on the file. If there is an
     * IOException, then an error message will be displayed.
     *
     * @param event This is the first parameter that represents the save button
     * clicked by the user
     * @exception IOException
     */
    @FXML
    private void handleSave(ActionEvent event) {
        FileChooser file = new FileChooser();
        File filename = file.showSaveDialog(new Stage());
        if (filename != null) {
            ArrayList<ShapeInterface> temp = new ArrayList();
            try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
                for (ShapeInterface s : canvasWrapper.getShapesOnCanvas().values()) {
                    temp.add(s);
                }
                temp.sort(new ShapeZComparator());
                for (ShapeInterface s : temp) {
                    pw.print(s.toString());
                }
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, "There was a problem while trying to write on the file.\n");
                alert.showAndWait();
            }

        }
    }

    /**
     * This code opens a file chooser and, if a file is selected, resets the
     * current drawing and loads the contents of the selected file clearing the
     * previous state of the application by restoring values of tracking
     * variables.
     *
     * @param event This is the first parameter that represents the load button
     * clicked by the user
     * @exception Exception
     */
    @FXML
    private void handleLoad(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Alert al = new Alert(AlertType.CONFIRMATION, "Opening a drawing file will reset the current drawing. Would you like to proceed?", ButtonType.NO, ButtonType.YES);
            al.showAndWait();
            if (al.getResult() == ButtonType.YES) {
                undoMenuItem.setDisable(true);
                canvasWrapper.reset();
                try ( Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile.getPath())))) {
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        StringTokenizer st = new StringTokenizer(line, ";");
                        while (st.hasMoreTokens()) {
                            String figure = st.nextToken();
                            double startX = Double.parseDouble(st.nextToken());
                            double startY = Double.parseDouble(st.nextToken());
                            Color interior = Color.web(st.nextToken());
                            Color border = Color.web(st.nextToken());
                            double endX = Double.parseDouble(st.nextToken());
                            double endY = Double.parseDouble(st.nextToken());
                            int z = Integer.parseInt(st.nextToken());
                            canvasWrapper.createShape(figure.toLowerCase(), startX, startY, endX, endY, interior, border, z);
                        }
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.ERROR, "There was a problem while trying to read the file.\n" + ex.getMessage());
                    alert.showAndWait();
                }
            }
        }

    }

    /**
     * This is the method for managing the user selection of a ShapeMenuItem:
     * Line. When the user clicks on the line pane, this method is called. It deselect all the shapes
     * selected and he set the selectedToll to this current one. It also notify the CanvasClass of the update
     *
     * @param event This is the first parameter that represents the click of the
     * user on ShapeMenuItem : Line
     */
    @FXML
    private void clickLine(MouseEvent event
    ) {
        canvasWrapper.deselectAll();
        if (selectedTool == null) {
            selectedTool = lineTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);

        } else if (selectedTool instanceof LineTool) {
            selectedTool.setSelected(false);
            selectedTool = null;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.DEFAULT);

        } else {
            selectedTool.setSelected(false);
            selectedTool = lineTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);
        }
    }

    /**
     * This is the method for managing the user selection of a ShapeMenuItem:
     * Ellipse. When the user clicks on the line pane, this method is called. It deselect all the shapes
     * selected and he set the selectedToll to this current one. It also notify the CanvasClass of the update
     *
     * @param event This is the first parameter that represents the click of the
     * user on ShapeMenuItem : Line
     */
    @FXML
    private void clickEllipse(MouseEvent event
    ) {
        canvasWrapper.deselectAll();
        if (selectedTool == null) {
            selectedTool = ellipseTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);

        } else if (selectedTool instanceof EllipseTool) {
            selectedTool.setSelected(false);
            selectedTool = null;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.DEFAULT);

        } else {
            selectedTool.setSelected(false);
            selectedTool = ellipseTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);
        }
    }

    /**
     * This is the method for managing the user selection of a ShapeMenuItem:
     * Rect. When the user clicks on the line pane, this method is called. It deselect all the shapes
     * selected and he set the selectedToll to this current one. It also notify the CanvasClass of the update
     *
     * @param event This is the first parameter that represents the click of the
     * user on ShapeMenuItem : Line
     */
    @FXML
    private void clickRect(MouseEvent event
    ) {
        canvasWrapper.deselectAll();
        if (selectedTool == null) {
            selectedTool = rectangleTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);
        } else if (selectedTool instanceof RectangleTool) {
            selectedTool.setSelected(false);
            selectedTool = null;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.DEFAULT);
        } else {
            selectedTool.setSelected(false);
            selectedTool = rectangleTool;
            canvasWrapper.setSelected(selectedTool);
            canvas.setCursor(Cursor.CROSSHAIR);
            selectedTool.setSelected(true);
        }
    }

    /**
     * The method above displays an alert to the user, asking if they want to
     * proceed with opening a new drawing. If the user clicks "Yes", the current
     * drawing is reset.
     *
     * @param event This is the first parameter that represents the click of the
     * user on the new button
     */
    @FXML
    private void handleNewDraw(ActionEvent event
    ) {
        Alert al = new Alert(AlertType.CONFIRMATION, "Opening a new drawing will reset the current drawing. Would you like to proceed?", ButtonType.NO, ButtonType.YES);
        al.showAndWait();
        if (al.getResult() == ButtonType.YES) {
            canvasWrapper.reset();
        }
    }

    /**
     * This method handle the starting point of the shapes selected from the
     * ShapeMenuItem when a Tool is selected
     *
     * @param event This is the first parameter that represents the first click
     * of the user on the canvas when it starts the drag on it
     */
    @FXML
    private void onCanvasDragDetected(MouseEvent event
    ) {
        if (selectedTool != null && event.getButton() == MouseButton.PRIMARY) {
            selectedTool.mouseDown(canvasWrapper, event.getX(), event.getY());
            canvas.startFullDrag();
        }
    }

    /**
     * This method handle the finish point of the shapes selected from the
     * ShapeMenuItem to be added on the canvas, then the selectedTool will add it to the canvas
     *
     *
     * @param event This is the first parameter that represents the last click
     * of the user on the canvas during the drag phase
     */
    @FXML
    private void onCanvasMouseDragReleased(MouseDragEvent event
    ) {
        if(selectedTool != null)
            if (event.getButton() == MouseButton.PRIMARY) {
                selectedTool.mouseUp(canvasWrapper, event.getX(), event.getY());
            }
    }

    /**
     * This method handle the combination of click on the keyboard of the user
     * and notify the KeyBoardEventManager of the update
     *
     *
     * @param event This is the first parameter that represent the clicked keys
     * on the keyboard of the user
     */
    @FXML
    private void combPressedHandler(KeyEvent event
    ) {
        if (keyCombinationShiftC.match(event)) {
            keyboardEventManager.notify("COPY");
        } else if (keyCombinationShiftX.match(event)) {
            keyboardEventManager.notify("CUT");
        } else if (keyCombinationShiftZ.match(event)) {
            keyboardEventManager.notify("UNDO");
        } else if (keyCombinationShiftV.match(event)) {
            keyboardEventManager.notify("PASTE");
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            keyboardEventManager.notify("DELETE");
        }
    }

    /**
     * 
     * @param canvas This is the first parameter that represent the Pane copied
     * in the canvas
     */
    public void setCanvas(Pane canvas) {
        this.canvas = canvas;
    }

    /**
     * This method notify the KeyBoardEventManager of the undo button clicked
     *
     *
     * @param event This is the first parameter that represent the click on the
     * undoButton on the interface by the user
     */
    @FXML
    private void undoPreviousAction(ActionEvent event) {
        keyboardEventManager.notify("UNDO");
    }

    /**
     * This method check if there are commands undoable and disable or enable the undoCommand based on the check
     * @param event This is the first parameter that represent the click on the
     * menuItem "Edit"
     */
    @FXML
    private void checkUndo(Event event) {
        if (canvasWrapper.historyIsEmpty()) {
            undoMenuItem.setDisable(true);
        } else {
            undoMenuItem.setDisable(false);
        }
    }

    /**
     * This method handle the click of the zoomSlider changing the zoom of the canvas
     *
     * @param event This is the first parameter that represent the click on the
     * zoomSlider
     */
    @FXML
    private void changeZoom(MouseEvent event) {
        double newZoom = zoomSlider.getValue() / 100;
        canvasWrapper.setZoom(newZoom);
    }

     /**
     * This method handle the click of the gridSlider changing the grid size of the canvas
     *
     * @param event This is the first parameter that represent the click on the
     * gridSlider
     */
    @FXML
    private void zoomGrid(MouseEvent event) {

        grid.getColumnConstraints().removeAll(colList);
        grid.getRowConstraints().removeAll(rowList);
        for (int i = 0; i < canvas.getPrefWidth() * 2 / gridSlider.getValue(); i++) { //double numCols = canvas.getPrefWidth()*2/gridSlider.getValue();
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(gridSlider.getValue());
            colList.add(colConst);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < canvas.getPrefHeight() * 2 / gridSlider.getValue(); i++) { //double numRows = canvas.getPrefHeight()*2/gridSlider.getValue();  
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(gridSlider.getValue());
            rowList.add(rowConst);
            grid.getRowConstraints().add(rowConst);
        }
    }

     /**
     * This method handle the enter pressed on the textfield adding the text writed by the user on the canvas
     *
     * @param event This is the first parameter that represent the enter pressed by the user editing the textfield
     */
    @FXML
    private void insertText(ActionEvent event) {
        double vScrollCoeff = ((ScrollPane) canvas.getParent().getParent().getParent().getParent()).getVvalue();
        double hScrollCoeff = ((ScrollPane) canvas.getParent().getParent().getParent().getParent()).getHvalue();
        double zoomCoeff = zoomSlider.getValue() / 100 + 0.75;
        double scaledWidth = canvas.getWidth() / (zoomCoeff * (zoomCoeff == 1 ? 1 : 2));
        double scaledHeight = canvas.getHeight() / (zoomCoeff * (zoomCoeff == 1 ? 1 : 2));
        double startX = scaledWidth / 2 + (canvas.getWidth() - scaledWidth) * (hScrollCoeff) - textBox.getText().length() * textSizeBox.getSelectionModel().getSelectedItem() / 5;
        double startY = scaledHeight / 2 + (canvas.getHeight() - scaledHeight) * (vScrollCoeff)/*scaledHeight/4+scaledHeight*(vScrollCoeff)+(vScrollCoeff==0 ? 0 : scaledHeight/4)*/;
        canvasWrapper.createShape(textBox.getText() + "/", startX, startY, textSizeBox.getSelectionModel().getSelectedItem(), 18, Color.GOLD, Color.BROWN);
    }

    /**
     * This method handle the choice of the user in the comboBox and change the fontsize according to this value
     *
     * @param event This is the first parameter that represent the choice in the comboBox
     */
    @FXML
    private void setFontSize(ActionEvent event) {
        canvasWrapper.setFontSize(textSizeBox.getSelectionModel().getSelectedItem());
    }

    /**
     * This method handle the click on the Left Button of the user, calling the rotate method of the canvas
     *
     * @param event This is the first parameter that represent the click on the button
     */
    @FXML
    private void rotateLeft(ActionEvent event) {
        canvasWrapper.rotate();
    }

    /**
     * This method handle the click on the Right Button of the user, calling the rotate method of the canvas
     *
     * @param event This is the first parameter that represent the click on the button
     */
    @FXML
    private void rotateRight(ActionEvent event) {
        canvasWrapper.rotate();

    }
}
