package com.dstar.imate.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.Persistent;

import com.datastax.hectorjpa.annotation.ColumnFamily;
import com.datastax.hectorjpa.annotation.Index;
import com.datastax.hectorjpa.annotation.Indexes;
import com.dstar.imate.data.IGroup;
import com.dstar.imate.entity.AbstractEntity;


@Entity(name ="Group")
@ColumnFamily("Group")
@Indexes({ 
	@Index(fields = "name", 	order = "name asc")
	})
@NamedQueries({ 
	@NamedQuery(name = "ByName", 		query = "select g from Group g where g.name = :name order by g.name asc"),
	@NamedQuery(name = "LikeName", 		query = "select g from Group g where g.name >= :nameStart and g.name <= :nameEnd order by g.name asc")
	})
public class GroupEntity extends AbstractEntity implements IGroup<GroupEntity>{
	private static final long serialVersionUID = 1L;

	@Persistent(optional=false)
	//@Unique Not supported in Hector-JPA,should be done by logic before persist	
	private String name;

	@Persistent(optional=false)
	private Date created;
	
	public GroupEntity() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group:{ ");
		builder.append("id:\"");
		builder.append(this.getId());
		builder.append("\" name:\"");
		builder.append(name);
		builder.append("\" created:\"");
		builder.append(created);
		builder.append("\" }");
		return builder.toString();
	}

}
