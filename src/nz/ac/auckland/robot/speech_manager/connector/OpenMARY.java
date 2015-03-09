package nz.ac.auckland.robot.speech_manager.connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 *  
 * @author sjai013
 * 
 * OpenMARY driver for healthcare robot.
 * Accepted parameters are:
 * 	text - text to synthesise
 * 	voice - voice to use
 * 	emotion - emotion type (may be one of "happy", "neutral", "sad")
 *  emotion_level - integer indicating level of emotion
 *  
 *  
 */
public class OpenMARY implements GenericConnector {
	private final int maryPort = 59125;
	private HashMap<String, String> query = new HashMap<String, String>();
	
	@Override
	public void SynthesiseText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynthesiseText(HashMap<String, Object> params) {
		
		URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost("localhost").setPort(maryPort).setPath("/process");
		SetDefaultParameters();
		
		query.put("INPUT_TEXT", (String) params.get("text"));
		query.put("VOICE", (String) params.get("voice"));
		
		WriteParameters(uriBuilder);
		
		//http://localhost:59125/process?INPUT_TEXT=${!utt_name_var}&INPUT_TYPE=TEXT&OUTPUT_TYPE=AUDIO&LOCALE=en_NZ&AUDIO=WAVE_FILE&VOICE=$voice
			
		try {
			URI u = uriBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	/**
	 * Sets default parameters which are unlikely to change (these are added first, so they can be overwritten by SynthesiseText)
	 * 
	 */
	private void SetDefaultParameters()
	{
		
		query.put("INPUT_TYPE", "TEXT");
		query.put("OUTPUT_TYPE", "AUDIO");
		query.put("LOCALE", "en_NZ");
		query.put("AUDIO", "WAV_FILE");
		
	}
	
	/**
	 * Write parameters to URIBuilder object
	 * @param uriBuilder uriBuilder instance
	 */
	private void WriteParameters(URIBuilder uriBuilder)
	{
		for (String key: query.keySet())
		{
			uriBuilder.addParameter(key, query.get(key));
		}
						
	}

}
