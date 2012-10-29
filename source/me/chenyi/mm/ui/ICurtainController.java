package me.chenyi.mm.ui;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public interface ICurtainController
{
//    public void setTopComponent(int index);

    public void setBottomComponent(int index);

    public void pull(int index, boolean down);

    public void pullUpAllComponent(boolean includeBottomComponent);
}
