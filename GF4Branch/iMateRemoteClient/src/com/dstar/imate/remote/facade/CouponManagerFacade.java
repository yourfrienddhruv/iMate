package com.dstar.imate.remote.facade;

import com.dstar.imate.remote.CouponManager;
import com.dstar.imate.remote.data.Coupon;
import com.dstar.imate.transport.ResponseData;

public class CouponManagerFacade extends BaseFacade<CouponManager<Coupon>> implements CouponManager<Coupon>{
	//@EJB(lookup="java:global/iMateEAR/iMate/CouponManagerBean")
	private CouponManager<Coupon> manager;
	protected CouponManagerFacade(CouponManager<Coupon> manager){
		super(manager);
		this.manager=manager;
	}
		
	public static CouponManagerFacade getInstance(CouponManager<Coupon> manager){
		if(manager!=null){
			return new CouponManagerFacade(manager);
		}else{
			throw new IllegalArgumentException("Cant initilize Facade,EJB manager Reference is null.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.CouponManager#saveCoupon(com.dstar.imate.data.ICoupon)
	 */
	@Override
	public ResponseData<Coupon> saveCoupon(Coupon newCoupon) {
		try{
			return manager.saveCoupon(newCoupon);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}
}
