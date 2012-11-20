package me.chenyi.mm.flow;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.pwnt.jflow.Shape;
import be.pwnt.jflow.model.FlowModel;
import me.chenyi.mm.model.*;
import me.chenyi.mm.service.ImageType;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieFlowModel extends AbstractListModel implements FlowModel
{

    private NodeType nodeType;
    private NodePicture defaultShape;
    private List<Long> nodeIds = Collections.emptyList();
    private Map<Integer, NodePicture> shapeMap = new HashMap();//Index - > NodePicture

    private EventAdapter ea;

    public MovieFlowModel()
    {
        Connection connection = null;
        try
        {
            ea = new EventAdapter();

            defaultShape = new NodePicture(getClass().getResource("/posters/p1.jpg"), null);

            connection = DatabaseUtil.openConnection();
            nodeIds = (List<Long>)ModelUtils.getAllMovieIds(connection);
            nodeType = ModelUtils.getNodeType(connection, NodeType.TYPE_MOVIE);

            ModelEventProxy.getInstance().addModelEventListener(ea);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseUtil.closeConnection(connection);
        }
    }

//    @Override
//    public int getTotalShapeCount()
//    {
//        if (nodeIds == null || nodeIds.size() == 0)
//            return 1;
//        return nodeIds.size();
//    }

    @Override
    public Shape getShape(int index)
    {
        if(nodeIds.size() == 0)
            return defaultShape;

        while(index < 0)
            index += nodeIds.size();

        while(index >= nodeIds.size())
            index -= nodeIds.size();

        long nodeId = nodeIds.get(index);

        NodePicture shape = shapeMap.get(index);
        if(shape != null)
            return shape;

        Node node = ModelUtils.getNode(nodeType, nodeId);
        if(node == null)
        {
            shapeMap.put(index, defaultShape);
            return defaultShape;
        }
        File file = NodeUtil.getImageFile(node, ImageType.poster.toString());
        System.out.println("file = " + file);
        if(file == null)
        {
            shapeMap.put(index, defaultShape);
            return defaultShape;
        }

        try
        {
            if(file.exists())
            {
                shape = new NodePicture(file.toURI().toURL(), node);
                shapeMap.put(index, shape);
                return shape;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        shapeMap.put(index, defaultShape);
        return defaultShape;
    }

    @Override
    public int getShapeIndex(Shape shape)
    {
        if(shape == null)
            return -1;
        for(Map.Entry<Integer, NodePicture> entry : shapeMap.entrySet())
        {
            if(shape.equals(entry.getValue()))
                return entry.getKey();
        }
        return -1;
    }

    @Override
    public int getSize()
    {
        if(nodeIds == null || nodeIds.size() == 0)
            return 1;
        return nodeIds.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        return getShape(index);
    }

    public void removeAllShapes()
    {
        int size = getSize();
        nodeIds.clear();
        shapeMap.clear();
        if(size >= 0)
        {
            fireIntervalRemoved(this, 0, size - 1);
        }
    }

    public boolean removeShapeByNodeId(long nodeId)
    {
        int index = nodeIds.indexOf(nodeId);
        boolean rv = nodeIds.remove(nodeId);
        if(index >= 0)
        {
            fireIntervalRemoved(this, index, index);
        }
        return rv;
    }

    public boolean removeShapeAt(int index)
    {
        if(index < 0 || index >= nodeIds.size())
            return false;

        nodeIds.remove(index);
        if(index >= 0)
        {
            fireIntervalRemoved(this, index, index);
        }
        return true;
    }

    public boolean addNodeId(long nodeId)
    {
        nodeIds.add(nodeId);
        int lastIndex = nodeIds.size() - 1;
        fireIntervalAdded(this, lastIndex, lastIndex);
        return true;
    }

    public boolean addNodeIdAt(int index, long nodeId)
    {
        if(index < 0 || index >= nodeIds.size())
            return false;

        nodeIds.add(index, nodeId);
        fireIntervalAdded(this, index, index);
        return true;
    }

    public boolean updateAt(int index)
    {
        if(index < 0 || index >= nodeIds.size())
            return false;

        shapeMap.remove(index);
        fireContentsChanged(this, index, index);
        return true;
    }

    public boolean updateNodeId(long nodeId)
    {
        int index = nodeIds.indexOf(nodeId);

        if (index < 0)
            return false;

        shapeMap.remove(index);
        fireContentsChanged(this, index, index);
        return true;
    }

    public boolean updateNodeIdAt(int index, long nodeId)
    {
        if(index < 0 || index >= nodeIds.size())
            return false;

        nodeIds.set(index, nodeId);
        shapeMap.remove(index);
        fireContentsChanged(this, index, index);
        return true;
    }

    private class EventAdapter implements ModelEventListener
    {

        @Override
        public void objectAdded(ModelObject obj)
        {
            System.out.println("MovieFlowModel$EventAdapter.objectAdded");
            if (obj == null)
                return;

            if (!ModelObjectType.node.equals(obj.getModelObjectType()))
                return;

            Node node = (Node)obj;
            if (node.getNodeType().getName().equals(NodeType.TYPE_MOVIE))
            {
                addNodeId(node.getId());
            }
        }

        @Override
        public void objectRemoved(ModelObject obj)
        {
            System.out.println("MovieFlowModel$EventAdapter.objectRemoved");
            if (obj == null)
                return;

            if (!ModelObjectType.node.equals(obj.getModelObjectType()))
                return;

            Node node = (Node)obj;
            if (node.getNodeType().getName().equals(NodeType.TYPE_MOVIE))
            {
                removeShapeByNodeId(node.getId());
            }
        }

        @Override
        public void objectUpdated(ModelObject obj)
        {
            System.out.println("MovieFlowModel$EventAdapter.objectUpdated");
            if (obj == null)
                return;

            if (!ModelObjectType.node.equals(obj.getModelObjectType()))
                return;

            Node node = (Node)obj;
            if (node.getNodeType().getName().equals(NodeType.TYPE_MOVIE))
            {
                updateNodeId(node.getId());
            }
        }
    }
}
