package me.chenyi.jython;

import org.junit.Test;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptLibraryTest
{

    @Test
    public void testAddMovie()
        throws Exception
    {
        ScriptLibrary library = new ScriptLibrary();
        library.addMovie("The Avengers");
    }

    @Test
    public void testShowWaitDialog()
        throws Exception
    {
        ScriptLibrary library = new ScriptLibrary();
        library.showWaitDialog("Hi");
        Thread.sleep(1000);
        library.showWaitDialog("This is Sean");
        Thread.sleep(1000);
        library.showWaitDialog("Bye");
        Thread.sleep(1000);
        System.out.println("this is test");
    }
}
