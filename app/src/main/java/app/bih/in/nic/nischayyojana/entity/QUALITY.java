package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class QUALITY implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _QualityID;
	private String _QualityName;

	public static Class<QUALITY> SCHEME_CLASS = QUALITY.class;

	public QUALITY() {
	}

	public QUALITY(SoapObject obj) {

//		this._SchemeID=obj.getProperty("Id").toString();
//		this._SchemeName=obj.getProperty("SchemeName").toString();
	}

	public String get_QualityID() {
		return _QualityID;
	}

	public void set_QualityID(String _QualityID) {
		this._QualityID = _QualityID;
	}

	public String get_QualityName() {
		return _QualityName;
	}

	public void set_QualityName(String _QualityName) {
		this._QualityName = _QualityName;
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

}
