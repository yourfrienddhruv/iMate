package com.dstar.imate.web.ws.groupon;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.grizzly.websockets.WebSocketEngine;

import com.dstar.imate.data.utils.CassandraService;
import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;

@WebServlet(urlPatterns="/GrouponServiceRegister" ,loadOnStartup=1 )
public class CouponServiceRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(CouponServiceRegister.class.getName());
	
	@Resource
	private CouponService app;

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Registoring ws app :"+this.app);
		/*boolean glassfishServer=false;
		//boolean glassfishServer=System.getProperty("JBOSS_HOME")!=null;
		if(!glassfishServer){
			//Hack to auto-start WebSocketServer Grizzly on NON GF servers on 8080 so disable the HTTP of server!
			StandaloneWebSocketServer.runServer(".",9090,app);
		}else{
			System.out.println("### Server is Glassfish so using inbuilt WS Engine ### ");
		}
		*/
		try{// start embeded cassandra for openshift on port defined in cassandra.ymal
			CassandraService.setupKeyspace("/tmp/cassandra","localhost",59076);
		}catch(Exception e){
			throw new ServletException(e);
		}
		
		WebSocketEngine.getEngine().register(app); //Most IMP to register the Application Instance 
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			StandaloneWebSocketServer.runServer("/tmp/cassandra",Integer.parseInt(req.getParameter("port")),app);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StandaloneWebSocketServer.stopServer();
		}
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
