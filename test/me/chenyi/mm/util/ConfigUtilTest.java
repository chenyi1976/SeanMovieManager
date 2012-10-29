package me.chenyi.mm.util;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ConfigUtilTest
{
    @Test
    public void testConfigUtil()
        throws Exception
    {
        Object value = ConfigUtil.getInstance().getConfig("testKey");
        System.out.println("value = " + value);
    }
}
