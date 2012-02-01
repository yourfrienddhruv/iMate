package com.dstar.imate.business;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.dstar.imate.data.Coupon;
import com.dstar.imate.transport.ResponseData;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CouponManager extends BaseManager{
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
	public ResponseData<Coupon> searchLatest(Coupon search) {
		log.debug("finding like {}",search);
		TypedQuery<Coupon> q = em.createNamedQuery("likeBrandByExpiring", Coupon.class)
				.setParameter("brandStart", search.getBrand()).setParameter("brandEnd", search.getBrand()+END_OF_RANGE)
				.setMaxResults(20);
		List<Coupon> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.coupon.found",new Coupon[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new Coupon[foundList.size()]));
		}
	}
}