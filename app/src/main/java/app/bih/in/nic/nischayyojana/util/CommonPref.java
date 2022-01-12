package app.bih.in.nic.nischayyojana.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import app.bih.in.nic.nischayyojana.entity.FarmerInfo;
import app.bih.in.nic.nischayyojana.entity.UserInfo;

public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}


	public static void setUserDetails(Context context, UserInfo userInfo) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		editor.putString("UserId", userInfo.get_UserID());
		editor.putString("UserName", userInfo.get_UserName());
		editor.putString("UserPassword", userInfo.get_Password());
		editor.putString("Role", userInfo.get_Role());
		editor.putString("DistrictId", userInfo.get_DistCode());
		editor.putString("DistrictName", userInfo.get_DistName());
		editor.putString("BlockId", userInfo.get_BlockCode());
		editor.putString("BlockName", userInfo.get_BlockName());
		editor.putString("PanchayatId", userInfo.get_PanchayatCode());
		//editor.putString("PacsId", userInfo.get_PacsCode());
		//editor.putString("PacsName", userInfo.get_PacsnName());
		


		editor.commit();

	}

	public static UserInfo getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserInfo userInfo = new UserInfo();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		userInfo.set_UserID(prefs.getString("UserId", ""));
		userInfo.set_UserName(prefs.getString("UserName", ""));
		userInfo.set_Role(prefs.getString("Role", ""));
		userInfo.set_DistCode(prefs.getString("DistrictId", ""));
		userInfo.set_DistName(prefs.getString("DistrictName", ""));
		userInfo.set_BlockCode(prefs.getString("BlockId", ""));
		userInfo.set_BlockName(prefs.getString("BlockName", ""));
		userInfo.set_PanchayatCode(prefs.getString("PanchayatId", ""));
		//userInfo.set_PacsCode(prefs.getString("PacsId", ""));
		//userInfo.set_PacsnName(prefs.getString("PacsName", userInfo.get_PacsnName()));
		
		userInfo.set_Role(prefs.getString("Role", ""));

		return userInfo;
	}

	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		dateTime = dateTime + 1 * 3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);

		if (System.currentTimeMillis() > a)
			return 1;
		else
			return 0;
	}
	
	public static void setFragmentData(Context context, String[] data) {

		String key = "_FragmentData";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		editor.putString("DistName", data[0]);
		editor.putString("DistCode", data[1]);

		editor.commit();

	}
	
	public static String[] getFragmentData(Context context) {

		String key = "_FragmentData";
		String[] data = new String[2];
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		data[0]= prefs.getString("DistName", "");
		data[1]= prefs.getString("DistCode", "");
		
		return data;
	}

	public static void setFarmerDetails(Context context, FarmerInfo farmerInfo) {

		String key = "_FARMER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		editor.putString("UserId", farmerInfo.get_UserID());
		editor.putString("UserName", farmerInfo.get_UserName());
		editor.putString("UserPassword", farmerInfo.get_Password());
		editor.putString("Role", farmerInfo.get_Role());
		editor.putString("DistrictId", farmerInfo.get_DistCode());
		editor.putString("DistrictName", farmerInfo.get_DistName());
		editor.putString("BlockId", farmerInfo.get_BlockCode());
		editor.putString("BlockName", farmerInfo.get_BlockName());
		editor.putString("PanchayatId", farmerInfo.get_PanchayatCode());
		//editor.putString("PacsId", userInfo.get_PacsCode());
		editor.putString("FatherName", farmerInfo.get_FatherName());
		editor.putString("MobileNum", farmerInfo.get_MobileNum());
		editor.putString("GenderCode", farmerInfo.get_GenderCode());
		editor.putString("CategoryCode", farmerInfo.get_CategoryCode());

		editor.commit();
	}

	public static FarmerInfo getFarmerDetails(Context context) {

		String key = "_FARMER_DETAILS";
		FarmerInfo farmerInfo = new FarmerInfo();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		farmerInfo.set_UserID(prefs.getString("UserId", ""));
		farmerInfo.set_UserName(prefs.getString("UserName", ""));
		farmerInfo.set_Role(prefs.getString("Role", ""));
		farmerInfo.set_DistCode(prefs.getString("DistrictId", ""));
		farmerInfo.set_DistName(prefs.getString("DistrictName", ""));
		farmerInfo.set_BlockCode(prefs.getString("BlockId", ""));
		farmerInfo.set_BlockName(prefs.getString("BlockName", ""));
		farmerInfo.set_PanchayatCode(prefs.getString("PanchayatId", ""));

		farmerInfo.set_FatherName(prefs.getString("FatherName", ""));
		farmerInfo.set_MobileNum(prefs.getString("MobileNum", ""));
		farmerInfo.set_GenderCode(prefs.getString("GenderCode", ""));
		farmerInfo.set_CategoryCode(prefs.getString("CategoryCode", ""));

		//userInfo.set_PacsCode(prefs.getString("PacsId", ""));
//		farmerInfo.set_FatherName(prefs.getString("FatherName", farmerInfo.get_FatherName()));
//		farmerInfo.set_MobileNum(prefs.getString("MobileNum", farmerInfo.get_MobileNum()));
//		farmerInfo.set_GenderCode(prefs.getString("GenderCode", farmerInfo.get_GenderCode()));
//		farmerInfo.set_CategoryCode(prefs.getString("CategoryCode", farmerInfo.get_CategoryCode()));


		return farmerInfo;
	}
	
}
