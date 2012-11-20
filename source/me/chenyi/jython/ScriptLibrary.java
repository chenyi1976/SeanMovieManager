package me.chenyi.jython;

import me.chenyi.mm.model.Attribute;
import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.model.Node;
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

    public long getCurrentMovieId()
    {
        return ScriptEnvironment.getInstance().getCurrentMovieId();
    }

    public void setCurrentMovieId(long item)
    {
        ScriptEnvironment.getInstance().setCurrentMovieId(item);
    }

    public String getAttributeName(long attributeId)
    {
        Attribute attribute = ModelUtils.getAttribute(attributeId);
        if (attribute == null)
            return null;
        return attribute.getName();
    }

    public Object getAttributeValue(long itemId, String attributeName)
    {
        Node movie = ModelUtils.getMovie(itemId);
        if (movie == null)
            return null;
        return movie.getAttributeValue(attributeName);
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

    public void addMovie(long movieId)
    {
        ServiceUtilities.addMovieInfoToDatabase(movieId, true);
    }

}
