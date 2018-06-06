package virtualEsp;
/**
 *
 * @author Ohad Wolski
 */
public class virtualEsp {

    public static Threads Threads = new Threads();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initialize();

        Threads.Start();

        try {
            Threads.Join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        // TODO code application logic here
        // initialize all of the data structures?
        // is this how it is made?

    }
}
