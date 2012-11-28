package trident.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import me.chenyi.mm.ui.AlphaPanel;
import org.pushingpixels.trident.Timeline;

/** Created with IntelliJ IDEA. User: seanc Date: 28/11/12 Time: 13:45 */
public class SlidePanelWithoutLayoutTest
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
                timeline.addPropertyToInterpolate("ratio", 0f, 1f);
                timeline.addPropertyToInterpolate("alpha", 0f, 1f);
                timeline.play();
            }
        });
        frame.getContentPane().add(runButton, BorderLayout.NORTH);


        frame.setVisible(true);
    }

    public static class SlidePanel extends JPanel
    {
        public Component parent;

        public float ratio;
        public float alpha;

        private final AlphaPanel backPanel = new AlphaPanel();
        private final JPanel frontPanel = new JPanel();

        private SlidePanel(Component parent)
        {
            this.parent = parent;

            setLayout(null);
            backPanel.setBackground(Color.green);
            backPanel.setMaximumSize(new Dimension(400, 300));
            backPanel.setPreferredSize(new Dimension(400, 300));
            backPanel.setSize(new Dimension(400, 300));
            backPanel.setLocation(100, 100);
            backPanel.add(new JLabel("This is a label"));
            backPanel.setAlpha(0.1f);
            backPanel.setOpaque(false);
            add(backPanel);
            frontPanel.setBackground(Color.blue);
            frontPanel.setMaximumSize(new Dimension(300, 400));
            frontPanel.setPreferredSize(new Dimension(300, 400));
            frontPanel.setSize(new Dimension(300, 400));
            frontPanel.setLocation(50, 50);
//            add(frontPanel);

            parent.addComponentListener(new ComponentAdapter()
            {
                @Override
                public void componentResized(ComponentEvent e)
                {

                }

                @Override
                public void componentMoved(ComponentEvent e)
                {
                }

                @Override
                public void componentShown(ComponentEvent e)
                {
                }

                @Override
                public void componentHidden(ComponentEvent e)
                {
                }
            });
        }

        public void setRatio(float ratio)
        {
            this.ratio = ratio;
            Dimension size = parent.getSize();
//            backPanel.setMaximumSize(new Dimension(size.width * slide / 100, size.height * slide / 100));
            backPanel.setSize(new Dimension((int)(size.width * ratio), (int)(size.height * ratio)));
            revalidate();
        }

        public float getRatio()
        {
            return ratio;
        }

        public float getAlpha()
        {
            return alpha;
        }

        public void setAlpha(float alpha)
        {
            this.alpha = alpha;
            backPanel.setAlpha(alpha);
//            revalidate();
        }
    }

}

