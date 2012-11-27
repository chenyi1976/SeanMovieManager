package me.chenyi.mm.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Date;

import me.chenyi.mm.MovieManager;
import me.chenyi.mm.util.Version;

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
        int build = Version.getBuild();
        Date datetime = Version.getDatetime();
        String version = Version.getVersion();

        JOptionPane.showMessageDialog(MovieManager.getFrame(),
                                      "Sean's Movie Manager\nBuild:" + build + "\nDateTime:" + datetime + "\nVersion:" + version);
    }
}
