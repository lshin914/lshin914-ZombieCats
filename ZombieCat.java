import java.util.Random;

public class ZombieCat extends Cat {

    private int roundsZombieCat = 0;
    private int roundsSinceLastEaten = 0;
    private int xEdge = 0;
    private int yEdge = 0;

    public ZombieCat(int x, int y, City cty, Random rnd) {
        // parent constructor
        super(x, y, cty, rnd);
        // zombiecats are default red
        this.lab = LAB_RED;
        // zombiecats move three steps at a time
        this.stepLen = 3;
    }

    public void step() {

        // zombiecat does not randomly change direction?

        // TORUS
        // if x-value is zero and we are moving west, then set x-value to 80 and continue moving west
        if (getX() == 0 && getDir() == 3) {
            xEdge = 78;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 1 && getDir() == 3) {
            xEdge = 79;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 2 && getDir() == 3) {
            xEdge = 80;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if x-value is 80 and we are moving east, then set x-value to 0 and continue moving east
        else if (getX() == 80 && getDir() == 1) {
            xEdge = 2;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 79 && getDir() == 1) {
            xEdge = 1;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 78 && getDir() == 1) {
            xEdge = 0;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is zero and we are moving north, set y-value to 80 and continue moving north
        else if (getY() == 0 && getDir() == 0) {
            xEdge = getX();
            yEdge = 78;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 1 && getDir() == 0) {
            xEdge = getX();
            yEdge = 79;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 2 && getDir() == 0) {
            xEdge = getX();
            yEdge = 80;
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is 80 and we are moving south, set y-value to 0 and continue moving south
        else if (getY() == 80 && getDir() == 2) {
            xEdge = getX();
            yEdge = 2;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 79 && getDir() == 2) {
            xEdge = getX();
            yEdge = 1;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 78 && getDir() == 2) {
            xEdge = getX();
            yEdge = 0;
            setGridPoint(xEdge, yEdge);
        }
        // move forward three steps as normal
        else {
            int xPos = (getX()+ (stepLen * dirX[getDir()]));
            int yPos = (getY()+ (stepLen * dirY[getDir()]));
            // set zombiecat's new position
            setGridPoint(xPos, yPos);
        }

        roundsZombieCat++;
        roundsSinceLastEaten++;
    }

    public void takeAction() {

        // if a zombiecat doesn't eat within 100 rounds, it dies
        if (roundsSinceLastEaten == 100) {
            dead = true;
        }
        
        for (Creature creature: city.creatures) {
            // if a mouse and at same position as zombiecat, mouse is dead (eaten)
            if (creature.lab == LAB_BLUE && getX() == creature.getX() && getY() == creature.getY()) {
                creature.dead = true;
            }
            // if a cat and at same position as zombiecat, cat is turned into zombiecat at same location
            if ((creature.lab == LAB_YELLOW || creature.lab == LAB_CYAN) && creature.getX() == getX() && getY() == creature.getY()) {
                city.creaturesToAdd.add(new ZombieCat(getX(), getY(), city, rand));
            }
        }

        // zombiecat searches up to 40 grid points
        // if mouse or cat found, chases changes color to black
        // if not found, moves as normal and remains red
        Creature chased = null;
        for (Creature creature: city.creatures) {
            // found mouse
            if (creature.lab == LAB_BLUE && dist(creature) <= 40 && (chased == null || dist(creature) < dist(chased) )) {
                chased = creature;
            }
            // found cat
            if ((creature.lab == LAB_YELLOW || creature.lab == LAB_CYAN) && dist(creature) <= 40 && (chased == null || dist(creature) < dist(chased))) {
                chased = creature;
            }
        }

        // zombie cat chases the creature it finds
        if (chased != null) {
            chase(chased);
        }
        else {
            this.lab = LAB_RED;
        }
    }

    public void chase(Creature creature) {
        // zombiecat searches up to 40 grid points
        // if mouse or cat found, chases changes color to black
        // if not found, moves as normal and remains red
        this.lab = LAB_BLACK;

        // check if distance bewteen x is less than y direction, go in x-direction
        if (Math.abs(creature.getX() - getX()) < Math.abs(creature.getY() - getY())) {
            if (creature.getY() > getY()) {
                setDir(2);
            }
            else {
                setDir(0);
            }
        }
        // check if distance bewteen y is less than x direction, go in y-direction (north or south)
        else {
            if (getX() < creature.getX()) {
                setDir(1);
            }
            else {
                setDir(3);
            }
        }
    }
}