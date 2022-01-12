package app.bih.in.nic.nischayyojana.ui;

import android.Manifest;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.MarshmallowPermission;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class SplashActivity extends Activity {

	SQLiteDatabase db;
	DataBaseHelper helper;

	MarshmallowPermission permission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		TextView textView = (TextView) this.findViewById(R.id.textview_marguee);
		textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		textView.setSelected(true);
		textView.setSingleLine(true);

		helper=new DataBaseHelper(this);

		try {
			helper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}


		ModifyTable();

//		if (Utiilties.isOnline(SplashActivity.this))
//		{
//			new CheckUpdate().execute();
//		}
//		else {
//
//			start();
//		}

		//------------Check Device name
		String devicename=getDeviceName();
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DEVICE_NAME", devicename ).commit();
		Log.e("DEVICE_NAME", devicename);


		//---------------Check Device type (mobile/tablet
		boolean isTablet=isTablet(SplashActivity.this);
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

		permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
		//permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);

	}

	public void ModifyTable()
	{
		if(isColumnExists("PayJaltbl","InspectorName")==false) {
			AlterTable("PayJaltbl", "InspectorName");
		}
		if(isColumnExists("PayJaltbl","InspectorPost")==false) {
			AlterTable("PayJaltbl","InspectorPost");
		}
		if(isColumnExists("PayJaltbl","OtherYojanaName")==false) {
			AlterTable("PayJaltbl","OtherYojanaName");
		}

		if(isColumnExists("PayJaltbl","OtherMotorPumpCapacity")==false) {
			AlterTable("PayJaltbl","OtherMotorPumpCapacity");
		}

		if(isColumnExists("GaliNali","InspectorName")==false) {
			AlterTable("GaliNali","InspectorName");
		}

		if(isColumnExists("GaliNali","InspectorPost")==false) {
			AlterTable("GaliNali","InspectorPost");
		}
		if(isColumnExists("GaliNali","OtherYojanaName")==false) {
			AlterTable("GaliNali","OtherYojanaName");
		}
		if(isColumnExists("GaliNali","OtherMotorPumpCapacity")==false) {
			AlterTable("GaliNali","OtherMotorPumpCapacity");
		}

		if(isColumnExists("PayJaltbl","BoringFtMt")==false) {
			AlterTable("PayJaltbl","BoringFtMt");
		}
		if(isColumnExists("PayJaltbl","StageFtMt")==false) {
			AlterTable("PayJaltbl","StageFtMt");
		}
		if(isColumnExists("PayJaltbl","VitranFtMt")==false) {
			AlterTable("PayJaltbl","VitranFtMt");
		}
		if(isColumnExists("PayJaltbl","VitranDepthFtMt")==false) {
			AlterTable("PayJaltbl","VitranDepthFtMt");
		}
		if(isColumnExists("GaliNali","TotalRoadDistance_LengthFtMt")==false) {
			AlterTable("GaliNali","TotalRoadDistance_LengthFtMt");
		}

		if(isColumnExists("GaliNali","TotalRoadDistance_BreadthFtMt")==false) {
			AlterTable("GaliNali","TotalRoadDistance_BreadthFtMt");
		}

		if(isColumnExists("GaliNali","TotalPathRoadDistance_BreadthFtMt")==false) {
			AlterTable("GaliNali","TotalPathRoadDistance_BreadthFtMt");
		}

		if(isColumnExists("GaliNali","TotalNaliDistance_LengthFtMt")==false) {
			AlterTable("GaliNali","TotalNaliDistance_LengthFtMt");
		}
		if(isColumnExists("GaliNali","TotalNali_BreadthFtMt")==false) {
			AlterTable("GaliNali","TotalNali_BreadthFtMt");
		}
		if(isColumnExists("GaliNali","TotalNali_GharaiFtMt")==false) {
			AlterTable("GaliNali","TotalNali_GharaiFtMt");
		}
		if(isColumnExists("GaliNali","MittiKryaFtMt")==false) {
			AlterTable("GaliNali","MittiKryaFtMt");
		}
		if(isColumnExists("ReportGaliNali","InspectorName")==false) {
			AlterTable("ReportGaliNali","InspectorName");
		}

		if(isColumnExists("ReportGaliNali","InspectorPost")==false) {
			AlterTable("ReportGaliNali","InspectorPost");
		}

		if(isColumnExists("ReportPayJaltbl","InspectorName")==false) {
			AlterTable("ReportPayJaltbl","InspectorName");
		}

		if(isColumnExists("ReportPayJaltbl","InspectorPost")==false) {
			AlterTable("ReportPayJaltbl","InspectorPost");
		}

		if(isColumnExists("VillageList","PanchayatCode")==false) {
			AlterTable("VillageList","PanchayatCode");
		}

		if(isColumnExists("GaliNali","VillageCode")==false) {
			AlterTable("GaliNali","VillageCode");
		}
		if(isColumnExists("PayJaltbl","VillageCode")==false) {
			AlterTable("PayJaltbl","VillageCode");
		}

		if(isColumnExists("ReportPayJaltbl","VillageCode")==false) {
			AlterTable("ReportPayJaltbl","VillageCode");
		}

		if(isColumnExists("ReportGaliNali","VillageCode")==false) {
			AlterTable("ReportGaliNali","VillageCode");
		}
	}
	public void AlterTable(String tableName,String columnName)
	{
		 db = helper.getReadableDatabase();

		try{

			db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
			Log.e("ALTER Done",tableName +"-"+ columnName);
		}
		catch (Exception e)
		{
			Log.e("ALTER Failed",tableName +"-"+ columnName);
		}
	}
	public boolean isColumnExists (String table, String column) {
		 db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				if (column.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}

		return false;
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

	private void start() {
		Thread background = new Thread() {
			@Override
			public void run() {
				try {
					// Thread will sleep for 2 seconds
					sleep(2 * 1000);
					// After 5 seconds redirect to another intent
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					finish();
					//Intent i = new Intent(getBaseContext(), UserHomeActivity.class);
					startActivity(i);

					SplashActivity.this.finish();

				} catch (Exception e) {

				}
			}
		};
		background.start();
	}

	@SuppressLint("NewApi")
	private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {

		CheckUpdate() {

		}


		LinearLayout llVersion = (LinearLayout) findViewById(R.id.llVersion);

		@Override
		protected void onPreExecute() {
			// this.dialog.setMessage("Checking for update...");
			// this.dialog.setMessage("Loading...");
			// this.dialog.show();
		}

		@Override
		protected Versioninfo doInBackground(Void... Params) {

			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			//String imei = tm.getDeviceId();
//			if (imei.equals("000000000000000")) {
//				// IMEI for user "test"
//				imei = "359296057628662";
//			}
			String imei = "359296057628662";
			String version = null;
			try {
				version = String.valueOf(getPackageManager().getPackageInfo(
						getPackageName(), 0).versionName);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Versioninfo versioninfo = WebServiceHelper.CheckVersion(imei,
					version);

			return versioninfo;
		}

		@Override
		protected void onPostExecute(final Versioninfo versioninfo) {
			// if (this.dialog.isShowing()) {
			// this.dialog.dismiss();

			//llVersion.setVisibility(View.GONE);

			final AlertDialog.Builder ab = new AlertDialog.Builder(
					SplashActivity.this);
			ab.setCancelable(false);
			if (versioninfo != null && versioninfo.isValidDevice()) {

				CommonPref.setCheckUpdate(getApplicationContext(),
						System.currentTimeMillis());

				if (!versioninfo.getAdminMsg().equals("NA")
						&& !versioninfo.getAdminMsg().trim()
						.equalsIgnoreCase("anyType{}")) {

					ab.setTitle(versioninfo.getAdminTitle());
					ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
					ab.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int whichButton) {
									dialog.dismiss();

									showDailog(ab, versioninfo);

								}
							});
					ab.show();
				}
				else {
					showDailog(ab, versioninfo);
				}
			} else {
				if (versioninfo != null) {
					Toast.makeText(getApplicationContext(),
							"Your device is not registered !",
							Toast.LENGTH_LONG).show();

				}
				Intent i = new Intent(getBaseContext(), LoginActivity.class);

				startActivity(i);
				finish();
			}

		}
	}

	private void showDailog(AlertDialog.Builder ab,
							final Versioninfo versioninfo) {

		if (versioninfo.isVerUpdated()) {

			if (versioninfo.getPriority() == 0) {

				Intent i = new Intent(getBaseContext(), LoginActivity.class);
				startActivity(i);
				finish();
			} else if (versioninfo.getPriority() == 1) {

				ab.setTitle(versioninfo.getUpdateTile());
				ab.setMessage(versioninfo.getUpdateMsg());
				ab.setPositiveButton("Update",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int whichButton) {


								Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();
							}
						});
				ab.setNegativeButton("Ignore",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int whichButton) {
								// GlobalVariables.isOffline
								// = true;

								dialog.dismiss();

								Intent i = new Intent(getBaseContext(),	LoginActivity.class);
								startActivity(i);
								finish();
							}

						});

				ab.show();

			} else if (versioninfo.getPriority() == 2) {

				ab.setTitle(versioninfo.getUpdateTile());
				ab.setMessage(versioninfo.getUpdateMsg());
				// ab.setMessage("Please update your App its required. Click on Update button");

				ab.setPositiveButton("Update",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int whichButton) {

								Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();
								// finish();
							}
						});
				ab.show();
			}
		}
		else {

			Intent i = new Intent(getBaseContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Utiilties.isOnline(SplashActivity.this))
		{
			new CheckUpdate().execute();
		}
		else {
			AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें।");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
			//start();
		}
	}
}