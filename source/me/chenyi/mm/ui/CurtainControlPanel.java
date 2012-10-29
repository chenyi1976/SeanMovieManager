package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;

import me.chenyi.mm.action.ShowAddPanelAction;
import me.chenyi.mm.action.ShowFilterPanelAction;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class CurtainControlPanel extends CurtainPagePanel
{
    public CurtainControlPanel(ICurtainController curtainController)
    {
        super(curtainController);

        setOpaque(false);
        setBackground(Color.black);
        setAlpha(0.0f);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

        JButton addButton = new JButton(new ShowAddPanelAction(curtainController));
        buttonPanel.add(addButton);
        JButton filterButton = new JButton(new ShowFilterPanelAction(curtainController));
        buttonPanel.add(filterButton);

        setLayout(new GridBagLayout());
        add(buttonPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    }

    @Override
    public boolean contains(int x, int y)
    {
        Component[] components = getComponents();
        for(int i = 0; i < components.length; i++)
        {
            Component component = components[i];
            Point containerPoint = SwingUtilities.convertPoint(
                this,
                x, y,
                component);
            if(component.contains(containerPoint))
            {
                return true;
            }
        }
        return false;
    }
}
