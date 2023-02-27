import java.util.Random;

abstract class Creature {

    
    // Note, that output should be in (x,y) format as
    // the plotter expects it in that format.


    // dir: 0=North, 1=East, 2=South, 3=West.
    // 0    1  2  3  4  5  6  7  8 
    // 1
    // 2
    // 3             N (r-1,c+0)
    // 4             0
    //(r+0,c-1) W 3 [ ] 1 E (r+0,c+1)
    // 5             2
    // 6             S (r+1,c+0)
    //
    //
    // 
    // 
    
    public final static int NORTH = 0;
    public final static int EAST = 1;
    public final static int SOUTH = 2;
    public final static int WEST = 3;
    public final static int NUM_DIRS = 4;
    public final static int[] DIRS = {NORTH,EAST,SOUTH,WEST};


    //Use the index of the direction to determine how to add to a row or column
    //For example, if NORTH (index 0), the we subtract 1 from Y, and add 0 to X
    //direction
    protected final int[] dirY = {-1,0,1,0};
    protected final int[] dirX = {0, 1, 0, -1};


    //Point Colors -- handy contests to use to make your code more readiable
    public final static char LAB_BLACK='k'; // zombies that are chasing are black
    public final static char LAB_BLUE='b'; // mice are blue
    public final static char LAB_RED='r'; // normal zombies are red
    public final static char LAB_YELLOW='y'; // normal cats are yellow
    public final static char LAB_ORANGE='o';
    public final static char LAB_PINK='p';
    public final static char LAB_MAGENTA='m';
    public final static char LAB_CYAN='c'; // cats chasing mice are cyan
    public final static char LAB_GREEN='g';
    public final static char LAB_GRAY='e';


    //current direction facing
    private int dir;

    //current point in grid
    private GridPoint point;

    //current color label for the point
    protected char lab;

    //random instance
    protected Random rand;

    //City in which this creature lives so that it can update it's
    //location and get other information it might need (like the
    //location of other creatures) when making decisions.    
    protected City city;

    //boolean to set when this creature is dead
    protected boolean dead;

    //how wide the steps are
    protected int stepLen;

    public Creature(int x, int y, City cty, Random rnd){
        //DEFAULT Constructor
        point = new GridPoint(x,y);
        city = cty;
        rand = rnd;
        // default color is gray
        lab = LAB_GRAY;
        // randomly generate number from 0 - 3
        dir = rand.nextInt(NUM_DIRS);
        // alive at first
        dead= false;
        // default take one step (does not skip steps)
        stepLen=1;
    }

    public boolean isDead(){ return dead;}

    
    //getter/setter methods
    public int getY(){
        return point.y;
    }
    public int getX(){
        return point.x;
    }
    
    public GridPoint getGridPoint(){
        return new GridPoint(point); //return a copy to preseve
                                     //encapsulation
    }
    public void setGridPoint(int x, int y) {
        this.point.x = x;
        this.point.y = y;
    }
    
    public char getLab(){
        return lab;
    }
    public void setDir(int dir){
        this.dir = dir;
    }
    public int getDir(){
        return this.dir;
    }


    //compute the distance to another creature
    public int dist(Creature c){
        return point.dist(c.getGridPoint());
    }

    //make a random turn
    public void randomTurn() {
        this.dir = rand.nextInt(4);
    }

    
    //TODO: Methods you may want complete here or in a child class, depending on your design
    abstract void step();
        // move forward one step based on direction
    abstract void takeAction();


    
    //To String so you can output a creature to the plotter
    public String toString() {
        //output in (x,y) format
        return ""+this.point.x+" "+this.point.y+" "+lab;
    }

}