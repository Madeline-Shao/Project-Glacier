package projectGlacier;

import java.io.IOException;


/**
 * Creates sabotage cards that affect the opponents missions
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class Sabotage extends Card
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -9014155274373783329L;


    /**
     * creates sabotage cards
     * 
     * @param mission
     *            - this card's mission
     * @param number
     *            - this card's number
     * @throws IOException
     */
    public Sabotage( int mission, int number, String filename ) throws IOException
    {
        super( mission, number, filename );
    }

    // UNCOMMENT DURING JUNIT TESTING
    // /**
    // * creates sabotage cards
    // * for testing purposes only
    // *
    // * @param mission
    // * @param number
    // */
     public Sabotage( int mission, int number)
     {
     super( mission, number);
     }
}
