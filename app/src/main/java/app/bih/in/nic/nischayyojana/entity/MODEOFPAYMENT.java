package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class MODEOFPAYMENT implements KvmSerializable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _PayModeID;
	private String _PayModeName;

	public static Class<MODEOFPAYMENT> SCHEME_CLASS = MODEOFPAYMENT.class;

	public MODEOFPAYMENT() {
	}

	public MODEOFPAYMENT(SoapObject obj) {

		this._PayModeID=obj.getProperty("Id").toString();
		this._PayModeName=obj.getProperty("PaymentMode").toString();
	}

	public String get_PayModeID() {
		return _PayModeID;
	}

	public void set_PayModeID(String _PayModeID) {
		this._PayModeID = _PayModeID;
	}

	public String get_PayModeName() {
		return _PayModeName;
	}

	public void set_PayModeName(String _PayModeName) {
		this._PayModeName = _PayModeName;
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
