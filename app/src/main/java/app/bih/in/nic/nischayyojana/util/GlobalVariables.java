package app.bih.in.nic.nischayyojana.util;

import android.location.Location;

import app.bih.in.nic.nischayyojana.entity.FarmerInfo;
import app.bih.in.nic.nischayyojana.entity.UserInfo;

public class GlobalVariables {

	public static UserInfo LoggedUser;
	public static FarmerInfo LoggedFarmer;

	public static boolean isOffline = false;
	public static boolean isOfflineGPS = false;

	public static String DivisionId = "";

	public static String Pid = "0";
	public static String area = "";
	public static String Last_Visited = "";
	public final static String TargetURL = "http://eservices.bih.nic.in/bcd/mUserReport.aspx";

	public static int fyearBack;
	public static String fySelectd = "";
	public static Location glocation=null;
	public static boolean isShownHelp = false;
	public static boolean isServiceStoped=false;
}
