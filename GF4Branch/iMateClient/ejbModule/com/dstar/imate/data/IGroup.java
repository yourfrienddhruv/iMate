package com.dstar.imate.data;

import java.util.Date;

public interface IGroup<G extends IGroup<?>> extends IData {
	public String getName();

	public void setName(String name);

	public Date getCreated();

	public void setCreated(Date created);
}
