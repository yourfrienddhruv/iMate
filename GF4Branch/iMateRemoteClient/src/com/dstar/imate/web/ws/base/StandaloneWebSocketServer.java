package com.dstar.imate.web.ws.base;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class StandaloneWebSocketServer {
    public static void main(String[] args) throws Exception {
    	runServer(args[0],Integer.parseInt(args[1]));
    }
    
    public static void runServer(String basePath,int port,WebSocketApplication... apps) throws Exception{
    	if(basePath==null){
    		basePath="C:\\Innovate\\eclipseWorkspace\\iMateGIT\\HTML5Branch\\iMatePOH5\\WebContent";
    	}
    	if(port <1){
    		port=9090;
    	}
    	
    	// create a Grizzly HttpServer to server static resources from 'webapp', on PORT.
        final HttpServer server = HttpServer.createSimpleServer(basePath, port);

        // Register the WebSockets add on with the HttpServer
        server.getListener("grizzly").registerAddOn(new WebSocketAddOn());

        // register the application
        for(WebSocketApplication app : apps){
        	WebSocketEngine.getEngine().register(app);
        }
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } finally {
            // stop the server
            server.stop();
        }
    }
}
