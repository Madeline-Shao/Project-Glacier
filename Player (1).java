package projectGlacier;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * creates a player in the game
 *
 * @author Madeline Shao
 * @version Apr 30, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class Player implements Serializable
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -3229738995687862912L;

    /**
     * the player's name
     */
    private String name;

    /**
     * the player's hand
     */
    private ArrayList<Card> hand;


    /**
     * Constructs new player
     */
    public Player()
    {
        name = "EMPTY";
    }


    /**
     * constructs new player
     * 
     * @param name
     *            player's name
     * @param hand
     *            inital cards in player's hand
     */
    public Player( String name, ArrayList<Card> hand )
    {
        this.name = name;
        this.hand = hand;
    }


    /**
     * draws a card from the deck of the affiliated board and adds it to the
     * player's hand
     * 
     * @param board
     *            the board the player is playing on
     * @return -3 if successful, -1 if deck is empty, -2 if 10 or more cards in
     *         hand, and the mission number if the card drawn is a disaster card
     */
    public int drawDeck( Board board )
    {
        if ( hand.size() < 10 )
        {
            Card c = board.drawDeck();
            if ( c instanceof Disaster )
            {
                int mission;
                if ( board.getPlayerOne().equals( this ) )
                {
                    if ( board.calculateMissionScore( board.getAlaskaOne() ) < board
                        .calculateMissionScore( board.getAntarcticaOne() ) )
                    {
                        if ( board.calculateMissionScore( board.getAlaskaOne() ) < board
                            .calculateMissionScore( board.getCanadaOne() ) )
                        {
                            if ( board.calculateMissionScore( board.getAlaskaOne() ) < board
                                .calculateMissionScore( board.getGreenlandOne() ) )
                            {
                                if ( board.calculateMissionScore( board.getAlaskaOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board.calculateMissionScore( board.getAlaskaOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 0;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandOne() ) < board
                                            .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                .calculateMissionScore( board.getGreenlandOne() ) )
                            {
                                if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 2;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandOne() ) < board
                                            .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if ( board.calculateMissionScore( board.getAntarcticaOne() ) < board
                            .calculateMissionScore( board.getCanadaOne() ) )
                        {
                            if ( board.calculateMissionScore( board.getAntarcticaOne() ) < board
                                .calculateMissionScore( board.getGreenlandOne() ) )
                            {
                                if ( board.calculateMissionScore( board.getAntarcticaOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getAntarcticaOne() ) < board
                                            .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 1;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandOne() ) < board
                                            .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                .calculateMissionScore( board.getGreenlandOne() ) )
                            {
                                if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board.calculateMissionScore( board.getCanadaOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 2;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandOne() ) < board
                                    .calculateMissionScore( board.getNorwayOne() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandOne() ) < board
                                            .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayOne() ) < board
                                        .calculateMissionScore( board.getRussiaOne() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    if ( board.calculateMissionScore( board.getAlaskaTwo() ) < board
                        .calculateMissionScore( board.getAntarcticaTwo() ) )
                    {
                        if ( board.calculateMissionScore( board.getAlaskaTwo() ) < board
                            .calculateMissionScore( board.getCanadaTwo() ) )
                        {
                            if ( board.calculateMissionScore( board.getAlaskaTwo() ) < board
                                .calculateMissionScore( board.getGreenlandTwo() ) )
                            {
                                if ( board.calculateMissionScore( board.getAlaskaTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board.calculateMissionScore( board.getAlaskaTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 0;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandTwo() ) < board
                                            .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                .calculateMissionScore( board.getGreenlandTwo() ) )
                            {
                                if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 2;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandTwo() ) < board
                                            .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if ( board.calculateMissionScore( board.getAntarcticaTwo() ) < board
                            .calculateMissionScore( board.getCanadaTwo() ) )
                        {
                            if ( board.calculateMissionScore( board.getAntarcticaTwo() ) < board
                                .calculateMissionScore( board.getGreenlandTwo() ) )
                            {
                                if ( board.calculateMissionScore( board.getAntarcticaTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getAntarcticaTwo() ) < board
                                            .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 1;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandTwo() ) < board
                                            .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                .calculateMissionScore( board.getGreenlandTwo() ) )
                            {
                                if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board.calculateMissionScore( board.getCanadaTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 2;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                            else
                            {
                                if ( board.calculateMissionScore( board.getGreenlandTwo() ) < board
                                    .calculateMissionScore( board.getNorwayTwo() ) )
                                {
                                    if ( board
                                        .calculateMissionScore( board.getGreenlandTwo() ) < board
                                            .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 3;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                                else
                                {
                                    if ( board.calculateMissionScore( board.getNorwayTwo() ) < board
                                        .calculateMissionScore( board.getRussiaTwo() ) )
                                    {
                                        mission = 4;
                                    }
                                    else
                                    {
                                        mission = 5;
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println( "Disaster " + mission );
                disaster( board, mission );
                drawDeck( board );
                new DisasterWindow( mission );
                return mission;
            }
            if ( c != null )
            {
                hand.add( c );
                return -3;
            }
            else
            {
                return -1;
            }
        }
        return -2;
    }


    /**
     * draws a card from the discard pile of given mission and adds it to the
     * player's hand
     * 
     * @param mission
     *            which mission discard pile to draw from
     * @param board
     *            the board the player is playing on
     * @return 0 if successful, -1 if discard pile is empty, and -2 if 10 or
     *         more cards in hand
     */
    public int drawDiscard( Board board, int mission )
    {
        if ( hand.size() < 10 )
        {
            Card c = board.drawDiscard( mission );
            if ( c != null )
            {
                hand.add( c );
                return 0;
            }
            else
            {
                return -1;
            }
        }
        return -2;
    }


    /**
     * Gets rid of the mission of the player's choosing. Activated when the
     * player draws a disaster card.
     * 
     * @param board
     *            the board the player is playing on
     * @param mission
     *            the mission the player wants to discard
     */
    public boolean disaster( Board board, int mission )
    {
        return board.disaster( this, mission );
    }


    /**
     * places a card in its respective mission
     * 
     * @param c
     *            the card to place
     * @param board
     *            the board the player is playing on
     * @return returns 0 if successful, returns -1 if c is not in hand, -2 if 10
     *         or less cards in hand
     */
    public int place( Board board, Card c )
    {
        if ( hand.size() >= 10 )
        {
            if ( hand.contains( c ) )
            {
                board.place( this, c );
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -2;
        }
    }


    /**
     * discards a card to its respective mission discard pile
     * 
     * @param c
     *            the card to place
     * @param board
     *            the board the player is playing on
     * @return returns 0 if successful, returns -1 if c is not in hand, -2 if 10
     *         or less cards in hand
     */
    public int discard( Board board, Card c )
    {
        if ( hand.size() >= 10 )
        {
            if ( hand.contains( c ) )
            {
                board.discard( this, c );
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -2;
        }
    }


    /**
     * returns the player's hand
     * 
     * @return the player's hand
     */
    public ArrayList<Card> getHand()
    {
        return hand;
    }


    /**
     * returns the player's name
     * 
     * @return the player's name
     */
    public String getName()
    {
        return name;
    }


    /*
     * (non-Javadoc) overrides Object's equals method Players are equal if names
     * are equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object other )
    {
        if ( other instanceof Player )
        {
            Player o = (Player)other;
            if ( o.getName().equals( this.getName() ) )
            {
                return true;
            }
        }
        return false;
    }
}
