package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class APPNAME implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _MobileAppId;
	private String _AppName;

	public static Class<APPNAME> SCHEME_CLASS = APPNAME.class;

	public APPNAME() {
	}

	public APPNAME(SoapObject obj) {

		this._MobileAppId=obj.getProperty("MobileAppId").toString();
		this._AppName=obj.getProperty("AppName").toString();
	}

	public String get_MobileAppId() {
		return _MobileAppId;
	}

	public void set_MobileAppId(String _MobileAppId) {
		this._MobileAppId = _MobileAppId;
	}

	public String get_AppName() {
		return _AppName;
	}

	public void set_AppName(String _AppName) {
		this._AppName = _AppName;
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
