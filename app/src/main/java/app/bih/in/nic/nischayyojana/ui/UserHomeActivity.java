package app.bih.in.nic.nischayyojana.ui;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.Block;
import app.bih.in.nic.nischayyojana.entity.District;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.MODEOFPAYMENT;
import app.bih.in.nic.nischayyojana.entity.PANCHAYAT;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.VillageData;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportGNProperty;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportProperty;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;
import android.speech.tts.TextToSpeech;

public class UserHomeActivity extends Activity {


	Button btnShowPending, btnUploading, btnSelReport, btnProgReport;

	RelativeLayout rlbtns1,rlbtns2,rlbtns3,rlbtns4,rlbtns5;
	LinearLayout lnbtns1,lnbtns2,lnbtns3,lnbtns4,lnmenu1,lnmenu2,lnmenu3,lnmenu4,lnmenu5,lnbtns5;

	LinearLayout lnAllDownLoadIMG,lnDistBlkPan;

	DataBaseHelper localDBHelper;
	String userid;
	TextView lbl0,lbl1,lbl2,lbl3,lbl4,lbl5,txthelpbtn;//,lblWardCount,lblNischayCount,lblYojanaCount,lblPayModeCount;
	ProgressBar p1,p2,p3,p4,p5;
	boolean isuploading1=false,isuploading2=false,isuploading3=false;
	String _WardCode,_WardName,_FYearCode,_NischayCode,_SchemeName;

	ProgressBar progressSYcn;

	Spinner spDistrict,spBlock,spPanchayat;
	String _vardistrictName="",_vardistrictID="";
	String _varblockName="",_varblockID="";
	String _varpanchayatName="",_varpanchayatID="";

	ArrayList<District> DistrictList = new ArrayList<District>();
	ArrayAdapter<String> districtadapter;

	ArrayList<Block> BlockList = new ArrayList<Block>();
	ArrayAdapter<String> blockadapter;
	ArrayList<String> BlocktStringList;

	ArrayList<PanchayatData> PanchayatList = new ArrayList<>();
	ArrayAdapter<String> Panchayatadapter;

	ArrayList<ward> WardList = new ArrayList<ward>();
	ArrayList<SCHEME> NischayList = new ArrayList<>();
	ArrayList<YOJANA> YojanaList = new ArrayList<>();
	ArrayList<VillageData> VillageList = new ArrayList<>();
	ArrayList<MODEOFPAYMENT> PayModeList = new ArrayList<>();
	ArrayList<WorkProgressReportProperty> PayJalRecordsList = new ArrayList<>();

	ImageView i1,i2,imghowto;
	ImageView downVillage,downWard,downNischay,downYojana,downPayMode,downPayJal,downGaliNaali;
	LinearLayout l1,l2,l3,l4,lnWardVisitRec;

	//    String status1="e";
	String status1="c";
	String status2="c";
	String status3="c";
	String status4="C";

	String pcode;
	String title, msg;
	private TextToSpeech textToSpeech;
	String work_type;
	TextView txtVillDown,txtWardDown,txtNisDown,txtYojDown,txtPayDown,txtPJalDown,txtGaliNaaliDown;

	String nischayCode="0";
	String _varWardYojanDetails="na";
	int IMG_WIDTH=500,IMG_HEIGHT=500;


	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_home);
		textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int ttsLang = textToSpeech.setLanguage(Locale.US);

					if (ttsLang == TextToSpeech.LANG_MISSING_DATA
							|| ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
						Log.e("TTS", "The Language is not supported!");
					} else {
						Log.i("TTS", "Language Supported.");
					}
					Log.i("TTS", "Initialization success.");
				} else {
					Toast.makeText(getApplicationContext(), "Text To Voice Initialization failed!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		pcode=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();
		userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
		_NischayCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");
		_SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");

		i1=(ImageView) findViewById(R.id.imgbtn1);
		i2=(ImageView) findViewById(R.id.imgbtn2);
		l1=(LinearLayout) findViewById(R.id.lnweb1);
		l2=(LinearLayout) findViewById(R.id.lnweb2);

		lnWardVisitRec=(LinearLayout) findViewById(R.id.lnWardVisitRec);

		imghowto= findViewById(R.id.imghowto);

		BlinkImageView(imghowto);
		spDistrict=(Spinner)findViewById(R.id.spinnerdistrict);
		spBlock=(Spinner)findViewById(R.id.spinnerblock);
		spPanchayat=(Spinner)findViewById(R.id.spinnerpanchayat);


		TextView txtMainHeader=(TextView) findViewById(R.id.txtHeaderTop);
		txthelpbtn=(TextView) findViewById(R.id.txthelpbtn);

		txtVillDown=(TextView) findViewById(R.id.txtVillDown);
		txtWardDown=(TextView) findViewById(R.id.txtWardDown);
		txtNisDown=(TextView) findViewById(R.id.txtNisDown);
		txtYojDown=(TextView) findViewById(R.id.txtYojDown);
		txtPayDown=(TextView) findViewById(R.id.txtPayDown);
		txtPJalDown=(TextView) findViewById(R.id.txtPJalDown);
		txtGaliNaaliDown=(TextView) findViewById(R.id.txtGaliNaaliDown);

		txtMainHeader.setText(R.string.headertext);

		txtMainHeader.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		txtMainHeader.setSelected(true);
		txtMainHeader.setSingleLine(true);

		txthelpbtn.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		txthelpbtn.setSelected(true);
		txthelpbtn.setSingleLine(true);

		//APP_VERSION
		String appver=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("APP_VERSION", "");
		TextView lblfooter=(TextView) findViewById(R.id.lblfooter);
		lblfooter.setText(lblfooter.getText()+ " [V-"+ appver + "]");

		localDBHelper = new DataBaseHelper(this);
		userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

		_vardistrictID= CommonPref.getUserDetails(UserHomeActivity.this).get_DistCode();
		_varblockID=CommonPref.getUserDetails(UserHomeActivity.this).get_BlockCode();
		_varpanchayatID=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();

		work_type= PreferenceManager.getDefaultSharedPreferences(UserHomeActivity.this).getString("WORK_TYPE", "");
		if(_vardistrictID.trim().length()<3)
		{
			_vardistrictID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DIST_CODE", "");
		}
		if(_varblockID.trim().length()<3)
		{
			_varblockID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("BLOCK_CODE", "");
		}


		if(_varpanchayatID.trim().length()<3)
		{
			_varpanchayatID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PAN_CODE", "");
		}
		if(_varblockID.equalsIgnoreCase("0")) {
			_varpanchayatID="0";
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", _varpanchayatID).commit();
		}

		p1=(ProgressBar) findViewById(R.id.progressbar1);
		p2=(ProgressBar) findViewById(R.id.progressbar2);
		p3=(ProgressBar) findViewById(R.id.progressbar3);
		p4=(ProgressBar) findViewById(R.id.progressbar4);
		p5=(ProgressBar) findViewById(R.id.progressbar5);
		progressSYcn=(ProgressBar) findViewById(R.id.progressSYcn);

		lbl0=(TextView) findViewById(R.id.txtSyncAll);
		lbl1=(TextView) findViewById(R.id.lbl1);
		lbl2=(TextView) findViewById(R.id.lbl2);
		lbl3=(TextView) findViewById(R.id.lbl3);
		lbl4=(TextView) findViewById(R.id.lbl4);
		lbl5=(TextView) findViewById(R.id.lbl5);

		rlbtns1=(RelativeLayout) findViewById(R.id.rl_allbyns1);
		rlbtns2=(RelativeLayout) findViewById(R.id.rl_allbyns2);
		rlbtns3=(RelativeLayout) findViewById(R.id.rl_allbyns3);
		rlbtns4=(RelativeLayout) findViewById(R.id.rl_allbyns4);
		rlbtns5=(RelativeLayout) findViewById(R.id.rl_allbyns5);

		lnmenu1=(LinearLayout) findViewById(R.id.lnmenu1);
		lnmenu2=(LinearLayout) findViewById(R.id.lnmenu2);
		lnmenu3=(LinearLayout) findViewById(R.id.lnmenu3);
		lnmenu4=(LinearLayout) findViewById(R.id.lnmenu4);
		lnmenu5=(LinearLayout) findViewById(R.id.lnmenu5);

		lnAllDownLoadIMG=(LinearLayout) findViewById(R.id.lnAllDownLoadIMG);
		lnAllDownLoadIMG.setVisibility(View.VISIBLE);

		lnDistBlkPan=(LinearLayout) findViewById(R.id.lnDistBlkPan);
		lnDistBlkPan.setVisibility(View.GONE);


		p1.setVisibility(View.GONE);
		p2.setVisibility(View.GONE);
		p3.setVisibility(View.GONE);
		p4.setVisibility(View.GONE);
		p5.setVisibility(View.GONE);
		progressSYcn.setVisibility(View.GONE);


		lnbtns1=(LinearLayout) findViewById(R.id.lnbutons1);
		lnbtns2=(LinearLayout) findViewById(R.id.lnbutons2);
		lnbtns3=(LinearLayout) findViewById(R.id.lnbutons3);
		lnbtns4=(LinearLayout) findViewById(R.id.lnbutons4);
		lnbtns5=(LinearLayout) findViewById(R.id.lnbutons5);


		lnbtns1.setVisibility(View.VISIBLE);
		lnbtns2.setVisibility(View.VISIBLE);
		lnbtns3.setVisibility(View.VISIBLE);
		lnbtns4.setVisibility(View.VISIBLE);
		lnbtns5.setVisibility(View.VISIBLE);

		lnbtns1.bringToFront();
		lnbtns2.bringToFront();
		lnbtns3.bringToFront();
		lnbtns4.bringToFront();
		lnbtns5.bringToFront();

		//downWard,downNischay,downYojana,downPayMode,downPayJal,downGaliNaali
		downVillage=findViewById(R.id.downVillage);
		downWard=findViewById(R.id.downWard);
		downNischay=findViewById(R.id.downNischay);
		downYojana=findViewById(R.id.downYojana);
		downPayMode=findViewById(R.id.downPayMode);
		downPayJal=findViewById(R.id.downPayJal);
		downGaliNaali=findViewById(R.id.downGaliNaali);


		spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				if (arg2 > 0) {

					District dist = DistrictList.get(arg2 - 1);
					_vardistrictID = dist.get_DistCode();
					_vardistrictName=dist.get_DistName();
					//CommonPref.setUserDetails();
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", _vardistrictID).commit();
					setBlockSpinnerData();
					spDistrict.clearAnimation();
					BlinkSpinner(spBlock);

				} else {
					_vardistrictID = "0";
					_vardistrictName="NA";
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", "0").commit();
					spBlock.setSelection(0);
					spPanchayat.setSelection(0);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 > 0) {
					Block blk = BlockList.get(arg2 - 1);
					_varblockID = blk.getBlockCode();
					_varblockName=blk.getBlockName();
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", _varblockID).commit();
					setPanchayatSpinnerData();
					spBlock.clearAnimation();
					BlinkSpinner(spPanchayat);
				}
				else if(arg2==0)
				{
					_varblockID = "0";
					_varblockName="0";
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", "0").commit();
					spPanchayat.setSelection(0);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 > 0) {
					PanchayatData wrd = PanchayatList.get(arg2 - 1);
					_varpanchayatID = wrd.getPcode();
					_varpanchayatName=wrd.getPname();

					pcode=_varpanchayatID;
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", _varpanchayatID).commit();

					setTintColorForDownloadImage();
					spPanchayat.clearAnimation();
				}
				else if(arg2==0)
				{
					// spWard.setSelection(0);
					// spVillage.setSelection(0);
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", "0").commit();
					_varpanchayatID = "0";
					_varpanchayatName="0";
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});


		rlbtns1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lnbtns1.getVisibility() == View.VISIBLE) {
					lnbtns1.setVisibility(View.INVISIBLE);
					lbl1.setGravity(Gravity.CENTER);
				} else {
					lnbtns1.setVisibility(View.VISIBLE);
					lnbtns1.bringToFront();
					lbl1.setGravity(Gravity.BOTTOM);
				}
			}
		});

		rlbtns2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lnbtns2.getVisibility() == View.VISIBLE) {
					lnbtns2.setVisibility(View.INVISIBLE);
					lbl2.setGravity(Gravity.CENTER);
				} else {
					lnbtns2.setVisibility(View.VISIBLE);
					lnbtns2.bringToFront();
					lbl2.setGravity(Gravity.BOTTOM);
				}
			}
		});

		rlbtns3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lnbtns3.getVisibility() == View.VISIBLE) {
					lnbtns3.setVisibility(View.INVISIBLE);
					lbl3.setGravity(Gravity.CENTER);
				} else {
					lnbtns3.setVisibility(View.VISIBLE);
					lnbtns3.bringToFront();
					lbl3.setGravity(Gravity.BOTTOM);
				}
			}
		});

		rlbtns4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lnbtns4.getVisibility() == View.VISIBLE) {
					lnbtns4.setVisibility(View.INVISIBLE);
					lbl4.setGravity(Gravity.CENTER);
				} else {
					lnbtns4.setVisibility(View.VISIBLE);
					lnbtns4.bringToFront();
					lbl4.setGravity(Gravity.BOTTOM);
				}
			}
		});

		rlbtns5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lnbtns5.getVisibility() == View.VISIBLE) {
					lnbtns5.setVisibility(View.INVISIBLE);
					lbl5.setGravity(Gravity.CENTER);
				} else {
					lnbtns5.setVisibility(View.VISIBLE);
					lnbtns5.bringToFront();
					lbl5.setGravity(Gravity.BOTTOM);
				}
			}
		});

		setTintColorForDownloadImage();


		lnWardVisitRec.setVisibility(View.GONE);
		String usertype = CommonPref.getUserDetails(UserHomeActivity.this).get_Role();

		if (usertype.equals("PANADM")) {

			if(work_type.equalsIgnoreCase("PV"))
			{
				rlbtns1.setVisibility(View.VISIBLE);  //----voucher entry payjal form
				rlbtns2.setVisibility(View.VISIBLE);  //----voucher entry gli-naali pakkikaran form
				lnDistBlkPan.setVisibility(View.GONE);
				lnWardVisitRec.setVisibility(View.GONE);
				lbl4.setText("भौतिक प्रगति");
				lbl5.setText("भौतिक प्रगति");

			}
			else if(work_type.equalsIgnoreCase("PI"))
			{
				rlbtns1.setVisibility(View.GONE);
				rlbtns2.setVisibility(View.GONE);
				lnDistBlkPan.setVisibility(View.VISIBLE);
				lnWardVisitRec.setVisibility(View.VISIBLE);
				loadDistrictSpinnerdata();
				lbl4.setText("भौतिक निरीक्षण");
				lbl5.setText("भौतिक निरीक्षण");
			}

		} else {
			rlbtns1.setVisibility(View.GONE);
			rlbtns2.setVisibility(View.GONE);
			lnDistBlkPan.setVisibility(View.VISIBLE);
			lnWardVisitRec.setVisibility(View.VISIBLE);
			loadDistrictSpinnerdata();
			lbl4.setText("भौतिक निरीक्षण");
			lbl5.setText("भौतिक निरीक्षण");

		}


		if(getIntent().hasExtra("FROMLOGIN") || getIntent().hasExtra("FROMPREHOME")) {

			startActivity(new Intent(UserHomeActivity.this, WardYojanaDetailsActivity.class));
		}
		if(_vardistrictID.trim().length()<3) {
			BlinkSpinner(spDistrict);
		}
		if(getIntent().hasExtra("FROMLOGIN") || getIntent().hasExtra("FROMPREHOME")) {
			startActivity(new Intent(UserHomeActivity.this, HelpActivity.class));
		}

	}


	public void OnClick_WardYojanaDetails(View v)
	{
		startActivity(new Intent(UserHomeActivity.this, WardYojanaDetailsActivity.class));
	}
	public void getWardYojanDetails()
	{
		localDBHelper = new DataBaseHelper(UserHomeActivity.this);
		WardList=localDBHelper.getWardLocal(_varpanchayatID);
//_varpanchayatName

		if(WardList.size()>0 ) {
			//1-Pay Jal
			//2-GaliNaali
			StringBuilder sb=new StringBuilder();
			sb.append("<b>किसी वार्ड में कितने योजना जुड़े हुए हैं उसकी संख्या वार्ड के नाम के साथ ( ) ब्रैकेट मै दरसाई गई है|</b><br>");
			sb.append("<br>पेय-जल: "+getPanName()+"  के वार्ड और उसमे जुड़े योजना की संख्या( )मै - <br>");
			for(int i=0;i<WardList.size();i++)
			{
				sb.append(WardList.get(i).getWardname()+" ("+getYojanaCount(WardList.get(i).getWardCode(),"1")+")");
			}
			sb.append("<br>");
			sb.append("<br>गली-नाली: "+getPanName()+" के वार्ड और उसमे जुड़े योजना की संख्या( )मै - <br>");
			for(int i=0;i<WardList.size();i++)
			{
				sb.append(WardList.get(i).getWardname()+" ("+getYojanaCount(WardList.get(i).getWardCode(),"2")+")");
			}

			_varWardYojanDetails=sb.toString();
			Log.e("WARDYOJANA",sb.toString());
		}
	}
	public String getYojanaCount(String wcode,String NischayID)
	{
		ArrayList <YOJANA> YojanaTypeList = new ArrayList<>();
		String yc="0";
		localDBHelper = new DataBaseHelper(UserHomeActivity.this);
		YojanaTypeList=localDBHelper.getYojanaList(NischayID,_varpanchayatID,wcode);

		yc=String.valueOf(YojanaTypeList.size());

		return yc;
	}
	public String getPanName()
	{
		localDBHelper = new DataBaseHelper(UserHomeActivity.this);
		return localDBHelper.getPanchayatName(_varpanchayatID)+ " पंचायत";

	}

//	public void TextToSpeech(final String msg)
//	{
//		int timeout = 1000; // make the activity visible for 4 (4000) seconds
//
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//
//				Log.i("TTS", "button clicked: " + msg);
//				int speechStatus = textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
//
//				if (speechStatus == TextToSpeech.ERROR) {
//					Log.e("TTS", "Error in converting Text to Speech!");
//				}
//
//			}
//		}, timeout);
//
//	}

	public void loadDistrictSpinnerdata() {

		DistrictList = localDBHelper.getDistrict();
		String[] divNameArray = new String[DistrictList.size() + 1];
		divNameArray[0] = "-जिला चुनें-";
		int i = 1;
		int setID=0;
		for (District dist : DistrictList) {

			divNameArray[i] = dist.get_DistName();
			if(_vardistrictID.equalsIgnoreCase(DistrictList.get(i-1).get_DistCode()))
			{
				setID=i;
				spDistrict.setEnabled(false);
			}

			i++;
		}
		districtadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
		districtadapter.setDropDownViewResource(R.layout.dropdownlist);
		spDistrict.setAdapter(districtadapter);

		spDistrict.setSelection(setID);
	}
	public void setBlockSpinnerData()
	{
		DataBaseHelper placeData = new DataBaseHelper(UserHomeActivity.this);
		BlockList=placeData.getBlock(_vardistrictID);

		if(BlockList.size()>0) loadBlockSpinnerData(BlockList);

//		msg = "Please select block";
//		TextToSpeech(msg);
	}

	private void loadBlockSpinnerData(ArrayList<Block> bList)
	{
		BlocktStringList=new ArrayList<String>();

		BlocktStringList.add("-प्रखंड चुनें-");
		int setID=0;
		for(int i=0;i<bList.size();i++)
		{
			BlocktStringList.add(bList.get(i).getBlockName());
			if(_varblockID.equalsIgnoreCase(bList.get(i).getBlockCode()))
			{
				setID=i+1;
				spBlock.setEnabled(false);
			}


		}
		blockadapter=new ArrayAdapter(this,R.layout.dropdownlist,BlocktStringList);
		spBlock.setAdapter(blockadapter);
		spBlock.setSelection(setID);
	}

	public void setPanchayatSpinnerData(){
		DataBaseHelper placeData = new DataBaseHelper(UserHomeActivity.this);
		PanchayatList=placeData.getPanchayatLocal(_varblockID);
		String isfound="n";

		if(PanchayatList.size()>0)
		{
			//
			for(int i=0;i<PanchayatList.size();i++)
			{
				if(_varpanchayatID.equalsIgnoreCase(PanchayatList.get(i).getPcode()))
				{
					isfound="y";
				}
			}
		}
		//if particular pan id not exist in local database then sync from server
//		if(isfound.equalsIgnoreCase("n"))
//		{
//			new LoadPanchayatData(_varblockID).execute();
//		}
		if(PanchayatList.size()>0 ) loadPanchayatSpinnerData(PanchayatList);

//		msg = "Please select panchayat";
//		TextToSpeech(msg);
	}
	private void loadPanchayatSpinnerData(ArrayList<PanchayatData> pList)
	{
		ArrayList<String> PanchayatStringList=new ArrayList<String>();
		PanchayatStringList.add("-पंचायत चुनें-");
		int setID=0;
		for(int i=0;i<pList.size();i++)
		{
			PanchayatStringList.add(pList.get(i).getPname());
			if(_varpanchayatID.equalsIgnoreCase(pList.get(i).getPcode()))
			{
				setID=i+1;
				//spPanchayat.setEnabled(false);
			}

		}
		Panchayatadapter=new ArrayAdapter(this,R.layout.dropdownlist,PanchayatStringList);
		spPanchayat.setAdapter(Panchayatadapter);
		spPanchayat.setSelection(setID);
	}


	public void OnClick_MMGNPJY(View v)
	{
		//check whether all required data is exist in data base
		if(CheckForDataExistForVarification()==true) {

//			if (status1.equalsIgnoreCase("e"))//expanded
//			{
//				l1.setVisibility(View.GONE);
//				i1.setImageResource(R.drawable.plush);
//				status1 = "c";
//			} else {
//				status1 = "e";
//				l1.setVisibility(View.VISIBLE);
//				i1.setImageResource(R.drawable.minush);
//
//				l2.setVisibility(View.GONE);
//				i2.setImageResource(R.drawable.plush);
//			}
		}
	}

	public void OnClick_MMGNGNPY(View v)
	{
		//check whether all required data is exist in data base
		if(CheckForDataExistForVarification()==true) {
//			if (status2.equalsIgnoreCase("e"))//expanded
//			{
//				l2.setVisibility(View.GONE);
//				i2.setImageResource(R.drawable.plush);
//				status2 = "c";
//			} else {
//				status2 = "e";
//				l2.setVisibility(View.VISIBLE);
//				i2.setImageResource(R.drawable.minush);
//
//				l1.setVisibility(View.GONE);
//				i1.setImageResource(R.drawable.plush);
//			}
		}
	}

	public void setTintColorForDownloadImage(){
		//downWard,downNischay,downYojana,downPayMode,downPayJal,downGaliNaali
		String[] data=isDataExist();

		if(data!=null)
		{
			if(data.length>0)
			{
				if(data[6].equalsIgnoreCase("0"))
				{
					downVillage.setColorFilter(this.getResources().getColor(R.color.red));
//					String msg="Syncronize ward data";
//					TextToSpeech(msg);
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					if(_varpanchayatID.trim().length()>3) {
						BlinkImageView(downVillage);
						ScrollTextView(txtVillDown);
					}
				}
				else
				{
					downVillage.setColorFilter(this.getResources().getColor(R.color.green));
					downVillage.clearAnimation();
				}
				if(data[0].equalsIgnoreCase("0"))
				{
					downWard.setColorFilter(this.getResources().getColor(R.color.red));
//					String msg="Syncronize ward data";
//					TextToSpeech(msg);
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					if(!data[6].equalsIgnoreCase("0"))
					{
						BlinkImageView(downWard);
						ScrollTextView(txtWardDown);
					}
//					if(_varpanchayatID.trim().length()>3) {
//						BlinkImageView(downWard);
//					}
				}
				else
				{
					downWard.setColorFilter(this.getResources().getColor(R.color.green));
					downWard.clearAnimation();

				}

				if(data[1].equalsIgnoreCase("0"))
				{
					downNischay.setColorFilter(this.getResources().getColor(R.color.red));
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					BlinkImageView(downNischay);
					ScrollTextView(txtNisDown);
				}
				else
				{
					downNischay.setColorFilter(this.getResources().getColor(R.color.green));
					downNischay.clearAnimation();
				}

				if(data[2].equalsIgnoreCase("0"))
				{
					downYojana.setColorFilter(this.getResources().getColor(R.color.red));
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					if(!data[0].equalsIgnoreCase("0"))
					{
						BlinkImageView(downYojana);
						ScrollTextView(txtYojDown);
					}
					//BlinkImageView(downYojana);
				}
				else
				{
					downYojana.setColorFilter(this.getResources().getColor(R.color.green));
					downYojana.clearAnimation();

				}

				if(data[3].equalsIgnoreCase("0"))
				{
					downPayMode.setColorFilter(this.getResources().getColor(R.color.red));
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					//BlinkImageView(downPayMode);
				}
				else
				{
					downPayMode.setColorFilter(this.getResources().getColor(R.color.green));
					downPayMode.clearAnimation();
				}

				if(data[4].equalsIgnoreCase("0"))
				{
					downPayJal.setColorFilter(this.getResources().getColor(R.color.yellow));
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					//BlinkImageView(downPayJal);
				}
				else
				{
					downPayJal.setColorFilter(this.getResources().getColor(R.color.green));
					downPayJal.clearAnimation();
				}

				if(data[5].equalsIgnoreCase("0"))
				{
					downGaliNaali.setColorFilter(this.getResources().getColor(R.color.yellow));
					lnAllDownLoadIMG.setVisibility(View.VISIBLE);
					lnAllDownLoadIMG.bringToFront();
					lbl0.setGravity(Gravity.BOTTOM);
					//BlinkImageView(downGaliNaali);
				}
				else
				{
					downGaliNaali.setColorFilter(this.getResources().getColor(R.color.green));
					downGaliNaali.clearAnimation();
				}
			}
			if(!data[0].equalsIgnoreCase("0") && !data[2].equalsIgnoreCase("0"))
			{
				getWardYojanDetails();
			}

		}
	}

	public boolean CheckForDataExistForVarification()
	{
		//check whether all required data is exist in data base
		String[] data=isDataExist();

		if(data!=null)
		{
			if(data[0].equalsIgnoreCase("0"))
			{
				title="वार्ड सूची नहीं मिली";
				if(pcode.trim().length()<3)
				{
					pcode="";
				}
				msg=pcode + " : इस पंचायत कोड के लिए कोई वार्ड सूची नहीं मिली |\nआप वार्ड की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं|\nकृपया पंचायत सेलेक्ट करके डाटा सिंक करें| डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें। ";
				if(pcode.trim().length()<3)
				{
//					msg +="\nकृपया पंचायत सेलेक्ट करके डाटा सिंक करें| डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें।";
					msg=pcode + " : इस पंचायत कोड के लिए कोई वार्ड सूची नहीं मिली |\nआप वार्ड की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं|\nकृपया पंचायत सेलेक्ट करके डाटा सिंक करें| डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें। ";
				}
				HideMenu();
				showAlertMsg(title,msg);
				return false;
			}
			if(data[1].equalsIgnoreCase("0"))
			{
				HideMenu();
				title="निश्चय के प्रकार की सूची नहीं मिली";
				msg="निश्चय के प्रकार की कोई सूची नहीं मिली |\nआप निश्चय के प्रकार की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं| डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें। ";
				showAlertMsg(title,msg);
				return false;
			}
			if(data[2].equalsIgnoreCase("0"))
			{
				HideMenu();
				if(pcode.trim().length()<3)
				{
					pcode="";
				}
				title="योजना सूची नहीं मिली";
				msg=pcode + " : इस पंचायत कोड के लिए कोई योजना सूची नहीं मिली |\nआप योजना की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं| डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें।";
				if(pcode.trim().length()<3)
				{
					msg +="\nकृपया पंचायत सेलेक्ट करके डाटा सिंक करें |";
				}
				showAlertMsg(title,msg);
				return false;
			}
			if(data[3].equalsIgnoreCase("0"))
			{
				HideMenu();
				title="मोड ऑफ़ पेमेंट की सूची नहीं मिली";
				msg="मोड ऑफ़ पेमेंट की कोई सूची नहीं मिली |\nआप मोड ऑफ़ पेमेंट की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं | डेटा को सिंक्रनाइज़ करें, फिर पुन: प्रयास करें | यदि कोई डेटा उपलब्ध नहीं है तो अपने व्यवस्थापक से संपर्क करें।";
				showAlertMsg(title,msg);
				return false;
			}
		}
		return true;
	}
	public void HideMenu()
	{
//		rlbtns1.setVisibility(View.GONE);
//		rlbtns2.setVisibility(View.GONE);
//		rlbtns3.setVisibility(View.GONE);
//		rlbtns4.setVisibility(View.GONE);
//		rlbtns5.setVisibility(View.GONE);
	}
	public void OnClick_goToLoginScreen(View v)
	{
		Intent i=new Intent(UserHomeActivity.this, LoginActivity.class);
		finish();
		startActivity(i);
	}
	public void OnClick_VoucherEntryMMGPJNY(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "1").commit();
		Intent i=new Intent(UserHomeActivity.this, VoucherEntryActivity.class);
		i.putExtra("NTYPE","1");
		startActivity(i);
	}
	public void OnClick_VoucherEditMMGPJNY(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "1").commit();
		Intent i=new Intent(UserHomeActivity.this, VoucherEntryListActivity.class);
		i.putExtra("EDIT","yes");
		i.putExtra("UserID",userid);
		i.putExtra("NTYPE","1");
		startActivity(i);
	}
	public void OnClick_VoucherUploadMMGPJNY(View v)
	{
		if(isuploading1==false)
		{
			_NischayCode="1";
			ShowAlertMSG("VOUCHER_ENTRY");
		}
	}
	public void OnClick_VoucherEntryMMGGNPNY(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
		Intent i=new Intent(UserHomeActivity.this, VoucherEntryActivity.class);
		i.putExtra("NTYPE","2");
		startActivity(i);
	}
	public void OnClick_VoucherEditMMGGNPNY(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
		Intent i=new Intent(UserHomeActivity.this, VoucherEntryListActivity.class);
		i.putExtra("EDIT","yes");
		i.putExtra("UserID",userid);
		i.putExtra("NTYPE","2");
		startActivity(i);
	}
	public void OnClick_VoucherUploadMMGGNPNY(View v)
	{
		if(isuploading1==false)
		{
			_NischayCode="2";
			ShowAlertMSG("VOUCHER_ENTRY");
		}
	}
	public void OnClick_PayJalEntry(View v)
	{
		if(CheckForDataExistForVarification()==true) {
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
			Intent i = new Intent(UserHomeActivity.this, PayJalActivity.class);
			i.putExtra("NTYPE", "1");
			startActivity(i);
		}
	}
	public void OnClick_PayJalEdit(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
		Intent i=new Intent(UserHomeActivity.this, PayJalListActivity.class);
		i.putExtra("NTYPE","1");
		i.putExtra("EDIT","yes");
		i.putExtra("UserID",userid);
		startActivity(i);
	}
	public void OnClick_PayJalUpload(View v)
	{
		if(isuploading3==false)
		{
			_NischayCode="1";
			ShowAlertMSG("PAYJAL");
		}
	}


	public void OnClick_GaliNaaliEntry(View v)
	{
		if(CheckForDataExistForVarification()==true) {
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
			Intent i = new Intent(UserHomeActivity.this, GaliNaliActivity.class);
			i.putExtra("NTYPE", "2");
			startActivity(i);
		}
	}
	public void OnClick_GaliNaaliEdit(View v)
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
		Intent i=new Intent(UserHomeActivity.this, GaliNaliListActivity.class);
		i.putExtra("NTYPE","2");
		i.putExtra("EDIT","yes");
		i.putExtra("UserID",userid);
		startActivity(i);
	}
	public void OnClick_GaliNaaliUpload(View v)
	{
		if(isuploading3==false)
		{
			_NischayCode="2";
			ShowAlertMSG("GALINAALI");
		}
	}

	public void ShowAlertMSG(final String whatto){
		if(isPhotoTaken(whatto))
		{
			if(Utiilties.isOnline(UserHomeActivity.this)) {
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("क्या आपको यकीन है ? इसे सर्वर पर अपलोड करना चाहते हैं ?");
				ab.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						sendPending(whatto);

					}
				});
				ab.setNegativeButton("अभी नहीं", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.dismiss();
						dialog.cancel();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
				ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						GlobalVariables.isOffline = false;
						Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						startActivity(I);
					}
				});
				ab.setNegativeButton("बाद में अपलोड करें", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.cancel();
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
		}
		else
		{
			TakePhotoMsg();
		}

	}

	public void sendPending(String WhoseRecords) {

		SQLiteHelper placeData = new SQLiteHelper(this);
		SQLiteDatabase db = placeData.getReadableDatabase();

		Cursor cursor=null;
		if(WhoseRecords.equalsIgnoreCase("VOUCHER_ENTRY"))
		{
			cursor = db
					.rawQuery(
							"SELECT * FROM VoucherEntryDetails WHERE CreatedBy='"+ userid +"' AND NischayCode='"+ _NischayCode + "'",
							null);

			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					String[] param = new String[18];
					param[0] = cursor.getString(cursor.getColumnIndex("WardCode"));
					param[1] = cursor.getString(cursor.getColumnIndex("NischayCode"));
					param[2] = cursor.getString(cursor.getColumnIndex("YojanaCode"));
					param[3] = cursor.getString(cursor.getColumnIndex("ReceiverName"));
					param[4] = cursor.getString(cursor.getColumnIndex("MobileNum"));
					param[5] = cursor.getString(cursor.getColumnIndex("PaymentForWhat"));

					param[6] = cursor.getString(cursor.getColumnIndex("PaymentModeCode"));
					param[7] = cursor.getString(cursor.getColumnIndex("TransactionNumber"));
					param[8] = cursor.getString(cursor.getColumnIndex("PaymentDate"));
					param[9] = cursor.getString(cursor.getColumnIndex("Amount"));
					param[10] = cursor.getString(cursor.getColumnIndex("CreatedBy"));
					param[11] = cursor.getString(cursor.getColumnIndex("CreatedDate"));
					param[12] = ""; //status is null

					String appver = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("APP_VERSION", "");
					String devicedetail = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DEVICE_DETAILS", "");

					param[13] = appver;
					param[14] = devicedetail;
					param[15] = cursor.getString(cursor.getColumnIndex("Remarks"));
					param[16] = cursor.getString(cursor.getColumnIndex("FYear"));
					param[17] = cursor.getString(cursor.getColumnIndex("id"));

					new UploadPendingVoucherEntry().execute(param);
				}
			} else {
				Toast.makeText(UserHomeActivity.this,"आपके पास कोई अपलोड लंबित नहीं है !", Toast.LENGTH_SHORT).show();

			}
			cursor.close();
		}
		if(WhoseRecords.equalsIgnoreCase("PAYJAL"))
		{

			if(isPhotoTaken(WhoseRecords))
			{
				cursor = db.rawQuery("SELECT id, FYear, PanCode, PanName, WardCode, WardName, NischayCode, YojanaCode, YojanaTypeNAME, CurrentPhysicalStatus, isBoringStarted, BoringDepth, ISIMarkBoring, UPVCPipeCMLNo, isMotorPumpStatus, ISIMarkMotorPump, MotorPumpCapacity, isStaggingDone, TypeOfStagging, HeightOfStagging, TenkiCapacity, TenkiQuantity, isVitranPranali, TotalLength, DepthFromSurfaceLevel, ISIMarkVitranPranali, CMLNumber, isElectrictyConnection, isElectConnection, ConsumerNumber, isGrihJalSunyojan, TotalHouseNum, WaterSupplyStatus, JalStumbhKaNirman, TypeOfPipe, FerulKaUpyog, isProjectCompleted, ProjectCompleteionDate, Remarks, CreatedBy, CreatedDate, Latitude, Longitude, InspectorName, InspectorPost, extraColumn1, extraColumn2, extraColumn3, extraColumn4, extraColumn5, OtherYojanaName, OtherMotorPumpCapacity , BoringFtMt, StageFtMt, VitranFtMt, VitranDepthFtMt, VillageCode FROM PayJaltbl WHERE CreatedBy='"+ userid +"' AND Latitude Is Not Null",null);
				Cursor cursorPic1_2 = db.rawQuery("SELECT Photo1, Photo2 FROM PayJaltbl WHERE CreatedBy='"+ userid +"' AND Latitude Is Not Null",null);
				Cursor cursorPic3_4 = db.rawQuery("SELECT Photo3, Photo4 FROM PayJaltbl WHERE CreatedBy='"+ userid +"' AND Latitude Is Not Null",null);

				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {
						String[] param = new String[45];
						if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							param = new String[45];
						}
						else if(CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param = new String[45];
							}
						}
						param[0] = cursor.getString(cursor.getColumnIndex("id"));
						param[1] = cursor.getString(cursor.getColumnIndex("YojanaCode"));

						try {
							if (cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("फीट")) {
								String ft = cursor.getString(cursor.getColumnIndex("BoringDepth"));
								double value = Double.parseDouble(ft);
								double meters = value / 3.2808;
								//param[2] = String.valueOf(meters);
								param[2] = new DecimalFormat("##.##").format(meters);
								Log.e("BoringFT", ft);
								Log.e("BoringMT", param[2]);
							} else if (cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("मीटर")) {
								param[2] = cursor.getString(cursor.getColumnIndex("BoringDepth"));
							}
						}
						catch (Exception e)
						{
							param[2]="0.00";
						}

						param[3] = cursor.getString(cursor.getColumnIndex("ISIMarkBoring"));
						param[4] = cursor.getString(cursor.getColumnIndex("UPVCPipeCMLNo"));
						param[5] = cursor.getString(cursor.getColumnIndex("ISIMarkMotorPump"));

						param[6] = cursor.getString(cursor.getColumnIndex("MotorPumpCapacity"));
						param[7] = cursor.getString(cursor.getColumnIndex("TypeOfStagging"));


						try
						{
							if(cursor.getString(cursor.getColumnIndex("StageFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("HeightOfStagging"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[8] = String.valueOf(meters);
								param[8] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("StageFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[8] = cursor.getString(cursor.getColumnIndex("HeightOfStagging"));
							}
						}
						catch (Exception e)
						{
							param[8]="0.00";
						}

						param[9] = cursor.getString(cursor.getColumnIndex("TenkiCapacity"));
						param[10] = cursor.getString(cursor.getColumnIndex("TenkiQuantity"));

						try {
							if (cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("फीट")) {
								String ft = cursor.getString(cursor.getColumnIndex("TotalLength"));
								double value = Double.parseDouble(ft);
								double meters = value / 3.2808;
								//param[11] = String.valueOf(meters);
								param[11] = new DecimalFormat("##.##").format(meters);
							} else if (cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("मीटर")) {
								param[11] = cursor.getString(cursor.getColumnIndex("TotalLength"));
							}
						}
						catch (Exception e)
						{
							param[11]="0.00";
						}


						try{
							if(cursor.getString(cursor.getColumnIndex("VitranDepthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("DepthFromSurfaceLevel"));

								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[12] = String.valueOf(meters);
								param[12] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("VitranDepthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[12] = cursor.getString(cursor.getColumnIndex("DepthFromSurfaceLevel"));
							}
						}
						catch (Exception e)
						{
							param[12]="0.00";
						}


						param[13] = cursor.getString(cursor.getColumnIndex("ISIMarkVitranPranali"));
						param[14] = cursor.getString(cursor.getColumnIndex("CMLNumber"));

						param[15] = cursor.getString(cursor.getColumnIndex("isElectConnection"));
						param[16] = cursor.getString(cursor.getColumnIndex("ConsumerNumber"));
						param[17] = cursor.getString(cursor.getColumnIndex("TotalHouseNum"));
						param[18] = cursor.getString(cursor.getColumnIndex("WaterSupplyStatus"));
						param[19] = cursor.getString(cursor.getColumnIndex("JalStumbhKaNirman"));
						param[20] = cursor.getString(cursor.getColumnIndex("TypeOfPipe"));
						param[21] = cursor.getString(cursor.getColumnIndex("FerulKaUpyog"));
						param[22] = cursor.getString(cursor.getColumnIndex("CurrentPhysicalStatus"));


						param[23] = cursor.getString(cursor.getColumnIndex("Remarks"));


						param[24] = cursor.getString(cursor.getColumnIndex("CreatedBy"));
						param[25] = "M"; 			//entry mode -web or movile
						param[26] = cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate"));

						while (cursorPic1_2.moveToNext()){


							String photo1=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";
							String pic1="",pic2="";
							if (!photo1.equals("")){
								pic1=resizeBase64Image(photo1);
							}

//							param[27] = cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
//									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";
							param[27] =pic1;
							String photo2=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";

							if(!photo2.equals("")){
								pic2=resizeBase64Image(photo2);
							}
							param[28] = pic2;
						}
						cursorPic1_2.close();

						while (cursorPic3_4.moveToNext()){
							String pic3="",pic4="";
							String photo3=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";
							if(!photo3.equals("")){
								pic3=resizeBase64Image(photo3);
							}

							param[29] = pic3;

							String photo4=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

							if(!photo4.equals("")){
								pic4=resizeBase64Image(photo4);
							}

							param[30] =pic4;
						}
						cursorPic3_4.close();
//						param[27] = cursor.isNull(cursor.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";
//
//						param[28] = cursor.isNull(cursor.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";
//
//						param[29] = cursor.isNull(cursor.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";
//
//						param[30] = cursor.isNull(cursor.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

						param[31] = cursor.getString(cursor.getColumnIndex("Latitude"));
						param[32] = cursor.getString(cursor.getColumnIndex("Longitude"));



						String appver = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("APP_VERSION", "");
						String devicedetail = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DEVICE_DETAILS", "");

						param[33] = appver;
						param[34] = devicedetail;


						if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							param[35] = cursor.getString(cursor.getColumnIndex("InspectorName"));
							param[36] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
							param[37] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));

							param[38] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
							param[39] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
							param[40] = cursor.getString(cursor.getColumnIndex("PanCode"));
							param[41] = cursor.getString(cursor.getColumnIndex("WardCode"));
							param[42] = cursor.getString(cursor.getColumnIndex("VillageCode"));
						}
						else if(CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param[35] = cursor.getString(cursor.getColumnIndex("InspectorName"));
								param[36] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
								param[37] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));

								param[38] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
								param[39] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
								param[40] = cursor.getString(cursor.getColumnIndex("PanCode"));
								param[41] = cursor.getString(cursor.getColumnIndex("WardCode"));
								param[42] = cursor.getString(cursor.getColumnIndex("VillageCode"));
							}
							else if(work_type.equalsIgnoreCase("PV")) {
//								param[35] = cursor.getString(cursor.getColumnIndex("InspectorName"));
//								param[36] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
//								param[37] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));
//
//								param[38] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
//								param[39] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
//								param[40] = cursor.getString(cursor.getColumnIndex("PanCode"));
//								param[41] = cursor.getString(cursor.getColumnIndex("WardCode"));
								param[42] = cursor.getString(cursor.getColumnIndex("VillageCode"));
							}
						}

						param[43] = cursor.getString(cursor.getColumnIndex("extraColumn3"));
						param[44] = cursor.getString(cursor.getColumnIndex("extraColumn4"));
						new UploadPendingPayJalPhysicalVer().execute(param);
					}
				} else {
					Toast.makeText(UserHomeActivity.this,"आपके पास कोई अपलोड लंबित नहीं है !", Toast.LENGTH_SHORT).show();

				}
				cursor.close();
			}
			else
			{
				TakePhotoMsg();
			}

		}
		if(WhoseRecords.equalsIgnoreCase("GALINAALI"))
		{
			if(isPhotoTaken(WhoseRecords))
			{
				cursor = db.rawQuery("SELECT id, FYear, PanCode, WardCode, wardName, NischayCode, YojanaCode, CurrentPhysicalStatus, currentPhysical_Name, RoadType, PathRoadType, NaliType, JalNikasi, TotalRoadDistance_Length, TotalRoadDistance_Breadth, TotalRoadDistance_Fat, TotalPathRoadDistance_Breadth, TotalNaliDistance_Length, TotalNali_Breadth, TotalNali_Gharai, SokhtaKiSankhya, MittiKrya, HugePipe, ProjectCompleteionDate, Remarks, CreatedDate, CreatedBy, Latitude, Longitude, InspectorName, InspectorPost, extraColumn1, extraColumn2, extraColumn3, extraColumn4, extraColumn5, OtherYojanaName, OtherMotorPumpCapacity, TotalRoadDistance_LengthFtMt, TotalRoadDistance_BreadthFtMt, TotalPathRoadDistance_BreadthFtMt, TotalNaliDistance_LengthFtMt, TotalNali_BreadthFtMt, TotalNali_GharaiFtMt, MittiKryaFtMt, VillageCode FROM GaliNali where CreatedBy=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{userid});
				Cursor cursorPic1_2 = db.rawQuery("SELECT Photo1, Photo2 FROM GaliNali where CreatedBy=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{userid});
				Cursor cursorPic3_4 = db.rawQuery("SELECT Photo3, Photo4 FROM GaliNali where CreatedBy=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{userid});
				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {

						String[] param = new String[39];
						if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							param = new String[39];
						}
						else if(CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param = new String[39];
							}
						}
						param[0] = cursor.getString(cursor.getColumnIndex("id"));
						param[1] = cursor.getString(cursor.getColumnIndex("YojanaCode"));
						param[2] = cursor.getString(cursor.getColumnIndex("RoadType"));

						try{
							if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_LengthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Length"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[3] = String.valueOf(meters);
								param[3] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_LengthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[3] = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Length"));
							}
						}
						catch (Exception e)
						{
							param[3]="0.00";
						}

						try
						{
							if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_BreadthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Breadth"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[4] = String.valueOf(meters);
								param[4] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_BreadthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[4] = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Breadth"));
							}
						}
						catch (Exception e)
						{
							param[4]="0.00";
						}

						param[5] = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Fat"));

						param[6] = cursor.getString(cursor.getColumnIndex("PathRoadType"));

						try{
							if(cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_BreadthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_Breadth"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[7] = String.valueOf(meters);
								param[7] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_BreadthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[7] = cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_Breadth"));
							}
						}
						catch (Exception e)
						{
							param[7]="0.00";
						}

						param[8] = cursor.getString(cursor.getColumnIndex("NaliType"));


						try{
							if(cursor.getString(cursor.getColumnIndex("TotalNaliDistance_LengthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalNaliDistance_Length"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[9] = String.valueOf(meters);
								param[9] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalNaliDistance_LengthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[9] = cursor.getString(cursor.getColumnIndex("TotalNaliDistance_Length"));
							}
						}
						catch (Exception e)
						{
							param[9]="0.00";
						}


						try
						{
							if(cursor.getString(cursor.getColumnIndex("TotalNali_BreadthFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalNali_Breadth"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[10] = String.valueOf(meters);
								param[10] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalNali_BreadthFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[10] = cursor.getString(cursor.getColumnIndex("TotalNali_Breadth"));
							}
						}
						catch (Exception e)
						{
							param[10]="0.00";
						}

						try
						{
							if(cursor.getString(cursor.getColumnIndex("TotalNali_GharaiFtMt")).equalsIgnoreCase("फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("TotalNali_Gharai"));
								double value = Double.parseDouble(ft);
								double meters = value/3.2808;
								//param[11] = String.valueOf(meters);
								param[11] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("TotalNali_GharaiFtMt")).equalsIgnoreCase("मीटर"))
							{
								param[11] = cursor.getString(cursor.getColumnIndex("TotalNali_Gharai"));
							}
						}
						catch (Exception e)
						{
							param[11]="0.00";
						}

						param[12] = cursor.getString(cursor.getColumnIndex("SokhtaKiSankhya"));



						try{
							if(cursor.getString(cursor.getColumnIndex("MittiKryaFtMt")).equalsIgnoreCase("घन फीट"))
							{
								String ft = cursor.getString(cursor.getColumnIndex("MittiKrya"));
								double value = Double.parseDouble(ft);
								//1 ft³ = 0.02831685 m³
								double meters = value*0.02831685;
								//param[4] = String.valueOf(meters);
								param[13] = new DecimalFormat("##.##").format(meters);
							}
							else if(cursor.getString(cursor.getColumnIndex("MittiKryaFtMt")).equalsIgnoreCase("घन मीटर"))
							{
								param[13] = cursor.getString(cursor.getColumnIndex("MittiKrya"));
							}
						}
						catch (Exception e)
						{
							param[13]="0.00";
						}
						param[14] = cursor.getString(cursor.getColumnIndex("JalNikasi"));
						param[15] = cursor.getString(cursor.getColumnIndex("HugePipe"));
						param[16] = cursor.getString(cursor.getColumnIndex("CurrentPhysicalStatus"));

						param[17] = cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate"));

						param[18] = cursor.getString(cursor.getColumnIndex("Remarks"));

						param[19] = cursor.getString(cursor.getColumnIndex("CreatedBy"));
						param[20] = cursor.getString(cursor.getColumnIndex("CreatedDate"));

						while (cursorPic1_2.moveToNext()){
							String pic1="",pic2="";
							String photo1=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";

							if (!photo1.equals("")){
								pic1=resizeBase64Image(photo1);
							}


							param[21] = pic1;
							String photo2=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";

							if (!photo2.equals("")){
								pic2=resizeBase64Image(photo2);
							}


							param[22] = pic2;
						}
						cursorPic1_2.close();

						while (cursorPic3_4.moveToNext()){
							String pic3="",pic4="";


							String phot3=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";
							if (!phot3.equals("")){
								pic3=resizeBase64Image(phot3);
							}


							param[23] = pic3;


							String photo4=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

							if (!photo4.equals("")){
								pic4=resizeBase64Image(photo4);

							}

							param[24] = pic4;
						}
						cursorPic3_4.close();

//						param[21] = cursor.isNull(cursor.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";
//
//						param[22] = cursor.isNull(cursor.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";
//
//						param[23] = cursor.isNull(cursor.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";
//
//						param[24] = cursor.isNull(cursor.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
//								cursor.getBlob(cursor.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

						param[25] = cursor.getString(cursor.getColumnIndex("Latitude"));
						param[26] = cursor.getString(cursor.getColumnIndex("Longitude"));

						String appver = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("APP_VERSION", "");
						String devicedetail = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DEVICE_DETAILS", "");

						param[27] = appver;
						param[28] = devicedetail;


						if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							param[29] = cursor.getString(cursor.getColumnIndex("InspectorName"));
							param[30] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
							param[31] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));

							param[32] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
							param[33] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
							param[34] = cursor.getString(cursor.getColumnIndex("PanCode"));
							param[35] = cursor.getString(cursor.getColumnIndex("WardCode"));
							param[36] = cursor.getString(cursor.getColumnIndex("VillageCode"));
						}
						else if(CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param[29] = cursor.getString(cursor.getColumnIndex("InspectorName"));
								param[30] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
								param[31] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));

								param[32] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
								param[33] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
								param[34] = cursor.getString(cursor.getColumnIndex("PanCode"));
								param[35] = cursor.getString(cursor.getColumnIndex("WardCode"));
								param[36] = cursor.getString(cursor.getColumnIndex("VillageCode"));
							}
							else if(work_type.equalsIgnoreCase("PV")) {
//								param[29] = cursor.getString(cursor.getColumnIndex("InspectorName"));
//								param[30] = cursor.getString(cursor.getColumnIndex("InspectorPost"));
//								param[31] = cursor.getString(cursor.getColumnIndex("OtherYojanaName"));
//
//								param[32] = cursor.getString(cursor.getColumnIndex("extraColumn1"));
//								param[33] = cursor.getString(cursor.getColumnIndex("extraColumn2"));
//								param[34] = cursor.getString(cursor.getColumnIndex("PanCode"));
//								param[35] = cursor.getString(cursor.getColumnIndex("WardCode"));
								param[36] = cursor.getString(cursor.getColumnIndex("VillageCode"));
							}
						}
						param[37] = cursor.getString(cursor.getColumnIndex("extraColumn3"));
						param[38] = cursor.getString(cursor.getColumnIndex("extraColumn4"));
						new UploadPendingGaliNaaliData().execute(param);

					}
				}else {
					Toast.makeText(UserHomeActivity.this,"आपके पास कोई अपलोड लंबित नहीं है !", Toast.LENGTH_SHORT).show();

				}
				cursor.close();
			}
			else
			{
				TakePhotoMsg();
			}

		}



		db.close();
		ShowPending();

	}

	public boolean isPhotoTaken(String WhoseRecords){

		SQLiteHelper placeData = new SQLiteHelper(this);
		SQLiteDatabase db = placeData.getReadableDatabase();

		Cursor cursor=null;
		if(WhoseRecords.equalsIgnoreCase("PAYJAL")) {
			cursor = db
					.rawQuery(
							"SELECT CreatedBy FROM PayJaltbl WHERE CreatedBy='" + userid + "' AND Photo1 IS NOT NULL",
							null);

			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					return true;
				}
			}
		}
		if(WhoseRecords.equalsIgnoreCase("GALINAALI")) {
			cursor = db
					.rawQuery(
							"SELECT CreatedBy FROM GaliNali WHERE CreatedBy='" + userid + "' AND Photo1 IS NOT NULL",
							null);

			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					return true;
				}
			}
		}
		return false;
	}

	private class UploadPendingVoucherEntry extends AsyncTask<String, Void, String> {

		String eid = "-1";
		private final ProgressDialog dialog = new ProgressDialog(UserHomeActivity.this);

		@Override
		protected void onPreExecute() {

			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("अपलोड हो रहा है ...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... param) {

			eid = param[17];  // id
			String res = WebServiceHelper.UploadVoucherEntry(param);

			return res;
		}

		@Override
		protected void onPostExecute(String result) {


			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if(result!=null)
			{
				if (!result.toString().equals("0")) {
					// if (result.toString().equals("1")) {
					SQLiteHelper placeData = new SQLiteHelper(UserHomeActivity.this);
					Log.e("eid",eid);
					placeData.deletePendingVoucherEntryDetails(eid);

					ShowPending();


					Toast.makeText(getApplicationContext(),"सफलतापूर्वक अपलोड किया गया", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class UploadPendingPayJalPhysicalVer extends AsyncTask<String, Void, String> {

		String eid = "-1";
		private final ProgressDialog dialog = new ProgressDialog(UserHomeActivity.this);

		@Override
		protected void onPreExecute() {

			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("अपलोड हो रहा है ...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... param) {

			eid = param[0];  // id
			String res = WebServiceHelper.UploadPayJalPhysicalVer(UserHomeActivity.this,param);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {

			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if(result!=null)
			{
				if (result.toString().equals("1")) {
					// if (result.toString().equals("1")) {
					SQLiteHelper placeData = new SQLiteHelper(UserHomeActivity.this);
					Log.e("eid",eid);

					//---------------KEEP THIS RECORD--------------------------
					placeData.deletePayJalPhysicalVer(eid);
					ShowPending();

					Toast.makeText(getApplicationContext(),"सफलतापूर्वक अपलोड किया गया", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class UploadPendingGaliNaaliData extends AsyncTask<String, Void, String> {

		String eid = "-1";
		private final ProgressDialog dialog = new ProgressDialog(UserHomeActivity.this);

		@Override
		protected void onPreExecute() {

			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("अपलोड हो रहा है ...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... param) {

			eid = param[0];  // id
			String res = WebServiceHelper.UploadPendingGaliNaaliData(UserHomeActivity.this,param);

			return res;
		}

		@Override
		protected void onPostExecute(String result) {

			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if(result!=null)
			{
				if (result.toString().equals("1")) {
					// if (result.toString().equals("1")) {
					SQLiteHelper placeData = new SQLiteHelper(UserHomeActivity.this);
					Log.e("eid",eid);
					placeData.deletePendingGaliNaali(eid);

					ShowPending();

					Toast.makeText(getApplicationContext(),"सफलतापूर्वक अपलोड किया गया", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ShowPending();
	}

	/**
	 * Function to load the spinner data from SQLite database
	 * */


	private void ShowPending() {

		final TextView lblcount1 = (TextView) findViewById(R.id.lblcount1); //voucher pay jal
		final TextView lblcount4 = (TextView) findViewById(R.id.lblcount4); //physical ver payjal

		final TextView lblcount2 = (TextView) findViewById(R.id.lblcount2); //voucher gli-naali
		final TextView lblcount5 = (TextView) findViewById(R.id.lblcount5); //phy gli-naali

		try {
			SQLiteHelper placeData = new SQLiteHelper(this);
			SQLiteDatabase db = placeData.getReadableDatabase();

			int PendingUpload1 = 0;
			int PendingUpload2 = 0;
			int PendingUpload3 = 0;
			int PendingUpload4 = 0;

			//Cursor cur = db.rawQuery("Select (select count(*) from VoucherEntryDetails WHERE NischayCode='1' AND CreatedBy='"+userid+"' ) as t1 ,(select count(*) from VoucherEntryDetails WHERE NischayCode='2' AND CreatedBy='"+userid+"') as t2,(select count(*) from GaliNali WHERE CreatedBy='"+userid+"' ) as t3,(select count(*) from PayJaltbl WHERE CreatedBy='"+userid+"' ) as t4" , null);
			Cursor cur = db.rawQuery("Select (select count(*) from VoucherEntryDetails WHERE NischayCode='1' AND CreatedBy='"+userid+"') as t1 ,(select count(*) from VoucherEntryDetails WHERE NischayCode='2' AND CreatedBy='"+userid+"') as t2,(select count(*) from GaliNali WHERE CreatedBy='"+userid+"' AND Latitude IS Not Null ) as t3,(select count(*) from PayJaltbl WHERE CreatedBy='"+userid+"' AND Latitude IS Not Null )  as t4" , null);

			while (cur.moveToNext()) {
				PendingUpload1 = cur.getInt((cur.getColumnIndex("t1")));
				PendingUpload4 = cur.getInt((cur.getColumnIndex("t4")));

				PendingUpload2 = cur.getInt((cur.getColumnIndex("t2")));
				PendingUpload3 = cur.getInt((cur.getColumnIndex("t3")));
			}
			//voucher entry pay jal
			lblcount1.setText(String.valueOf(PendingUpload1));

			//voucher entry gali naali
			lblcount2.setText(String.valueOf(PendingUpload2));

			lblcount5.setText(String.valueOf(PendingUpload3));

			lblcount4.setText(String.valueOf(PendingUpload4));
		} catch (Exception ex) {
			lblcount1.setText("0");
			lblcount2.setText("0");
			lblcount5.setText("0");
			lblcount4.setText("0");
		}
	}



	void showToast(CharSequence msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(this);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setIcon(R.drawable.logo);
			ab.setTitle("बंद करें");
			ab.setMessage("क्या आप वाकई इस एप्लिकेशन को बंद करना चाहते हैं?");
			ab.setPositiveButton("[ नही ]", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			});

			ab.setNegativeButton("[ हाॅं ]", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					finish();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void TakePhotoMsg(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.i1small);
		builder.setTitle(" प्रथम फोटो ?");
		builder.setMessage("आप फोटो के बिना रिकॉर्ड अपलोड नहीं कर सकते|\nकृपया कम से कम एक पहली तस्वीर लें ")

				.setCancelable(false)
				.setPositiveButton("[ ठीक ]", new DialogInterface.OnClickListener() {
					public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
						dialog.dismiss();
						dialog.cancel();
					}
				});

		final AlertDialog alert = builder.create();
		alert.show();
	}

	public String getCurrentDate(long milliSeconds) {

		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

		return formatter.format(milliSeconds);
	}

	public class GetFyear extends AsyncTask<Void, Void, ArrayList<Fyear>> {

		public GetFyear() {

		}

		private final ProgressDialog dialog = new ProgressDialog(UserHomeActivity.this);

		private final AlertDialog alertDialog = new AlertDialog.Builder(UserHomeActivity.this).create();

		@Override
		protected void onPreExecute() {
			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("Loading Financial Year.\nPlease wait...");
			this.dialog.show();
		}

		@Override
		protected ArrayList<Fyear> doInBackground(Void... params) {

			ArrayList<Fyear> res1 = WebServiceHelper.loadFYearList();

			return res1;
		}

		@Override
		protected void onPostExecute(ArrayList<Fyear> result) {

			if (this.dialog.isShowing()) {

				this.dialog.dismiss();

				SQLiteHelper placeData = new SQLiteHelper(UserHomeActivity.this);
				placeData.insertFyear(result);

				Intent iUserHome = new Intent(getApplicationContext(), UserHomeActivity.class);
				startActivity(iUserHome);
				finish();
			}
		}
	}

	public void ShowReport(final View view) {

		String userrole = CommonPref.getUserDetails(getApplicationContext()).get_Role();

		if (!Utiilties.isOnline(UserHomeActivity.this)) {
			Toast.makeText(UserHomeActivity.this, " No Internet connection !\n Please check your internet connectivity.", Toast.LENGTH_SHORT).show();

		} else {

			if (userrole.equals("ADM") || userrole.equals("DIVADM") || userrole.equals("INS")) {


			} else if (userrole.equals("DSTADM") || userrole.equals("DM")) {

				String distcode = CommonPref.getUserDetails(getApplicationContext()).get_DistCode();
				String distname = CommonPref.getUserDetails(getApplicationContext()).get_DistName();
				String[] data = { distname, distcode };
				CommonPref.setFragmentData(getApplicationContext(), data);
			}
		}
	}

	public void OnClick_goSyncAllData(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {
			if (lnAllDownLoadIMG.getVisibility() == View.VISIBLE) {
				lnAllDownLoadIMG.setVisibility(View.GONE);
				lbl0.setGravity(Gravity.CENTER);
			} else {
				lnAllDownLoadIMG.setVisibility(View.VISIBLE);
				lnAllDownLoadIMG.bringToFront();
				lbl0.setGravity(Gravity.BOTTOM);
			}
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					dialog.cancel();
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});

			ab.setNegativeButton("अभी नहीं", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.dismiss();
					dialog.cancel();
				}
			});
			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}
	}

	public void OnClick_SyncWard(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {
			String d="0",b="0",p="0";
			d=CommonPref.getUserDetails(UserHomeActivity.this).get_DistCode();
			b=CommonPref.getUserDetails(UserHomeActivity.this).get_BlockCode();
			p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();

			if(p.trim().length()>2) {
				progressSYcn.setVisibility(View.VISIBLE);

				new LoadWARDData(d, b, p).execute(); //for panadm
			}
			else if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
			{
				//for not panadm
				if(_varpanchayatID.trim().length()>2) {
					progressSYcn.setVisibility(View.VISIBLE);

					new LoadWARDData(_vardistrictID,_varblockID, _varpanchayatID).execute();
				}
				else
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
					ab.setMessage("कृपया पंचायत का चयन करेंं");
					ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}
			}
			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("कृपया पंचायत का चयन करेंं");
				ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
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
		}
	}

	public void OnClick_SyncNischay(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {

			progressSYcn.setVisibility(View.VISIBLE);
			new LoadNischayData().execute();
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
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
		}
	}

	public void OnClick_SyncYojana(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {


			String p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();
			if(p.trim().length()>2) {
				progressSYcn.setVisibility(View.VISIBLE);
				new LoadYojanaData(p).execute();
			}
			else if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
			{
				//for not panadm
				if(_varpanchayatID.trim().length()>2) {
					progressSYcn.setVisibility(View.VISIBLE);
					new LoadYojanaData(_varpanchayatID).execute();
				}
				else
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
					ab.setMessage("कृपया पंचायत का चयन करेंं");
					ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}
			}

			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("कृपया पंचायत का चयन करेंं");
				ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
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
		}
	}

	public void OnClick_SyncPayMode(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {

			progressSYcn.setVisibility(View.VISIBLE);
			new LoadPayModeData().execute();
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("बाद में अपलोड करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.cancel();
					dialog.dismiss();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}
	}

	public void OnClick_SyncPayJal(View v){
		if(Utiilties.isOnline(UserHomeActivity.this)) {

			String p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();

			progressSYcn.setVisibility(View.VISIBLE);
			if(p.trim().length()>2) {
				progressSYcn.setVisibility(View.VISIBLE);
				new LoadPayJalData(p).execute(); //for panadm
			}
			else if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
			{
				//for not panadm
				if(_varpanchayatID.trim().length()>2) {
					progressSYcn.setVisibility(View.VISIBLE);
					new LoadPayJalData(_varpanchayatID).execute();
				}
				else
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
					ab.setMessage("कृपया पंचायत का चयन करेंं");
					ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}
			}
			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("कृपया पंचायत का चयन करेंं");
				ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}

		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("बाद में अपलोड करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.cancel();
					dialog.dismiss();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}
	}

	public void OnClick_SyncGaliNaali(View v) {
		if(Utiilties.isOnline(UserHomeActivity.this)) {

			String p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();

			progressSYcn.setVisibility(View.VISIBLE);
			if(p.trim().length()>2) {
				progressSYcn.setVisibility(View.VISIBLE);
				new LoadGaliNaaliData(p).execute(); //for panadm
			}
			else if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
			{
				//for not panadm
				if(_varpanchayatID.trim().length()>2) {
					progressSYcn.setVisibility(View.VISIBLE);
					new LoadGaliNaaliData( _varpanchayatID).execute();
				}
				else
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
					ab.setMessage("कृपया पंचायत का चयन करेंं");
					ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}
			}
			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("कृपया पंचायत का चयन करेंं");
				ab.setPositiveButton("[ठीक है ]", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("बाद में अपलोड करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.cancel();
					dialog.dismiss();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}
	}

	private class LoadWARDData extends AsyncTask<String, String, ArrayList<ward>> {
		String distCode="";
		String blockCode="";
		String panCode="";

		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

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

			WardList = WebServiceHelper.loadWardList(panCode);
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

			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {

					long i=localDBHelper.setWardLocal(result);
					Log.e("WARD",""+result.size());
					//lblWardCount.setText(result.size());
//					String msg="Syncronize yojana data";
//					TextToSpeech(msg);

//					BlinkImageView(downYojana);
					setTintColorForDownloadImage();
					if(i>0)
					{
						downWard.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downWard.clearAnimation();
						Toast.makeText(getApplicationContext(), "वार्ड लोड हो गया ", Toast.LENGTH_LONG).show();
					}
				} else {

					Toast.makeText(getApplicationContext(), "वार्ड लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
					title="वार्ड सूची नहीं मिली";
					msg=panCode + " : इस पंचायत कोड के लिए कोई वार्ड सूची नहीं मिली |\nआप वार्ड की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं";
					showAlertMsg(title,msg);
				}
			}
		}
	}

	private class LoadNischayData extends AsyncTask<String, String, ArrayList<SCHEME>> {

		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);


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
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {
					Log.e("SCHEME:NISCHAY",""+result.size());
					//lblNischayCount.setText(result.size());
					long i=localDBHelper.setNischayLocal(result);
					if(i>0)
					{
						Toast.makeText(getApplicationContext(), "निश्चय टाइप लोड हो गया  ", Toast.LENGTH_LONG).show();
					}
				} else {
					title="निश्चय के प्रकार की सूची नहीं मिली";
					msg="निश्चय के प्रकार की कोई सूची नहीं मिली |\nआप निश्चय के प्रकार की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं";
					showAlertMsg(title,msg);
					Toast.makeText(getApplicationContext(), "निश्चय टाइप लोड नहीं हो पाया ", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private class LoadYojanaData extends AsyncTask<String, String, ArrayList<YOJANA>> {

		String panCode="";
		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

		LoadYojanaData(String _pancode) {
			this.panCode=_pancode;
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading Yojana Type...");
			dialog.show();
		}

		@Override
		protected ArrayList<YOJANA> doInBackground(String... param) {

			YojanaList = WebServiceHelper.loadYojanaList(panCode);
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
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {

					//lblYojanaCount.setText(result.size());
					Log.e("YOJANA",""+result.size());
					long i=localDBHelper.setYojanaLocal(result,panCode);
					if(i>0)
					{
						downYojana.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downYojana.clearAnimation();
						//BlinkImageView(downPayMode);
						Toast.makeText(getApplicationContext(), "योजना का नाम लोड हो गया", Toast.LENGTH_LONG).show();
					}
					else
					{

						Toast.makeText(getApplicationContext(), "योजना का नाम लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {

					title="योजना सूची नहीं मिली";
					msg=panCode + " : इस पंचायत कोड के लिए कोई योजना सूची नहीं मिली |\nआप योजना की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं";
					showAlertMsg(title,msg);

					Toast.makeText(getApplicationContext(), panCode +" : इस पंचायत कोड के लिए कोई योजना सूची नहीं मिली", Toast.LENGTH_LONG).show();

				}
			}
		}
	}

	private class LoadPayModeData extends AsyncTask<String, String, ArrayList<MODEOFPAYMENT>> {


		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

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
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {
					Log.e("PayMode",""+result.size());
					//lblPayModeCount.setText(result.size());
					long i=localDBHelper.setPayModeLocal(result);
					if(i>0)
					{
						downPayMode.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downPayMode.clearAnimation();
						Toast.makeText(getApplicationContext(), "मोड ऑफ़ पेमेंट लोड हो गया", Toast.LENGTH_LONG).show();
					}
				} else {
					title="मोड ऑफ़ पेमेंट की सूची नहीं मिली";
					msg="मोड ऑफ़ पेमेंट की कोई सूची नहीं मिली |\nआप मोड ऑफ़ पेमेंट की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं";
					showAlertMsg(title,msg);
					Toast.makeText(getApplicationContext(), "मोड ऑफ़ पेमेंट लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private class LoadPayJalData extends AsyncTask<String, String, ArrayList<WorkProgressReportProperty>> {

		String panCode;

		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

		LoadPayJalData(String _panCode) {
			this.panCode=_panCode;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog.setMessage("पेय जल के सभी भौतिक और वित्तीय डेटा लोड हो रहा है...");
				dialog.show();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		protected ArrayList<WorkProgressReportProperty> doInBackground(String... param) {
			PayJalRecordsList= WebServiceHelper.getPayJalRecords(UserHomeActivity.this,panCode,CommonPref.getUserDetails(UserHomeActivity.this).get_UserID());
			return PayJalRecordsList;
		}

		@Override
		protected void onPostExecute(ArrayList<WorkProgressReportProperty> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", ex.getMessage());
			}
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {
					Log.e("PayJalData",""+result.size());
					//lblPayModeCount.setText(result.size());
					long i=localDBHelper.setPayJalRecordsLocal(result);
					if(i>0)
					{
						downPayJal.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downPayJal.clearAnimation();
						Toast.makeText(getApplicationContext(), "पेय जल के सभी भौतिक और वित्तीय डेटा लोड हो गया", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(getApplicationContext(), "पेय जल भौतिक और वित्तीय डेटा Insert नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "पेय जल भौतिक और वित्तीय डेटा नहीं मिला", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "पेय जल के भौतिक और वित्तीय डेटा लोड नहीं हो पाया : Null Result", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "पेय जल के भौतिक और वित्तीय डेटा लोड नहीं हो पाया : Internet Slow or Server Busy", Toast.LENGTH_LONG).show();
			}
		}
	}

	private class LoadGaliNaaliData extends AsyncTask<String, String, ArrayList<WorkProgressReportGNProperty>> {

		String panCode;
		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

		LoadGaliNaaliData(String _panCode) {
			this.panCode=_panCode;
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("गली- नाली के सभी भौतिक और वित्तीय डेटा लोड हो रहा है...");
			dialog.show();
		}

		@Override
		protected ArrayList<WorkProgressReportGNProperty> doInBackground(String... param) {
			//PayJalRecordsList= WebServiceHelper.getPayJalRecords(pcode);
			// WebServiceHelper.getPayJalRecords(UserHomeActivity.this,panCode,CommonPref.getUserDetails(UserHomeActivity.this).get_UserID());
			return  WebServiceHelper.getGaliNaaliRecords(UserHomeActivity.this,panCode,CommonPref.getUserDetails(UserHomeActivity.this).get_UserID());
		}

		@Override
		protected void onPostExecute(ArrayList<WorkProgressReportGNProperty> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", "Exception Getting Gali-Naali Data");
			}
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {
					Log.e("PayJalData",""+result.size());
					//lblPayModeCount.setText(result.size());
					long i=localDBHelper.setGaliNaaliRecordsLocal(result);
					if(i>0)
					{
						downGaliNaali.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downGaliNaali.clearAnimation();
						Toast.makeText(getApplicationContext(), "गली- नाली के  सभी भौतिक और वित्तीय डेटा लोड हो गया", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(getApplicationContext(), "गली- नाली के  भौतिक और वित्तीय डेटा Insert नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "गली- नाली के  भौतिक और वित्तीय डेटा लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "गली- नाली के भौतिक और वित्तीय डेटा लोड नहीं हो पाया : Null Result", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "गली- नाली के और वित्तीय डेटा लोड नहीं हो पाया : Internet Slow or Server Busy", Toast.LENGTH_LONG).show();
			}
		}
	}

	public String[] isDataExist()
	{
		String[] data=new String[7];

		String d="0",b="0",p="0";
		//d=CommonPref.getUserDetails(UserHomeActivity.this).get_DistCode();
		//b=CommonPref.getUserDetails(UserHomeActivity.this).get_BlockCode();

		// p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();
		p=_varpanchayatID;
		String data1 = "0";
		String data2 = "0";
		String data3 = "0";
		String data4 = "0";
		String data5 = "0";
		String data6 = "0";
		String data7 = "0";

		try {
			SQLiteHelper placeData = new SQLiteHelper(this);
			SQLiteDatabase db = placeData.getReadableDatabase();


			Cursor cur = db.rawQuery("Select (select count(*) from Ward Where PanchayatCode='"+ p +"') as t1 ,(select count(*) from Schemes) as t2,(select count(*) from SubSchemes WHERE PanchayatCode='"+ p +"') as t3,(select count(*) from PaymentMode ) as t4,(select count(*) from PayJaltbl ) as t5,(select count(*) from GaliNali ) as t6,(select count(*) from VillageList WHERE PanchayatCode='"+ p +"') as t7" , null);

			while (cur.moveToNext()) {
				data1 = cur.getString((cur.getColumnIndex("t1")));
				data2 = cur.getString((cur.getColumnIndex("t2")));

				data3 = cur.getString((cur.getColumnIndex("t3")));
				data4 = cur.getString((cur.getColumnIndex("t4")));

				data5 = cur.getString((cur.getColumnIndex("t5")));
				data6 = cur.getString((cur.getColumnIndex("t6")));
				data7 = cur.getString((cur.getColumnIndex("t7")));

			}
			data[0]=data1;
			data[1]=data2;
			data[2]=data3;
			data[3]=data4;
			data[4]=data5;
			data[5]=data6;
			data[6]=data7;

		} catch (Exception ex) {
			data[0]=data1;
			data[1]=data2;
			data[2]=data3;
			data[3]=data4;
			data[4]=data5;
			data[5]=data6;
		}

		return  data;
	}

	public  void showAlertMsg(String title,String msg)
	{

		AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
		ab.setIcon(R.drawable.logo);
		ab.setTitle(title);
		ab.setMessage(msg);
		ab.setPositiveButton("[ ऐप बंद करें ]", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		ab.setNegativeButton("[ठीक है ]", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				lnbtns1.setVisibility(View.VISIBLE);
				lnbtns1.bringToFront();
				lbl1.setGravity(Gravity.BOTTOM);
			}
		});
		ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
		ab.show();
	}

	public void OnClick_viewReport(View v)
	{
		startActivity(new Intent(UserHomeActivity.this,WorkProgressReportListActivity.class));
	}
	public void OnClick_goGetPanchayat(View v){

		if(Utiilties.isOnline(UserHomeActivity.this)) {

			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("यदि आपके चयनित ब्लॉक के लिए कोई पंचायत का नाम गायब है तो आप पंचायत सूची को अपडेट करने के लिए डेटा को सिंक करें ।");
			ab.setPositiveButton("[ सिंक करें ]", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					if((spBlock != null && spBlock.getSelectedItem() !=null ))
					{
						if((String)spBlock.getSelectedItem()!="-चुनें-")
						{

							new LoadPanchayatData(_varblockID).execute();

						}else {
							Toast.makeText(UserHomeActivity.this, "कृपया वार्ड का चयन करें", Toast.LENGTH_LONG).show();

						}
					}
				}
			});
			ab.setNegativeButton("[ बाद में ]", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.cancel();
					dialog.dismiss();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();

		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(I);
				}
			});
			ab.setNegativeButton("बाद में ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					dialog.cancel();
					dialog.dismiss();
				}
			});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		}

	}

	private class LoadPanchayatData extends AsyncTask<String, String, ArrayList<PANCHAYAT>> {

		String blkCode;
		private final ProgressDialog dialog = new ProgressDialog(
				UserHomeActivity.this);

		LoadPanchayatData(String _BlkCode) {
			this.blkCode=_BlkCode;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog.setMessage("पंचायत डेटा लोड हो रहा है...");
				dialog.show();
			}
			catch (Exception e)
			{

			}
		}

		@Override
		protected ArrayList<PANCHAYAT> doInBackground(String... param) {
			//PayJalRecordsList= WebServiceHelper.getPayJalRecords(pcode);
			return  WebServiceHelper.loadPanList(blkCode);
		}

		@Override
		protected void onPostExecute(ArrayList<PANCHAYAT> result) {
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", "Exception Getting Panchayat Data");
			}
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {
					Log.e("PayJalData",""+result.size());
					//lblPayModeCount.setText(result.size());
					long i=localDBHelper.setPanchayatLocal(result);
					if(i>0)
					{
						setPanchayatSpinnerData();
						Toast.makeText(getApplicationContext(), "पंचायत रिकॉर्ड अपडेट हो गया ", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(getApplicationContext(), "पंचायत रिकॉर्ड अपडेट नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "पंचायत रिकॉर्ड अपडेट नहीं हो पाया", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "पंचायत रिकॉर्ड अपडेट नहीं हो पाया : Null Result", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "पंचायत रिकॉर्ड अपडेट नहीं हो पाया : Internet Slow or Server Busy", Toast.LENGTH_LONG).show();
			}
		}
	}

	public  void OnClick_WardReport(View v)
	{
		if(Utiilties.isOnline(UserHomeActivity.this)) {

			startActivity(new Intent(UserHomeActivity.this,WarkVisitingReportListActivity.class));
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
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
		}
	}

	public  void BlinkImageView(ImageView img)
	{

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(200); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		img.startAnimation(anim);
	}
	public  void ScrollTextView(TextView txt)
	{
		txt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		txt.setSelected(true);
		txt.setSingleLine(true);
	}
	public  void BlinkSpinner(Spinner sp)
	{
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(200); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		sp.startAnimation(anim);
	}
	public void OnClick_Help(View v)
	{
		startActivity(new Intent(UserHomeActivity.this,HelpActivity.class));
	}

	public void OnClick_SyncVillage(View v)
	{
		if(Utiilties.isOnline(UserHomeActivity.this)) {


			String p=CommonPref.getUserDetails(UserHomeActivity.this).get_PanchayatCode();
			if(p.trim().length()>2) {
				progressSYcn.setVisibility(View.VISIBLE);
				new LoadVillageData(p).execute();
			}
			else if(!CommonPref.getUserDetails(UserHomeActivity.this).get_Role().equalsIgnoreCase("PANADM"))
			{
				//for not panadm
				if(_varpanchayatID.trim().length()>2) {
					progressSYcn.setVisibility(View.VISIBLE);
					new LoadVillageData(_varpanchayatID).execute();
				}
				else
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
					ab.setMessage("कृपया पंचायत का चयन करेंं");
					ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});

					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
					ab.show();

				}
			}

			else
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
				ab.setMessage("कृपया पंचायत का चयन करेंं");
				ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();
			}
		}
		else
		{
			AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
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
		}
	}
	private class LoadVillageData extends AsyncTask<String, String, ArrayList<VillageData>> {

		String panCode="";
		private final ProgressDialog dialog2 = new ProgressDialog(UserHomeActivity.this);

		LoadVillageData(String _pancode) {
			this.panCode=_pancode;
		}

		@Override
		protected void onPreExecute() {
			try {
				dialog2.setMessage("लोडिंग ग्राम सूची...");
				dialog2.show();
			}
			catch(Exception e)
			{
				Log.e("EXX",e.getMessage());
			}
		}

		@Override
		protected ArrayList<VillageData> doInBackground(String... param) {

			VillageList = WebServiceHelper.loadVillageList(panCode);
			return VillageList;
		}

		@Override
		protected void onPostExecute(ArrayList<VillageData> result) {
			try {
				if (dialog2.isShowing()) {
					dialog2.dismiss();
				}
			}
			catch(Exception ex)
			{
				Log.e("ERROR:", ex.getMessage());
			}
			progressSYcn.setVisibility(View.GONE);
			if(result!=null)
			{
				if (result.size()>0) {

					//lblYojanaCount.setText(result.size());
					Log.e("VillageList",""+result.size());
					long i=localDBHelper.setVillageLocal(result);
					if(i>0)
					{
						downVillage.setColorFilter(UserHomeActivity.this.getResources().getColor(R.color.green));
						downVillage.clearAnimation();
						BlinkImageView(downWard);
						ScrollTextView(txtWardDown);
						Toast.makeText(getApplicationContext(), "ग्राम सूची लोड हो गया", Toast.LENGTH_LONG).show();
					}
					else
					{

						Toast.makeText(getApplicationContext(), "ग्राम सूची लोड नहीं हो पाया", Toast.LENGTH_LONG).show();
					}
				} else {

					title="ग्राम सूची नहीं मिली";
					msg=panCode + " : इस पंचायत कोड के लिए कोई ग्राम सूची नहीं मिली |\nआप ग्राम की सूची के बिना इस पर काम करना जारी नहीं रख सकते हैं";
					showAlertMsg(title,msg);

					Toast.makeText(getApplicationContext(), panCode +" : इस पंचायत कोड के लिए कोई ग्राम सूची नहीं मिली", Toast.LENGTH_LONG).show();

				}
			}
		}
	}

	public void ShowInformation(String title,String msg)
	{
		TextView tvmsg = new TextView(this);
		tvmsg.setPadding(10,2,10,2);

		tvmsg.setText(Html.fromHtml(msg),TextView.BufferType.SPANNABLE);
		AlertDialog.Builder ab = new AlertDialog.Builder(UserHomeActivity.this);
		ab.setIcon(R.mipmap.ic_launcher);
		ab.setTitle(title);
		ab.setView(tvmsg);

		ab.setPositiveButton("[वेबसाइट पर जाएँ]", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();
				dialog.dismiss();
				try {
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nicapp.bih.nic.in/nischaysoft/"));
					startActivity(myIntent);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(UserHomeActivity.this, "No application can handle this request(कोई भी एप्लिकेशन इस अनुरोध को संभाल नहीं सकता है.)"
							+ " Please install a webbrowser (कृपया एक वेब ब्राउज़र इंस्टॉल करें)",  Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});

		ab.setNegativeButton("[ बंद करें ]", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();
				dialog.dismiss();
			}
		});

		ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
		ab.show();
	}

	public void OnClick_SendFeedback(View v)
	{
		startActivity(new Intent(UserHomeActivity.this,FeedBackActivity.class));
	}


	public String resizeBase64Image(String base64image){
		byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inPurgeable = true;
		Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


		if(image.getHeight() <= 400 && image.getWidth() <= 400){
			return base64image;
		}
		image = Bitmap.createScaledBitmap(image, IMG_WIDTH, IMG_HEIGHT, false);

		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG,70, baos);

		byte [] b=baos.toByteArray();
		System.gc();
		return Base64.encodeToString(b, Base64.NO_WRAP);

	}
}
