package nz.ac.auckland.robot.speech_manager.servlets;

@SuppressWarnings("serial")
public class ExceptionHttp extends Exception {
	
	private final int errorCode;
	
	public ExceptionHttp(String message, int errorCode)
	{
		super(message);
		this.errorCode = errorCode;
	}
	
	public int GetErrorCode()
	{
		return errorCode;
	}

}
