package me.chenyi.jython;

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
    private File file;

    public Script(ScriptTriggerType triggerType, String name)
    {
        this.triggerType = triggerType;
        this.name = name;
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
        try
        {
            return SysUtil.getConfigDir().getAbsolutePath() + "/plugin/" + triggerType.getTriggerTypeName() + "_" + name + ".py";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
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
