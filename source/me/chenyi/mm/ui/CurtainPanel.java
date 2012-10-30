package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import org.pushingpixels.trident.Timeline;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 * @deprecated
 */
public class CurtainPanel extends JPanel
{
    private JPanel backPanel;
    private final java.util.List<SizeFixedPanel> frontPanelList;

    private int currentFrontIndex;

//    private java.util.List<JComponent> frontComponentList;

    private final Timeline timeline;


    /**
     * @param backComponent
     * @param frontComponentList
     * @param curtainLocation
     */
    public CurtainPanel(JComponent backComponent, java.util.List<JComponent> frontComponentList, CurtainLocation curtainLocation)
    {
//        this.frontComponentList = frontComponentList;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc =
            new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                                   new Insets(5, 5, 5, 5), 0, 0);

        frontPanelList = new ArrayList();
        for(JComponent component : frontComponentList)
        {
            SizeFixedPanel frontPanel = new SizeFixedPanel(5,
                                                           curtainLocation == CurtainLocation.NORTH || curtainLocation == CurtainLocation.SOUTH);

            frontPanelList.add(frontPanel);

            frontPanel.setBackground(Color.green);
            frontPanel.setLayout(new BorderLayout());
            frontPanel.add(component, BorderLayout.CENTER);
            frontPanel.setVisible(false);

            if(curtainLocation == CurtainLocation.SOUTH
                || curtainLocation == CurtainLocation.EAST)
            {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                add(panel, gbc);// add a panel to force it.
            }

            if(curtainLocation == CurtainLocation.NORTH)
            {
                gbc.weighty = 0;
                gbc.anchor = GridBagConstraints.NORTH;
            }
            else if(curtainLocation == CurtainLocation.SOUTH)
            {
                gbc.weighty = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.SOUTH;
            }
            else if(curtainLocation == CurtainLocation.EAST)
            {
                gbc.weightx = 0;
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.EAST;
            }
            else
            {
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.WEST;
            }
            add(frontPanel, gbc);// add the front one first, make it on the top
        }


        this.backPanel = new JPanel();
        backPanel.setBackground(Color.blue);
        backPanel.setLayout(new BorderLayout());
        backPanel.add(backComponent, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        if(curtainLocation == CurtainLocation.NORTH || curtainLocation == CurtainLocation.SOUTH)
            gbc.gridheight = 2;
        else if(curtainLocation == CurtainLocation.EAST || curtainLocation == CurtainLocation.WEST)
            gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        add(backPanel, gbc);

        //test button
//        JButton button = new JButton("Add Size");
//        button.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                frontPanel.setFixedSize(frontPanel.getFixedSize() + 30);
//            }
//        });
//        gbc.gridy += 2;
//        gbc.weighty = 0;
//        add(button, gbc);

        timeline = new Timeline(this);
        timeline.setDuration(2500);

    }

    public void setFrontPanelSize(int fixedSize)
    {
        frontPanelList.get(currentFrontIndex).setFixedSize(fixedSize);
    }

    public int getFrontPanelSize()
    {
        return frontPanelList.get(currentFrontIndex).getFixedSize();
    }

    public void pull(int index, boolean isDown)
    {
        if (index < 0 || index >= frontPanelList.size())
            return;

//        frontPanel.removeAll();
//        frontPanel.add(frontComponentList.get(index), BorderLayout.CENTER);
//        frontPanel.invalidate();

        frontPanelList.get(index).setVisible(true);

        timeline.cancel();

        timeline.clearPropertyToInterpolate();
        timeline.addPropertyToInterpolate("frontPanelSize", (int)0, (int)getHeight());

        if (isDown)
            timeline.play();
        else
            timeline.playReverse();
    }

    public enum CurtainLocation
    {
        EAST,
        WEST,
        NORTH,
        SOUTH;
    }
}
