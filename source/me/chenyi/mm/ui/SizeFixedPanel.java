package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 * @deprecated
 */
public class SizeFixedPanel extends JPanel
{
    private int fixedSize;
    private boolean isHeightFixed;

    public SizeFixedPanel(int fixedSize, boolean isHeightFixed)
    {
        this.fixedSize = fixedSize;
        this.isHeightFixed = isHeightFixed;
    }

    @Override
    public Dimension getPreferredSize()
    {
        Dimension size = super.getSize();
        if(size == null || size.getWidth() == 0 || size.getHeight() == 0)
            return isHeightFixed ? new Dimension(800, fixedSize): new Dimension(fixedSize, 300);
        return isHeightFixed ? new Dimension((int)size.getWidth(), fixedSize): new Dimension(fixedSize, (int)size.getHeight());
    }

    @Override
    public Dimension getMinimumSize()
    {
        Dimension size = super.getSize();
        if(size == null || size.getWidth() == 0 || size.getHeight() == 0)
            return isHeightFixed ? new Dimension(800, fixedSize): new Dimension(fixedSize, 300);
        return isHeightFixed ? new Dimension((int)size.getWidth(), fixedSize): new Dimension(fixedSize, (int)size.getHeight());
    }

    public int getFixedSize()
    {
        return fixedSize;
    }

    public void setFixedSize(int fixedSize)
    {
        this.fixedSize = fixedSize;
        revalidate();   //re layout children and repaint it self
        getParent().repaint(); //repaint parent component.
    }
}
