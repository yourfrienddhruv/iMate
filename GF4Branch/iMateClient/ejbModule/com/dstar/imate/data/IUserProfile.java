/**
 * 
 */
package com.dstar.imate.data;

import java.util.Date;

public interface IUserProfile<U extends IUserProfile<?>> extends IData {

	public String getUsername();

	public void setUsername(String username);

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public String getPassword();

	public void setPassword(String password);

	public String getStatus();

	public void setStatus(String status);

	public Date getBirthDate();

	public void setBirthDate(Date birthDate);
}
