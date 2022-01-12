package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class WorkProgressReportGNProperty implements Serializable{
	public static Class<WorkProgressReportGNProperty> WorkProgressReportPayJal_CLASS = WorkProgressReportGNProperty.class;
	private String row_id;
	private String FYear;
	private String PanCode;
	private String PanName;
	private String WardCode;
	private String WardName;
	private String NischayCode;
	private String YojanaCode;
	private String YojanaTypeNAME;
	private String CurrentPhysicalStatus;
	private String RoadType ;
	private String PathRoadType ;
	private String NaliType ;
	private String JalNikasi;
	private String TotalRoadDistance_Length ;
	private String TotalRoadDistance_Breadth;
	private String TotalRoadDistance_Fat ;
	private String TotalPathRoadDistance_Breadth ;
	private String TotalNaliDistance_Length ;
	private String TotalNali_Breadth ;
	private String TotalNali_Gharai ;
	private String SokhtaKiSankhya ;
	private String MittiKrya ;
	private String HugePipe ;
	private String ProjectCompleteionDate;
	private String Remarks;
	private String CreatedBy;
	private String CreatedDate;
	private String Photo1;
	private String Photo2;
	private String Photo3;
	private String Photo4;
	private String Latitude;
	private String Longitude;
	private String DistCode;
	private String BlockCode;
	private String TotalAllotment;
	private String TotalExpenditure;
	private String YujanaNumber;
	private String RequestDate;
	private String SubmitDate;
	private String TechAcceptedAmount;
	private String TechAcceptedDate;
	private String PanAcceptedAmount;
	private String PanAcceptedDate;
	private String PanNischayPrbhariName;
	private String PanNischayPrbhariMobNum;
	private String PanYojanaPrbhariName;
	private String PanYojanaPrbhariMobNum;
	private String PrashakhiDeptName;
	private String MaapPustNumprivate;
	private String WorkStartingDate;
	private String WorkEndDuration;

	private String SchemeName;
	private String SchemeNum;
	private String DistName;
	private String BlockName;

	private String InspectorName;
	private String InspectorPost;
	private String EntryDate;
	private String VillageCode;


	public WorkProgressReportGNProperty() {
		// TODO Auto-generated constructor stub
	}
	public WorkProgressReportGNProperty(SoapObject obj,String ROLE,String work_type) {

		//this.row_id= obj.getProperty("DistrictCode").toString().trim();

		this.DistCode= obj.getProperty("DistCode").toString().trim();
		this.DistName= obj.getProperty("DistName").toString().trim();
		this.BlockCode= obj.getProperty("BlockCode").toString().trim();
		this.BlockName= obj.getProperty("BlockName").toString().trim();
		this.PanCode= obj.getProperty("PanchayatCode").toString().trim();
		this.PanName= obj.getProperty("PanchayatName").toString().trim();
		this.WardCode= obj.getProperty("WARDCODE").toString().trim();
		this.WardName= obj.getProperty("WARDNAME").toString().trim();
		this.NischayCode= obj.getProperty("f_SchemeId").toString().trim();
		this.FYear= obj.getProperty("FYear").toString().trim();
		this.SchemeName= obj.getProperty("SchemeName").toString().trim();
		this.SchemeNum= obj.getProperty("SchemeNo").toString().trim();

		this.RequestDate= obj.getProperty("DateOfRequestByWard").toString().trim();
		this.SubmitDate= obj.getProperty("DateOfSubmissionByJEToBlock").toString().trim();

		if(ROLE.equalsIgnoreCase("PANADM")) {
			if(work_type.equalsIgnoreCase("PV")) {
				this.TechAcceptedAmount = obj.getProperty("AmountOfTS").toString().trim();
				this.TechAcceptedDate = obj.getProperty("DateOfTS").toString().trim();
				this.PanAcceptedAmount = obj.getProperty("AmountOfAS").toString().trim();
				this.PanAcceptedDate = obj.getProperty("DateOfAS").toString().trim();
			}
		}

		this.PanYojanaPrbhariName= obj.getProperty("NameOfJE").toString().trim();
		this.PanYojanaPrbhariMobNum= obj.getProperty("MobNoOfJE").toString().trim();

		this.PanNischayPrbhariName= obj.getProperty("InChargeName").toString().trim();
		this.PanNischayPrbhariMobNum= obj.getProperty("IncahrgeMobNo").toString().trim();

//		this.PanNischayPrbhariName= obj.getProperty("NameOfJE").toString().trim();
//		this.PanNischayPrbhariMobNum= obj.getProperty("MobNoOfJE").toString().trim();
//		this.PanYojanaPrbhariName= obj.getProperty("InChargeName").toString().trim();
//		this.PanYojanaPrbhariMobNum= obj.getProperty("IncahrgeMobNo").toString().trim();
		this.PrashakhiDeptName= obj.getProperty("DeptNameOfJE").toString().trim();
		this.MaapPustNumprivate= obj.getProperty("MBNO").toString().trim();
		this.WorkStartingDate= obj.getProperty("DateOfStartedWork").toString().trim();
		this.WorkEndDuration= obj.getProperty("FinaliseDuretion").toString().trim();

		if(ROLE.equalsIgnoreCase("PANADM")) {
			if(work_type.equalsIgnoreCase("PV")) {
				this.TotalAllotment = obj.getProperty("TotFundTrfForWard").toString().trim();
				this.TotalExpenditure = obj.getProperty("TotalVoucherAmtNaliGali").toString().trim();
			}
		}
		this.CurrentPhysicalStatus= obj.getProperty("CurrentPhysicalStatus").toString().trim();

		this.YojanaCode= obj.getProperty("SchemeNo").toString().trim();
		this.YojanaTypeNAME= obj.getProperty("SchemeName").toString().trim();

		this.RoadType = obj.getProperty("TypeOfPavement").toString().trim();
		this.PathRoadType = obj.getProperty("TypeOfPavement").toString().trim();

		this.NaliType = obj.getProperty("NaliType").toString().trim();

		this.JalNikasi= obj.getProperty("DraunageFacilities").toString().trim();

		this.TotalRoadDistance_Length = obj.getProperty("TotalLenth").toString().trim();
		this.TotalRoadDistance_Breadth= obj.getProperty("Roadlength").toString().trim();
		this.TotalRoadDistance_Fat = obj.getProperty("RoadThick").toString().trim();

		this.TotalPathRoadDistance_Breadth = obj.getProperty("Patrilength").toString().trim();

		this.TotalNaliDistance_Length = obj.getProperty("NaliLength").toString().trim();
		this.TotalNali_Breadth = obj.getProperty("NaliWidth").toString().trim();
		this.TotalNali_Gharai = obj.getProperty("NaliDepth").toString().trim();

		this.SokhtaKiSankhya = obj.getProperty("SoakPitConstructed").toString().trim();
		this.MittiKrya = obj.getProperty("SoailWork").toString().trim();
		this.HugePipe = obj.getProperty("NoOfHugePipe").toString().trim();
		//this.ProjectCompleteionDate= obj.getProperty("DistrictCode").toString().trim();
		this.Remarks= obj.getProperty("Remarks").toString().trim();
		this.CreatedBy= obj.getProperty("EntryBy").toString().trim();
		this.CreatedDate= obj.getProperty("EntryDate").toString().trim();

		this.Photo1= obj.getProperty("PhotoPath1").toString().trim();
		this.Photo2= obj.getProperty("PhotoPath2").toString().trim();
		this.Photo3= obj.getProperty("PhotoPath3").toString().trim();
		this.Photo4= obj.getProperty("PhotoPath4").toString().trim();


		this.VillageCode= obj.getProperty("VilageCode").toString().trim();

		if(ROLE.equalsIgnoreCase("PANADM")) {
			if(work_type.equalsIgnoreCase("PI")) {
				this.InspectorName = obj.getProperty("InspectorName").toString().trim();
				this.InspectorPost = obj.getProperty("Designation").toString().trim();
			}
		}
		else
		{
			this.InspectorName = obj.getProperty("InspectorName").toString().trim();
			this.InspectorPost = obj.getProperty("Designation").toString().trim();
		}
	}

	public static Class<WorkProgressReportGNProperty> getWorkProgressReportPayJal_CLASS() {
		return WorkProgressReportPayJal_CLASS;
	}

	public static void setUSER_CLASS(Class<WorkProgressReportGNProperty> uSER_CLASS) {
		WorkProgressReportPayJal_CLASS = uSER_CLASS;
	}

	public String getVillageCode() {
		return VillageCode;
	}

	public void setVillageCode(String villageCode) {
		VillageCode = villageCode;
	}

	public String getInspectorName() {
		return InspectorName;
	}

	public void setInspectorName(String inspectorName) {
		InspectorName = inspectorName;
	}

	public String getInspectorPost() {
		return InspectorPost;
	}

	public void setInspectorPost(String inspectorPost) {
		InspectorPost = inspectorPost;
	}

	public String getEntryDate() {
		return EntryDate;
	}

	public void setEntryDate(String entryDate) {
		EntryDate = entryDate;
	}

	public String getSchemeName() {
		return SchemeName;
	}

	public void setSchemeName(String schemeName) {
		SchemeName = schemeName;
	}

	public String getSchemeNum() {
		return SchemeNum;
	}

	public void setSchemeNum(String schemeNum) {
		SchemeNum = schemeNum;
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

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}

	public String getFYear() {
		return FYear;
	}

	public void setFYear(String FYear) {
		this.FYear = FYear;
	}

	public String getPanCode() {
		return PanCode;
	}

	public void setPanCode(String panCode) {
		PanCode = panCode;
	}

	public String getPanName() {
		return PanName;
	}

	public void setPanName(String panName) {
		PanName = panName;
	}

	public String getWardCode() {
		return WardCode;
	}

	public void setWardCode(String wardCode) {
		WardCode = wardCode;
	}

	public String getWardName() {
		return WardName;
	}

	public void setWardName(String wardName) {
		WardName = wardName;
	}

	public String getNischayCode() {
		return NischayCode;
	}

	public void setNischayCode(String nischayCode) {
		NischayCode = nischayCode;
	}

	public String getYojanaCode() {
		return YojanaCode;
	}

	public void setYojanaCode(String yojanaCode) {
		YojanaCode = yojanaCode;
	}

	public String getYojanaTypeNAME() {
		return YojanaTypeNAME;
	}

	public void setYojanaTypeNAME(String yojanaTypeNAME) {
		YojanaTypeNAME = yojanaTypeNAME;
	}

	public String getCurrentPhysicalStatus() {
		return CurrentPhysicalStatus;
	}

	public void setCurrentPhysicalStatus(String currentPhysicalStatus) {
		CurrentPhysicalStatus = currentPhysicalStatus;
	}

	public String getRoadType() {
		return RoadType;
	}

	public void setRoadType(String roadType) {
		RoadType = roadType;
	}

	public String getPathRoadType() {
		return PathRoadType;
	}

	public void setPathRoadType(String pathRoadType) {
		PathRoadType = pathRoadType;
	}

	public String getNaliType() {
		return NaliType;
	}

	public void setNaliType(String naliType) {
		NaliType = naliType;
	}

	public String getJalNikasi() {
		return JalNikasi;
	}

	public void setJalNikasi(String jalNikasi) {
		JalNikasi = jalNikasi;
	}

	public String getTotalRoadDistance_Length() {
		return TotalRoadDistance_Length;
	}

	public void setTotalRoadDistance_Length(String totalRoadDistance_Length) {
		TotalRoadDistance_Length = totalRoadDistance_Length;
	}

	public String getTotalRoadDistance_Breadth() {
		return TotalRoadDistance_Breadth;
	}

	public void setTotalRoadDistance_Breadth(String totalRoadDistance_Breadth) {
		TotalRoadDistance_Breadth = totalRoadDistance_Breadth;
	}

	public String getTotalRoadDistance_Fat() {
		return TotalRoadDistance_Fat;
	}

	public void setTotalRoadDistance_Fat(String totalRoadDistance_Fat) {
		TotalRoadDistance_Fat = totalRoadDistance_Fat;
	}

	public String getTotalPathRoadDistance_Breadth() {
		return TotalPathRoadDistance_Breadth;
	}

	public void setTotalPathRoadDistance_Breadth(String totalPathRoadDistance_Breadth) {
		TotalPathRoadDistance_Breadth = totalPathRoadDistance_Breadth;
	}

	public String getTotalNaliDistance_Length() {
		return TotalNaliDistance_Length;
	}

	public void setTotalNaliDistance_Length(String totalNaliDistance_Length) {
		TotalNaliDistance_Length = totalNaliDistance_Length;
	}

	public String getTotalNali_Breadth() {
		return TotalNali_Breadth;
	}

	public void setTotalNali_Breadth(String totalNali_Breadth) {
		TotalNali_Breadth = totalNali_Breadth;
	}

	public String getTotalNali_Gharai() {
		return TotalNali_Gharai;
	}

	public void setTotalNali_Gharai(String totalNali_Gharai) {
		TotalNali_Gharai = totalNali_Gharai;
	}

	public String getSokhtaKiSankhya() {
		return SokhtaKiSankhya;
	}

	public void setSokhtaKiSankhya(String sokhtaKiSankhya) {
		SokhtaKiSankhya = sokhtaKiSankhya;
	}

	public String getMittiKrya() {
		return MittiKrya;
	}

	public void setMittiKrya(String mittiKrya) {
		MittiKrya = mittiKrya;
	}

	public String getHugePipe() {
		return HugePipe;
	}

	public void setHugePipe(String hugePipe) {
		HugePipe = hugePipe;
	}

	public String getProjectCompleteionDate() {
		return ProjectCompleteionDate;
	}

	public void setProjectCompleteionDate(String projectCompleteionDate) {
		ProjectCompleteionDate = projectCompleteionDate;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getPhoto1() {
		return Photo1;
	}

	public void setPhoto1(String photo1) {
		Photo1 = photo1;
	}

	public String getPhoto2() {
		return Photo2;
	}

	public void setPhoto2(String photo2) {
		Photo2 = photo2;
	}

	public String getPhoto3() {
		return Photo3;
	}

	public void setPhoto3(String photo3) {
		Photo3 = photo3;
	}

	public String getPhoto4() {
		return Photo4;
	}

	public void setPhoto4(String photo4) {
		Photo4 = photo4;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
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

	public String getTotalAllotment() {
		return TotalAllotment;
	}

	public void setTotalAllotment(String totalAllotment) {
		TotalAllotment = totalAllotment;
	}

	public String getTotalExpenditure() {
		return TotalExpenditure;
	}

	public void setTotalExpenditure(String totalExpenditure) {
		TotalExpenditure = totalExpenditure;
	}

	public String getYujanaNumber() {
		return YujanaNumber;
	}

	public void setYujanaNumber(String yujanaNumber) {
		YujanaNumber = yujanaNumber;
	}

	public String getRequestDate() {
		return RequestDate;
	}

	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}

	public String getSubmitDate() {
		return SubmitDate;
	}

	public void setSubmitDate(String submitDate) {
		SubmitDate = submitDate;
	}

	public String getTechAcceptedAmount() {
		return TechAcceptedAmount;
	}

	public void setTechAcceptedAmount(String techAcceptedAmount) {
		TechAcceptedAmount = techAcceptedAmount;
	}

	public String getTechAcceptedDate() {
		return TechAcceptedDate;
	}

	public void setTechAcceptedDate(String techAcceptedDate) {
		TechAcceptedDate = techAcceptedDate;
	}

	public String getPanAcceptedAmount() {
		return PanAcceptedAmount;
	}

	public void setPanAcceptedAmount(String panAcceptedAmount) {
		PanAcceptedAmount = panAcceptedAmount;
	}

	public String getPanAcceptedDate() {
		return PanAcceptedDate;
	}

	public void setPanAcceptedDate(String panAcceptedDate) {
		PanAcceptedDate = panAcceptedDate;
	}

	public String getPanNischayPrbhariName() {
		return PanNischayPrbhariName;
	}

	public void setPanNischayPrbhariName(String panNischayPrbhariName) {
		PanNischayPrbhariName = panNischayPrbhariName;
	}

	public String getPanNischayPrbhariMobNum() {
		return PanNischayPrbhariMobNum;
	}

	public void setPanNischayPrbhariMobNum(String panNischayPrbhariMobNum) {
		PanNischayPrbhariMobNum = panNischayPrbhariMobNum;
	}

	public String getPanYojanaPrbhariName() {
		return PanYojanaPrbhariName;
	}

	public void setPanYojanaPrbhariName(String panYojanaPrbhariName) {
		PanYojanaPrbhariName = panYojanaPrbhariName;
	}

	public String getPanYojanaPrbhariMobNum() {
		return PanYojanaPrbhariMobNum;
	}

	public void setPanYojanaPrbhariMobNum(String panYojanaPrbhariMobNum) {
		PanYojanaPrbhariMobNum = panYojanaPrbhariMobNum;
	}

	public String getPrashakhiDeptName() {
		return PrashakhiDeptName;
	}

	public void setPrashakhiDeptName(String prashakhiDeptName) {
		PrashakhiDeptName = prashakhiDeptName;
	}

	public String getMaapPustNumprivate() {
		return MaapPustNumprivate;
	}

	public void setMaapPustNumprivate(String maapPustNumprivate) {
		MaapPustNumprivate = maapPustNumprivate;
	}

	public String getWorkStartingDate() {
		return WorkStartingDate;
	}

	public void setWorkStartingDate(String workStartingDate) {
		WorkStartingDate = workStartingDate;
	}

	public String getWorkEndDuration() {
		return WorkEndDuration;
	}

	public void setWorkEndDuration(String workEndDuration) {
		WorkEndDuration = workEndDuration;
	}
}
