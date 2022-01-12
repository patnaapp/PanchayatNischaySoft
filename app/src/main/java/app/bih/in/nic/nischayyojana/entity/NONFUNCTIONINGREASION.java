package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class NONFUNCTIONINGREASION implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _NONFUNCTIONINGREASIONID;
	private String _NONFUNCTIONINGREASIONName;

	public static Class<NONFUNCTIONINGREASION> NONFUNCTIONINGREASION_CLASS = NONFUNCTIONINGREASION.class;

	public NONFUNCTIONINGREASION() {
	}

	public NONFUNCTIONINGREASION(SoapObject obj) {

		this._NONFUNCTIONINGREASIONID=obj.getProperty("NONFUNCTIONINGREASIONID").toString();
		this._NONFUNCTIONINGREASIONName=obj.getProperty("NONFUNCTIONINGREASIONNAME").toString();
	}

	public String get_NONFUNCTIONINGREASIONID() {
		return _NONFUNCTIONINGREASIONID;
	}

	public void set_NONFUNCTIONINGREASIONID(String _NONFUNCTIONINGREASIONID) {
		this._NONFUNCTIONINGREASIONID = _NONFUNCTIONINGREASIONID;
	}

	public String get_NONFUNCTIONINGREASIONName() {
		return _NONFUNCTIONINGREASIONName;
	}

	public void set_NONFUNCTIONINGREASIONName(String _NONFUNCTIONINGREASIONName) {
		this._NONFUNCTIONINGREASIONName = _NONFUNCTIONINGREASIONName;
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
