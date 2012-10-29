package me.chenyi.mm;

import me.chenyi.jython.ScriptTriggerType;
import me.chenyi.jython.ScriptUtilities;
import me.chenyi.mm.model.DatabaseUtil;
import me.chenyi.mm.service.ServiceUtilities;
import me.chenyi.mm.util.SysUtil;

import javax.swing.*;
import java.util.List;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieManager extends ThreadGroup
{
    private static MovieManagerConfig config = new MovieManagerConfig();

    public MovieManager()
    {
        super("Sean's Movie Manager");
    }

    public static MovieManagerConfig getConfig()
    {
        return config;
    }

    public static void main(String[] args)
    {
        Runnable appStarter = new Runnable()
        {
            public void run()
            {
                //if it is mac, make the JMenuBar show properly.
                if (SysUtil.isMac()) {
                    System.setProperty("apple.laf.useScreenMenuBar", "true");

                    // set the name of the application menu item
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Sean's Movie Manager");
                }

                List<String> serviceErrors = ServiceUtilities.initMovieService();

                if (serviceErrors != null && serviceErrors.size() > 0)
                {
                    JOptionPane.showMessageDialog(null, serviceErrors.toString());
                }
                List<String> databaseErrors = DatabaseUtil.initDatabase();
                if (databaseErrors != null && databaseErrors.size() > 0)
                {
                    JOptionPane.showMessageDialog(null, databaseErrors.toString());
                    return;
                }

                MovieManagerFrame frame = new MovieManagerFrame();
                ScriptUtilities.executeScripts(ScriptTriggerType.OnAppStart);
            }
        };

        new Thread(new MovieManager(), appStarter, "SeanMovieManager").start();
    }

    public void uncaughtException(Thread t, Throwable ex)
    {
        ex.printStackTrace();
    }
}


