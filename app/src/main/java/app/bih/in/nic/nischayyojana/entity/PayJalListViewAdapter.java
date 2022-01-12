package app.bih.in.nic.nischayyojana.entity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.ui.PayJalActivity;
import app.bih.in.nic.nischayyojana.ui.UserHomeActivity;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;


public class PayJalListViewAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	int IMG_WIDTH=500,IMG_HEIGHT=500;
	private ArrayList<PayJalProperty> mEntries = new ArrayList<>();
	public PayJalListViewAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mEntries.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mEntries.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = (RelativeLayout) mLayoutInflater.inflate(R.layout.grid_view_item,	null);
		TextView wid = (TextView) vi.findViewById(R.id.tvID);
		TextView pid = (TextView) vi.findViewById(R.id.tvPanID);

		TextView txtIAutName = (TextView) vi.findViewById(R.id.tvIAutName);
		TextView txtComments = (TextView) vi.findViewById(R.id.txtComments);
		TextView txtMBNumber = (TextView) vi.findViewById(R.id.txtMBNumber);

		ImageView bedit = (ImageView) vi.findViewById(R.id.btnedit);
		ImageView bupload = (ImageView) vi.findViewById(R.id.btnupload);
		ImageView btndelete = (ImageView) vi.findViewById(R.id.btndelete);
		final LinearLayout allbtns =(LinearLayout) vi.findViewById(R.id.btnList);
		final RelativeLayout allinfotxt =(RelativeLayout) vi.findViewById(R.id.relallinfo);

		allinfotxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (allbtns.getVisibility() == View.VISIBLE) {
					allbtns.setVisibility(View.INVISIBLE);
				} else {
					allbtns.setVisibility(View.VISIBLE);
				}
			}
		});
		bedit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent iListPendingUpload = new Intent(mContext, PayJalActivity.class);
				iListPendingUpload.putExtra("EDIT", "EDIT");
				iListPendingUpload.putExtra("ID", mEntries.get(position).get_Row_ID());
//					iListPendingUpload.putExtra("PanID", mEntries.get(position).get_Pan_Code());
				mContext.startActivity(iListPendingUpload);

			}

		});
		bupload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ShowAlertMSG("PAYJAL", mEntries.get(position).get_Row_ID());

				if(isPhotoTaken("PAYJAL", mEntries.get(position).get_Row_ID()))
				{
					ShowAlertMSG("PAYJAL", mEntries.get(position).get_Row_ID());
				}
				else
				{
					TakePhotoMsg();
				}

			}
		});

		btndelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
				ab.setIcon(R.drawable.deleteentry);
				ab.setTitle("रिकॉर्ड हटाएं");
				ab.setMessage("क्या आपको यकीन है? इस रिकॉर्ड को हटाना चाहते हैं?");
				ab.setPositiveButton("हाॅं ", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.dismiss();
						deleteRec(mEntries.get(position).get_Row_ID());
						String userid= PreferenceManager.getDefaultSharedPreferences(mContext).getString("USER_ID", "");

						final ArrayList<PayJalProperty> entries = getLocalPendingView(userid);

						upDateEntries(entries);
					}
				});
				ab.setNegativeButton("नही", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.dismiss();
					}
				});

				ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
				ab.show();

			}
		});

		wid.setText(String.valueOf(mEntries.get(position).get_Row_ID()));
		txtIAutName.setText(String.valueOf(mEntries.get(position).get_BoringStarted()).equalsIgnoreCase("Y")? "हाँ":"नहीं");
		txtComments.setText(String.valueOf(mEntries.get(position).get_StaggingStarted()).equalsIgnoreCase("Y")? "हाँ":"नहीं");
		txtMBNumber.setText(String.valueOf(mEntries.get(position).get_CompletionDate()));

		/*

		wi.set_BoringStarted(cursor.getString(1).equalsIgnoreCase("Y")? "हाँ":"नहीं");
				wi.set_StaggingStarted(cursor.getString(2).equalsIgnoreCase("Y")? "हाँ":"नहीं");
		 */
		//cnt.setText(String.valueOf(mEntries.get(position).get_Count()));
		return vi;
	}
	public void ShowAlertMSG(final String whatto,final String thisRowID)
	{
		if(Utiilties.isOnline(mContext)) {
			AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
			ab.setMessage("क्या आपको यकीन है ? इसे सर्वर पर अपलोड करना चाहते हैं ?");
			ab.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {

					sendPending(whatto,thisRowID);
					//upload code
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
			AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
			ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
			ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					GlobalVariables.isOffline = false;
					Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					mContext.startActivity(I);
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
	public void sendPending(String WhoseRecords,String thisRowID) {

		SQLiteHelper placeData = new SQLiteHelper(mContext);
		SQLiteDatabase db = placeData.getReadableDatabase();

		Cursor cursor=null;
		Cursor cursorPic1_2=null;
		Cursor cursorPic3_4=null;

		String work_type= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WORK_TYPE", "");

		if(WhoseRecords.equalsIgnoreCase("PAYJAL"))
		{

			if(isPhotoTaken(WhoseRecords,thisRowID))
			{
				cursor = db.rawQuery("SELECT id, FYear, PanCode, PanName, WardCode, WardName, NischayCode, YojanaCode, YojanaTypeNAME, CurrentPhysicalStatus, isBoringStarted, BoringDepth, ISIMarkBoring, UPVCPipeCMLNo, isMotorPumpStatus, ISIMarkMotorPump, MotorPumpCapacity, isStaggingDone, TypeOfStagging, HeightOfStagging, TenkiCapacity, TenkiQuantity, isVitranPranali, TotalLength, DepthFromSurfaceLevel, ISIMarkVitranPranali, CMLNumber, isElectrictyConnection, isElectConnection, ConsumerNumber, isGrihJalSunyojan, TotalHouseNum, WaterSupplyStatus, JalStumbhKaNirman, TypeOfPipe, FerulKaUpyog, isProjectCompleted, ProjectCompleteionDate, Remarks, CreatedBy, CreatedDate, Latitude, Longitude, InspectorName, InspectorPost, extraColumn1, extraColumn2, extraColumn3, extraColumn4, extraColumn5, OtherYojanaName, OtherMotorPumpCapacity , BoringFtMt, StageFtMt, VitranFtMt, VitranDepthFtMt, VillageCode FROM PayJaltbl WHERE id='"+ thisRowID +"' AND Latitude Is Not Null",null);
				cursorPic1_2 = db.rawQuery("SELECT Photo1, Photo2 FROM PayJaltbl WHERE id='"+ thisRowID +"' AND Latitude Is Not Null",null);
				cursorPic3_4 = db.rawQuery("SELECT Photo3, Photo4 FROM PayJaltbl WHERE id='"+ thisRowID +"' AND Latitude Is Not Null",null);

				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {
						String[] param = new String[45];
						if(!CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
						{
							param = new String[45];
						}
						else if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param = new String[45];
							}
						}
						param[0] = cursor.getString(cursor.getColumnIndex("id"));
						param[1] = cursor.getString(cursor.getColumnIndex("YojanaCode"));

						if(cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("फीट"))
						{
							String ft = cursor.getString(cursor.getColumnIndex("BoringDepth"));
							double value = Double.parseDouble(ft);
							double meters = value/3.2808;
							//param[2] = String.valueOf(meters);
							param[2] = new DecimalFormat("##.##").format(meters);
							Log.e("BoringFT",ft);
							Log.e("BoringMT",param[2]);
						}
						else if(cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("मीटर"))
						{
							param[2] = cursor.getString(cursor.getColumnIndex("BoringDepth"));
						}

						param[3] = cursor.getString(cursor.getColumnIndex("ISIMarkBoring"));
						param[4] = cursor.getString(cursor.getColumnIndex("UPVCPipeCMLNo"));
						param[5] = cursor.getString(cursor.getColumnIndex("ISIMarkMotorPump"));

						param[6] = cursor.getString(cursor.getColumnIndex("MotorPumpCapacity"));
						param[7] = cursor.getString(cursor.getColumnIndex("TypeOfStagging"));


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


						param[9] = cursor.getString(cursor.getColumnIndex("TenkiCapacity"));
						param[10] = cursor.getString(cursor.getColumnIndex("TenkiQuantity"));

						if(cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("फीट"))
						{
							String ft = cursor.getString(cursor.getColumnIndex("TotalLength"));
							double value = Double.parseDouble(ft);
							double meters = value/3.2808;
							//param[11] = String.valueOf(meters);
							param[11] = new DecimalFormat("##.##").format(meters);
						}
						else if(cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("मीटर"))
						{
							param[11] = cursor.getString(cursor.getColumnIndex("TotalLength"));
						}

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
							String pic1="",pic2="";

							String photo1=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo1")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo1")), Base64.NO_WRAP) : "";


							if(!photo1.equals("")){
								pic1=resizeBase64Image(photo1);
							}

							param[27] = pic1;

							String photo2=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";

							if (!photo2.equals("")){
								pic2=resizeBase64Image(photo2);

							}

							param[28] = pic2;
						}
						cursorPic1_2.close();

						while (cursorPic3_4.moveToNext()){
							String pic3="",pic4="";


							String photo3=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";

							if (!photo3.equals("")){
								pic3=resizeBase64Image(photo3);
							}

							param[29] =pic3;

							String photo4=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

							if (!photo4.equals("")){
								pic4=resizeBase64Image(photo4);

							}

							param[30] =pic4;
						}
						cursorPic3_4.close();

						param[31] = cursor.getString(cursor.getColumnIndex("Latitude"));
						param[32] = cursor.getString(cursor.getColumnIndex("Longitude"));



						String appver = PreferenceManager.getDefaultSharedPreferences(mContext).getString("APP_VERSION", "");
						String devicedetail = PreferenceManager.getDefaultSharedPreferences(mContext).getString("DEVICE_DETAILS", "");

						param[33] = appver;
						param[34] = devicedetail;


						if(!CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
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
						else if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
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
					Toast.makeText(mContext,"आपके पास कोई अपलोड लंबित नहीं है !", Toast.LENGTH_SHORT).show();

				}
				cursor.close();
			}
			else
			{
				TakePhotoMsg();
			}

		}

		db.close();

	}
	public void TakePhotoMsg()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
	public boolean isPhotoTaken(String WhoseRecords,String thisRowID)
	{

		SQLiteHelper placeData = new SQLiteHelper(mContext);
		SQLiteDatabase db = placeData.getReadableDatabase();

		Cursor cursor=null;
		if(WhoseRecords.equalsIgnoreCase("PAYJAL")) {
			cursor = db
					.rawQuery(
							"SELECT id FROM PayJaltbl WHERE id='" + thisRowID + "' AND Photo1 IS NOT NULL",
							null);

			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					return true;
				}
			}
		}
		return false;
	}
	private class UploadPendingPayJalPhysicalVer extends AsyncTask<String, Void, String> {

		String eid = "-1";
		private final ProgressDialog dialog = new ProgressDialog(mContext);

		@Override
		protected void onPreExecute() {

			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("अपलोड हो रहा है ...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... param) {

			eid = param[0];  // id
			String res = WebServiceHelper.UploadPayJalPhysicalVer(mContext,param);
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
					SQLiteHelper placeData = new SQLiteHelper(mContext);
					Log.e("eid",eid);

					//---------------KEEP THIS RECORD--------------------------
					placeData.deletePayJalPhysicalVer(eid);

					String   userid= PreferenceManager.getDefaultSharedPreferences(mContext).getString("USER_ID", "");
					final ArrayList<PayJalProperty> entries = getLocalPendingView(userid);

					upDateEntries(entries);

					Toast.makeText(mContext,"सफलतापूर्वक अपलोड किया गया", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(mContext, "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public long deleteRec(String id)
	{
		long c = -1;

		try {
			SQLiteHelper placeData = new SQLiteHelper(mContext);
			SQLiteDatabase db = placeData.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = id;
			c = db.delete("PayJaltbl", "id=?", DeleteWhere);
			db.close();

		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	private ArrayList<PayJalProperty> getLocalPendingView(String userid) {

		SQLiteHelper placeData = new SQLiteHelper(mContext);
		SQLiteDatabase db = placeData.getReadableDatabase();
		String _WardCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WARD_CODE", "");
		String _FYearCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("FYEAR_CODE", "");
		String _SchemeCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");

//        wi.set_BoringStarted(cursor.getString(1).equalsIgnoreCase("Y")? "हाँ":"नहीं");
//        wi.set_StaggingStarted(cursor.getString(2).equalsIgnoreCase("Y")? "हाँ":"नहीं");
		ArrayList<PayJalProperty> WorkList = new ArrayList<>();
		try {
			Cursor cursor = db.rawQuery("SELECT id,isBoringStarted,isStaggingDone,ProjectCompleteionDate FROM PayJaltbl WHERE CreatedBy='"+userid +"'  ORDER BY id DESC", null);
			while (cursor.moveToNext()) {
				PayJalProperty wi = new PayJalProperty();

				wi.set_Row_ID(cursor.getString(0));
				wi.set_BoringStarted(cursor.getString(1));
				wi.set_StaggingStarted(cursor.getString(2));
				wi.set_CompletionDate(cursor.getString(3));
				WorkList.add(wi);
			}
			Log.e("C COunt",""+cursor.getCount());
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return WorkList;
	}
	public void upDateEntries(ArrayList<PayJalProperty> entries) {
		mEntries = entries;
		notifyDataSetChanged();
	}


	public String resizeBase64Image(String base64image){
		byte [] encodeByte= Base64.decode(base64image.getBytes(),Base64.DEFAULT);
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
