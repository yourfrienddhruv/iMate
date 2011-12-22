package com.dstar.imate.remote.data;

import java.util.Date;

import com.dstar.imate.data.IGroup;
import com.dstar.imate.web.json.base.gson.Json;

public class Group extends AbstractViewObject implements IGroup<Group>{
	private static final long serialVersionUID = 1L;

	private String name;

	private Date created;
	
	public Group() {
		super();
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
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
	public String toString() {
		return Json.to(this);
	}
}
