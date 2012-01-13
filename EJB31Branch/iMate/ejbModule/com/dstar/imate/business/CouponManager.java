package com.dstar.imate.business;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import com.dstar.imate.data.Coupon;
import com.dstar.imate.transport.ResponseData;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CouponManager extends AbstractManager{
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CouponManager.class);
	
	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
		log.debug("Inted EntityManager = {}", em);
	}

	public ResponseData<Coupon> saveCoupon(Coupon newCoupon) {
		log.debug("Before persist :{}", newCoupon);
			if(newCoupon.getCreated()==null){	newCoupon.setCreated(new Date());	}
			em.persist(newCoupon);
			return ResponseData.positive(newCoupon);
	}

}