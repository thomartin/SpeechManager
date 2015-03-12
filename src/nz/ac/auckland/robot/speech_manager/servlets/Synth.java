package nz.ac.auckland.robot.speech_manager.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.auckland.robot.speech_manager.connectors.GenericTTSConnector;
import org.apache.commons.io.IOUtils;


/**
 * This servlet is used to synthesise arbitary text. <br>
 * The standard address for this is http://.../SpeechManager/synth <p>
 * The request via a GET command by the client, and the acceptable GET parameters are: <br>
 * 		text - text to synthesise <br>
 * 		emotion - emotion to use (acceptable parameters are "happy", "sad", "neutral" <br>
 * 		emotion_level - strength of emotion (integer) <br>
 * 		driver - driver to use.  Currently implemented drivers are: "OpenMARY" and "Festival".  Not all drivers will implement all parameters, 
 * 				but will implement the 'text' parameter as a minumum.  Check documentation for each individual driver to determine whether a parameter is implemented. <br>
 * 		voice - voice name.  If the specified voice does not exist for the selected driver, then the default voice is used. <br>
 * 
 * @author sjai013
 *
 */
public class Synth extends HttpServlet 
{
	
	private static final long serialVersionUID = 209537810907487689L; //Auto-generated serialVersionUID

	/**
	 * Load config properties 
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		
		System.out.println("Servlet initialised");
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		try
		{
			final String dir = System.getProperty("user.dir");
	        System.out.println("current dir = " + dir);
	        
			String text = req.getParameter("text");
			String emotion = req.getParameter("emotion");
			String emotionLevel = req.getParameter("emotion_level");
			String driver = req.getParameter("driver");
			String voice = req.getParameter("voice");
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("text", text);
			params.put("emotion", emotion);
			params.put("emotion_level", emotionLevel);
			params.put("driver", driver);
			params.put("voice", voice);
			
			String connectorNamespace = GenericTTSConnector.class.getPackage().getName();
			GenericTTSConnector c = (GenericTTSConnector) Class.forName(connectorNamespace + "." + driver).newInstance();
			
			byte[] audio = c.synthesiseText(params);
			
			
			
			// Now we need to decide whether to save wav file to a temp location and play wav file, or synthesise directly to mixer.
			// Or we could pass wav file to a parent AudioManager, and use SpeechManager purely for creating dialogue (i.e. not playing).
			
			
			
			resp.setContentType("audio/wav");
			resp.setContentLength(audio.length);
			
			//For the time being, we just return the wav file as a stream, and let the caller handle playing.
			IOUtils.copy(new ByteArrayInputStream(audio), resp.getOutputStream());	
	
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			resp.sendError(400, e.getMessage());

		}




	}

}
