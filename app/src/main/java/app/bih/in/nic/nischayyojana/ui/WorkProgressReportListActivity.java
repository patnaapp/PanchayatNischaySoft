package app.bih.in.nic.nischayyojana.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.entity.Block;
import app.bih.in.nic.nischayyojana.entity.District;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportListViewAdapter;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportProperty;
import app.bih.in.nic.nischayyojana.util.CommonPref;


public class WorkProgressReportListActivity extends AppCompatActivity {

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB

    DataBaseHelper localDBHelper;
    String ids,userid;
    WorkProgressReportListViewAdapter adapter;

    ListView gv;
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;

    Spinner spDistrict,spBlock,spPanchayat,spinnerNischayType;
    
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
    String _varNischayTypeNAME="",_varNischayTypeID="",setNischayTypeID="0";
    LinearLayout lnBasicDetails,lnSearch,lnOtherDetails,lnOtherDetailsNEW;
    TextView lblYojanaName;
//    lblDBP,
    public String srcButton = null;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_progress_report_list);
        getSupportActionBar().hide();

        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");

        spDistrict=(Spinner)findViewById(R.id.spinnerdistrict);
        spBlock=(Spinner)findViewById(R.id.spinnerblock);
        spPanchayat=(Spinner)findViewById(R.id.spinnerpanchayat);
        spinnerNischayType= findViewById(R.id.spinnernischaytype) ;
        lnBasicDetails= findViewById(R.id.lnBasicDetails) ;
        lnOtherDetails= findViewById(R.id.lnOtherDetails) ;
        lnOtherDetailsNEW= findViewById(R.id.lnOtherDetailsNEW) ;
       // lblDBP= findViewById(R.id.lblDBP) ;
        lblYojanaName= findViewById(R.id.lblYojanaName) ;
        lnSearch= findViewById(R.id.lnSearch) ;

        lnBasicDetails.setVisibility(View.GONE);
        lnOtherDetails.setVisibility(View.GONE);
        lnOtherDetailsNEW.setVisibility(View.GONE);

        localDBHelper = new DataBaseHelper(this);
        adapter = new WorkProgressReportListViewAdapter(this);

        gv = (ListView) findViewById(R.id.grid);
        gv.setEmptyView(findViewById(R.id.empty_list_view));
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

        _vardistrictID= CommonPref.getUserDetails(WorkProgressReportListActivity.this).get_DistCode();
        _varblockID=CommonPref.getUserDetails(WorkProgressReportListActivity.this).get_BlockCode();
        _varpanchayatID=CommonPref.getUserDetails(WorkProgressReportListActivity.this).get_PanchayatCode();
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
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    District dist = DistrictList.get(arg2 - 1);
                    _vardistrictID = dist.get_DistCode();
                    _vardistrictName=dist.get_DistName();
                     setBlockSpinnerData();

                } else {
                    _vardistrictID = "0";
                    _vardistrictName="NA";
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
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    Block blk = BlockList.get(arg2 - 1);
                    _varblockID = blk.getBlockCode();
                    _varblockName=blk.getBlockName();
                    setPanchayatSpinnerData();
                }
                else if(arg2==0)
                {
                    _varblockID = "0";
                    _varblockName="0";
                    spPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    PanchayatData wrd = PanchayatList.get(arg2 - 1);
                    _varpanchayatID = wrd.getPcode();
                    _varpanchayatName=wrd.getPname();
                    //setWardSpinnerData();
                }
                else if(arg2==0)
                {
                    // spWard.setSelection(0);
                    // spVillage.setSelection(0);
                    _varpanchayatID = "0";
                    _varpanchayatName="0";
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
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", _varNischayTypeID ).commit();
                    getLocalData(_varNischayTypeID,_varpanchayatID);

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
        
        loadDistrictSpinnerdata();
        //setBlockSpinnerData();
        //setPanchayatSpinnerData();
        getSchemeSpinnerData();
        if(CommonPref.getUserDetails(WorkProgressReportListActivity.this).get_Role().equalsIgnoreCase("PANADM"))
        {
            spDistrict.setEnabled(false);
            spBlock.setEnabled(false);
            spPanchayat.setEnabled(false);
        }
        else
        {
            spDistrict.setEnabled(true);
            spBlock.setEnabled(true);
            spPanchayat.setEnabled(true);
        }
        //
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
        DataBaseHelper placeData = new DataBaseHelper(WorkProgressReportListActivity.this);
        BlockList=placeData.getBlock(_vardistrictID);

        if(BlockList.size()>0) loadBlockSpinnerData(BlockList);
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
    public void setPanchayatSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(WorkProgressReportListActivity.this);
        PanchayatList=placeData.getPanchayatLocal(_varblockID);
        if(PanchayatList.size()>0 ) loadPanchayatSpinnerData(PanchayatList);
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
        localDBHelper = new DataBaseHelper(WorkProgressReportListActivity.this);
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

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerNischayType.setSelection(Integer.parseInt(setNischayTypeID));
        }
        if(getIntent().hasExtra("NTYPE")) {
            spinnerNischayType.setSelection(Integer.parseInt(setNischayTypeID));
        }
    }

    @Override
    protected void onResume() {
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        if(_varNischayTypeID!="") {
            final ArrayList<WorkProgressReportProperty> WorkList = getLocalData(_varNischayTypeID, _varpanchayatID);
            adapter.upDateEntries(WorkList);
        }
        super.onResume();
    }

     private ArrayList<WorkProgressReportProperty> getLocalData(String schemeID,String panCode) {

        DataBaseHelper localHelper=new DataBaseHelper(WorkProgressReportListActivity.this);
         String work_type= PreferenceManager.getDefaultSharedPreferences(WorkProgressReportListActivity.this).getString("WORK_TYPE", "");
        ArrayList<WorkProgressReportProperty> WorkList = localHelper.getLocalData(schemeID,panCode);
        if(WorkList.size()>0)
        {
            if(CommonPref.getUserDetails(WorkProgressReportListActivity.this).get_Role().equalsIgnoreCase("PANADM")) {

                if(work_type.equalsIgnoreCase("PI")) {
                    lnOtherDetailsNEW.setVisibility(View.VISIBLE);
                    lnOtherDetails.setVisibility(View.GONE);
                }
                else {
                    lnOtherDetails.setVisibility(View.VISIBLE);
                    lnOtherDetailsNEW.setVisibility(View.GONE);
                }
            }
            else {
                lnOtherDetailsNEW.setVisibility(View.VISIBLE);
                lnOtherDetails.setVisibility(View.GONE);
            }

         adapter.upDateEntries(WorkList);
         gv.setAdapter(adapter);
         adapter.notifyDataSetChanged();
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(WorkProgressReportListActivity.this);
            ab.setMessage("कोई रिकॉर्ड नहीं मिला. होम स्क्रीन पर जाएं और डेटा सिंक्रनाइज़ करें ");
            ab.setNegativeButton("[ X ]", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                   dialog.dismiss();
                }
            });
            ab.setPositiveButton("[ होम स्क्रीन पर जाएं ]", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    finish();
                }
            });
            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
        }
        return WorkList;
    }

    public void OnClick_SearchRecord(View v)
    {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
    }
    public void OnClick_goToHomeScreen(View v)
    {
        finish();
       // startActivity(new Intent(WorkProgressReportListActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(WorkProgressReportListActivity.this, LoginActivity.class);
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
