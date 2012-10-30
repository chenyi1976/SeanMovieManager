package be.pwnt.jflow.model;

import javax.swing.*;

import be.pwnt.jflow.Shape;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public interface FlowModel extends ListModel
{
//    public int getTotalShapeCount();

    public Shape getShape(int index);

    public int getShapeIndex(Shape shape);

}
