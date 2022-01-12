package app.bih.in.nic.nischayyojana.entity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.ui.ConsolidatedRptSchemeProfileActivity;
import app.bih.in.nic.nischayyojana.ui.GaliNaliActivity;
import app.bih.in.nic.nischayyojana.ui.PayJalActivity;
import app.bih.in.nic.nischayyojana.ui.UserHomeActivity;
import app.bih.in.nic.nischayyojana.util.CommonPref;


public class WorkProgressReportListViewAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<WorkProgressReportProperty> mEntries = new ArrayList<>();
	String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
	//TextView lblListViewHeader;
	public WorkProgressReportListViewAdapter(Context context) {
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
			vi = (RelativeLayout) mLayoutInflater.inflate(R.layout.grid_view_work_progress_report_item,	null);
		TextView wid = (TextView) vi.findViewById(R.id.tvID);

		TextView txtWardNumber = (TextView) vi.findViewById(R.id.txtWardNumber);
		TextView txtyojana = (TextView) vi.findViewById(R.id.txtyojana);

		TextView txtTotalDistribution = (TextView) vi.findViewById(R.id.txtTotalDistribution);
		TextView txtTotalExpenditure = (TextView) vi.findViewById(R.id.txtTotalExpenditure);
		TextView txtPhysicalStatus = (TextView) vi.findViewById(R.id.txtPhysicalStatus);

		txtPhysicalStatus.setTextColor(Color.BLUE);
		txtyojana.setTextColor(Color.BLUE);


		final String work_type;

		work_type= PreferenceManager.getDefaultSharedPreferences(mContext).getString("WORK_TYPE", "");

		txtPhysicalStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				_SchemeCode=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");


				if(_SchemeCode.equalsIgnoreCase("1"))
				{
					Intent iListPendingUpload = new Intent(mContext, PayJalActivity.class);
					iListPendingUpload.putExtra("VIEW", "VIEW");
					iListPendingUpload.putExtra("NTYPE", _SchemeCode);
					iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
					mContext.startActivity(iListPendingUpload);
				}
				else if(_SchemeCode.equalsIgnoreCase("2"))
				{
					Intent iListPendingUpload = new Intent(mContext, GaliNaliActivity.class);
					iListPendingUpload.putExtra("VIEW", "VIEW");
					iListPendingUpload.putExtra("NTYPE", _SchemeCode);
					iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
					mContext.startActivity(iListPendingUpload);
				}
			}
		});

		txtyojana.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM")) {
					_SchemeCode = PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");
					if(work_type.equalsIgnoreCase("PV")) {
						Intent iListPendingUpload = new Intent(mContext, ConsolidatedRptSchemeProfileActivity.class);
						iListPendingUpload.putExtra("VIEW", "VIEW");
						iListPendingUpload.putExtra("NTYPE", _SchemeCode);
						iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
						mContext.startActivity(iListPendingUpload);
					}
					else if(work_type.equalsIgnoreCase("PI")) {
						if(_SchemeCode.equalsIgnoreCase("1"))
						{
							Intent iListPendingUpload = new Intent(mContext, PayJalActivity.class);
							iListPendingUpload.putExtra("VIEW", "VIEW");
							iListPendingUpload.putExtra("NTYPE", _SchemeCode);
							iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
							mContext.startActivity(iListPendingUpload);
						}
						else if(_SchemeCode.equalsIgnoreCase("2"))
						{
							Intent iListPendingUpload = new Intent(mContext, GaliNaliActivity.class);
							iListPendingUpload.putExtra("VIEW", "VIEW");
							iListPendingUpload.putExtra("NTYPE", _SchemeCode);
							iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
							mContext.startActivity(iListPendingUpload);
						}
					}
				}
				else
				{
					if(_SchemeCode.equalsIgnoreCase("1"))
					{
						Intent iListPendingUpload = new Intent(mContext, PayJalActivity.class);
						iListPendingUpload.putExtra("VIEW", "VIEW");
						iListPendingUpload.putExtra("NTYPE", _SchemeCode);
						iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
						mContext.startActivity(iListPendingUpload);
					}
					else if(_SchemeCode.equalsIgnoreCase("2"))
					{
						Intent iListPendingUpload = new Intent(mContext, GaliNaliActivity.class);
						iListPendingUpload.putExtra("VIEW", "VIEW");
						iListPendingUpload.putExtra("NTYPE", _SchemeCode);
						iListPendingUpload.putExtra("ID", mEntries.get(position).getRow_id());
						mContext.startActivity(iListPendingUpload);
					}
				}

			}
		});

		wid.setText(String.valueOf(mEntries.get(position).getRow_id()));
		txtWardNumber.setText(String.valueOf(mEntries.get(position).getWardName()));
		txtyojana.setText(String.valueOf(mEntries.get(position).getYojanaTypeNAME()));

		txtyojana.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		txtyojana.setSelected(true);
		txtyojana.setSingleLine(true);


		if(CommonPref.getUserDetails(mContext).get_Role().equalsIgnoreCase("PANADM")) {
			if(work_type.equalsIgnoreCase("PV")) {
				txtTotalDistribution.setText(String.valueOf(mEntries.get(position).getTotalAllotment()));
				txtTotalExpenditure.setText(String.valueOf(mEntries.get(position).getTotalExpenditure()));
			}
			else if(work_type.equalsIgnoreCase("PI")) {
				txtTotalDistribution.setText(String.valueOf(mEntries.get(position).getCreatedDate()));
				txtTotalExpenditure.setText(String.valueOf(mEntries.get(position).getRemarks()));
				txtTotalExpenditure.setEllipsize(TextUtils.TruncateAt.MARQUEE);
				txtTotalExpenditure.setSelected(true);
				txtTotalExpenditure.setSingleLine(true);
			}
		}
		else
		{
			txtTotalDistribution.setText(String.valueOf(mEntries.get(position).getCreatedDate()));
			txtTotalExpenditure.setText(String.valueOf(mEntries.get(position).getRemarks()));
			txtTotalExpenditure.setEllipsize(TextUtils.TruncateAt.MARQUEE);
			txtTotalExpenditure.setSelected(true);
			txtTotalExpenditure.setSingleLine(true);
		}

		txtPhysicalStatus.setText(getPhysicalStatus(String.valueOf(mEntries.get(position).getCurrentPhysicalStatus())));
		_SchemeCode=PreferenceManager.getDefaultSharedPreferences(mContext).getString("SCHEME_CODE", "");

		return vi;
	}

	public String getPhysicalStatus(String phcode)
	{
		DataBaseHelper placeData = new DataBaseHelper(mContext);
		SQLiteDatabase db = placeData.getWritableDatabase();
		return placeData.getProjectStatus(phcode);
	}

	public void upDateEntries(ArrayList<WorkProgressReportProperty> entries) {
		mEntries = entries;
		notifyDataSetChanged();
	}

}
