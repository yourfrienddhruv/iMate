package com.dstar.imate.web.ws.relationship;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.grizzly.websockets.WebSocketEngine;

import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.remote.data.Group;
import com.dstar.imate.remote.data.Relationship;
import com.dstar.imate.remote.data.UserProfile;
import com.dstar.imate.remote.facade.RelationshipManagerFacade;

/**
 * VERY IMP : ENABLE Glassfish WEBSOCKET support. Required Grizzly 2.1.7+ which is only available after GlassFish 4.x + versions. 
 *	listner-1 :  ws://host:8080/RelationshipServiceRegister
 *  listner-2 : wss://host:8181/RelationshipServiceRegister
 *  
 *	======One time configuration required for all GlassFish Domains.========
 *
 * Start the Server:
 * 	asadmin start-domain
 * Enable websockets support:
 *		asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.websockets-support-enabled=true
 *		asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-2.http.websockets-support-enabled=true
 * Increase keep-alive timeouts: 
 * 		asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.timeout-seconds=900
 * Stop the Server:
 * 		asadmin stop-domain
 * Start again : 
 * 		asadmin start-domain
 * Verify :
 * 		asadmin get configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.websockets-support-enabled
 * 		asadmin get configs.config.server-config.network-config.protocols.protocol.http-listener-2.http.websockets-support-enabled
 *
 *
 */

@WebServlet(urlPatterns="/RelationshipServiceRegister" ,loadOnStartup=1 )
public class RelationshipServiceRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(WebSocketEngine.WEBSOCKET);
	private RelationshipService app;

	@EJB
	private RelationshipManager<Relationship, UserProfile, Group> relationshipManager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.app = new RelationshipService(RelationshipManagerFacade.getInstance(relationshipManager));
		System.out.println("Registoring this.app"+this.app);
		WebSocketEngine.getEngine().register(app);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("<html><head></head><body>RelationshipServiceRegister done.</body></html>");
		resp.getWriter().flush();
	}

	@Override
	public void destroy() {
		WebSocketEngine.getEngine().unregister(app);
	}

}
