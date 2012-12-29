package me.chenyi.mm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

import me.chenyi.mm.util.FileUtil;
import me.chenyi.mm.util.SysUtil;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieManagerConfig
{
    private static Logger logger = Logger.getLogger(MovieManagerConfig.class.getName());

    private Properties userProperties;
    private Properties detaultProperties;
    private final String CONFIG_DEFAULT_PROPERTY = "smm.config.default.properties";
    private final String CONFIG_USER_PROPERTY = "smm.config.user.properties";

    MovieManagerConfig()
    {
        try
        {
            userProperties = loadUserConfig();
            detaultProperties = loadDefaultConfig();
        }
        catch(Exception e)
        {
            logger.warning(e.toString() + ": " + e.getMessage());
        }
    }

//    public static ConfigUtil getInstance()
//    {
//        if (instance == null)
//            instance = new ConfigUtil();
//        return instance;
//    }

    public String getConfig(String configName)
    {
        if (userProperties.containsKey(configName))
            return String.valueOf(userProperties.get(configName));
        if (detaultProperties.containsKey(configName))
            return String.valueOf(detaultProperties.get(configName));
        return null;
    }

    private Properties loadDefaultConfig()
        throws Exception
    {
        Properties configProperties = new Properties();
        String defaultConfigFilePath = SysUtil.getConfigDir() + "/" + CONFIG_DEFAULT_PROPERTY;
        File defaultFile = new File(defaultConfigFilePath);
        if (!defaultFile.exists())
        {
            FileUtil.copyToDir("/config/" + CONFIG_DEFAULT_PROPERTY, SysUtil.getConfigDir(), CONFIG_DEFAULT_PROPERTY);
        }
        FileInputStream fis = new FileInputStream(defaultFile);
        configProperties.load(fis);
        return configProperties;
    }

    private Properties loadUserConfig()
        throws Exception
    {
        Properties configProperties = new Properties();
        String filePath = SysUtil.getConfigDir() + "/" + CONFIG_USER_PROPERTY;
        File userFile = new File(filePath);
        if (!userFile.exists())
        {
            FileUtil.copyToDir("/config/" + CONFIG_USER_PROPERTY, SysUtil.getConfigDir(), CONFIG_USER_PROPERTY);
        }
        FileInputStream fis = new FileInputStream(userFile);
        configProperties.load(fis);
        return configProperties;
    }

}
