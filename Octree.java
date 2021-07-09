public class Octree implements OctreeNodeI {
    private OctreeNodeI[] children = new OctreeNodeI[8];
    private double x;
    private double y;
    private double z;
    private double width;
    private double mass;
    private Vector3 massCenter;

    public Octree(double x, double y, double z, double width) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        mass = 0;
        massCenter = new Vector3();
    }

    @Override
    public boolean add(Body b) {
        if (b != null) {
            if (children[selectPos(b)] == null) {
                children[selectPos(b)] = b;
                return true;
            }
            else if (children[selectPos(b)] != null) {
                mass += b.getMass();
                massCenter = massCenter.times(mass).plus(b.getMassCenter().times(b.getMass())).times(1/(mass + b.getMass()));
                if (children[selectPos(b)].getBody() != null) {
                    Body temp = children[selectPos(b)].getBody().deepCopy();
                    children[selectPos(b)] = new Octree(x + getWidth(b)[0], y + getWidth(b)[1], z + getWidth(b)[2], width / 2);
                    children[selectPos(b)].add(b);
                    children[selectPos(temp)].add(temp);
                    return true;
                }
                children[selectPos(b)].add(b);
            }
        }
        return false;
    }

    private double[] getWidth(Body b) {
        if (selectPos(b) == 0) return new double[]{width / 2, width / 2, width / 2};
        if (selectPos(b) == 1) return new double[]{-width / 2, width / 2, width / 2};
        if (selectPos(b) == 2) return new double[]{width / 2, -width / 2, width / 2};
        if (selectPos(b) == 3) return new double[]{-width / 2, -width / 2, width / 2};
        if (selectPos(b) == 4) return new double[]{width / 2, width / 2, -width / 2};
        if (selectPos(b) == 5) return new double[]{-width / 2, width / 2, -width / 2};
        if (selectPos(b) == 6) return new double[]{width / 2, -width / 2, -width / 2};
        return new double[]{-width / 2, -width / 2, -width / 2};
    }

    public void barnesHut(Body b){
        Body tmp = new Body("", mass, 0, massCenter, null, null);
        if (width / b.distanceTo(tmp) < Simulation.T){
            Vector3 forceToAdd = b.gravitationalForce(tmp);
            b.setForceOnBody(b.getForceOnBody().plus(forceToAdd));
        }
        else {
            for (OctreeNodeI child : children) {
                if (child != null)
                    child.barnesHut(b);
            }
        }
    }

    public int selectPos(Body b) {
        double[] bCord = new double[]{b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ()};
        int[] temp = new int[]{1, 1, 1};
        if (bCord[0] < x) temp[0] = -1;
        if (bCord[1] < y) temp[1] = -1;
        if (bCord[2] < z) temp[2] = -1;

        if (temp[0] == 1 && temp[1] == 1 && temp[2] == 1) return 0;
        if (temp[0] == -1 && temp[1] == 1 && temp[2] == 1) return 1;
        if (temp[0] == 1 && temp[1] == -1 && temp[2] == 1) return 2;
        if (temp[0] == -1 && temp[1] == -1 && temp[2] == 1) return 3;
        if (temp[0] == 1 && temp[1] == 1 && temp[2] == -1) return 4;
        if (temp[0] == -1 && temp[1] == 1 && temp[2] == -1) return 5;
        if (temp[0] == 1 && temp[1] == -1 && temp[2] == -1) return 6;
        return 7;
    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public void draw() {

        for (OctreeNodeI child : children) {
            if (child != null && child.getBody() == null){
                child.draw();
            }
            else if (child != null && child.getBody() != null) {
                if(Simulation.drawOctree)
                    drawSquare((Body) child);
                child.draw();
            }
        }
    }

    private void drawSquare(Body b){
        int quadrant = selectPos(b);
        StdDraw.setPenColor(b.getColor());
        if (quadrant == 0 || quadrant == 4) StdDraw.square(x + width/2,y + width/2,width/2);
        if (quadrant == 1 || quadrant == 5) StdDraw.square(x - width/2,y + width/2,width/2);
        if (quadrant == 2 || quadrant == 6) StdDraw.square(x + width/2,y - width/2,width/2);
        if (quadrant == 3 || quadrant == 7) StdDraw.square(x - width/2,y - width/2,width/2);
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public Vector3 getMassCenter() {
        return massCenter;
    }
}
