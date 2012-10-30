package me.chenyi.mm.ui;

// External imports
// None

// Local imports
// None

import javax.swing.*;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieAddPanelTest
{
    @Test
    public void testMovieAddPanel()
        throws Exception
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        frame.getContentPane().add(new MovieAddPanel(new CurtainPanel2()));

        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        frame.getContentPane().add(new MovieAddPanel(new CurtainPanel2()));

        frame.setVisible(true);
    }

}
