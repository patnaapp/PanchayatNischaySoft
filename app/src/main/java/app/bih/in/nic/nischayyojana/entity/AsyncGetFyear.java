package app.bih.in.nic.nischayyojana.entity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;


public class AsyncGetFyear extends AsyncTask<Void, Void, ArrayList<Fyear>> {
	Context mContext;
	public AsyncGetFyear (Context context)
	{
	       mContext =context;
	}
	

	private final ProgressDialog dialog = new ProgressDialog(mContext);

	//private final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

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

			SQLiteHelper placeData = new SQLiteHelper(mContext);
			placeData.insertFyear(result);

		}

	}

}