package app.bih.in.nic.nischayyojana.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.MODEOFPAYMENT;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.UserInfo;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class LoginActivity extends Activity {

	ConnectivityManager cm;
	public static String UserPhoto;
	String version;
	TelephonyManager tm;
	private static String imei="000000";

	String NoPacs, NoVMs, SelPacs, SelVMs;
	private static CheckBox show_hide_password;
	ProgressBar progressbar1, progressbar2, progressbar3;
	Spinner spWard;
	ArrayList<ward> WardList = new ArrayList<ward>();
	ArrayList<SCHEME> NischayList = new ArrayList<>();
	ArrayList<YOJANA> YojanaList = new ArrayList<>();
	ArrayList<MODEOFPAYMENT> PayModeList = new ArrayList<>();

	String ids, uid;
	EditText txtmobilenum, txtpwd, txtemailid;
	String userid;
	DataBaseHelper localDBHelper;
	ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loginx);
		localDBHelper = new DataBaseHelper(this);
		userid = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
		spWard = (Spinner) findViewById(R.id.spinnerward);
		txtmobilenum = (EditText) findViewById(R.id.txtmobilenum);
		txtpwd = (EditText) findViewById(R.id.txtpwd);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		progressbar1.setVisibility(View.GONE);

//		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
//		progressbar3 = (ProgressBar) findViewById(R.id.progressbar3);
//		progressbar2.setVisibility(View.GONE);
//		progressbar3.setVisibility(View.GONE);

		pd = new ProgressDialog(this);
		pd.setCanceledOnTouchOutside(false);
		// pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));

		show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
		show_hide_password
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
												 boolean isChecked) {
						final EditText password = (EditText) findViewById(R.id.txtpwd);
						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password
						}
					}
				});
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		txtmobilenum.setText(CommonPref.getUserDetails(getApplicationContext()).get_UserID());

		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			TextView tv = (TextView) findViewById(R.id.txtVersion);
			tv.setText("App Version : " + version);

		} catch (NameNotFoundException e) {

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		checkOnline();
	}

	protected void checkOnline() {
		// TODO Auto-generated method stub
		super.onResume();

		if (Utiilties.isOnline(LoginActivity.this) == false) {

			AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें या ऑफ़लाइन मोड के साथ जारी रखें।");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("ऑफ़लाइन जारी रखें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					GlobalVariables.isOffline = true;
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();

		} else {

			GlobalVariables.isOffline = false;
			// new CheckUpdate().execute();
		}
	}

	public void onClick_Login(View view) {

		Utiilties.hideKeyboard(this);
		Login();

	}


	public void Login() {


		if (!GlobalVariables.isOffline && !Utiilties.isOnline(LoginActivity.this)) {

			AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें या ऑफ़लाइन मोड के साथ जारी रखें।");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("ऑफ़लाइन जारी रखें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					GlobalVariables.isOffline = true;
				}
			});
			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();

		} else {

			String[] param = new String[3];
			param[0] = txtmobilenum.getText().toString();
			param[1] = txtpwd.getText().toString();

			if (!param[0].equals("") && !param[0].equals("null")) {
				if (!param[1].equals("") && !param[1].equals("null")) {
					progressbar1.setVisibility(View.VISIBLE);
					new LoginTask().execute(param);
				} else {
					txtpwd.setError("कृप्या पास्वर्ड भरे");
				}
			} else if (param[1].equals("") || param[1].equals("null")) {
				txtmobilenum.setError("कृपया आईडी भरे");
				txtpwd.setError("कृप्या पास्वर्ड भरे");
			} else {
				txtmobilenum.setError("कृपया आईडी भरे");
			}
		}
	}

	private class LoginTask extends AsyncTask<String, Void, UserInfo> {

		private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

		private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

		@Override
		protected void onPreExecute() {

			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("प्रमाणित कर रहा है...");
			this.dialog.show();
		}

		@Override
		protected UserInfo doInBackground(String... param) {

			if (GlobalVariables.isOffline) {
				UserInfo userInfo = new UserInfo();
				userInfo.set_isAuthenticated(true);
				return userInfo;
			} else
				return WebServiceHelper.Login(param[0], param[1]);

		}

		@Override
		protected void onPostExecute(UserInfo result) {
			AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
				progressbar1.setVisibility(View.GONE);
				final EditText userPass = (EditText) findViewById(R.id.txtpwd);
				final EditText userName = (EditText) findViewById(R.id.txtmobilenum);

				if (result == null) {

					ab.setTitle("अनुत्तीर्ण");
					ab.setMessage("प्रमाणीकरण विफल: NULL Rec");
					ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();


				}
				else if (result.get_isAuthenticated() == false) {

					ab.setTitle("अनुत्तीर्ण");
					ab.setMessage("प्रमाणीकरण विफल: आईडी और पासवर्ड मेल नहीं खाता | कृपया पुन: प्रयास करें");
					ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}else {

					SQLiteHelper placeData = new SQLiteHelper(LoginActivity.this);

					if (GlobalVariables.isOffline == false) {

						try {
							GlobalVariables.LoggedUser = result;
							GlobalVariables.LoggedUser.set_UserID(userName.getText().toString().trim().toLowerCase());
							GlobalVariables.LoggedUser.set_Password(userPass.getText().toString().trim());
							CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);


							placeData.insertUserDetails(GlobalVariables.LoggedUser);
							PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", result.get_UserID()).commit();
							PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_NAME", result.get_UserName()).commit();
							if(!result.get_PanchayatCode().equalsIgnoreCase("NA")) {
								PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", result.get_PanchayatCode()).commit();
							}

							String d="0",b="0",p="0";
							d=CommonPref.getUserDetails(LoginActivity.this).get_DistCode();
							b=CommonPref.getUserDetails(LoginActivity.this).get_BlockCode();

							p=CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode();

							if(CommonPref.getUserDetails(LoginActivity.this).get_Role().equalsIgnoreCase("PANADM"))
							{
								localDBHelper = new DataBaseHelper(LoginActivity.this);
								WardList=localDBHelper.getWardLocal(p);
								if(WardList.size()<=0 ) {
									new LoadWARDData(d,b,p).execute();
								}
							}

							localDBHelper = new DataBaseHelper(LoginActivity.this);
							NischayList=localDBHelper.getSchemeList();
							if(NischayList.size()<=0 ) {
								new LoadNischayData().execute();
							}

							if(CommonPref.getUserDetails(LoginActivity.this).get_Role().equalsIgnoreCase("PANADM")) {
								localDBHelper = new DataBaseHelper(LoginActivity.this);
								YojanaList = localDBHelper.getYojanaList("1", p,"0");
								if (YojanaList.size() <= 0) {
									new LoadYojanaData().execute();
								}
							}
							localDBHelper = new DataBaseHelper(LoginActivity.this);
							PayModeList=localDBHelper.getPayModeList();

							if(PayModeList.size()<=0 ) {
								new LoadPayModeData().execute();
							}

							if(result.get_Role().equalsIgnoreCase("PANADM")) {

								Intent I = new Intent(getApplicationContext(), PREHomeActivity.class);
								I.putExtra("FROMLOGIN","Y");
								finish();
								startActivity(I);
							}
							else
							{
								Intent I = new Intent(getApplicationContext(),UserHomeNewActivity.class);
								I.putExtra("FROMLOGIN","Y");
								finish();
								startActivity(I);
							}

						} catch (Exception ex) {
							Toast.makeText(LoginActivity.this, "लॉगिन विफल: Exception", Toast.LENGTH_SHORT).show();
						}
					} else {

						if (placeData.getUserCount() > 0) {

							GlobalVariables.LoggedUser = placeData.getUserDetails(userName.getText().toString().trim().toLowerCase(), userPass
									.getText().toString());

							if (GlobalVariables.LoggedUser != null) {

								CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);



								String d="0",b="0",p="0";
								d=CommonPref.getUserDetails(LoginActivity.this).get_DistCode();
								b=CommonPref.getUserDetails(LoginActivity.this).get_BlockCode();
								p=CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode();



                                localDBHelper = new DataBaseHelper(LoginActivity.this);
                                WardList=localDBHelper.getWardLocal(p);
                                if(WardList.size()<=0 ) {
                                    new LoadWARDData(d,b,p).execute();
                                }
                                localDBHelper = new DataBaseHelper(LoginActivity.this);
                                NischayList=localDBHelper.getSchemeList();
                                if(NischayList.size()<=0 ) {
                                    new LoadNischayData().execute();
                                }

                                localDBHelper = new DataBaseHelper(LoginActivity.this);
                                YojanaList=localDBHelper.getYojanaList("1",p,"0");
                                if(YojanaList.size()<=0 ) {
                                    new LoadYojanaData().execute();
                                }

                                localDBHelper = new DataBaseHelper(LoginActivity.this);
                                PayModeList=localDBHelper.getPayModeList();
                                if(PayModeList.size()<=0 ) {
                                    new LoadPayModeData().execute();
                                }

								Intent iUserHome = new Intent(getApplicationContext(), UserHomeNewActivity.class);
								iUserHome.putExtra("FROMLOGIN","Y");
								startActivity(iUserHome);
								finish();

							} else {

								Toast.makeText(getApplicationContext(), "आईडी और पासवर्ड मेल नहीं खाता!", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "कृपया पहली बार लॉगिन के लिए इंटरनेट कनेक्शन चालू करें.", Toast.LENGTH_LONG)
									.show();

							ab.setTitle("ऑनलाइन लॉगिन");
							ab.setMessage("कृपया पहली बार लॉगिन के लिए इंटरनेट कनेक्शन चालू करें.");
							ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int whichButton) {
									dialog.dismiss();
								}
							});

							ab.setNegativeButton("[ इंटरनेट ऑन करें ]", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int whichButton) {
									dialog.dismiss();
								}
							});
							ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
							ab.show();
						}
					}
				}
			}
		}

	}
	private class LoadWARDData extends AsyncTask<String, String, ArrayList<ward>> {
		String distCode="";
		String blockCode="";
		String panCode="";

		private final ProgressDialog dialog = new ProgressDialog(
				LoginActivity.this);

		LoadWARDData(String distCode,String blockCode,String panCode) {
			this.distCode = distCode;
			this.blockCode=blockCode;
			this.panCode = panCode;
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading Wards...");
			dialog.show();
		}

		@Override
		protected ArrayList<ward> doInBackground(String... param) {

			WardList = WebServiceHelper.loadWardList(CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode());
			return WardList;
		}

		@Override
		protected void onPostExecute(ArrayList<ward> result) {
		    try {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            catch(Exception ex)
            {
                Log.e("ERROR:", ex.getMessage());
            }
			if(result!=null)
			{
				if (result.size()>0) {

					long i=localDBHelper.setWardLocal(result);
					if(i>0)
					{
						Toast.makeText(getApplicationContext(), "वार्ड लोड हो गया ", Toast.LENGTH_LONG).show();
					}
				} else {

					Toast.makeText(getApplicationContext(), "वार्ड लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	private class LoadNischayData extends AsyncTask<String, String, ArrayList<SCHEME>> {

		private final ProgressDialog dialog = new ProgressDialog(
				LoginActivity.this);


		@Override
		protected void onPreExecute() {
            dialog.setMessage("Loading Nischay Type...");
            dialog.show();
		}

		@Override
		protected ArrayList<SCHEME> doInBackground(String... param) {

			NischayList = WebServiceHelper.loadNischayList();
			return NischayList;
		}

		@Override
		protected void onPostExecute(ArrayList<SCHEME> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", ex.getMessage());
			}

			if(result!=null)
			{
				if (result.size()>0) {

					long i=localDBHelper.setNischayLocal(result);
					if(i>0)
					{
						Toast.makeText(getApplicationContext(), "निश्चय टाइप लोड हो गया  ", Toast.LENGTH_LONG).show();
					}
				} else {

					Toast.makeText(getApplicationContext(), "निश्चय टाइप लोड नहीं हो पाया ", Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	private class LoadYojanaData extends AsyncTask<String, String, ArrayList<YOJANA>> {


		private final ProgressDialog dialog = new ProgressDialog(
				LoginActivity.this);

		LoadYojanaData() {
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading Yojana Type...");
			dialog.show();
		}

		@Override
		protected ArrayList<YOJANA> doInBackground(String... param) {

			YojanaList = WebServiceHelper.loadYojanaList(CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode());
			return YojanaList;
		}

		@Override
		protected void onPostExecute(ArrayList<YOJANA> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", ex.getMessage());
			}
			if(result!=null)
			{
				if (result.size()>0) {

					long i=localDBHelper.setYojanaLocal(result,CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode());
					if(i>0)
					{
						Toast.makeText(getApplicationContext(), "योजना का नाम लोड हो गया ", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "योजना का नाम लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {

					String pcode=CommonPref.getUserDetails(LoginActivity.this).get_PanchayatCode();

					Toast.makeText(getApplicationContext(), pcode +" : इस पंचायत कोड के लिए कोई योजना सूची नहीं मिली", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private class LoadPayModeData extends AsyncTask<String, String, ArrayList<MODEOFPAYMENT>> {


		private final ProgressDialog dialog = new ProgressDialog(
				LoginActivity.this);

		LoadPayModeData() {
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading Payment mode...");
			dialog.show();
		}

		@Override
		protected ArrayList<MODEOFPAYMENT> doInBackground(String... param) {

			PayModeList= WebServiceHelper.loadPaymentModeList();
			return PayModeList;
		}

		@Override
		protected void onPostExecute(ArrayList<MODEOFPAYMENT> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", ex.getMessage());
			}

			if(result!=null)
			{
				if (result.size()>0) {

					long i=localDBHelper.setPayModeLocal(result);
					if(i>0)
					{
						Toast.makeText(getApplicationContext(), "मोड ऑफ़ पेमेंट लोड हो गया", Toast.LENGTH_LONG).show();
					}
				} else {

					Toast.makeText(getApplicationContext(), "मोड ऑफ़ पेमेंट लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

    public static String cleanStringForVulnerability(String aString) {
        if (aString == null) return null;
        String cleanString = "";
        for (int i = 0; i < aString.length(); ++i) {
            cleanString += cleanChar(aString.charAt(i));
        }
        return cleanString;
    }

    private static char cleanChar(char aChar) {

        // 0 - 9
        for (int i = 48; i < 58; ++i) {
            if (aChar == i) return (char) i;
        }

        // 'A' - 'Z'
        for (int i = 65; i < 91; ++i) {
            if (aChar == i) return (char) i;
        }

        // 'a' - 'z'
        for (int i = 97; i < 123; ++i) {
            if (aChar == i) return (char) i;
        }

        // other valid characters
        switch (aChar) {
            case '/':
                return '/';
            case '.':
                return '.';
            case '-':
                return '-';
            case '_':
                return '_';
        }
        return '%';
    }
}
