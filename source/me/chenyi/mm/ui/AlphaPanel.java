package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class AlphaPanel extends JPanel
{
    protected Image backgroundImage = null;
    protected float alpha = 1.0f;

    public AlphaPanel()
    {
        super();
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public AlphaPanel(LayoutManager layout)
    {
        super(layout);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public float getAlpha()
    {
        return alpha;
    }

    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
        repaint();
    }

    public void setBackgroundImage(Image backgroundImage)
    {
        this.backgroundImage = backgroundImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g.create();
//        g2.clearRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        if(backgroundImage != null)
        {
            int width = backgroundImage.getWidth(null);
            int height = backgroundImage.getHeight(null);
            float ratio = Math.min(((float)getHeight()) / height, ((float)getWidth()) / width);
            width = (int)(ratio * width);
            height = (int)(ratio * height);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            System.out.println("height = " + height);
            System.out.println("width = " + width);
            g2.drawImage(backgroundImage,
                         x,
                         y,
                         width,
                         height,
                         this);
        }
    }
}
