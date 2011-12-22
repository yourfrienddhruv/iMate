package com.dstar.imate.transport;

import com.dstar.imate.data.IData;

public class ResponseData<DataClass extends IData> extends Response{
	private static final long serialVersionUID = 1L;
	private DataClass data;
	public DataClass getData() {
		return this.data;
	}
	protected ResponseData(DataClass data) {
		super();
		this.data=data;
	}	
	protected ResponseData(boolean success, String messageKey,DataClass data,Exception e) {
		super(success,messageKey,e);
		this.data=data;
	}
	public static<DataClass extends IData>  ResponseData<DataClass> init() {
		return new ResponseData<DataClass>(false,MessageKeyConstant.NO_OPS,null,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> positive(DataClass data ) {
		return new ResponseData<DataClass>(data);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> failed(Exception e) {
		return new ResponseData<DataClass>(false,MessageKeyConstant.OPERATION_FAILED,null,e);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> negative(String messageKey,DataClass data) {
		return new ResponseData<DataClass>(false,messageKey,data,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> negative(String messageKey,DataClass data,Exception e) {
		return new ResponseData<DataClass>(false,messageKey,data,e);
	}
}
