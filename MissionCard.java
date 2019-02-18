package projectGlacier;

import java.io.IOException;


/**
 * Creates cards that can be used to further expeditions in the Game
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class MissionCard extends Card
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -1683969002693936196L;


    /**
     * creates mission card
     * 
     * @param mission
     * @param number
     * @throws IOException
     */
    public MissionCard( int mission, int number, String filename ) throws IOException
    {
        super( mission, number, filename );
    }

    // UNCOMMENT DURING JUNIT TESTING
    // /**
    // * creates mission card
    // * for testing purposes only
    // *
    // * @param mission
    // * @param number
    // */
     public MissionCard( int mission, int number)
     {
     super( mission, number);
     }
}