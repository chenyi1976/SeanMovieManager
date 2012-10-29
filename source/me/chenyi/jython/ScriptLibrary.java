package me.chenyi.jython;

import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.service.ServiceUtilities;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptLibrary
{
    private static ScriptLibrary instance = null;

    public static ScriptLibrary getInstance()
    {
        if (instance == null)
            instance = new ScriptLibrary();
        return instance;
    }

    public long getCurrentItemId()
    {
        return ScriptEnvironment.getInstance().getCurrentItemId();
    }

    public void setCurrentItemId(long item)
    {
        ScriptEnvironment.getInstance().setCurrentItemId(item);
    }

    public int getMovieCount()
    {
        return ModelUtils.getAllMovieIds().size();
    }

    public void addMovie(String name)
    {
        ServiceUtilities.addMovieInfoToDatabase(name, 1, true);
    }

    public void addMovies(String name)
    {
        ServiceUtilities.addMovieInfoToDatabase(name, -1, true);
    }

    public void addMovies(String name, int maxCount)
    {
        ServiceUtilities.addMovieInfoToDatabase(name, maxCount, true);
    }

    public void addMovie(int movieId)
    {
        ServiceUtilities.addMovieInfoToDatabase(movieId, true);
    }

}
