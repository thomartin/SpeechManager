package nz.ac.auckland.robot.speech_manager.connectors;

import java.util.HashMap;

/**
 * Generic connector for an external TTS engine.  All TTS drivers must implement this interface.
 * @author sjai013
 *
 */

public interface GenericTTSConnector {
	
	public void synthesiseText(String text);
	public byte[] synthesiseText(HashMap<String, Object> params);
	

}
