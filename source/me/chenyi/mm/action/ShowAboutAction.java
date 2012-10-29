package me.chenyi.mm.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ShowAboutAction extends AbstractAction
{
    public ShowAboutAction()
    {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("ShowAboutAction.actionPerformed");
    }
}
