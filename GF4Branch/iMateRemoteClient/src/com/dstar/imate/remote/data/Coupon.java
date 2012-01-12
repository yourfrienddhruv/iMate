package com.dstar.imate.remote.data;
import java.lang.reflect.Type;

import java.util.Date;

import com.dstar.imate.data.ICoupon;
import com.dstar.imate.web.json.base.gson.Json;
import com.google.gson.reflect.TypeToken;

public class Coupon extends AbstractViewObject implements ICoupon<Coupon>{
	private static final long serialVersionUID = 1L;

	public static final Type TYPE = new TypeToken<ICoupon<Coupon>>() {}.getType();
	private String brand;
	private Double discount;
	private Double details;
	private Date created;
	private Date validFrom;
	private Date expiring;
	
	public Coupon() {
		super();
	}
		
	@Override
	public String getBrand() {
		return brand;
	}

	@Override
	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public Double getDiscount() {
		return discount;
	}

	@Override
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public Double getDetails() {
		return details;
	}

	@Override
	public void setDetails(Double details) {
		this.details = details;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public Date getValidFrom() {
		return validFrom;
	}

	@Override
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Override
	public Date getExpiring() {
		return expiring;
	}

	@Override
	public void setExpiring(Date expiring) {
		this.expiring = expiring;
	}

	@Override
	public String toString() {
		return Json.to(this);
	}
}
