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

        frame.getContentPane().setLayout(null);
//        frame.getContentPane().setLayout(new OverlayLayout(frame.getContentPane()));
        frame.getContentPane().setBackground(Color.BLUE);

        int size = 200;

        AlphaPanel alphaPanel = new AlphaPanel();
        alphaPanel.add(new JLabel("Opaque: false, color"));
        alphaPanel.setSize(new Dimension(size, size));
        alphaPanel.setLocation(0, 0);
        alphaPanel.setMaximumSize(new Dimension(size, size));
        alphaPanel.setBackground(Color.yellow);
        alphaPanel.setAlpha(0.5f);

        AlphaPanel alphaPanel2 = new AlphaPanel();
        alphaPanel2.add(new JLabel("Opaque: false, image"));
        alphaPanel2.setSize(new Dimension(size, size));
        alphaPanel2.setMaximumSize(new Dimension(size, size));
        alphaPanel2.setLocation(size, 0);
        alphaPanel2.setBackgroundImage(
            new ImageIcon(alphaPanel.getClass().getResource("/images/gradient.png").getPath()).getImage());
        alphaPanel2.setAlpha(0.5f);

        AlphaPanel alphaPanel3 = new AlphaPanel();
        alphaPanel3.add(new JLabel("Opaque: false, image & color"));
        alphaPanel3.setSize(new Dimension(size, size));
        alphaPanel3.setLocation(2 * size, 0);
        alphaPanel3.setMaximumSize(new Dimension(size, size));
        alphaPanel3.setBackground(Color.yellow);
        alphaPanel3.setBackgroundImage(
            new ImageIcon(alphaPanel.getClass().getResource("/images/gradient.png").getPath()).getImage());
        alphaPanel3.setAlpha(0.5f);

        AlphaPanel alphaPanel4 = new AlphaPanel();
        alphaPanel4.setOpaque(true);
        alphaPanel4.add(new JLabel("Opaque: true, color"));
        alphaPanel4.setSize(new Dimension(size, size));
        alphaPanel4.setLocation(0, size);
        alphaPanel4.setMaximumSize(new Dimension(size, size));
        alphaPanel4.setBackground(Color.yellow);
        alphaPanel4.setAlpha(0.5f);

        AlphaPanel alphaPanel5 = new AlphaPanel();
        alphaPanel5.setOpaque(true);
        alphaPanel5.add(new JLabel("Opaque: true, image "));
        alphaPanel5.setSize(new Dimension(size, size));
        alphaPanel5.setLocation(size, size);
        alphaPanel5.setMaximumSize(new Dimension(size, size));
        alphaPanel5.setBackgroundImage(
            new ImageIcon(alphaPanel.getClass().getResource("/images/gradient.png").getPath()).getImage());
        alphaPanel5.setAlpha(0.1f);

        AlphaPanel alphaPanel6 = new AlphaPanel();
        alphaPanel6.setOpaque(true);
        alphaPanel6.add(new JLabel("Opaque: true, color & image"));
        alphaPanel6.setSize(new Dimension(size, size));
        alphaPanel6.setLocation(2*size, size);
        alphaPanel6.setMaximumSize(new Dimension(size, size));
        alphaPanel6.setBackground(Color.yellow);
        alphaPanel6.setBackgroundImage(
            new ImageIcon(alphaPanel.getClass().getResource("/images/gradient.png").getPath()).getImage());
        alphaPanel6.setAlpha(0.5f);


        frame.getContentPane().add(alphaPanel);
        frame.getContentPane().add(alphaPanel2);
        frame.getContentPane().add(alphaPanel3);
        frame.getContentPane().add(alphaPanel4);
        frame.getContentPane().add(alphaPanel5);
        frame.getContentPane().add(alphaPanel6);

        //this button is used to demo how the alpha affect the back component.
        JButton hello = new JButton("Hello");
        hello.setOpaque(false);
        hello.setSize(2*size, size);
        hello.setLocation(size /2, size/ 2);
        frame.getContentPane().add(hello);

        frame.setVisible(true);
    }

}
