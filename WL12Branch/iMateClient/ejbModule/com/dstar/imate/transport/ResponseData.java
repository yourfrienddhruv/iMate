package com.dstar.imate.transport;

import com.dstar.imate.data.IData;
import com.google.gson.annotations.Expose;

public class ResponseData<DataClass extends IData> extends Response{
	private static final long serialVersionUID = 1L;
	
	@Expose
	private DataClass data;
	public DataClass getData() {
		return this.data;
	}
	
	@Expose
	private DataClass[] dataSet;
	public DataClass[] getDataSet() {
		return this.dataSet;
	}
	protected ResponseData() { //to comply to reflection based utilities like Json.from
		super();
	}
	protected ResponseData(DataClass data,DataClass[] dataSet) {
		super();
		this.data=data;
		this.dataSet = dataSet;
	}	
	protected ResponseData(boolean success, String messageKey,DataClass data,DataClass[] dataSet,Exception e) {
		super(success,messageKey,e);
		this.data=data;
	}
	public static<DataClass extends IData>  ResponseData<DataClass> init() {
		return new ResponseData<DataClass>(false,MessageKeyConstant.NO_OPS,null,null,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> positive(DataClass data ) {
		return new ResponseData<DataClass>(data,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> failed(Exception e) {
		return new ResponseData<DataClass>(false,MessageKeyConstant.OPERATION_FAILED,null,null,e);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> negative(String messageKey,DataClass data) {
		return new ResponseData<DataClass>(false,messageKey,data,null,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> negative(String messageKey,DataClass data,Exception e) {
		return new ResponseData<DataClass>(false,messageKey,data,null,e);
	}
	//data set
	public static<DataClass extends IData>  ResponseData<DataClass> positiveSet(DataClass[] dataSet ) {
		return new ResponseData<DataClass>(null,dataSet);
	}
	public static<DataClass extends IData> ResponseData<DataClass> negativeSet(String messageKey,DataClass[] dataSet) {
		return new ResponseData<DataClass>(false,messageKey,null,dataSet,null);
	}
	public static<DataClass extends IData>  ResponseData<DataClass> negativeSet(String messageKey,DataClass[] dataSet,Exception e ) {
		return new ResponseData<DataClass>(false,messageKey,null,dataSet,e);
	}
	//combined
	public static<DataClass extends IData>  ResponseData<DataClass> positive(DataClass data ,DataClass[] dataSet ) {
		return new ResponseData<DataClass>(data,dataSet);
	}
	public static<DataClass extends IData> ResponseData<DataClass> negative(String messageKey,DataClass data ,DataClass[] dataSet) {
		return new ResponseData<DataClass>(false,messageKey,data,dataSet,null);
	}
	
}
