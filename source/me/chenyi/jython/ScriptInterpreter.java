package me.chenyi.jython;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import org.python.core.*;
import org.python.util.PythonInterpreter;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptInterpreter extends PythonInterpreter
{
    private static final String ROUTINES = "me/chenyi/jython/jython_rules.py";

    private Map sharedCache;

    private static final String[] MODULES =
    {
        "string.py", // Ties in jython-lib.jar
        "me/chenyi/jython/jython_rules.py"
    };

    static
    {
        PySystemState.initialize();

        // Look up each module in MODULES and add its containing jar file to sys.path
        for (int i = 0; i < MODULES.length; i++)
        {
            String moduleName = MODULES[i];
            String jarPath = findJarContaining(moduleName);
            if (jarPath == null)
            {
                System.out.println("Unable to find jar file containing " + moduleName + " ");
            }
            else
            {
                File baseFile = new File(jarPath);
                String nestedPath = "";

                while(!baseFile.exists())
                {
                    nestedPath = (nestedPath.equals(""))? baseFile.getName() : baseFile.getName()+"/"+nestedPath;
                    baseFile = baseFile.getParentFile();
                }

                try
                {
                    SyspathArchive archive = new SyspathArchive(jarPath);
                    System.out.println("Creating SyspathArchiveHack("+jarPath+")");
                    Py.getSystemState().path.append(archive);
                    System.out.println("Added "+jarPath+" to the Jython classpath");
                }
                catch (IOException e)
                {
                    System.out.println("Unable to create SyspathArchive for " + moduleName);
                    System.out.println("try to add it as path");
                    try
                    {
                        Py.getSystemState().path.append(new PyString(jarPath));
                    }
                    catch(Exception e1)
                    {
                        System.out.println("fail to to add as path again, ignore it: " + jarPath);
                        e1.printStackTrace();
                    }
//                    e.printStackTrace();
                }
            }
        }

        //Py.getSystemState().callExitFunc();
    }

    /**
     * Default constructor
     */
    protected ScriptInterpreter(Map sharedCache)
    {
        this.sharedCache = sharedCache;

        loadLibrary(ROUTINES);
    }

    public boolean loadLibrary(String libraryResource) throws IllegalArgumentException
    {
        try
        {
            PyCode libraryCode = (PyCode) sharedCache.get(libraryResource);
            if (libraryCode == null)
            {
                synchronized (sharedCache)
                {
                    libraryCode = (PyCode) sharedCache.get(libraryResource);
                    if (libraryCode == null)
                    {
                        // load the resource
                        ClassLoader classLoader = getClass().getClassLoader();
                        BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(libraryResource)));

                        int length = 0;
                        char[] cbuf = new char[1024];
                        StringBuffer buffer = new StringBuffer();
                        while ((length = br.read(cbuf)) != -1)
                            buffer.append(cbuf, 0, length);

                        libraryCode = compile(buffer.toString());
//System.out.println("** Interpreter.store library cache value");
                        sharedCache.put(libraryResource, libraryCode);
                    }
                }
            }

            super.exec(libraryCode);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Interpreter library '" + libraryResource + "' could not be loaded: " + e.getMessage());
        }
    }

    /**
     * Allows code to be compile and cached without having to run the code.
     *
     * @param script the script to be pre-compiled and stored
     */
    public void doCompile(String script)
    {
        // check to see if we have a cache defined for this code
        PyCode scriptCode = (PyCode) sharedCache.get(script);
        if (scriptCode == null)
        {
            synchronized (sharedCache)
            {
                scriptCode = (PyCode) sharedCache.get(script);
                if (scriptCode == null)
                {
                    scriptCode = compile(script);
                    sharedCache.put(script, scriptCode);
                }
            }
        }
    }

    /**
     * Called to execute a generic script that can be used by all columns.
     * This script does not return any values, but instead it is assummed that
     * all those that reference the code will know about the script variables.
     *
     * @param script the script to be run.
     */
    public void exec(String script)
            throws UnsupportedOperationException, IllegalStateException
    {
        try
        {
            // check to see if we have a cache defined for this code
            PyCode scriptCode = (PyCode) sharedCache.get(script);
            if (scriptCode == null)
            {
                synchronized (sharedCache)
                {
                    scriptCode = (PyCode) sharedCache.get(script);
                    if (scriptCode == null)
                    {
//                        System.out.println("Compiling rule " + script);
                        scriptCode = compile(script);
                        sharedCache.put(script, scriptCode);
                    }
                }
            }

            super.exec(scriptCode);
        }
        catch (PyException pye)
        {
            StringBuffer errorBuffer = new StringBuffer("\n** Problem parsing following python script: **\n");
            errorBuffer.append(script);
            errorBuffer.append("\n");
            errorBuffer.append("** ------------------------- **\n");
            errorBuffer.append(pye.toString());
            errorBuffer.append("** ------------------------- **\n");

            throw new UnsupportedOperationException(errorBuffer.toString());
        }
        catch(Exception e)
        {
            System.out.println("Interpreter.exec exception " + e.getMessage());
            System.out.println("** Script = " + script);
            System.out.println("-----------------------");
        }
    }

    /**
     * Parses the equation and returns a result as either an Object based on PyObject
     * or null if there was no value. This method should be called when an arbitrary
     * value of unknown type is returned.
     *
     * @param eqn the equation to be interpreted.
     * @return an Object representing the results of the equation or null if there was
     *         no result. The value returned will be based on PyObject.
     */
    public Object parse(String eqn)
            throws UnsupportedOperationException
    {
        StringBuffer eqnBuffer = new StringBuffer("result = ")
                .append(eqn);

        try
        {
            exec(eqnBuffer.toString());
            PyObject result = get("result");
            if (result instanceof PyNone)
                return null;
            else
                return result;
        }
        catch (PyException pye)
        {
            throw new UnsupportedOperationException("Error parsing results");
        }
    }

    public static String findJarContaining(String item)
    {
        String header = "jar:file:";
        String header2 = "vfszip:";
        String header3 = "file:";
        String trailer = "!/" + item;
        String trailer2 = "/" + item;
        String trailer3 = "/" + item;

        URL url = ScriptInterpreter.class.getResource("/" + item);
        // We should get something like
        // "jar:file:/C:/Documents and Settings/kejohnson/.javaws/cache/http/Dlocalhost/P80/DMdemo/DMlib/RMmyjython.jar!/__run__.py"
        // or "vfszip:/D:/iICE/Server/jboss-5.1.0.GA/server/iICE/deploy/iMercury-test.ear/lib/InnovationCommon.jar/resource/rules.py"
        if (url == null)
        {
            System.out.println("Main: getResource failed for " + item);
            return null;
        }

        String path = url.toString();

        try
        {
            path = URLDecoder.decode(url.toString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("Interpreter: " + e.getMessage());
        }

        System.out.println(path);

        if (!path.startsWith(header) || !path.endsWith(trailer))
        {
            if (!path.startsWith(header2) || !path.endsWith(trailer2))
            {
                if(!path.startsWith(header3) || !path.endsWith(trailer3))
                {
                    System.out.println("Interpreter: Can't interpret URL for " + item + ": " + path);
                    return null;
                }
            }
        }

        if (path.startsWith(header))
        {
            // Strip header and trailer
            path = path.substring(header.length(), path.length() - trailer.length());
        }
        else if (path.startsWith(header2))
        {
            // Strip header2 and trailer2
            path = path.substring(header2.length(), path.length() - trailer2.length());
        }
        else if (path.startsWith(header3))
        {
            // Strip header2 and trailer2
            path = path.substring(header3.length(), path.length() - trailer3.length());
        }

        // path is in local drive windows
        if (path.matches("/[a-zA-Z]:.*"))
        {
            path = path.substring(1);
        }
        // path is in the network
        else if (path.matches("//.*"))
        {
            // does nothing
        }
        // path is linux
        else
        {
            // does nothing
        }

        System.out.println("Path for " + item + ": " + path);
        return path;
    }
}
