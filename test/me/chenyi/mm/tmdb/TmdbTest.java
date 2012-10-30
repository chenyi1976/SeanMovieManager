package me.chenyi.mm.tmdb;

// External imports
// None

// Local imports
// None

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moviejukebox.themoviedb.MovieDbException;
import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.MovieDb;
import me.chenyi.mm.model.DatabaseUtil;
import me.chenyi.mm.model.Attribute;
import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.model.Node;
import me.chenyi.mm.model.NodeType;
import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class TmdbTest
{
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";
    private static TheMovieDb tmdb;

    public TmdbTest()
        throws MovieDbException
    {
        tmdb = new TheMovieDb(API_KEY);
    }

    @Test
    public void testSearchMovieAndAddtoDb()
        throws Exception
    {
        List<MovieDb> movieDbList = tmdb.searchMovie("300", "", true);
        Connection connection = DatabaseUtil.openConnection();
        NodeType nodeType = ModelUtils.getNodeType(connection, NodeType.TYPE_MOVIE);
        for(MovieDb movieDb : movieDbList)
        {
            Map<Attribute, Object> valueMap = new HashMap();

            Field[] fields = movieDb.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                System.out.println("field.getName() = " + field.getName());
            }

            valueMap.put(ModelUtils.getOrAddAttribute(connection, "backdropPathAttr", Attribute.AttributeType.String), movieDb.getBackdropPath());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "budget", Attribute.AttributeType.Number), movieDb.getBudget());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "homepage", Attribute.AttributeType.String), movieDb.getHomepage());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "title", Attribute.AttributeType.String), movieDb.getTitle());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "voteAverage", Attribute.AttributeType.Number), movieDb.getVoteAverage());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "imdbID", Attribute.AttributeType.String), movieDb.getImdbID());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "overview", Attribute.AttributeType.String), movieDb.getOverview());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "popularity", Attribute.AttributeType.Number), movieDb.getPopularity());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "revenue", Attribute.AttributeType.Number), movieDb.getRevenue());
            valueMap.put(ModelUtils.getOrAddAttribute(connection, "runtime", Attribute.AttributeType.Number), movieDb.getRuntime());

            ModelUtils.addNode(connection, nodeType, valueMap);
        }
        DatabaseUtil.closeConnection(connection);
    }


    @Test
    public void testGetMovieInfo()
        throws Exception
    {
        MovieDb movieDb = tmdb.getMovieInfo(11, "");
        System.out.println("movieDb.getValueMap() = " + movieDb.getValueMap());
    }
}
