package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class PANCHAYAT implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<PANCHAYAT> PANCHAYAT_CLASS = PANCHAYAT.class;
	
	/*
	<DistrictCode>string</DistrictCode>
          <BlockCode>string</BlockCode>
          <PanchayatCode>string</PanchayatCode>
          <PanchayatName>string</PanchayatName>
	 */
	private String _DistrictCode = "";
	private String _BlockCode = "";
	private String _PanCode = "";
	private String _PanName = "";

	public PANCHAYAT() {
		super();
	}

	public PANCHAYAT(SoapObject obj) {
		
		this._DistrictCode = obj.getProperty("DistrictCode").toString();
		this._BlockCode = obj.getProperty("BlockCode").toString();
		this._PanCode = obj.getProperty("PanchayatCode").toString();
		this._PanName = obj.getProperty("PanchayatName").toString();

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


	public String get_PanName() {
		return _PanName;
	}

	public void set_PanName(String _PanName) {
		this._PanName = _PanName;
	}

	public String get_DistrictCode() {
		return _DistrictCode;
	}

	public void set_DistrictCode(String _DistrictCode) {
		this._DistrictCode = _DistrictCode;
	}

	public String get_BlockCode() {
		return _BlockCode;
	}

	public void set_BlockCode(String _BlockCode) {
		this._BlockCode = _BlockCode;
	}

	public String get_PanCode() {
		return _PanCode;
	}

	public void set_PanCode(String _PanCode) {
		this._PanCode = _PanCode;
	}

	

}
