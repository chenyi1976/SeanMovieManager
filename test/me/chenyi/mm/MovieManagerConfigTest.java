package me.chenyi.mm;

import me.chenyi.mm.MovieManager;
import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieManagerConfigTest
{
    @Test
    public void testConfigUtil()
        throws Exception
    {
        Object value = MovieManager.getConfig().getConfig("testKey");
        System.out.println("value = " + value);
    }
}
