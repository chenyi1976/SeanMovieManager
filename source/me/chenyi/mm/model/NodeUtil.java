package me.chenyi.mm.model;

import java.io.File;
import java.util.List;

import me.chenyi.mm.service.ServiceUtilities;
import me.chenyi.mm.util.ConfigUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class NodeUtil
{
    public static File getImageFile(Node node, String imageType)
    {
        if (node == null || imageType == null)
            return null;

        String imageSize = ConfigUtil.getInstance().getConfig(imageType + "_size");
        if(imageSize == null)
        {
            List<String> imageSizeList = ServiceUtilities.getImageSizeList(imageType);
            if(imageSizeList == null || imageSizeList.size() == 0)
            {
                return null;
            }
            imageSize = imageSizeList.get(0);
            if(imageSize == null)
            {
                return null;
            }
        }
        Object fileObj = node.getAttributeValue(imageType + "_" + imageSize);
        if(!(fileObj instanceof File))
        {
            return null;
        }
        return (File)fileObj;
    }
}
