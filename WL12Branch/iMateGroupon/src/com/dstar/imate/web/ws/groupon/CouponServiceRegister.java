package com.dstar.imate.web.ws.groupon;

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

import com.dstar.imate.remote.CouponManager;
import com.dstar.imate.remote.facade.CouponManagerFacade;
import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;

@WebServlet(urlPatterns="/GrouponServiceRegister" ,loadOnStartup=1 )
public class CouponServiceRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(CouponServiceRegister.class.getName());
	private CouponService app;

	@EJB
	private CouponManager couponManager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.app = new CouponService(CouponManagerFacade.getInstance(couponManager));
		System.out.println("Registoring ws app :"+this.app);
		//GF 
		WebSocketEngine.getEngine().register(app);
		
		//WL & JBOSS
		/*try {
			StandaloneWebSocketServer.runServer(this.app);
		} catch (IOException e) {
			new ServletException(e);
		}*/
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("<html><head></head><body>Service Registration done.</body></html>");
		resp.getWriter().flush();
	}

	@Override
	public void destroy() {
		WebSocketEngine.getEngine().unregister(app);
		//@TODO one servelet detroy will stop the whole WS server, only unregistration should suffice.
		StandaloneWebSocketServer.stopServer();
	}

}
