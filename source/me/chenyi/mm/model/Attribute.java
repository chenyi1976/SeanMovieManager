package me.chenyi.mm.model;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 25/08/12
 * Time: 09:00
 */
public class Attribute extends ModelObject
{
    private int id;
    private String name;
    private AttributeType type;

    public Attribute(int id, String name, AttributeType type)
    {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public AttributeType getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return "Attribute{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type=" + type +
            '}';
    }

    @Override
    public ModelObjectType getModelObjectType()
    {
        return ModelObjectType.attribute;
    }

    public enum AttributeType
    {
        String,
        Number,
        File;
    }
}
