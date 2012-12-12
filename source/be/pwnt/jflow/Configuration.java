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

public class Configuration {
	public enum VerticalAlignment {
		TOP, MIDDLE, BOTTOM
	}

	//define how many shapes will show up.
    //for example, you have 14 shapes, at beginning, it will show: 0, 1, 2, 3, 4, 5, 6, 7, 8
    //when it scroll to right for one shape, it will show: 1, 2, 3, 4, 5, 6, 7, 8, 9
    public int visibleShapeCount = 9;

	//define orientation of shapes, from left to right or from right to left.
    //LEFT_TO_RIGHT means: 0, 1, 2, 3, 4
    //LEFT_TO_RIGHT means: 4, 3, 2, 1, 0
    public ComponentOrientation shapeOrientation = ComponentOrientation.LEFT_TO_RIGHT;

	public double shapeWidth = 1.0 / 3;

	public VerticalAlignment verticalShapeAlignment = VerticalAlignment.BOTTOM;

    //the rotation for shapes.
	public double shapeRotation = Math.PI / 12;

	public double shapeSpacing = 1.0 / 2;

	public double scrollScale = 7.0 / 8;

    //scrollFactor define scroll speed when dragged, the bigger, the faster.
	public double scrollFactor = 10.0;

    //if autoScrollAmount is not zero, then the flow panel will always scroll.
    //( 1.0/16 ) is a good speed for auto scroll.
	public double autoScrollAmount = 0.0;

    //when user stop drag, whether scroll nearest shape to center.
	public boolean autoCentralizeShape = true;

    //this is a threshold which will stop auto scroll when shape is almost centered.
	public double dragEaseOutFactor = 0.04;

    //when 'send an command to roll to specified position', roll animation duration, in second.
	public double commandRollerDuration = 1.0;

    //when 'send an command to roll to specified position', roll animation interval, in second.
	public double commandRollerInterval = 0.1;

    //define scroll direction.
	public boolean inverseScrolling = false;

	public boolean enableShapeSelection = true;

	public int framesPerSecond = 25;

    //define the opacity of refection,
    //value range is from 0 to 1,
    //when reflectionOpacity = 0, there is no reflection
    //when reflectionOpacity = 1, there is no transparency effect.
	public double reflectionOpacity = 1.0 / 4;
	
	public double shadingFactor = 3.0;

    //what is this?
	public double zoomScale = 2.0;

    //and this?
	public double zoomFactor = 0.02;
	
	public int activeShapeBorderWidth = 3;

	public Color backgroundColor = Color.black;

	public Color activeShapeBorderColor = Color.yellow;
	
	public Color activeShapeOverlayColor = null;
	
	public boolean highQuality = false;
}
