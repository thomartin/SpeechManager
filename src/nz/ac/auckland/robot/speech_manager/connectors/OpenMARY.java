package nz.ac.auckland.robot.speech_manager.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *  
 * @author sjai013
 * 
 * OpenMARY driver for healthcare robot.
 * Accepted parameters are:
 * 	text - text to synthesise
 * 	voice - voice to use
 *  
 *  
 */
public class OpenMARY implements GenericTTSConnector {
	private final int maryPort = 59125;
	private HashMap<String, String> query = new HashMap<String, String>();
	
	@Override
	public void synthesiseText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] synthesiseText(HashMap<String, Object> params) {
		
		URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost("localhost").setPort(maryPort).setPath("/process");
		setDefaultParameters();
		
		query.put("INPUT_TEXT", (String) params.get("text"));
		query.put("VOICE", (String) params.get("voice"));
		
		writeParameters(uriBuilder);
			
		try {
			URI u = uriBuilder.build();
			HttpGet httpget = new HttpGet(u);
			System.out.println("Executing request " + httpget.getRequestLine());
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = null;
			InputStream istream = null;
			byte[] audioData = null;
			
			try {
				response = httpClient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					istream = entity.getContent();
					audioData = IOUtils.toByteArray(istream);
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				try {
					response.close();
					return audioData;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		

	}
	
	/**
	 * Sets default parameters which are unlikely to change (these are added first, so they can be overwritten by SynthesiseText)
	 * 
	 */
	private void setDefaultParameters()
	{
		
		query.put("INPUT_TYPE", "TEXT");
		query.put("OUTPUT_TYPE", "AUDIO");
		query.put("LOCALE", "en_NZ");
		query.put("AUDIO", "WAVE_FILE");
		
	}
	
	/**
	 * Write parameters to URIBuilder object
	 * @param uriBuilder uriBuilder instance
	 */
	private void writeParameters(URIBuilder uriBuilder)
	{
		for (String key: query.keySet())
		{
			uriBuilder.addParameter(key, query.get(key));
		}
						
	}

}
