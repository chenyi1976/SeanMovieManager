package me.chenyi.jython;

// External imports
// None

// Local imports
// None

import me.chenyi.jython.ScriptUtilities;
import org.junit.Test;
import org.python.util.PythonInterpreter;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class JythonTest
{
    @Test
    public void testFirstJythonScript()
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        sb.append("import random\n");
        sb.append("print random.random()\n");
        new PythonInterpreter().exec(sb.toString());
    }

    @Test
    public void testScript()
        throws Exception
    {
        ScriptUtilities.executeScript("setCurrentItemId(100)\nprint getCurrentItemId()");
    }

    @Test
    public void testSwing()
        throws Exception
    {
        StringBuffer script = new StringBuffer();
        script.append("from javax.swing import JOptionPane\n");
        script.append("JOptionPane.showMessageDialog(None, 'Hello World')");
        ScriptUtilities.executeScript(script.toString());
    }
}
