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
import com.dstar.imate.data.IRelationship;

@Entity(name ="Relationship")
@ColumnFamily("Relationship")
@NamedQueries({ 
	@NamedQuery(name = "ByUserProfile",		query = "select r from Relationship r where r.user.id = :userProfileId order by r.group.name asc")
	})
public class RelationshipEntity extends AbstractEntity implements IRelationship<RelationshipEntity,UserProfileEntity,GroupEntity>{
	private static final long serialVersionUID = 1L;


	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private UserProfileEntity user;

	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private GroupEntity group;

	@Persistent(optional = false)
	private String relationshipType;

	@Persistent(optional = false)
	private Date started;

	@Persistent(optional = true)
	private Date ended;
	
	public RelationshipEntity() {
		super();
	}

	public UserProfileEntity getUser() {
		return user;
	}

	public void setUser(UserProfileEntity user) {
		this.user = user;
	}

	public GroupEntity getGroup() {
		return group;
	}

	public void setGroup(GroupEntity group) {
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
