package me.chenyi.jython;

import java.util.Map;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptUtilitiesTest
{
    @Test
    public void testGetScriptListByTriggerType()
        throws Exception
    {
        Map<String,Script> scriptMap =
            ScriptUtilities.getScriptsByTriggerType(ScriptTriggerType.OnAppStart);
        for(String name : scriptMap.keySet())
        {
            System.out.println("name = " + name);
            System.out.println("scriptContent = " + scriptMap.get(name).getScriptContent());
        }
    }

//    @Test
//    public void testAddMovie()
//        throws Exception
//    {
//        ScriptUtilities.
//    }
}
