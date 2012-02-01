package com.dstar.imate.web.ws.groupon;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.dstar.imate.business.CouponManager;
import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;

public class CouponServiceTest extends CouponService {
	// =============== test support methods =========================//
	public static void main(String[] args) throws Exception {
		CouponServiceTest test= new CouponServiceTest();
		test.inject(doManualInjection());
		StandaloneWebSocketServer.runServer(test);
	}

	private static CouponManager doManualInjection() throws NamingException {
		Properties env = new Properties();
		// env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
		// env.put("org.omg.CORBA.ORBInitialHost", "dhrgohil.in.ibm.com");
		// env.put("org.omg.CORBA.ORBInitialHost", "115.118.181.82");
		// env.put("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(env);
		return (CouponManager) ctx.lookup("java:global/iMateGroupon/RelationshipManager");
	}
}
