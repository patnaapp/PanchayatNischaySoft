package app.bih.in.nic.nischayyojana.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;

public class WardYojanaDetailsActivity extends Activity {

	SQLiteDatabase db;
	DataBaseHelper helper;
	TextView txtHowTo;
	DataBaseHelper localDBHelper;
	ArrayList<ward> WardList = new ArrayList<ward>();
	String _varWardYojanDetails="na";
	String _varpanchayatName="",_varpanchayatID="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wardyojanadetails_activity);
		localDBHelper = new DataBaseHelper(this);
		txtHowTo = (TextView) this.findViewById(R.id.txtHowTo);

		String msg="";


		_varpanchayatID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PAN_CODE", "");

		Log.e("_varpanchayatID",_varpanchayatID);
//		String wardList="वार्ड-योजना विवरण निम्नलिखित हैं";

		String msg0="<font color='red'><p>यदि आप इस स्क्रीन पर वार्ड-योजना का नाम नहीं देख पा रहे हैं तो सबसे पहले होम स्क्रीन पे जायें | ड्रॉप डाउन बॉक्स में जिला ,प्रखण्ड और फिर पंचायत का चयन करें । पंचायत का चयन करने के बाद गांव, वार्ड, योजना के प्रकार को सिंक्रोनाइज सेक्शन से सिंक्रनाइज़ करें । सिंक्रोनाइज करने के बाद दुबारा इस स्क्रीन को ओपन करें | इस बार आपको अपने वार्ड-योजना का विवरण दिखे गा | इस स्क्रीन को दुबारा ओपन करने के लिए होम स्क्रीन पर  \"वार्ड-योजना विवरण\" के बटन को क्लिक करें | <p></font>";
		if(_varpanchayatID.length()>3) {

			_varWardYojanDetails=getWardYojanDetails();

			if (!_varWardYojanDetails.equalsIgnoreCase("na")) {
				msg = "<p>"+_varWardYojanDetails + "</p>----------------------------<br><p style=\"background-color:#b3b3b3;\"><font color='red'>यदि आप भौतिक स्थिति  या भौतिक निरीक्षण के लिए जा रहे हैं, तो वहां जाने से पहले यह सुनिश्चित कर लें कि आप जिस पंचायत के वार्ड का दौरा करने जा रहे हैं उस वार्ड  के लिए \"योजना\" डेटाबेस में  है या नहीं ?यदि नहीं तो पहले उस वार्ड के लिए योजना का नाम जोड़ लें |</font></p><p>यदि आप योजना का नाम जोड़ने के लिए अधिकृत नहीं हैं, तो आपको अपने हेड या विभाग से संपर्क करना होगा |</p><p>योजना का नाम जोड़ने के लिए इस वेब साइट पर जाएँ :-<br><br><a href=\"http://nicapp.bih.nic.in/nischaysoft/\"><b>http://nicapp.bih.nic.in/nischaysoft/</b></a></font></p>";
			} else {
				msg = "<br>"+msg0+"<br></p><p style=\"background-color:#b3b3b3;\"><font color='red'>यदि आप भौतिक स्थिति या भौतिक निरीक्षण के लिए जा रहे हैं, तो वहां जाने से पहले यह सुनिश्चित कर लें कि आप जिस पंचायत के वार्ड का दौरा करने जा रहे हैं उस वार्ड  के लिए \"योजना\" डेटाबेस में  है या नहीं ?यदि नहीं तो पहले उस वार्ड के लिए योजना का नाम जोड़ लें |</font></p><p>यदि आप योजना का नाम जोड़ने के लिए अधिकृत नहीं हैं, तो आपको अपने हेड या विभाग से संपर्क करना होगा |</p><p>योजना का नाम जोड़ने के लिए इस वेब साइट पर जाएँ :-<br><br><a href=\"http://nicapp.bih.nic.in/nischaysoft/\"><b>http://nicapp.bih.nic.in/nischaysoft/</b></a></font></p>";
			}
		}
		else
		{
			msg = "<br>"+msg0+"<br></p><p style=\"background-color:#b3b3b3;\"><font color='red'>यदि आप भौतिक स्थिति या भौतिक निरीक्षण के लिए जा रहे हैं, तो वहां जाने से पहले यह सुनिश्चित कर लें कि आप जिस पंचायत के वार्ड का दौरा करने जा रहे हैं उस वार्ड  के लिए \"योजना\" डेटाबेस में  है या नहीं ?यदि नहीं तो पहले उस वार्ड के लिए योजना का नाम जोड़ लें |</font></p><p>यदि आप योजना का नाम जोड़ने के लिए अधिकृत नहीं हैं, तो आपको अपने हेड या विभाग से संपर्क करना होगा |</p><p>योजना का नाम जोड़ने के लिए इस वेब साइट पर जाएँ :-<br><br><a href=\"http://nicapp.bih.nic.in/nischaysoft/\"><b>http://nicapp.bih.nic.in/nischaysoft/</b></a></font></p>";
		}

		txtHowTo.setBackgroundColor(Color.TRANSPARENT);
		txtHowTo.setMovementMethod(LinkMovementMethod.getInstance());
		txtHowTo.setText(Html.fromHtml(msg),TextView.BufferType.SPANNABLE);

	}


	public void OnClick_Close(View v)
	{
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public String getWardYojanDetails()
	{
		localDBHelper = new DataBaseHelper(WardYojanaDetailsActivity.this);
		WardList=localDBHelper.getWardLocal(_varpanchayatID);

		String wyinfo="";
		if(WardList.size()>0 ) {
			//1-Pay Jal
			//2-GaliNaali
			StringBuilder sb=new StringBuilder();
			wyinfo=wyinfo+"<h3 style=\"text-align:justify\"><font color='red' align='centre'>वार्ड का नाम और योजना का नाम जो इसमें जोड़े गए हैं का विवरण</font></h3>";
			wyinfo=wyinfo+"<br><h4 style=\"text-align:justify\"><font color='blue' style=\"text-align:justify\">पेय-जल: "+getPanName()+"  के वार्ड और उसमे जुड़े योजना का विवरण  </font></h4>";


			for(int i=0;i<WardList.size();i++)
			{
				wyinfo=wyinfo+"<b><font color='blue'>"+WardList.get(i).getWardname()+":-</font></b><br>"+getYojanaList(WardList.get(i).getWardCode(),"1")+"<br>";
			}

			wyinfo=wyinfo+"<div style=\"text-align: center\"><h4><font color='blue'>गली-नाली: "+getPanName()+" के वार्ड और उसमे जुड़े योजना का विवरण <font></h4></div>";
			for(int i=0;i<WardList.size();i++)
			{
				wyinfo=wyinfo+"<b><font color='blue'>"+WardList.get(i).getWardname()+":-</font></b><br>"+getYojanaList(WardList.get(i).getWardCode(),"2")+"<br>";
			}

			_varWardYojanDetails=wyinfo;
			Log.e("WARDYOJANA",sb.toString());
		}
		return _varWardYojanDetails;
	}
	public String getYojanaList(String wcode,String NischayID)
	{
		ArrayList<YOJANA> YojanaTypeList = new ArrayList<>();
		String yc="";
		localDBHelper = new DataBaseHelper(WardYojanaDetailsActivity.this);
		YojanaTypeList=localDBHelper.getYojanaList(NischayID,_varpanchayatID,wcode);

		if(YojanaTypeList.size()>0)
		{
			for(int x=0;x<YojanaTypeList.size();x++)
			{
				yc +=(x+1)+". "+YojanaTypeList.get(x).get_YojanaName()+"<br>";
			}
		}
		else
		{
			yc="<font color='red'><i>इस वार्ड मे किसी योजना का नाम नहीं जोड़ा गया हैं|</i></font>";
		}

		return yc;
	}
	public String getPanName()
	{
		localDBHelper = new DataBaseHelper(WardYojanaDetailsActivity.this);
		return localDBHelper.getPanchayatName(_varpanchayatID)+ " पंचायत";

	}
}