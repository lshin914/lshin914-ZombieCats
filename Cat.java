import java.util.Random;
import java.lang.Math;

public class Cat extends Creature {
    private int roundsCat = 0;
    private int roundsSinceLastEaten = 0;
    private int xEdge = 0;
    private int yEdge = 0;

    public Cat(int x, int y, City cty, Random rnd) {
        // parent constructor
        super(x, y, cty, rnd);
        // cats are default yellow
        this.lab = LAB_YELLOW;
        // cats move two steps at a time
        this.stepLen = 2;
    }
    
    public void step() {

        // do i need yellow label?
        // randomly changes direction 5% of the time
        int rand5 = rand.nextInt(20);
        if (rand5 == 5) {
            // get and set random direction
            int randDir = rand.nextInt(4);
            setDir(randDir);
        } 

        // TORUS
        // if x-value is zero and we are moving west, then set x-value to 80 and continue moving west
        if (getX() == 0 && getDir() == 3) {
            xEdge = 79;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 1 && getDir() == 3) {
            xEdge = 80;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if x-value is 80 and we are moving east, then set x-value to 0 and continue moving east
        else if (getX() == 80 && getDir() == 1) {
            xEdge = 1;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        else if (getX() == 79 && getDir() == 1) {
            xEdge = 0;
            yEdge = getY();
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is zero and we are moving north, set y-value to 80 and continue moving north
        else if (getY() == 0 && getDir() == 0) {
            xEdge = getX();
            yEdge = 79;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 1 && getDir() == 0) {
            xEdge = getX();
            yEdge = 80;
            setGridPoint(xEdge, yEdge);
        }
        // if y-value is 80 and we are moving south, set y-value to 0 and continue moving south
        else if (getY() == 80 && getDir() == 2) {
            xEdge = getX();
            yEdge = 1;
            setGridPoint(xEdge, yEdge);
        }
        else if (getY() == 79 && getDir() == 2) {
            xEdge = getX();
            yEdge = 0;
            setGridPoint(xEdge, yEdge);
        }
        // move forward two steps as normal
        else {
            int xPos = (getX()+ (stepLen * dirX[getDir()]));
            int yPos = (getY()+ (stepLen * dirY[getDir()]));
            // set cat's new position
            setGridPoint(xPos, yPos);
        }

        roundsCat++;
        roundsSinceLastEaten++;
    }

    public void takeAction() {

        // if cat doesn't eat in 50 moves, it dies ==> becomes zombiecat
        // get rid of cat and create new zombiecat at same location
        if (roundsSinceLastEaten == 50) {
            dead = true;
            city.creaturesToAdd.add(new ZombieCat(getX(), getY(), city, rand));
        }

        // cat and mouse at same location, cat eats mouse & mouse dies
        for (Creature creature: city.creatures) {
            // if a mouse and at same position as cat, mouse is dead (eaten)
            if (creature.lab == LAB_BLUE && getX() == creature.getX() && getY() == creature.getY()) {
                creature.dead = true;
                // has eaten
                roundsSinceLastEaten = 0;
            }
        }

        // cat searches up to 20 grid points
        // if mouse found, cat chases mouse and changes color to cyan
        // if mouse not found, cat moves as normal and remains yellow
        Creature chased = null;
        for (Creature creature: city.creatures) {
            // if the creature is a mouse and is within 20 grid points and the cat is not chasing anything currently or 
            // the current creature is closest to the cat, the cat chases it
            if (creature.lab == LAB_BLUE && dist(creature) <= 20 && (chased == null || dist(creature) < dist(chased))) {
                    chased = creature;
            }
        }

        // cat chases the mouse it found
        if (chased != null) {
            chase(chased);
        }
        else {
            this.lab = LAB_YELLOW;
        }

    }

    public void chase(Creature creature) {
        // cat searches up to 20 grid points
        // if mouse found, cat chases mouse and changes color to cyan
        // if mouse not found, cat moves as normal and remains yellow
        this.lab = LAB_CYAN;

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