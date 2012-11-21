package me.chenyi.mm.util;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class SysUtilTest
{
    @Test
    public void testOpenFileWithSystem()
        throws Exception
    {
        SysUtil.openFileWithSystem(new File("d:\\SeanData\\Picture\\Screenshot\\Image 488.png"));
    }

    @Test
    public void testOpenUrl()
        throws Exception
    {
        SysUtil.openUrlInBrowser("http://google.com");
    }

    @Test
    public void tesetCreateSymbolicLink()
        throws Exception
    {

        File file = new File("d:\\SeanData\\Picture\\Screenshot\\Image 488.png");
        Map<File,String> symbolicLink = SysUtil.createSymbolicLink(1000, Collections.singletonList(file));
        for(Map.Entry<File, String> entry : symbolicLink.entrySet())
        {
            System.out.println("entry.getKey().getAbsolutePath() = " + entry.getKey().getAbsolutePath());
            System.out.println("entry.getValue() = " + entry.getValue());
        }
    }

    @Test
    public void testCreateSymbolicLinkWithDir()
        throws Exception
    {

        File file = new File("d:\\SeanData\\Picture\\Screenshot\\");
        Map<File,String> symbolicLink = SysUtil.createSymbolicLink(1000, Collections.singletonList(file));
        for(Map.Entry<File, String> entry : symbolicLink.entrySet())
        {
            System.out.println("entry.getKey().getAbsolutePath() = " + entry.getKey().getAbsolutePath());
            System.out.println("entry.getValue() = " + entry.getValue());
        }
    }
}
