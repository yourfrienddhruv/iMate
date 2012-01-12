package com.dstar.imate.web.ws.base;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class StandaloneWebSocketServer {
	private static HttpServer server = null;
	private static boolean alreadyStarted = false;

	public static void main(String[] args) throws Exception {
		runServer(args[0], Integer.parseInt(args[1]));
	}

	public static void runServer(WebSocketApplication... apps) throws IOException {
		runServer(null, -1, apps);
	}

	public static void runServer(String basePath, int port, WebSocketApplication... apps) throws IOException {
		if (!alreadyStarted) {
			if (basePath == null) {
				basePath = "."; // Does Not Matter as we don't want to serve HTTP content over this port
			}
			if (port < 1) {
				port = 9090;
			}
			// create a Grizzly HttpServer to server static resources from 'webapp', on PORT.
			server = HttpServer.createSimpleServer(basePath, port);

			// Register the WebSockets add on with the HttpServer
			server.getListener("grizzly").registerAddOn(new WebSocketAddOn());

			alreadyStarted = true;
			server.start();
			System.out.println("WS Server starting on port " + port);			
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
		} else {
			System.out.println("WS Server already stopped.");
		}
	}
}
