package me.chenyi.jython;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import me.chenyi.mm.model.Attribute;
import me.chenyi.mm.model.DatabaseUtil;
import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.model.Node;
import me.chenyi.mm.service.ServiceUtilities;
import me.chenyi.mm.util.SysUtil;

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

    public Object setAttributeValue(long itemId, String attributeName, Object value)
    {
        try
        {
            Map<Attribute, Object> valueMap = new HashMap();
            Connection connection = DatabaseUtil.openConnection();

            Attribute.AttributeType type = Attribute.AttributeType.String;
            if(value instanceof Number)
            {
                type = Attribute.AttributeType.Number;
            }
            else
                value = String.valueOf(value);
            valueMap.put(ModelUtils.getOrAddAttribute(connection, attributeName, type), value);

            Node node = ModelUtils.updateNode(connection, itemId, valueMap);
            DatabaseUtil.closeConnection(connection);
            return node;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
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

    public int openUrlInBrowser(String url)
    {
        return SysUtil.openUrlInBrowser(url);
    }

}
