package com.dstar.imate.data;

import java.util.Date;

public interface ICoupon<G extends ICoupon<?>> extends IData {
	public String getBrand() ;
	public void setBrand(String brand);
	public Double getDiscount() ;
	public void setDiscount(Double discount);
	public Double getDetails() ;
	public void setDetails(Double details) ;
	public Date getCreated();
	public void setCreated(Date created);
	public Date getValidFrom();
	public void setValidFrom(Date validFrom) ;
	public Date getExpiring();
	public void setExpiring(Date expiring);}
