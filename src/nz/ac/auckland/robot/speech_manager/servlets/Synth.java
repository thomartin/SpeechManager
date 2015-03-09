package nz.ac.auckland.robot.speech_manager.servlets;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.auckland.robot.speech_manager.connector.GenericConnector;
import nz.ac.auckland.robot.speech_manager.connector.OpenMARY;

import org.apache.http.client.utils.URIBuilder;


@SuppressWarnings("serial")
public class Synth extends HttpServlet 
{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		//final String pathBase = "/home/sjai013/Voice-Adaptation/";
		
		try
		{
			URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost("localhost").setPort(9001).setPath("/SpeechManager/synth");
			uriBuilder.addParameter("text", "Hello, I am Charlie");
			URI u = uriBuilder.build();
			
			System.out.println(u);
			
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
			c.SynthesiseText(params);
			
			req.setAttribute("newJobID", String.format("%04X", text));

			req.getRequestDispatcher("/newJobID.jsp").forward(req,resp);

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

	/**
	 * Breaks full name into Initials, First Name(s), Last Name
	 * @param name Full name in format [First1 First2 Firstn Last].  I.e. Gaius Julius Caesar
	 * @return String[0] = Initial, String[1] = First Name(s), String[2] = Last Name (i.e. GC, Gaius Julius, Caesar)
	 */
	private String[] ParseName(String name)
	{
		String[] returnNames = {"","",""};
		String[] names = name.split(" ");

		//Build first name - concatenation of all elements except the last element
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < names.length-1; i++) 
		{
			sb.append(names[i] + " ");
		}

		returnNames[1] = sb.toString().trim();


		returnNames[2] = names[names.length-1];


		//Build Initials (first letter of first name and first letter of last name)
		returnNames[0] = returnNames[1].substring(0, 1) + returnNames[2].substring(0, 1);

		return returnNames;

	}


}
