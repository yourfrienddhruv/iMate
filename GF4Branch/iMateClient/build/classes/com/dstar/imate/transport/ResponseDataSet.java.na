package com.dstar.imate.transport;

import java.util.SortedSet;

import com.dstar.imate.data.IData;

public class ResponseDataSet<DataSetClass extends SortedSet<? extends IData>> extends Response{
	private static final long serialVersionUID = 1L;
	private DataSetClass dataSet;
	public DataSetClass getDataSet() {
		return this.dataSet;
	}
	protected ResponseDataSet(DataSetClass dataSet) {
		super();
		this.dataSet = dataSet;
	}
	protected ResponseDataSet(boolean success, String messageKey,DataSetClass dataSet,Exception e) {
		super(success,messageKey,e);
		this.dataSet = dataSet;		
	}
	public static<DataSetClass extends SortedSet<? extends IData>>  ResponseDataSet<DataSetClass> init() {
		return new ResponseDataSet<DataSetClass>(false,MessageKeyConstant.NO_OPS,null,null);
	}
	public static<DataSetClass extends SortedSet<? extends IData>>  ResponseDataSet<DataSetClass> failed(Exception e) {
		return new ResponseDataSet<DataSetClass>(false,MessageKeyConstant.OPERATION_FAILED,null,e);
	}
	public static<DataSetClass extends SortedSet<? extends IData>>  ResponseDataSet<DataSetClass> positive(DataSetClass data ) {
		return new ResponseDataSet<DataSetClass>(data);
	}
	public static<DataSetClass extends SortedSet<? extends IData>>  ResponseDataSet<DataSetClass> negative(String messageKey,DataSetClass data ) {
		return new ResponseDataSet<DataSetClass>(false,messageKey,data,null);
	}
	public static<DataSetClass extends SortedSet<? extends IData>>  ResponseDataSet<DataSetClass> negative(String messageKey,DataSetClass data,Exception e ) {
		return new ResponseDataSet<DataSetClass>(false,messageKey,data,e);
	}
}
