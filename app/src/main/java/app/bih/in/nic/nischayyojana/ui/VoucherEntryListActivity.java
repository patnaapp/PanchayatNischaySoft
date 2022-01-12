package app.bih.in.nic.nischayyojana.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.entity.VoucherEntryListViewAdapter;
import app.bih.in.nic.nischayyojana.entity.VoucherEntryProperty;


public class VoucherEntryListActivity extends AppCompatActivity {

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB

    DataBaseHelper localDBHelper;
    String ids,userid;
    VoucherEntryListViewAdapter adapter;

    ListView gv;
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
    public String srcButton = null;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_progress_list);
        getSupportActionBar().hide();

        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _SchemeCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");
        localDBHelper = new DataBaseHelper(this);
        adapter = new VoucherEntryListViewAdapter(this);

        gv = (ListView) findViewById(R.id.grid);
        gv.setEmptyView(findViewById(R.id.empty_list_view));
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

        final ArrayList<VoucherEntryProperty> WorkList = getLocalPendingView(userid,_SchemeCode);
        adapter.upDateEntries(WorkList);

        gv.setAdapter(adapter);

        if(getIntent().hasExtra("EDIT"))
        {
            ids = getIntent().getStringExtra("ID");


            if (ids == null) {
                ids = "0";
            }
            else
            {
                ShowData();
            }
        }

    }
    @Override
    protected void onResume() {
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        final ArrayList<VoucherEntryProperty> WorkList = getLocalPendingView(userid,_SchemeCode);
        adapter.upDateEntries(WorkList);
        super.onResume();
    }
    private ArrayList<VoucherEntryProperty> getLocalPendingView(String userid,String ncode) {

        SQLiteHelper placeData = new SQLiteHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        ArrayList<VoucherEntryProperty> WorkList = new ArrayList<>();
        try {

            Cursor cursor = db
                    .rawQuery(
                            "SELECT id,ReceiverName,PaymentForWhat FROM VoucherEntryDetails where CreatedBy=? AND NischayCode=? ORDER BY id DESC",
                            new String[]{userid,ncode});
          while (cursor.moveToNext()) {
                VoucherEntryProperty wi = new VoucherEntryProperty();

                wi.set_Row_ID(cursor.getString(0));
                wi.setReceiverName(cursor.getString(1));
                wi.set_Purpose(cursor.getString(2));
                WorkList.add(wi);
            }
            Log.e("C COunt",""+cursor.getCount());
            cursor.close();
            db.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return WorkList;
    }
    public void ShowData()
    {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db
                .rawQuery(
                        "SELECT * FROM VoucherEntryDetails where id=?",
                        new String[]{String.valueOf(ids)});



        db.close();
        cursor.close();

    }
    public void OnClick_goToHomeScreen(View v)
    {
        finish();
       // startActivity(new Intent(PayJalActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(VoucherEntryListActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"Back press is not allowed, To view/update previous page info login again.",Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}
