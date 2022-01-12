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
import app.bih.in.nic.nischayyojana.entity.GaliNaliListViewAdapter;
import app.bih.in.nic.nischayyojana.entity.GaliNaliProperty;


public class GaliNaliListActivity extends AppCompatActivity {

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB

    DataBaseHelper localDBHelper;
    String ids,userid;
    GaliNaliListViewAdapter adapter;

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
        adapter = new GaliNaliListViewAdapter(this);

        gv = (ListView) findViewById(R.id.grid);
        gv.setEmptyView(findViewById(R.id.empty_list_view));
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

        final ArrayList<GaliNaliProperty> WorkList = getLocalPendingView(userid);
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
        ShowData();
    }
    @Override
    protected void onResume() {
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        final ArrayList<GaliNaliProperty> WorkList = getLocalPendingView(userid);
        adapter.upDateEntries(WorkList);
        super.onResume();
    }
    private ArrayList<GaliNaliProperty> getLocalPendingView(String userid) {

        SQLiteHelper placeData = new SQLiteHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        /*CREATE TABLE "GaliNali" ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `FYear` TEXT, `PanCode` TEXT,
        `WardCode` TEXT, `wardName` TEXT, `NischayCode` TEXT, `YojanaCode` TEXT, `CurrentPhysicalStatus` TEXT,
        `currentPhysical_Name` TEXT, `RoadType` TEXT, `PathRoadType` TEXT, `NaliType` TEXT, `JalNikasi` TEXT,
        `TotalRoadDistance_Length` TEXT, `TotalRoadDistance_Breadth` TEXT, `TotalRoadDistance_Fat` TEXT, `TotalPathRoadDistance_Breadth` TEXT, `TotalNaliDistance_Length` TEXT, `TotalNali_Breadth` TEXT, `TotalNali_Gharai` TEXT, `SokhtaKiSankhya` TEXT, `MittiKrya` TEXT, `HugePipe` TEXT, `Remarks` TEXT, `CreatedDate` datetime, `CreatedBy` TEXT )
         */

        ArrayList<GaliNaliProperty> WorkList = new ArrayList<>();
        try {

            Cursor cursor = db
                    .rawQuery(
                            "SELECT id,YojanaCode,CurrentPhysicalStatus FROM GaliNali where CreatedBy=? AND Latitude Is Not Null ORDER BY id DESC",
                            new String[]{userid});
            while (cursor.moveToNext()) {
                GaliNaliProperty wi = new GaliNaliProperty();

                wi.set_Row_ID(cursor.getString(0));
                wi.set_MukhiyaName(getYojanaName(cursor.getString(1)));
                wi.set_MukhiyaMobNum(getProjectStatusName(cursor.getString(2)));
               // wi.set_GpSecName(cursor.getString(3));
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
    public void ShowData()
    {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        //SELECT id,PanCode,WardCode,YojanaCode,CurrentPhysicalStatus FROM GaliNali where CreatedBy=? AND id=? ORDER BY id DESC
//        Cursor cursor = db
//                .rawQuery(
//                        "SELECT * FROM GaliNali where id=?",
//                        new String[]{String.valueOf(ids)});

        Cursor cursor = db
                .rawQuery(
                        "SELECT * FROM GaliNali where CreatedBy=?",
                        new String[]{String.valueOf(userid)});



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
        Intent i=new Intent(GaliNaliListActivity.this, LoginActivity.class);
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
    public String getYojanaName(String yid)
    {
        DataBaseHelper helper=new DataBaseHelper(GaliNaliListActivity.this);
        return helper.getYojanaName(yid);
    }
    public String getProjectStatusName(String pid)
    {
        DataBaseHelper helper=new DataBaseHelper(GaliNaliListActivity.this);
        return helper.getProjectStatus(pid);
    }
}
