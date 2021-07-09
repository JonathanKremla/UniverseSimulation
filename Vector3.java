import java.awt.*;

public class Vector3 {

    // This class represents vectors in a 3D vector space.


    //TODO: change modifiers.
    private double x;
    private double y;
    private double z;


    //TODO: define constructor.
    public Vector3(){}
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {
        return  new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        return  new Vector3(this.x * d, this.y * d, this.z * d);
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);

    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {
        return  Math.sqrt((this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y) + (this.z - v.z) * (this.z - v.z));

    }

    // Returns the length (norm) of this vector.
    public double length() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));

    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double length = this.length();
        this.x = this.x / length;
        this.y = this.y / length;
        this.z = this.z / length;
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the existing StdDraw canvas. The z-coordinate is not used.
    public void drawAsDot(double radius, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(this.x, this.y, radius);
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        return "[" + this.x + "," + this.y + "," + this.z + "]";

    }


}
