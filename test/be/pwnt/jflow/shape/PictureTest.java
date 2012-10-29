package be.pwnt.jflow.shape;

import be.pwnt.jflow.demo.DemoFlowModel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 17/08/12
 * Time: 08:54
 */
public class PictureTest {

    @Test
    public void testProject() throws Exception {
        DemoFlowModel configuration = new DemoFlowModel();
        Picture picture = (Picture) configuration.getShape(0);
//                       picture.

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));

        DemoFlowModel configuration = new DemoFlowModel();
//        Picture picture = (Picture) configuration.shapes[0];
//        frame.getContentPane().add(picture);

        frame.setVisible(true);
    }

}
