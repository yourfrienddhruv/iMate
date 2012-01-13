package com.dstar.imate.web.ws.groupon;

import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.data.IData;
import com.dstar.imate.entity.Coupon;
import com.dstar.imate.remote.facade.CouponManagerFacade;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.ws.base.JsonWebSocketApplication;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public class CouponService extends JsonWebSocketApplication<SecureServiceSession> {
	public static final String OP_SHARE = "share";
	public static final String OP_SEARCH_LATEST = "searchLatest";
	public static final String OP_GET_TAGS = "getTags";
	
	@Override
	public SecureServiceSession createSocket(ProtocolHandler handler, WebSocketListener... listeners) {
		return new SecureServiceSession(handler, listeners);
	}

	@Override
	public String getApplicationBaseURI() {
		return "/GrouponService";
	}
	
	@Override
	public JsonResponse<? extends IData> processRequest(JsonRequest req) {
		if (OP_SHARE.equals(req.getType())) {
			JsonResponse<Coupon> resp = new JsonResponse<Coupon>();
			resp.setData(doShare(req.getData(Coupon.class)));
			return resp;
		}else if(OP_SEARCH_LATEST.equals(req.getType())){
			return positive("Ok!");
		}else if(OP_GET_TAGS.equals(req.getType())){
			return positive("Ok!");
		} else {
			return negative(req.getData().getMessageKey(), "Unsupported operation.");
		}
	}
	
	private ResponseData<Coupon> doShare(Coupon newCoupon) {
		return couponManager.saveCoupon(newCoupon);
	}

	private CouponManagerFacade couponManager;
	public CouponService(CouponManagerFacade manager){
		super();
		if(manager==null){
			throw new IllegalArgumentException("Can't inilitize Service "+this.getClass()+" due to "+CouponManagerFacade.class+" referece is Null");
		}else{
			this.couponManager=manager;
		}
	}

	@Override
	public JsonResponse<? extends IData> processProtocol(SecureServiceSession ws, JsonRequest req) {
		return negative("unsupported.protocol.request",null);
	}
	
}
