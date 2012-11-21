package me.chenyi.mm.model;

/** Created with IntelliJ IDEA. User: yichen1976 Date: 25/08/12 Time: 09:00 */
public class NodeType extends ModelObject
{
    public static String TYPE_MOVIE = "movie";
    public static String TYPE_PERSON = "person";
    public static String TYPE_COMPANY = "company";

    long id;
    String name;

    public NodeType(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public long getId()
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        NodeType nodeType = (NodeType)o;

        if(id != nodeType.id)
            return false;
        if(name != null ? !name.equals(nodeType.name) : nodeType.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
