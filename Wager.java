package projectGlacier;

import java.io.IOException;


/**
 * adds multiplier to mission
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class Wager extends Card
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -5299304057518184294L;

    /**
     * sets the number of the Card to 1
     */
    private static int number = 1;


    /**
     * creates the Wager card
     * 
     * @param mission
     *            - mission number
     * @throws IOException
     */
    public Wager( int mission, String filename ) throws IOException
    {
        super( mission, number, filename );
    }

    // UNCOMMENT DURING TESTING
    // /**
    // * creates the Wager card
    // * for testing purposes only
    // *
    // * @param mission
    // */
     public Wager ( int mission)
     {
     super( mission, number);
     }
}
