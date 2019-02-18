package projectGlacier;

import java.io.*;
import java.net.*;


public class Client implements Serializable
{
    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = 6812073225614241219L;

    /**
     * this client's socket
     */
    Socket clientSocket = null;

    /**
     * name of host
     */
    String hostname;

    /**
     * Socket's object output stream
     */
    transient ObjectOutputStream out = null;

    /**
     * Socket's object input stream
     */
    transient ObjectInputStream in = null;

    /**
     * this game window
     */
    GameWindow game;


    /**
     * Constructor
     * 
     * @param hostname
     *            - IP of server
     */
    public Client( String hostname )
    {
        this.hostname = hostname;
    }


    /**
     * Runs this client
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run() throws IOException, ClassNotFoundException
    {
        int port = 6785;

        try
        {
            clientSocket = new Socket( InetAddress.getByName( hostname ), port );
            out = new ObjectOutputStream( clientSocket.getOutputStream() );
            in = new ObjectInputStream( clientSocket.getInputStream() );
        }
        catch ( UnknownHostException e )
        {
            System.err.println( "Don't know about host: " + hostname );
        }
        catch ( IOException e )
        {
            System.err.println( "Couldn't get I/O for the connection to: " + hostname );
        }

        Deck deck = new Deck();
        Player one = new Player( "TIM", deck.newHand() );
        Player two = new Player( "FRANK", deck.newHand() );
        Board board = new Board( one, two, deck );
        this.game = new GameWindow( board );
        out.writeObject( game );
        game.setMyTurn( 2 );
        game.setClient( this );
        waitAndAccept();
    }


    /**
     * Sends a message to the server
     * 
     * @param str
     *            - the message
     * @throws IOException
     */
    public void send( String str ) throws IOException
    {
        try
        {
            out.writeObject( str );
        }
        catch ( SocketException e )
        {
            end();
        }
    }


    /**
     * Waits for incoming updates from the server
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void waitAndAccept() throws IOException, ClassNotFoundException
    {
        while ( true )
        {
            try
            {
                String message = (String)in.readObject();
                System.out.println( message );
                if ( message.equals( "NT" ) ) // Next turn
                {
                    game.nextTurn();
                    break;
                }
                else if ( message.equals( "DDEC" ) ) // draw deck
                {
                    game.board.drawDeck();
                }
                else if ( message.length() > 3 && message.substring( 0, 4 ).equals( "DDIS" ) ) // draw
                                                                                               // discard
                {
                    game.board.drawDiscard( Integer.parseInt( message.substring( 4 ) ) );
                    game.bp.redo();
                }
                else if ( message.length() > 2 && message.substring( 0, 3 ).equals( "PLA" ) ) // place
                                                                                              // card
                {
                    game.board.place( game.board.getPlayerTwo(),
                        getCardFromName( message.substring( 3 ) ) );
                    game.bp.redo();
                }
                else if ( message.length() > 2 && message.substring( 0, 3 ).equals( "DIS" ) ) // discard
                                                                                              // card
                {
                    game.board.discard( game.board.getPlayerTwo(),
                        getCardFromName( message.substring( 3 ) ) );
                    game.bp.redo();
                }
                else if ( message.length() > 2 && message.substring( 0, 3 ).equals( "DED" ) )
                {
                    int mission = Integer.parseInt( message.substring( 3 ) );
                    new DisasterWindow( mission, false );
                    game.board.disaster( game.board.getPlayerTwo(), mission );
                }
                else
                {
                    end();
                }
            }
            catch ( SocketException e )
            {
            }
            catch ( EOFException e )
            {
            }
        }

        System.out.println( "END" );
    }


    /**
     * Returns the card from the name
     * 
     * @param message
     *            - the card name
     * @return the card
     * @throws NumberFormatException
     * @throws IOException
     */
    public Card getCardFromName( String message ) throws NumberFormatException, IOException
    {
        Card result;
        if ( message.substring( 0, 5 ).equals( "Wager" ) )
        {
            result = new Wager( Integer.parseInt( message.substring( 5, 6 ) ), message );
        }
        else if ( message.substring( 0, 7 ).equals( "Mission" ) )
        {
            if ( message.substring( 8, 10 ).equals( "10" ) )
            {
                result = new MissionCard( Integer.parseInt( message.substring( 7, 8 ) ),
                    10,
                    message );
            }
            else
            {
                result = new MissionCard( Integer.parseInt( message.substring( 7, 8 ) ),
                    Integer.parseInt( message.substring( 8, 9 ) ),
                    message );
            }
        }
        else if ( message.substring( 0, 8 ).equals( "Sabotage" ) )
        {
            result = new Sabotage( Integer.parseInt( message.substring( 8, 9 ) ),
                -Integer.parseInt( message.substring( 9, 10 ) ),
                message );
        }
        else
        {
            result = new Disaster( message );
        }
        return result;
    }


    /**
     * Ends game and connection
     * 
     * @throws IOException
     */
    public void end() throws IOException
    {
        game.dispose();
        try
        {
            send( "END" );
            out.close();
            in.close();
            clientSocket.close();
        }
        catch ( SocketException e )
        {
        }
    }
}
