package com.dstar.imate.remote.data;

import java.util.Date;

import com.dstar.imate.data.IRelationship;
import com.dstar.imate.web.json.base.gson.Json;

public class Relationship extends AbstractViewObject implements IRelationship<Relationship, UserProfile, Group>{
	private static final long serialVersionUID = 1L;
	private UserProfile user;

	private Group group;

	private String relationshipType;

	private Date started;

	private Date ended;
	
	public Relationship() {
		super();
	}
	@Override
	public UserProfile getUser() {
		return user;
	}
	@Override
	public void setUser(UserProfile user) {
		this.user = user;
	}
	@Override
	public Group getGroup() {
		return group;
	}
	@Override
	public void setGroup(Group group) {
		this.group = group;
	}
	@Override
	public String getRelationshipType() {
		return relationshipType;
	}
	@Override
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
	@Override
	public Date getStarted() {
		return started;
	}
	@Override
	public void setStarted(Date started) {
		this.started = started;
	}
	@Override
	public Date getEnded() {
		return ended;
	}
	@Override
	public void setEnded(Date ended) {
		this.ended = ended;
	}
	@Override
	public String toString() {
		return Json.to(this);
	}
}
