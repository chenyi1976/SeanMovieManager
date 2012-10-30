package me.chenyi.mm.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

import me.chenyi.mm.MovieManagerFrame;
import me.chenyi.mm.ui.ICurtainController;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ShowFilterPanelAction extends AbstractAction
{
    private ICurtainController controller;

    public ShowFilterPanelAction(ICurtainController controller)
    {
        super("Filter");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        controller.pullUpAllComponent(false);
        controller.pull(MovieManagerFrame.INDEX_FILTER, true);
    }
}
