package com.dstar.imate.web.ws.groupon;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;

import com.dstar.imate.business.BaseManager;
import com.dstar.imate.business.CouponManager;
import com.dstar.imate.data.Coupon;
import com.dstar.imate.data.StringData;
import com.dstar.imate.web.ws.base.JsonWebSocketRESTApplication;
import com.dstar.imate.web.ws.base.Operation;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.dstar.imate.web.ws.base.data.JsonResponse;

@Path("/Coupon")
@RequestScoped
@ManagedBean
public class CouponService extends JsonWebSocketRESTApplication {

	@EJB
	private CouponManager couponManager;

	public CouponService() {
		super(CouponService.class);
	}
	
	@Operation("getTags")
	public JsonResponse<StringData> getTags(JsonRequest req) {
		return positive("Ok!");
	}

	@Operation("searchLatest")
	public JsonResponse<Coupon> searchLatestByBrand(JsonRequest req) {
		System.out.println("searchLatestByBrand:"+req.getData(Coupon.class));
		JsonResponse<Coupon> resp = new JsonResponse<Coupon>();
		Coupon search = new Coupon();
		search.setBrand(req.getData(Coupon.class).getBrand());
		resp.setData(couponManager.searchLatest(search));
		return resp;
	}

	@Operation("share")
	public JsonResponse<Coupon> doShare(JsonRequest req) {
		System.out.println(req.getData());
		JsonResponse<Coupon> resp = new JsonResponse<Coupon>();
		resp.setData(couponManager.saveCoupon(req.getData(Coupon.class)));
		return resp;
	}

	@Override
	public void inject(BaseManager... managers) { // with manual injection from Servlet or @Test for webSocket
		for(BaseManager m:managers){
			if(m instanceof CouponManager)this.couponManager = (CouponManager) m;
		}
	}
	
//	private ResponseData<Coupon> dummySearchResponse(){
//		Coupon[] items = new Coupon[2];
//		items[0] = new Coupon();
//		items[0].setDiscount(10.4d);
//		items[0].setBrand("Super Brand");
//		items[1] = new Coupon();
//		items[1].setDiscount(40.5d);
//		items[1].setBrand("Super Duper Brand");
//		ResponseData<Coupon> result = ResponseData.positive(null, items);
//		return result;
//	}
}
