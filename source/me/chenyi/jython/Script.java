package me.chenyi.jython;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import me.chenyi.mm.util.FileUtil;
import me.chenyi.mm.util.SysUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class Script
{
    private ScriptTriggerType triggerType;
    private String name;
    private String scriptContent;
    private String scriptFileLocation;
    private ImageIcon scriptIcon;

    public Script(ScriptTriggerType triggerType, String name)
    {
        this.triggerType = triggerType;
        this.name = name;

        updateFileLocation(triggerType, name);
    }

    private void updateFileLocation(ScriptTriggerType triggerType, String name)
    {
        String absolutePath = "";
        try
        {
            absolutePath = SysUtil.getConfigDir().getAbsolutePath();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        scriptFileLocation = absolutePath + "/plugin/" + triggerType.getTriggerTypeName() + "_" + name + ".py";
        scriptIcon = new ImageIcon(absolutePath + "/plugin/" + triggerType.getTriggerTypeName() + "_" + name + ".png");
    }

    public String getName()
    {
        return name;
    }

    public ScriptTriggerType getTriggerType()
    {
        return triggerType;
    }

    public String getScriptFileLocation()
    {
        return scriptFileLocation;
    }

    public ImageIcon getScriptIcon()
    {
        return scriptIcon;
    }

    public String getScriptContent()
    {
        if (scriptContent != null)
            return scriptContent;

        try
        {
            scriptContent = FileUtil.readFileToStringBuffer(getScriptFileLocation()).toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return scriptContent;
    }

    public boolean updateScript(ScriptTriggerType type, String name, String content)
    {
        if (type == null || name == null || content == null)
            return false;

        if (type.equals(this.triggerType)
            && name.equalsIgnoreCase(this.name)
            && content.equalsIgnoreCase(this.scriptContent))
            return false;

        if ((!name.equalsIgnoreCase(this.name)) || (!type.equals(this.triggerType)))
        {
            //if the file name changed, then delete the old file.
            new File(getScriptFileLocation()).delete();
            updateFileLocation(type, name);
        }

        this.name = name;
        this.triggerType = type;
        this.scriptContent = content;
        return FileUtil.writeToFile(content.getBytes(), new File(getScriptFileLocation()));
    }

    @Override
    public String toString()
    {
        return triggerType.getTriggerTypeName() + "_" + name;
    }
}
