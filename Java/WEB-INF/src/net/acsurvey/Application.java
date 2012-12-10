package net.acsurvey;

//log4j classes
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//Red5 classes
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;

public class Application extends ApplicationAdapter {
	/**Variable used for generating the log*/
	private static final Log log = LogFactory.getLog(Application.class);

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		log.info("New connection attempt from " + conn.getRemoteAddress());
		// Insure that the listeners are properly attached.
		return super.roomConnect(conn, params);
	}

	/**
	 * Delegate method which logs connection/client/user disconnections.
	 * 
	 * @param conn
	 */
	@Override
	public void roomDisconnect(IConnection conn) {
		log.info("Connection closed by " + conn.getRemoteAddress());
		// Call the super class to insure that all listeners are properly dismissed.
		super.roomDisconnect(conn);
	}
}
