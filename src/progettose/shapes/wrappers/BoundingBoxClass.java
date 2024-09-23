package progettose.shapes.wrappers;

import javafx.scene.shape.Rectangle;

/**
 *
 * Class to manage the creation of a bounding box for a selected shape
 *
 *
 */
public class BoundingBoxClass {

    // The rectangle shape that identify the box
    private final Rectangle box;

    // The variable that track the starting x of the rectangle box
    private double xChange;

    // The variable that track the starting y of the rectangle box
    private double yChange;

    /**
     * This method is the constructor of the BoundingBoxClass.
     *
     * @param minX This is the first parameter that represents the starting X
     * coordinate of the shape
     * @param minY This is the second parameter that represents the starting Y
     * coordinate of the shape
     * @param width This is the third parameter that represents the width of the
     * shape
     * @param height This is the fourth parameter that represents the height of
     * the shape In this method we create the box and set its interior to be
     * transparent and its border to be black dotted, we also set it not visible
     */
    public BoundingBoxClass(double minX, double minY, double width, double height) {
        Rectangle temp = new Rectangle(minX, minY, width, height);
        temp.setStyle("-fx-fill: transparent;\n -fx-stroke: grey; \n-fx-stroke-width: 3;  \n -fx-stroke-dash-array: 10 10 10 10;\n -fx-stroke-dash-offset:6; \n -fx-stroke-line-cap:butt;");
        temp.setVisible(true);
        this.box = temp;
        xChange = minX;
        yChange = minY;
    }

    /**
     * This method return the rectangle box
     *
     * @return box This returns the box created by the class
     */
    public Rectangle getBox() {
        return box;
    }

    /**
     * This method change the xChange attribute of the class and it sets the X
     * starting coordinate of the rectangle box itself
     *
     * @param x This is the first parameter that represent the new x coordinate
     * of the box
     */
    public void setXChange(double x) {
        this.xChange = x;
        this.box.setX(x);
    }

    /**
     * This method change the yChange attribute of the class and it sets the Y
     * starting coordinate of the rectangle box itself
     *
     * @param y This is the first parameter that represent the new y coordinate
     * of the box
     */
    public void setYChange(double y) {
        this.yChange = y;
        this.box.setY(y);

    }

    /**
     * This method return the xChange attribute
     *
     * @return double This returns the xChange attribute representing the X
     * starting coordinate of the rectangle box
     */
    public double getXChange() {
        return xChange;

    }

    /**
     * This method return the xChange attribute
     *
     * @return double This returns the xChange attribute representing the Y
     * starting coordinate of the rectangle box
     */
    public double getYChange() {
        return yChange;
    }

}
