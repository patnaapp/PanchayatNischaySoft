package app.bih.in.nic.nischayyojana.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.entity.PayJalListViewAdapter;
import app.bih.in.nic.nischayyojana.entity.PayJalProperty;


public class PayJalListActivity extends AppCompatActivity {

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB

    DataBaseHelper localDBHelper;
    String ids,userid;
    PayJalListViewAdapter adapter;

    ListView gv;

    public String srcButton = null;
    View view;
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
    int IMG_WIDTH=500,IMG_HEIGHT=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_jal_list);

        getSupportActionBar().hide();
        localDBHelper = new DataBaseHelper(this);
        adapter = new PayJalListViewAdapter(this);

        gv = (ListView) findViewById(R.id.grid);
        gv.setEmptyView(findViewById(R.id.empty_list_view));

        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _SchemeCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");

        final ArrayList<PayJalProperty> WorkList = getLocalPendingView(userid);
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
        final ArrayList<PayJalProperty> WorkList = getLocalPendingView(userid);
        adapter.upDateEntries(WorkList);
        super.onResume();
    }
    private ArrayList<PayJalProperty> getLocalPendingView(String userid) {

        SQLiteHelper placeData = new SQLiteHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        ArrayList<PayJalProperty> WorkList = new ArrayList<>();
        try {

            Cursor cursor = db.rawQuery("SELECT id,isBoringStarted,isStaggingDone,ProjectCompleteionDate FROM PayJaltbl WHERE CreatedBy='"+userid +"' AND Latitude is not null ORDER BY id DESC", null);
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
    public void ShowData()
    {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PayJaltbl where CreatedBy=?",new String[]{String.valueOf(userid)});

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
        Intent i=new Intent(PayJalListActivity.this, LoginActivity.class);
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
