package me.chenyi.jython.editor;

import javax.swing.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptEditorFrameTest
{
    public static void main(String[] args)
    {
        ScriptEditorFrame scriptEditorFrame = ScriptEditorFrame.getInstance();
        scriptEditorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scriptEditorFrame.setVisible(true);
    }
}
