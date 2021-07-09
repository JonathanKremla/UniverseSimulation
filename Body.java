import java.awt.*;
import java.lang.Math;

public class Body implements OctreeNodeI {


    // This class represents celestial bodies like stars, planets, asteroids, etc..
    //TODO: change modifiers.
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color; // for drawing the body.
    private Vector3 forceOnBody;

    //TODO: define constructor.
    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    public Vector3 getCurrentMovement(){
        return currentMovement;
    }

    // Returns the distance between this body and the specified 'body'.
    public double distanceTo(Body body) {
        return this.position.minus(body.position).length();
    }

    //Returns a vector representing the gravitational force exerted by 'body' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(Body body) {
        Vector3 v = body.position.minus(this.position);
        double distance = v.length();
        v.normalize();
        double force = Simulation.G * this.mass * body.mass / (distance * distance);
        return v.times(force);

    }

    public Vector3 getPosition(){
        return position;
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force)
    // Hint: see simulation loop in Simulation.java to find out how this is done
    public void move(Vector3 force) {
        Vector3 newPosition = force.times(1 / this.mass).plus(this.position).plus(this.currentMovement);
        Vector3 newMovement = newPosition.minus(this.position);
        this.position = newPosition;
        this.currentMovement = newMovement;

    }

    public void move(){
        move(forceOnBody);
    }

    // Returns a string with the information about this body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        String info = new String("" + this.name + "," + this.mass + "kg radius: " + this.radius + "m, position:" + this.position.toString() + " m, movement: " + this.currentMovement.toString() + "m/s");
        return info;
    }

    // Draws the body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    // Hint: use the method drawAsDot implemented in Vector3 for this
    @Override
    public void draw() {
        this.position.drawAsDot(1e10 * Math.log10(this.radius), this.color);

    }

    @Override
    public void barnesHut(Body b) {
        if (!this.equals(b)) {
            Vector3 forceToAdd = b.gravitationalForce(this);
            b.setForceOnBody(b.getForceOnBody().plus(forceToAdd));
        }
    }

    public void setForceOnBody(Vector3 forceToSet){
        forceOnBody = forceToSet;
    }

    public Vector3 getForceOnBody() {
        return forceOnBody;
    }


    public Body deepCopy(){
        return new Body(name,mass,radius,position,currentMovement,color);
    }

    @Override
    public Vector3 getMassCenter() {
        return position;
    }

    @Override
    public boolean add(Body b) {
        return false;
    }

    @Override
    public Body getBody(){
        return this;
    }

    @Override
    public double getMass() {
        return mass;
    }

    public Color getColor() {
        return color;
    }

}
