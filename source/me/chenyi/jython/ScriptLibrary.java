package me.chenyi.jython;

import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moviejukebox.themoviedb.model.MovieDb;
import me.chenyi.mm.MovieManager;
import me.chenyi.mm.MovieManagerFrame;
import me.chenyi.mm.model.Attribute;
import me.chenyi.mm.model.DatabaseUtil;
import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.model.Node;
import me.chenyi.mm.service.ServiceUtilities;
import me.chenyi.mm.ui.IndeterminateWaitDialog;
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

    public String[] searchMovieByName(String name)
    {
        List<MovieDb> movieDbs = ServiceUtilities.searchMovie(name);
        String[] result = new String[movieDbs.size()];
        int index = 0;
        for(MovieDb movieDb : movieDbs)
        {
            result[index] = movieDb.getTitle();
            index ++;
        }
        return result;
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

    public MovieManagerFrame getMainFrame()
    {
        return MovieManager.getFrame();
    }

    private IndeterminateWaitDialog waitDialog;
    public void showWaitDialog(final String waitText)
    {
        if (waitDialog == null)
        {
            waitDialog = new IndeterminateWaitDialog(MovieManager.getFrame());
        }
        if (SwingUtilities.isEventDispatchThread())
        {
            waitDialog.setWaitText(waitText);
            waitDialog.setVisible(true);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    waitDialog.setWaitText(waitText);
                    waitDialog.setVisible(true);
                }
            });
        }
    }

    public void closeWaitDialog()
    {
        if (waitDialog == null)
        {
            return;
        }
        waitDialog.setVisible(false);
    }

}
