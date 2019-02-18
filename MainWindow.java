package projectGlacier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Main Window
 *
 * @author Radence Tsow
 * @version May 7, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
    /**
     * this
     */
    JFrame frame = new JFrame( "Project Glacier" );


    /**
     * Constructor
     * 
     * @throws IOException
     */
    public MainWindow() throws IOException
    {

        BackgroundPane background = new BackgroundPane();
        background.setBackground( ImageIO.read( new File( "Background.gif" ) ) );

        StartListener StartListener = new StartListener();
        JButton StartBtn = new JButton( "Start Game" );
        StartBtn.addActionListener( StartListener );

        JoinListener JoinListener = new JoinListener();
        JButton JoinBtn = new JButton( "Join Game" );
        JoinBtn.addActionListener( JoinListener );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setContentPane( background );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel TitleImage = new JLabel();
        TitleImage.setIcon( new ImageIcon( "Title.gif" ) );

        JLabel BlankFiller = new JLabel();
        BlankFiller.setIcon( new ImageIcon( "Blank.gif" ) );

        JLabel CompanyImage = new JLabel();
        CompanyImage.setIcon( new ImageIcon( "MADICE.gif" ) );

        frame.add( BlankFiller, gbc );
        frame.add( BlankFiller, gbc );
        frame.add( BlankFiller, gbc );
        frame.add( TitleImage, gbc );
        frame.add( StartBtn, gbc );
        frame.add( JoinBtn, gbc );
        frame.add( CompanyImage, gbc );
        frame.pack();
        frame.setVisible( true );
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
            try
            {
                new JoinWindow();
            }
            catch ( IOException e1 )
            {
                e1.printStackTrace();
            }
        }
    }


    /**
     * Listens for when the player wants to start the game
     *
     * @author William Zong
     * @version May 25, 2018
     * @author Period: 4
     * @author Assignment: ProjectGlacier
     *
     * @author Sources: none
     */
    private class StartListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                new IPWindow();
            }
            catch ( IOException e1 )
            {
                e1.printStackTrace();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    }
}
