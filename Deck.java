package projectGlacier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Creates the deck
 *
 * @author Radence Tsow
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
// COMMENT OUT DURING JUNIT TESTING
//public class Deck extends ArrayList<Card>
//{
//    /**
//     * Serial UID used for serialization
//     */
//    private static final long serialVersionUID = 4441844349811615126L;
//
//
//    /**
//     * Constructor
//     * 
//     * @throws IOException
//     */
//    public Deck() throws IOException
//    {
//        // add Mission cards
//        for ( int i = 0; i < 6; i++ )
//        {
//            for ( int k = 2; k < 11; k++ )
//            {
//                this.add( new MissionCard( i, k, "Mission" + i + k + ".gif" ) );
//            }
//        }
//        // add Disaster cards
//        for ( int i = 0; i < 3; i++ )
//        {
//            this.add( new Disaster( "Disaster.gif" ) );
//        }
//        // add Wager cards
//        for ( int i = 0; i < 6; i++ )
//        {
//            for ( int k = 0; k < 3; k++ )
//            {
//                this.add( new Wager( i, "Wager" + i + ".gif" ) );
//            }
//        }
//        // add Sabotage cards
//        for ( int i = 0; i < 6; i++ )
//        {
//            for ( int k = 1; k < 5; k++ )
//            {
//                this.add( new Sabotage( i, -k, "Sabotage" + i + k + ".gif" ) );
//            }
//        }
//
//        Collections.shuffle( this ); // shuffles deck
//    }
//
//
//    /**
//     * creates a new hand out of the top ten cards of the deck
//     * 
//     * @return a new hand (10 cards)
//     */
//    public ArrayList<Card> newHand()
//    {
//        ArrayList<Card> hand = new ArrayList<Card>();
//        for ( int i = 0; i < 10; i++ )
//        {
//            Card c = this.remove( 0 );
//            while ( c instanceof Disaster )
//            {
//                int index = (int)( Math.random() * size() );
//                add( index, c );
//                c = this.remove( 0 );
//            }
//            hand.add( c );
//        }
//        return hand;
//    }
//}

//// Stub class for testing
//// uncomment when JUnit testing
@SuppressWarnings("serial")
public class Deck extends ArrayList<Card>
{
    public Deck()
    {
        //add Mission cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 2; k < 11; k++ )
            {
                this.add( new MissionCard( i, k) );
            }
        }
        //add Disaster cards
        for( int i = 0; i < 3; i++)
        {
            this.add( new Disaster() );
        }
        //add Wager cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 0; k < 3; k++ )
            {
                this.add( new Wager(i) );
            }
        }
        //add Sabotage cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 1; k < 4; k++ )
            {
                this.add( new Sabotage( i, -k) );
            }
        }

        Collections.shuffle( this ); //shuffles deck

    }

    /**
     * creates a new hand out of the top ten cards of the deck
     * @return a new hand (10 cards)
     */
    public ArrayList<Card> newHand()
    {
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 10; i++)
        {
            Card c = this.remove( 0 );
            while (c instanceof Disaster)
            {
                int index = (int)(Math.random() * size());
                add(index, c);
                c = this.remove( 0 );
            }
            hand.add( c );
        }
        return hand;
    }
}