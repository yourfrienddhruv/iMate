package com.dstar.imate.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.Persistent;

import com.datastax.hectorjpa.annotation.ColumnFamily;
import com.datastax.hectorjpa.annotation.Index;
import com.datastax.hectorjpa.annotation.Indexes;
import com.dstar.imate.data.ICoupon;

@Entity(name ="Coupon")
@ColumnFamily("Coupon")
@Indexes({ 
	@Index(fields = "brand", 	order = "brand asc"),
	@Index(fields = "expiring", 	order = "expiring desc")
	})
@NamedQueries({ 
	@NamedQuery(name = "ByExpiring", 		query = "select c from Coupon c where c.brand = :brand order by c.expiring desc")
	})
public class CouponEntity extends AbstractEntity implements ICoupon<CouponEntity>{
	private static final long serialVersionUID = 1L;

	public CouponEntity(){super();}
	
	@Persistent(optional=false)
	private String brand;
	
	@Persistent(optional=false)
	private Double discount;
	
	@Persistent
	private Double details;
	
	@Persistent(optional=false)
	private Date created;
	
	@Persistent(optional=false)
	private Date validFrom;
	
	@Persistent(optional=false)
	private Date expiring;
	
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
		return "CouponEntity [brand=" + brand + ", discount=" + discount + ", details=" + details + ", created=" + created + ", validFrom="
				+ validFrom + ", expiring=" + expiring + "]";
	}
	
}
