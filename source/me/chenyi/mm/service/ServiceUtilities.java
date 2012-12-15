package me.chenyi.mm.service;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.*;
import java.util.logging.Logger;

import com.moviejukebox.themoviedb.IMovieService;
import com.moviejukebox.themoviedb.MovieDbException;
import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.MovieDb;
import com.moviejukebox.themoviedb.model.TmdbConfiguration;
import me.chenyi.mm.MovieManager;
import me.chenyi.mm.model.*;
import me.chenyi.mm.util.FileUtil;
import me.chenyi.mm.util.SysUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ServiceUtilities
{
    private static Logger logger = Logger.getLogger(ServiceUtilities.class.getName());

    private static final String API_KEY;
    private static IMovieService movieService;

    static
    {
        String configApiKey = MovieManager.getConfig().getConfig("apiKey");
        API_KEY = configApiKey == null? "5a1a77e2eba8984804586122754f969f": configApiKey;
    }

    public static List<String> initMovieService()
    {
        try {
            movieService = new TheMovieDb(API_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(e.getMessage());
        }
        return Collections.emptyList();
    }

    //you can implement any movie service you want, for example, IMDB
    public static void setMovieService(IMovieService movieService)
    {
        ServiceUtilities.movieService = movieService;
    }

    public static Map<String, Map<String, Class>> getNodeTypeAttributeMap()
    {
        return movieService.getNodeTypeAttributeMap();
    }

    public static List<String> getImageSizeList(String imageType)
    {
        TmdbConfiguration configuration = movieService.getConfiguration();
        if (imageType.equalsIgnoreCase(ImageType.backdrop.toString()))
        {
            return configuration.getBackdropSizes();
        }
        else if (imageType.equalsIgnoreCase(ImageType.logo.toString()))
        {
            return configuration.getLogoSizes();
        }
        else if (imageType.equalsIgnoreCase(ImageType.poster.toString()))
        {
            return configuration.getPosterSizes();
        }
        else if (imageType.equalsIgnoreCase(ImageType.profile.toString()))
        {
            return configuration.getProfileSizes();
        }
        return Collections.emptyList();
    }

    public static Map<String, String> getImageFileMap(String movieTitle, String imageType)
    {
        Map<String, String> result = new HashMap<String, String>();
        List<String> sizeList = getImageSizeList(imageType);

        for (String sizeStr : sizeList) {
            String attrName = imageType + "_" + sizeStr;
            try
            {
                String imageFileName = URLEncoder.encode(movieTitle + "_" + attrName + ".jpg", "UTF8");
                result.put(attrName, imageFileName);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * return image file path mapping.
     * @param imageUrl the image url in tmdb api
     * @param imageType which type: backdrop / poster for movie, logo / profile for ?
     * @return mapping contains image file information,
     * key: attribute name, consist with imageType and sizeInfo (imageType_sizeInfo)
     * value: image url in TMDB site, like ''
     */
    public static Map<String, String> getImageUrlMap(String imageUrl, String imageType)
    {
        Map<String, String> result = new HashMap<String, String>();
        TmdbConfiguration configuration = movieService.getConfiguration();
        List<String> sizeList = getImageSizeList(imageType);

        String baseUrl = configuration.getBaseUrl();
        for (String sizeStr : sizeList) {
            result.put(imageType + "_" + sizeStr, baseUrl + "/" + sizeStr + "/" + imageUrl);
        }
        return result;
    }

    private static void downloadImage(Connection connection,String movieTitle, String urlPath, String imageType, Map valueMap)
    {
        if (urlPath == null || connection == null || movieTitle == null || imageType == null)
            return;

        Map<String, String> imageUrlMap = getImageUrlMap(urlPath, imageType);
        String size = MovieManager.getConfig().getConfig(imageType + "_size");
        if (size == null)
        {
            List<String> imageSizeList = getImageSizeList(imageType);
            if (imageSizeList == null || imageSizeList.size() == 0)
                return;
            size = imageSizeList.get(0);
            if (size == null)
                return;
        }
        Map<String, String> posterFilePathList = getImageFileMap(movieTitle, imageType);
        for (String attrName : imageUrlMap.keySet()) {
            if (attrName.endsWith(size))
            {
                String url = imageUrlMap.get(attrName);
                String imageFileName = posterFilePathList.get(attrName);
                try
                {
                    FileUtil.downloadToFile(new URL(url), new File(
                        SysUtil.getConfigDir().getAbsolutePath() + "/image/" + imageFileName));
                    if (valueMap != null)
                        valueMap.put(ModelUtils.getOrAddAttribute(connection, attrName, Attribute.AttributeType.File),
                                 imageFileName);
                    return;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addMovieInfoToDatabase(long movieId, boolean update)
    {
        MovieDb movieDb = null;
        try
        {
            movieDb = movieService.getMovieInfo(movieId, "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }

        if (movieDb == null)
        {
            logger.warning(String.format("updateMovieInfoToDatabase: movieDb == null for id: %s", movieId));
            return;
        }

        Connection connection = null;
        try
        {
            connection = DatabaseUtil.openConnection();

            String movieTitle = movieDb.getTitle();
            long tmdbId = movieDb.getId();

            Map<Attribute, Object> valueMap = new HashMap();

            Map<String, Object> movieDbValueMap = movieDb.getValueMap();
            for(String attributeName : movieDbValueMap.keySet())
            {
                Object value = movieDbValueMap.get(attributeName);
                if (value == null)
                    continue;
                Attribute.AttributeType type = Attribute.AttributeType.String;
                if(value instanceof Number)
                {
                    type = Attribute.AttributeType.Number;
                }
                else
                    value = String.valueOf(value);
                valueMap.put(ModelUtils.getOrAddAttribute(connection, attributeName, type), value);
            }

            downloadImage(connection, movieTitle, movieDb.getPosterPath(), ImageType.poster.toString(), valueMap);
            downloadImage(connection, movieTitle, movieDb.getBackdropPath(), ImageType.backdrop.toString(), valueMap);

            Node node = ModelUtils.searchMovieByTmdbId(tmdbId);
            //see if the node already exist.
            if (node != null)
            {
                if (update)
                    ModelUtils.updateNode(connection, movieId, valueMap);
                else
                {
                    logger.warning(String.format("Node already exist for id: %s, title: %s", movieId, movieTitle));
                }
            }
            else
            {
                ModelUtils.addMovie(connection, valueMap);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null)
            {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }

    public static List<MovieDb> searchMovie(String name)
    {
        try
        {
            return movieService.searchMovie(name, "", true);
        }
        catch(MovieDbException e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void addMovieInfoToDatabase(String name, int maxCount, boolean update)
    {
        try
        {
            List<MovieDb> movieDbList = movieService.searchMovie(name, "", true);
            if (movieDbList == null || movieDbList.size() == 0)
                return;

            if (maxCount <= 0)
                maxCount = Integer.MAX_VALUE;

            Connection connection = DatabaseUtil.openConnection();
            for(int i = 0; i < Math.min(movieDbList.size(), maxCount); i ++)
            {
                MovieDb movieDb = movieDbList.get(i);

                //search movie does not return all Movie info
                //this is to get full movie info
                //todo: is this correct?
                movieDb = movieService.getMovieInfo(movieDb.getId(), "");

                String movieTitle = movieDb.getTitle();
                long tmdbId = movieDb.getId();

                Map<Attribute, Object> valueMap = new HashMap();

                Map<String, Object> movieDbValueMap = movieDb.getValueMap();
                for(String attributeName : movieDbValueMap.keySet())
                {
                    Object value = movieDbValueMap.get(attributeName);
                    if(value == null)
                        continue;
                    Attribute.AttributeType type = Attribute.AttributeType.String;
                    if(value instanceof Number)
                    {
                        type = Attribute.AttributeType.Number;
                    }
                    else
                        value = String.valueOf(value);
                    valueMap.put(ModelUtils.getOrAddAttribute(connection, attributeName, type), value);
                }

                downloadImage(connection, movieTitle, movieDb.getPosterPath(), ImageType.poster.toString(), valueMap);
                downloadImage(connection, movieTitle, movieDb.getBackdropPath(), ImageType.backdrop.toString(),
                              valueMap);

                Node node = ModelUtils.searchMovieByTmdbId(tmdbId);

                //see if the node already exist.
                if(node != null)
                {
                    if (update)
                    {
                        ModelUtils.updateNode(connection, tmdbId, valueMap);
                    }
                    else
                    {
                        logger.warning(String.format("Node already exist for %s", movieTitle));
                        continue;
                    }
                }
                else
                {
                    ModelUtils.addMovie(connection, valueMap);
                }
            }
            DatabaseUtil.closeConnection(connection);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
