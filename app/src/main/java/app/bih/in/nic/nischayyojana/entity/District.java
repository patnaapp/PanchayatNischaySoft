package app.bih.in.nic.nischayyojana.entity;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class District implements KvmSerializable, Serializable {
	
	private static final long serialVersionUID = 1L;

	public static Class<District> DISTRICT_CLASS = District.class;
	private String _DistCode = "";
	private String _DistName = "";
	private String _PacsBankId = "";

	public District() {
		super();
	}

	public District(SoapObject obj) {
		
		this._DistCode = obj.getProperty("DistCode").toString();
		this._DistName = obj.getProperty("DistName").toString();
		this._PacsBankId = obj.getProperty("PacksBankId").toString();

	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	
	
	
	
	
	
	
	public String get_DistCode() {
		return _DistCode;
	}

	public void set_DistCode(String _DistCode) {
		this._DistCode = _DistCode;
	}

	public String get_DistName() {
		return _DistName;
	}

	public void set_DistName(String _DistName) {
		this._DistName = _DistName;
	}

	public String get_PacsBankId() {
		return _PacsBankId;
	}

	public void set_PacsBankId(String _PacsBankId) {
		this._PacsBankId = _PacsBankId;
	}

	

}
