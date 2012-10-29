package me.chenyi.mm.ui;

import java.awt.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class CurtainPagePanel extends AlphaPanel implements ICurtainPage
{
    protected ICurtainController controller;

    public CurtainPagePanel(ICurtainController controller)
    {
        super();
        this.controller = controller;
    }

    @Override
    public void curtainActivate()
    {

    }

    @Override
    public void curtainDeactivate()
    {

    }

}
