package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class SCHEME implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _SchemeID;
	private String _SchemeName;
	private String _SchemeNameHN;

	public static Class<SCHEME> SCHEME_CLASS = SCHEME.class;

	public SCHEME() {
	}

	public SCHEME(SoapObject obj) {

		this._SchemeID=obj.getProperty("Id").toString();
		this._SchemeName=obj.getProperty("SchemeName").toString();
		this._SchemeNameHN=obj.getProperty("SchemeNameHN").toString();
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

	public String get_SchemeNameHN() {
		return _SchemeNameHN;
	}

	public void set_SchemeNameHN(String _SchemeNameHN) {
		this._SchemeNameHN = _SchemeNameHN;
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
