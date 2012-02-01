package com.dstar.imate.web.ws.relationship;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.business.RelationshipManager;
import com.dstar.imate.data.IData;
import com.dstar.imate.data.StringData;
import com.dstar.imate.data.UserProfile;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.ws.base.JsonWebSocketApplication;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public class RelationshipService extends JsonWebSocketApplication<SecureServiceSession> {
	public static final String OP_LOGIN = "login";
	public static final String OP_SENDMESSAGE = "sendMessage";

	@Override
	public SecureServiceSession createSocket(ProtocolHandler handler, WebSocketListener... listeners) {
		return new SecureServiceSession(handler, listeners);
	}

	@Override
	public String getApplicationBaseURI() {
		return "/RelationshipService";
	}

	@Override
	public JsonResponse<? extends IData> processRequest(JsonRequest req) {
		if (OP_LOGIN.equals(req.getType())) {
			JsonResponse<UserProfile> resp = new JsonResponse<UserProfile>();
			resp.setData(doLogin(req.getData().getMessageKey()));
			return resp;
		} else if (OP_SENDMESSAGE.equals(req.getType())) {
			JsonResponse<StringData> resp = new JsonResponse<StringData>();
			resp.setData(ResponseData.positive(new StringData("msg.recorded")));
			return resp;
		} else {
			JsonResponse<StringData> resp = new JsonResponse<StringData>();
			resp.setData(ResponseData.negative(req.getData().getMessageKey(), new StringData("Unsupported operation requested.")));
			return resp;
		}
	}

	private ResponseData<UserProfile> doLogin(String username) {
		return relationshipManager.fetchProfile(username);
	}

	// =============== startup config methods =========================//

	private RelationshipManager relationshipManager;

	public RelationshipService(RelationshipManager manager) {
		super();
		if (manager == null) {
			throw new IllegalArgumentException("Can't inilitize Service " + this.getClass() + " due to " + RelationshipManager.class
					+ " referece is Null");
		} else {
			this.relationshipManager = manager;
		}
	}

	@Override
	public JsonResponse<? extends IData> processProtocol(SecureServiceSession ws, JsonRequest req) {
		return negative("unsupported.protocol.request", null);
	}

}
