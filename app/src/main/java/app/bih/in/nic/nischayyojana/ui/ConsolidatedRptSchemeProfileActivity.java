package app.bih.in.nic.nischayyojana.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.entity.Block;
import app.bih.in.nic.nischayyojana.entity.District;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.util.CommonPref;

public class ConsolidatedRptSchemeProfileActivity extends AppCompatActivity {
    DataBaseHelper localDBHelper;

    String _SchemeName;
    Spinner spDistrict,spBlock,spPanchayat,spinnerNischayType,spinnerFYear;

    String _vardistrictName="",_vardistrictID="";
    String _varblockName="",_varblockID="";
    String _varpanchayatName="",_varpanchayatID="";

    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayAdapter<String> districtadapter;

    ArrayList<Block> BlockList = new ArrayList<Block>();
    ArrayAdapter<String> blockadapter;
    ArrayList<String> BlocktStringList;

    ArrayList<PanchayatData> PanchayatList = new ArrayList<PanchayatData>();
    ArrayAdapter<String> panchayatadapter;

    ArrayList<SCHEME> NischayTypeList = new ArrayList<>();
    ArrayAdapter<String> NischayTypeadapter;
    String _varNischayTypeNAME="",_varNischayTypeID="0",setNischayTypeID="0";

    ArrayList<Fyear> FyearList = new ArrayList<>();
    ArrayAdapter<String> fyearadapter;
    String _varFYearID="0",_varFYearName="";

    TextView txtYojanaID,txtFYear,txtWardName,txtyojanaNumber,txtYojanaName,txtAnurodhKiTarikh;
    TextView txtPrakalJamaKiTarikh,txtTaknikiSwikrityAmount,txtTaknikiSwikrityDate,txtGramPanchayatSwikrityDate;
    TextView txtGramPanchayatSwikrityAmount,txtNischayPrabhariName,txtNischayPrabhariMobNum,txtYojanaKAbiyantName;
    TextView txtYojanaKAbiyantMobNum,txtPrashakhiBibhagKaName, txtMapPustNumber, txtWorkStartDate,txtWorkCompletionDate;

    String _row_id="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidated_rpt_scheme_profile);

        spDistrict=(Spinner)findViewById(R.id.spinnerdistrict);
        spBlock=(Spinner)findViewById(R.id.spinnerblock);
        spPanchayat=(Spinner)findViewById(R.id.spinnerpanchayat);
        spinnerNischayType= findViewById(R.id.spinnernischaytype) ;
        spinnerFYear = findViewById(R.id.spinnerFYear) ;
//txtYojanaID,txtFYear,txtWardName,txtyojanaNumber,txtYojanaName,txtAnurodhKiTarikh;

        txtYojanaID=findViewById(R.id.txtYojanaID);
        txtFYear=findViewById(R.id.txtFYear);
        txtWardName=findViewById(R.id.txtWardName);
        txtyojanaNumber=findViewById(R.id.txtyojanaNumber);
        txtYojanaName=findViewById(R.id.txtYojanaName);
        txtAnurodhKiTarikh=findViewById(R.id.txtAnurodhKiTarikh);

        txtPrakalJamaKiTarikh=findViewById(R.id.txtPrakalJamaKiTarikh);
        txtTaknikiSwikrityAmount=findViewById(R.id.txtTaknikiSwikrityAmount);
        txtTaknikiSwikrityDate=findViewById(R.id.txtTaknikiSwikrityDate);
        txtGramPanchayatSwikrityDate=findViewById(R.id.txtGramPanchayatSwikrityDate);
        txtGramPanchayatSwikrityAmount=findViewById(R.id.txtGramPanchayatSwikrityAmount);
        txtNischayPrabhariName=findViewById(R.id.txtNischayPrabhariName);
        txtNischayPrabhariMobNum=findViewById(R.id.txtNischayPrabhariMobNum);
        txtYojanaKAbiyantName=findViewById(R.id.txtYojanaKAbiyantName);

        txtYojanaKAbiyantMobNum=findViewById(R.id.txtYojanaKAbiyantMobNum);
        txtPrashakhiBibhagKaName=findViewById(R.id.txtPrashakhiBibhagKaName);
        txtMapPustNumber=findViewById(R.id.txtMapPustNumber);
        txtWorkStartDate=findViewById(R.id.txtWorkStartDate);
        txtWorkCompletionDate=findViewById(R.id.txtWorkCompletionDate);


        localDBHelper = new DataBaseHelper(this);

        _vardistrictID= CommonPref.getUserDetails(ConsolidatedRptSchemeProfileActivity.this).get_DistCode();
        _varblockID=CommonPref.getUserDetails(ConsolidatedRptSchemeProfileActivity.this).get_BlockCode();
        _varpanchayatID=CommonPref.getUserDetails(ConsolidatedRptSchemeProfileActivity.this).get_PanchayatCode();

        if(_vardistrictID.trim().length()<3)
        {
            _vardistrictID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DIST_CODE", "");
        }
        if(_varblockID.trim().length()<3)
        {
            _varblockID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("BLOCK_CODE", "");
        }
        if(_varpanchayatID.trim().length()<3)
        {
            _varpanchayatID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PAN_CODE", "");
        }



        if(getIntent().hasExtra("NTYPE"))
        {
            _varNischayTypeID=getIntent().getStringExtra("NTYPE").toString();
        }

        if(getIntent().hasExtra("ID"))
        {
            _row_id=getIntent().getStringExtra("ID").toString();
        }

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    District dist = DistrictList.get(arg2 - 1);
                    _vardistrictID = dist.get_DistCode();
                    // setBlockSpinnerData();

                } else {

                    spBlock.setSelection(0);
                    spPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    Block blk = BlockList.get(arg2 - 1);
                    _varblockID = blk.getBlockCode();
                    //setPanchayatSpinnerData();
                }
                else if(arg2==0)
                {
                    spPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    PanchayatData wrd = PanchayatList.get(arg2 - 1);
                    _varpanchayatID = wrd.getPcode();
                    // setWardSpinnerData();
                }
                else if(arg2==0)
                {
                    // spWard.setSelection(0);
                    // spVillage.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerNischayType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    SCHEME wrd = NischayTypeList.get(arg2 - 1);
                    _varNischayTypeID = wrd.get_SchemeID();
                    _varNischayTypeNAME= wrd.get_SchemeName();

                }
                else
                {
                    _varNischayTypeID = "";
                    _varNischayTypeNAME= "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerFYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    _varFYearID = FyearList.get(arg2-1).getFyId();
                    _varFYearName = FyearList.get(arg2-1).getFy();
                    LoadData("0",_varFYearID,_varNischayTypeID,_varpanchayatID);
                }
                else
                {
                    _varFYearID="0";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //loadFYear();
        loadDistrictSpinnerdata();
        setBlockSpinnerData();
        setPanchayatSpinnerData();
        getSchemeSpinnerData();

//        if(CommonPref.getUserDetails(ConsolidatedRptSchemeProfileActivity.this).get_Role().equalsIgnoreCase("PANADM"))
//        {
//            spDistrict.setEnabled(false);
//            spBlock.setEnabled(false);
//            spPanchayat.setEnabled(false);
//            spinnerNischayType.setEnabled(false);
//        }
//        else
//        {
//            spDistrict.setEnabled(true);
//            spBlock.setEnabled(true);
//            spPanchayat.setEnabled(true);
//            spinnerNischayType.setEnabled(true);
//        }

        spDistrict.setEnabled(false);
        spBlock.setEnabled(false);
        spPanchayat.setEnabled(false);
        spinnerNischayType.setEnabled(false);

        LoadData(_row_id,"0",_varNischayTypeID,_varpanchayatID);
    }

    public void loadDistrictSpinnerdata() {

        DistrictList = localDBHelper.getDistrict();
        String[] divNameArray = new String[DistrictList.size() + 1];
        divNameArray[0] = "-जिला चुनें-";
        int i = 1;
        int setID=0;
        for (District dist : DistrictList) {

            divNameArray[i] = dist.get_DistName();
            if(_vardistrictID.equalsIgnoreCase(DistrictList.get(i-1).get_DistCode()))
            {
                setID=i;
            }
            i++;
        }
        districtadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
        districtadapter.setDropDownViewResource(R.layout.dropdownlist);
        spDistrict.setAdapter(districtadapter);

        spDistrict.setSelection(setID);
    }
    public void setBlockSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(ConsolidatedRptSchemeProfileActivity.this);
        BlockList=placeData.getBlock(_vardistrictID);

        if(BlockList.size()>0) loadBlockSpinnerData(BlockList);
    }
    public void setPanchayatSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(ConsolidatedRptSchemeProfileActivity.this);
        PanchayatList=placeData.getPanchayatLocal(_varblockID);
        if(PanchayatList.size()>0 ) loadPanchayatSpinnerData(PanchayatList);
    }


    private void loadBlockSpinnerData(ArrayList<Block> bList)
    {
        BlocktStringList=new ArrayList<String>();

        BlocktStringList.add("-प्रखंड चुनें-");
        int setID=0;
        for(int i=0;i<bList.size();i++)
        {
            BlocktStringList.add(bList.get(i).getBlockName());
            if(_varblockID.equalsIgnoreCase(bList.get(i).getBlockCode()))
            {
                setID=i+1;
            }
        }
        blockadapter=new ArrayAdapter(this,R.layout.dropdownlist,BlocktStringList);
        spBlock.setAdapter(blockadapter);
        spBlock.setSelection(setID);
    }
    private void loadPanchayatSpinnerData(ArrayList<PanchayatData> pList)
    {
        ArrayList<String> PanchayatStringList=new ArrayList<String>();
        PanchayatStringList.add("-पंचायत चुनें-");
        int setID=0;
        for(int i=0;i<pList.size();i++)
        {
            PanchayatStringList.add(pList.get(i).getPname());
            if(_varpanchayatID.equalsIgnoreCase(pList.get(i).getPcode()))
            {
                setID=i+1;
            }
        }
        panchayatadapter=new ArrayAdapter(this,R.layout.dropdownlist,PanchayatStringList);
        spPanchayat.setAdapter(panchayatadapter);
        spPanchayat.setSelection(setID);
    }



    private void getSchemeSpinnerData()
    {
        localDBHelper = new DataBaseHelper(ConsolidatedRptSchemeProfileActivity.this);
        NischayTypeList=localDBHelper.getSchemeList();
        if(NischayTypeList.size()>0 ) {
            loadSchemeSpinnerData();
        }
    }
    private void loadSchemeSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-निश्चय के प्रकार चुनें-");
        setNischayTypeID="0";
        for(int i=0;i<NischayTypeList.size();i++)
        {
            StringList.add(NischayTypeList.get(i).get_SchemeName());
            if(_varNischayTypeID.equalsIgnoreCase(NischayTypeList.get(i).get_SchemeID()))
            {
                setNischayTypeID=""+ (i+1);
                _SchemeName=NischayTypeList.get(i).get_SchemeName();
            }
        }
        NischayTypeadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerNischayType.setAdapter(NischayTypeadapter);

        spinnerNischayType.setSelection(Integer.parseInt(setNischayTypeID));
    }
    public  void loadFYear()
    {
        localDBHelper = new DataBaseHelper(ConsolidatedRptSchemeProfileActivity.this);
        FyearList=localDBHelper.getFYear();
        if(FyearList.size()>0 ) {
            loadFYearSpinnerData();
        }
    }


    private void loadFYearSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        int setID=0;
        for(int i=0;i<FyearList.size();i++)
        {
            StringList.add(FyearList.get(i).getFy());
            if(_varFYearID.equalsIgnoreCase(FyearList.get(i).getFyId()))
            {
                setID=(i+1);
            }
        }
        fyearadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerFYear.setAdapter(fyearadapter);

        if(getIntent().hasExtra("VIEW"))
        {
            spinnerFYear.setSelection(setID);
        }
    }





    public void onClick_GoBack(View v)
    {
        finish();
    }
    public void OnClick_goToHomeScreen(View v)
    {
        finish();
        // startActivity(new Intent(ConsolidatedRptSchemeProfileActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(ConsolidatedRptSchemeProfileActivity.this, LoginActivity.class);
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

    public  void LoadData(String thisRowID,String thsiFYID,String ntid,String panCode)
    {
        SQLiteHelper db=new SQLiteHelper(ConsolidatedRptSchemeProfileActivity.this);
        Cursor cursor=db.getRecords(thisRowID, thsiFYID, ntid,panCode);

        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                txtYojanaID.setText(cursor.getString(cursor.getColumnIndex("NischayCode")));

                _varFYearID=cursor.getString(cursor.getColumnIndex("FYear"));
                loadFYear();

                txtFYear.setText(cursor.getString(cursor.getColumnIndex("FYear")));
                txtWardName.setText(cursor.getString(cursor.getColumnIndex("WardName")));
                txtyojanaNumber.setText(cursor.getString(cursor.getColumnIndex("YojanaCode")));

                txtYojanaName.setText(cursor.getString(cursor.getColumnIndex("YojanaTypeNAME")));

                try{
                    String td1=cursor.getString(cursor.getColumnIndex("RequestDate"));
                    txtAnurodhKiTarikh.setText(td1.substring(0,10));

                    String td2=cursor.getString(cursor.getColumnIndex("SubmitDate"));
                    txtPrakalJamaKiTarikh.setText(td2.substring(0,10));

                    txtTaknikiSwikrityAmount.setText(cursor.getString(cursor.getColumnIndex("TechAcceptedAmount")));

                    String td=cursor.getString(cursor.getColumnIndex("TechAcceptedDate"));
                    txtTaknikiSwikrityDate.setText(td.substring(0,10));

                    //txtGramPanchayatSwikrityDate.setText(cursor.getString(cursor.getColumnIndex("PanAcceptedDate")));

                    String td3=cursor.getString(cursor.getColumnIndex("PanAcceptedDate"));
                    txtGramPanchayatSwikrityDate.setText(td3.substring(0,10));

                }catch (Exception e){
                    Toast.makeText(this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }



                txtGramPanchayatSwikrityAmount.setText(cursor.getString(cursor.getColumnIndex("PanAcceptedAmount")));
                txtNischayPrabhariName.setText(cursor.getString(cursor.getColumnIndex("PanNischayPrbhariName")));
                txtNischayPrabhariMobNum.setText(cursor.getString(cursor.getColumnIndex("PanNischayPrbhariMobNum")));
                txtYojanaKAbiyantName.setText(cursor.getString(cursor.getColumnIndex("PanYojanaPrbhariName")));

                txtYojanaKAbiyantMobNum.setText(cursor.getString(cursor.getColumnIndex("PanYojanaPrbhariMobNum")));
                txtPrashakhiBibhagKaName.setText(cursor.getString(cursor.getColumnIndex("PrashakhiDeptName")));
                txtMapPustNumber.setText(cursor.getString(cursor.getColumnIndex("MaapPustNum")));

                //txtWorkStartDate.setText(cursor.getString(cursor.getColumnIndex("WorkStartingDate")));

                String td4=cursor.getString(cursor.getColumnIndex("WorkStartingDate"));
                txtWorkStartDate.setText(td4.substring(0,10));

                txtWorkCompletionDate.setText(cursor.getString(cursor.getColumnIndex("WorkEndDuration"))+ " महीना");
            }
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(ConsolidatedRptSchemeProfileActivity.this);
            ab.setTitle("रिकॉर्ड नहीं मिला");
            ab.setMessage("माफ़ कीजिये ! वित्तीय वर्ष "+ _varFYearName  + " के लिए कोई रिकॉर्ड नहीं मिला");
            ab.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    dialog.dismiss();
                    clearTextView();
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
        }
        db.close();
        cursor.close();

    }

    public void clearTextView()
    {
        txtFYear.setText("");
        txtWardName.setText("");
        txtyojanaNumber.setText("");
        txtYojanaName.setText("");
        txtAnurodhKiTarikh.setText("");
        txtPrakalJamaKiTarikh.setText("");
        txtTaknikiSwikrityAmount.setText("");
        txtTaknikiSwikrityDate.setText("");
        //txtGramPanchayatSwikrityDate.setText("");
        txtGramPanchayatSwikrityDate.setText("");
        txtGramPanchayatSwikrityAmount.setText("");
        txtNischayPrabhariName.setText("");
        txtNischayPrabhariMobNum.setText("");
        txtYojanaKAbiyantName.setText("");
        txtYojanaKAbiyantMobNum.setText("");
        txtPrashakhiBibhagKaName.setText("");
        txtMapPustNumber.setText("");
        //txtWorkStartDate.setText("");
        txtWorkStartDate.setText("");
        txtWorkCompletionDate.setText("");
    }
}
