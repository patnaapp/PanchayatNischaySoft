package app.bih.in.nic.nischayyojana.db;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.MODEOFPAYMENT;
import app.bih.in.nic.nischayyojana.entity.PANCHAYAT;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.UserInfo;
import app.bih.in.nic.nischayyojana.entity.Versioninfo;
import app.bih.in.nic.nischayyojana.entity.VillageData;
import app.bih.in.nic.nischayyojana.entity.WardVisitingReportProperty;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportGNProperty;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportProperty;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;

public class WebServiceHelper {

	//adm96397
	//8092

	public static final String SERVICENAMESPACE = "http://prdnischaysoft.bih.nic.in/";
	public static final String SERVICEURL = "http://prdnischaysoft.bih.nic.in/MMNischayaYojnaWebService.asmx";

//	public static final String SERVICENAMESPACE = "http://nicapp.bih.nic.in/";
//	public static final String SERVICEURL = "http://nicapp.bih.nic.in/MMNischayaYojnaWebService.asmx ";

//	public static final String SERVICENAMESPACE = "http://10.133.20.184/";
//	public static final String SERVICEURL = "http://10.133.20.184/RuralDevelopmentPondLakeWebService.asmx";

	public static final String AUTHENTICATE_METHOD = "Authenticate";
	public static final String APPVERSION_METHOD = "getAppLatest";
	public static final String INSERT_VoucherEntry = "VoucherEntry";


	public static final String INSERT_PayJalPhysicalVer = "insertPhysicalStsutusPayjal";
	public static final String INSERT_PhysicalStatusForNALIGALI = "insertPhysicalStsutusForNALIGALI";

	public static final String INSERT_PajJalInspectionData = "insertPhysicalInspectionForPAYJALMobNew";
	public static final String INSERT_GaliNaaliInspectionData = "insertPhysicalInspectionForNALIGALI";


	public static final String FYEAR_LIST_METHOD = "FyearList";

	//private static final String SCHEME_LIST_METHOD = "GetSchemeList";

	public static final String WARD_LIST_METHOD="getWardLstNew";
	public static final String PANCHAYAT_LIST_METHOD="getPanchayatNew";
	public static final String NISCHAY_LIST_METHOD="getNischayLst";
	public static final String YOJANA_LIST_METHOD="getSchemeLstNew";
	public static final String VILLAGE_LIST_METHOD="getVillageListNew";
	public static final String PAYMODE_LIST_METHOD="getPaymentModeLst";

	public static final String GET_PAY_JAL_RECORD="getPhysicalStsutusPayjalLst";
	public static final String GET_GALI_NAALI_RECORD="getPhysicalStsutusNaliGaliLst";

	public static final String GET_PAY_JAL_INS_RECORD="getPhysicalInspectionPayjalList";
	public static final String GET_GALI_NAALI_INS_RECORD="getPhysicalInspectionStsutusNaliGaliLst";
	public static final String WARD_VISITED_PAYJAL_RECORD="getNumbTimesWardVisitedPayjalList";
	public static final String WARD_VISITED_GALINAALI_RECORD="getNumbTimesWardVisitedNaliGaliList";

	public static UserInfo Login(String User_ID, String Pwd) {
		try {
			SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);

			request.addProperty("UserID", User_ID);
			request.addProperty("Password", Pwd);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			envelope.addMapping(SERVICENAMESPACE, UserInfo.USER_CLASS.getSimpleName(), UserInfo.USER_CLASS);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);

			Object result = envelope.getResponse();
			// response = envelope.getResponse().toString();

			if (result != null) {

				return new UserInfo((SoapObject) result);
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}

	public static ArrayList<Fyear> loadFYearList() {

		SoapObject request = new SoapObject(SERVICENAMESPACE, FYEAR_LIST_METHOD);

		// request.addProperty("DivisionId", Block_Code);

		SoapObject res1;
		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE, Fyear.FYEAR_CLASS.getSimpleName(), Fyear.FYEAR_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + FYEAR_LIST_METHOD, envelope);

			res1 = (SoapObject) envelope.getResponse();

		}catch (Exception e) {
			return null;
		}

		int TotalProperty = res1.getPropertyCount();
		ArrayList<Fyear> FyearList = new ArrayList<Fyear>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					Fyear fyear = new Fyear(final_object);
					FyearList.add(fyear);
				}
			} else
				return FyearList;
		}

		return FyearList;

	}


	public static String UploadVoucherEntry(String... param) {
		String response = "";
		try {

			SoapObject request = new SoapObject(SERVICENAMESPACE, INSERT_VoucherEntry);

			request.addProperty("_Type", param[1]);
			request.addProperty("_WardNo", param[0]);
			request.addProperty("_SchemeId", param[2]);
			request.addProperty("_PaymentReceverName", param[3]);

			request.addProperty("_PaymentReceverMobNo", param[4]);
			request.addProperty("_PaymentHeadName", param[5]); //purpose of payment

			request.addProperty("_PaymentMode", param[6]);

			request.addProperty("_Amount", param[9]);
			request.addProperty("_TransactionNo", param[7]);

			request.addProperty("_PaymentDate", param[8]);
			request.addProperty("_EntryDate", param[11]);
			request.addProperty("_Status", param[12]);
			request.addProperty("_FYear", param[16]);//----N

			request.addProperty("_APPVERSION", param[13]);
			request.addProperty("_DEVICETYPE", param[14]);

			request.addProperty("_Remarks", param[15]); //---N
			request.addProperty("_ENTRYBY", param[10]); //--N



			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			Log.d("TAG", envelope.toString());
			try {

				androidHttpTransport.call(SERVICENAMESPACE + INSERT_VoucherEntry, envelope);
				response = envelope.getResponse().toString();

			} catch (Exception e) {
				response = e.toString();
				e.printStackTrace();

				if (envelope.bodyIn instanceof SoapObject) { // SoapObject =
					// SUCCESS
					SoapObject soapObject = (SoapObject) envelope.bodyIn;
					response = parseSOAPResponse(soapObject);
				} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault
					// = FAILURE
					SoapFault soapFault = (SoapFault) envelope.bodyIn;
					// throw new Exception(soapFault.getMessage());
					response = soapFault.getMessage();
				}
			}

		} catch (Exception e) {
			response = e.toString();
			e.printStackTrace();
			// return "0";
		}
		return response;
	}

	public static String UploadPayJalPhysicalVer(Context context,String... param) {
		String response = "";
		try {
			SoapObject request;
			String work_type= PreferenceManager.getDefaultSharedPreferences(context).getString("WORK_TYPE", "");
			if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {

				if(work_type.equalsIgnoreCase("PI")) {
					request = new SoapObject(SERVICENAMESPACE, INSERT_PajJalInspectionData);
				}
				else
				{
					request = new SoapObject(SERVICENAMESPACE, INSERT_PayJalPhysicalVer);
				}
			}
			else
			{
				request = new SoapObject(SERVICENAMESPACE, INSERT_PajJalInspectionData);
			}

			request.addProperty("SchemeId", param[1]);
			request.addProperty("BoaringDepth", param[2]);
			request.addProperty("BoringISIMARK", param[3]);
			request.addProperty("BoringCMLNo", param[4]);
			request.addProperty("MoterISIMark", param[5]);
			request.addProperty("MoterCapacity", param[6]);
			request.addProperty("StagingType", param[7]);
			request.addProperty("StagingHeight", param[8]);
			request.addProperty("TankCapacity", param[9]);
			request.addProperty("NoOfTank", param[10]);
			// request.addProperty("_PacsId", param[4]);
			request.addProperty("PipeLenth", param[11]);

			request.addProperty("PipeDepth", param[12]);
			request.addProperty("PipeIsIMark", param[13]);

			request.addProperty("PipeCMLNO", param[14]);
			request.addProperty("IsElectricityConnection", param[15]);

			request.addProperty("CunsumerNo", param[16]);
			request.addProperty("NoOfConnectedHouse", param[17]);
			request.addProperty("WaterSupplyStatus", param[18]);
			request.addProperty("jalSamtambh", param[19]);
			request.addProperty("TypeyOfPIpe", param[20]);
			request.addProperty("Farul", param[21]);
			request.addProperty("CurrentPhysicalStatus", param[22]);

			request.addProperty("Remarks", param[23]);
			request.addProperty("EntryBy", param[24]);

			request.addProperty("EntryMode", param[25]);
			request.addProperty("IsCompletedDate", param[26]);

			request.addProperty("PhotoPath1", param[27]);
			request.addProperty("PhotoPath2", param[28]);
			request.addProperty("PhotoPath3", param[29]);
			request.addProperty("PhotoPath4", param[30]);
			request.addProperty("Latitude", param[31]);
			request.addProperty("Longitude", param[32]);

			request.addProperty("APPVERSION", param[33]);
			request.addProperty("DEVICETYPE", param[34]);



			if(!CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM"))
			{
				request.addProperty("SchemeName", param[37]);
				request.addProperty("InspectorName", param[35]);
				request.addProperty("Designation", param[36]);

				request.addProperty("DistCode", param[38]);
				request.addProperty("BlockCode", param[39]);
				request.addProperty("PanchayatCode", param[40]);
				request.addProperty("WardCode", param[41]);
				request.addProperty("VilageCode", param[42]);

			}
			else if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM"))
			{
				if(work_type.equalsIgnoreCase("PI"))
				{
					request.addProperty("SchemeName", "");
					request.addProperty("InspectorName", param[35]);
					request.addProperty("Designation", param[36]);

					request.addProperty("DistCode", param[38]);
					request.addProperty("BlockCode", param[39]);
					request.addProperty("PanchayatCode", param[40]);
					request.addProperty("WardCode", param[41]);
					request.addProperty("VilageCode", param[42]);
					request.addProperty("OthervillageName", param[45]);
					request.addProperty("OtherwardName", param[46]);
					request.addProperty("IotDevice", param[47]);
					request.addProperty("OtherYojnaName",param[37]);
					request.addProperty("OtherPanchayatName", "");
				}
				else if(work_type.equalsIgnoreCase("PV"))
				{
					//request.addProperty("SchemeName", param[37]);
					//request.addProperty("InspectorName", param[35]);
					//request.addProperty("Designation", param[36]);

					//request.addProperty("DistCode", param[38]);
					//request.addProperty("BlockCode", param[39]);
					//request.addProperty("PanchayatCode", param[40]);
					//request.addProperty("WardCode", param[41]);
					request.addProperty("VilageCode", param[42]);
				}
			}
			request.addProperty("MbNub", param[43]);  // SEND Y / N
			request.addProperty("IsMbuploaded", param[43]); // SEND Y / N
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			Log.d("TAG", envelope.toString());
			try {

				if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {
					if(work_type.equalsIgnoreCase("PI")) {
						androidHttpTransport.call(SERVICENAMESPACE + INSERT_PajJalInspectionData, envelope);
					}
					else
					{
						androidHttpTransport.call(SERVICENAMESPACE + INSERT_PayJalPhysicalVer, envelope);
					}
				}
				else
				{
					androidHttpTransport.call(SERVICENAMESPACE + INSERT_PajJalInspectionData, envelope);
				}

				response = envelope.getResponse().toString();

			} catch (Exception e) {
				response = e.toString();
				e.printStackTrace();

				if (envelope.bodyIn instanceof SoapObject) { // SoapObject =
					// SUCCESS
					SoapObject soapObject = (SoapObject) envelope.bodyIn;
					response = parseSOAPResponse(soapObject);
				} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault
					// = FAILURE
					SoapFault soapFault = (SoapFault) envelope.bodyIn;
					// throw new Exception(soapFault.getMessage());
					response = soapFault.getMessage();
				}
			}

		} catch (Exception e) {
			response = e.toString();
			e.printStackTrace();
			// return "0";
		}
		return response;
	}

	public static String UploadPendingGaliNaaliData(Context context,String... param) {
		String response = "";
		try {
			String work_type= PreferenceManager.getDefaultSharedPreferences(context).getString("WORK_TYPE", "");
			SoapObject request;

			if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {
				if(work_type.equalsIgnoreCase("PI")) {
					request = new SoapObject(SERVICENAMESPACE, INSERT_GaliNaaliInspectionData);
				}
				else
				{
					request = new SoapObject(SERVICENAMESPACE, INSERT_PhysicalStatusForNALIGALI);
				}
			}
			else
			{
				request = new SoapObject(SERVICENAMESPACE, INSERT_GaliNaaliInspectionData);
			}
			request.addProperty("SchemeId", param[1]);

			request.addProperty("TypeOfPavement", param[2]);

			request.addProperty("TotalLenth", param[3]);//TotalRoadDistance_Length
			request.addProperty("Roadlength", param[4]);//TotalRoadDistance_Breadth
			request.addProperty("RoadThick", param[5]);//TotalRoadDistance_Fat

			request.addProperty("PatriType", param[6]);
			request.addProperty("Patrilength", param[7]); //TotalPathRoadDistance_Breadth
			request.addProperty("NaliType", param[8]);

			request.addProperty("NaliLength", param[9]);
			request.addProperty("NaliWidth", param[10]);
			request.addProperty("NaliDepth", param[11]);
			request.addProperty("SoakPitConstructed", param[12]);
			request.addProperty("SoailWork", param[13]);
			request.addProperty("DraunageFacilities", param[14]);
			request.addProperty("NoOfHugePipe", param[15]);
			request.addProperty("CurrentPhysicalStatus", param[16]);
			request.addProperty("Remarks", param[18]);
			request.addProperty("EntryBy", param[19]);
			request.addProperty("EntryMode", "M");
			String comdate=null;
			request.addProperty("IsCompletedDate", param[17]);

			request.addProperty("PhotoPath1", param[21]);
			request.addProperty("PhotoPath2", param[22]);
			request.addProperty("PhotoPath3", param[23]);
			request.addProperty("PhotoPath4", param[24]);
			request.addProperty("Latitude", param[25]);
			request.addProperty("Longitude", param[26]);

			request.addProperty("APPVERSION", param[27]);
			request.addProperty("DEVICETYPE", param[28]);


			if(!CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM"))
			{
				request.addProperty("SchemeName", param[31]);
				request.addProperty("InspectorName", param[29]);
				request.addProperty("Designation", param[30]);

				request.addProperty("DistCode", param[32]);
				request.addProperty("BlockCode", param[33]);
				request.addProperty("PanchayatCode", param[34]);
				request.addProperty("WardCode", param[35]);
				request.addProperty("VilageCode", param[36]);

			}
			else if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM"))
			{
				if(work_type.equalsIgnoreCase("PI"))
				{
					request.addProperty("SchemeName", param[31]);
					request.addProperty("InspectorName", param[29]);
					request.addProperty("Designation", param[30]);

					request.addProperty("DistCode", param[32]);
					request.addProperty("BlockCode", param[33]);
					request.addProperty("PanchayatCode", param[34]);
					request.addProperty("WardCode", param[35]);
					request.addProperty("VilageCode", param[36]);
				}
				else if(work_type.equalsIgnoreCase("PV"))
				{
					//request.addProperty("SchemeName", param[37]);
					//request.addProperty("InspectorName", param[35]);
					//request.addProperty("Designation", param[36]);

					//request.addProperty("DistCode", param[38]);
					//request.addProperty("BlockCode", param[39]);
					//request.addProperty("PanchayatCode", param[40]);
					//request.addProperty("WardCode", param[41]);
					request.addProperty("VilageCode", param[36]);
				}
			}
			request.addProperty("MbNub", param[37]);   // SEND Y / N
			request.addProperty("IsMbuploaded", param[37]);    // SEND Y / N

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			Log.d("TAG", envelope.toString());
			try {

				if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {
					if(work_type.equalsIgnoreCase("PI")) {
						androidHttpTransport.call(SERVICENAMESPACE + INSERT_GaliNaaliInspectionData, envelope);
					}
					else
					{
						androidHttpTransport.call(SERVICENAMESPACE + INSERT_PhysicalStatusForNALIGALI, envelope);
					}
				}
				else
				{
					androidHttpTransport.call(SERVICENAMESPACE + INSERT_GaliNaaliInspectionData, envelope);
				}

				response = envelope.getResponse().toString();

			} catch (Exception e) {
				response = e.toString();
				e.printStackTrace();

				if (envelope.bodyIn instanceof SoapObject) { // SoapObject =
					// SUCCESS
					SoapObject soapObject = (SoapObject) envelope.bodyIn;
					response = parseSOAPResponse(soapObject);
				} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault
					// = FAILURE
					SoapFault soapFault = (SoapFault) envelope.bodyIn;
					// throw new Exception(soapFault.getMessage());
					response = soapFault.getMessage();
				}
			}

		} catch (Exception e) {
			response = e.toString();
			e.printStackTrace();
			// return "0";
		}
		return response;
	}


	private static String parseSOAPResponse(SoapObject response) {
		String res = null;
		SoapObject result = (SoapObject) response.getProperty("InsertJFMWorkResult");
		res = result.toString();
		// ...
		// more code in here to populate the CityForecastBO object from the
		// response object (illustrated in the steps below)

		return res;
	}

	public static Versioninfo CheckVersion(String imei, String version) {

		SoapObject request = new SoapObject(SERVICENAMESPACE, APPVERSION_METHOD);

		request.addProperty("IMEI", "0000000000");
		request.addProperty("Ver", version);
		Versioninfo versioninfo;
		SoapObject res1;
		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			envelope.addMapping(SERVICENAMESPACE, Versioninfo.Versioninfo_CLASS.getSimpleName(), Versioninfo.Versioninfo_CLASS);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD, envelope);

			res1 = (SoapObject) envelope.getResponse();

			int TotalProperty = res1.getPropertyCount();

			// Object property = res1.getProperty(0);
			SoapObject final_object = (SoapObject) res1.getProperty(0);
			versioninfo = new Versioninfo(final_object);

		} catch (Exception e) {

			return null;
		}
		return versioninfo;

	}

	//=======================BY SHEK==============================


	public static ArrayList<ward> loadWardList(String Panchayat_Code) {

		String PACS_VM_LIST_METHOD=null;


		SoapObject request = new SoapObject(SERVICENAMESPACE,
				WARD_LIST_METHOD);

		request.addProperty("PanchayatCode", Panchayat_Code);


		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,ward.ward_CLASS.getSimpleName(), ward.ward_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + WARD_LIST_METHOD,
					envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<ward> pvmArrayList = new ArrayList<ward>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					ward vill = new ward(final_object,Panchayat_Code);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}

	public static ArrayList<PANCHAYAT> loadPanList(String Block_Code) {


		SoapObject request = new SoapObject(SERVICENAMESPACE,
				PANCHAYAT_LIST_METHOD);

		request.addProperty("_BlockCode", Block_Code);


		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,PANCHAYAT.PANCHAYAT_CLASS.getSimpleName(), PANCHAYAT.PANCHAYAT_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + PANCHAYAT_LIST_METHOD,
					envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<PANCHAYAT> pvmArrayList = new ArrayList<PANCHAYAT>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					PANCHAYAT vill = new PANCHAYAT(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}
	public static ArrayList<WardVisitingReportProperty> loadWardVisitingList(String panCOde, String userID, String scheme) {

		SoapObject request;
		if(scheme.equalsIgnoreCase("1")) {
			request = new SoapObject(SERVICENAMESPACE, WARD_VISITED_PAYJAL_RECORD);
		}
		else
		{
			request = new SoapObject(SERVICENAMESPACE, WARD_VISITED_GALINAALI_RECORD);
		}

		request.addProperty("PanchayatCode", panCOde);
		request.addProperty("UserId", userID);


		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,WardVisitingReportProperty.WardVisitingReport_CLASS.getSimpleName(), WardVisitingReportProperty.WardVisitingReport_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					SERVICEURL);

			if(scheme.equalsIgnoreCase("1")) {
				androidHttpTransport.call(SERVICENAMESPACE + WARD_VISITED_PAYJAL_RECORD,	envelope);
			}
			else
			{
				androidHttpTransport.call(SERVICENAMESPACE + WARD_VISITED_GALINAALI_RECORD,	envelope);
			}
			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<WardVisitingReportProperty> pvmArrayList = new ArrayList<WardVisitingReportProperty>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					WardVisitingReportProperty vill = new WardVisitingReportProperty(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}
	public static ArrayList<SCHEME> loadNischayList() {

		String PACS_VM_LIST_METHOD=null;


		SoapObject request = new SoapObject(SERVICENAMESPACE,	NISCHAY_LIST_METHOD);



		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,SCHEME.SCHEME_CLASS.getSimpleName(), SCHEME.SCHEME_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + NISCHAY_LIST_METHOD,
					envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<SCHEME> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					SCHEME vill = new SCHEME(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}

	public static ArrayList<YOJANA> loadYojanaList(String pancode) {


		SoapObject request = new SoapObject(SERVICENAMESPACE,YOJANA_LIST_METHOD);

		request.addProperty("PanchayatCode",pancode);

		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,ward.ward_CLASS.getSimpleName(), ward.ward_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + YOJANA_LIST_METHOD, envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<YOJANA> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					YOJANA vill = new YOJANA(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}
	public static ArrayList<VillageData> loadVillageList(String pancode) {

		SoapObject request = new SoapObject(SERVICENAMESPACE,	VILLAGE_LIST_METHOD);

		request.addProperty("PanchayatCode",pancode);

		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,VillageData.VillageData_CLASS.getSimpleName(), VillageData.VillageData_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(	SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + VILLAGE_LIST_METHOD,envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<VillageData> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					VillageData vill = new VillageData(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}
	public static ArrayList<MODEOFPAYMENT> loadPaymentModeList() {


		SoapObject request = new SoapObject(SERVICENAMESPACE,	PAYMODE_LIST_METHOD);

		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,ward.ward_CLASS.getSimpleName(), ward.ward_CLASS);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					SERVICEURL);
			androidHttpTransport.call(SERVICENAMESPACE + PAYMODE_LIST_METHOD,
					envelope);

			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<MODEOFPAYMENT> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					MODEOFPAYMENT vill = new MODEOFPAYMENT(final_object);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}

	public static ArrayList<WorkProgressReportProperty> getPayJalRecords(Context context,String pcode,String userid) {

		String work_type= PreferenceManager.getDefaultSharedPreferences(context).getString("WORK_TYPE", "");
		String ROLE=CommonPref.getUserDetails(context).get_Role();
		SoapObject request;
		//= new SoapObject(SERVICENAMESPACE,	GET_PAY_JAL_RECORD);
		if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {

			if(work_type.equalsIgnoreCase("PI"))
			{
				request = new SoapObject(SERVICENAMESPACE, GET_PAY_JAL_INS_RECORD);
			}
			else
			{
				request = new SoapObject(SERVICENAMESPACE, GET_PAY_JAL_RECORD);
			}
		}
		else
		{
			request = new SoapObject(SERVICENAMESPACE, GET_PAY_JAL_INS_RECORD);
		}
		request.addProperty("PanchayatCode",pcode);
		request.addProperty("UserId",userid);

		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,WorkProgressReportProperty.getWorkProgressReportPayJal_CLASS().getSimpleName(), WorkProgressReportProperty.getWorkProgressReportPayJal_CLASS());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(	SERVICEURL);


			if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {

				if(work_type.equalsIgnoreCase("PI")) {
					androidHttpTransport.call(SERVICENAMESPACE + GET_PAY_JAL_INS_RECORD,	envelope);
				}
				else
				{
					androidHttpTransport.call(SERVICENAMESPACE + GET_PAY_JAL_RECORD,	envelope);
				}
			}
			else
			{
				androidHttpTransport.call(SERVICENAMESPACE + GET_PAY_JAL_INS_RECORD,	envelope);
			}


			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<WorkProgressReportProperty> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					WorkProgressReportProperty vill = new WorkProgressReportProperty(final_object,ROLE,work_type);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}

	public static ArrayList<WorkProgressReportGNProperty> getGaliNaaliRecords(Context context,String pcode,String userid) {

		String work_type= PreferenceManager.getDefaultSharedPreferences(context).getString("WORK_TYPE", "");
		String ROLE=CommonPref.getUserDetails(context).get_Role();
		SoapObject request;
		//= new SoapObject(SERVICENAMESPACE,	GET_GALI_NAALI_RECORD);
		if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {

			if(work_type.equalsIgnoreCase("PI")) {
				request = new SoapObject(SERVICENAMESPACE, GET_GALI_NAALI_INS_RECORD);
			}
			else
			{
				request = new SoapObject(SERVICENAMESPACE, GET_GALI_NAALI_RECORD);
			}
		}
		else
		{
			request = new SoapObject(SERVICENAMESPACE, GET_GALI_NAALI_INS_RECORD);
		}
		request.addProperty("PanchayatCode",pcode);
		request.addProperty("UserId",userid);


		SoapObject res1;
		try {

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(SERVICENAMESPACE,WorkProgressReportGNProperty.getWorkProgressReportPayJal_CLASS().getSimpleName(), WorkProgressReportGNProperty.getWorkProgressReportPayJal_CLASS());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(	SERVICEURL);



			if(CommonPref.getUserDetails(context).get_Role().equalsIgnoreCase("PANADM")) {

				if(work_type.equalsIgnoreCase("PI")) {
					androidHttpTransport.call(SERVICENAMESPACE + GET_GALI_NAALI_INS_RECORD,	envelope);
				}
				else
				{
					androidHttpTransport.call(SERVICENAMESPACE + GET_GALI_NAALI_RECORD,	envelope);
				}
			}
			else
			{
				androidHttpTransport.call(SERVICENAMESPACE + GET_GALI_NAALI_INS_RECORD,	envelope);
			}


			res1 = (SoapObject) envelope.getResponse();

		} catch (Exception e) {
			Log.e("Exception1: ",e.getLocalizedMessage());
			Log.e("Exception2: ",e.getMessage());
			return null;
		}
		int TotalProperty = res1.getPropertyCount();

		ArrayList<WorkProgressReportGNProperty> pvmArrayList = new ArrayList<>();

		for (int ii = 0; ii < TotalProperty; ii++) {
			if (res1.getProperty(ii) != null) {
				Object property = res1.getProperty(ii);
				if (property instanceof SoapObject) {
					SoapObject final_object = (SoapObject) property;
					WorkProgressReportGNProperty vill = new WorkProgressReportGNProperty(final_object,ROLE,work_type);
					pvmArrayList.add(vill);
				}
			} else
				return pvmArrayList;
		}


		return pvmArrayList;
	}
}
