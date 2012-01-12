package com.dstar.imate.web.ws.groupon;

import java.lang.reflect.Type;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.data.IData;
import com.dstar.imate.remote.data.Coupon;
import com.dstar.imate.remote.facade.CouponManagerFacade;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.json.base.gson.Json;
import com.dstar.imate.web.ws.base.JsonWebSocketApplication;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;
import com.google.gson.reflect.TypeToken;

public class CouponService extends JsonWebSocketApplication<SecureServiceSession> {
	public static final String OP_SHARE = "share";
	public static final String OP_SEARCH_LATEST = "searchlatest";
	
	@Override
	public SecureServiceSession createSocket(ProtocolHandler handler, WebSocketListener... listeners) {
		return new SecureServiceSession(handler, listeners);
	}

	@Override
	public String getApplicationBaseURI() {
		return "/GrouponService";
	}

	public static final Type TYPE_JsonRequest_Coupon = new TypeToken<JsonRequest<Coupon>>() {}.getType();
	
	@Override
	public JsonResponse<? extends IData> processRequest(String jsonRequestData) {
		//if (OP_SHARE.equals(req.getType())) {
			JsonRequest<Coupon> req = Json.from(jsonRequestData,TYPE_JsonRequest_Coupon);
			JsonResponse<Coupon> resp = new JsonResponse<Coupon>();
			resp.setData(doShare(req.getData().getData()));
			return resp;
		/*}else if(OP_SEARCH_LATEST.equals(req.getType())){
			JsonResponse<StringData> resp = new JsonResponse<StringData>();
			resp.setData(ResponseData.positive(new StringData("msg.recorded")));
			return resp;
		} else {
			JsonResponse<StringData> resp = new JsonResponse<StringData>();
			resp.setData(ResponseData.negative(req.getData().getMessageKey(), new StringData("Unsupported operation requested.")));
			return resp;
		}*/
	}

	private ResponseData<Coupon> doShare(Coupon newCoupon) {
		return couponManager.saveCoupon(newCoupon);
	}
	
	//=============== startup config methods =========================//

	private CouponManagerFacade couponManager;
	public CouponService(CouponManagerFacade manager){
		super();
		if(manager==null){
			throw new IllegalArgumentException("Can't inilitize Service "+CouponService.class+" due to manager referece is Null "+CouponManagerFacade.class);
		}else{
			this.couponManager=manager;
		}
	}
}
