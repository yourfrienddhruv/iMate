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

@Entity(name ="UserProfile")
@ColumnFamily("UserProfile")
@Indexes({ 
	@Index(fields = "username,status", 	order = "username asc")
	})
@NamedQueries({ 
	@NamedQuery(name = "ByUsername", 		query = "select up from UserProfile up where up.username = :username and up.status = :status order by up.username asc"),
	@NamedQuery(name = "LikeUserName", 		query = "select up from UserProfile up where up.username >= :usernameStart and up.username <= :usernameEnd  and up.status='A' order by up.username asc") 
	})
public class UserProfile extends AbstractEntity{
	private static final long serialVersionUID = 1L;

	@Expose
	@Persistent(optional=false)
	private String username;

	@Expose
	@Persistent(optional=false)
	private String firstName;

	@Expose
	@Persistent(optional=false)
	private String lastName;

	@Expose
	@Persistent(optional=false)
	private String password;

	//@Enumerated(EnumType.ORDINAL)//private UserProfileStatus status = UserProfileStatus.A ;
	@Expose
	@Persistent(optional=false)
	private String status= "A";
	
	@Expose
	@Persistent
	private Date birthDate;

	public UserProfile() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBirthDate() {
		return birthDate;
	}

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
