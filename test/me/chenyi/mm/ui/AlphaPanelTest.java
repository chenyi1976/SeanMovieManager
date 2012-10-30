package me.chenyi.mm.ui;

// External imports
// None

// Local imports
// None

import javax.swing.*;
import java.awt.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class AlphaPanelTest
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        frame.getContentPane().setBackground(Color.BLUE);

        AlphaPanel alphaPanel = new AlphaPanel();
        alphaPanel.setOpaque(false);
//        alphaPanel.setBackground(Color.black);
        alphaPanel.setBackgroundImage(
            new ImageIcon(alphaPanel.getClass().getResource("/images/gradient.png").getPath()).getImage());
        alphaPanel.setAlpha(0.9f);

        frame.getContentPane().add(alphaPanel);

        frame.setVisible(true);
    }

}
