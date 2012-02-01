package com.dstar.imate.web.ws.base;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class StandaloneWebSocketServer {
	private static HttpServer server = null;
	public static boolean alreadyStarted = false;
	
	public static void runServer(WebSocketApplication... apps){
		runServer(null, -1, apps);
	}

	public static void runServer(String basePath, int port, WebSocketApplication... apps) {
		if (!alreadyStarted) {
			if (basePath == null) {
				basePath = "."; // "." Does Not Matter as we don't want to serve HTTP content over this port
			}
			if (port < 0) {
				port = 8080;
			}
			// create a Grizzly HttpServer to server static resources from 'basePath', on PORT.
			server = HttpServer.createSimpleServer(basePath, port);
			// Register the WebSockets add on with the HttpServer
			server.getListener("grizzly").registerAddOn(new WebSocketAddOn());
			try{
				server.start();
				alreadyStarted = true;
			}catch (Exception e) {
				System.out.println("WARN : NOT able to start WS Server on port " + port + ". Ignore for GlassFish Server" + e.getMessage());	
			}
			System.out.println("WS Server running on port " + port);			
		} else {
			System.out.println("WS Server already running on port " + port);
		}

		// register the application
		for (WebSocketApplication app : apps) {
			WebSocketEngine.getEngine().register(app);
		}
	}

	public static void stopServer() {
		if (alreadyStarted) {
			server.stop();
			alreadyStarted=false;
		} else {
			System.out.println("WS Server already stopped.");
		}
	}
}
