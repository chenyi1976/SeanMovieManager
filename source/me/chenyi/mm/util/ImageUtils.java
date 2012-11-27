package me.chenyi.mm.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * General utility methods for doing stuff to images, such as resizes
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ImageUtils
{
    /**
     * Load the named image off disk and pre-scale it to the requested maximum
     * dimensions. The max is assuming a square image and will rescale the
     * incoming image so that is the max in either direction, while maintaining
     * the aspect ratio.
     *
     * @param file The name of the files to load
     * @param maxDimension The maximum size to allow
     * @return A loaded, but not cached image of the correct size or null if it failed
     */
    public static BufferedImage loadScaledImage(File file, int maxDimension)
    {
        BufferedImage ret_val = null;

        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int readLength = -1;
            while ((readLength = fis.read(buffer)) != -1)
            {
                baos.write(buffer, 0, readLength);
            }

            ret_val = ImageController.loadUncachedImage(baos.toByteArray());

            if(ret_val.getWidth(null) > maxDimension ||
               ret_val.getHeight(null) > maxDimension)
            {
                ret_val = ImageUtils.resizeImage(ret_val, maxDimension);
            }
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return ret_val;
    }

    /**
     * Convert a Jawa image to the underlying byte array.
     *
     * @param img The imput image to process
     * @return A corresponding byte array
     */
    public static byte[] imageToBytes(BufferedImage img)
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(img, "jpg", buffer);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return buffer.toByteArray();

    }

    /**
     * Scale an image.  Generally used to scale an image to a power of 2.
     *
     * @param image The image to scale
     * @param maxDimension The maximum size to allow
     * @return A loaded, but not cached image of the correct size
     */
    public static BufferedImage resizeImage(Image image, int maxDimension) throws IOException
    {
        BufferedImage ret_val = null;

        if(image instanceof BufferedImage)
            ret_val = resizeImage((BufferedImage)image, maxDimension);
        else if(image != null)
        {
            // need to convert the image to a buffered image
            BufferedImage b_img = new BufferedImage(image.getWidth(null),
                                                    image.getHeight(null),
                                                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = b_img.getGraphics();
            g.drawImage(image, 0, 0, null);

            ret_val = resizeImage(b_img, maxDimension);
        }

        return ret_val;
    }

    /**
     * Scale an image.  Generally used to scale an image to a power of 2.
     *
     * @param image The image to scale
     * @param maxDimension The maximum size to allow
     * @return A loaded, but not cached image of the correct size
     */
    public static BufferedImage resizeImage(BufferedImage image, int maxDimension) throws IOException
    {
        int newWidth = 0;
        int newHeight = 0;

        float srcWidth = image.getWidth();
        float srcHeight = image.getHeight();

        if(srcWidth > srcHeight)
        {
            float scale = maxDimension / srcWidth;

            newWidth = maxDimension;
            newHeight = (int)(srcHeight * scale);
        }
        else
        {
            float scale = maxDimension / srcHeight;

            newWidth = (int)(srcWidth * scale);
            newHeight = maxDimension;
        }

        boolean hasAlpha = image.getColorModel( ).hasAlpha( );

        if(hasAlpha && (newWidth <= 64) && (newHeight <= 64))
        {
            return scalePretty(image, newWidth, newHeight);
        }

        double xScale = (float)newWidth / (float)srcWidth;
        double yScale = (float)newHeight / (float)srcHeight;

        AffineTransform at = AffineTransform.getScaleInstance( xScale, yScale );
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage ret_image = null;
        if(hasAlpha)
        {
            ret_image = atop.filter( image, null );
        }
        else
        {
            ret_image = new BufferedImage( newWidth, newHeight, image.getType( ) );
            atop.filter( image, ret_image );
        }

        return ret_image ;
    }

    /**
     * Scale an image.  Generally used to scale an image to a power of 2.
     *
     * @param image The image to scale
     * @param newWidth The new width
     * @param newHeight The new height
     */
    private static BufferedImage scalePretty(BufferedImage image, int newWidth, int newHeight)
    {
        Image rimg = image.getScaledInstance(newWidth, newHeight,
                                             Image.SCALE_AREA_AVERAGING);

        boolean hasAlpha = image.getColorModel().hasAlpha();

        BufferedImage ret_image = null;
        // Not sure why .getType doesn't work right for this
        if(hasAlpha)
        {
            ret_image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        }
        else
        {
            ret_image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D g2 = ret_image.createGraphics();
        g2.drawImage(rimg, 0, 0, null);

        return ret_image;
    }
}
