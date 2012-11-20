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

    private long currentMovieId = -1;

    public static ScriptEnvironment getInstance()
    {
        if (instance == null)
        {
            instance = new ScriptEnvironment();
        }
        return instance;
    }

    public long getCurrentMovieId()
    {
        return currentMovieId;
    }

    public void setCurrentMovieId(long currentMovieId)
    {
        this.currentMovieId = currentMovieId;
    }
}
