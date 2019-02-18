package projectGlacier;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 * ProjectGlacier tests:
 *   Player
 *   Deck
 *   Card (and its subclasses)
 *   Board
 *
 * @author Madeline Shao
 * @version 5/2/18
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 * 
 * @author Sources: none
 *
 */
public class JUProjectGlacierTest
{
    // --Test Player
    /**
     * Player tests:
     *   playerConstructor - constructs Player and then compares values
     *   playerDrawDeck - checks that hand contains drawn card
     *   playerDrawDiscard - checks that hand contains the drawn card
     *   playerDrawDeckTooMuch - (if player tries to draw card when they have more than 10 cards) returned value should be -1
     *   playerDrawDiscardTooMuch - (if player tries to draw card when they have more than 10 cards) returned value should be -1
     *   playerDrawDeckEmpty - (if player tries to draw from empty deck) returns -1
     *   playerDrawDiscardEmpty - (if player tries to draw from empty discard pile) returns -1
     *   playerPlace - checks that hand does not contain placed card, returned value should be 0
     *   playerDiscard - checks that hand does not contain discarded card, returned value should be 0
     *   playerPlaceDNE - (if player tries to place a card they don't have) returned value should be -1
     *   playerDiscardDNE - (if player tries to discard a card they don't have) returned value should be -1
     *   playerPlaceTooLittle - (if player tries to place card when they have less than 10 cards) returned value should be -1
     *   playerDiscardTooLittle - (if player tries to discard card when they have less than 10 cards) returned value should be -1
     *   playerGetHand - compares value returned to constructed value
     *   playerGetName - compares value returned to constructed value
     *   playerDisaster - compares value returned to expected value
     *   
     * @author Madeline Shao
     */
    private String name1 = "one";
    private String name2 = "two";
    private Deck deck = new Deck();
    private ArrayList<Card> hand1 = deck.newHand();
    private ArrayList<Card> hand2 = deck.newHand();
    private Board board = new Board(new Player(name1, hand1), new Player(name2, hand2), deck);

    @Test
    public void playerConstructor()
    {
        Player p = new Player(name1, hand1);
        assertNotNull(p);
        assertTrue(p.getName().equals( name1 ) && p.getHand().equals( hand1 ));
    }
    
    @Test
    public void playerGetHand()
    {
        Player p = new Player(name1, hand1);
        assertTrue(p.getHand().equals( hand1 ));
    }

    @Test
    public void playerGetName()
    {
        Player p = new Player(name1, hand1);
        assertTrue(p.getName().equals( name1 ));
    }

    @Test
    public void playerPlace()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        int i = p.place( board, c );
        if (!(c instanceof Wager))
            assertFalse( p.getHand().contains( c ));
        assertTrue(i == 0);
    }

    @Test
    public void playerDiscard()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        int i = p.discard( board, c );
        if (!(c instanceof Wager))
            assertFalse( p.getHand().contains( c ));
        assertTrue(i == 0);
    }
    
    @Test
    public void playerPlaceDNE()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (deck.get( j ) instanceof Disaster || deck.get( j) instanceof Wager )
        {
            j++;
        }
        Card c = deck.get( j );
        int i = p.place( board, c );
        if (!(c instanceof Disaster || c instanceof Wager))
            assertFalse( p.getHand().contains( c ));
        assertTrue(i == -1);
    }

    @Test
    public void playerDiscardDNE()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (deck.get( j ) instanceof Disaster || deck.get( j) instanceof Wager )
        {
            j++;
        }
        Card c = deck.get( j );
        int i = p.discard( board, c );
        if (!(c instanceof Disaster || c instanceof Wager))
            assertFalse( p.getHand().contains( c ));
        assertTrue(i == -1);
    }
    
    @Test
    public void playerPlaceTooLittle()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.place( board, c );
        c = p.getHand().get(0);
        int i = p.place( board, c );
//        if (!(c instanceof Disaster || c instanceof Wager))
        assertTrue( p.getHand().contains( c ));
        assertTrue(i == -2);
    }

    @Test
    public void playerDiscardTooLittle()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.discard( board, c );
        c = p.getHand().get(0);
        int i = p.discard( board, c );
//        if (!(c instanceof Disaster || c instanceof Wager))
        assertTrue( p.getHand().contains( c ));
        assertTrue(i == -2);
    }

    @Test
    public void playerDrawDeck()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.discard( board, c );
        c = deck.get( 0 );
        int i = p.drawDeck( board );
        p.place( board,  new MissionCard(3, 3) );
        if (c instanceof Disaster)
        {
            assertTrue(i == 3);
        }
        else
        {
            assertTrue(p.getHand().contains( c ));
            if (!(c instanceof Wager))
                assertFalse(deck.contains( c ));
            assertTrue(i == -3);
        }
    }

    @Test
    public void playerDrawDiscard()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.discard( board, c );
        int i = p.drawDiscard( board, c.getMission() );
        assertTrue(p.getHand().contains( c ));
        assertTrue(i == 0);
    }
    
    @Test
    public void playerDrawDeckTooMuch()
    {
        Player p = new Player(name1, hand1);
        Card c = deck.get( 0 );
        int i = p.drawDeck( board );
        if (!(c instanceof Disaster || c instanceof Wager))
            assertFalse(p.getHand().contains( c ));
        assertTrue(deck.contains( c ));
        assertTrue(i == -2);
    }

    @Test
    public void playerDrawDiscardTooMuch()
    {
        Player p = new Player(name1, hand1);
        Player p2 = new Player(name2, hand2);
        int j = 0;
        while (p2.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p2.getHand().get( j );
        p2.discard( board, c );
        
        int i = p.drawDiscard( board, c.getMission());
        if (!(c instanceof Wager))
            assertFalse(p.getHand().contains( c ));
        assertTrue(i == -2);
    }
    
    @Test
    public void playerDrawDeckEmpty()
    {
        Player p = new Player(name1, hand1);
        while (!deck.isEmpty())
        {
            deck.remove( 0 );
        }
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.discard( board, c );
        int i = p.drawDeck( board );
        assertTrue(i == -1);
    }
    
    @Test
    public void playerDrawDiscardEmpty()
    {
        Player p = new Player(name1, hand1);
        int j = 0;
        while (p.getHand().get( j ) instanceof Disaster)
        {
            j++;
        }
        Card c = p.getHand().get( j );
        p.discard( board, c );
        int i; 
        if (c.getMission() != 0)
        {
            i = p.drawDiscard( board, c.getMission() - 1 );
        }
        else
        {
            i = p.drawDiscard( board, 3 );
        }
//        System.out.println(p.getHand().size() + "" + i );
//        System.out.println(i);
        assertTrue(i == -1);
        
    }
    
    @Test
    public void playerDisaster()
    {
        Player p = new Player(name1, hand1);
        assertFalse(p.disaster( board, 2 ));
        board.place( p, new MissionCard(2, 2) );
        assertTrue(p.disaster( board, 2 ));
    }
    
    // --Test Deck
    
    /**
     * Deck tests:
     *   deckConstructor - constructs Deck and then compares values
     *   deckNewHand - checks that returned value meets requirements
     *   @author Madeline Shao
     */

    @Test
    public void deckConstructor()
    {
        Deck deck = new Deck();
        
        assertNotNull(deck);
      //test Mission cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 2; k < 11; k++ )
            {
                assertTrue(deck.contains( new MissionCard(i, k) ));
            }
        }
        //test Disaster cards
        for( int i = 0; i < 3; i++)
        {
            assertTrue(deck.contains( new Disaster() ));
        }
        //test Wager cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 0; k < 3; k++ )
            {
                assertTrue(deck.contains( new Wager(i) ));
            }
        }
        //test Sabotage cards
        for( int i = 0; i < 6; i++ )
        {
            for( int k = 1; k < 4; k++ )
            {
                assertTrue(deck.contains( new Sabotage(i, -k) ));
            }
        }
        
        Card c = new MissionCard(0, 0);
        deck.remove(  c);
        assertFalse(deck.contains( c ));
    }
    
    @Test
    public void deckNewHand()
    {
        Card[] cards = new Card[10];
        
        int count = 0;
        int j = 0;
        while (count < 10)
        {
            if (!(deck.get(j) instanceof Disaster))
            {
                cards[count] = deck.get( j );
                count++;
            }
            j++;
        }
        
        
        ArrayList<Card> hand = deck.newHand();
        
        assertTrue(hand.size() == 10);
        
        for (int i = 0; i < 10; i++)
        {
            assertTrue(hand.contains( cards[i] ));
            assertFalse(hand.get(i) instanceof Disaster);
            if (!(cards[i] instanceof Wager))
            {
                assertFalse(deck.contains( cards[i] ));
            }
        }
        
        ArrayList<Card> handTwo = deck.newHand();
        for (int i = 0; i < 10; i++)
        {
            if (!(hand.get( i ) instanceof Wager))
            {
                assertFalse(handTwo.contains( hand.get( i ) ));
            }
        }
    }
    
// --Test Card
    
    /**
     * Card tests:
     *   missionCardConstructor - constructs MissionCard and then compares values
     *   sabotageConstructor - constructs Sabotage and then compares values
     *   wagerConstructor - constructs Wager and then compares values
     *   disasterConstructor - constructs Disaster and then compares values
     *   cardGetMission - compares value returned to constructed value
     *   cardGetNumber - compares value returned to constructed value
     *   cardEqual - compares two equal cards
     *   @author Madeline Shao
     */

    @Test
    public void missionCardConstructor()
    {
        Card mission = new MissionCard(3, 3);
        assertNotNull(mission);
        assertTrue(mission.getMission() == 3 && mission.getNumber() == 3);
    }
    
    @Test
    public void sabotageConstructor()
    {
        Card sabotage = new Sabotage(3, -3);
        assertNotNull(sabotage);
        assertTrue(sabotage.getMission() == 3 && sabotage.getNumber() == -3);
    }
    
    @Test
    public void wagerConstructor()
    {
        Card wager = new Wager(3);
        assertNotNull(wager);
        assertTrue(wager.getMission() == 3 && wager.getNumber() == 1);
    }
    
    @Test
    public void disaster()
    {
        Card disaster = new Disaster();
        assertNotNull(disaster);
        assertTrue(disaster.getMission() == -1 && disaster.getNumber() == 0);
    }
    
    @Test
    public void cardGetMission()
    {
        Card mission = new MissionCard(3, 4);
        assertTrue(mission.getMission() == 3);
    }

    @Test
    public void cardGetNumber()
    {
        Card mission = new MissionCard(3, 4);
        assertTrue(mission.getNumber() == 4);
    }
    
    @Test
    public void cardEquals()
    {
        Card mission = new MissionCard(3, 4);
        Card mission2 = new MissionCard(3, 4);
        assertTrue(mission.equals( mission2 ));
        
        Card mission3 = new MissionCard(4, 3);
        assertFalse(mission.equals( mission3 ));
    }
    
// --Test Board
    
    /**
     * Board tests:
     *   boardConstructor - constructs board and then compares values
     *   boardCalculateScores - compares value returned to constructed value 
     *   boardCalculateMissionScore - compares value returned to constructed value
     *   boardDrawDeck - checks that deck no longer contains draw card
     *   boardDrawDiscard - checks discard pile no longer contains drawn card
     *   boardPlace - checks that respective pile contains card
     *   boardDiscard - checks that respective pile contains card
     *   boardDisaster - checks that correct mission is deleted
     *   
     *   @author Madeline Shao
     */
    
    private String n1 = "one";
    private String n2 = "two";
    private Deck d = new Deck();
    private ArrayList<Card> h1 = deck.newHand();
    private ArrayList<Card> h2 = deck.newHand();
    private Player player1 = new Player(n1, h1);
    private Player player2 = new Player(n2, h2);

    @Test
    public void boardConstructor()
    {
        Board b = new Board(player1, player2, d);
        assertNotNull(b);
        assertTrue(player1.equals( b.getPlayerOne() ));
        assertTrue(player2.equals( b.getPlayerTwo() ));
    }
    
    @Test
    public void boardCalculateScores()
    {
        Board b = new Board(player1, player2, d);
        
        Card c = new MissionCard(0, 3);
        b.place( player1, c );
        c = new MissionCard(0, 6);
        b.place( player1, c );
        c = new MissionCard(0, 9);
        b.place( player1, c );
        
        c = new Wager(1);
        b.place( player1, c );
        c = new MissionCard(1, 3);
        b.place( player1, c );
        c = new MissionCard(1, 6);
        b.place( player1, c );
        c = new MissionCard(1, 9);
        b.place( player1, c );
        
        c = new Wager(2);
        b.place( player1, c );
        b.place( player1, c );
        b.place( player1, c );
        c = new MissionCard(2, 3);
        b.place( player1, c );
        c = new MissionCard(2, 6);
        b.place( player1, c );
        c = new MissionCard(2, 9);
        b.place( player1, c );
        
        c = new MissionCard(3, 3);
        b.place( player1, c );
        c = new MissionCard(3, 6);
        b.place( player1, c );
        c = new MissionCard(3, 9);
        b.place( player1, c );
        c = new Sabotage(3, -4);
        b.place( player2, c );
        
        c = new Wager(4);
        b.place( player1, c );
        c = new MissionCard(4, 3);
        b.place( player1, c );
        c = new Sabotage(4, -4);
        b.place( player2, c );
        c = new MissionCard(4, 6);
        b.place( player1, c );
        c = new MissionCard(4, 9);
        b.place( player1, c );
        
        c = new Wager(5);
        b.place( player1, c );
        c = new MissionCard(5, 2);
        b.place( player1, c );
        c = new MissionCard(5, 3);
        b.place( player1, c );
        c = new MissionCard(5, 4);
        b.place( player1, c );
        c = new MissionCard(5, 5);
        b.place( player1, c );
        c = new Sabotage(5, -4);
        b.place( player2, c );
        c = new MissionCard(5, 6);
        b.place( player1, c );
        c = new MissionCard(5, 7);
        b.place( player1, c );
        c = new Sabotage(5, -7);
        b.place( player2, c );
        c = new MissionCard(5, 10);
        b.place( player1, c );

        assertTrue(b.calculateScores()[1] == 0);
        assertTrue(b.calculateScores()[0] == 120);
    }

    @Test
    public void boardCalculateMissionScore()
    {
        Board b = new Board(player1, player2, d);
        
        assertTrue(b.calculateMissionScore( b.getAlaskaOne() ) == 0);
        
        Card c = new MissionCard(0, 3);
        b.place( player1, c );
        c = new MissionCard(0, 6);
        b.place( player1, c );
        c = new MissionCard(0, 9);
        b.place( player1, c );
        assertTrue(b.calculateMissionScore( b.getAlaskaOne() ) == 8);
        
        c = new Wager(1);
        b.place( player1, c );
        c = new MissionCard(1, 3);
        b.place( player1, c );
        c = new MissionCard(1, 6);
        b.place( player1, c );
        c = new MissionCard(1, 9);
        b.place( player1, c );
        assertTrue(b.calculateMissionScore( b.getAntarcticaOne() ) == 16);
        
        c = new Wager(2);
        b.place( player1, c );
        b.place( player1, c );
        b.place( player1, c );
        c = new MissionCard(2, 3);
        b.place( player1, c );
        c = new MissionCard(2, 6);
        b.place( player1, c );
        c = new MissionCard(2, 9);
        b.place( player1, c );
        assertTrue(b.calculateMissionScore( b.getCanadaOne() ) == 32);
        
        c = new MissionCard(3, 3);
        b.place( player1, c );
        c = new MissionCard(3, 6);
        b.place( player1, c );
        c = new MissionCard(3, 9);
        b.place( player1, c );
        c = new Sabotage(3, -4);
        b.place( player2, c );
        assertTrue(b.calculateMissionScore( b.getGreenlandOne() ) == 4);
        
        c = new Wager(4);
        b.place( player1, c );
        c = new MissionCard(4, 3);
        b.place( player1, c );
        c = new Sabotage(4, -4);
        b.place( player2, c );
        c = new MissionCard(4, 6);
        b.place( player1, c );
        c = new MissionCard(4, 9);
        b.place( player1, c );
        assertTrue(b.calculateMissionScore( b.getNorwayOne() ) == 8);
        
        c = new Wager(5);
        b.place( player1, c );
        c = new MissionCard(5, 2);
        b.place( player1, c );
        c = new MissionCard(5, 3);
        b.place( player1, c );
        c = new MissionCard(5, 4);
        b.place( player1, c );
        c = new MissionCard(5, 5);
        b.place( player1, c );
        c = new Sabotage(5, -4);
        b.place( player2, c );
        c = new MissionCard(5, 6);
        b.place( player1, c );
        c = new MissionCard(5, 7);
        b.place( player1, c );
        c = new Sabotage(5, -7);
        b.place( player2, c );
        c = new MissionCard(5, 10);
        b.place( player1, c );
        assertTrue(b.calculateMissionScore( b.getRussiaOne() ) == 52);
        
        
    }
    
    @Test
    public void boardDrawDeck()
    {
        Board b = new Board(player1, player2, d);
        Card c = d.get( 0 );
        assertTrue(b.drawDeck().equals( c ));
        while (!d.isEmpty())
        {
            b.drawDeck();
        }
        assertNull(b.drawDeck());
    }
    
    @Test
    public void boardDrawDiscard()
    {
        Board b = new Board(player1, player2, d);
        assertNull(b.drawDiscard( 3 ));
        
        Card card = new MissionCard(0, 3);
        b.discard( player1, card );
        Card c = b.drawDiscard(0);
        assertTrue(c.equals( card )); 
        assertTrue(b.getAlaskaDiscard().isEmpty());
    }
    
    @Test
    public void boardPlace()
    {
        Board b = new Board(player1, player2, d);
        
        Card c = new MissionCard(0, 3);
        assertTrue(b.place( player1, c ));
        assertTrue(b.getAlaskaOne().contains( c ));
        
        c = new Wager(1);
        assertTrue(b.place( player2, c ));
        assertTrue(b.place( player2, c ));
        assertTrue(b.getAntarcticaTwo().contains( c ));
        
        c = new MissionCard(1, 3);
        assertTrue(b.place( player2, c ));
        assertTrue(b.getAntarcticaTwo().contains( c ));
        
        c = new MissionCard(1, 2);
        assertFalse(b.place(player2, c));
        assertFalse(b.getAntarcticaTwo().contains( c ));
        
        c = new Wager(1);
        assertFalse(b.place( player2, c ));
        
        c = new Sabotage(1, -3);
        assertTrue(b.place( player1, c ));
        assertTrue(b.getAntarcticaTwo().contains( c ));
        
        assertTrue(b.place( player2, c ));
        assertTrue(b.getAntarcticaOne().contains( c ));
        
        c = new MissionCard(1, 2);
        assertFalse(b.place( player2, c ));
        assertFalse(b.getAntarcticaTwo().contains( c ));
        
        c = new MissionCard(1, 4);
        assertTrue(b.place( player2, c ));
        assertTrue(b.getAntarcticaTwo().contains( c ));
        
        c = new Wager(1);
        assertTrue(b.place( player1, c ));
        assertTrue(b.getAntarcticaOne().contains( c ));
    }
    
    @Test
    public void boardDiscard()
    {
        Board b = new Board(player1, player2, d);
        Card c = new MissionCard(0, 3);
        b.discard( player1, c ); 
        assertTrue(b.getAlaskaDiscard().contains( c ));
    }
    
    @Test
    public void boardDisaster()
    {
        Board b = new Board(player1, player2, d);
        assertFalse(b.disaster( player1, 2 ));
        b.place( player1, new MissionCard(2, 2) );
        assertTrue(b.disaster( player1, 2 ));
        assertTrue(b.getCanadaOne().isEmpty());
    }    
}

