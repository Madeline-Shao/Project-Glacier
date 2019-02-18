package projectGlacier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ResultWindow
{
    /**
     * This
     */
    JFrame frame = new JFrame( "Game Over" );


    /**
     * Constuctor
     * 
     * @param type
     *            - 0 = win; 1 - loss; 2 - tie
     */
    public ResultWindow( int type )
    {
        BackListener backListener = new BackListener();
        JButton BackBtn = new JButton( "Back" );
        BackBtn.addActionListener( backListener );
        JLabel label;
        if ( type == 0 )
        {
            label = new JLabel( "You won!" );
        }
        else if ( type == 1 )
        {
            label = new JLabel( "You lost" );
        }
        else
        {
            label = new JLabel( "You tied!" );
        }

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
