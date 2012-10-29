package me.chenyi.mm.model;

/** Created with IntelliJ IDEA. User: yichen1976 Date: 25/08/12 Time: 09:00 */
public class NodeType extends ModelObject
{
    public static String TYPE_MOVIE = "movie";
    public static String TYPE_PERSON = "person";
    public static String TYPE_COMPANY = "company";

    int id;
    String name;

    public NodeType(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "NodeType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public ModelObjectType getModelObjectType()
    {
        return ModelObjectType.nodetype;
    }
}
