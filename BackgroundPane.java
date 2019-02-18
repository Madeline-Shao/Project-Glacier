package projectGlacier;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/**
 * Background for some of the other windows
 *
 * @author William Zong
 * @version May 25, 2018
 * @author Period: 4
 * @author Assignment: ProjectGlacier
 *
 * @author Sources: none
 */
@SuppressWarnings("serial")
public class BackgroundPane extends JPanel
{

    /**
     * The original background image
     */
    private BufferedImage img;

    /**
     * The scaled background image
     */
    private BufferedImage scaled;


    /**
     * Default empty constructor
     */
    public BackgroundPane()
    {
    }


    @Override
    public Dimension getPreferredSize()
    {
        return img == null ? super.getPreferredSize()
            : new Dimension( img.getWidth(), img.getHeight() );
    }


    /**
     * Sets the image to the given image
     * 
     * @param value
     *            - image
     */
    public void setBackground( BufferedImage value )
    {
        if ( value != img )
        {
            this.img = value;
            repaint();
        }
    }


    @Override
    public void invalidate()
    {
        super.invalidate();
        if ( getWidth() > img.getWidth() || getHeight() > img.getHeight() )
        {
            scaled = getScaledInstanceToFill( img, getSize() );
        }
        else
        {
            scaled = img;
        }
    }


    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        if ( scaled != null )
        {
            int x = ( getWidth() - scaled.getWidth() ) / 2;
            int y = ( getHeight() - scaled.getHeight() ) / 2;
            g.drawImage( scaled, x, y, this );
        }
    }


    /**
     * Scales image to fit given size
     * 
     * @param img
     *            - image
     * @param size
     *            - size of final image
     * @return
     */
    public static BufferedImage getScaledInstanceToFill( BufferedImage img, Dimension size )
    {

        double scaleFactor = getScaleFactorToFill( img, size );

        return getScaledInstance( img, scaleFactor );

    }


    /**
     * Returns scale factor
     * 
     * @param img
     *            - image
     * @param size
     *            - size of final image
     * @return the double scale factor
     */
    public static double getScaleFactorToFill( BufferedImage img, Dimension size )
    {

        double dScale = 1;

        if ( img != null )
        {

            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();

            double dScaleWidth = getScaleFactor( imageWidth, size.width );
            double dScaleHeight = getScaleFactor( imageHeight, size.height );

            dScale = Math.max( dScaleHeight, dScaleWidth );

        }

        return dScale;

    }


    /**
     * Returns the scale factor
     * 
     * @param iMasterSize
     *            - original size
     * @param iTargetSize
     *            - desired size
     * @return the scale factor
     */
    public static double getScaleFactor( int iMasterSize, int iTargetSize )
    {

        double dScale = (double)iTargetSize / (double)iMasterSize;

        return dScale;

    }


    /**
     * Returns the scaled image
     * 
     * @param img
     *            - image
     * @param dScaleFactor
     *            - scale factor
     * @return the scaled image
     */
    public static BufferedImage getScaledInstance( BufferedImage img, double dScaleFactor )
    {

        return getScaledInstance( img,
            dScaleFactor,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR,
            true );

    }


    /**
     * Returns scaled image
     * 
     * @param img
     *            - image
     * @param dScaleFactor
     *            - scaled factor
     * @param hint
     *            - used to get render hint
     * @param bHighQuality
     *            - whether or not scale should be good quality
     * @return
     */
    protected static BufferedImage getScaledInstance(
        BufferedImage img,
        double dScaleFactor,
        Object hint,
        boolean bHighQuality )
    {

        BufferedImage imgScale = img;

        int iImageWidth = (int)Math.round( img.getWidth() * dScaleFactor );
        int iImageHeight = (int)Math.round( img.getHeight() * dScaleFactor );

        // System.out.println("Scale Size = " + iImageWidth + "x" +
        // iImageHeight);
        if ( dScaleFactor <= 1.0d )
        {

            imgScale = getScaledDownInstance( img, iImageWidth, iImageHeight, hint, bHighQuality );

        }
        else
        {

            imgScale = getScaledUpInstance( img, iImageWidth, iImageHeight, hint, bHighQuality );

        }

        return imgScale;

    }


    /**
     * Returns a scaled down image
     * 
     * @param img
     *            - image
     * @param targetWidth
     *            - target width
     * @param targetHeight
     *            - target height
     * @param hint
     *            - render hint
     * @param higherQuality
     *            - better or worse quality after scaling
     * @return scaled image
     */
    protected static BufferedImage getScaledDownInstance(
        BufferedImage img,
        int targetWidth,
        int targetHeight,
        Object hint,
        boolean higherQuality )
    {

        int type = ( img.getTransparency() == Transparency.OPAQUE ) ? BufferedImage.TYPE_INT_RGB
            : BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage)img;
        if ( targetHeight > 0 || targetWidth > 0 )
        {
            int w, h;
            if ( higherQuality )
            {
                // Use multi-step technique: start with original size, then
                // scale down in multiple passes with drawImage()
                // until the target size is reached
                w = img.getWidth();
                h = img.getHeight();
            }
            else
            {
                // Use one-step technique: scale directly from original
                // size to target size with a single drawImage() call
                w = targetWidth;
                h = targetHeight;
            }

            do
            {
                if ( higherQuality && w > targetWidth )
                {
                    w /= 2;
                    if ( w < targetWidth )
                    {
                        w = targetWidth;
                    }
                }

                if ( higherQuality && h > targetHeight )
                {
                    h /= 2;
                    if ( h < targetHeight )
                    {
                        h = targetHeight;
                    }
                }

                BufferedImage tmp = new BufferedImage( Math.max( w, 1 ), Math.max( h, 1 ), type );
                Graphics2D g2 = tmp.createGraphics();
                g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, hint );
                g2.drawImage( ret, 0, 0, w, h, null );
                g2.dispose();

                ret = tmp;
            } while ( w != targetWidth || h != targetHeight );
        }
        else
        {
            ret = new BufferedImage( 1, 1, type );
        }
        return ret;
    }


    /**
     * Returns a scaled up image
     * 
     * @param img
     *            - image
     * @param targetWidth
     *            - target width
     * @param targetHeight
     *            - target height
     * @param hint
     *            - render hint
     * @param higherQuality
     *            - better or worse quality after scaling
     * @return scaled image
     */
    protected static BufferedImage getScaledUpInstance(
        BufferedImage img,
        int targetWidth,
        int targetHeight,
        Object hint,
        boolean higherQuality )
    {

        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if ( higherQuality )
        {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        }
        else
        {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do
        {
            if ( higherQuality && w < targetWidth )
            {
                w *= 2;
                if ( w > targetWidth )
                {
                    w = targetWidth;
                }
            }

            if ( higherQuality && h < targetHeight )
            {
                h *= 2;
                if ( h > targetHeight )
                {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage( w, h, type );
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, hint );
            g2.drawImage( ret, 0, 0, w, h, null );
            g2.dispose();

            ret = tmp;
            tmp = null;

        } while ( w != targetWidth || h != targetHeight );
        return ret;
    }

}
