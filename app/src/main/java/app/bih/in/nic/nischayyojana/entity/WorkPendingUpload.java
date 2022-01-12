package app.bih.in.nic.nischayyojana.entity;

import java.io.Serializable;



public class WorkPendingUpload implements Serializable{
	public static Class<WorkPendingUpload> USER_CLASS = WorkPendingUpload.class;
	private int _id ;
//	private String _Detail_Id = "";
	private String _Numbers= "";
	private String _Qty = "";
	private String _Name = "";
	private String _EntryDate = "";
	
//	private String _Description = "";
//	private String _Latitude = "";
//	private String _Longitude = "";
//	private String _PhysicalWorkId = "";
//	private byte[] _Photo = null;
//	private String _PhotoDate = "";
//	private byte[] _Photo2 = null;
//	private String _PhotoDate2 = "";
//	
//	private byte[] _Photo3 = null;
//	private String _PhotoDate3 = "";
//	
//	private byte[] _Photo4 = null;
//	private String _PhotoDate4 = "";
	
	public WorkPendingUpload() {
		// TODO Auto-generated constructor stub
	}

	public static Class<WorkPendingUpload> getUSER_CLASS() {
		return USER_CLASS;
	}

	public static void setUSER_CLASS(Class<WorkPendingUpload> uSER_CLASS) {
		USER_CLASS = uSER_CLASS;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_Qty() {
		return _Qty;
	}

	public void set_Qty(String _Qty) {
		this._Qty = _Qty;
	}

	public String get_Name() {
		return _Name;
	}

	public void set_Name(String _Name) {
		this._Name = _Name;
	}

	public String get_EntryDate() {
		return _EntryDate;
	}

	public void set_EntryDate(String _EntryDate) {
		this._EntryDate = _EntryDate;
	}

	public String get_Numbers() {
		return _Numbers;
	}

	public void set_Numbers(String _Numbers) {
		this._Numbers = _Numbers;
	}


}
