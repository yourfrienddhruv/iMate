package com.dstar.imate.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.Persistent;

import com.datastax.hectorjpa.annotation.ColumnFamily;
import com.datastax.hectorjpa.annotation.Index;
import com.datastax.hectorjpa.annotation.Indexes;
import com.dstar.imate.data.IUserProfile;
import com.dstar.imate.entity.AbstractEntity;

@Entity(name ="UserProfile")
@ColumnFamily("UserProfile")
@Indexes({ 
	@Index(fields = "username,status", 	order = "username asc")
	})
@NamedQueries({ 
	@NamedQuery(name = "ByUsername", 		query = "select up from UserProfile up where up.username = :username and up.status = :status order by up.username asc"),
	@NamedQuery(name = "LikeUserName", 		query = "select up from UserProfile up where up.username >= :usernameStart and up.username <= :usernameEnd  and up.status='A' order by up.username asc") 
	})
public class UserProfileEntity extends AbstractEntity implements IUserProfile<UserProfileEntity>{
	private static final long serialVersionUID = 1L;

	@Persistent(optional=false)
	private String username;

	@Persistent(optional=false)
	private String firstName;

	@Persistent(optional=false)
	private String lastName;

	@Persistent(optional=false)
	private String password;

	//@Enumerated(EnumType.ORDINAL)//private UserProfileStatus status = UserProfileStatus.A ;
	@Persistent(optional=false)
	private String status= "A";
	
	@Persistent
	private Date birthDate;

	public UserProfileEntity() {
		super();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfile:{ ");
		builder.append("id:\"");
		builder.append(this.getId());
		builder.append("\" firstName:\"");
		builder.append(firstName);
		builder.append("\" lastName:\"");
		builder.append(lastName);
		builder.append("\" password:\"");
		builder.append(password);
		builder.append("\" status:\"");
		builder.append(status);
		builder.append("\" birthDate=\"");
		builder.append(birthDate);
		builder.append("\" }");
		return builder.toString();
	}
}
