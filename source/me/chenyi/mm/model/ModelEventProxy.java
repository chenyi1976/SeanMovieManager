package me.chenyi.mm.model;

import javax.swing.event.EventListenerList;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ModelEventProxy
{
    private static ModelEventProxy instance = null;
    protected EventListenerList listenerList = new EventListenerList();

    public static ModelEventProxy getInstance()
    {
        if(instance == null)
        {
            instance = new ModelEventProxy();
        }
        return instance;
    }

    private ModelEventProxy()
    {

    }

    public void addModelEventListener(ModelEventListener l)
    {
        listenerList.add(ModelEventListener.class, l);
    }

    public void removeModelEventListener(ModelEventListener l)
    {
        listenerList.add(ModelEventListener.class, l);
    }

    public void fireModelObjectAdded(ModelObject obj)
    {
        Object[] listeners = listenerList.getListenerList();
        for(int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if(listeners[i] == ModelEventListener.class)
            {
                ((ModelEventListener)listeners[i + 1]).objectAdded(obj);
            }
        }
    }

    public void fireModelObjectRemoved(ModelObject obj)
    {
        Object[] listeners = listenerList.getListenerList();
        for(int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if(listeners[i] == ModelEventListener.class)
            {
                ((ModelEventListener)listeners[i + 1]).objectRemoved(obj);
            }
        }
    }

    public void fireModelObjectUpdated(ModelObject obj)
    {
        Object[] listeners = listenerList.getListenerList();
        for(int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if(listeners[i] == ModelEventListener.class)
            {
                ((ModelEventListener)listeners[i + 1]).objectUpdated(obj);
            }
        }
    }
}
