package me.chenyi.other;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: yichen1976
 * Date: 4/09/12
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MenuBarTest {

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        JMenuBar menubar = new JMenuBar();
        JMenu hello = new JMenu("Hello");
        hello.add(new JMenuItem("World"));
        menubar.add(hello);
        frame.setJMenuBar(menubar);
//        frame.getContentPane()

        frame.setVisible(true);
    }
}
