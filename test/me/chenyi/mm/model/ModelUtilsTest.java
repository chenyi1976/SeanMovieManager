package me.chenyi.mm.model;

import java.sql.Connection;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ModelUtilsTest
{
    @Test
    public void testFindMovieInDB() throws Exception {
        Connection connection = DatabaseUtil.openConnection();
        NodeType movieNodeType = ModelUtils.getNodeType(connection, NodeType.TYPE_MOVIE);
        Node node = ModelUtils.searchNodeByTitle(connection, movieNodeType, "300");
        System.out.println("node = " + node);
        DatabaseUtil.closeConnection(connection);
    }

    @Test
    public void testGetMovieInfo() throws Exception {
        Connection connection = DatabaseUtil.openConnection();
        NodeType movieNodeType = ModelUtils.getNodeType(connection, NodeType.TYPE_MOVIE);
        Node node = ModelUtils.getMovie(1);
        System.out.println("node = " + node);
        DatabaseUtil.closeConnection(connection);
    }

}
