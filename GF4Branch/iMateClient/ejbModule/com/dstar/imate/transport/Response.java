package com.dstar.imate.transport;

import com.dstar.imate.transport.api.IResponse;

/**
 * WEB layer and other interaction will have this as response type
 * Response code is a development overhead trying to avoid unless necessary
 * 
 * @author Administrator
 */
public class Response  implements IResponse{
	private static final long serialVersionUID = 1L;
	private boolean success=true;
	private String messageKey=MessageKeyConstant.SUCCESS;
	private Exception ex =null;
	public boolean isSuccess() {
		return success;
	}
	public boolean isNoOps(){
		return (MessageKeyConstant.NO_OPS.equals(messageKey));
	}
	public boolean isFailed(){
		return (ex!=null);
	}
	public String getMessageKey() {
		return messageKey;
	}
	public Exception getException() {
		return ex;
	}
	public Response() {
		super();
		this.success = true;
		this.messageKey = MessageKeyConstant.SUCCESS;
		this.ex =null;
	}
	public Response(boolean success, String messageKey,Exception ex) {
		super();
		this.success = success;
		this.messageKey = messageKey;
		this.ex = ex;
	}
	@Override
	public String toString() {
		return "success=" + success + ", messageKey=" + messageKey + ", ex=" + ex + "";
	}
}
