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
	final String propFilePath = "H:\\config.properties";
	Properties prop;
	
	public void loadProperties() throws IOException
	{

		
		File configFile = new File(propFilePath);
		
		
		//Make sure config file exists - if it doesn't, copy default file
		if (!configFile.exists())
		{
			//TODO: Copy to copy default file
		}
		
		//Make sure it exists now, otherwise throw an exception
		if (!configFile.exists())
		{
			throw new FileNotFoundException("Unable to read or create property file at '" + propFilePath + "'");
		}
		
		
		//If we get to here, file exists.  Read properties.
		FileInputStream configFis = new FileInputStream(propFilePath);
		prop = new Properties();
		prop.load(configFis);		
	}
	
	public String getProperty(String key)
	{
		String propVal = prop.getProperty(key);
		System.out.println(propVal);
		return propVal;
	}
	
	public void setProperty(String key, String value) throws FileNotFoundException, IOException
	{
		prop.setProperty(key, value);
		prop.store(new FileOutputStream(propFilePath), "");
		
	}
}
