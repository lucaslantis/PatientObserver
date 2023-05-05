package DataClasses;

/**
 * Class to store a pair of variables
 * @param <T1> variable type 1
 * @param <T2> variable type 2
 */
public class Pair <T1, T2>{
    public T1 x;
    public T2 y;

    /**
     * Constructor receiving two pairs
     * @param x variable 1
     * @param y variable 2
     */
    public Pair(T1 x, T2 y){
        this.x = x;
        this.y = y;
    }
}
