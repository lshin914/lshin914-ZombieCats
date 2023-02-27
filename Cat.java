import java.util.Random;
import java.lang.Math;

public class Cat extends Creature {
    private int roundsCat = 0;
    private int roundsSinceLastEaten = 0;

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

        // move forward two steps
        int xPos = (getX()+ (stepLen * dirX[getDir()]));
        int yPos = (getY()+ (stepLen * dirY[getDir()]));

        // set cat's new position
        setGridPoint(xPos, yPos);

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