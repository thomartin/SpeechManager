package nz.ac.auckland.robot.speech_manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to handle loading of properties from the configuration file
 * @author sjai013
 *
 */
public class ConfigManager 
{
	final String propFilePath;
	Properties prop;
	
	public ConfigManager() throws IOException
	{
		this("H:\\config.properties");
	}
	
	public ConfigManager(String propFilePath) throws IOException
	{
		this.propFilePath = propFilePath;

		this.loadProperties();
		
				
	}
	
	/**
	 * Load properties
	 * @throws IOException
	 */
	private void loadProperties() throws IOException
	{

		
		File configFile = new File(propFilePath);
		
		
		//Make sure config file exists - if it doesn't, copy default file
		if (!configFile.exists())
		{
			//TODO: Copy default file
		}
		
		//Make sure it exists now, otherwise throw an exception
		if (!configFile.exists())
		{
			throw new FileNotFoundException("Unable to read or create property file at '" + propFilePath + "'");
		}
		
		
		//If we get to here, file exists.  Read properties.
		FileInputStream configFis = new FileInputStream(propFilePath);
		this.prop = new Properties();
		this.prop.load(configFis);		
	}
	
	/**
	 * Get configuration property
	 * @param module Module to which property belongs
	 * @param moduleKey Name of key in module
	 * @return Value of property
	 */
	public String getProperty(String module, String moduleKey)
	{
		String key = module + "." + moduleKey;
		String propVal = this.prop.getProperty(key);
		return propVal;
	}
	
	/**
	 * Set configuration property
	 * @param module Module to which property belongs
	 * @param moduleKey Name of key in module
	 * @param value Value of property
	 * @throws FileNotFoundException Unable to find properties file
	 * @throws IOException Unable to write to properties file
	 */
	public void setProperty(String module, String moduleKey, String value) throws FileNotFoundException, IOException
	{
		String key = module + "." + moduleKey;
		
		this.prop.setProperty(key, value);
		this.prop.store(new FileOutputStream(propFilePath), "");
		
	}
	
}
