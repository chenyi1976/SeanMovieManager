package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class CurtainPanel2Test
{

    @Test
    public void testJLabelComponent()
        throws Exception
    {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        frame.getContentPane().setLayout(new BorderLayout());
        java.util.List<CurtainPagePanel> pages = new ArrayList();
//        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/images/nocover_jaguar.png"))));
//        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/images/nocover_puma.png"))));
//        pages.add((JComponent)new JLabel( new ImageIcon(frame.getClass().getResource("/images/nocover_tiger.png"))));

        final CurtainPanel2 curtainPanel = new CurtainPanel2();
        curtainPanel.addComponentList(pages);

        frame.getContentPane().add(curtainPanel, BorderLayout.CENTER);

        JButton button = new JButton("Pull Down");
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(1, true);
            }
        });

        JButton button2 = new JButton("Push Up");
        frame.getContentPane().add(button2, BorderLayout.NORTH);
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(1, false);
            }
        });

//        JButton button3 = new JButton("Up");
//        frame.getContentPane().add(button3, BorderLayout.EAST);
//        button3.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                curtainPanel.pull(1, true);
//            }
//        });

        curtainPanel.setBottomComponent(0);

        System.out.println("CurtainPanel2Test.testJLabelComponent");
        frame.setVisible(true);
        System.out.println("CurtainPanel2Test.testJLabelComponent");
    }

    @Test
    public void testMMComponent()
        throws Exception
    {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        final CurtainPanel2 curtainPanel = new CurtainPanel2();

        frame.getContentPane().setLayout(new BorderLayout());
        java.util.List<CurtainPagePanel> pages = new ArrayList();
        pages.add(new MovieMainPanel(curtainPanel));
        final MovieAddPanel movieAddPanel = new MovieAddPanel(curtainPanel);
        pages.add(movieAddPanel);

        curtainPanel.addComponentList(pages);

        frame.getContentPane().add(curtainPanel, BorderLayout.CENTER);

        JButton button = new JButton("Pull Down");
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(1, true);
                movieAddPanel.getTextField().requestFocus();
            }
        });

        JButton button2 = new JButton("Push Up");
        frame.getContentPane().add(button2, BorderLayout.NORTH);
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                curtainPanel.pull(1, false);
            }
        });

//        JButton button3 = new JButton("Up");
//        frame.getContentPane().add(button3, BorderLayout.EAST);
//        button3.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                curtainPanel.pull(1, true);
//            }
//        });


        System.out.println("CurtainPanel2Test.testMMComponent");
        frame.setVisible(true);
        System.out.println("CurtainPanel2Test.testMMComponent");

        curtainPanel.setBottomComponent(0);
    }

    public static void main(String[] args)
    {
        CurtainPanel2Test test = new CurtainPanel2Test();
        try
        {
            test.testMMComponent();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
