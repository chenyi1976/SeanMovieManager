package me.chenyi.mm.flow;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import be.pwnt.jflow.shape.Picture;
import me.chenyi.mm.model.Node;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class NodePicture extends Picture
{
    private Node node;

    public NodePicture(BufferedImage image, Node node)
    {
        super(image);
        this.node = node;
    }

    public NodePicture(URL url, Node node)
        throws IOException
    {
        super(url);
        this.node = node;
    }

    public Node getNode()
    {
        return node;
    }
}
