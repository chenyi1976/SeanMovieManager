package me.chenyi.jython;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptEnvironment
{
    private static ScriptEnvironment instance = null;

    private long currentItemId = -1;

    public static ScriptEnvironment getInstance()
    {
        if (instance == null)
        {
            instance = new ScriptEnvironment();
        }
        return instance;
    }

    public long getCurrentItemId()
    {
        return currentItemId;
    }

    public void setCurrentItemId(long currentItemId)
    {
        this.currentItemId = currentItemId;
    }
}
