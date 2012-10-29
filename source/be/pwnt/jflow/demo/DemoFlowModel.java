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

package be.pwnt.jflow.demo;

import javax.swing.*;
import java.io.IOException;

import be.pwnt.jflow.Shape;
import be.pwnt.jflow.model.FlowModel;
import be.pwnt.jflow.shape.Picture;

public class DemoFlowModel extends AbstractListModel implements FlowModel
{

    private Shape[] shapes;

    public DemoFlowModel() {
		shapes = new Shape[14];
		for (int i = 0; i < shapes.length; i++) {
			try {
				shapes[i] = new Picture(getClass().getResource(
						"img/pic" + (i + 1) + ".jpg"));
			} catch (IOException e) {
			}
		}
	}

//    @Override
//    public int getTotalShapeCount()
//    {
//        return shapes.length;
//    }

    @Override
    public Shape getShape(int index)
    {
        if(index < 0 || index >= getSize())
            return null;
        return shapes[index];
    }

    @Override
    public int getShapeIndex(Shape shape)
    {
        if (shape == null)
            return -1;
        int index = -1;
        for(Shape s : shapes)
        {
            index ++;
            if (shape.equals(s))
                return index;
        }
        return -1;
    }

    @Override
    public int getSize()
    {
        return shapes.length;
    }

    @Override
    public Object getElementAt(int index)
    {
        return getShape(index);
    }
}
