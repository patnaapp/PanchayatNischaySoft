package app.bih.in.nic.nischayyojana.entity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.bih.in.nic.nischayyojana.R;


public class PendingUploadsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<WorkPendingUpload> mEntries = new ArrayList<WorkPendingUpload>();

	public PendingUploadsAdapter(Context context) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = (LinearLayout) mLayoutInflater.inflate(R.layout.grid_item,
					null);
		
		TextView wdid = (TextView) vi.findViewById(R.id.wdid);
		TextView qty = (TextView) vi.findViewById(R.id.Qty);
		TextView date = (TextView) vi.findViewById(R.id.date);
		
		wdid.setText(String.valueOf(mEntries.get(position).get_id()));
		qty.setText(String.valueOf(mEntries.get(position).get_Qty()));
		date.setText(mEntries.get(position).get_EntryDate());
		return vi;
		
	}

	public void upDateEntries(ArrayList<WorkPendingUpload> entries) {
		mEntries = entries;
		notifyDataSetChanged();
	}

}
