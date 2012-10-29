package me.chenyi.mm.model;

import java.util.HashMap;
import java.util.Map;

/** Created with IntelliJ IDEA. User: yichen1976 Date: 25/08/12 Time: 09:00 */
public class Node extends ModelObject
{

    private int id;
    private NodeType nodeType;
    private Map<Attribute, Object> values;

    public Node(int id, NodeType nodeType, Map<Attribute, Object> values)
    {
        this.id = id;
        this.nodeType = nodeType;
        this.values = values != null ? values : new HashMap();
    }

    public int getId()
    {
        return id;
    }

    public NodeType getNodeType()
    {
        return nodeType;
    }

    public Object getAttributeValue(Attribute attribute)
    {
        return values.get(attribute);
    }

    public Object getAttributeValue(String attributeName)
    {
        Attribute attribute = ModelUtils.getAttribute(attributeName);
        return values.get(attribute);
    }

    public void addAttrValue(int attrId, Object value)
    {
        Attribute attribute = ModelUtils.getAttribute(attrId);
        if(attribute != null)
            values.put(attribute, value);
    }

    @Override
    public String toString()
    {
        return "Node{" +
            "id=" + id +
            ", nodeType=" + nodeType +
            ", values=" + values +
            '}';
    }

    @Override
    public ModelObjectType getModelObjectType()
    {
        return ModelObjectType.node;
    }
}
