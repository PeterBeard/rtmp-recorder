package net.acsurvey;

import java.util.Date;
import java.text.SimpleDateFormat;

//log4j classes
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//Red5 classes
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.stream.ClientBroadcastStream;

// Manages streamed audio from clients
public class StreamManager {
	// Handle logs
	private static final Log log = LogFactory.getLog(Application.class);
	// Handled connections
	private Application app;
	
	// Start recording -- open a file and name it after the user's IP and the current timestamp
	public void startRecording()
	{
		IConnection clientConnection = Red5.getConnectionLocal(); // Get the connection
		String clientIP = clientConnection.getRemoteAddress(); // Get the user's IP
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss"); // Create a date formatter
		String streamFileName = clientIP + "." + format.format(new Date()); // Format the current date
		IBroadcastStream currentStream = app.getBroadcastStream(clientConnection.getScope(), "hostStream"); // Get the current stream
		log.info("Recording stream from client " + clientIP); // Make a note in the log
		// Try to save the stream to disk
		try
		{
			currentStream.saveAs(streamFileName, false);
			log.info("Stream is being saved to " + streamFileName);
		} catch(Exception e) {
			log.error("Unable to save stream."); // There's not really much else I care to do about this
		}
	}
	
	// Stop recording -- close the file and the stream
	public void stopRecording()
	{
		log.info("Recording stopped.");
		IConnection clientConnection = Red5.getConnectionLocal(); // Get the connection
		ClientBroadcastStream clientStream = (ClientBroadcastStream) app.getBroadcastStream(clientConnection.getScope(), "hostStream"); // Get a handle to the stream
		clientStream.stopRecording(); // Stop recording the stream
	}
	
	// Need this for Spring to work right :/
	public void setApplication(Application app)
	{
		this.app = app;
	}
}
