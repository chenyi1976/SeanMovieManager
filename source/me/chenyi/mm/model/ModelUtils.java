package me.chenyi.mm.model;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.moviejukebox.themoviedb.model.MovieDb;
import org.apache.commons.collections.map.HashedMap;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 25/08/12
 * Time: 09:03
 */
public class ModelUtils {

    private static Map<Integer, Attribute> attrCacheById = new HashMap();
    private static Map<String, Attribute> attrCacheByName = new HashMap();

    private static Map<Integer, NodeType> nodeTypeCacheById = new HashedMap();
    private static Map<String, NodeType> nodeTypeCacheByName = new HashedMap();

    private static Map<Integer, Node> nodeCacheById = new HashMap<Integer, Node>();
    private static Map<String, Node> nodeCacheByTitle = new HashMap<String, Node>();
    
    private static Map<NodeType, Collection<Integer>> nodeIdCacheByType = new HashedMap();

    private static ModelEventProxy eventProxy = ModelEventProxy.getInstance();

    public static Collection<Attribute> getAllAttributes(Connection connection)
    {
        try {
            Collection<Attribute> allAttributes = DatabaseUtil.getAllAttributes(connection);
            attrCacheById.clear();
            attrCacheByName.clear();
            for(Attribute attribute : allAttributes)
            {
                attrCacheById.put(attribute.getId(), attribute);
                attrCacheByName.put(attribute.getName(), attribute);
            }
            return allAttributes;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Collection<Attribute> getAllAttributes()
    {
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.openConnection();
            Collection<Attribute> attributes = getAllAttributes(connection);
            return attributes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseUtil.closeConnection(connection);
        }
        return Collections.emptyList();
    }

    public static Attribute getAttribute(Connection connection, int attrId)
    {
        if (attrCacheByName.isEmpty())
            getAllAttributes(connection);

        return attrCacheByName.get(attrId);
    }

    public static Attribute getAttribute(int attrId)
    {
        if (attrCacheById.isEmpty())
            getAllAttributes();

        return attrCacheById.get(attrId);
    }

    public static Attribute getAttribute(Connection connection, String attrName)
    {
        if (attrCacheByName.isEmpty())
            getAllAttributes(connection);

        return attrCacheByName.get(attrName);
    }

    public static Attribute getAttribute(String attrName)
    {
        if (attrCacheByName.isEmpty())
            getAllAttributes();

        return attrCacheByName.get(attrName);
    }

    public static Attribute getOrAddAttribute(Connection connection, String attrName, Attribute.AttributeType attrType)
    {
        if (attrCacheByName.isEmpty())
            getAllAttributes();

        if (!attrCacheByName.containsKey(attrName))
        {
            return addAttribute(connection, attrName, attrType);
        }
        return attrCacheByName.get(attrName);
    }

    public static Attribute getOrAddAttribute(String attrName, Attribute.AttributeType attrType)
    {
        if (attrCacheByName.isEmpty())
            getAllAttributes();

        if (!attrCacheByName.containsKey(attrName))
        {
            return addAttribute(attrName, attrType);
        }
        return attrCacheByName.get(attrName);
    }

    public static Attribute addAttribute(Connection connection, String name, Attribute.AttributeType attrType)
    {
        try
        {
            Attribute attr = DatabaseUtil.addAttribute(connection, name, attrType.ordinal());
            if (attr != null)
            {
                eventProxy.fireModelObjectAdded(attr);
                attrCacheByName.put(name, attr);
                attrCacheById.put(attr.getId(), attr);
                return attr;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Attribute addAttribute(String name, Attribute.AttributeType attrType)
    {
        try
        {
            Connection connection = DatabaseUtil.openConnection();
            Attribute attr = DatabaseUtil.addAttribute(connection, name, attrType.ordinal());
            DatabaseUtil.closeConnection(connection);
            if (attr != null)
            {
                eventProxy.fireModelObjectAdded(attr);
                attrCacheByName.put(name, attr);
                attrCacheById.put(attr.getId(), attr);
                return attr;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<NodeType> getAllNodeTypes()
    {
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.openConnection();
            Collection<NodeType> nodeTypes = getAllNodeTypes(connection);
            return nodeTypes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseUtil.closeConnection(connection);
        }
        return Collections.emptyList();
    }

    public static Collection<NodeType> getAllNodeTypes(Connection connection)
    {
        try
        {
            Collection<NodeType> nodeTypes = DatabaseUtil.getAllNodeTypes(connection);
            for (NodeType nodeType : nodeTypes) {
                nodeTypeCacheById.put(nodeType.getId(), nodeType);
                nodeTypeCacheByName.put(nodeType.getName(), nodeType);
            }
            return nodeTypes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static NodeType addNodeType(String nodeTypeName)
    {
        Connection connection = null;
        try {
            connection = DatabaseUtil.openConnection();
            NodeType nodeType = DatabaseUtil.addNodeType(connection, nodeTypeName);
            if (nodeType != null) {
                eventProxy.fireModelObjectAdded(nodeType);
                nodeTypeCacheById.put(nodeType.getId(), nodeType);
                nodeTypeCacheByName.put(nodeTypeName, nodeType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            DatabaseUtil.closeConnection(connection);
        }
        return null;
    }

    public static NodeType addNodeType(Connection connection, String nodeTypeName)
    {
        try {
            NodeType nodeType = DatabaseUtil.addNodeType(connection, nodeTypeName);
            if (nodeType != null) {
                eventProxy.fireModelObjectAdded(nodeType);
                nodeTypeCacheById.put(nodeType.getId(), nodeType);
                nodeTypeCacheByName.put(nodeTypeName, nodeType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NodeType getNodeType(Connection connection, String nodeTypeName)
    {
        if (nodeTypeCacheByName.isEmpty())
            getAllNodeTypes(connection);

        if (!nodeTypeCacheByName.containsKey(nodeTypeName))
        {
            return addNodeType(connection, nodeTypeName);
        }
        return nodeTypeCacheByName.get(nodeTypeName);
    }

    public static NodeType getNodeType(String nodeTypeName)
    {
        if (nodeTypeCacheByName.isEmpty())
            getAllNodeTypes();

        if (!nodeTypeCacheByName.containsKey(nodeTypeName))
        {
            return addNodeType(nodeTypeName);
        }
        return nodeTypeCacheByName.get(nodeTypeName);
    }

    public static Collection<Integer> getAllNodeIds(Connection connection, NodeType nodeType)
    {
        try {
            Collection<Integer> nodes = nodeIdCacheByType.get(nodeType);
            if (nodes != null)
                return nodes;

            Collection<Integer> allNodes = DatabaseUtil.getALlNodeIds(connection, nodeType);
            nodeIdCacheByType.put(nodeType, allNodes);
            return allNodes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Integer> getAllNodeIds(NodeType nodeType)
    {
        try {
            Collection<Integer> nodes = nodeIdCacheByType.get(nodeType);
            if (nodes != null)
                return nodes;

            Connection connection = DatabaseUtil.openConnection();
            Collection<Integer> allNodes = DatabaseUtil.getALlNodeIds(connection, nodeType);
            DatabaseUtil.closeConnection(connection);
            nodeIdCacheByType.put(nodeType, allNodes);
            return allNodes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Integer> getAllMovieIds(Connection connection)
    {
        try {
            NodeType nodeType = getNodeType(NodeType.TYPE_MOVIE);
            Collection<Integer> nodes = nodeIdCacheByType.get(nodeType);
            if (nodes != null)
                return nodes;

            Collection<Integer> allNodes = DatabaseUtil.getALlNodeIds(connection, nodeType);
            nodeIdCacheByType.put(nodeType, allNodes);
            return allNodes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Integer> getAllMovieIds()
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Collection<Integer> allNodes = getAllMovieIds(connection);
            DatabaseUtil.closeConnection(connection);
            return allNodes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchNodeByTitle(Connection connection, NodeType nodeType, String title)
    {
        try {
            Node node = nodeCacheByTitle.get(title);
            if (node != null)
                return node;

            Attribute titleAttr = getOrAddAttribute(connection, "title", Attribute.AttributeType.String);
            Collection<Node> nodes = DatabaseUtil.searchForNode(connection, nodeType, Collections.singletonMap(titleAttr, (Object)title));
            if (nodes == null || nodes.size() == 0)
                return null;

            if (nodes.size() > 1)
                System.out.println("find more than one node for title: " + title);

            return nodes.iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchNodeByTitle(NodeType nodeType, String title)
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Node node = searchNodeByTitle(connection, nodeType, title);
            DatabaseUtil.closeConnection(connection);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchMovieByTitle(Connection connection, String title)
    {
        try {
            NodeType movieType = getNodeType(connection, NodeType.TYPE_MOVIE);
            Node node = searchNodeByTitle(connection, movieType, title);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchMovieByTitle(String title)
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Node node = searchMovieByTitle(connection, title);
            DatabaseUtil.closeConnection(connection);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchMovieByTmdbId(Connection connection, Integer tmdbId)
    {
        try {
            NodeType movieType = getNodeType(connection, NodeType.TYPE_MOVIE);

            Attribute titleAttr = getOrAddAttribute(connection, MovieDb.ATTR_ID, Attribute.AttributeType.String);
            Collection<Node> nodes = DatabaseUtil.searchForNode(connection, movieType, Collections.singletonMap(titleAttr, (Object)tmdbId));
            if (nodes == null || nodes.size() == 0)
                return null;

            if (nodes.size() > 1)
                System.out.println("find more than one node for tmdb id: " + tmdbId);

            return nodes.iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node searchMovieByTmdbId(Integer tmdbId)
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Node node = searchMovieByTmdbId(connection, tmdbId);
            DatabaseUtil.closeConnection(connection);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node getNode(Connection connection, NodeType nodeType, Integer nodeId)
    {
        try {
            Node node = nodeCacheById.get(nodeId);
            if (node != null)
                return node;

            node = DatabaseUtil.getNode(connection, nodeType, nodeId);
            nodeCacheById.put(nodeId, node);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node getNode(NodeType nodeType, Integer nodeId)
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Node node = getNode(connection, nodeType, nodeId);
            DatabaseUtil.closeConnection(connection);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node getMovie(Connection connection, Integer nodeId)
    {
        NodeType movieType = getNodeType(connection, NodeType.TYPE_MOVIE);
        return getNode(connection, movieType, nodeId);
    }

    public static Node getMovie(Integer nodeId)
    {
        try {
            Connection connection = DatabaseUtil.openConnection();
            Node node = getMovie(connection, nodeId);
            DatabaseUtil.closeConnection(connection);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Node addNode(Connection connection, NodeType nodeType, Map<Attribute, Object> valueMap)
    {
        try
        {
            Node node = DatabaseUtil.addNode(connection, nodeType, valueMap);
            eventProxy.fireModelObjectAdded(node);
            return node;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Node addMovie(Connection connection, Map<Attribute, Object> valueMap)
    {
        try
        {
            NodeType movieType = getNodeType(connection, NodeType.TYPE_MOVIE);
            Node node = DatabaseUtil.addNode(connection, movieType, valueMap);
            eventProxy.fireModelObjectAdded(node);
            return node;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Node updateMovie(Connection connection, int nodeId, Map<Attribute, Object> valueMap)
    {
        try
        {
            NodeType movieType = getNodeType(connection, NodeType.TYPE_MOVIE);
            return DatabaseUtil.updateNode(connection, movieType, nodeId, valueMap);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
