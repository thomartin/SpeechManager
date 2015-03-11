package nz.ac.auckland.robot.speech_manager.servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.auckland.robot.speech_manager.connectors.GenericConnector;
import nz.ac.auckland.robot.speech_manager.connectors.OpenMARY;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.commons.io.IOUtils;


@SuppressWarnings("serial")
public class Synth extends HttpServlet 
{

	/**
	 * Load config properties 
	 */
	public void init(ServletConfig config) throws ServletException
	{
		
		System.out.println("Servlet initialised");
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		//final String pathBase = "/home/sjai013/Voice-Adaptation/";
		
		try
		{
			URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost("localhost").setPort(9001).setPath("/SpeechManager/synth");
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
			
			GenericConnector c = new OpenMARY();
			
			byte[] audio = c.synthesiseText(params);
			
			// Now we need to decide whether to save wav file to a temp location and play wav file, or synthesise directly to mixer.
			// Or we could pass wav file to a parent AudioManager, and use SpeechManager purely for creating dialogue (i.e. not playing).
			
			
			
			resp.setContentType("audio/wav");
			resp.setContentLength(audio.length);
			
			//For the time being, we just return the wav file as a stream, and let the caller handle playing.
			IOUtils.copy(new ByteArrayInputStream(audio), resp.getOutputStream());	
			
		
			
			//req.setAttribute("newJobID", String.format("%04X", text));
			
			 

			//req.getRequestDispatcher("/newJobID.jsp").forward(req,resp);
			

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
