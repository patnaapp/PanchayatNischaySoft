package app.bih.in.nic.nischayyojana.entity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.ui.VoucherEntryActivity;


public class WorkProgressReportListGNViewAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<WorkProgressReportGNProperty> mEntries = new ArrayList<>();
	String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
	//TextView lblListViewHeader;
	public WorkProgressReportListGNViewAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_SchemeCode=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");
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
			vi = (RelativeLayout) mLayoutInflater.inflate(R.layout.grid_view_voucher_item,	null);
			TextView wid = (TextView) vi.findViewById(R.id.tvID);
			TextView pid = (TextView) vi.findViewById(R.id.tvPanID);

			TextView tvMileStone = (TextView) vi.findViewById(R.id.tvMileStone);
			TextView txtNumOfHouse = (TextView) vi.findViewById(R.id.txtNumOfHouse);
		   // lblListViewHeader = (TextView) vi.findViewById(R.id.lblListViewHeader);

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

					Intent iListPendingUpload = new Intent(mContext, VoucherEntryActivity.class);
					iListPendingUpload.putExtra("EDIT", "EDIT");
					iListPendingUpload.putExtra("NTYPE", _SchemeCode);
					iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
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
						deleteRec(mEntries.get(position).getRow_id());
						String userid= PreferenceManager.getDefaultSharedPreferences(mContext).getString("USER_ID", "");

						final ArrayList<WorkProgressReportGNProperty> entries = getLocalPendingView(userid,_SchemeCode);

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
//				if(((PendingViewListActivity)mContext).getNumOfRecords(mEntries.get(position).get_Ward_ID(),mEntries.get(position).get_Pan_Code())<10)
//				{
//					AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//					ab.setTitle("Condition Applied");
//					ab.setMessage("Sorry! You have taken only " + ((PendingViewListActivity)mContext).getNumOfRecords(mEntries.get(position).get_Ward_ID(),mEntries.get(position).get_Pan_Code())+ " latitude and longitude values. For each ward you should take minimum 10 latitude - longitude values before uploading it to server.");
//					ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int whichButton) {
//
//							dialog.dismiss();
//
//						}
//					});
//
//					ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
//					ab.show();
//				}
//				else
//				{
//					((PendingViewListActivity)mContext).UploadPendingToServer(mEntries.get(position).get_Ward_ID(),mEntries.get(position).get_Pan_Code());
//				}

			}
		});
		wid.setText(String.valueOf(mEntries.get(position).getRow_id()));
		tvMileStone.setText(String.valueOf(mEntries.get(position).getBlockCode()));
		txtNumOfHouse.setText(String.valueOf(mEntries.get(position).getDistCode()));
		if(_SchemeCode.equalsIgnoreCase("1"))
		{
			//lblListViewHeader.setText(R.string.headertext1);
		}
		if(_SchemeCode.equalsIgnoreCase("2"))
		{
			//lblListViewHeader.setText(R.string.headertext2);
		}
		//cnt.setText(String.valueOf(mEntries.get(position).get_Count()));
		return vi;
	}

	public long deleteRec(String id)
	{
		long c = -1;

		try {
			SQLiteHelper placeData = new SQLiteHelper(mContext);
			SQLiteDatabase db = placeData.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = id;
			c = db.delete("VoucherEntryDetails", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	private ArrayList<WorkProgressReportGNProperty> getLocalPendingView(String userid,String ncode) {

		SQLiteHelper placeData = new SQLiteHelper(mContext);
		SQLiteDatabase db = placeData.getReadableDatabase();
		_WardCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WARD_CODE", "");
		_WardName= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WARD_NAME", "");
		_FYearCode= PreferenceManager.getDefaultSharedPreferences(mContext).getString("FYEAR_CODE", "");
		_SchemeName=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_NAME", "");


		if(_SchemeCode.equalsIgnoreCase("1"))
		{
			//lblListViewHeader.setText(R.string.headertext1);
		}
		if(_SchemeCode.equalsIgnoreCase("2"))
		{
			//lblListViewHeader.setText(R.string.headertext2);
		}
		ArrayList<WorkProgressReportGNProperty> WorkList = new ArrayList<>();
		try {
			Cursor cursor = db
					.rawQuery(
							"SELECT id,ReceiverName,PaymentForWhat FROM VoucherEntryDetails where CreatedBy=? AND NischayCode=? ORDER BY id DESC",
							new String[]{userid,ncode});
			while (cursor.moveToNext()) {
				WorkProgressReportGNProperty wi = new WorkProgressReportGNProperty();

				wi.setRow_id(cursor.getString(0));
				wi.setBlockCode(cursor.getString(1));
				wi.setDistCode(cursor.getString(2));
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
	public void upDateEntries(ArrayList<WorkProgressReportGNProperty> entries) {
		mEntries = entries;
		notifyDataSetChanged();
	}

}
