package app.bih.in.nic.nischayyojana.entity;

import java.io.Serializable;


public class PhysicalProgressWardProperty implements Serializable{
	public static Class<PhysicalProgressWardProperty> USER_CLASS = PhysicalProgressWardProperty.class;

	private String _Ward_ID= "";
	private String _Ward_Name = "";
	private String _Count= "";
		private String _Pan_Code="";
	private String _Pan_Name="";

	private String _Row_ID="";

	private String BoringCompletedInFeet="";
	private String BoringCompletedDate="";
	private String StagingHightInFeet="";
	private String TankInstallationInLtr="";
	private String ConstructionDate="";
	private String TotalHouseBenifited="";
	private String TotalLength="";

	public String getBoringCompletedInFeet() {
		return BoringCompletedInFeet;
	}

	public void setBoringCompletedInFeet(String boringCompletedInFeet) {
		BoringCompletedInFeet = boringCompletedInFeet;
	}

	public String getBoringCompletedDate() {
		return BoringCompletedDate;
	}

	public void setBoringCompletedDate(String boringCompletedDate) {
		BoringCompletedDate = boringCompletedDate;
	}

	public String getStagingHightInFeet() {
		return StagingHightInFeet;
	}

	public void setStagingHightInFeet(String stagingHightInFeet) {
		StagingHightInFeet = stagingHightInFeet;
	}

	public String getTankInstallationInLtr() {
		return TankInstallationInLtr;
	}

	public void setTankInstallationInLtr(String tankInstallationInLtr) {
		TankInstallationInLtr = tankInstallationInLtr;
	}

	public String getConstructionDate() {
		return ConstructionDate;
	}

	public void setConstructionDate(String constructionDate) {
		ConstructionDate = constructionDate;
	}

	public String getTotalHouseBenifited() {
		return TotalHouseBenifited;
	}

	public void setTotalHouseBenifited(String totalHouseBenifited) {
		TotalHouseBenifited = totalHouseBenifited;
	}

	public String getTotalLength() {
		return TotalLength;
	}

	public void setTotalLength(String totalLength) {
		TotalLength = totalLength;
	}

	public String get_Row_ID() {
		return _Row_ID;
	}

	public void set_Row_ID(String _Row_ID) {
		this._Row_ID = _Row_ID;
	}

	public String get_Pan_Code() {
		return _Pan_Code;
	}

	public void set_Pan_Code(String _Pan_Code) {
		this._Pan_Code = _Pan_Code;
	}

	public String get_Pan_Name() {
		return _Pan_Name;
	}

	public void set_Pan_Name(String _Pan_Name) {
		this._Pan_Name = _Pan_Name;
	}

	public String get_Ward_Name() {
		return _Ward_Name;
	}

	public void set_Ward_Name(String _Ward_Name) {
		this._Ward_Name = _Ward_Name;
	}

	public String get_Count() {
		return _Count;
	}

	public void set_Count(String _Count) {
		this._Count = _Count;
	}


	public String get_Ward_ID() {
		return _Ward_ID;
	}

	public void set_Ward_ID(String _Ward_ID) {
		this._Ward_ID = _Ward_ID;
	}

	public PhysicalProgressWardProperty() {
		// TODO Auto-generated constructor stub
	}

	public static Class<PhysicalProgressWardProperty> getUSER_CLASS() {
		return USER_CLASS;
	}

	public static void setUSER_CLASS(Class<PhysicalProgressWardProperty> uSER_CLASS) {
		USER_CLASS = uSER_CLASS;
	}

}
