package progettose.commands;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import progettose.application.CanvasClass;
import progettose.shapes.creators.ShapeCreator;
import progettose.shapes.wrappers.ShapeInterface;


public class RotateCommand extends Command {

    /**
     * Class constructor
     *
     * @param app, command invoker
     * @param receiver, command receiver
     */
    public RotateCommand(CanvasClass app, Receiver receiver) {
        super(app, receiver);
    }

    /**
     * Method for the execution of the command. It use the clipboard for getting
     * the shapes to add to the canvas. It saves the previous context.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        saveBackup();
        ShapeCreator shapeCreator = new ShapeCreator();

        ShapeInterface s = null;
        int count = 0;
        boolean check = true;
        for (Group a : this.receiver.getSelectedShape()) {

            if (a.getChildren().get(0) instanceof Ellipse) {
                Ellipse temp = (Ellipse) a.getChildren().get(0);
                s = shapeCreator.createShape("ellipse", temp.getCenterX() - temp.getRadiusY(), temp.getCenterY() - temp.getRadiusX(), Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getFill().toString()), Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getStroke().toString()), temp.getCenterX() + temp.getRadiusY(), temp.getCenterY() + temp.getRadiusX(), this.receiver.getPrevContext().get(a.getChildren().get(0)).getZ());
            } else if (a.getChildren().get(0) instanceof Rectangle) {
                Rectangle temp = (Rectangle) a.getChildren().get(0);
                s = shapeCreator.createShape("rectangle", temp.getX() + temp.getWidth() / 2 - temp.getHeight() / 2, temp.getY() - temp.getWidth() / 2 + temp.getHeight() / 2, Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getFill().toString()), Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getStroke().toString()), temp.getX() + temp.getWidth() / 2 + temp.getHeight() / 2, temp.getY() + temp.getWidth() / 2 + temp.getHeight() / 2, this.receiver.getPrevContext().get(a.getChildren().get(0)).getZ());
            } else if (a.getChildren().get(0) instanceof Line) {
                Line temp = (Line) a.getChildren().get(0);
                s = shapeCreator.createShape("line", temp.getEndX() - (temp.getEndX() - temp.getStartX()) / 2 + (temp.getEndY() - temp.getStartY()) / 2, temp.getEndY() - (temp.getEndY() - temp.getStartY()) / 2 - (temp.getEndX() - temp.getStartX()) / 2, Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getStroke().toString()), Color.web(this.receiver.getPrevContext().get(a.getChildren().get(0)).getShape().getStroke().toString()), temp.getEndX() - (temp.getEndX() - temp.getStartX()) / 2 - (temp.getEndY() - temp.getStartY()) / 2, temp.getEndY() - (temp.getEndY() - temp.getStartY()) / 2 + (temp.getEndX() - temp.getStartX()) / 2, this.receiver.getPrevContext().get(a.getChildren().get(0)).getZ());

            } else {
                count++;
                check = false;
            }
            if (check) {
                if (s.getBoundingBox().getXChange() < 0) {
                    s.setX(s.getX() - s.getBoundingBox().getXChange());
                    s.getBoundingBox().setXChange(s.getBoundingBox().getXChange() - s.getBoundingBox().getXChange());
                }
                if (s.getBoundingBox().getXChange() + s.getBoundingBox().getBox().getWidth() > this.app.getCanvas().getWidth()) {
                    s.setX(s.getX() - (-this.app.getCanvas().getWidth() + s.getBoundingBox().getBox().getX() + s.getBoundingBox().getBox().getWidth()));
                    s.getBoundingBox().setXChange(s.getBoundingBox().getXChange() - (-this.app.getCanvas().getWidth() + s.getBoundingBox().getBox().getX() + s.getBoundingBox().getBox().getWidth()));
                }
                if (s.getBoundingBox().getYChange() < 0) {
                    s.setY(s.getY() - s.getBoundingBox().getYChange());
                    s.getBoundingBox().setYChange(s.getBoundingBox().getYChange() - s.getBoundingBox().getYChange());
                }
                if (s.getBoundingBox().getYChange() + s.getBoundingBox().getBox().getHeight() > this.app.getCanvas().getHeight()) {
                    s.setY(s.getY() - (-this.app.getCanvas().getHeight() + s.getBoundingBox().getBox().getY() + s.getBoundingBox().getBox().getHeight()));
                    s.getBoundingBox().setYChange(s.getBoundingBox().getYChange() - (-this.app.getCanvas().getHeight() + s.getBoundingBox().getBox().getY() + s.getBoundingBox().getBox().getHeight()));
                }
                if(s.getBoundingBox().getBox().getHeight() > this.app.getCanvas().getHeight()  ||  s.getBoundingBox().getBox().getWidth() > this.app.getCanvas().getWidth()){
                    return false;
                }

                this.receiver.removeFromGroup(a);
                this.receiver.addInGroup(s, a);
            }
            check = true;

        }
        if (this.receiver.getSelectedShape().size() > 0 && this.receiver.getSelectedShape().size() != count) {
            return true;
        } else {
            return false;
        }
    }
}
