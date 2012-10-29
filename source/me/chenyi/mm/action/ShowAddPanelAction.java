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
public class ShowAddPanelAction extends AbstractAction
{
    private ICurtainController controller;

    public ShowAddPanelAction(ICurtainController controller)
    {
        super("Add");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        controller.pullUpAllComponent(false);
        controller.pull(MovieManagerFrame.INDEX_ADD, true);
    }
}
