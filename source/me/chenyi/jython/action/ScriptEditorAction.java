package me.chenyi.jython.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

import me.chenyi.jython.editor.ScriptEditorFrame;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptEditorAction extends AbstractAction
{
    public ScriptEditorAction()
    {
        super("Script Editor");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ScriptEditorFrame scriptEditorFrame = ScriptEditorFrame.getInstance();
        scriptEditorFrame.setVisible(true);
    }
}
