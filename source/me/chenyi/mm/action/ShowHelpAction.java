package me.chenyi.mm.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ShowHelpAction extends AbstractAction
{
    public ShowHelpAction()
    {
        super("Online Help");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("ShowHelpAction.actionPerformed");
    }
}
