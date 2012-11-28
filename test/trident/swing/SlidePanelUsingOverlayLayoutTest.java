package trident.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.pushingpixels.trident.Timeline;

/** Created with IntelliJ IDEA. User: seanc Date: 28/11/12 Time: 13:45 */
public class SlidePanelUsingOverlayLayoutTest
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        SlidePanel slidePanel = new SlidePanel(frame.getContentPane());
        frame.getContentPane().add(slidePanel, BorderLayout.CENTER);

        JButton runButton = new JButton("Run");
        final Timeline timeline = new Timeline(slidePanel);
        timeline.setDuration(2500);
        runButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!timeline.isDone())
                    timeline.cancel();
                timeline.clearPropertyToInterpolate();
                timeline.addPropertyToInterpolate("slide", 0, 100);
                timeline.play();
            }
        });
        frame.getContentPane().add(runButton, BorderLayout.NORTH);


        frame.setVisible(true);
    }

    public static class SlidePanel extends JPanel
    {
        public Component parent;

        public int slide;
        private final JPanel backPanel = new JPanel();
        private final JPanel frontPanel = new JPanel();

        private SlidePanel(Component parent)
        {
            this.parent = parent;

            setLayout(new OverlayLayout(SlidePanel.this));
            backPanel.setBackground(Color.green);
            backPanel.setMaximumSize(new Dimension(400, 300));
            add(backPanel);
            frontPanel.setBackground(Color.blue);
            frontPanel.setMaximumSize(new Dimension(300, 400));
            add(frontPanel);
        }

        public void setSlide(int slide)
        {
            this.slide = slide;
            Dimension size = parent.getSize();
            backPanel.setMaximumSize(new Dimension(size.width * slide / 100, size.height * slide / 100));
            revalidate();
        }

        public int getSlide()
        {
            return slide;
        }
    }

}

