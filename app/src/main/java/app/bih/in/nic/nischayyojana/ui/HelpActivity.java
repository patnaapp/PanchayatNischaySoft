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
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class HelpActivity extends Activity {

	SQLiteDatabase db;
	DataBaseHelper helper;
	TextView txtHowTo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_activity);

		txtHowTo = (TextView) this.findViewById(R.id.txtHowTo);

		showHelpMSG();
//		TextView textView = (TextView) this.findViewById(R.id.textview_marguee);
//		textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//		textView.setSelected(true);
//		textView.setSingleLine(true);


	}

	public void showHelpMSG()
	{

		GlobalVariables.isShownHelp=true;
		String msg="<p><font color='blue'>यदि आप निरीक्षण करने जा रहे हैं और लॉगिन करने के बाद यदि जिला, ब्लॉक और पंचायत का नाम ड्रॉप डाउन बॉक्स में सेलेक्टेड नहीं दिख रहा है तो \n" +
				"आप ड्रॉप डाउन बॉक्स में पहले जिले का चयन करें, फिर ब्लॉक का चयन करें और अंत में पंचायत का चयन करें |\n" +
				"पंचायत का चयन करने के बाद गांव, वार्ड, एवं योजना-टाइप को सिंक्रोनाइज़ करे|\n" +
				"आखिर में आप पेय जल और गली नाली डेटा को सिंक्रोनाइज़ करें |</font></p>\n" +
				"<p><font color='red'><b>*पेय जल और गली नाली का डेटा तभी सिंक्रोनाइज़ होगा जब आप कोई निरीक्षण-डेटा को सर्वर पर सेव/अपलोड कर चुके हों *</b></font></p>"+
				"\n\n" +
				"<font color='blue'><b><u>मुख्य मंत्री ग्रामीण निश्चय योजना :: नई प्रविष्टि - डिलीट/हटाना - परिवर्त्तन - अपलोड कैसे करना है ? </u></b></font>\n" +
				"<p><font color='blue'>मुख्य मंत्री ग्रामीण के कोई भी निश्चय योजना के नई प्रविष्टि बटन पर क्लिक करें और सभी डाटा का एंट्री कर के डाटा को सेव करें, फिर फोटो ले कर पुनः डाटा को सेव करें ।\n" +
				"डाटा को सुधारने के लिए परिवर्त्तन बटन पर क्लिक करें। डाटा सुधारने के बाद डाटा को सेव कर ले |\n" +
				"अगर आप डाटा को डिलीट /हटाना चाहतें हैं तो 'हटायें' बटन पर क्लिक करें |\n" +
				"डाटा को सर्वर पर सेव/अपलोड करने के लिये अपलोड बटन पर क्लिक करें |</font></p>\n" +
				"<p><b><font color='red'><marquee>फोटो के बिना आप सर्वर पर डेटा अपलोड नहीं कर सकते</marquee></font></b></p>\n" +
				"\n<font color='blue'><b><u>रिपोर्ट</u></b>\n" +
				"<p><font color='blue'>अगर आप अपने कार्य का <u>'प्रगति रिपोर्ट'</u> देखने चाहतै हैं तो पहले दोनों योजना के डाटा को सिंक्रोनाइज़ करें |</p>" +
				"<p><font color='blue'><b>कार्य प्रगति रिपोर्ट :- </b>डाटा सिंक्रोनाइज़ करने के बाद 'कार्य प्रगति रिपोर्ट' पर क्लिक करें |\n" +
				"एक नया स्क्रीन दिखेगा| उस स्क्रीन मे योजना का सेलेक्शन करें |\n" +
				"योजना का सेलेक्शन करने पर आप के द्वारा अभी तक जितने भी इंस्पेक्शन रिपोर्ट सर्वर पर अपलोड किया गया होगा वो सभी रिकॉर्ड आप के स्क्रीन पर दिखेगा |</font></p>\n" +
				"\n" +
				"<p><font color='blue'><b>वार्ड-दौरा की स्थिति :- [ <u>इस रिपोर्ट के लिए इंटरनेट कनेक्शन आवश्यक है</u> ]</b>अभी तक आपने कौन-कौन से वार्ड मे कितने बार इंस्पेक्शन किये हैं उस रिकॉर्ड को देखने के लीये 'वार्ड-दौरा की स्थिति' बटन पर क्लिक करें |\n" +
				"एक नया स्क्रीन दिखेगा| उस स्क्रीन मे योजना का सेलेक्शन करें |\n" +
				"योजना का सेलेक्शन करने पर अगर आप के द्वारा अभी तक जितने भी वार्ड मे इंस्पेक्शन किये हैं वो सभी रिकॉर्ड आप के स्क्रीन पर दिखेगा|</font></p>\n\n"+
				"<p align='center'><b><font color='red'><marquee>फोटो के बिना आप सर्वर पर डेटा अपलोड नहीं कर सकते</marquee></font></b></p>\n" +
				"<p><font color='grey'>नोट: अगर आप फिजिकल स्थिति एंड फिजिकल इंस्पेक्शन दोनों करैंगे तो कोई एक तरह के डाटा मोबाइल मे सेव करने के बाद उसे अपलोड करना जरुरी है |</p>\n";

		txtHowTo.setBackgroundColor(Color.TRANSPARENT);
		txtHowTo.setText(Html.fromHtml(msg),TextView.BufferType.SPANNABLE);

	}

	public void OnClick_Close(View v)
	{
		finish();

//		String msg=""+Html.fromHtml("<font color='red'>यदि आप भौतिक स्थिति  या भौतिक निरीक्षण के लिए जा रहे हैं, तो वहां जाने से पहले यह सुनिश्चित कर लें कि आप जिस पंचायत के वार्ड का दौरा करने जा रहे हैं उस वार्ड  के लिए \"योजना\" डेटाबेस में  है या नहीं ?यदि नहीं तो पहले उस वार्ड के लिए योजना का नाम जोड़ लें |</font>");
//		ShowInformation("सुनिश्चित करें",msg);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void ShowInformation(String title,String msg)
	{
		AlertDialog.Builder ab = new AlertDialog.Builder(HelpActivity.this);
		ab.setIcon(R.mipmap.ic_launcher);
		ab.setTitle(title);
		ab.setMessage(msg);

		ab.setNegativeButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
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