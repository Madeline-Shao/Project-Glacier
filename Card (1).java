package projectGlacier;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;


/**
 * Creates the cards used in the game
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public abstract class Card implements Serializable
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -8492611466053462157L;

    /**
     * holds the integer for the designated mission
     */
    private int mission;

    /**
     * holds the number on the card
     */
    private int number;

    /**
     * holds the card's image's filename
     */
    public String fileName;


    /**
     * creates the card
     * 
     * @param mission
     *            - mission number; 0 - Alaska, 1 - Antarctica, 2 - Canada, 3 -
     *            Greenland, 4 - Norway, 5 - Russia
     * @param number
     *            - number
     * @param fileName
     *            - file name of image
     * @throws IOException
     */
    public Card( int mission, int number, String fileName ) throws IOException
    {
        this.mission = mission;
        this.number = number;
        this.fileName = fileName;
    }


    /**
     * returns the image for this card
     * 
     * @return the image
     * @throws IOException
     */
    public Image getImageFromFile() throws IOException
    {
        File file = new File( fileName );

        if ( !file.canRead() )
        {
            throw new IOException(
                fileName + " could not be opened. Check that you specified the path" );
        }

        return ImageIO.read( file );
    }


    // UNCOMMENT DURING JUNIT TESTING
    // /**
    // * creates the card
    // * for testing purposes only
    // *
    // * @param mission
    // * @param number
    // */
     public Card( int mission, int number)
     {
     this.mission = mission;
     this.number = number;
     }
    //
    /**
     * returns the Mission number
     * 
     * @return mission
     */
    public int getMission()
    {
        return mission;
    }


    /**
     * returns the number on the card
     * 
     * @return number
     */
    public int getNumber()
    {
        return number;
    }


    @Override
    public boolean equals( Object other )
    {
        if ( other instanceof Card )
        {
            Card o = (Card)other;
            if ( o.getMission() == mission && o.getNumber() == number )
            {
                return true;
            }
        }
        return false;
    }
}
