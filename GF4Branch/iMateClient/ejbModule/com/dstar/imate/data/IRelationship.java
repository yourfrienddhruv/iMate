package com.dstar.imate.data;

import java.util.Date;

public interface IRelationship<R extends IRelationship<?,?,?>,U extends IUserProfile<?>,G extends IGroup<?>> extends IData {
	public U getUser();

	public void setUser(U user);

	public G getGroup();

	public void setGroup(G group);

	public String getRelationshipType();

	public void setRelationshipType(String relationshipType);

	public Date getStarted();

	public void setStarted(Date started);

	public Date getEnded();

	public void setEnded(Date ended);
}
