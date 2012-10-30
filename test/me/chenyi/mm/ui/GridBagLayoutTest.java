package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import me.chenyi.mm.ui.CurtainPanel;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class GridBagLayoutTest
{

    public static void main(String[] args)
    {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        frame.getContentPane().setLayout(new BorderLayout());
        java.util.List<JComponent> pages = new ArrayList();
        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/posters/p1_1.jpg"))));
        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/posters/p1.jpg"))));
        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/posters/p3.jpg"))));

        final CurtainPanel curtainPanel = new CurtainPanel(new JLabel("Back"), pages, CurtainPanel.CurtainLocation.NORTH);
        frame.getContentPane().add(curtainPanel, BorderLayout.CENTER);

        JButton button = new JButton("Down");
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(0, true);
            }
        });

        JButton button2 = new JButton("Up");
        frame.getContentPane().add(button2, BorderLayout.NORTH);
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(0, false);
            }
        });

        JButton button3 = new JButton("Up");
        frame.getContentPane().add(button3, BorderLayout.EAST);
        button3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(1, true);
            }
        });

        frame.setVisible(true);
    }

}
