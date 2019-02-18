package projectGlacier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Window to join existing game
 *
 * @author Radence Tsow
 * @version May 7, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
@SuppressWarnings("serial")
public class JoinWindow extends JFrame
{
    /**
     * this
     */
    JFrame frame = new JFrame( "Join Game" );

    /**
     * text box for typing out IP address
     */
    JTextField textBox = new JTextField( 20 );


    /**
     * Constructor
     * 
     * @throws IOException
     */
    public JoinWindow() throws IOException
    {
        BackgroundPane background = new BackgroundPane();
        background.setBackground( ImageIO.read( new File( "Background.gif" ) ) );

        BackListener backListener = new BackListener();
        JButton BackBtn = new JButton( "Back" );
        BackBtn.addActionListener( backListener );

        JoinListener joinListener = new JoinListener();
        JButton JoinBtn = new JButton( "Join" );
        JoinBtn.addActionListener( joinListener );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setContentPane( background );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        frame.add( new JLabel( "Enter IP address:" ), gbc );
        frame.add( textBox, gbc );
        frame.add( JoinBtn, gbc );
        frame.add( BackBtn, gbc );
        frame.pack();
        frame.setVisible( true );
    }


    /**
     * Listens for when the player wants to exits from screen
     *
     * @author William
     * @version May 25, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    private class BackListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                new MainWindow();
            }
            catch ( IOException e1 )
            {
                e1.printStackTrace();
            }
            frame.dispose(); // Remove
            frame.setVisible( false );
            frame.setEnabled( false );
        }
    }


    /**
     * Listens for when the player wants to join the game
     *
     * @author William Zong
     * @version May 25, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    private class JoinListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            String IP = textBox.getText();
            Client client = new Client( IP );
            try
            {
                client.run();
                frame.dispose(); // Remove
                frame.setVisible( false );
                frame.setEnabled( false );
            }
            catch ( IOException e1 )
            {
                e1.printStackTrace();
            }
            catch ( ClassNotFoundException e1 )
            {
                e1.printStackTrace();
            }
        }
    }
}
