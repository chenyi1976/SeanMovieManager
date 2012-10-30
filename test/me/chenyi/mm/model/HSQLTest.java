package me.chenyi.mm.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.chenyi.mm.util.SysUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class HSQLTest
{
    public static String getDbConnectionUrl()
            throws Exception
    {
        File configDir = SysUtil.getConfigDir();
        return "jdbc:hsqldb:file:" + configDir.getAbsolutePath() + "/Temp/Temp_DB";
    }


    @Test
    public void testHSQLOpen()
        throws Exception
    {
        Connection connection = null;

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(getDbConnectionUrl(), "SA", "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testHsqlTableExist()
        throws Exception
    {
        Connection connection = null;

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(getDbConnectionUrl(), "SA", "");

            try
            {
                connection.prepareStatement("select * from testtable;").execute();
            }
            catch(SQLException e)
            {
                Assert.assertTrue(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
