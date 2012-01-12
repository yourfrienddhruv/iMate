package com.dstar.imate.business;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import com.dstar.imate.entity.CouponEntity;
import com.dstar.imate.remote.CouponManager;
import com.dstar.imate.transport.ResponseData;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote({CouponManager.class})
//@Local({CouponManager.class}) Should not required
public class CouponManagerBean implements CouponManager<CouponEntity> {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CouponManagerBean.class);

	//private static final String END_OF_RANGE="\uffff";
	
	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
		log.debug("Inted EntityManager = {}", em);
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.CouponManager#saveCoupon(com.dstar.imate.data.ICoupon)
	 */
	@Override
	public ResponseData<CouponEntity> saveCoupon(CouponEntity newCoupon) {
		log.debug("Before persist :{}", newCoupon);
			if(newCoupon.getCreated()==null){	newCoupon.setCreated(new Date());	}
			em.persist(newCoupon);
			if (newCoupon.getId() != null) {
				return ResponseData.positive(newCoupon);
			} else {
				return ResponseData.negative("fail.creation.coupon", newCoupon);
			}
	}

}