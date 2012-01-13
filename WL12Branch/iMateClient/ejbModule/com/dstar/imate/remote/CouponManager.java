package com.dstar.imate.remote;

import com.dstar.imate.entity.Coupon;
import com.dstar.imate.transport.ResponseData;

public interface CouponManager extends IManager {
	public ResponseData<Coupon> saveCoupon(Coupon newCoupon);
}
