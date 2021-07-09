import java.awt.*;

public class Simulation {
    public static final double G = 6.6743e-11;
    public static final double AU = 150e9;
    public static final double T = 1.5;
    public static final int n = 10000;
    public static final boolean drawOctree = false;  //if true set Z Position to zero.
    public static final boolean zPositionZero = false; // sets Z Coordinate of every Body to zero.
    public static void main(String[] args) {

        Body[] bodyArray  = createBodyArray(n);
        drawSetup();

        double seconds = 0;

        while(true) {

            seconds++;
            

            StdDraw.clear(Color.black);

            Octree octree = new Octree(0,0,0,4e13);
            for(int i = 0; i < bodyArray.length; i++){
                if(bodyArray[i].getPosition().getY() < 4e13 && bodyArray[i].getPosition().getY() > -4e13
                    && bodyArray[i].getPosition().getX() < 4e13 && bodyArray[i].getPosition().getX() > -4e13
                        && bodyArray[i].getPosition().getZ() < 4e13 && bodyArray[i].getPosition().getZ() > -4e13)
                octree.add(bodyArray[i]);
            }
            octree.draw();
            StdDraw.show();

            for (int i = 0; i < bodyArray.length; i++) {
                bodyArray[i].setForceOnBody(new Vector3(0,0,0));
                octree.barnesHut(bodyArray[i]);
            }

            for (int i = 0; i < bodyArray.length; i++) {
                bodyArray[i].move();

            }
            if (seconds%(3*3600) == 0) {
                StdDraw.clear(StdDraw.BLACK);
                StdDraw.show();
            }
        }
    }

    public static void drawSetup(){
        int scale = 200;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(Color.BLACK);
        StdDraw.setXscale(-scale*AU,scale*AU);
        StdDraw.setYscale(-scale*AU,scale*AU);
        StdDraw.enableDoubleBuffering();
    }


    public static Body[] createBodyArray(int size){
        Body[] bodyArray = new Body[size];
        for(int i = 0; i < bodyArray.length;i++){
            bodyArray[i] = randomBody(1.0e35,1.0e43,1.0e2,1.0e10,-2.e13,2.e13,1.e9,1.e10);
        }
        return bodyArray;
    }

    public static Body randomBody(double massMin, double massMax,double radiusMin,double radiusMax, double positionMin, double positionMax, double movementMin, double movementMax){
        double mass = (massMax-massMin +1 ) * Math.random() + massMin;
        double radius = (radiusMax-radiusMin +1 ) * Math.random() + radiusMin;
        double positionX = (positionMax-positionMin) * Math.random() * (Math.round(Math.random()) == 1 ? -1 : 1) + positionMin/100;
        double positionY = (positionMax-positionMin) * Math.random() * (Math.round(Math.random()) == 1 ? 1 : -1) + positionMin/100;
        double positionZ;
        if(!zPositionZero)
            positionZ = (positionMax-positionMin) * Math.random() * (Math.round(Math.random()) == 1 ? 1 : -1) + positionMin/100;
        else
            positionZ = 0;

        double movementY = (movementMax-movementMin) * Math.random() * (Math.round(Math.random()) == 1 ? 1 : -1) + movementMin;
        double movementX = (movementMax-movementMin) * Math.random() * (Math.round(Math.random()) == 1 ? 1 : -1) + movementMin;
        double movementZ = (movementMax-movementMin) * Math.random() * (Math.round(Math.random()) == 1 ? 1 : -1) + movementMin;

        return new Body("",mass,radius,new Vector3(positionX,positionY,positionZ),new Vector3(0,movementY,0),randomColor());
    }

    public static Color randomColor(){
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();
        return new Color(r,g,b);
    }
}
