package app.bih.in.nic.nischayyojana.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.Versioninfo;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class PREHomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prehome_activity);

		//------------Check Device name
		String devicename=getDeviceName();
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DEVICE_NAME", devicename ).commit();
		Log.e("DEVICE_NAME", devicename);


		//---------------Check Device type (mobile/tablet
		boolean isTablet=isTablet(PREHomeActivity.this);
		if(isTablet) {
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DEVICE_DETAILS", "Tablet:"+devicename).commit();
			Log.e("DEVICE_DETAILS", "Tablet");
		}
		else
		{
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DEVICE_DETAILS", "Mobile:"+devicename).commit();
			Log.e("DEVICE_DETAILS", "Mobile");
		}

		//---------------Check App Version
		String version = getAppVersion();
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("APP_VERSION", version).commit();
		Log.e("APP_VERSION", version);

		TextView appversion = (TextView) findViewById(R.id.txtVersion);
		appversion.setText("App Version : " + version);
	}

	public void OnClick_goToLoginScreen(View v)
	{
		Intent i=new Intent(PREHomeActivity.this, LoginActivity.class);
		finish();
		startActivity(i);
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return model.toUpperCase();
		} else {
			return manufacturer.toUpperCase() + " " + model;
		}
	}


	public String getAppVersion()
	{
		String version="";
		try {
			version = getPackageManager().getPackageInfo(getPackageName(),
					0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}


	public void OnClick_DoPhysicalVerification(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("WORK_TYPE", "PV" ).commit();
		Intent I = new Intent(getApplicationContext(),UserHomeNewActivity.class);
		I.putExtra("FROMPREHOME","Y");
		startActivity(I);
	}

	public void OnClick_DoPhysicalInspection(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("WORK_TYPE", "PI" ).commit();
		Intent I = new Intent(getApplicationContext(),UserHomeNewActivity.class);
		I.putExtra("FROMPREHOME","Y");
		startActivity(I);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}