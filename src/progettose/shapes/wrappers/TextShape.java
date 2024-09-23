package progettose.shapes.wrappers;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.text.Text;
/**
 *
 * Class to manage the creation of an RectangleShape that is a wrapper for the
 * real JavaFX shape : Rectangle
 *
 */
public class TextShape implements ShapeInterface {
    // The box that represents the bounding box of the TextShape

    private BoundingBoxClass box;
    // The Text that represents the real JavaFX Shape Text that will be managed

    private Text text;
   // The X coordinate of the shape
    private double x;
    // The y coordinate of the shape
    private double y;
    // The z coordinate of the shape
    private int z;

    
    /**
     * This method is the constructor of the TextShape
     *
     * @param text This is the first parameter that represents the text
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the text
     * @param y This is the second parameter that represents the Y coordinate of
     * the start of the text.
     * In this method we create the shape and the
     * bounding box calling the bound() method
     *
     */
    public TextShape(Text text, double x, double y) {
        this.text = text;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = 0;
    }

     /**
     * This method is the constructor of the TextShape
     *
     * @param text This is the first parameter that represents the text
     * shape
     * @param x This is the second parameter that represents the X coordinate of
     * the start of the text
     * @param y This is the second parameter that represents the Y coordinate of
     * the start of the text.
     * @param z This is the third parameter that represent the Z coordinate of
     * the text
     * In this method we create the shape and the
     * bounding box calling the bound() method
     *
     */
    public TextShape(Text text, double x, double y, int z) {
        this.text = text;
        this.box = this.bound();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * In this method we create the bounding box associated at the text
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object created
     */
    @Override
    public BoundingBoxClass bound() {
        FontLoader font = Toolkit.getToolkit().getFontLoader();
        return new BoundingBoxClass(text.getX(), text.getY() - text.getFont().getSize(), font.computeStringWidth(text.getText(), text.getFont()) + 5, text.getFont().getSize() + 5);
    }

    /**
     *
     * In this method we return the JavaFX text wrapped by our class
     *
     * @return Ellipse This return the JavaFX text wrapped by our class
     */
    @Override
    public Text getShape() {
        return this.text;

    }
 /**
     *
     * In this method we return the box representing the bounding box of the
     * text
     *
     * @return BoundingBoxClass This return the BoundingBoxClass object
     * associated to the text
     */
    @Override
    public BoundingBoxClass getBoundingBox() {
        return box;
    }

     /**
     * This method returns the Z coordinate of the text
     *
     * @return int This returns the Z coordinate of the text
     *
     */
    @Override
    public int getZ() {
        return z;
    }

    /**
     * This method returns the X coordinate of the text
     *
     * @return int This returns the X coordinate of the text
     *
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * This method sets the X coordinate of the TEXT
     *
     * @param x This is the first parameter that represent the new X coordinate
     * of the text
     *
     */
    @Override
    public void setX(double x) {
        this.x = x;
        this.text.setX(x);
        this.box.getBox().setX(x);
    }

    /**
     * This method returns the Y coordinate of the text
     *
     * @return int This returns the Y coordinate of the text
     *
     */
    @Override
    public double getY() {
        return y;
    }
/**
     * This method sets the Y coordinate of the TEXT
     *
     * @param y This is the first parameter that represent the new Y coordinate
     * of the TEXT
     *
     */
    @Override
    public void setY(double y) {
        this.y = y;
        this.text.setY(y + text.getFont().getSize());
    }
/**
     * This method sets the Z coordinate of the TEXT
     *
     * @param z This is the first parameter that represent the new Z coordinate
     * of the text
     *
     */
    @Override
    public void setZ(int z) {
        this.z = z;
    }

   
    /**
     * This method returns a string representation of a text object. The
     * text object's x, y, z coordinates, fontsize and text
     *
     * @return String
     *
     */
    @Override
    public String toString() {
        return this.text.getText() + "/" + ";" + x + ";" + (y+this.text.getFont().getSize()) + ";" + this.text.getFill() + ";" + this.text.getFill() + ";" + this.text.getFont().getSize() + ";" + (this.y + this.text.getFont().getSize()) + ";" + this.z + ";\n";
    }

    
    /**
     * 
     * @return double This returns the width of the text
     */
    @Override
    public double getWidth() {
        return Toolkit.getToolkit().getFontLoader().computeStringWidth(this.text.getText(), this.text.getFont());
    }

    /**
     * 
     * @return double This returns the font of the text
     */
    @Override
    public double getHeight() {
        return this.text.getFont().getSize();
    }

}
