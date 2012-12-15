package me.chenyi.mm.ui;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;

import be.pwnt.jflow.*;
import be.pwnt.jflow.Shape;
import be.pwnt.jflow.event.ShapeEvent;
import be.pwnt.jflow.event.ShapeListener;
import me.chenyi.jython.ScriptEnvironment;
import me.chenyi.jython.ScriptLibrary;
import me.chenyi.jython.ScriptTriggerType;
import me.chenyi.jython.ScriptUtilities;
import me.chenyi.mm.flow.MovieFlowConfiguration;
import me.chenyi.mm.flow.MovieFlowModel;
import me.chenyi.mm.flow.NodePicture;
import me.chenyi.mm.model.Node;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieMainPanel extends CurtainPagePanel
{

    private MovieDetailPanel detailPanel;
    private JFlowPanel flowPanel;

    private EventAdapter ea;

    public MovieMainPanel(ICurtainController curtainController)
    {
        super(curtainController);

        ea = new EventAdapter();

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
                                                        GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        detailPanel = new MovieDetailPanel();
        add(detailPanel, gbc);

        gbc.gridy ++;
        gbc.weighty = 1;
        MovieFlowModel flowModel = new MovieFlowModel();
        flowModel.addListDataListener(ea);
        flowPanel = new JFlowPanel(new MovieFlowConfiguration(), flowModel);

        flowPanel.addShapeListener(ea);
        add(flowPanel, gbc);

        updateDetailInfoBySelection();
    }

    private void updateDetailInfoBySelection()
    {
        Shape shape = flowPanel.getCenterShape();
        if(shape instanceof NodePicture)
        {
            NodePicture nodePicture = (NodePicture)shape;
            Node node = nodePicture.getNode();
            if(node != null)
            {
                detailPanel.setNode(node);
                ScriptEnvironment.getInstance().setCurrentMovieId(node.getId());
                ScriptUtilities.executeScripts(ScriptTriggerType.OnMovieSelected);
            }
        }
    }

    private class EventAdapter implements ShapeListener, ListDataListener
    {
        @Override
        public void shapeActivated(ShapeEvent e)
        {
            System.out.println("MovieMainPanel$EventAdapter.shapeActivated");
        }

        @Override
        public void shapeDeactivated(ShapeEvent e)
        {

        }

        @Override
        public void shapeCentered(ShapeEvent e)
        {
            updateDetailInfoBySelection();
        }

        @Override
        public void shapeClicked(ShapeEvent e)
        {
//            System.out.println("MovieMainPanel$EventAdapter.shapeClicked");
            flowPanel.scrollToShape(e.getShape());
        }

        @Override
        public void intervalAdded(ListDataEvent e)
        {
            System.out.println("MovieMainPanel$EventAdapter.intervalAdded");
        }

        @Override
        public void intervalRemoved(ListDataEvent e)
        {
            System.out.println("MovieMainPanel$EventAdapter.intervalRemoved");
        }

        @Override
        public void contentsChanged(ListDataEvent e)
        {
            System.out.println("MovieMainPanel$EventAdapter.contentsChanged");
        }
    }

}
