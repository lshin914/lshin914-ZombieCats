import java.util.Random;

public class Mouse extends Creature {
    private int roundsMouse = 0;

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

        // move forward one step as normal
        int xPos = (getX()+ (stepLen * dirX[getDir()]));
        int yPos = (getY()+ (stepLen * dirY[getDir()]));

        // set mouse's new position
        setGridPoint(xPos, yPos);

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
