package me.chenyi.mm.service;

import me.chenyi.mm.model.ModelUtils;
import me.chenyi.mm.model.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 15/09/12
 * Time: 17:36
 */
public class ServiceUtiltiesTest {

    @Before
    public void setUp()
        throws Exception
    {
        ServiceUtilities.initMovieService();
    }

    @Test
    public void testGrabMovieInfoToDatabase() throws Exception {

//        String movieTitle = "Men in Black 2";
        String movieTitle = "Prometheus";
        ServiceUtilities.addMovieInfoToDatabase(movieTitle, 1, true);
        Node node = ModelUtils.searchMovieByTitle(movieTitle);
        Assert.assertNotNull(node);
    }

    @Test
    public void testPosterImageSize()
        throws Exception
    {
        List<String> poster = ServiceUtilities.getImageSizeList("poster");
        Assert.assertNotNull(poster);
        for(String size : poster)
        {
            System.out.println("size = " + size);
        }
    }

    @Test
    public void testBackdropImageSize()
        throws Exception
    {
        List<String> poster = ServiceUtilities.getImageSizeList("backdrop");
        Assert.assertNotNull(poster);
        for(String size : poster)
        {
            System.out.println("size = " + size);
        }
    }

    @Test
    public void testAddMovieInfoToDatabase() throws Exception {

//        String movieTitle = "Men in Black 2";
        long movieId = 11;
        ServiceUtilities.addMovieInfoToDatabase(movieId, true);
        Node node = ModelUtils.searchMovieByTmdbId(movieId);
        System.out.println("node = " + node);
        Assert.assertNotNull(node);
    }


}
