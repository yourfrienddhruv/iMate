/**
 * 
 */
package com.dstar.imate.web.ws.relationship;

import java.rmi.RMISecurityManager;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.dstar.imate.business.RelationshipManager;
import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;

public class RelationshipServiceTest extends RelationshipService {
	public RelationshipServiceTest(RelationshipManager manager) {
		super(manager);
	}

	// =============== test support methods =========================//
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.main", "");
		System.setProperty("java.security.policy", "AllPermission.policy");
		System.setSecurityManager(new RMISecurityManager());

		StandaloneWebSocketServer.runServer(new RelationshipServiceTest(doManualInjection()));
	}

	private static RelationshipManager doManualInjection() {
		try {
			Properties env = new Properties();
			// env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
			// env.put("org.omg.CORBA.ORBInitialHost", "dhrgohil.in.ibm.com");
			// env.put("org.omg.CORBA.ORBInitialHost", "115.118.181.82");
			// env.put("org.omg.CORBA.ORBInitialPort", "3700");
			InitialContext ctx = new InitialContext(env);
			return(RelationshipManager) ctx.lookup("java:global/iMateEAR/iMate/RelationshipManager");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			// @TODO if service is not in serviceable mode then should fail, so client knows and don't send operations.
		}
	}
}
