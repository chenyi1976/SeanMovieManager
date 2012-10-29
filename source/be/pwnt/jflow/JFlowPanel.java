/*
 * JFlow
 * Created by Tim De Pauw <http://pwnt.be/>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package be.pwnt.jflow;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import be.pwnt.jflow.event.ShapeEvent;
import be.pwnt.jflow.event.ShapeListener;
import be.pwnt.jflow.geometry.Point3D;
import be.pwnt.jflow.geometry.RotationMatrix;
import be.pwnt.jflow.model.FlowModel;
import be.pwnt.jflow.shape.Picture;

@SuppressWarnings("serial")
public class JFlowPanel extends JPanel
{
	private Collection<ShapeListener> listeners;

	private Configuration config;
    private FlowModel flowModel;

	private Scene scene;

	private double scrollDelta;

	private double dragStart;

	private double dragRate;

	private boolean buttonOnePressed;

	private boolean dragging;

	private Shape activeShape;

    private Shape centerShape;

	private Timer easingTimer;

	private Timer commandRollerTimer;

	private int shapeArrayOffset;

    private EventAdapter ea;

	public JFlowPanel(Configuration config, FlowModel flowModel) {
		super();

		this.config = config;
        this.flowModel = flowModel;

        ea = new EventAdapter();

        flowModel.addListDataListener(ea);

		listeners = new HashSet<ShapeListener>();
		scene = new Scene(new Point3D(0, 0, 1), new RotationMatrix(0, 0, 0),
				new Point3D(0, 0, 1));
		buttonOnePressed = false;
		dragging = false;
		shapeArrayOffset = 0;
		activeShape = null;
        centerShape = null;
		setLayout(null);
		setBackground(config.backgroundColor);
		setScrollRate(0);
		if (config.autoScrollAmount != 0) {
			new Timer().scheduleAtFixedRate(new AutoScroller(), 0,
					1000 / config.framesPerSecond);
		}
		addMouseListener(ea);
		addMouseMotionListener(ea);
		//to make it work with touch pad of macbook.
        addMouseWheelListener(ea);
	}

	public void addShapeListener(ShapeListener listener) {
		listeners.add(listener);
	}

	public void removeShapeListener(ShapeListener listener) {
		listeners.remove(listener);
	}

	public synchronized double getScrollRate() {
		return scrollDelta;
	}

	public synchronized void setScrollRate(double scrollRate) {
//        System.out.println("scrollRate = " + scrollRate);
		this.scrollDelta = scrollRate;
		normalizeScrollRate();
		updateShapes();
        if (scrollDelta == 0)
        {
            Shape shape = flowModel.getShape(getShapeIndexByFlowPos(config.visibleShapeCount / 2));
            setCenterShape(shape);
        }
	}

	private synchronized void normalizeScrollRate() {
		while (scrollDelta < -0.5) {
			scrollDelta += 1;
			if (--shapeArrayOffset < 0) {
				shapeArrayOffset += flowModel.getSize();
			}

		}
		while (scrollDelta > 0.5) {
			scrollDelta -= 1;
			if (++shapeArrayOffset >= flowModel.getSize()) {
				shapeArrayOffset -= flowModel.getSize();
			}
		}
	}

    private void updateShape(int i, double maxHeight)
    {
        int shapeIndex = getShapeIndexByFlowPos(i);
        if(flowModel.getShape(shapeIndex) instanceof Picture)
        {
            Picture pic = (Picture)flowModel.getShape(shapeIndex);
            double j = i - config.visibleShapeCount / 2
                + scrollDelta;
            j = (j < 0 ? -1 : 1)
                * Math.pow(Math.abs(j), config.scrollScale);
            double comp = 0;
            if(j < 0)
            {
                comp = config.shapeWidth / 2;
            }
            else if(j > 0)
            {
                comp = -config.shapeWidth / 2;
            }
            double height = config.shapeWidth * pic.getHeight() / pic.getWidth();
            double top, bottom;
            switch(config.verticalShapeAlignment)
            {
                case TOP:
                    top = maxHeight / 2 - height;
                    bottom = maxHeight / 2;
                    break;
                case BOTTOM:
                    top = -maxHeight / 2;
                    bottom = -maxHeight / 2 + height;
                    break;
                default:
                    top = -height / 2;
                    bottom = height / 2;
            }
            double z = -config.zoomFactor * Math.pow(Math.abs(j), config.zoomScale);
            Point3D topLeft = new Point3D(
                -config.shapeWidth / 2 + (config.scrollDirection == ComponentOrientation.LEFT_TO_RIGHT ? -1 : 1) * comp,
                top, 0);
            Point3D bottomRight = new Point3D(
                config.shapeWidth / 2 + (config.scrollDirection == ComponentOrientation.LEFT_TO_RIGHT ? -1 : 1) * comp,
                bottom, 0);
            pic.setCoordinates(topLeft, bottomRight);
            pic.setRotationMatrix(new RotationMatrix(0,
                                                     (config.scrollDirection == ComponentOrientation.LEFT_TO_RIGHT ? 1 : -1) * config.shapeRotation * j,
                                                     0));
            pic.setLocation(new Point3D(
                (config.scrollDirection == ComponentOrientation.LEFT_TO_RIGHT ? 1 : -1) * (-config.shapeSpacing * j + comp),
                0, z));
        }
    }

	// FIXME only works for Pictures
	private synchronized void updateShapes() {
		double maxHeight = 0;
		for (int i = 0; i < config.visibleShapeCount; i++)
        {
            Shape shape = flowModel.getShape(getShapeIndexByFlowPos(i));
			if (shape instanceof Picture) {
				Picture pic = (Picture) shape;
				double height = config.shapeWidth * pic.getHeight()
						/ pic.getWidth();
				if (height > maxHeight) {
					maxHeight = height;
				}
			}
		}
        //update the shape in the side, then update the middle shape,
        //so that if the shape count is smaller than visible count, the shapes will align to center.
        for (int i = 0; i < config.visibleShapeCount / 2; i++) {
            updateShape(i, maxHeight);
            updateShape(config.visibleShapeCount - 1 - i, maxHeight);
		}
        updateShape(config.visibleShapeCount / 2, maxHeight);
		checkActiveShape();
		repaint();
	}

    public Shape getCenterShape()
    {
        return centerShape;
    }

    public Shape getActiveShape()
    {
        return activeShape;
    }

    private synchronized void setCenterShape(Shape shape)
    {
		if (centerShape != shape) {
			centerShape = shape;
            ShapeEvent evt = new ShapeEvent(shape);
            for (ShapeListener listener : listeners) {
                listener.shapeCentered(evt);
            }
		}
    }

	private synchronized void setActiveShape(Shape shape) {
		if (activeShape != shape) {
			if (activeShape != null) {
				ShapeEvent evt = new ShapeEvent(shape);
				for (ShapeListener listener : listeners) {
					listener.shapeDeactivated(evt);
				}
			}
			activeShape = shape;
			if (activeShape != null) {
				ShapeEvent evt = new ShapeEvent(shape);
				for (ShapeListener listener : listeners) {
					listener.shapeActivated(evt);
				}
			}
		}
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		// respect stacking order
		for (int i = 0; i < config.visibleShapeCount / 2; i++) {
			paintShape(flowModel.getShape(getShapeIndexByFlowPos(i)), g);
			paintShape(flowModel.getShape(getShapeIndexByFlowPos(config.visibleShapeCount - 1 - i)), g);
		}
		paintShape(flowModel.getShape(getShapeIndexByFlowPos(config.visibleShapeCount / 2)), g);
	}

	// get shape index by ui position
	private int getShapeIndexByFlowPos(int flowPosition) {
//        System.out.println("ui position = " + flowPosition);
        int size = flowModel.getSize();
        if (size <= 0)
            return -1;
        int pos = (flowPosition - shapeArrayOffset) % size;
        while (pos < 0)
        {
            pos += size;
        }
        while (pos >= size)
        {
            pos -= size;
        }
//        System.out.println("shape index = " + pos);
        return pos;
	}

	// get ui position by shape index
	private int getFlowPosByShapeIndex(int shapeIndex) {
//        System.out.println("shape index = " + shapeIndex);
        int size = flowModel.getSize();
        if (size <= 0)
            return -1;
        int pos = (shapeIndex + shapeArrayOffset) % size;
        while (pos < 0)
        {
            pos += size;
        }
        while (pos >= size)
        {
            pos -= size;
        }
//        System.out.println("ui pos = " + pos);
        //the shape is not in the UI, so return -1;
        if (pos < 0 || pos >= config.visibleShapeCount)
            return -1;
        return pos;
	}

	private void paintShape(Shape shape, Graphics g) {
		shape.paint(g, scene, getSize(), shape == activeShape, config);
	}

	private void checkActiveShape() {
		if (config.enableShapeSelection) {
			SwingUtilities.invokeLater(new ActiveShapeChecker());
		}
	}

	private void updateCursor() {
		setCursor(dragging ? Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)
				: (activeShape == null ? Cursor.getDefaultCursor() : Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR)));
	}

    public void scrollToShape(Shape newShape)
    {
        int newShapeIndex = flowModel.getShapeIndex(newShape);

        int newShapeUiPos = getFlowPosByShapeIndex(newShapeIndex);
        if (newShapeUiPos == (config.visibleShapeCount / 2))
            return;

        if(commandRollerTimer != null)
        {
            commandRollerTimer.cancel();
        }
        if(easingTimer != null)//cancel the easing timer, otherwise easing timer will mess the command roller timer.
        {
            easingTimer.cancel();
        }
        commandRollerTimer = new Timer();
        double scrollOffset = (config.visibleShapeCount / 2) -  newShapeUiPos + scrollDelta;
        commandRollerTimer.scheduleAtFixedRate(new CommandScroller(scrollOffset), 0, 100);
    }

    private class AutoScroller extends TimerTask {
		@Override
		public void run() {
			if (!dragging) {
//                System.out.println("Auto Scroller");
				setScrollRate(getScrollRate() + config.autoScrollAmount);
			}
		}
	}

    private class CommandScroller extends TimerTask {

        private final double offsetPer;
        private final long taskCount;

        private long count;

        public CommandScroller(double scrollOffset)
        {
            taskCount = Math.round(config.commandRollerDuration / 0.1);

            offsetPer = scrollOffset / taskCount;

            count = 0;
        }

        @Override
		public void run() {
            if (offsetPer == 0 || count > taskCount)
            {
                commandRollerTimer.cancel();
                setScrollRate(0);//so that the shape will always be in the center position.
                return;
            }

//            System.out.println("Command Scroller");
            setScrollRate(scrollDelta + offsetPer);

            count ++;
		}
	}

	private class DragEaser extends TimerTask {
		@Override
		public void run() {
			if (scrollDelta > 0)
            {
                if (scrollDelta > config.dragEaseOutFactor)
                    scrollDelta -= config.dragEaseOutFactor;
                else
                    scrollDelta = 0;
            }
            else
            {
                if (-scrollDelta > config.dragEaseOutFactor)
                    scrollDelta += config.dragEaseOutFactor;
                else
                    scrollDelta = 0;
            }
//            System.out.println("Eraser Scroller");
            setScrollRate(scrollDelta);
            if (scrollDelta == 0)
				easingTimer.cancel();
		}
	}

	private class ActiveShapeChecker implements Runnable {
		@Override
		public void run() {
			if (!dragging) {
				Point mp = getMousePosition();
				Shape newActiveShape = null;
				if (mp != null) {
					Point3D p = new Point3D(mp.getX(), mp.getY(), 0);
					int i = getShapeIndexByFlowPos(config.visibleShapeCount / 2);
					if (flowModel.getShape(i).contains(p)) {
						newActiveShape = flowModel.getShape(i);
					}
					i = 1;
					while (i <= config.visibleShapeCount / 2
							&& newActiveShape == null) {
						int j = getShapeIndexByFlowPos(config.visibleShapeCount / 2 - i);
						int k = getShapeIndexByFlowPos(config.visibleShapeCount / 2 + i);
						if (flowModel.getShape(j).contains(p)) {
							newActiveShape = flowModel.getShape(j);
						} else if (flowModel.getShape(k).contains(p)) {
							newActiveShape = flowModel.getShape(k);
						}
						i++;
					}
				}
				repaint();
				if (activeShape != newActiveShape) {
					setActiveShape(newActiveShape);
				}
			}
			updateCursor();
		}
	}

    private class EventAdapter implements MouseListener, MouseMotionListener, MouseWheelListener, ListDataListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(config.enableShapeSelection && activeShape != null)
            {
                ShapeEvent evt = new ShapeEvent(activeShape, e);
                for(ShapeListener listener : listeners)
                {
                    listener.shapeClicked(evt);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(config.autoScrollAmount == 0)
            {
                if(easingTimer != null)
                {
                    easingTimer.cancel();
                }
            }
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                buttonOnePressed = true;
                dragStart = e.getLocationOnScreen().getX();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            buttonOnePressed = false;
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                dragging = false;
                updateCursor();
                checkActiveShape();
                if(config.autoScrollAmount == 0)
                {
                    if(easingTimer != null)
                    {
                        easingTimer.cancel();
                    }
                    easingTimer = new Timer();
                    easingTimer.scheduleAtFixedRate(new DragEaser(), 0, 100);
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(config.autoScrollAmount == 0)
            {
                if(easingTimer != null)
                {
                    easingTimer.cancel();
                }
            }
            if(buttonOnePressed)
            {
                dragging = true;
                setActiveShape(null);
                updateCursor();
                double dragEnd = e.getLocationOnScreen().getX();
                dragRate = config.scrollFactor * (dragEnd - dragStart) / getWidth();
                setScrollRate(getScrollRate()
                                  + (config.inverseScrolling ? dragRate : -dragRate));
                dragStart = dragEnd;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            checkActiveShape();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            dragRate = config.scrollFactor * e.getUnitsToScroll() / getWidth();
            setScrollRate(getScrollRate()
                              + (config.inverseScrolling ? dragRate : -dragRate));

            checkActiveShape();
            if(config.autoScrollAmount == 0)
            {
                if(easingTimer != null)
                {
                    easingTimer.cancel();
                }
                easingTimer = new Timer();
                easingTimer.scheduleAtFixedRate(new DragEaser(), 0, 100);
            }
        }

        @Override
        public void intervalAdded(ListDataEvent e)
        {
            System.out.println("JFlowPanel$EventAdapter.intervalAdded");
            updateShapes();
        }

        @Override
        public void intervalRemoved(ListDataEvent e)
        {
            System.out.println("JFlowPanel$EventAdapter.intervalRemoved");
            updateShapes();
        }

        @Override
        public void contentsChanged(ListDataEvent e)
        {
            System.out.println("JFlowPanel$EventAdapter.contentsChanged");
            updateShapes();
        }
    }
}
