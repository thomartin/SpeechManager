package nz.ac.auckland.robot.speech_manager.connectors;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

public interface GenericConnector {
	
	public void synthesiseText(String text);
	public byte[] synthesiseText(HashMap<String, Object> params);
	

}
