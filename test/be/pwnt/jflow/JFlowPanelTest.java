package be.pwnt.jflow;

import javax.swing.*;

import java.awt.*;

import be.pwnt.jflow.demo.DemoFlowModel;
import be.pwnt.jflow.event.ShapeEvent;
import be.pwnt.jflow.event.ShapeListener;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 17/08/12
 * Time: 07:56
 */
public class JFlowPanelTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        final DemoFlowModel demoModel = new DemoFlowModel();
        Configuration config = new Configuration();
        config.shapeRotation =  Math.PI / 30;
        config.shapeSpacing = 0.4;
//        config.scrollScale = 1.0;
//        config.verticalShapeAlignment = Configuration.VerticalAlignment.TOP;
//        config.scrollFactor = 10;
//        config.autoScrollAmount = 1.0 / 16;
//        config.shapeOrientation = ComponentOrientation.RIGHT_TO_LEFT;
        final JFlowPanel flowPanel = new JFlowPanel(config, demoModel);
        flowPanel.addShapeListener(new ShapeListener()
        {
            @Override
            public void shapeActivated(ShapeEvent e)
            {

            }

            @Override
            public void shapeDeactivated(ShapeEvent e)
            {

            }

            @Override
            public void shapeClicked(ShapeEvent e)
            {
                flowPanel.scrollToShape(e.getShape());
            }

            @Override
            public void shapeCentered(ShapeEvent e)
            {
                Shape shape = e.getShape();
                System.out.println("shape = " + shape);
            }
        });
        frame.getContentPane().add(flowPanel);
        System.out.println("flowPanel.getCenterShape() = " + flowPanel.getCenterShape());
        frame.setVisible(true);
    }

//    @Test
//    public void testDiv()
//        throws Exception
//    {
//        System.out.println("(-2 % 100) = " + (-2 % 100));
//    }
}
