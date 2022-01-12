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
import java.text.DecimalFormat;
import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.ui.GaliNaliActivity;
import app.bih.in.nic.nischayyojana.ui.UserHomeActivity;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;


public class GaliNaliListViewAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<GaliNaliProperty> mEntries = new ArrayList<>();
	String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
	DataBaseHelper helper;
	int IMG_WIDTH=500,IMG_HEIGHT=500;

	public GaliNaliListViewAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		helper=new DataBaseHelper(mContext);
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
			vi = (RelativeLayout) mLayoutInflater.inflate(R.layout.grid_view_galinaali_details_item,	null);
		TextView wid = (TextView) vi.findViewById(R.id.tvID);
		TextView pid = (TextView) vi.findViewById(R.id.tvPanID);

		TextView tvYojana = (TextView) vi.findViewById(R.id.tvYojana);
		TextView txtProjectStatus = (TextView) vi.findViewById(R.id.txtProjectStatus);
		//TextView txtPipeLine = (TextView) vi.findViewById(R.id.txtPipeLine);

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

				Intent iListPendingUpload = new Intent(mContext, GaliNaliActivity.class);
				iListPendingUpload.putExtra("EDIT", "EDIT");
				iListPendingUpload.putExtra("ID", mEntries.get(position).get_Row_ID());
//					iListPendingUpload.putExtra("PanID", mEntries.get(position).get_Pan_Code());
				mContext.startActivity(iListPendingUpload);

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

						final ArrayList<GaliNaliProperty> entries = getLocalPendingView(userid);

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
		bupload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isPhotoTaken("GALINAALI", mEntries.get(position).get_Row_ID()))
				{
					ShowAlertMSG("GALINAALI", mEntries.get(position).get_Row_ID());
				}
				else
				{
					TakePhotoMsg();
				}
			}
		});
		wid.setText(String.valueOf(mEntries.get(position).get_Row_ID()));
		tvYojana.setText(String.valueOf(mEntries.get(position).get_MukhiyaName()));
		txtProjectStatus.setText(String.valueOf(mEntries.get(position).get_MukhiyaMobNum()));
		//txtPipeLine.setText(String.valueOf(mEntries.get(position).get_GpSecName()));

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
		String work_type= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WORK_TYPE", "");
		Cursor cursor=null;
		Cursor cursorPic1_2=null;
		Cursor cursorPic3_4=null;

		if(WhoseRecords.equalsIgnoreCase("GALINAALI"))
		{
			if(isPhotoTaken(WhoseRecords,thisRowID))
			{
				cursor = db.rawQuery("SELECT id, FYear, PanCode, WardCode, wardName, NischayCode, YojanaCode, CurrentPhysicalStatus, currentPhysical_Name, RoadType, PathRoadType, NaliType, JalNikasi, TotalRoadDistance_Length, TotalRoadDistance_Breadth, TotalRoadDistance_Fat, TotalPathRoadDistance_Breadth, TotalNaliDistance_Length, TotalNali_Breadth, TotalNali_Gharai, SokhtaKiSankhya, MittiKrya, HugePipe, ProjectCompleteionDate, Remarks, CreatedDate, CreatedBy, Latitude, Longitude, InspectorName, InspectorPost, extraColumn1, extraColumn2, extraColumn3, extraColumn4, extraColumn5, OtherYojanaName, OtherMotorPumpCapacity, TotalRoadDistance_LengthFtMt, TotalRoadDistance_BreadthFtMt, TotalPathRoadDistance_BreadthFtMt, TotalNaliDistance_LengthFtMt, TotalNali_BreadthFtMt, TotalNali_GharaiFtMt, MittiKryaFtMt, VillageCode FROM GaliNali where id=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{thisRowID});
				cursorPic1_2 = db.rawQuery("SELECT Photo1, Photo2 FROM GaliNali where id=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{thisRowID});
				cursorPic3_4 = db.rawQuery("SELECT Photo3, Photo4 FROM GaliNali where id=? AND Latitude Is Not Null ORDER BY id DESC",new String[]{thisRowID});

				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {

						String[] param = new String[39];
						if(!CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
						{
							param = new String[39];
						}
						else if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
						{
							if(work_type.equalsIgnoreCase("PI")) {
								param = new String[39];
							}
						}
						param[0] = cursor.getString(cursor.getColumnIndex("id"));
						param[1] = cursor.getString(cursor.getColumnIndex("YojanaCode"));
						param[2] = cursor.getString(cursor.getColumnIndex("RoadType"));
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

						param[5] = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Fat"));

						param[6] = cursor.getString(cursor.getColumnIndex("PathRoadType"));

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


						param[8] = cursor.getString(cursor.getColumnIndex("NaliType"));


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


						param[12] = cursor.getString(cursor.getColumnIndex("SokhtaKiSankhya"));



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


							param[21] =pic1;

							String photo2=cursorPic1_2.isNull(cursorPic1_2.getColumnIndex("Photo2")) == false ? Base64.encodeToString(
									cursorPic1_2.getBlob(cursorPic1_2.getColumnIndex("Photo2")), Base64.NO_WRAP) : "";

							if (!photo2.equals("")){
								pic2=resizeBase64Image(photo2);
							}


							param[22] =pic2;
						}
						cursorPic1_2.close();

						while (cursorPic3_4.moveToNext()){

							String pic3="",pic4="";

							String photo3=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo3")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo3")), Base64.NO_WRAP) : "";


							if (!photo3.equals("")){
								pic3=resizeBase64Image(photo3);
							}

							param[23] =pic3;

							String photo4=cursorPic3_4.isNull(cursorPic3_4.getColumnIndex("Photo4")) == false ? Base64.encodeToString(
									cursorPic3_4.getBlob(cursorPic3_4.getColumnIndex("Photo4")), Base64.NO_WRAP) : "";

							if (!photo4.equals("")){
								pic4=resizeBase64Image(photo4);
							}


							param[24] =pic4;
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

						String appver = PreferenceManager.getDefaultSharedPreferences(mContext).getString("APP_VERSION", "");
						String devicedetail = PreferenceManager.getDefaultSharedPreferences(mContext).getString("DEVICE_DETAILS", "");

						param[27] = appver;
						param[28] = devicedetail;



						if(!CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
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
						else if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM"))
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
							if(work_type.equalsIgnoreCase("PV")) {
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
		if(WhoseRecords.equalsIgnoreCase("GALINAALI")) {
			cursor = db
					.rawQuery(
							"SELECT id FROM GaliNali WHERE id='" + thisRowID + "' AND Photo1 IS NOT NULL",
							null);

			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					return true;
				}
			}
		}
		return false;
	}
	public long deleteRec(String id)
	{
		long c = -1;

		try {
			SQLiteHelper placeData = new SQLiteHelper(mContext);
			SQLiteDatabase db = placeData.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = id;
			c = db.delete("GaliNali", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	private class UploadPendingGaliNaaliData extends AsyncTask<String, Void, String> {

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
			String res = WebServiceHelper.UploadPendingGaliNaaliData(mContext,param);

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
					placeData.deletePendingGaliNaali(eid);
					String   userid= PreferenceManager.getDefaultSharedPreferences(mContext).getString("USER_ID", "");
					final ArrayList<GaliNaliProperty> entries = getLocalPendingView(userid);

					upDateEntries(entries);
					Toast.makeText(mContext,"सफलतापूर्वक अपलोड किया गया", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(mContext, "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(mContext, "अपलोड करना विफल रहा!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	private ArrayList<GaliNaliProperty> getLocalPendingView(String userid) {

		SQLiteHelper placeData = new SQLiteHelper(mContext);
		SQLiteDatabase db = placeData.getReadableDatabase();
		_WardCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WARD_CODE", "");
		_WardName= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WARD_NAME", "");
		_FYearCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("FYEAR_CODE", "");
		_SchemeName=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_NAME", "");
		_SchemeCode=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");
		ArrayList<GaliNaliProperty> WorkList = new ArrayList<>();
		try {
			Cursor cursor = db
					.rawQuery(
							"SELECT id,YojanaCode,CurrentPhysicalStatus FROM GaliNali where CreatedBy=? ORDER BY id DESC",
							new String[]{userid});
			while (cursor.moveToNext()) {
				GaliNaliProperty wi = new GaliNaliProperty();

				wi.set_Row_ID(cursor.getString(0));
				wi.set_MukhiyaName(getYojanaName(cursor.getString(1)));
				wi.set_MukhiyaMobNum(getProjectStatusName(cursor.getString(2)));
				//wi.set_GpSecName(cursor.getString(3));
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
	public String getYojanaName(String yid)
	{
		DataBaseHelper helper=new DataBaseHelper(mContext);
		return helper.getYojanaName(yid);
	}
	public String getProjectStatusName(String pid)
	{
		DataBaseHelper helper=new DataBaseHelper(mContext);
		return helper.getProjectStatus(pid);
	}
	public void upDateEntries(ArrayList<GaliNaliProperty> entries) {
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
