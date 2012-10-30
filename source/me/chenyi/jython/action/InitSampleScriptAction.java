package me.chenyi.jython.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

import me.chenyi.mm.util.FileUtil;
import me.chenyi.mm.util.SysUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class InitSampleScriptAction extends AbstractAction
{
    public InitSampleScriptAction()
    {
        super("Init Sample Script");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            File configDir = SysUtil.getConfigDir();
            File triggerFolder = new File(configDir.getAbsolutePath() + "/plugin/");
            if(!triggerFolder.exists())
            {
                triggerFolder.mkdirs();
            }
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }

        //todo: how to copy over the .py under /plugin in project into the configDir/plugin folder?
//        this.getClass().getResource("/plugin");
//        File[] files = triggerFolder.listFiles();
//        for(File file : files)
//        {
//            if(file.isFile() && file.getName().toLowerCase().endsWith(".py")
//                && file.getName().toLowerCase().startsWith(triggerTypeName.toLowerCase()))
//            {
//                StringBuffer stringBuffer = FileUtil.readFileToStringBuffer(file);
//                result.put(file.getName(), stringBuffer.toString());
//            }
//        }
    }
}
