/**
 * 
 */
package com.dstar.imate.web.ws.base;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;


/**
 * @author Administrator
 *
 */
public class SecureServiceSession extends JsonWebSocket {

	/**
	 * @param handler
	 * @param listeners
	 */
	public SecureServiceSession(ProtocolHandler handler, WebSocketListener[] listeners) {
		super(handler, listeners);
	}

}
