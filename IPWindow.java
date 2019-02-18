package projectGlacier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * * IP window to display IP address
 *
 * @author Radence Tsow
 * @version May 7, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: None
 */
public class IPWindow
{
    JFrame frame = new JFrame( "IP address" );
    JFrame window;

    public IPWindow(  ) throws IOException, InterruptedException
    {
//        window = main;
        
        InetAddress myIP = InetAddress.getLocalHost();
        String IP = myIP.toString();
        IP = IP.substring( IP.indexOf( "/" ) + 1 );

        JoinWindowListener loginListener = new JoinWindowListener();
        JButton BackBtn = new JButton( "Back" );
        BackBtn.addActionListener( loginListener );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        frame.add( new JLabel( IP ), gbc );
        frame.add( BackBtn, gbc );
        frame.pack();
        frame.setVisible( true );
    }


    private class JoinWindowListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            frame.dispose();
            int port = 6785;
            Server serv = new Server( port );
            serv.startServer();
        }
    }
}
