package me.chenyi.jython;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.chenyi.mm.util.SysUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptUtilities
{
    private static ScriptInterpreter interpreter = null;
    public static boolean executeScript(String pythonScript)
    {
        try
        {
            if (interpreter == null)
                interpreter = new ScriptInterpreter(new HashMap());

            interpreter.exec(pythonScript);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Map<String, Boolean> executeScripts(ScriptTriggerType triggerType)
    {
        Map<String, Boolean> result = new HashMap();
        Map<String, Script> scriptMap = ScriptUtilities.getScriptsByTriggerType(triggerType);
        for(String scriptName : scriptMap.keySet())
        {
            result.put(scriptName, ScriptUtilities.executeScript(scriptMap.get(scriptName).getScriptContent()));
        }
        return result;
    }

    public static void compileEditRule(String rule)
        throws Exception
    {
        if(rule == null || rule.length() == 0)
            return;

        if(interpreter == null)
            interpreter = new ScriptInterpreter(new HashMap());
        interpreter.doCompile(rule);
    }

    private static Map<ScriptTriggerType, Map<String, Script>> scriptMap = new HashMap();
    private static boolean needReloadScript = true;

    public static void reloadScripts()
    {
        needReloadScript = true;
    }

    public static Map<ScriptTriggerType, Map<String, Script>> getScripts()
    {
        if (needReloadScript)
            initScriptList();
        return scriptMap;
    }

    public static Map<String, Script> getScriptsByTriggerType(ScriptTriggerType triggerType)
    {
        if (needReloadScript)
            initScriptList();
        if (scriptMap.containsKey(triggerType))
            return scriptMap.get(triggerType);
        return Collections.emptyMap();
    }

    private static void initScriptList()
    {
        scriptMap.clear();
        for(ScriptTriggerType triggerType : ScriptTriggerType.values())
        {
            Map<String, Script> result = new HashMap();
            try
            {
                File configDir = SysUtil.getConfigDir();
                String triggerTypeName = triggerType.getTriggerTypeName();
                File triggerFolder = new File(configDir.getAbsolutePath() + "/plugin/");
                if(triggerFolder.exists() && triggerFolder.isDirectory())
                {
                    File[] files = triggerFolder.listFiles();
                    for(File file : files)
                    {
                        String fileName = file.getName();
                        if(file.isFile() && fileName.toLowerCase().endsWith(".py")
                            && fileName.toLowerCase().startsWith(triggerTypeName.toLowerCase()))
                        {
                            String scriptName = fileName.substring(triggerTypeName.length() + 1, fileName.length() - 3);
                            System.out.println("scriptName = " + scriptName);
                            result.put(fileName, new Script(triggerType, scriptName));
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            scriptMap.put(triggerType, result);
        }
        needReloadScript = false;
    }
}
