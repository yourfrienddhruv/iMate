/**
 * 
 */
package com.dstar.imate.web.ws.relationship;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.web.ws.base.JsonWebSocket;

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
