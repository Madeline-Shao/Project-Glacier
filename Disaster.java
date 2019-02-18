package projectGlacier;

import java.io.IOException;


/**
 * causes a disaster to the player in which the player must skip the turn
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class Disaster extends Card
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -3830975651135270643L;

    /**
     * holds the number of the card
     */
    private static int number = 0;


    /**
     * creates the Disaster card
     * 
     * @param mission
     *            - mission number
     * @throws IOException
     */
    public Disaster( String filename ) throws IOException
    {
        super( -1, number, filename );
    }

    // UNCOMMENT DURING JUNIT TESTING
    // /**
    // * creates the Disaster card
    // * for testing purposes only
    // *
    // * @param mission
    // */
     public Disaster()
     {
     super( -1, number );
     }
}
