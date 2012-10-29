package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.List;

import org.pushingpixels.trident.Timeline;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class CurtainPanel2 extends JPanel implements ICurtainController
{
    private int currentComponentIndex;

    private java.util.List<CurtainPagePanel> componentList;

    private java.util.List<CurtainPagePanel> componentListByLayerOrder;

    private final Timeline timeline;

//    private CurtainControlPanel controllerPanel;

    /**
     */
    public CurtainPanel2()
    {
        componentList = new ArrayList();
        componentListByLayerOrder = new ArrayList(componentList);

        setLayout(new OverlayLayout(this));

//        controllerPanel = new CurtainControlPanel(this);
//        controllerPanel.setBorder(BorderFactory.createLineBorder(Color.red));
//        add(controllerPanel);
//        controllerPanel.setAlignmentX(0.5f);
//        controllerPanel.setAlignmentY(0f);
//        controllerPanel.setMaximumSize(new Dimension(0, 0));

//        for(JComponent component : componentList)
//        {
//            component.setAlignmentX(0.5f);
//            component.setAlignmentY(0f);
//            component.setMaximumSize(new Dimension(0, 0));
////            component.setVisible(false);
//            add(component);
//        }

        timeline = new Timeline(this);
        timeline.setDuration(2500);

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                System.out.println("CurtainPanel2.componentResized");
                refreshSize();
            }
        });
    }

    public void addComponentList(List<CurtainPagePanel> componentList)
    {
        this.componentList.addAll(componentList);
        for(CurtainPagePanel curtainPagePanel : componentList)
        {
            curtainPagePanel.setAlignmentX(0.5f);
            curtainPagePanel.setAlignmentY(0f);
            curtainPagePanel.setMaximumSize(new Dimension(0, 0));
            add(curtainPagePanel);
        }
    }

    public void removeComponentList(List<CurtainPagePanel> componentList)
    {
        this.componentList.removeAll(componentList);
        for(CurtainPagePanel curtainPagePanel : componentList)
        {
            remove(curtainPagePanel);
        }
    }

    @Override
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }

    public void setCurrentComponentSize(Dimension size)
    {
//        System.out.println("size = " + size);
        JComponent component = componentList.get(currentComponentIndex);
        component.setMaximumSize(size);
        revalidate();
    }

    public Dimension getCurrentComponentSize()
    {
        return componentList.get(currentComponentIndex).getMaximumSize();
    }

    public void refreshSize()
    {
//        controllerPanel.setMaximumSize(CurtainPanel2.this.getSize());
        for(JComponent component : CurtainPanel2.this.componentList)
        {
            if(component.getMaximumSize().width > 10)
            {
                component.setMaximumSize(CurtainPanel2.this.getSize());
            }
        }
    }

    /**
     * WARNNING: this method only works the Windows already setVisible!
     * @param index
     */
    public void setBottomComponent(int index)
    {
        if (index < 0 || index >= componentList.size())
            return;

        currentComponentIndex = index;

        final CurtainPagePanel component = componentList.get(index);
//        component.setVisible(true);

        componentListByLayerOrder.remove(component);
        componentListByLayerOrder.add(component);

        this.removeAll();
//        this.add(controllerPanel);
        for(JComponent jcomponent : componentListByLayerOrder)
        {
            this.add(jcomponent);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
//                controllerPanel.setMaximumSize(getSize());
                component.setMaximumSize(getSize());
                revalidate();
            }
        });

    }

//    public void setTopComponent(int index)
//    {
//        if (index < 0 || index >= componentList.size())
//            return;
//
//        currentComponentIndex = index;
//
//        final JComponent component = componentList.get(index);
//
//        componentListByLayerOrder.remove(component);
//        componentListByLayerOrder.add(0, component);
//
//        this.removeAll();
//        for(JComponent jcomponent : componentListByLayerOrder)
//        {
//            this.add(jcomponent);
//        }
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run()
//            {
//                component.setMaximumSize(getSize());
//                revalidate();
//            }
//        });
//    }

    public void pull(int index, boolean isDown)
    {
        //todo: should use state pattern here, refuse request if it is impossible.

        if (index < 0 || index >= componentList.size())
            return;

        timeline.cancel();

        CurtainPagePanel previousComponent = componentList.get(index);
        previousComponent.curtainDeactivate();

        currentComponentIndex = index;

        CurtainPagePanel component = componentList.get(index);
//        component.setVisible(true);

        componentListByLayerOrder.remove(component);
        componentListByLayerOrder.add(0, component);

        this.removeAll();
//        this.add(controllerPanel);
        for(JComponent jcomponent : componentListByLayerOrder)
        {
            this.add(jcomponent);
        }

        //todo: use call back to activate the new panel.
//        timeline.addCallback();
        timeline.clearPropertyToInterpolate();
        Dimension size = getSize();
        timeline.addPropertyToInterpolate("currentComponentSize", new Dimension(1, 1), size);

        if (isDown)
            timeline.play();
        else
            timeline.playReverse();

        //todo: need to fire property change once panel changed.
    }

    @Override
    public void pullUpAllComponent(boolean includeBottomComponent)
    {
        JComponent excludeComponent = componentListByLayerOrder.get(componentListByLayerOrder.size() - 1);
        for(JComponent component : componentList)
        {
            if (!includeBottomComponent)
                if (component == excludeComponent)
                    continue;
            component.setMaximumSize(new Dimension(1, 1));
        }
    }
}
