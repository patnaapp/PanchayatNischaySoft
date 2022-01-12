package app.bih.in.nic.nischayyojana.entity;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class Fyear implements KvmSerializable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fyId;
	private String fy;
	private boolean STATUS;

	public static Class<Fyear> FYEAR_CLASS = Fyear.class;

	public Fyear() {
	}

	public Fyear(SoapObject obj) {
		this.fyId = obj.getProperty("FYID").toString();
		this.fy = obj.getProperty("FY").toString();
		this.STATUS = Boolean.valueOf(((obj.getProperty("Status"))
				.toString().toLowerCase()));

	}

	

	public String getFyId() {
		return fyId;
	}

	public void setFyId(String fyId) {
		this.fyId = fyId;
	}

	public String getFy() {
		return fy;
	}

	public void setFy(String fy) {
		this.fy = fy;
	}

	public boolean getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(boolean STATUS) {
		this.STATUS = STATUS;
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
