package projectGlacier;

import java.io.*;
import java.util.*;


/**
 * Represents the playing board. Holds fields for all other items
 *
 * @author William Zong
 * @version Apr 27, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class Board implements Serializable
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -4303189424692359791L;

    /**
     * References the drawing deck
     */
    Deck deck;

    /**
     * References the first player's Alaska mission
     */
    LinkedList<Card> alaskaOne;

    /**
     * References the second player's Alaska mission
     */
    LinkedList<Card> alaskaTwo;

    /**
     * References the first player's Antarctica mission
     */
    LinkedList<Card> antarcticaOne;

    /**
     * References the second player's Antarctica mission
     */
    LinkedList<Card> antarcticaTwo;

    /**
     * References the first player's Canada mission
     */
    LinkedList<Card> canadaOne;

    /**
     * References the second player's Canada mission
     */
    LinkedList<Card> canadaTwo;

    /**
     * References the first player's Greenland mission
     */
    LinkedList<Card> greenlandOne;

    /**
     * References the second player's Greenland mission
     */
    LinkedList<Card> greenlandTwo;

    /**
     * References the first player's Norway mission
     */
    LinkedList<Card> norwayOne;

    /**
     * References the second player's Norway mission
     */
    LinkedList<Card> norwayTwo;

    /**
     * References the first player's Russia mission
     */
    LinkedList<Card> russiaOne;

    /**
     * References the second player's Russia mission
     */
    LinkedList<Card> russiaTwo;

    /**
     * References the Alaska discard pile
     */
    Stack<Card> alaskaDiscard;

    /**
     * References the Antarctica discard pile
     */
    Stack<Card> antarcticaDiscard;

    /**
     * References the Canada discard pile
     */
    Stack<Card> canadaDiscard;

    /**
     * References the Greenland discard pile
     */
    Stack<Card> greenlandDiscard;

    /**
     * References the Norway discard pile
     */
    Stack<Card> norwayDiscard;

    /**
     * References the Russia discard pile
     */
    Stack<Card> russiaDiscard;

    /**
     * References the first player
     */
    Player playerOne;

    /**
     * References the second player
     */
    Player playerTwo;


    /**
     * Default Constructor
     * 
     * @param one
     *            - player one
     * @param two
     *            - player tow
     * @param deck
     *            - deck
     */
    public Board( Player one, Player two, Deck deck )
    {
        this.deck = deck;
        alaskaOne = new LinkedList<Card>();
        alaskaTwo = new LinkedList<Card>();
        antarcticaOne = new LinkedList<Card>();
        antarcticaTwo = new LinkedList<Card>();
        canadaOne = new LinkedList<Card>();
        canadaTwo = new LinkedList<Card>();
        greenlandOne = new LinkedList<Card>();
        greenlandTwo = new LinkedList<Card>();
        norwayOne = new LinkedList<Card>();
        norwayTwo = new LinkedList<Card>();
        russiaOne = new LinkedList<Card>();
        russiaTwo = new LinkedList<Card>();
        alaskaDiscard = new Stack<Card>();
        antarcticaDiscard = new Stack<Card>();
        canadaDiscard = new Stack<Card>();
        greenlandDiscard = new Stack<Card>();
        norwayDiscard = new Stack<Card>();
        russiaDiscard = new Stack<Card>();
        playerOne = one;
        playerTwo = two;
    }


    /**
     * Returns the current scores of both players
     * 
     * @return the current scores of both players where scores[0] = player one
     *         and scores[1] = player two
     */
    public int[] calculateScores()
    {
        int[] scores = new int[2];
        int playerOneScore = 0;
        int playerTwoScore = 0;
        if ( alaskaOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( alaskaOne );
        }
        if ( alaskaTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( alaskaTwo );
        }
        if ( antarcticaOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( antarcticaOne );
        }
        if ( antarcticaTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( antarcticaTwo );
        }
        if ( canadaOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( canadaOne );
        }
        if ( canadaTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( canadaTwo );
        }
        if ( greenlandOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( greenlandOne );
        }
        if ( greenlandTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( greenlandTwo );
        }
        if ( norwayOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( norwayOne );
        }
        if ( norwayTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( norwayTwo );
        }
        if ( russiaOne.size() > 0 )
        {
            playerOneScore += calculateMissionScore( russiaOne );
        }
        if ( russiaTwo.size() > 0 )
        {
            playerTwoScore += calculateMissionScore( russiaTwo );
        }
        scores[0] = playerOneScore;
        scores[1] = playerTwoScore;
        return scores;
    }


    /**
     * Returns the specified mission's score
     * 
     * @param mission
     *            - the mission
     * @return the specified mission's score
     */
    public int calculateMissionScore( LinkedList<Card> mission )
    {
        if ( mission.size() == 0 )
        {
            return 0;
        }
        int score = -10;
        int wagerCount = 1;
        for ( int i = 0; i < mission.size(); i++ )
        {
            switch ( mission.get( i ).getNumber() )
            {
                case 1:
                    wagerCount++;
                    break;
                default:
                    score += mission.get( i ).getNumber();
            }
        }
        int bonus = 0;
        if ( mission.size() >= 10 )
        {
            bonus = 20;
        }
        return score * wagerCount + bonus;
    }


    /**
     * Removes and returns the first card in the deck
     * 
     * @return the first card in the deck
     */
    public Card drawDeck()
    {
        if ( !deck.isEmpty() )
            return deck.remove( 0 );
        else
            return null;
    }


    /**
     * Removes the top card from the specified discard pile
     * 
     * @param mission
     *            - specifies a discard pile where 0 = alaska ... 5 = russia
     * @return the top card from the specified discard pile
     */
    public Card drawDiscard( int mission )
    {
        switch ( mission )
        {
            case 0:
                if ( alaskaDiscard.size() > 0 )
                {
                    return alaskaDiscard.pop();
                }
                return null;
            case 1:
                if ( antarcticaDiscard.size() > 0 )
                {
                    return antarcticaDiscard.pop();
                }
                return null;
            case 2:
                if ( canadaDiscard.size() > 0 )
                {
                    return canadaDiscard.pop();
                }
                return null;
            case 3:
                if ( greenlandDiscard.size() > 0 )
                {
                    return greenlandDiscard.pop();
                }
                return null;
            case 4:
                if ( norwayDiscard.size() > 0 )
                {
                    return norwayDiscard.pop();
                }
                return null;
            case 5:
                if ( russiaDiscard.size() > 0 )
                {
                    return russiaDiscard.pop();
                }
                return null;
            default:
                throw new InputMismatchException();
        }
    }


    /**
     * Gets rid of the mission in the parameter. Activated when the
     * player draws a disaster card.
     * 
     * @param player
     *            the player who drew the disaster card
     * @param mission which mission is discarded
     * @return true if succeeded, false if mission is empty
     */
    public boolean disaster( Player player, int mission )
    {
        switch ( mission )
        {
            case 0:
                if ( player.equals( playerOne ) )
                {
                    if ( alaskaOne.isEmpty() )
                    {
                        return false;
                    }
                    alaskaOne = new LinkedList<Card>();
                    return true;
                }
                if ( alaskaTwo.isEmpty() )
                {
                    return false;
                }
                alaskaTwo = new LinkedList<Card>();
                return true;
            case 1:
                if ( player.equals( playerOne ) )
                {
                    if ( antarcticaOne.isEmpty() )
                    {
                        return false;
                    }
                    antarcticaOne = new LinkedList<Card>();
                    return true;
                }
                if ( antarcticaTwo.isEmpty() )
                {
                    return false;
                }
                antarcticaTwo = new LinkedList<Card>();
                return true;
            case 2:
                if ( player.equals( playerOne ) )
                {
                    if ( canadaOne.isEmpty() )
                    {
                        return false;
                    }
                    canadaOne = new LinkedList<Card>();
                    return true;
                }
                if ( canadaTwo.isEmpty() )
                {
                    return false;
                }
                canadaTwo = new LinkedList<Card>();
                return true;
            case 3:
                if ( player.equals( playerOne ) )
                {
                    if ( greenlandOne.isEmpty() )
                    {
                        return false;
                    }
                    greenlandOne = new LinkedList<Card>();
                    return true;
                }
                if ( greenlandTwo.isEmpty() )
                {
                    return false;
                }
                greenlandTwo = new LinkedList<Card>();
                return true;
            case 4:
                if ( player.equals( playerOne ) )
                {
                    if ( norwayOne.isEmpty() )
                    {
                        return false;
                    }
                    norwayOne = new LinkedList<Card>();
                    return true;
                }
                if ( norwayTwo.isEmpty() )
                {
                    return false;
                }
                norwayTwo = new LinkedList<Card>();
                return true;
            case 5:
                if ( player.equals( playerOne ) )
                {
                    if ( russiaOne.isEmpty() )
                    {
                        return false;
                    }
                    russiaOne = new LinkedList<Card>();
                    return true;
                }
                if ( russiaTwo.isEmpty() )
                {
                    return false;
                }
                russiaTwo = new LinkedList<Card>();
                return true;
            default:
                throw new InputMismatchException();
        }
    }


    /**
     * Places a card down onto its respective mission
     * 
     * @param player
     *            - the player who places the card
     * @param c
     *            - the card to place
     * @return if this card was placed successfully
     */
    public boolean place( Player player, Card c )
    {
        switch ( c.getMission() )
        {
            case 0:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        alaskaTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( alaskaOne.size() == 0
                        || alaskaOne.get( alaskaOne.size() - 1 ).getNumber() <= c.getNumber() )
                    {
                        alaskaOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    alaskaOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( alaskaTwo.size() == 0
                    || alaskaTwo.get( alaskaTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    alaskaTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            case 1:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        antarcticaTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( antarcticaOne.size() == 0 || antarcticaOne.get( antarcticaOne.size() - 1 )
                        .getNumber() <= c.getNumber() )
                    {
                        antarcticaOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    antarcticaOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( antarcticaTwo.size() == 0
                    || antarcticaTwo.get( antarcticaTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    antarcticaTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            case 2:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        canadaTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( canadaOne.size() == 0
                        || canadaOne.get( canadaOne.size() - 1 ).getNumber() <= c.getNumber() )
                    {
                        canadaOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    canadaOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( canadaTwo.size() == 0
                    || canadaTwo.get( canadaTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    canadaTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            case 3:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        greenlandTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( greenlandOne.size() == 0 || greenlandOne.get( greenlandOne.size() - 1 )
                        .getNumber() <= c.getNumber() )
                    {
                        greenlandOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    greenlandOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( greenlandTwo.size() == 0
                    || greenlandTwo.get( greenlandTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    greenlandTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            case 4:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        norwayTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( norwayOne.size() == 0
                        || norwayOne.get( norwayOne.size() - 1 ).getNumber() <= c.getNumber() )
                    {
                        norwayOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    norwayOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( norwayTwo.size() == 0
                    || norwayTwo.get( norwayTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    norwayTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            case 5:
                if ( player.equals( playerOne ) )
                {
                    if ( c instanceof Sabotage ) // sabotage
                    {
                        russiaTwo.add( 0, c );
                        player.getHand().remove( c );
                        return true;
                    }
                    if ( russiaOne.size() == 0
                        || russiaOne.get( russiaOne.size() - 1 ).getNumber() <= c.getNumber() )
                    {
                        russiaOne.add( c );
                        player.getHand().remove( c );
                        return true;
                    }
                    return false;
                }
                if ( c instanceof Sabotage ) // sabotage
                {
                    russiaOne.add( 0, c );
                    player.getHand().remove( c );
                    return true;
                }
                if ( russiaTwo.size() == 0
                    || russiaTwo.get( russiaTwo.size() - 1 ).getNumber() <= c.getNumber() )
                {
                    russiaTwo.add( c );
                    player.getHand().remove( c );
                    return true;
                }
                return false;
            default:
                throw new InputMismatchException();
        }
    }


    /**
     * Discards a card onto its respective discard pile
     * 
     * @param player
     *            - the player who discards
     * @param c
     *            - the card to discard
     */
    public void discard( Player player, Card c )
    {
        player.getHand().remove( c );
        switch ( c.getMission() )
        {
            case 0:
                alaskaDiscard.push( c );
                return;
            case 1:
                antarcticaDiscard.push( c );
                return;
            case 2:
                canadaDiscard.push( c );
                return;
            case 3:
                greenlandDiscard.push( c );
                return;
            case 4:
                norwayDiscard.push( c );
                return;
            case 5:
                russiaDiscard.push( c );
                return;
            default:
                throw new InputMismatchException();
        }
    }


    // THE FOLLOWING METHODS ARE FOR TESTING PURPOSES ONLY

    /**
     * returns the deck
     * 
     * @return the deck
     */
    public Deck getDeck()
    {
        return deck;
    }


    /**
     * returns player one's Alaska pile
     * 
     * @return player one's Alaska pile
     */
    public LinkedList<Card> getAlaskaOne()
    {
        return alaskaOne;
    }


    /**
     * returns player 2's Alaska pile
     * 
     * @return player 2's Alaska pile
     */
    public LinkedList<Card> getAlaskaTwo()
    {
        return alaskaTwo;
    }


    /**
     * returns player one's Antarctica pile
     * 
     * @return player one's Antarctica pile
     */
    public LinkedList<Card> getAntarcticaOne()
    {
        return antarcticaOne;
    }


    /**
     * returns player 2's Antarctica pile
     * 
     * @return player 2's Antarctica pile
     */
    public LinkedList<Card> getAntarcticaTwo()
    {
        return antarcticaTwo;
    }


    /**
     * returns player one's Canada pile
     * 
     * @return player one's Canada pile
     */
    public LinkedList<Card> getCanadaOne()
    {
        return canadaOne;
    }


    /**
     * returns player 2's Canada pile
     * 
     * @return player 2's Canada pile
     */
    public LinkedList<Card> getCanadaTwo()
    {
        return canadaTwo;
    }


    /**
     * returns player 1's Canada pile
     * 
     * @return player 1's Canada pile
     */
    public LinkedList<Card> getGreenlandOne()
    {
        return greenlandOne;
    }


    /**
     * returns player 2's Greenland pile
     * 
     * @return player 2's Greenland pile
     */
    public LinkedList<Card> getGreenlandTwo()
    {
        return greenlandTwo;
    }


    /**
     * returns player 1's Norway pile
     * 
     * @return player 21's Norway pile
     */
    public LinkedList<Card> getNorwayOne()
    {
        return norwayOne;
    }


    /**
     * returns player 2's Norway pile
     * 
     * @return player 2's Norway pile
     */
    public LinkedList<Card> getNorwayTwo()
    {
        return norwayTwo;
    }


    /**
     * returns player 1's Russia pile
     * 
     * @return player 1's Russia pile
     */
    public LinkedList<Card> getRussiaOne()
    {
        return russiaOne;
    }


    /**
     * returns player 2's Russia pile
     * 
     * @return player 2's Russia pile
     */
    public LinkedList<Card> getRussiaTwo()
    {
        return russiaTwo;
    }


    /**
     * returns Alaska's discard pile
     * 
     * @return Alaska's discard pile
     */
    public Stack<Card> getAlaskaDiscard()
    {
        return alaskaDiscard;
    }


    /**
     * returns Antarctica's discard pile
     * 
     * @return Antarctica's discard pile
     */
    public Stack<Card> getAntarcticaDiscard()
    {
        return antarcticaDiscard;
    }


    /**
     * returns Canada's discard pile
     * 
     * @return Canada's discard pile
     */
    public Stack<Card> getCanadaDiscard()
    {
        return canadaDiscard;
    }


    /**
     * returns Greenland's discard pile
     * 
     * @return Greenland's discard pile
     */
    public Stack<Card> getGreenlandDiscard()
    {
        return greenlandDiscard;
    }


    /**
     * returns Norway's discard pile
     * 
     * @return Norway's discard pile
     */
    public Stack<Card> getNorwayDiscard()
    {
        return norwayDiscard;
    }


    /**
     * returns Russia's discard pile
     * 
     * @return Russia's discard pile
     */
    public Stack<Card> getRussiaDiscard()
    {
        return russiaDiscard;
    }


    /**
     * returns player one
     * 
     * @return playerOne
     */
    public Player getPlayerOne()
    {
        return playerOne;
    }


    /**
     * returns player two
     * 
     * @return playerTwo
     */
    public Player getPlayerTwo()
    {
        return playerTwo;
    }
}
