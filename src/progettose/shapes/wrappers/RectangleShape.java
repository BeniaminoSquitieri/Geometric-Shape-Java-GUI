package progettose.shapes.wrappers;

/**
 *
 * Class to manage the creation of an RectangleShape that is a wrapper for the
 * real JavaFX shape : Rectangle
 *
 */
import javafx.scene.shape.Rectangle;

public class RectangleShape implements ShapeInterface {

    // The box that represents the bounding box of the RectangleShape
    private BoundingBoxClass box;
    // The Rectangle that represents the real JavaFX Shape Rectangle that will be managed
    private Rectangle rect;
    // The X coordinate of the shape
    private double x;
    // The y coordinate of the shape
    private double y;
    // The z coordinate of the shape
    private int z;

    /**
     * This method is the constructor of the RectangleShape
     *
     * @param shape This is the first parameter that represents the rectangle
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the rectangle
     * @param y This is the second parameter that represents the Y coordinate of
     * the start of the rectangle 
     * In this method we create the shape and the
     * bounding box calling the bound() method
     *
     */
    public RectangleShape(Rectangle shape, double x, double y) {
        this.rect = shape;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    /**
     * This method is the constructor of the RectangleShape
     *
     * @param shape This is the first parameter that represents the rectangle
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the rectangle
     * @param y This is the second parameter that represents the X coordinate of
     * the start of the rectangle
     * @param z This is the third parameter that represent the Z coordinate of
     * the rectangle In this method we create the shape and the bounding box
     * calling the bound() method
     *
     *
     */
    public RectangleShape(Rectangle shape, double x, double y, int z) {
        this.rect = shape;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * In this method we create the bounding box associated at the rectangle
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object created
     */
    @Override
    public BoundingBoxClass bound() {
        return new BoundingBoxClass(rect.getLayoutBounds().getMinX(), rect.getLayoutBounds().getMinY(), rect.getLayoutBounds().getWidth(), rect.getLayoutBounds().getHeight());
    }

    /**
     *
     * In this method we return the box representing the bounding box of the
     * rectangle
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object
     * associated to the rectangle
     */
    @Override
    public BoundingBoxClass getBoundingBox() {
        return box;
    }

    /**
     *
     * In this method we return the JavaFX rectangle wrapped by our class
     *
     * @return Ellipse This return the JavaFX rectangle wrapped by our class
     */
    @Override
    public Rectangle getShape() {
        return rect;
    }

    /**
     * This method returns a string representation of a rectangle object. The
     * rectangle object's x, y, z coordinates, fill color, stroke color, width
     * and height are all included in the string.
     *
     * @return String
     *
     */
    @Override
    public String toString() {
        return "Rectangle;" + x + ";" + y + ";" + this.rect.getFill() + ";" + this.rect.getStroke() + ";" + (this.x + this.rect.getWidth()) + ";" + (this.y + this.rect.getHeight()) + ";" + this.z + ";\n";
    }

    /**
     * This method returns the Z coordinate of the rectangle
     *
     * @return int This returns the Z coordinate of the rectangle
     *
     */
    @Override
    public int getZ() {
        return z;
    }

    /**
     * This method returns the X coordinate of the rectangle
     *
     * @return int This returns the X coordinate of the rectangle
     *
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * This method returns the Y coordinate of the rectangle
     *
     * @return int This returns the Y coordinate of the rectangle
     *
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * This method sets the Z coordinate of the rectangle
     *
     * @param z This is the first parameter that represent the new Z coordinate
     * of the rectangle
     *
     */
    @Override
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * This method sets the X coordinate of the rectangle
     *
     * @param x This is the first parameter that represent the new X coordinate
     * of the rectangle
     *
     */
    @Override
    public void setX(double x) {
        this.x = x;
        this.rect.setX(x);
    }

    /**
     * This method sets the Y coordinate of the rectangle
     *
     * @param y This is the first parameter that represent the new Y coordinate
     * of the rectangle
     *
     */
    @Override
    public void setY(double y) {
        this.y = y;
        this.rect.setY(y);

    }

    /**
     * 
     * @return double This returns the width of the Rectangle
     */
    @Override
    public double getWidth() {
        return this.rect.getWidth();
    }

    /**
     * 
     * @return double This returns the height of the Rectangle
     */
    @Override
    public double getHeight() {
        return this.rect.getHeight();
    }
}
