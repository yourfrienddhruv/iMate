package com.dstar.imate.web.ws.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Path;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.business.BaseManager;
import com.dstar.imate.business.CouponManager;
import com.dstar.imate.data.IData;
import com.dstar.imate.data.StringData;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.json.base.gson.external.bind.ExceptionTypeAdapter;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public abstract class JsonWebSocketApplication<T extends JsonWebSocket> extends WebSocketApplication {
	private static final Logger logger = Grizzly.logger(JsonWebSocket.class);

	@Override
	public abstract T createSocket(ProtocolHandler handler, WebSocketListener... listeners);

	@Override
	public boolean isApplicationRequest(HttpRequestPacket request) {
		return getApplicationBaseURI().equals(request.getRequestURI());
	}

	//Allow @EJB manual injection in-case of WebSocket &  @TEST mode 
	public abstract void inject(BaseManager... managers) ;
	
	@Override
	public void onMessage(WebSocket websocket, String data) {
		logger.log(Level.INFO, "==>>:{0}>>{1}", new Object[] { getApplicationBaseURI(), data });
		JsonRequest jsonRequest = JsonRequest.fromJson(data);
		@SuppressWarnings("unchecked")
		T ws = ((T) websocket);
		try {
			doSendBack(ws, jsonRequest, isProtocolRequest(jsonRequest) ? processProtocol(ws, jsonRequest) : processRequest(jsonRequest));
		} catch (Exception e) {
			JsonResponse<StringData> resp = new JsonResponse<StringData>();
			ResponseData<StringData> ex = ResponseData.failed(e);
			resp.setData(ex);
			doSendBack(ws, jsonRequest, resp);
		}
	}

	private void doSendBack(T ws, JsonRequest req, JsonResponse<? extends IData> resp) {
		resp.setType(req.getType());// to callback the same type
		ws.sendResponse(resp);
	}

	private boolean isProtocolRequest(JsonRequest req) {
		return req.getType() != null && req.getType().startsWith("_");
	}

	public abstract JsonResponse<? extends IData> processProtocol(T ws, JsonRequest req);

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

	private String baseURI;
	private HashMap<String, Method> mapping;

	public JsonWebSocketApplication(Class<? extends JsonWebSocketApplication<T>> clazz) {// for REST
																							// service
		super();
		Path uri = clazz.getAnnotation(Path.class);
		if (uri == null) {
			throw new IllegalArgumentException("@Path Annotation missing on " + clazz);
		} else {
			this.baseURI = uri.value();
		}
		this.mapping = new HashMap<String, Method>();
		for (Method m : clazz.getMethods()) {
			Operation op = m.getAnnotation(Operation.class);
			if (op != null) {// If @Operation is defined on the method
				if (op.value() == null || op.value().equals("")) {
					throw new IllegalArgumentException("@Operation(value) Annotation must have non blank value for " + m + " of " + clazz);
				} else if (this.mapping.containsKey(op.value())) {
					throw new IllegalArgumentException("Duplicate @Operation(" + op.value() + ") Annotation found. " + m
							+ " is trying to use same value of Operation which is being already used by " + this.mapping.get(op.value()) + " of " + clazz);
				} else {
					this.mapping.put(op.value(), m);
				}
			}
		}
	}

	public String getApplicationBaseURI() {
		return baseURI;
	}

	@SuppressWarnings("unchecked")
	public JsonResponse<? extends IData> processRequest(JsonRequest req) {
		try {
			if (req == null) {
				return operationUnsupported("Invalid Request format.");
			} else if (req.getType() == null) {
				return negative("error", "Invalid Request type.");
			} else {
				Method m = mapping.get(req.getType());
				if (m == null) {// Not found
					return operationUnsupported(req.getData().getMessageKey());
				} else {
					return (JsonResponse<? extends IData>) m.invoke(this, req);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return negative("error", e);
		}
	}

	// ========== UTILITIES ======== //
	protected static final JsonResponse<StringData> positive(String msg) {
		JsonResponse<StringData> resp = new JsonResponse<StringData>();
		resp.setData(ResponseData.positive(new StringData(msg)));
		return resp;
	}

	private static final JsonResponse<StringData> negative(String key, String msg) {
		JsonResponse<StringData> resp = new JsonResponse<StringData>();
		resp.setData(ResponseData.negative(key, new StringData(msg)));
		return resp;
	}

	protected static final JsonResponse<StringData> negative(String key, Exception e) {
		return negative(key,ExceptionTypeAdapter.getDeepestCause(e));
	}
	protected static final JsonResponse<StringData> operationUnsupported(String key) {
		return negative(key, "Unsupported operation requested.");
	}
}
