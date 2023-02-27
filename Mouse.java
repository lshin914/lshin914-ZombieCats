import java.util.Random;

public class Mouse extends Creature {
    private int roundsMouse = 0;
    private int xEdge = 0;
    private int yEdge = 0;

    // constructor
    public Mouse(int x, int y, City cty, Random rnd) {
        // parent constructor
        super(x, y, cty, rnd);
        // mice are always blue
        this.lab = LAB_BLUE;
    }

    public void step() {
        // randomly changes direction 20% of the time
        // random number out 0, 1, 2, 3, and 4, if 4 turn
        int rand20 = rand.nextInt(5);
        if (rand20 == 4) {
            // get and set random direction
            int randDir = rand.nextInt(4);
            setDir(randDir);
        }

        // TORUS
        // if x-value is zero and we are moving west, then set x-value to 80 and continue moving west
        if (getX() == 0 && getDir() == 3) {
            xEdge = 80;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if x-value is 80 and we are moving east, then set x-value to 0 and continue moving east
        else if (getX() == 80 && getDir() == 1) {
            xEdge = 0;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is zero and we are moving north, set y-value to 80 and continue moving north
        else if (getY() == 0 && getDir() == 0) {
            xEdge = getX();
            yEdge = 80;
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is 80 and we are moving south, set y-value to 0 and continue moving south
        else if (getY() == 80 && getDir() == 2) {
            xEdge = getX();
            yEdge = 0;
            setGridPoint(xEdge, yEdge);
        }
        // move forward one step as normal
        else {
            int xPos = (getX()+ (stepLen * dirX[getDir()]));
            int yPos = (getY()+ (stepLen * dirY[getDir()]));
            // set mouse's new position
            setGridPoint(xPos, yPos);
        }

        // increment this round
        roundsMouse++;
    }

    public void takeAction() {
        // every 20 rounds, new baby mouse at same location
        if (roundsMouse == 20) {
            // add baby mouse
            city.creaturesToAdd.add(new Mouse(getX(), getY(), city, rand));
        }

        // every 30 rounds, mouse is starved and dies
        if (roundsMouse == 30) {
            // set rounds to 0?
            // mouse dies
            dead = true;
        }
    }
}
