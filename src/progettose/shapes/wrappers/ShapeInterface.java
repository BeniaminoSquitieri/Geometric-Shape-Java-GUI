package progettose.shapes.wrappers;

import javafx.scene.shape.Shape;

/*
* This code defines an interface for a Shape class. The interface includes methods for getting the bounding box of a shape, 
* getting the shape itself andsetting the selected status of a shape
 */
public interface ShapeInterface {

    public BoundingBoxClass bound();

    public Shape getShape();

    public BoundingBoxClass getBoundingBox();

    public int getZ();

    public double getX();

    public void setX(double x);

    public double getY();

    public void setY(double y);

    public void setZ(int z);

    public double getWidth();

    public double getHeight();
}
