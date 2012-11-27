package me.chenyi.mm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class Version
{

    private static final String RESOURCE_BUNDLE = "versionInfo";

    private static final String BUILD_KEY = "build";
    private static final String DATETIME_KEY = "datetime";
    private static final String VERSION_KEY = "version";
//    private static final String REVISION_KEY = "revision";

    private static int build;
    private static Date datetime;
    private static String version;

    static
    {
        // Try to read in build and delpoyType information. If this fails, if
        // this fails, assign them to UNDEFINED.
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);

            // put this in a try/catch block because even if reading build
            // fails, it might be possible to get deploy type
            try
            {
                build = Integer.parseInt(bundle.getString(BUILD_KEY));
            }
            catch (Exception e)
            {
                build = -1;
            }

            // try and read the time of the deployment
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                datetime = sdf.parse(bundle.getString(DATETIME_KEY));
            }
            catch (Exception e)
            {
                datetime = null;
            }

            try
            {
                version = bundle.getString(VERSION_KEY);
            }
            catch(Exception e)
            {
                version = "unknown version";
            }
        }
        catch (MissingResourceException mre)
        {
            System.out.println("Problem reading deployment information " + mre.getMessage());
        }
    }

    public static int getBuild()
    {
        return build;
    }

    public static Date getDatetime()
    {
        return datetime;
    }

    public static String getVersion()
    {
        return version;
    }
}
