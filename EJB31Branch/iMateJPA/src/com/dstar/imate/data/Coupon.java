package com.dstar.imate.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.Persistent;

import com.datastax.hectorjpa.annotation.ColumnFamily;
import com.datastax.hectorjpa.annotation.Index;
import com.datastax.hectorjpa.annotation.Indexes;
import com.google.gson.annotations.Expose;

@Entity(name ="Coupon")
@ColumnFamily("Coupon")
@Indexes({ 
	@Index(fields = "brand", 	order = "brand asc"),
	@Index(fields = "expiring", 	order = "expiring desc")
	})
@NamedQueries({ 
	@NamedQuery(name = "ByExpiring", 		query = "select c from Coupon c where c.brand = :brand order by c.expiring desc")
	})
public class Coupon extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	public Coupon(){super();}
	
	@Expose
	@Persistent(optional=false)
	private String brand;
	
	@Expose
	@Persistent(optional=false)
	private Double discount;
	
	@Expose
	@Persistent
	private String details;
	
	@Expose
	@Persistent(optional=false)
	private Date created;
	
	@Expose
	@Persistent(optional=false)
	private Date validFrom;
	
	@Expose
	@Persistent(optional=false)
	private Date expiring;
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getExpiring() {
		return expiring;
	}

	public void setExpiring(Date expiring) {
		this.expiring = expiring;
	}

	@Override
	public String toString() {
		return "CouponEntity [brand=" + brand + ", discount=" + discount + ", details=" + details + ", created=" + created + ", validFrom="
				+ validFrom + ", expiring=" + expiring + "]";
	}
	
}
