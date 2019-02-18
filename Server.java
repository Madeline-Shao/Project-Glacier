package projectGlacier;

import java.io.*;
import java.net.*;


public class Server implements Serializable
{
    // declare a server socket and a client socket for the server

    /**
     * Serial UID used for serialization
     */
    private static final long serialVersionUID = 4220718854174160756L;

    /**
     * this
     */
    Server me;

    /**
     * This server
     */
    transient ServerSocket echoServer = null;

    /**
     * Client's socket
     */
    transient Socket clientSocket = null;

    /**
     * This server connection
     */
    transient ServerConnection connect = null;

    /**
     * This game window
     */
    GameWindow game = null;

    /**
     * Port number
     */
    int port;


    /**
     * Constructor
     * 
     * @param port
     *            - port number
     */
    public Server( int port )
    {
        me = this;
        this.port = port;
    }


    /**
     * Starts this server
     */
    public void startServer()
    {
        try
        {
            echoServer = new ServerSocket( port );
        }
        catch ( IOException e )
        {
            System.out.println( e );
        }

        System.out.println( "Waiting for connections. Only one connection is allowed." );

        try
        {
            clientSocket = echoServer.accept();
            ServerConnection oneconnection = new ServerConnection( clientSocket, this );
            connect = oneconnection;
            oneconnection.run();
        }
        catch ( IOException e )
        {
            System.out.println( e );
        }
    }


    /**
     * Sends a message to the client
     * 
     * @param str
     *            - message
     * @throws IOException
     */
    public void send( String str ) throws IOException
    {
        try
        {
            connect.out.writeObject( str );
        }
        catch ( SocketException e )
        {
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
                String message = (String)connect.in.readObject();
                System.out.println( message );
                if ( message.equals( "NT" ) ) // Next turn
                {
                    System.out.println( "next turn" );
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
     * Handles the connection with client
     *
     * @author William Zong
     * @version May 25, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    class ServerConnection
    {
        /**
         * The socket's object input stream
         */
        ObjectInputStream in;

        /**
         * The socket's object output stream
         */
        ObjectOutputStream out;

        /**
         * Client's socket
         */
        Socket clientSocket;

        /**
         * The server
         */
        Server server;


        /**
         * Constructor
         * 
         * @param clientSocket
         *            - client's socket
         * @param server
         *            - the server
         * @throws IOException
         */
        public ServerConnection( Socket clientSocket, Server server ) throws IOException
        {
            this.clientSocket = clientSocket;
            this.server = server;
            System.out.println( "Connection established with: " + clientSocket );
            try
            {
                out = new ObjectOutputStream( clientSocket.getOutputStream() );
                in = new ObjectInputStream( clientSocket.getInputStream() );
            }
            catch ( IOException e )
            {
                System.out.println( e );
            }
        }


        /**
         * Runs the connection
         * 
         * @throws IOException
         */
        public void run() throws IOException
        {
            GameWindow temp;

            try
            {
                temp = (GameWindow)in.readObject();
            }
            catch ( ClassNotFoundException e )
            {
                e.printStackTrace( System.out );
                return;
            }

            Deck deck = temp.board.deck;
            Player one = temp.board.getPlayerTwo();
            Player two = temp.board.getPlayerOne();
            Board board = new Board( one, two, deck );
            GameWindow game = new GameWindow( board );
            game.setMyTurn( 1 );
            game.setServer( me );
            server.game = game;
        }


        /**
         * Ends the connection
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
}