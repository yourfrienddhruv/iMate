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

import com.dstar.imate.data.IData;
import com.dstar.imate.data.StringData;
import com.dstar.imate.transport.ResponseData;
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

	@Override
	public void onMessage(WebSocket websocket, String data) {
		logger.log(Level.INFO, "==>>:{0}>>{1}", new Object[] { getApplicationBaseURI(), data });
		JsonRequest jsonRequest = JsonRequest.fromJson(data);
		@SuppressWarnings("unchecked")
		T ws = ((T) websocket);
		try {
			doSendBack(ws,jsonRequest,
					isProtocolRequest(jsonRequest) ? processProtocol(ws, jsonRequest)
					: processRequest(jsonRequest));
		} catch (Exception e) {
			JsonResponse<StringData> resp=new JsonResponse<StringData>();
			ResponseData<StringData> ex = ResponseData.failed(e);
			resp.setData(ex);
			doSendBack(ws,jsonRequest,resp);
		}
	}
	private void doSendBack(T ws,JsonRequest req,JsonResponse<? extends IData> resp){
		resp.setType(req.getType());// to callback the same type
		ws.sendResponse(resp);
	}

	private boolean isProtocolRequest(JsonRequest req) {
		return req.getType() != null && req.getType().startsWith("_");
	}

	public abstract JsonResponse<? extends IData> processProtocol(T ws, JsonRequest req);

	public abstract JsonResponse<? extends IData> processRequest(JsonRequest req);

	@SuppressWarnings("unchecked")
	@Override
	public void onClose(WebSocket websocket, DataFrame frame) {
		processClose((T) websocket, frame);
	}

	public void processClose(T websocket, DataFrame frame) {
		logger.log(Level.INFO, "==!> Closed session on :{0} due to: {1}", new Object[] { getApplicationBaseURI(), frame });
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

	// ========== UTILITIES ======== //
	protected static final JsonResponse<StringData> positive(String msg) {
		JsonResponse<StringData> resp = new JsonResponse<StringData>();
		resp.setData(ResponseData.positive(new StringData(msg)));
		return resp;
	}

	protected static final JsonResponse<StringData> negative(String key, String msg) {
		JsonResponse<StringData> resp = new JsonResponse<StringData>();
		resp.setData(ResponseData.negative(key, new StringData("Unsupported operation requested.")));
		return resp;
	}
}
