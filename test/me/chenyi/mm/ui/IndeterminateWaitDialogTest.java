package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: seanc
 * Date: 28/11/12
 * Time: 10:43
 */
 public class IndeterminateWaitDialogTest {

    public static void main(String[] args)
    {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        JButton waitButton = new JButton("Test Wait Dialog");
        waitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IndeterminateWaitDialog waitDialog = new IndeterminateWaitDialog(frame, "Wait", false, "Cancel");
                waitDialog.showDialog(new Runnable(){

                    @Override
                    public void run()
                    {
                        for (int i = 0; i < 10; i ++)
                        {
                            System.out.println("i = " + i);
                            try
                            {
                                Thread.sleep(1000);
                            }
                            catch(InterruptedException e1)
                            {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        frame.getContentPane().add(waitButton);

        frame.setVisible(true);
    }

}
