package com.dstar.imate.web.ws.base;

import java.net.URLDecoder;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.data.IData;
import com.dstar.imate.web.json.base.gson.Json;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public abstract class JsonWebSocketRESTApplication extends JsonWebSocketApplication<SecureServiceSession> {
	public JsonWebSocketRESTApplication(Class<? extends JsonWebSocketRESTApplication> clazz) {
		super(clazz);
	}

	@Override
	public SecureServiceSession createSocket(ProtocolHandler handler, WebSocketListener... listeners) {
		return new SecureServiceSession(handler, listeners);
	}

	@GET
	@Path("/{operation}")
	@Produces("application/json")
	public String GETDispatcher(@PathParam("operation") String type, @QueryParam("c") String callback, @QueryParam("j") String jsonPayload) {
		System.out.println(type + ":jsonPayload[original]=" + jsonPayload);
		JsonResponse<? extends IData> resp=null;
		JsonRequest req =null;
		try {
			jsonPayload = URLDecoder.decode(jsonPayload, "utf-8");
			//System.out.println("{\"type\":\""+type+"\",\"data\":"+jsonPayload+"}");
			req= JsonRequest.fromJson("{\"type\":\""+type+"\",\"data\":"+jsonPayload+"}");
			resp = processRequest(req);
		}catch (Exception e) {
			e.printStackTrace();
			resp = negative("error", e);
		}finally{
			resp.setType(req.getType());// to callback the same type
		}
		
		if (callback == null) {
			return Json.to(resp);
		} else {
			return callback + "(" + Json.to(resp) + ")"; // TO Support JSONP
		}
	}

	@POST
	@Path("/{operation}")
	@Produces("application/json")
	public String POSTDispatcher(@PathParam("operation") String type, @QueryParam("c") String callback, @FormParam("j") String jsonPayload) {
		return GETDispatcher(type, callback, jsonPayload);
	}

	@Override
	public JsonResponse<? extends IData> processProtocol(SecureServiceSession ws, JsonRequest req) {
		return operationUnsupported("unsupported.protocol.request");
	}
}
