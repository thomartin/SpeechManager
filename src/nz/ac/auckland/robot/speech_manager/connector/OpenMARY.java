package nz.ac.auckland.robot.speech_manager.connector;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

public class OpenMARY implements GenericConnector {

	@Override
	public void SynthesiseText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynthesiseText(List<NameValuePair> params) {
		
		URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost("localhost").setPort(9001).setPath("/SpeechManager/synth");
		uriBuilder.addParameter("text", "Hello, I am Charlie");

		uriBuilder.addParameters(params);
		
		

	}

}
