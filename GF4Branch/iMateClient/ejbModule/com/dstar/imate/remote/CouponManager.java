package com.dstar.imate.remote;

import com.dstar.imate.data.ICoupon;
import com.dstar.imate.transport.ResponseData;

public interface CouponManager<C extends ICoupon<?>> extends IManager {
	public ResponseData<C> saveCoupon(C newCoupon);
}
