package projectGlacier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;


/**
 * Handles GUI representation of the game board
 *
 * @author William Zong
 * @version May 19, 2018
 * @author Period: 4
 * @author Assignment: Project Glacier
 *
 * @author Sources: none
 */
public class GameWindow extends JFrame
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = -4646567238698957872L;

    /**
     * This frame
     */
    JFrame frame = new JFrame( "Project Glacier" );

    /**
     * Keeps track of which turn it is
     */
    int turnCounter = 1;

    /**
     * Keeps track of which turn is this players
     */
    int myTurn = 0;

    /**
     * The server
     */
    Server myServer = null;

    /**
     * The client
     */
    Client myClient = null;

    /**
     * The board
     */
    Board board;

    /**
     * The board panel
     */
    BoardPanel bp;

    /**
     * The hand panel
     */
    HandPanel hp;

    /**
     * Place mission button
     */
    JButton placeMission = new JButton( "PLACE ON MISSION" );

    /**
     * Place discard button
     */
    JButton placeDiscard = new JButton( "PLACE IN DISCARDS" );

    /**
     * Draw deck button
     */
    JButton drawDeck;

    /**
     * Draw discard button
     */
    JButton drawDiscard = new JButton();

    /**
     * Last discard's mission number
     */
    int lastDiscard;

    /**
     * Currently pressed card
     */
    Card pressedCard = null;

    /**
     * Width of hand cards
     */
    int handX;

    /**
     * Height of hand cards
     */
    int handY;

    /**
     * Width of mission cards
     */
    int missionX;

    /**
     * Height of mission cards
     */
    int missionY;


    /**
     * Constructor
     * 
     * @param b
     *            - board
     * @param p1
     *            - player one
     * @param p2
     *            - player two
     * @throws IOException
     */
    public GameWindow( Board b ) throws IOException
    {
        board = b;

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setLayout( new GridBagLayout() );
        Dimension xy = Toolkit.getDefaultToolkit().getScreenSize();
        handX = (int)( xy.getWidth() / 13 );
        handY = (int)( xy.getHeight() / 5 );
        missionX = (int)( xy.getWidth() / 15 );
        missionY = (int)( xy.getHeight() / 6 );
        frame.setMinimumSize( xy );

        GridBagConstraints c = new GridBagConstraints();

        hp = new HandPanel();

        c.fill = GridBagConstraints.NONE;
        c.ipady = 20;
        c.ipadx = 0;
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;

        bp = new BoardPanel();

        frame.pack();
        frame.add( hp, c );

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.gridy = 0;

        frame.pack();
        frame.add( bp, c );

        c.ipadx = 280;
        c.insets = new Insets( 0, 0, 30, 0 );
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridheight = 1;

        drawDeck = new JButton( "DRAW FROM DECK: " + ( board.deck.size() - 1 ) );
        placeMission.addActionListener( new PlaceMissionListener() );
        placeDiscard.addActionListener( new PlaceDiscardListener() );
        drawDeck.addActionListener( new DrawDeckListener() );
        drawDiscard.addActionListener( new DrawDiscardListener() );

        c.fill = GridBagConstraints.NONE;
        frame.pack();
        frame.add( placeMission, c );
        c.gridy = 1;
        frame.pack();
        frame.add( placeDiscard, c );
        c.gridy = 2;
        frame.pack();
        frame.add( drawDeck, c );
        c.gridy = 3;
        drawDiscard.setLayout( new FlowLayout() );
        JLabel text = new JLabel( "DRAW FROM DISCARDS" );
        JTextField discardMission = new JTextField( 1 );
        drawDiscard.add( text );
        drawDiscard.add( discardMission );
        frame.pack();
        frame.add( drawDiscard, c );
        frame.pack();
        frame.repaint();
        frame.setVisible( true );
    }


    /**
     * Sets current turn to the next turn
     */
    public void nextTurn()
    {
        if ( turnCounter == 1 )
        {
            turnCounter = 2;
        }
        else
        {
            turnCounter = 1;
        }
    }


    /**
     * Sets this player's turn
     * 
     * @param myTurn
     *            - my turn
     */
    public void setMyTurn( int myTurn )
    {
        this.myTurn = myTurn;
    }


    /**
     * Sets this player's server
     * 
     * @param serv
     *            - server
     */
    public void setServer( Server serv )
    {
        myServer = serv;
    }


    /**
     * Sets this player's client
     * 
     * @param cl
     *            - client
     */
    public void setClient( Client cl )
    {
        myClient = cl;
    }


    /**
     * Calls the server's or client's send
     * 
     * @param str
     *            - message
     * @throws IOException
     */
    public void send( String str ) throws IOException
    {
        if ( myServer != null )
        {
            myServer.send( str );
            if ( str.equals( "NT" ) )
            {
                try
                {
                    myServer.waitAndAccept();
                }
                catch ( ClassNotFoundException e )
                {
                    e.printStackTrace();
                }
            }
        }
        if ( myClient != null )
        {
            myClient.send( str );
            if ( str.equals( "NT" ) )
            {
                try
                {
                    myClient.waitAndAccept();
                }
                catch ( ClassNotFoundException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Listens for when the player wants to place a card onto a mission
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class PlaceMissionListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            if ( myTurn == turnCounter && pressedCard != null
                && board.getPlayerOne().getHand().size() == 10 )
            {
                board.place( board.getPlayerOne(), pressedCard );
                try
                {
                    hp.redo();
                    bp.redo();
                    send( "PLA" + pressedCard.fileName );
                }
                catch ( IOException e1 )
                {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * Listens for when the player wants to place a card into the discards
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class PlaceDiscardListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            if ( myTurn == turnCounter && pressedCard != null
                && board.getPlayerOne().getHand().size() == 10 )
            {
                board.discard( board.getPlayerOne(), pressedCard );
                lastDiscard = pressedCard.getMission();
                try
                {
                    hp.redo();
                    bp.redo();
                    send( "DIS" + pressedCard.fileName );
                }
                catch ( IOException e1 )
                {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * Listens for when the player wants to draw a card from the deck
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class DrawDeckListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            if ( myTurn == turnCounter && pressedCard != null
                && board.getPlayerOne().getHand().size() < 10 )
            {
                int num = board.getPlayerOne().drawDeck( board );
                if ( num >= 0 )
                {
                    try
                    {
                        send( "DED" + num );
                    }
                    catch ( IOException e1 )
                    {
                        e1.printStackTrace();
                    }
                }

                System.out.println( board.deck.size() );

                if ( board.deck.size() == 0 )
                {
                    try
                    {
                        endGame();
                    }
                    catch ( IOException e1 )
                    {
                        e1.printStackTrace();
                    }
                }

                try
                {
                    hp.redo();
                    drawDeck.setText( "DRAW FROM DECK: " + ( board.deck.size() - 1 ) );
                    send( "DDEC" );
                    send( "NT" );
                }
                catch ( IOException e1 )
                {
                    e1.printStackTrace();
                }
                lastDiscard = -1;
                pressedCard = null;

                nextTurn();
            }
        }
    }


    /**
     * Listens for when the player wants to draw a card from the discards
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class DrawDiscardListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            if ( myTurn == turnCounter && pressedCard != null
                && board.getPlayerOne().getHand().size() < 10 )
            {
                int mission;
                try
                {
                    mission = Integer
                        .parseInt( ( (JTextField)drawDiscard.getComponent( 1 ) ).getText() );
                }
                catch ( NumberFormatException e1 )
                {
                    return;
                }
                if ( mission == lastDiscard )
                {
                    return;
                }
                int i = board.getPlayerOne().drawDiscard( board, mission );
                if ( i == 0 )
                {
                    try
                    {
                        hp.redo();
                        bp.redo();
                        send( "DDIS" + mission );
                        send( "NT" );
                    }
                    catch ( IOException e1 )
                    {
                        e1.printStackTrace();
                    }
                    lastDiscard = -1;
                    pressedCard = null;
                    nextTurn();
                }
            }
        }
    }


    /**
     * Handles GUI representation of the players hand
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class HandPanel extends JPanel
    {
        /**
         * Serial UID used for serialization
         */
        private static final long serialVersionUID = 8264803620774433053L;

        /**
         * this
         */
        JPanel panel = this;


        /**
         * Constructor
         * 
         * @throws IOException
         */
        public HandPanel() throws IOException
        {
            super( new GridLayout( 1, 10, 5, 0 ) );
            ArrayList<Card> hand = board.getPlayerOne().getHand();
            for ( int i = 0; i < hand.size(); i++ )
            {
                JButton button = new JButton( new ImageIcon( hand.get( i )
                    .getImageFromFile()
                    .getScaledInstance( handX, handY, Image.SCALE_SMOOTH ) ) );
                button.addActionListener( new CardPressListener( hand.get( i ) ) );
                this.add( button );
            }
            this.validate();
            this.setVisible( true );
        }


        /**
         * Redraws the panel
         * 
         * @throws IOException
         */
        public void redo() throws IOException
        {
            removeAll();
            validate();
            repaint();
            ArrayList<Card> hand = board.getPlayerOne().getHand();
            for ( int i = 0; i < hand.size(); i++ )
            {
                JButton button = new JButton( new ImageIcon( hand.get( i )
                    .getImageFromFile()
                    .getScaledInstance( handX, handY, Image.SCALE_SMOOTH ) ) );
                button.addActionListener( new CardPressListener( hand.get( i ) ) );
                add( button );
            }
            validate();
        }


        /**
         * Listens for when the player clicks on a card in their hand
         *
         * @author William Zong
         * @version May 19, 2018
         * @author Period: 4
         * @author Assignment: ProjectGlacier
         *
         * @author Sources: none
         */
        class CardPressListener implements ActionListener
        {
            /**
             * Pressed card
             */
            Card card;


            /**
             * Constructor
             * 
             * @param c
             *            - the card
             */
            public CardPressListener( Card c )
            {
                card = c;
            }


            @Override
            public void actionPerformed( ActionEvent e )
            {
                pressedCard = card;
            }
        }
    }


    /**
     * Handles GUI representation of the playing board
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class BoardPanel extends JPanel
    {
        /**
         * Serial UID used for serialization
         */
        private static final long serialVersionUID = -7283149614364700785L;


        /**
         * Constructor
         * 
         * @throws IOException
         */
        public BoardPanel() throws IOException
        {
            super( new GridLayout( 3, 6, 25, 0 ) );
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new MissionPanel( 2, i ) );
            }
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new DiscardPanel( i ) );
            }
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new MissionPanel( 1, i ) );
            }
            validate();
            setVisible( true );
        }


        /**
         * Redraws the panel
         * 
         * @throws IOException
         */
        public void redo() throws IOException
        {
            removeAll();
            validate();
            repaint();
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new MissionPanel( 2, i ) );
            }
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new DiscardPanel( i ) );
            }
            for ( int i = 0; i < 6; i++ )
            {
                this.add( new MissionPanel( 1, i ) );
            }
            validate();
        }
    }


    /**
     * Handles GUI representation of one mission card
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class MissionPanel extends JPanel
    {
        /**
         * Serial UID used for serialization
         */
        private static final long serialVersionUID = 3103116830503062023L;

        /**
         * The player who owns these missions
         */
        int player;

        /**
         * The mission number
         */
        int mission;


        /**
         * Constructor
         * 
         * @param p
         *            - the player who owns this mission
         * @param m
         *            - the mission
         * @throws IOException
         */
        public MissionPanel( int p, int m ) throws IOException
        {
            player = p;
            mission = m;
            setLayout( new GridBagLayout() );
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.ipadx = 5;
            switch ( mission )
            {
                case 0:
                    if ( p == 1 )
                    {
                        if ( board.getAlaskaOne().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "AlaskaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getAlaskaOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getAlaskaTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "AlaskaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getAlaskaTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
                case 1:
                    if ( p == 1 )
                    {
                        if ( board.getAntarcticaOne().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "AntarcticaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getAntarcticaOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getAntarcticaTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "AntarcticaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getAntarcticaTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
                case 2:
                    if ( p == 1 )
                    {
                        if ( board.getCanadaOne().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "CanadaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getCanadaOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getCanadaTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "CanadaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getCanadaTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
                case 3:
                    if ( p == 1 )
                    {
                        if ( board.getGreenlandOne().size() == 0 )
                        {
                            Card card = new MissionCard( 0, -5, "GreenlandMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getGreenlandOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getGreenlandTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 1, -5, "GreenlandMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getGreenlandTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
                case 4:
                    if ( p == 1 )
                    {
                        if ( board.getNorwayOne().size() == 0 )
                        {
                            Card card = new MissionCard( 2, -5, "NorwayMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getNorwayOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getNorwayTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 3, -5, "NorwayMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getNorwayTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
                case 5:
                    if ( p == 1 )
                    {
                        if ( board.getRussiaOne().size() == 0 )
                        {
                            Card card = new MissionCard( 4, -5, "RussiaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getRussiaOne()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    else
                    {
                        if ( board.getRussiaTwo().size() == 0 )
                        {
                            Card card = new MissionCard( 5, -5, "RussiaMission.gif" );
                            // Card card = new MissionCard( 0, -5,
                            // "CardBack.gif" );
                            ImageIcon image = new ImageIcon( card.getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                        else
                        {
                            ImageIcon image = new ImageIcon( board.getRussiaTwo()
                                .getLast()
                                .getImageFromFile()
                                .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                            JLabel label = new JLabel( "", image, JLabel.CENTER );
                            add( label, c );
                        }
                    }
                    break;
            }

            if ( p == 1 )
            {
                int score = 0;
                switch ( mission )
                {
                    case 0:
                        score = board.calculateMissionScore( board.alaskaOne );
                        break;
                    case 1:
                        score = board.calculateMissionScore( board.antarcticaOne );
                        break;
                    case 2:
                        score = board.calculateMissionScore( board.canadaOne );
                        break;
                    case 3:
                        score = board.calculateMissionScore( board.greenlandOne );
                        break;
                    case 4:
                        score = board.calculateMissionScore( board.norwayOne );
                        break;
                    case 5:
                        score = board.calculateMissionScore( board.russiaOne );
                        break;
                }
                JLabel l = new JLabel( score + "" );
                add( l );
            }
            else
            {
                int score = 0;
                switch ( mission )
                {
                    case 0:
                        score = board.calculateMissionScore( board.alaskaTwo );
                        break;
                    case 1:
                        score = board.calculateMissionScore( board.antarcticaTwo );
                        break;
                    case 2:
                        score = board.calculateMissionScore( board.canadaTwo );
                        break;
                    case 3:
                        score = board.calculateMissionScore( board.greenlandTwo );
                        break;
                    case 4:
                        score = board.calculateMissionScore( board.norwayTwo );
                        break;
                    case 5:
                        score = board.calculateMissionScore( board.russiaTwo );
                        break;
                }
                JLabel l = new JLabel( score + "" );
                add( l );
            }
            validate();
            setVisible( true );
        }
    }


    /**
     * Handles GUI representation of the discard piles
     *
     * @author William Zong
     * @version May 19, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class DiscardPanel extends JPanel
    {
        private static final long serialVersionUID = 6360883252849815785L;

        int mission;


        /**
         * Constructor
         * 
         * @param m
         *            - mission
         */
        public DiscardPanel( int m ) throws IOException
        {
            mission = m;

            switch ( mission )
            {
                case 0:
                    if ( board.getAlaskaDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 0, -5,
                        // "AlaskaDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getAlaskaDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
                case 1:
                    if ( board.getAntarcticaDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 1, -5,
                        // "AntarcticaDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getAntarcticaDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
                case 2:
                    if ( board.getCanadaDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 2, -5,
                        // "CanadaDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getCanadaDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
                case 3:
                    if ( board.getGreenlandDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 3, -5,
                        // "GreenlandDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getGreenlandDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
                case 4:
                    if ( board.getNorwayDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 4, -5,
                        // "NorwayDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getNorwayDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
                case 5:
                    if ( board.getRussiaDiscard().size() == 0 )
                    {
                        // Card card = new MissionCard( 5, -5,
                        // "RussiaDiscard.gif" );
                        Card card = new MissionCard( 0, -5, "CardBack.gif" );
                        ImageIcon image = new ImageIcon( card.getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    else
                    {
                        ImageIcon image = new ImageIcon( board.getRussiaDiscard()
                            .peek()
                            .getImageFromFile()
                            .getScaledInstance( missionX, missionY, Image.SCALE_SMOOTH ) );
                        JLabel label = new JLabel( "", image, JLabel.CENTER );
                        add( label );
                    }
                    break;
            }
            validate();
            setVisible( true );
        }
    }


    /**
     * Ends this game
     * 
     * @throws IOException
     */
    public void endGame() throws IOException
    {
        frame.dispose();
        if ( myServer != null )
        {
            myServer.connect.end();
        }
        if ( myClient != null )
        {
            myClient.end();
        }
        if ( board.calculateScores()[0] > board.calculateScores()[1] )
        {
            new ResultWindow( 0 );
        }
        else if ( board.calculateScores()[0] < board.calculateScores()[1] )
        {
            new ResultWindow( 1 );
        }
        else
        {
            new ResultWindow( 2 );
        }
    }
}
