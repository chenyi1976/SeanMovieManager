package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 *
 */
public class AnimationPanel extends JPanel implements ImageObserver
{
    private static final Component component = new Component()
    {
    };
    private static final MediaTracker tracker = new MediaTracker(component);

    private static final long DEFAULT_DELAY = 0;

    private Image image;
    private int width;
    private int height;
    private BufferedImage bimg;

    private boolean running = false;

    private long delay = DEFAULT_DELAY;

    /**
     *
     */
    public AnimationPanel(String name)
    {
        this(loadImage(name));
    }

    /**
     *
     */
    public AnimationPanel(Image img)
    {
        setOpaque(false);

        image = img;
        width = image.getWidth(this);
        height = image.getHeight(this);
    }

    public Dimension getMinimumSize()
    {
        Insets i = getInsets();
        int widthInsets = i.left + i.right;
        int heightInsets = i.top + i.bottom;

        return new Dimension(width + widthInsets, height + heightInsets);
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public void start()
    {
        running = true;
        repaint();
    }

    public void stop()
    {
        running = false;
    }

    /**
     * The delay in milliseconds that can be specified to slow down the gif
     * animation. The default delay is 0.
     *
     * @param d the delay in milliseconds.
     */
    public void setDelay(long d)
    {
        delay = d;
    }

    public void paint(Graphics g)
    {
        Dimension d = getSize();

        if (running || (bimg == null))
            g.drawImage(image, (d.width - width) / 2, (d.height - height) / 2, this);
        else
            g.drawImage(bimg, (d.width - width) / 2, (d.height - height) / 2, this);
    }

    // overrides imageUpdate to control the animated gif's animation
    public boolean imageUpdate(Image img, int infoflags,
                               int x, int y, int width, int height)
    {
        if (isShowing() && (infoflags & ALLBITS) != 0)
        {
            if (bimg == null)
            {
                Graphics2D g2 = createGraphics2D(width, height);
                g2.drawImage(img, 0, 0, this);
                g2.dispose();
            }

            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException ie)
            {
            }

            repaint();
        }

        if (isShowing() && (infoflags & FRAMEBITS) != 0)
        {
            if (bimg == null)
            {
                Graphics2D g2 = createGraphics2D(width, height);
                g2.drawImage(img, 0, 0, this);
                g2.dispose();
            }

            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException ie)
            {
            }

            repaint();
        }

        return isShowing() && running;
    }

    private Graphics2D createGraphics2D(int w, int h)
    {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h)
        {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    private static final Image loadImage(String name)
    {
        URL url = AnimationPanel.class.getClassLoader().getResource(name);
        Image img = Toolkit.getDefaultToolkit().getImage(url);
        try
        {
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        }
        catch (Exception e)
        {
        }

        tracker.removeImage(img);

        return img;
    }

    public static void main(String argv[])
    {
        AnimationPanel demo = new AnimationPanel("com/innovit/ice/client/ui/image/gears.gif");
        //demo.init();
        javax.swing.JFrame f = new javax.swing.JFrame("Java 2D(TM) Demo - DukeAnim");
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(400, 300));
        f.setVisible(true);

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException ie)
        {

        }

        //demo.setDelay(50);

        for (int i = 0; i < 10; i++)
        {
            demo.start();

            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException ie)
            {

            }

            demo.stop();

            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException ie)
            {

            }
        }
    }
}
