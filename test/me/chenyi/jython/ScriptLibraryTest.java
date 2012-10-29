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
}
