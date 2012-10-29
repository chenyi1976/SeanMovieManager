package me.chenyi.jython.action;

// External imports
// None

// Local imports
// None

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import me.chenyi.jython.Script;
import me.chenyi.jython.ScriptUtilities;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptAction extends  AbstractAction
{
    private Script script;

    public ScriptAction(Script script)
    {
        super(script.getName());
        this.script = script;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ScriptUtilities.executeScript(script.getScriptContent());
    }
}
