package example.gamerisk.model;

/**
 * This class is to set coordinator location.
 */
public class Coordinator {

    private int x;

    private int y;

    /**
     * Constructor method with incoming parameters
     *
     * @param x x coordinate location with int type
     * @param y y coordinate location with int type
     */

    public Coordinator(int x, int y) {

        this.x = x;

        this.y = y;
    }

    /**
     * To get the X coordinator
     *
     * @return X with int type
     */

    public int getX() {
        return x;
    }

    /**
     * To set the X coordinator
     *
     * @param : X with int type
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To get the Y coordinator
     *
     * @return Y with int type
     */
    public int getY() {
        return y;
    }

    /**
     * To set the Y coordinator
     *
     * @param : Y with int type
     */
    public void setY(int y) {
        this.y = y;
    }
}
