package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class WardVisitingReportProperty implements Serializable{
	public static Class<WardVisitingReportProperty> WardVisitingReport_CLASS = WardVisitingReportProperty.class;
	private String row_id;

	/*
	<Total>string</Total>
          <DistCode>string</DistCode>
          <DistName>string</DistName>
          <BlockCode>string</BlockCode>
          <BlockName>string</BlockName>
          <PanchayatCode>string</PanchayatCode>
          <PanchayatName>string</PanchayatName>
          <WARDCODE>string</WARDCODE>
          <WARDNAME>string</WARDNAME>
	 */

	private String NischayCode;
	private String Total;

	private String PanchayatCode;
	private String PanchayatName;

	private String WARDCODE;
	private String WARDNAME;



	private String DistCode;
	private String DistName;

	private String BlockCode;
	private String BlockName;


	public WardVisitingReportProperty() {
		// TODO Auto-generated constructor stub
	}
	public WardVisitingReportProperty(SoapObject obj) {


//		this.DistCode= obj.getProperty("DistCode").toString().trim();
//		this.DistName= obj.getProperty("DistName").toString().trim();
//		this.BlockCode= obj.getProperty("BlockCode").toString().trim();
//		this.BlockName= obj.getProperty("BlockName").toString().trim();
//		this.PanchayatCode= obj.getProperty("PanchayatCode").toString().trim();
//		this.PanchayatName= obj.getProperty("PanchayatName").toString().trim();
		this.WARDCODE= obj.getProperty("WARDCODE").toString().trim();
		this.WARDNAME= obj.getProperty("WARDNAME").toString().trim();
		//this.NischayCode= obj.getProperty("f_SchemeId").toString().trim();
		this.Total= obj.getProperty("Total").toString().trim();

	}


	public static Class<WardVisitingReportProperty> getWardVisitingReport_CLASS() {
		return WardVisitingReport_CLASS;
	}

	public static void setUSER_CLASS(Class<WardVisitingReportProperty> uSER_CLASS) {
		WardVisitingReport_CLASS = uSER_CLASS;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}



	public String getPanchayatCode() {
		return PanchayatCode;
	}

	public void setPanchayatCode(String PanchayatCode) {
		PanchayatCode = PanchayatCode;
	}

	public String getPanchayatName() {
		return PanchayatName;
	}

	public void setPanchayatName(String PanchayatName) {
		PanchayatName = PanchayatName;
	}

	public String getWARDCODE() {
		return WARDCODE;
	}

	public void setWARDCODE(String WARDCODE) {
		WARDCODE = WARDCODE;
	}

	public String getWARDNAME() {
		return WARDNAME;
	}

	public void setWARDNAME(String WARDNAME) {
		WARDNAME = WARDNAME;
	}

	public String getNischayCode() {
		return NischayCode;
	}

	public void setNischayCode(String nischayCode) {
		NischayCode = nischayCode;
	}



	public String getDistCode() {
		return DistCode;
	}

	public void setDistCode(String distCode) {
		DistCode = distCode;
	}

	public String getBlockCode() {
		return BlockCode;
	}

	public void setBlockCode(String blockCode) {
		BlockCode = blockCode;
	}


	public String getDistName() {
		return DistName;
	}

	public void setDistName(String distName) {
		DistName = distName;
	}

	public String getBlockName() {
		return BlockName;
	}

	public void setBlockName(String blockName) {
		BlockName = blockName;
	}
}
