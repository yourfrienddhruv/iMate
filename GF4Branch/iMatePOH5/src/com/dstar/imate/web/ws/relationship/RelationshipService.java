package com.dstar.imate.web.ws.relationship;

import java.rmi.RMISecurityManager;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.data.StringData;
import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.remote.data.Group;
import com.dstar.imate.remote.data.Relationship;
import com.dstar.imate.remote.data.UserProfile;
import com.dstar.imate.remote.facade.RelationshipManagerFacade;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.ws.base.JsonWebSocketApplication;
import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;
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
	public JsonResponse processRequest(String reqJson) {
		//@TODO JsonRequest req
		 JsonRequest req=null;
		if (OP_LOGIN.equals(req.getType())) {
			JsonResponse<UserProfile> resp = new JsonResponse<UserProfile>();
			 resp.setData(doLogin(req.getData().getMessageKey()));
			 return resp;
		}else if(OP_SENDMESSAGE.equals(req.getType())){
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
	
	//=============== startup config methods =========================//

	private RelationshipManagerFacade relationshipManager;
	public RelationshipService(RelationshipManagerFacade manager){
		super();
		if(manager==null){
			this.relationshipManager=doManualInjection();
		}else{
			this.relationshipManager=manager;
		}
	}
	
	//=============== test support methods =========================//
	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.main","");
		System.setProperty("java.security.policy","AllPermission.policy");
		System.setSecurityManager(new RMISecurityManager());
		
		StandaloneWebSocketServer.runServer(new RelationshipService(null));
	}
	
	private static RelationshipManagerFacade doManualInjection() {
		try {
			Properties env = new Properties();
			//env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
			//env.put("org.omg.CORBA.ORBInitialHost", "dhrgohil.in.ibm.com");
			//env.put("org.omg.CORBA.ORBInitialHost", "115.118.181.82");
			//env.put("org.omg.CORBA.ORBInitialPort", "3700");
			InitialContext ctx = new InitialContext(env);
			@SuppressWarnings("unchecked")
			RelationshipManager<Relationship, UserProfile, Group> managerRemote = (RelationshipManager<Relationship, UserProfile, Group>) ctx
					.lookup("java:global/iMateEAR/iMate/RelationshipManagerBean");
			return RelationshipManagerFacade.getInstance(managerRemote);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			// @TODO if service is not in serviceable mode then should fail, so client knows and don't send operations.
		}
	}
}
