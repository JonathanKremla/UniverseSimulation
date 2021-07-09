
public interface OctreeNodeI {

    double getMass();

    Vector3 getMassCenter();

    boolean add(Body b);

    Body getBody();

    void draw();

    void barnesHut(Body b);






}
