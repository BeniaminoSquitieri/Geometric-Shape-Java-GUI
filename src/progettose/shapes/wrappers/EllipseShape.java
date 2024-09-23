package progettose.shapes.wrappers;

/**
 *
 * Class to manage the creation of an EllipseShape that is a wrapper for the
 * real JavaFX shape : Ellipse
 *
 */
import javafx.scene.shape.Ellipse;

public class EllipseShape implements ShapeInterface {

    // The box that represents the bounding box of the EllipseShape
    private BoundingBoxClass box;
    // The ellipse that represents the real JavaFX Shape Ellipse that will be managed
    private Ellipse ellipse;
    // The X coordinate of the boundingbox of the shape
    private double x;
    // The y coordinate of the boundingbox of the shape
    private double y;
    // The z coordinate of the shape
    private int z;

    /**
     * This method is the constructor of the EllipseShape
     *
     * @param ellipse This is the first parameter that represents the ellipse
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the center of the ellipse
     * @param y This is the second parameter that represents the X coordinate of
     * the center of the ellipse In this method we create the shape and the
     * bounding box calling the bound() method
     *
     */
    public EllipseShape(Ellipse ellipse, double x, double y) {
        this.ellipse = ellipse;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    /**
     * This method is the constructor of the EllipseShape
     *
     * @param ellipse This is the first parameter that represents the ellipse
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the boundingbox of the ellipse
     * @param y This is the second parameter that represents the X coordinate of
     * the boundingbox of the ellipse.
     * @param z This is the third parameter that represent the Z coordinate of
     * the ellipse In this method we create the shape and the bounding box
     * calling the bound() method
     *
     *
     */
    public EllipseShape(Ellipse ellipse, double x, double y, int z) {
        this.ellipse = ellipse;
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
        return new BoundingBoxClass(ellipse.getLayoutBounds().getMinX(), ellipse.getLayoutBounds().getMinY(), ellipse.getLayoutBounds().getWidth(), ellipse.getLayoutBounds().getHeight());
    }

    /**
     *
     * In this method we return the JavaFX ellipse wrapped by our class
     *
     * @return Ellipse This return the JavaFX ellipse wrapped by our class
     */
    @Override
    public Ellipse getShape() {
        return ellipse;
    }

    /**
     *
     * In this method we return the box representing the bounding box of the
     * ellipse
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object
     * associated to the ellipse
     */
    @Override
    public BoundingBoxClass getBoundingBox() {
        return box;
    }

    /**
     * In this method we manage the visibility of the box based on the selection
     * of the ellipse
     *
     * @param selected This is the first parameter that represents the selection
     * of the JavaFX ellipse
     *
     */
//    @Override
//    public void setSelected(boolean selected) {
//        if (selected == true) {
//            box.getBox().setVisible(true);
//        } else {
//            box.getBox().setVisible(false);
//        }
//    }
    /**
     * This method returns a string representation of an ellipse object. The
     * ellipse object's boundingbox's x and y coordinates,the z coordinate of
     * the ellipse, fill color, stroke color, and radius values are all included
     * in the string.
     *
     * @return String
     *
     */
    @Override
    public String toString() {
        return "Ellipse;" + x + ";" + y + ";" + this.ellipse.getFill() + ";" + this.ellipse.getStroke() + ";" + (this.x + 2 * this.ellipse.getRadiusX()) + ";" + (this.y + 2 * this.ellipse.getRadiusY()) + ";" + this.z + ";\n";
    }

    /**
     * This method returns the Z coordinate of the ellipse
     *
     * @return int This returns the Z coordinate of the ellipse
     *
     */
    @Override
    public int getZ() {
        return z;
    }

    /**
     * This method returns the X coordinate of the ellipse
     *
     * @return int This returns the X coordinate of the ellipse
     *
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * This method returns the Y coordinate of the ellipse
     *
     * @return int This returns the Y coordinate of the ellipse
     *
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * This method sets the Z coordinate of the ellipse
     *
     * @param z This is the first parameter that represent the new Z coordinate
     * of the ellipse
     *
     */
    @Override
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * This method sets the X coordinate of the bounding box of the ellipse, it
     * also change the centerX of the ellipse itself
     *
     * @param x This is the first parameter that represent the new X coordinate
     * of the boundingbox of the ellipse
     *
     */
    @Override
    public void setX(double x) {
        double offset = x - this.x;
        this.ellipse.setCenterX(this.ellipse.getCenterX() + offset);
        this.x = x;

    }

    /**
     * This method sets the Y coordinate of the bounding box of the ellipse, it
     * also change the centerY of the ellipse itself
     *
     * @param y This is the first parameter that represent the new Y coordinate
     * of the boundingbox of the ellipse
     *
     */
    @Override
    public void setY(double y) {
        double offset = y - this.y;
        this.ellipse.setCenterY(this.ellipse.getCenterY() + offset);
        this.y = y;
    }

    
    /**
     *
     * @return double This returns the Width of the shape calculated by the radiusX multiplied by two
     */
    @Override
    public double getWidth() {
        return this.ellipse.getRadiusX() * 2;
    }

       
    /**
     *
     * @return double This returns the Width of the shape calculated by the radiusY multiplied by two
     */
    @Override
    public double getHeight() {
        return this.ellipse.getRadiusY() * 2;
    }
}
