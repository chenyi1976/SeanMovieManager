package me.chenyi.mm.model;

import java.util.EventListener;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public interface ModelEventListener extends EventListener
{
    public void objectAdded(ModelObject obj);

    public void objectRemoved(ModelObject obj);

    public void objectUpdated(ModelObject obj);
}
