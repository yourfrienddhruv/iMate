package com.dstar.imate.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.Persistent;

import com.datastax.hectorjpa.annotation.ColumnFamily;
import com.google.gson.annotations.Expose;

@Entity(name ="Relationship")
@ColumnFamily("Relationship")
@NamedQueries({ 
	@NamedQuery(name = "ByUserProfile",		query = "select r from Relationship r where r.user.id = :userProfileId order by r.group.name asc")
	})
public class Relationship extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Expose
	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private UserProfile user;
	
	@Expose
	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Group group;

	@Expose
	@Persistent(optional = false)
	private String relationshipType;

	@Expose
	@Persistent(optional = false)
	private Date started;

	@Expose
	@Persistent(optional = true)
	private Date ended;
	
	public Relationship() {
		super();
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getEnded() {
		return ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Relationship:{ ");
		builder.append("id:\"");
		builder.append(this.getId());
		builder.append("\" user:");
		builder.append(user);
		builder.append(" group:");
		builder.append(group);
		builder.append(" started:\"");
		builder.append(started);
		builder.append("\" ended:\"");
		builder.append(ended);
		builder.append("\" }");
		return builder.toString();
	}
}
