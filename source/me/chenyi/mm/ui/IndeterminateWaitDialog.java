package me.chenyi.mm.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple dialog that displays a message and an animated gif to show that
 * work is 'in progress'. It should be used when the length of a task is not
 * known, but it is necessary to show that work is being done.
 *
 */
public class IndeterminateWaitDialog extends JDialog
{
    private static final String IMAGE = "images/gears.gif";

    private JLabel messageLabel;
    private AnimationPanel animationPanel;

    private boolean cancelled;
    private JButton cancelButton;

    public IndeterminateWaitDialog(Window owner)
    {
        this(owner, "Waiting...", "", false, "");
    }

    public IndeterminateWaitDialog(Window owner, String title, String waitText, boolean includeCancelButton, String cancelText)
    {
        super(owner, title, ModalityType.APPLICATION_MODAL);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createDisplayPanel(includeCancelButton), BorderLayout.CENTER);

        setWaitText(waitText);
        setCancelText(cancelText);
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void showDialog(Runnable r)
    {
        cancelled = false;

        Thread thread = new Thread(new RunnableWrapper(this, r));
        thread.start();

        setVisible(true);
    }

    public void setWaitText(String t)
    {
        messageLabel.setText(t);
    }

    public void setCancelText(String t)
    {
        if (cancelButton != null)
            cancelButton.setText(t);
    }

    /**
     * Overriden to ensure the animation is either started or stopped.
     *
     * @param v true to show the dialog, false to hide it.
     */
    public void setVisible(boolean v)
    {
        if (v)
        {
            pack();
            setLocationRelativeTo(getParent());

            // set the size and position of the dialog
            animationPanel.start();
        }
        else
            animationPanel.stop();

        super.setVisible(v);
    }

    private JPanel createDisplayPanel(boolean includeCancelButton)
    {
        JPanel p = new JPanel(false);
        p.setBorder(new EmptyBorder(3, 3, 3, 3));
        p.setLayout(new BorderLayout(10, 10));

        messageLabel = new JLabel("");
        p.add(messageLabel, BorderLayout.CENTER);

        animationPanel = new AnimationPanel(IMAGE);
        p.add(animationPanel, BorderLayout.EAST);

        if (includeCancelButton)
        {
            cancelButton = new JButton("");
            cancelButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    cancelled = true;
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            p.add(buttonPanel, BorderLayout.SOUTH);
            buttonPanel.add(cancelButton);
        }

        return p;
    }

    /**
     *
     */
    private class RunnableWrapper implements Runnable
    {
        private Runnable runnable;
        private JDialog dialog;

        public RunnableWrapper(JDialog d, Runnable r)
        {
            dialog = d;
            runnable = r;
        }

        public void run()
        {
            // not very nice, but nicer than before
            while (!dialog.isVisible())
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ie)
                {
                }
            }

            try
            {
                runnable.run();
            }
            catch(Throwable t)
            {
                t.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error during processing: " + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            finally
            {
                EventQueue.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
            }
        }
    }
}
