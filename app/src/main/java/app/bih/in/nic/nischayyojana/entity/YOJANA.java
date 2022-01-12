package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class YOJANA implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _SchemeID;
	private String _SchemeName;
	private String _Type;
	private String _Fyear;
	private String _YojanaID;
	private String _YojanaName;
	private String _WardCode;
	public static Class<YOJANA> SCHEME_CLASS = YOJANA.class;

	public YOJANA() {
	}

	public YOJANA(SoapObject obj) {
		this._YojanaID=obj.getProperty("SchemeId").toString();
		this._YojanaName=obj.getProperty("SchemeName").toString();
		this._SchemeID=obj.getProperty("type").toString();
		this._WardCode=obj.getProperty("WardCode").toString();
		this._Fyear=obj.getProperty("FYear").toString();
	}

	public String get_YojanaID() {
		return _YojanaID;
	}

	public void set_YojanaID(String _YojanaID) {
		this._YojanaID = _YojanaID;
	}

	public String get_YojanaName() {
		return _YojanaName;
	}

	public void set_YojanaName(String _YojanaName) {
		this._YojanaName = _YojanaName;
	}

	public String get_SchemeID() {
		return _SchemeID;
	}

	public void set_SchemeID(String _SchemeID) {
		this._SchemeID = _SchemeID;
	}

	public String get_SchemeName() {
		return _SchemeName;
	}

	public void set_SchemeName(String _SchemeName) {
		this._SchemeName = _SchemeName;
	}

	public String get_WardCode() {
		return _WardCode;
	}

	public void set_WardCode(String _WardCode) {
		this._WardCode = _WardCode;
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

	public String get_Type() {
		return _Type;
	}

	public void set_Type(String _Type) {
		this._Type = _Type;
	}

	public String get_Fyear() {
		return _Fyear;
	}

	public void set_Fyear(String _Fyear) {
		this._Fyear = _Fyear;
	}
}
