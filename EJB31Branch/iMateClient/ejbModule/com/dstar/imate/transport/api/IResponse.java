package com.dstar.imate.transport.api;

import java.io.Serializable;

public interface IResponse  extends Serializable{
	public boolean isSuccess();
	public boolean isNoOps();
	public boolean isFailed();
	public String getMessageKey() ;
	public Exception getException();
}
