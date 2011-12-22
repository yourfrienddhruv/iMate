package com.dstar.imate.web.ws.base;

import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.web.json.base.gson.Json;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public abstract class JsonWebSocketApplication<T extends JsonWebSocket> extends WebSocketApplication {
    private static final Logger logger = Grizzly.logger(JsonWebSocket.class);
	@Override
	
	public abstract T createSocket(ProtocolHandler handler, WebSocketListener... listeners);

	public abstract String getApplicationBaseURI();

	@Override
	public boolean isApplicationRequest(HttpRequestPacket request) {
		return getApplicationBaseURI().equals(request.getRequestURI());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(WebSocket websocket, String data) {
		logger.log(Level.INFO,"==>>:{0}>>{1}",new Object[]{getApplicationBaseURI(),data});
		JsonRequest req=Json.from(data, JsonRequest.class);
		logger.log(Level.INFO,"==>>:Parsed:{0}",req);
		if(isUserRequest(req)){
			JsonResponse resp=processRequest(req);
			resp.setOperation(req.getOperation());
			resp.setCallback(req.getCallback());
			((T) websocket).sendResponse(resp);
		}else{
			parseCustomProtocol((T) websocket,req);
		}
	}

	private boolean isUserRequest(JsonRequest req){
		return req.getType()==null;
	}
	
    /*public static String[] parseIncomming(String data){
    	String[] fields=data.substring(1, data.length()-2).split(",");
    	return new String[]{ fields[0].split(":")[VAL].trim(),fields[1].split(":")[VAL].trim(),fields[2].split(":")[VAL].trim()};
    }*/
    
    public void parseCustomProtocol(T websocket, JsonRequest req){
    	//for operation=="PROTOCOL" 
    	if("session_set_notificationHandler".equals(req.getOperation())){
   			websocket.setNotificationCallback(req.getCallback());
   			logger.log(Level.INFO,"==>>:PROTOCOL:setNotificationCallback:{0}",req);
    	}
    }
    
	public abstract JsonResponse processRequest(JsonRequest req);

	@SuppressWarnings("unchecked")
	@Override
	public void onClose(WebSocket websocket, DataFrame frame) {
		processClose((T) websocket, frame);
	}

	public void processClose(T websocket, DataFrame frame){
		 logger.log(Level.INFO, "==!> Closed session on :{0} due to: {1}", new Object[]{getApplicationBaseURI() ,frame});
	}

	@SuppressWarnings("unchecked")
	public Set<T> getAllConnected() {
		Set<T> connected = new TreeSet<T>();
		for (WebSocket websocket : getWebSockets()) {
			if (!websocket.isConnected()) {
				continue;
			} else {
				connected.add((T) websocket);
			}
		}
		return connected;
	}

    //private static final int NAME=0;
    //private static final int VAL=1;
}
