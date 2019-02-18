package projectGlacier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Window that announces disasters
 *
 * @author William Zong
 * @version May 25, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
public class DisasterWindow
{
    /**
     * This
     */
    JFrame frame = new JFrame( "Disaster" );


    /**
     * Constructor
     * 
     * @param mission
     *            - mission that the disaster occured
     */
    public DisasterWindow( int mission )
    {
        BackListener backListener = new BackListener();
        JButton BackBtn = new JButton( "Back" );
        BackBtn.addActionListener( backListener );
        JLabel label = new JLabel( "Disaster on mission " + mission + "!" );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        frame.add( label, gbc );
        frame.add( BackBtn, gbc );
        frame.pack();
        frame.setVisible( true );
    }


    /**
     * Constructor
     * 
     * @param mission
     *            - the mission the disaster occured on
     * @param me
     *            - player's disaster or not
     */
    public DisasterWindow( int mission, boolean me )
    {
        BackListener backListener = new BackListener();
        JButton BackBtn = new JButton( "Back" );
        BackBtn.addActionListener( backListener );
        JLabel label = new JLabel( "The other player had a disaster on mission " + mission + "!" );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        frame.add( label, gbc );
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
            frame.dispose();
            frame.setVisible( false );
            frame.setEnabled( false );
        }
    }
}
