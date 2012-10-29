package be.pwnt.jflow;

import be.pwnt.jflow.geometry.Point3D;
import be.pwnt.jflow.geometry.RotationMatrix;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 17/08/12
 * Time: 7:50
 */
public class SceneTest {

    Scene scene;

    @Before
    public void setUp() throws Exception {
        scene = new Scene(new Point3D(0, 0, 1), new RotationMatrix(0, 0, 0), new Point3D(0, 0, 1));
    }

    @Test
    public void testProject() throws Exception {
        Point3D project = scene.project(new Point3D(-1, -1, 0), new Dimension(800, 300));
        System.out.println("project = " + project);
        project = scene.project(new Point3D(-1, 1, 0), new Dimension(800, 300));
        System.out.println("project = " + project);
        project = scene.project(new Point3D(1, 1, 0), new Dimension(800, 300));
        System.out.println("project = " + project);
        project = scene.project(new Point3D(1, -1, 0), new Dimension(800, 300));
        System.out.println("project = " + project);
        project = scene.project(new Point3D(0, 0, 0), new Dimension(800, 300));
        System.out.println("project = " + project);
    }
}
