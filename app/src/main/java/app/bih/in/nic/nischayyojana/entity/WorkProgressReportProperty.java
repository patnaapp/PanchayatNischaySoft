package app.bih.in.nic.nischayyojana.entity;

import android.preference.PreferenceManager;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

import app.bih.in.nic.nischayyojana.ui.PayJalActivity;

public class WorkProgressReportProperty implements Serializable{
	public static Class<WorkProgressReportProperty> WorkProgressReportPayJal_CLASS = WorkProgressReportProperty.class;
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
	private String isBoringStartedp;
	private String BoringDepth;
	private String ISIMarkBoring;
	private String UPVCPipeCMLNo;
	private String isMotorPumpStatus;
	private String ISIMarkMotorPump;
	private String MotorPumpCapacity;
	private String isStaggingDone;
	private String TypeOfStagging;
	private String HeightOfStagging;
	private String TenkiCapacity;
	private String TenkiQuantity;
	private String isVitranPranali;
	private String TotalLength;
	private String DepthFromSurfaceLevel;
	private String ISIMarkVitranPranali;
	private String CMLNumber;
	private String isElectrictyConnection;
	private String isElectConnection;
	private String ConsumerNumber;
	private String isGrihJalSunyojan;
	private String TotalHouseNum;
	private String WaterSupplyStatus;
	private String JalStumbhKaNirman;
	private String TypeOfPipe;
	private String FerulKaUpyog;
	private String isProjectCompleted;
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

	public WorkProgressReportProperty() {
		// TODO Auto-generated constructor stub
	}
	public WorkProgressReportProperty(SoapObject obj,String ROLE,String work_type) {



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
				this.TechAcceptedAmount = obj.getProperty("AmountOfTS").toString().trim();/////
				this.TechAcceptedDate = obj.getProperty("DateOfTS").toString().trim();/////
				this.PanAcceptedAmount = obj.getProperty("AmountOfAS").toString().trim();/////
				this.PanAcceptedDate = obj.getProperty("DateOfAS").toString().trim();//////
			}
		}

		this.PanYojanaPrbhariName= obj.getProperty("NameOfJE").toString().trim();
		this.PanYojanaPrbhariMobNum= obj.getProperty("MobNoOfJE").toString().trim();

		this.PanNischayPrbhariName= obj.getProperty("InChargeName").toString().trim();
		this.PanNischayPrbhariMobNum= obj.getProperty("IncahrgeMobNo").toString().trim();

		this.PrashakhiDeptName= obj.getProperty("DeptNameOfJE").toString().trim();
		this.MaapPustNumprivate= obj.getProperty("MBNO").toString().trim();
		this.WorkStartingDate= obj.getProperty("DateOfStartedWork").toString().trim();
		this.WorkEndDuration= obj.getProperty("FinaliseDuretion").toString().trim();

		if(ROLE.equalsIgnoreCase("PANADM")) {
			if(work_type.equalsIgnoreCase("PV")) {
				this.TotalAllotment = obj.getProperty("TotFundTrfForWard").toString().trim();//////
				this.TotalExpenditure = obj.getProperty("TotalVoucherAmtPayjal").toString().trim();//////
			}
		}

		this.CurrentPhysicalStatus= obj.getProperty("CurrentPhysicalStatus").toString().trim();

		//---------------------------------
		this.YojanaCode= obj.getProperty("SchemeNo").toString().trim();
		this.YojanaTypeNAME= obj.getProperty("SchemeName").toString().trim();

		//this.isBoringStartedp= obj.getProperty("DistrictCode").toString().trim();
		this.BoringDepth= obj.getProperty("BoaringDepth").toString().trim();
		if(BoringDepth.trim().length()>0 && !BoringDepth.equalsIgnoreCase("0"))
		{
			this.isBoringStartedp="Y";
		}
		else
		{
			this.isBoringStartedp="N";
		}
		this.ISIMarkBoring= obj.getProperty("BoringISIMARK").toString().trim();
		this.UPVCPipeCMLNo= obj.getProperty("BoringCMLNo").toString().trim();

		//this.isMotorPumpStatus= obj.getProperty("DistrictCode").toString().trim();
		this.MotorPumpCapacity= obj.getProperty("MoterCapacity").toString().trim();
		this.ISIMarkMotorPump= obj.getProperty("MoterISIMark").toString().trim();
		if(MotorPumpCapacity.trim().length()>0 && !MotorPumpCapacity.equalsIgnoreCase("0"))
		{
			this.isMotorPumpStatus="Y";
		}
		else
		{
			this.isMotorPumpStatus="N";
		}



		//this.isStaggingDone= obj.getProperty("DistrictCode").toString().trim();
		this.TypeOfStagging= obj.getProperty("StagingType").toString().trim();
		this.TenkiCapacity= obj.getProperty("TankCapacity").toString().trim();
		if(TenkiCapacity.trim().length()>0 && !TenkiCapacity.equalsIgnoreCase("0"))
		{
			this.isStaggingDone="Y";
		}
		else
		{
			this.isStaggingDone="N";
		}
		this.HeightOfStagging= obj.getProperty("StagingHeight").toString().trim();

		this.TenkiQuantity= obj.getProperty("NoOfTank").toString().trim();

		//this.isVitranPranali= obj.getProperty("DistrictCode").toString().trim();
		this.TotalLength= obj.getProperty("PipeLenth").toString().trim();
		if(TotalLength.trim().length()>0 && !TotalLength.equalsIgnoreCase("0"))
		{
			this.isVitranPranali="Y";
		}
		else
		{
			this.isVitranPranali="N";
		}
		this.DepthFromSurfaceLevel= obj.getProperty("PipeDepth").toString().trim();
		this.ISIMarkVitranPranali= obj.getProperty("PipeIsIMark").toString().trim();
		this.CMLNumber= obj.getProperty("PipeCMLNO").toString().trim();

		this.isElectrictyConnection= obj.getProperty("IsElectricityConnection").toString().trim();
		this.isElectConnection= obj.getProperty("IsElectricityConnection").toString().trim();
		this.ConsumerNumber= obj.getProperty("CunsumerNo").toString().trim();

		//this.isGrihJalSunyojan= obj.getProperty("DistrictCode").toString().trim();
		this.TotalHouseNum= obj.getProperty("NoOfConnectedHouse").toString().trim();
		if(TotalHouseNum.trim().length()>0 && !TotalHouseNum.equalsIgnoreCase("0"))
		{
			this.isGrihJalSunyojan="Y";
		}
		else
		{
			this.isGrihJalSunyojan="N";
		}
		this.WaterSupplyStatus= obj.getProperty("WaterSupplyStatus").toString().trim();
		this.JalStumbhKaNirman= obj.getProperty("jalSamtambh").toString().trim();
		this.TypeOfPipe= obj.getProperty("TypeyOfPIpe").toString().trim();
		this.FerulKaUpyog= obj.getProperty("Farul").toString().trim();

		this.isProjectCompleted= obj.getProperty("CurrentPhysicalStatus").toString().trim();
		this.ProjectCompleteionDate= obj.getProperty("IsCompleted").toString().trim();
		this.Remarks= obj.getProperty("Remarks").toString().trim();
		this.CreatedBy= obj.getProperty("EntryBy").toString().trim();
		this.CreatedDate= obj.getProperty("EntryDate").toString().trim();

		this.Photo1= obj.getProperty("PhotoPath1").toString().trim();
		this.Photo2= obj.getProperty("PhotoPath2").toString().trim();
		this.Photo3= obj.getProperty("PhotoPath3").toString().trim();
		this.Photo4= obj.getProperty("PhotoPath4").toString().trim();

		//this.Latitude= obj.getProperty("DistrictCode").toString().trim();
		//this.Longitude= obj.getProperty("DistrictCode").toString().trim();



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

	public static Class<WorkProgressReportProperty> getWorkProgressReportPayJal_CLASS() {
		return WorkProgressReportPayJal_CLASS;
	}

	public static void setUSER_CLASS(Class<WorkProgressReportProperty> uSER_CLASS) {
		WorkProgressReportPayJal_CLASS = uSER_CLASS;
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

	public String getIsBoringStartedp() {
		return isBoringStartedp;
	}

	public void setIsBoringStartedp(String isBoringStartedp) {
		this.isBoringStartedp = isBoringStartedp;
	}

	public String getBoringDepth() {
		return BoringDepth;
	}

	public void setBoringDepth(String boringDepth) {
		BoringDepth = boringDepth;
	}

	public String getISIMarkBoring() {
		return ISIMarkBoring;
	}

	public void setISIMarkBoring(String ISIMarkBoring) {
		this.ISIMarkBoring = ISIMarkBoring;
	}

	public String getUPVCPipeCMLNo() {
		return UPVCPipeCMLNo;
	}

	public void setUPVCPipeCMLNo(String UPVCPipeCMLNo) {
		this.UPVCPipeCMLNo = UPVCPipeCMLNo;
	}

	public String getIsMotorPumpStatus() {
		return isMotorPumpStatus;
	}

	public void setIsMotorPumpStatus(String isMotorPumpStatus) {
		this.isMotorPumpStatus = isMotorPumpStatus;
	}

	public String getISIMarkMotorPump() {
		return ISIMarkMotorPump;
	}

	public void setISIMarkMotorPump(String ISIMarkMotorPump) {
		this.ISIMarkMotorPump = ISIMarkMotorPump;
	}

	public String getMotorPumpCapacity() {
		return MotorPumpCapacity;
	}

	public void setMotorPumpCapacity(String motorPumpCapacity) {
		MotorPumpCapacity = motorPumpCapacity;
	}

	public String getIsStaggingDone() {
		return isStaggingDone;
	}

	public void setIsStaggingDone(String isStaggingDone) {
		this.isStaggingDone = isStaggingDone;
	}

	public String getTypeOfStagging() {
		return TypeOfStagging;
	}

	public void setTypeOfStagging(String typeOfStagging) {
		TypeOfStagging = typeOfStagging;
	}

	public String getHeightOfStagging() {
		return HeightOfStagging;
	}

	public void setHeightOfStagging(String heightOfStagging) {
		HeightOfStagging = heightOfStagging;
	}

	public String getTenkiCapacity() {
		return TenkiCapacity;
	}

	public void setTenkiCapacity(String tenkiCapacity) {
		TenkiCapacity = tenkiCapacity;
	}

	public String getTenkiQuantity() {
		return TenkiQuantity;
	}

	public void setTenkiQuantity(String tenkiQuantity) {
		TenkiQuantity = tenkiQuantity;
	}

	public String getIsVitranPranali() {
		return isVitranPranali;
	}

	public void setIsVitranPranali(String isVitranPranali) {
		this.isVitranPranali = isVitranPranali;
	}

	public String getTotalLength() {
		return TotalLength;
	}

	public void setTotalLength(String totalLength) {
		TotalLength = totalLength;
	}

	public String getDepthFromSurfaceLevel() {
		return DepthFromSurfaceLevel;
	}

	public void setDepthFromSurfaceLevel(String depthFromSurfaceLevel) {
		DepthFromSurfaceLevel = depthFromSurfaceLevel;
	}

	public String getISIMarkVitranPranali() {
		return ISIMarkVitranPranali;
	}

	public void setISIMarkVitranPranali(String ISIMarkVitranPranali) {
		this.ISIMarkVitranPranali = ISIMarkVitranPranali;
	}

	public String getCMLNumber() {
		return CMLNumber;
	}

	public void setCMLNumber(String CMLNumber) {
		this.CMLNumber = CMLNumber;
	}

	public String getIsElectrictyConnection() {
		return isElectrictyConnection;
	}

	public void setIsElectrictyConnection(String isElectrictyConnection) {
		this.isElectrictyConnection = isElectrictyConnection;
	}

	public String getIsElectConnection() {
		return isElectConnection;
	}

	public void setIsElectConnection(String isElectConnection) {
		this.isElectConnection = isElectConnection;
	}

	public String getConsumerNumber() {
		return ConsumerNumber;
	}

	public void setConsumerNumber(String consumerNumber) {
		ConsumerNumber = consumerNumber;
	}

	public String getIsGrihJalSunyojan() {
		return isGrihJalSunyojan;
	}

	public void setIsGrihJalSunyojan(String isGrihJalSunyojan) {
		this.isGrihJalSunyojan = isGrihJalSunyojan;
	}

	public String getTotalHouseNum() {
		return TotalHouseNum;
	}

	public void setTotalHouseNum(String totalHouseNum) {
		TotalHouseNum = totalHouseNum;
	}

	public String getWaterSupplyStatus() {
		return WaterSupplyStatus;
	}

	public void setWaterSupplyStatus(String waterSupplyStatus) {
		WaterSupplyStatus = waterSupplyStatus;
	}

	public String getJalStumbhKaNirman() {
		return JalStumbhKaNirman;
	}

	public void setJalStumbhKaNirman(String jalStumbhKaNirman) {
		JalStumbhKaNirman = jalStumbhKaNirman;
	}

	public String getTypeOfPipe() {
		return TypeOfPipe;
	}

	public void setTypeOfPipe(String typeOfPipe) {
		TypeOfPipe = typeOfPipe;
	}

	public String getFerulKaUpyog() {
		return FerulKaUpyog;
	}

	public void setFerulKaUpyog(String ferulKaUpyog) {
		FerulKaUpyog = ferulKaUpyog;
	}

	public String getIsProjectCompleted() {
		return isProjectCompleted;
	}

	public void setIsProjectCompleted(String isProjectCompleted) {
		this.isProjectCompleted = isProjectCompleted;
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
