package me.chenyi.mm.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The controller of all the images within a JDK instance. All resources that wish to load an image should do so through
 * this class. It provides caching of regularly used images thus reducing memory usage and ensuring mutliple instances
 * are removed.
 */
public class ImageController
{
    private static final Logger logger;

    /** The default image returned when the image could not be found */
    private static BufferedImage defaultImage = null;

    /** the wrapped version of the image as an icon */
    private static Icon defaultIcon;

    /** Holds all the cached images */
    private static Map<String, Image> imageMap;

    /** The tracker used to load in images */
    private static MediaTracker tracker;

    static
    {
        logger = Logger.getLogger(ImageController.class.getName());

        // create stuff used for loading images
        Component comp = new Component()
        {
        };
        tracker = new MediaTracker(comp);

        imageMap = new HashMap(10);

        // create the default image
        defaultImage = new BufferedImage(22, 22, BufferedImage.TYPE_INT_RGB);
        Graphics g = defaultImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 22, 22);
        g.setColor(Color.black);
        g.drawRect(0, 0, 21, 21);
        g.drawLine(0, 0, 21, 21);

        defaultIcon = new ImageIcon(defaultImage);

        // initialise the status of the URLConnection stuff
        //URLConnection.setContentHandlerFactory(new ImageContentHandlerFactory());
        URLConnection.setFileNameMap(new ImageFileNameMap());
    }

    /**
     * Attempts to find the mimetype of the given fileName. If the mimetype is not found, null is returned, indicating that the ImageController does not support the given file type.
     *
     * @param fileName the name of the file to be checked.
     * @return the mime-type if known or null if the type cannot be determined.
     */
    public static String getMimeType(String fileName)
    {
        return URLConnection.getFileNameMap().getContentTypeFor(fileName);
    }

    /**
     * Checks the mime-type to see if it is a currently supported one.
     *
     * @param mimeType the mimeType to be checked.
     * @return true if the mime-type is supported, false if it isn't.
     */
    public static boolean isMimeTypeSupported(String mimeType)
    {
        return ((mimeType != null) && mimeType.startsWith("image"));
    }

    /**
     * Checks whether the supplied image is the default image returned when image loading failed Useful when the calling class may want to implement special load failed behaviour
     *
     * @param image Image
     * @return boolean
     */
    public static boolean isDefaultImage(Image image)
    {
        return image == defaultImage;
    }

    /**
     * Checks whether the supplied image is the default image returned when image loading failed Useful when the calling class may want to implement special load failed behaviour
     *
     * @param icon The icon to be checked against
     * @return true if this is reference equal to the default
     */
    public static boolean isDefaultIcon(Icon icon)
    {
        return icon == defaultIcon;
    }

    /**
     * Get the reference to the default image.
     *
     * @return The predefined default image
     */
    public static BufferedImage getDefaultImage()
    {
        return defaultImage;
    }

    /**
     * Get the reference to the default icon.
     *
     * @return The predefined default icon, which is a wrapper around the default image
     */
    public static Icon getDefaultIcon()
    {
        return defaultIcon;
    }

    /**
     * Returns the image resource represented by the given name. If the image has not been cached, it will be loaded in. If there is no image available for the given name, null will be returned.
     *
     * @param name the name used to describe the image. This name will usually represent the relative path of the image from the image directory, not the absolute path.
     * @return an Image if one could be constructed from the data or null.
     */
    public static Image loadImage(String name)
    {
        return loadImage(name, (ClassLoader)null);
    }

    /**
     * Request loading an image by name from the default classloader and wrap it as an icon.
     *
     * @param name the name used to describe the image. This name will usually represent the relative path of the image from the image directory, not the absolute path.
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(String name)
    {
        Image img = loadImage(name);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img);
    }

    /**
     * Request loading an image by name from the default classloader and wrap it as an icon.
     *
     * @param name the name used to describe the image. This name will usually represent the relative path of the image from the image directory, not the absolute path.
     * @param description A description string to associate with the icon.
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(String name, String description)
    {
        Image img = loadImage(name);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img, description);
    }

    /**
     * Returns the image resource represented by the given name. If the image has not been cached, it will be loaded in. If there is no image available for the given name, null will be returned.
     *
     * @param name the name used to describe the image. This name will usually represent the relative path of the image from the image directory, not the absolute path.
     * @param cl an optional classloader to use to lookup the resource. This may be necessary if the classes to be loaded are in an externally loaded by a separate Classloader.
     * @return an Image if one could be constructed from the data or null.
     */
    public static Image loadImage(String name, ClassLoader cl)
    {
        Image image = getImage(name);
        if(image != null)
            return image;

        // attempt to load in the image if we can and store it for
        // future use.
        URL url = (cl != null) ? cl.getResource(name) : ImageController.class.getClassLoader().getResource(name);

        if(url == null)
        {
            return defaultImage;
        }

        image = loadImageFromURL(url);

        // if we have successfully loaded in the image then cache it for later use
        if(image != null)
            putImage(name, image);
        else
            image = defaultImage;

        return image;
    }

    /**
     * Request loading an image by name from the classloader and wrap it as an icon.
     *
     * @param name the name used to describe the image. This name will usually represent the relative path of the image from the image directory, not the absolute path.
     * @param cl an optional classloader to use to lookup the resource. This may be necessary if the classes to be loaded are in an externally loaded by a separate Classloader.
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(String name, ClassLoader cl)
    {
        Image img = loadImage(name, cl);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img);
    }

    /**
     * Returns the image resource represented by the given name. If the image has not been cached, it will be loaded in. If there is no image available for the given URL, null will be returned.
     *
     * @param url the URL of the image to be loaded.
     * @return an Image if one could be constructed from the data or null.
     */
    public static Image loadImage(URL url)
    {
        Image image = getImage(url.getPath());
        if(image != null)
            return image;

        // attempt to load in the image if we can and store it for
        // future use.
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        System.out.println("ImageController:loadImage:: url - " + url.toString());
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        image = loadImageFromURL(url);

        // if we have successfully loaded in the image then cache it for later use
        if(image != null)
            putImage(url.getPath(), image);
        else
            image = defaultImage;

        return image;
    }

    /**
     * Request loading an image by name from the default classloader and wrap it as an icon.
     *
     * @param url the URL of the image to be loaded.
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(URL url)
    {
        Image img = loadImage(url);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img);
    }

    /**
     * Returns the image resource represented by the given byte[] and stored under the given name. If there is no image available for the given name, null will be returned.
     *
     * @param name the name used to identify the image with the image type appended to the name of the image.
     * @param data the data to be used to construct the image.
     * @return an Image if one could be constructed from the data or null.
     */
    public static Image loadImage(String name, byte[] data)
    {
        return loadImage(name, data, false);
    }

    /**
     * Request loading an image from the custom data definition, and give it a name.
     *
     * @param name the name used to identify the image with the image type appended to the name of the image.
     * @param data the data to be used to construct the image.
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(String name, byte[] data)
    {
        Image img = loadImage(name, data);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img);
    }

    /**
     * Returns the image resource represented by the given byte[] and stored under the given name. If there is no image available for the given name, null will be returned.
     *
     * @param name the name used to identify the image with the image type appended to the name of the image.
     * @param data the data to be used to construct the image.
     * @param forceReload force the image to be reloaded
     * @return an Image if one could be constructed from the data or null.
     */
    public static Image loadImage(String name, byte[] data, boolean forceReload)
    {
        Image image = getImage(name);
        if(image != null && !forceReload)
            return image;

        // attempt to load in the image if we can and store it for
        // future use.
        try
        {
            synchronized(tracker)
            {
                try
                {
                    image = ImageIO.read(new ByteArrayInputStream(data));
                    tracker.addImage(image, 0);
                    tracker.waitForID(0);
                }
                catch(InterruptedException e)
                {
                    System.out.println("Interrupted while loading image" + e.getMessage());
                }

                tracker.removeImage(image, 0);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Interrupted while loading image" + ioe.getMessage());
        }
        catch(Exception e)
        {
            // Something happened here
            System.out.println("Interrupted while loading image" + e.getMessage());
        }

        // if we have successfully loaded in the image then cache it for later use
        if(image != null)
            putImage(name, image);
        else
            image = defaultImage;

        return image;
    }

    /**
     * Returns the image resource represented by the given byte[] without performing any caching of the data.
     *
     * @param data the data to be used to construct the image.
     * @return an Image if one could be constructed from the data or null.
     */
    public static BufferedImage loadUncachedImage(byte[] data)
    {
        BufferedImage image = null;

        // attempt to load in the image if we can and store it for
        // future use.
        try
        {
            synchronized(tracker)
            {
                try
                {
                    image = ImageIO.read(new ByteArrayInputStream(data));
                    tracker.addImage(image, 0);
                    tracker.waitForID(0);
                }
                catch(InterruptedException e)
                {
                    System.out.println("Interrupted while loading image" + e.getMessage());
                }

                tracker.removeImage(image, 0);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Interrupted while loading image" + ioe.getMessage());
        }
        catch(Exception e)
        {
            // Something happened here
            System.out.println("Interrupted while loading image" + e.getMessage());
        }

        return image;
    }

    /**
     * Request loading an image by name from the default classloader and wrap it as an icon.
     *
     * @param name the name used to identify the image with the image type appended to the name of the image.
     * @param data the data to be used to construct the image.
     * @param forceReload force the image to be reloaded
     * @return The icon wrapper for this
     */
    public static Icon loadIcon(String name, byte[] data, boolean forceReload)
    {
        Image img = loadImage(name, data, forceReload);

        return (img == defaultImage) ? defaultIcon : new ImageIcon(img);
    }

    /**
     * Looks for the Image with the given name in the cache. If the image key exists but has been garbage collected then the reference object is removed.
     *
     * @param name the name of the image to be retrieved.
     * @return the Image if it exists or null.
     */
    public static Image getImage(String name)
    {
        return (Image)imageMap.get(name);
    }

    /** Loads an image, scales it to the desired size and saves it in the format specified by the suffix of the outputFile type. */
    public static void generateScaledImage(File inputFile, Dimension scale, File outputFile)
        throws Exception
    {
        Image image = loadImageFromURL(inputFile.toURI().toURL());

        int thumbWidth = scale.width;
        int thumbHeight = scale.height;

        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double)imageWidth / (double)imageHeight;
        if(thumbRatio < imageRatio)
            thumbHeight = (int)(thumbWidth / imageRatio);
        else
            thumbWidth = (int)(thumbHeight * imageRatio);

        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

        String outputFileStr = outputFile.getAbsolutePath();
        int index = outputFileStr.lastIndexOf('.');
        if(index == -1)
            throw new IllegalArgumentException("No file suffix");

        // Extract file suffix and use it to obtain an
        // Iterator of appropriate image writers.
        String suffix = outputFileStr.substring(index + 1);
        Iterator iter = ImageIO.getImageWritersBySuffix(suffix);

        // Throw exception if there are no image writers --
        // image format is not supported.
        if(!iter.hasNext())
            throw new IllegalArgumentException("No writer for suffix " + suffix);

        // Extract writer and set the writer's output
        // destination to the passed filename.
        ImageWriter writer = (ImageWriter)iter.next();
        writer.setOutput(ImageIO.createImageOutputStream(outputFile));

        writer.write(thumbImage);

        // Release any resources being held by the writer.
        writer.dispose();
    }

    /** Adds the image to the internal map and wraps it in a Reference object to allow it to be removed at some point later on. */
    private static void putImage(String name, Image image)
    {
        imageMap.put(name, image);
    }

    public static void clearImageCache()
    {
        imageMap.clear();
    }

    /**
     * Loads an image from the given URL or returns null if the Image could not be loaded. It does not perform any caching of the images.
     *
     * @param url the URL of the Image to be loaded.
     * @return an Image or null if there was a problem loading it.
     */
    private static final Image loadImageFromURL(URL url)
    {
//long time = System.currentTimeMillis();

        // attempt to load in the image if we can and store it for
        // future use.
        Image image = null;
        try
        {
            // make sure URL is valid
            if(url == null)
                throw new NullPointerException("Image URL is null");

            image = ImageIO.read(url);
            synchronized(tracker)
            {
                tracker.addImage(image, 0);
                try
                {
                    tracker.waitForID(0);
                }
                catch(InterruptedException ie)
                {
                    System.out.println("Interrupted while loading image " + url.toString());
                }
                tracker.removeImage(image, 0);
            }
        }
        catch(NullPointerException npe)
        {
            // problem creating url for this image
            System.out.println("Problem loading image for " + url + " Error = " + npe.getMessage());
        }
        catch(IOException ioe)
        {
            // problem creating url for this image
            System.out.println("Problem loading image for " + url + " Error = " + ioe.getMessage());
        }
        catch(Exception e)
        {
            // Something happened here
            System.out.println("Interrupted while loading image" + e.getMessage());
        }

//System.out.println("Time to load image = " + (System.currentTimeMillis()-time));

        return image;
    }

    public static String getContentTypeFor(String fileName)
    {
        if(fileName.toLowerCase().endsWith(".png"))
            return "image/png";
        else if(fileName.toLowerCase().endsWith(".tif"))
            return "image/tiff";
        else if(fileName.toLowerCase().endsWith(".tiff"))
            return "image/tiff";
        else if(fileName.toLowerCase().endsWith(".bmp"))
            return "image/bmp";
        else if(fileName.toLowerCase().endsWith(".gif"))
            return "image/gif";
        else if(fileName.toLowerCase().endsWith(".jpg"))
            return "image/jpg";
        else if(fileName.toLowerCase().endsWith(".jpeg"))
            return "image/jpg";
        else
            return null;
    }

    public static String getExtensionTypeForMimeType(String mimeType)
    {
        if(mimeType.equals("image/png"))
            return ".png";
        else if(mimeType.equals("image/tiff"))
            return ".tif";
        else if(mimeType.equals("image/bmp"))
            return ".bmp";
        else if(mimeType.equals("image/gif"))
            return ".gif";
        else if(mimeType.equals("image/jpg"))
            return ".jpg";
        else
            return "";
    }

    private static class ImageFileNameMap implements FileNameMap
    {
        public String getContentTypeFor(String fileName)
        {
            return ImageController.getContentTypeFor(fileName);
        }
    }

    /**
     * Scale an image.  Generally used to scale an image to a power of 2.
     *
     * @param image The image to scale
     * @param maxDimension The maximum size to allow
     * @return A loaded, but not cached image of the correct size
     */
    public static Image resizeImage(Image image, int maxDimension)
        throws IOException
    {
        Image residedImage = ImageUtils.resizeImage(image, maxDimension);
        return residedImage;
    }
}
