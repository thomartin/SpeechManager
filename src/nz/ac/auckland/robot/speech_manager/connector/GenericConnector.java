package nz.ac.auckland.robot.speech_manager.connector;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

public interface GenericConnector {
	
	public void SynthesiseText(String text);
	public void SynthesiseText(List<NameValuePair> params);
	

}
