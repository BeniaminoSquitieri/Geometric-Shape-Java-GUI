package progettose.shapes.wrappers;

import javafx.scene.shape.Line;

/**
 *
 * Class to manage the creation of a LineShape that is a wrapper for the real
 * JavaFX shape : Line
 *
 */
public class LineShape implements ShapeInterface {

    // The box that represents the bounding box of the LineShape
    private BoundingBoxClass box;
    // The Line that represents the real JavaFX Shape Line that will be managed
    private Line line;
    // The X coordinate of the shape
    private double x;
    // The y coordinate of the shape
    private double y;
    // The z coordinate of the shape
    private int z;

    /**
     * This method is the constructor of the LineShape
     *
     * @param shape This is the first parameter that represents the line shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the line
     * @param y This is the second parameter that represents the X coordinate of
     * the start of the line In this method we create the shape and the bounding
     * box calling the bound() method
     *
     */
    public LineShape(Line shape, double x, double y) {
        this.line = shape;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = 0;

    }

    /**
     * This method is the constructor of the LineShape
     *
     * @param shape This is the first parameter that represents the line shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the line
     * @param y This is the second parameter that represents the X coordinate of
     * the start of the line
     * @param z This is the third parameter that represent the Z coordinate of
     * the ellipse In this method we create the shape and the bounding box
     * calling the bound() method
     *
     *
     */
    public LineShape(Line shape, double x, double y, int z) {
        this.line = shape;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = z;

    }

    /**
     *
     * In this method we create the bounding box associated at the ellipse
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object created
     */
    @Override
    public BoundingBoxClass bound() {
        return new BoundingBoxClass(line.getLayoutBounds().getMinX(), line.getLayoutBounds().getMinY(), line.getLayoutBounds().getWidth(), line.getLayoutBounds().getHeight());
    }

    /**
     *
     * In this method we return the JavaFX line wrapped by our class
     *
     * @return Ellipse This return the JavaFX line wrapped by our class
     */
    @Override
    public Line getShape() {
        return line;
    }

    /**
     *
     * In this method we return the box representing the bounding box of the
     * line
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object
     * associated to the line
     */
    @Override
    public BoundingBoxClass getBoundingBox() {
        return box;
    }

    /**
     * In this method we manage the visibility of the box based on the selection
     * of the line
     *
     * @param selected This is the first parameter that represents the selection
     * of the JavaFX line
     *
     */
//    @Override
//    public void setSelected(boolean selected) {
//        if (selected == true) {
//            box.getBox().setVisible(true);
//        } else {
//            box.getBox().setVisible(false);
//        }
//
//    }
    /**
     * This method returns a string representation of a line object. The line
     * object's x, y, z coordinates, stroke color, and the line's start and end
     * x coordinates.
     *
     * @return String
     *
     */
    @Override
    public String toString() {
        return "Line;" + this.x + ";" + this.y + ";" + this.line.getStroke() + ";" + this.line.getStroke() + ";" + this.line.getEndX() + ";" + this.line.getEndY() + ";" + this.z + ";\n";
    }

    /**
     * This method returns the Z coordinate of the line
     *
     * @return int This returns the Z coordinate of the line
     *
     */
    @Override
    public int getZ() {
        return z;
    }

    /**
     * This method returns the X coordinate of the line
     *
     * @return int This returns the X coordinate of the line
     *
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * This method returns the Y coordinate of the line
     *
     * @return int This returns the Y coordinate of the line
     *
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * This method sets the Z coordinate of the line
     *
     * @param z This is the first parameter that represent the new Z coordinate
     * of the line
     *
     */
    @Override
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * This method sets the X coordinate of the line, it also change the end X
     * coordinate
     *
     * @param x This is the first parameter that represent the new X coordinate
     * of the line
     *
     */
    @Override
    public void setX(double x) {
        double diff = this.x - x;
        this.line.setStartX(x);
        this.line.setEndX(this.line.getEndX() - diff);

        this.x = x;

    }

    /**
     * This method sets the Y coordinate of the line, it also change the end Y
     * coordinate
     *
     * @param y This is the first parameter that represent the new Y coordinate
     * of the line
     *
     */
    @Override
    public void setY(double y) {
        double diff = this.y - y;
        this.line.setStartY(y);
        this.line.setEndY(this.line.getEndY() - diff);

        this.y = y;
    }

    /**
     *
     * @return double This returns the Width of the shape calculated on the offset between his startX and EndX
     */
    @Override
    public double getWidth() {
        return Math.abs(line.getStartX() - line.getEndX());
    }

    /**
     *
     * @return double This returns the Height of the shape calculated on the offset between his startY and EndY
     */
    @Override
    public double getHeight() {
        return Math.abs(line.getStartY() - line.getEndY());
    }

}
