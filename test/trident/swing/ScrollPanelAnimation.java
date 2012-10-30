package trident.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import org.pushingpixels.trident.Timeline;

public class ScrollPanelAnimation extends JFrame {
	public ScrollPanelAnimation() {
		getContentPane().setLayout(new BorderLayout());

                JPanel panel = new JPanel(){
            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(800, 600);
            }
        };
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setMaximum(100);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

		JButton button = new JButton("sample");
		button.setForeground(Color.blue);

		getContentPane().add(button, BorderLayout.SOUTH);

		final Timeline timeline = new Timeline(scrollPane.getVerticalScrollBar());
		timeline.addPropertyToInterpolate("Value", (int)0, (int)100);
		timeline.setDuration(2500);
		button.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timeline.getState() == Timeline.TimelineState.CANCELLED
                    || timeline.getState() == Timeline.TimelineState.DONE
                    || timeline.getState() == Timeline.TimelineState.IDLE)
                    timeline.play();
                else
                    timeline.abort();
            }
        });

		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ScrollPanelAnimation().setVisible(true);
			}
		});
	}
}
