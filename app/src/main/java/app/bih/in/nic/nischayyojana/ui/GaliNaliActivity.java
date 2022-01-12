package app.bih.in.nic.nischayyojana.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.Block;
import app.bih.in.nic.nischayyojana.entity.CurrentPhysical;
import app.bih.in.nic.nischayyojana.entity.District;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.VillageData;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class GaliNaliActivity extends AppCompatActivity  implements View.OnClickListener{

    String usertype;
    EditText txtInspectorName,txtMBNumber;
    TextView txtInspectionDate;
    EditText txtOtherYojana;
    EditText txtInspectorPost;

    ImageView img_home,btncal1;
    Button btnsave ;
    CheckBox ProjectCompleted;
    String wantToEdit="no";
    //Spinner spDistrict,spBlock,spPanchayat;
    Spinner spinnerFYear,spinnerPanchayat,spinnerWard,spinnerNischayType,spinneryojanatype,
            spinnerCurrentPhysical,spinnerRoadType,spinnerNaliType,spinnerJalNikasi,spinnerMBCompleted;

    //spinnerPatriRoadType

    EditText txtTotalRoadDistance_Length,txtTotalRoadDistance_Breadth,txtTotalRoadDistance_Fat,
            txtTotalNaliDistance_Length,txtTotalNali_Breadth,txtTotalNali_Gharai,txtSokhtaKiSankhya,txtMittiKrya,txtHugePipe,txtRemarks;
//txtTotalPathRoadDistance_Breadth

    String _varRoadType,_varNaliType,_varJalNikasi,_varMPStatus;
    //_varPathRoadType
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;


    ArrayAdapter arrayAdapterRoadType;
    ArrayAdapter arrayAdapterMPStatus;
    ArrayAdapter arrayAdapterPathRoadType;
    ArrayAdapter arrayAdapterNaliType;
    ArrayAdapter arrayAdapterJalNikasi;
    String  RoadType[]={"-चुनें-","Bricks Soling","PCC","Paver Block","Bricks Soling+PCC","Other"};
    String  NaliType[]={"-चुनें-","RCC","Brick","Hume Pipe"};
    String  JalNikasi[]={"-चुनें-","हाँ","नहीं"};
    String  MPStatus[]={"-चुनें-","हाँ","नहीं"};

    TextView txtDateOfCompletionOfProject;

    DataBaseHelper localDBHelper;
    String ids,uid;

    String _vardistrictName="",_vardistrictID="";
    String _varblockName="",_varblockID="";
    String _varpanchayatName="",_varpanchayatID="";

    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayAdapter<String> districtadapter;

    ArrayList<Block> BlockList = new ArrayList<Block>();
    ArrayAdapter<String> blockadapter;
    ArrayList<String> BlocktStringList;

    ArrayList<PanchayatData> PanchayatList = new ArrayList<>();
    ArrayAdapter<String> Panchayatadapter;


    ArrayList<ward> WardList = new ArrayList<>();
    ArrayAdapter<String> wardadapter;
    String _varwardName="",_varwardID="",_varwardSetID="";

    ArrayList<SCHEME> NischayTypeList = new ArrayList<>();
    ArrayAdapter<String> NischayTypeadapter;
    String _varNischayTypeNAME="",_varNischayTypeID="",setNischayTypeID="0";

    ArrayList<YOJANA> YojanaTypeList = new ArrayList<>();
    ArrayAdapter<String> YojanaTypeadapter;
    String _varYojanaTypeNAME="",_varYojanaTypeID="",setYojanaTypeID="0";

    ArrayList<CurrentPhysical> CurrentphysicalList = new ArrayList<>();
    ArrayAdapter<String> physicaladapter;
    String _varcurrentPhysical_Name="",_varcurrentPhysical_Id="0";


    ArrayList<Fyear> FyearList = new ArrayList<>();
    ArrayAdapter<String> fyearadapter;
    String _varFYearID="0";

    String isProjectCompleted="N";
    ProgressDialog pd;
    LinearLayout lin_yojnaCompleteddate;
    LinearLayout ln_Inspection,lnMBNumber;

    private Calendar cal;
    private int day;
    private int month;
    private int year;
    boolean date1=false;

    Spinner spinnerTotalRoadDistance_Length,spinnerTotalRoadDistance_Breadth;
    //spinnernTotalPathRoadDistance_Breadth
    Spinner spinnernTotalNaliDistance_Length,spinnernTotalNali_Breadth,spinnernTotalNali_Gharai;

    Spinner spinnernMittiKrya,spinnerVillage;

    String _varTotalRoadDistance_LengthFtMt="0";
    String _varTotalRoadDistance_BreadthFtMt="0";
    String _varTotalPathRoadDistance_BreadthFtMt="0";
    String _varTotalNaliDistance_LengthFtMt="0";
    String _varTotalNali_BreadthFtMt="0";
    String _varTotalNali_GharaiFtMt="0";
    String _varMittiKryaFtMt="0";

    ArrayAdapter<String> spinnerFtMtdapter;
    String[] FtMt = new String[]{
            "-चुनें-",
            "फीट",
            "मीटर"
    };

    final List<String> FtMtList = new ArrayList<>(Arrays.asList(FtMt));


    ArrayAdapter<String> spinnerAreaFtMtdapter;
    String[] AreaFtMt = new String[]{
            "-चुनें-",
            "घन फीट",
            "घन मीटर"
    };

    final List<String> AreaFtMtList = new ArrayList<>(Arrays.asList(AreaFtMt));
    ArrayList<VillageData> VillageList = new ArrayList<>();
    ArrayAdapter<String> villageadapter;
    String _varVillageName="",_varVillageID="0";

    //1 ft³ = 0.02831685 m³
    String work_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gali_nali);
        getSupportActionBar().hide();
        localDBHelper= new DataBaseHelper(GaliNaliActivity.this);
        work_type= PreferenceManager.getDefaultSharedPreferences(GaliNaliActivity.this).getString("WORK_TYPE", "");
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");

        _varpanchayatID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PAN_CODE", "");

        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _SchemeCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");
        usertype = CommonPref.getUserDetails(GaliNaliActivity.this).get_Role();
        initialization();

    }
    public void initialization(){
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _varNischayTypeID=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");
        btnsave = (Button) findViewById(R.id.btnsave);

//        spDistrict=(Spinner)findViewById(R.id.spinnerdistrict);
//        spBlock=(Spinner)findViewById(R.id.spinnerblock);
//        spPanchayat=(Spinner)findViewById(R.id.spinnerpanchayat);

        spinnerFYear = findViewById(R.id.spinnerFYear) ;
        spinnerPanchayat = findViewById(R.id.spinnerPanchayat) ;
        spinnerVillage = findViewById(R.id.spinnerVillage) ;
        spinnerWard = findViewById(R.id.spinnerward) ;
        spinnerNischayType= findViewById(R.id.spinnernischaytype) ;
        spinneryojanatype= findViewById(R.id.spinneryojanatype) ;
        spinnerCurrentPhysical=(Spinner) findViewById(R.id.spinnernisCurrentPhysicalStatus);

        spinnerRoadType=(Spinner)findViewById(R.id.spinnerRoadType);
        // spinnerPatriRoadType=(Spinner)findViewById(R.id.spinnerPatriRoadType);
        spinnerNaliType=(Spinner)findViewById(R.id.spinnerNaliType);
        spinnerJalNikasi=(Spinner)findViewById(R.id.spinnerJalNikasi);

        spinnerTotalRoadDistance_Length= findViewById(R.id.spinnerTotalRoadDistance_Length) ;
        spinnerTotalRoadDistance_Breadth= findViewById(R.id.spinnerTotalRoadDistance_Breadth) ;
        //spinnernTotalPathRoadDistance_Breadth= findViewById(R.id.spinnernTotalPathRoadDistance_Breadth) ;
        spinnernTotalNaliDistance_Length= findViewById(R.id.spinnernTotalNaliDistance_Length) ;
        spinnernTotalNali_Breadth= findViewById(R.id.spinnernTotalNali_Breadth) ;
        spinnernTotalNali_Gharai= findViewById(R.id.spinnernTotalNali_Gharai) ;
        spinnernMittiKrya= findViewById(R.id.spinnernMittiKrya) ;
        spinnerMBCompleted= findViewById(R.id.spinnerMBCompleted) ;

        spinnerFtMtdapter=new ArrayAdapter(this,R.layout.dropdownlist,FtMtList);
        spinnerAreaFtMtdapter=new ArrayAdapter(this,R.layout.dropdownlist,AreaFtMtList);

        spinnerTotalRoadDistance_Length.setAdapter(spinnerFtMtdapter);
        spinnerTotalRoadDistance_Breadth.setAdapter(spinnerFtMtdapter);
        // spinnernTotalPathRoadDistance_Breadth.setAdapter(spinnerFtMtdapter);
        spinnernTotalNaliDistance_Length.setAdapter(spinnerFtMtdapter);
        spinnernTotalNali_Breadth.setAdapter(spinnerFtMtdapter);
        spinnernTotalNali_Gharai.setAdapter(spinnerFtMtdapter);
        spinnernMittiKrya.setAdapter(spinnerAreaFtMtdapter);



        txtTotalRoadDistance_Length=(EditText)findViewById(R.id.txtTotalRoadDistance_Length);
        txtTotalRoadDistance_Breadth=(EditText)findViewById(R.id.txtTotalRoadDistance_Breadth);
        txtTotalRoadDistance_Fat=(EditText)findViewById(R.id.txtTotalRoadDistance_Fat);
        //txtTotalPathRoadDistance_Breadth=(EditText)findViewById(R.id.txtTotalPathRoadDistance_Breadth);
        txtTotalNaliDistance_Length=(EditText)findViewById(R.id.txtTotalNaliDistance_Length);
        txtTotalNali_Breadth=(EditText)findViewById(R.id.txtTotalNali_Breadth);
        txtTotalNali_Gharai=(EditText)findViewById(R.id.txtTotalNali_Gharai);
        txtSokhtaKiSankhya=(EditText)findViewById(R.id.txtSokhtaKiSankhya);
        txtMittiKrya=(EditText)findViewById(R.id.txtMittiKrya);
        txtHugePipe=(EditText)findViewById(R.id.txtHugePipe);
        txtRemarks=(EditText)findViewById(R.id.txtRemarks);
        txtMBNumber=(EditText)findViewById(R.id.txtMBNumber);
        lin_yojnaCompleteddate=(LinearLayout) findViewById(R.id.lin_yojnaCompleteddate);

        ln_Inspection=(LinearLayout) findViewById(R.id. ln_Inspection);
        lnMBNumber=(LinearLayout) findViewById(R.id. lnMBNumber);


        txtDateOfCompletionOfProject=(EditText) findViewById(R.id.txtDateOfCompletionOfProject);

        txtInspectorName=(EditText) findViewById(R.id.txtInspectorName);
        txtInspectionDate=(TextView) findViewById(R.id.txtInspectionDate);
        txtInspectionDate.setText(Utiilties.getDateString());
        txtOtherYojana=(EditText) findViewById(R.id.txtOtherYojana);
        txtInspectorPost=(EditText) findViewById(R.id.txtInspectorPost);

        txtOtherYojana.setVisibility(View.GONE);


        btncal1=(ImageView) findViewById(R.id.btncal1);

        btncal1.setOnClickListener(this);
        ProjectCompleted=(CheckBox) findViewById(R.id.ProjectCompleted) ;

        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

        _vardistrictID= CommonPref.getUserDetails(GaliNaliActivity.this).get_DistCode();
        _varblockID=CommonPref.getUserDetails(GaliNaliActivity.this).get_BlockCode();
        _varpanchayatID=CommonPref.getUserDetails(GaliNaliActivity.this).get_PanchayatCode();
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

//        if (usertype.equals("PANADM")) {
//            ln_Inspection.setVisibility(View.GONE);
//        }
//        else
//        {
//            ln_Inspection.setVisibility(View.VISIBLE);
//        }

        if (usertype.equals("PANADM")) {
            if(work_type.equalsIgnoreCase("PI")) {
                ln_Inspection.setVisibility(View.VISIBLE);
            }
            else
            {
                ln_Inspection.setVisibility(View.GONE);
            }
        }
        else
        {
            ln_Inspection.setVisibility(View.VISIBLE);
        }

        arrayAdapterRoadType = new ArrayAdapter(this,android.R.layout.simple_spinner_item,RoadType);
        spinnerRoadType.setAdapter(arrayAdapterRoadType);
        spinnerFYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    _varFYearID = FyearList.get(arg2-1).getFyId();
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


//        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 > 0) {
//
//                    District dist = DistrictList.get(arg2 - 1);
//                    _vardistrictID = dist.get_DistCode();
//                    _vardistrictName=dist.get_DistName();
//                    setBlockSpinnerData();
//
//                } else {
//                    _vardistrictID = "0";
//                    _vardistrictName="NA";
//                    spBlock.setSelection(0);
//                    spPanchayat.setSelection(0);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
//        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                // TODO Auto-generated method stub
//                if (arg2 > 0) {
//                    Block blk = BlockList.get(arg2 - 1);
//                    _varblockID = blk.getBlockCode();
//                    _varblockName=blk.getBlockName();
//                    setPanchayatSpinnerData();
//                }
//                else if(arg2==0)
//                {
//                    _varblockID = "0";
//                    _varblockName="0";
//                    spPanchayat.setSelection(0);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
        spinnerPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    VillageData wrd = VillageList.get(arg2 - 1);
                    _varVillageID = wrd.getVillcode();
                }
                else if(arg2==0)
                {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    ward wrd = WardList.get(arg2 - 1);
                    _varwardID = wrd.getWardCode();
                    getYojanaSpinnerData();
                }
                else if(arg2==0)
                {
                    //spVillage.setSelection(0);
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


        spinneryojanatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    //YOJANA wrd = YojanaTypeList.get(arg2 - 1);
                    try {
                        YOJANA wrd = YojanaTypeList.get(arg2 - 1);
                        _varYojanaTypeID = wrd.get_YojanaID();
                        _varYojanaTypeNAME = wrd.get_YojanaName();
                    }
                    catch (Exception ex)
                    {
                        _varYojanaTypeID = "0";
                        _varYojanaTypeNAME = "Any";
                    }

                    if(_varYojanaTypeNAME.equalsIgnoreCase("अन्य"))
                    {
                        txtOtherYojana.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txtOtherYojana.setVisibility(View.GONE);
                        txtOtherYojana.setText("");
                    }
                }
                else
                {
                    _varYojanaTypeID = "0";
                    _varYojanaTypeNAME= "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerCurrentPhysical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    CurrentPhysical currentPhysical = CurrentphysicalList.get(arg2 - 1);
                    _varcurrentPhysical_Id = currentPhysical.getCurrentPhysical_Id();
                    _varcurrentPhysical_Name = currentPhysical.getCurrentPhysical_Name();

                    if(_varcurrentPhysical_Id.equalsIgnoreCase("3"))
                    {
                        lin_yojnaCompleteddate.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        lin_yojnaCompleteddate.setVisibility(View.GONE);
                    }


                } else {
                    _varcurrentPhysical_Id = "";
                    _varcurrentPhysical_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerRoadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _varRoadType=(String)spinnerRoadType.getItemAtPosition(position);
                if(_varRoadType.equalsIgnoreCase("Bricks Soling"))
                {
                    _varRoadType="1";
                }
                else  if(_varRoadType.equalsIgnoreCase("PCC"))
                {
                    _varRoadType="2";
                } else  if(_varRoadType.equalsIgnoreCase("Paver Block")){
                    _varRoadType="3";
                }
                //Bricks Soling+PCC
                else  if(_varRoadType.equalsIgnoreCase("Bricks Soling+PCC")){
                    _varRoadType="4";
                }
                else  if(_varRoadType.equalsIgnoreCase("Other")){
                    _varRoadType="5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrayAdapterPathRoadType = new ArrayAdapter(this,android.R.layout.simple_spinner_item,RoadType);
        //      spinnerPatriRoadType.setAdapter(arrayAdapterPathRoadType);

//        spinnerPatriRoadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                _varPathRoadType=(String)spinnerPatriRoadType.getItemAtPosition(position);
//                if(_varPathRoadType.equalsIgnoreCase("Bricks Soling"))
//                {
//                    _varPathRoadType="1";
//                }
//                else  if(_varPathRoadType.equalsIgnoreCase("PCC"))
//                {
//                    _varPathRoadType="2";
//                } else  if(_varPathRoadType.equalsIgnoreCase("Paver Block")){
//                    _varPathRoadType="3";
//                }
//                else  if(_varPathRoadType.equalsIgnoreCase("Bricks Soling+PCC")){
//                    _varPathRoadType="4";
//                }
//                else  if(_varPathRoadType.equalsIgnoreCase("Other")){
//                    _varPathRoadType="5";
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        arrayAdapterNaliType = new ArrayAdapter(this,android.R.layout.simple_spinner_item,NaliType);
        spinnerNaliType.setAdapter(arrayAdapterNaliType);

        spinnerNaliType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _varNaliType=(String)spinnerNaliType.getItemAtPosition(position);
                if(_varNaliType.equalsIgnoreCase("RCC"))
                {
                    _varNaliType="1";
                }
                else  if(_varNaliType.equalsIgnoreCase("Brick"))
                {
                    _varNaliType="2";
                } else  if(_varNaliType.equalsIgnoreCase("Hume Pipe")){
                    _varNaliType="3";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrayAdapterJalNikasi = new ArrayAdapter(this,android.R.layout.simple_spinner_item,JalNikasi);
        spinnerJalNikasi.setAdapter(arrayAdapterJalNikasi);

        spinnerJalNikasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _varJalNikasi=(String)spinnerJalNikasi.getItemAtPosition(position);
                if(_varJalNikasi.equalsIgnoreCase("हाँ"))
                {
                    _varJalNikasi="Y";
                }
                else
                {
                    _varJalNikasi="N";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ProjectCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isProjectCompleted="Y";

                }
                else{
                    // Do your coding
                    isProjectCompleted="N";
                    txtDateOfCompletionOfProject.setText("");
                }
            }
        });



        spinnerTotalRoadDistance_Length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTotalRoadDistance_LengthFtMt = FtMtList.get(arg2);

                } else {
                    _varTotalRoadDistance_LengthFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerTotalRoadDistance_Breadth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTotalRoadDistance_BreadthFtMt = FtMtList.get(arg2);

                } else {
                    _varTotalRoadDistance_BreadthFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

//        spinnernTotalPathRoadDistance_Breadth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 > 0) {
//
//                    _varTotalPathRoadDistance_BreadthFtMt = FtMtList.get(arg2);
//
//                } else {
//                    _varTotalPathRoadDistance_BreadthFtMt = "0";
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

        spinnernTotalNaliDistance_Length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTotalNaliDistance_LengthFtMt = FtMtList.get(arg2);

                } else {
                    _varTotalNaliDistance_LengthFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernTotalNali_Breadth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTotalNali_BreadthFtMt = FtMtList.get(arg2);

                } else {
                    _varTotalNali_BreadthFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernTotalNali_Gharai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTotalNali_GharaiFtMt = FtMtList.get(arg2);

                } else {
                    _varTotalNali_GharaiFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernMittiKrya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varMittiKryaFtMt = AreaFtMtList.get(arg2);

                } else {
                    _varMittiKryaFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        lnMBNumber.setVisibility(View.GONE);
        arrayAdapterMPStatus = new ArrayAdapter(this,android.R.layout.simple_spinner_item,MPStatus);
        spinnerMBCompleted.setAdapter(arrayAdapterMPStatus);
        spinnerMBCompleted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _varMPStatus=(String)spinnerMBCompleted.getItemAtPosition(position);
                if(_varMPStatus.equalsIgnoreCase("हाँ"))
                {
                    _varMPStatus="Y";
                    lnMBNumber.setVisibility(View.VISIBLE);
                }
                else
                {
                    _varMPStatus="N";
                    lnMBNumber.setVisibility(View.GONE);
                    txtMBNumber.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(getIntent().hasExtra("NTYPE")) {
            _varNischayTypeID = getIntent().getStringExtra("NTYPE");
        }

        if(getIntent().hasExtra("EDIT"))
        {
            ids = getIntent().getStringExtra("ID");
            if (ids == null) {
                ids = "0";
            }
            ShowData("EDIT");
        }
        else if(getIntent().hasExtra("VIEW"))
        {
            ids = getIntent().getStringExtra("ID");
            if (ids == null) {
                ids = "0";
            }
            ShowData("VIEW");
        }
        else
        {
            //getWardSpinnerData(_vardistrictID,_varblockID,_varpanchayatID,_varwardID);
            if(!_varblockID.equalsIgnoreCase("0")) {
                getWardSpinnerData(_vardistrictID, _varblockID, _varpanchayatID, _varwardID);
            }
            getSchemeSpinnerData();
        }

        getPanchayatSpinnerData(_varblockID);
        getVillageSpinnerData(_varpanchayatID);
        loadSpeedCurrentPhysical();
        loadFYear();

        //getYojanaSpinnerData();


        spinnerPanchayat.setEnabled(false);

    }

//    public void EnableDisableSpinner(boolean b)
//    {
//        spDistrict.setEnabled(b);
//        spBlock.setEnabled(b);
//        spPanchayat.setEnabled(b);
//    }

//    public void loadDistrictSpinnerdata() {
//
//        DistrictList = localDBHelper.getDistrict();
//        String[] divNameArray = new String[DistrictList.size() + 1];
//        divNameArray[0] = "-जिला चुनें-";
//        int i = 1;
//        int setID=0;
//        for (District dist : DistrictList) {
//
//            divNameArray[i] = dist.get_DistName();
//            if(_vardistrictID.equalsIgnoreCase(DistrictList.get(i-1).get_DistCode()))
//            {
//                setID=i;
//            }
//            i++;
//        }
//        districtadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
//        districtadapter.setDropDownViewResource(R.layout.dropdownlist);
//        spDistrict.setAdapter(districtadapter);
//
//        spDistrict.setSelection(setID);
//    }
//    public void setBlockSpinnerData()
//    {
//        DataBaseHelper placeData = new DataBaseHelper(GaliNaliActivity.this);
//        BlockList=placeData.getBlock(_vardistrictID);
//
//        if(BlockList.size()>0) loadBlockSpinnerData(BlockList);
//    }
//
//    private void loadBlockSpinnerData(ArrayList<Block> bList)
//    {
//        BlocktStringList=new ArrayList<String>();
//
//        BlocktStringList.add("-प्रखंड चुनें-");
//        int setID=0;
//        for(int i=0;i<bList.size();i++)
//        {
//            BlocktStringList.add(bList.get(i).getBlockName());
//            if(_varblockID.equalsIgnoreCase(bList.get(i).getBlockCode()))
//            {
//                setID=i+1;
//            }
//        }
//        blockadapter=new ArrayAdapter(this,R.layout.dropdownlist,BlocktStringList);
//        spBlock.setAdapter(blockadapter);
//        spBlock.setSelection(setID);
//    }
//    public void setPanchayatSpinnerData()
//    {
//        DataBaseHelper placeData = new DataBaseHelper(GaliNaliActivity.this);
//        PanchayatList=placeData.getPanchayatLocal(_varblockID);
//        if(PanchayatList.size()>0 ) loadPanchayatSpinnerData(PanchayatList);
//    }
//    private void loadPanchayatSpinnerData(ArrayList<PanchayatData> pList)
//    {
//        ArrayList<String> PanchayatStringList=new ArrayList<String>();
//        PanchayatStringList.add("-पंचायत चुनें-");
//        int setID=0;
//        for(int i=0;i<pList.size();i++)
//        {
//            PanchayatStringList.add(pList.get(i).getPname());
//            if(_varpanchayatID.equalsIgnoreCase(pList.get(i).getPcode()))
//            {
//                setID=i+1;
//            }
//        }
//        Panchayatadapter=new ArrayAdapter(this,R.layout.dropdownlist,PanchayatStringList);
//        spPanchayat.setAdapter(Panchayatadapter);
//        spPanchayat.setSelection(setID);
//    }

    @Override
    public void onClick(View view) {
        if (view.equals(btncal1)) {
            date1=true;
            ProjectCompleted.setChecked(true);
        }

        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        // return new DatePickerDialog(this, datePickerListener, year, month, day);

        DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        // dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            //---taking mm/dd/yyyyy
            String ds=(selectedMonth + 1) + "/" + selectedDay  +  "/"+ selectedYear;
            //String ds=  selectedDay+ "/"+ (selectedMonth + 1)  +  "/"+ selectedYear;
            ds=ds.replace("/","-");
            if(date1)
            {
                txtDateOfCompletionOfProject.setText(ds);
                Log.e("DateOfReqMadeByWIMC", ds);
            }

        }
    };
    private void getPanchayatSpinnerData(String blkid)
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        PanchayatList=localDBHelper.getPanchayatLocal(blkid);
        if(PanchayatList.size()>0 ) {
            loadPanchayatSpinnerData();
        }
    }
    private void loadPanchayatSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<PanchayatList.size();i++)
        {
            StringList.add(PanchayatList.get(i).getPname());
            if(_varpanchayatID.equalsIgnoreCase(PanchayatList.get(i).getPcode()))
            {
                setWID=""+ (i+1);
            }
        }
        Panchayatadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerPanchayat.setAdapter(Panchayatadapter);

        spinnerPanchayat.setSelection(Integer.parseInt(setWID));

    }
    private void getVillageSpinnerData(String panid)
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        VillageList=localDBHelper.getVillageLocal(panid);
        if(WardList.size()>0 ) {
            loadVillageSpinnerData(VillageList);
        }
        else
        {
            //
        }
    }
    private void loadVillageSpinnerData(ArrayList<VillageData> pList)
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<pList.size();i++)
        {
            StringList.add(pList.get(i).getVillname());
            if(_varVillageID.equalsIgnoreCase(pList.get(i).getVillcode()))
            {
                setWID=""+ (i+1);
            }
        }
        villageadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerVillage.setAdapter(villageadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerVillage.setSelection(Integer.parseInt(setWID));
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerVillage.setSelection(Integer.parseInt(setWID));
        }
    }
    private void getWardSpinnerData(String distid,String blkid,String panid,String setWID)
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        WardList=localDBHelper.getWardLocal(panid);
        if(WardList.size()>0 ) {
            loadWardSpinnerData(WardList);
        }
        else
        {
            new GaliNaliActivity.LoadWARDData(distid,blkid,panid).execute("");
        }
    }
    private void loadWardSpinnerData(ArrayList<ward> pList)
    {
        //https://stackoverflow.com/questions/9611220/how-do-you-set-the-spinner-text-color
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<pList.size();i++)
        {
            StringList.add(pList.get(i).getWardname()+" ("+getYojanaCount(pList.get(i).getWardCode())+")");
            if(_varwardID.equalsIgnoreCase(pList.get(i).getWardCode()))
            {
                setWID=""+ (i+1);
            }
        }
        wardadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);

        spinnerWard.setAdapter(wardadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerWard.setSelection(Integer.parseInt(setWID));
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerWard.setSelection(Integer.parseInt(setWID));
        }
    }
    private class LoadWARDData extends AsyncTask<String, String, ArrayList<ward>> {
        String distCode="";
        String blockCode="";
        String panCode="";

        private final ProgressDialog dialog = new ProgressDialog(
                GaliNaliActivity.this);

        LoadWARDData(String distCode,String blockCode,String panCode) {
            this.distCode = distCode;
            this.blockCode=blockCode;
            this.panCode = panCode;
        }

        @Override
        protected void onPreExecute() {
            pd.setMessage("Loading Wards...");
            pd.show();
        }

        @Override
        protected ArrayList<ward> doInBackground(String... param) {

            WardList = WebServiceHelper.loadWardList(panCode);
            return WardList;
        }

        @Override
        protected void onPostExecute(ArrayList<ward> result) {
            if (pd.isShowing()) {
                pd.dismiss();
            }

            if(result!=null)
            {
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(GaliNaliActivity.this);
                    long i=placeData.setWardLocal(result);
                    if(i>0)
                    {
                        loadLocalWardINSpinnerdata();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Ward loading failed", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void loadLocalWardINSpinnerdata()//yes-no
    {

        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        WardList=localDBHelper.getWardLocal( _varpanchayatID);
        if(WardList.size()>0 ) {
            loadWardSpinnerData(WardList);
        }
    }

    public String getYojanaCount(String wcode)
    {
        String yc="0";
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        YojanaTypeList=localDBHelper.getYojanaList(_varNischayTypeID,_varpanchayatID,wcode);

        yc=String.valueOf(YojanaTypeList.size());

        return yc;
    }
    private void getSchemeSpinnerData()
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        NischayTypeList=localDBHelper.getSchemeList();
        if(NischayTypeList.size()>0 ) {
            loadSchemeSpinnerData();
        }
    }
    private void loadSchemeSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
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

    private void getYojanaSpinnerData()
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        YojanaTypeList=localDBHelper.getYojanaList(_varNischayTypeID,_varpanchayatID,_varwardID);
        if(YojanaTypeList.size()>0 ) {
            loadYojanaSpinnerData();
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(GaliNaliActivity.this);
            ab.setIcon(R.drawable.logo);
            ab.setCancelable(false);
            ab.setTitle("योजना के प्रकार");
            ab.setMessage("क्षमा करें, पंचायत कोड: " + _varpanchayatID + "\nऔर निश्चय के प्रकार: " + _varNischayTypeID +"\nऔर वार्ड आईडी: "+ _varwardID+"\nके लिए कोई योजना के प्रकार की सूची नहीं मिली |\nआप भौतिक सत्यापन आगे नहीं कर सकते हैं");
            ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
        }
    }

    private void loadYojanaSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        setYojanaTypeID="0";
        for(int i=0;i<YojanaTypeList.size();i++)
        {
            StringList.add(YojanaTypeList.get(i).get_YojanaName());
            if(_varYojanaTypeID.equalsIgnoreCase(YojanaTypeList.get(i).get_YojanaID()))
            {
                setYojanaTypeID=""+ (i+1);
            }
        }

        // StringList.add("अन्य");

        YojanaTypeadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
//        YojanaTypeadapter=new ArrayAdapter(this,R.layout.spinner_item,StringList);
//        YojanaTypeadapter.setDropDownViewResource(R.layout.spinner_item);
        spinneryojanatype.setAdapter(YojanaTypeadapter);
        if(getIntent().hasExtra("EDIT"))
        {
            spinneryojanatype.setSelection(Integer.parseInt(setYojanaTypeID));
        }
        if(getIntent().hasExtra("VIEW"))
        {
            setYojanaTypeID="0";
            for(int i=0;i<YojanaTypeList.size();i++)
            {
                StringList.add(YojanaTypeList.get(i).get_YojanaName());
                if(_varYojanaTypeNAME.equalsIgnoreCase(YojanaTypeList.get(i).get_YojanaName().trim()))
                {
                    setYojanaTypeID=""+ (i+1);
                }
            }
            spinneryojanatype.setSelection(Integer.parseInt(setYojanaTypeID));
        }

    }
    public  void loadSpeedCurrentPhysical()
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        CurrentphysicalList=localDBHelper.getCurrentPhysicalList();
        if(CurrentphysicalList.size()>0 ) {
            loadCurrentPhysicalSpinnerData();
        }
    }


    private void loadCurrentPhysicalSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        int setID=0;
        for(int i=0;i<CurrentphysicalList.size();i++)
        {
            StringList.add(CurrentphysicalList.get(i).getCurrentPhysical_Name());
            if(_varcurrentPhysical_Id.equalsIgnoreCase(CurrentphysicalList.get(i).getCurrentPhysical_Id()))
            {
                setID=(i+1);
            }
        }
        physicaladapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerCurrentPhysical.setAdapter(physicaladapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerCurrentPhysical.setSelection(setID);
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerCurrentPhysical.setSelection(setID);
        }
    }
    public  void loadFYear()
    {
        localDBHelper = new DataBaseHelper(GaliNaliActivity.this);
        FyearList=localDBHelper.getFYear();
        if(FyearList.size()>0 ) {
            loadFYearSpinnerData();
        }
    }

    private void loadFYearSpinnerData()
    {
        int y=Utiilties.getYearFromDate(new Date());
        int m=Utiilties.getMonthFromDate(new Date());

        Log.e("new Date()",""+new Date());

        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        int setID=0;
        for(int i=0;i<FyearList.size();i++)
        {
            if(m<=3) {
                String[] yl=FyearList.get(i).getFy().split(" ");
                int yll=Integer.parseInt(yl[1]);
                if (yll<y) {
                    StringList.add(FyearList.get(i).getFy());
                }
            }
            else
            {
                String[] yl=FyearList.get(i).getFy().split(" ");
                int yll=Integer.parseInt(yl[1]);
                if (yll<(y +1)) {
                    StringList.add(FyearList.get(i).getFy());
                }
            }

            if(_varFYearID.equalsIgnoreCase(FyearList.get(i).getFyId()))
            {
                setID=(i+1);
            }
        }
        fyearadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerFYear.setAdapter(fyearadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerFYear.setSelection(setID);
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerFYear.setSelection(setID);
        }
    }
    private void loadFYearSpinnerDataOLD()
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

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerFYear.setSelection(setID);
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerFYear.setSelection(setID);
        }
    }
    public void onClick_SaveContinue(View view)
    {

        if(btnsave.getText().toString().equalsIgnoreCase("फ़ोटो देखें"))
        {
            finish();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
            Intent i = new Intent(GaliNaliActivity.this, MultiplePhotoNewActivity.class);
            i.putExtra("VIEW", "VIEW");
            i.putExtra("UserID", uid);
            i.putExtra("ID", ids);
            i.putExtra("NTYPE", "2");
            startActivity(i);
        }
        else {
            if (validateRecordBeforeSaving_p1().equalsIgnoreCase("yes")) {
                SQLiteDatabase db = localDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("FYear", _varFYearID);
                values.put("PanCode", _varpanchayatID);
                values.put("WardCode", _varwardID);
                values.put("WardName", _varwardName);

                values.put("NischayCode", _varNischayTypeID);
                values.put("YojanaCode", _varYojanaTypeID);
                values.put("CurrentPhysicalStatus", _varcurrentPhysical_Id);


                values.put("RoadType", _varRoadType);
                values.put("PathRoadType", _varRoadType);
                values.put("NaliType", _varNaliType);
                values.put("JalNikasi", _varJalNikasi);
                values.put("TotalRoadDistance_Length", txtTotalRoadDistance_Length.getText().toString().trim());
                values.put("TotalRoadDistance_Breadth", txtTotalRoadDistance_Breadth.getText().toString().trim());
                values.put("TotalRoadDistance_Fat", txtTotalRoadDistance_Fat.getText().toString().trim());
                values.put("TotalPathRoadDistance_Breadth", txtTotalRoadDistance_Breadth.getText().toString().trim());//----
                values.put("TotalNaliDistance_Length", txtTotalNaliDistance_Length.getText().toString().trim());
                values.put("TotalNali_Breadth", txtTotalNali_Breadth.getText().toString().trim());
                values.put("TotalNali_Gharai", txtTotalNali_Gharai.getText().toString().trim());
                values.put("SokhtaKiSankhya", txtSokhtaKiSankhya.getText().toString().trim());
                values.put("MittiKrya", txtMittiKrya.getText().toString().trim());
                values.put("HugePipe", txtHugePipe.getText().toString().trim());
                values.put("ProjectCompleteionDate", txtDateOfCompletionOfProject.getText().toString().trim());
                values.put("Remarks", txtRemarks.getText().toString().trim());
                values.put("CreatedDate", Utiilties.getDateString());
                values.put("CreatedBy", uid);
                if (!usertype.equals("PANADM")) {
                    values.put("InspectorName", txtInspectorName.getText().toString().trim());
                    values.put("InspectorPost", txtInspectorPost.getText().toString().trim());
                    values.put("OtherYojanaName", txtOtherYojana.getText().toString().trim());

                    values.put("extraColumn1", _vardistrictID.trim());  //Dist Code
                    values.put("extraColumn2", _varblockID.trim());  //Block Code
                    values.put("PanCode", _varpanchayatID.trim());
                    values.put("WardCode", _varwardID.trim());
                    values.put("VillageCode", _varVillageID.trim());
                }
                else if (usertype.equals("PANADM")) {
                    if(work_type.equalsIgnoreCase("PI")) {
                        values.put("InspectorName", txtInspectorName.getText().toString().trim());
                        values.put("InspectorPost", txtInspectorPost.getText().toString().trim());
                        values.put("OtherYojanaName", txtOtherYojana.getText().toString().trim());

                        values.put("extraColumn1", _vardistrictID.trim());  //Dist Code
                        values.put("extraColumn2", _varblockID.trim());  //Block Code
                        values.put("PanCode", _varpanchayatID.trim());
                        values.put("WardCode", _varwardID.trim());
                        values.put("VillageCode", _varVillageID.trim());
                    }
                    else if(work_type.equalsIgnoreCase("PV")) {
                        values.put("VillageCode", _varVillageID.trim());
                    }
                }


                values.put("TotalRoadDistance_LengthFtMt", _varTotalRoadDistance_LengthFtMt);
                values.put("TotalRoadDistance_BreadthFtMt", _varTotalRoadDistance_BreadthFtMt);
                values.put("TotalPathRoadDistance_BreadthFtMt", _varTotalPathRoadDistance_BreadthFtMt);
                values.put("TotalNaliDistance_LengthFtMt", _varTotalNaliDistance_LengthFtMt);
                values.put("TotalNali_BreadthFtMt", _varTotalNali_BreadthFtMt);
                values.put("TotalNali_GharaiFtMt", _varTotalNali_GharaiFtMt);
                values.put("MittiKryaFtMt", _varMittiKryaFtMt);

                values.put("extraColumn3", _varMPStatus);
                values.put("extraColumn4", txtMBNumber.getText().toString());

                String[] whereArgs = new String[]{String.valueOf(ids)};
                long c = db.update("GaliNali", values, "id=?", whereArgs);
                String isdone = "";
                if (c > 0) {
                    Toast.makeText(this, "रिकॉर्ड अपडेट हो गया ", Toast.LENGTH_SHORT).show();
                    isdone = "रिकॉर्ड अपडेट हो गया ";
                    c = Long.parseLong(ids);
                    wantToEdit="yes";
                }
                if (c <= 0) {

                    c = db.insert("GaliNali", null, values);
                    if(c>0)
                    {
                        wantToEdit="no";
                        isdone = "रिकॉर्ड  सेव हो गया";
                    }
                }
                if (c > 0) {
                    Toast.makeText(this, isdone, Toast.LENGTH_SHORT).show();

                    Button btnsave = (Button) findViewById(R.id.btnsave);
                    //btnsave.setEnabled(false);
                    final String id = String.valueOf(c);
                    AlertDialog.Builder ab = new AlertDialog.Builder(GaliNaliActivity.this);
                    ab.setTitle("रिकॉर्ड");
                    ab.setCancelable(false);
                    ab.setMessage(isdone);
                    ab.setPositiveButton("[ फोटो ले ]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "2").commit();
                            Intent i = new Intent(GaliNaliActivity.this, MultiplePhotoNewActivity.class);
                            i.putExtra("EDIT", wantToEdit);
                            i.putExtra("UserID", uid);
                            i.putExtra("ID", id);
                            i.putExtra("NTYPE", "2");
                            startActivity(i);
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();
                }
                db.close();
            }
        }
    }
    public void OnClick_goToHomeScreen(View v)
    {
        finish();
        // startActivity(new Intent(GaliNaliActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(GaliNaliActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
    }
    public void ShowData(String dataFor)
    {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor=null;
        if(dataFor.equalsIgnoreCase("EDIT")) {
            cursor = db
                    .rawQuery(
                            "SELECT * FROM GaliNali where id=?",
                            new String[]{String.valueOf(ids)});
        }
        else if(dataFor.equalsIgnoreCase("VIEW")) {
            Toast.makeText(this, "--REPORT---", Toast.LENGTH_SHORT).show();
            cursor = db
                    .rawQuery(
                            "SELECT * FROM ReportGaliNali where id=?",
                            new String[]{String.valueOf(ids)});
        }

        if (cursor.moveToNext()) {

            if (!usertype.equals("PANADM")) {
                if(dataFor.equalsIgnoreCase("EDIT")) {
                    txtInspectorName.setText(cursor.getString(cursor.getColumnIndex("InspectorName")));
                    txtInspectorPost.setText(cursor.getString(cursor.getColumnIndex("InspectorPost")));
                }
                else
                {
                    txtInspectorName.setText(cursor.getString(cursor.getColumnIndex("InspectorName")));
                    txtInspectorPost.setText(cursor.getString(cursor.getColumnIndex("InspectorPost")));
                }
            }
            else if (usertype.equals("PANADM")) {
                if(work_type.equalsIgnoreCase("PI")) {
                    if (dataFor.equalsIgnoreCase("EDIT")) {
                        txtInspectorName.setText(cursor.getString(cursor.getColumnIndex("InspectorName")));
                        txtInspectorPost.setText(cursor.getString(cursor.getColumnIndex("InspectorPost")));
                    } else {
                        txtInspectorName.setText(cursor.getString(cursor.getColumnIndex("InspectorName")));
                        txtInspectorPost.setText(cursor.getString(cursor.getColumnIndex("InspectorPost")));
                    }
                }
            }
            _varFYearID=cursor.getString(cursor.getColumnIndex("FYear"));
            _varpanchayatID=cursor.getString(cursor.getColumnIndex("PanCode"));
            _varwardID=cursor.getString(cursor.getColumnIndex("WardCode"));
            _varNischayTypeID="2";          //cursor.getString(cursor.getColumnIndex("NischayCode"));
            _varYojanaTypeID=cursor.getString(cursor.getColumnIndex("YojanaCode"));

            if(getIntent().hasExtra("EDIT"))
            {
                _varYojanaTypeID=cursor.getString(cursor.getColumnIndex("YojanaCode"));
            }
            if(getIntent().hasExtra("VIEW"))
            {
                _varYojanaTypeNAME=cursor.getString(cursor.getColumnIndex("SchemeName"));
            }

            getWardSpinnerData(_vardistrictID,_varblockID,_varpanchayatID,_varwardID);


            _varVillageID=cursor.getString(cursor.getColumnIndex("VillageCode"));
            getVillageSpinnerData(_varpanchayatID);

            getSchemeSpinnerData();
            getYojanaSpinnerData();
            if (!usertype.equals("PANADM")) {
                if (getIntent().hasExtra("EDIT")) {
                    if (_varYojanaTypeNAME.length()<=0 && _varYojanaTypeID.equalsIgnoreCase("0")) {
                        txtOtherYojana.setVisibility(View.VISIBLE);
                        txtOtherYojana.setText(cursor.getString(cursor.getColumnIndex("OtherYojanaName")));
                    }
                }
            }

            try{
                _varcurrentPhysical_Id=cursor.getString(cursor.getColumnIndex("CurrentPhysicalStatus"));


                if(_varcurrentPhysical_Id.equalsIgnoreCase("3") )
                {
                    lin_yojnaCompleteddate.setVisibility(View.VISIBLE);
                    txtDateOfCompletionOfProject.setText(cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate")));
                    if(cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate")).trim().length()>=8)
                    {
                        ProjectCompleted.setChecked(true);
                    }
                }
                else
                {
                    lin_yojnaCompleteddate.setVisibility(View.GONE);
                    ProjectCompleted.setChecked(false);
                }
            }catch (Exception e){
                Toast.makeText(this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }



            loadCurrentPhysicalSpinnerData();


            _varRoadType=cursor.getString(cursor.getColumnIndex("RoadType"));
//            if(_varRoadType.equalsIgnoreCase("11"))
//            {
//                _varRoadType="5";
//            }
            spinnerRoadType.setSelection(Integer.parseInt(_varRoadType));

//            _varPathRoadType=cursor.getString(cursor.getColumnIndex("PathRoadType"));
//            if(_varPathRoadType.equalsIgnoreCase("11"))
//            {
//                _varPathRoadType="5";
//            }
            //spinnerPatriRoadType.setSelection(Integer.parseInt(_varPathRoadType));

            _varNaliType=cursor.getString(cursor.getColumnIndex("NaliType"));
            spinnerNaliType.setSelection(Integer.parseInt(_varNaliType));


            _varJalNikasi=cursor.getString(cursor.getColumnIndex("JalNikasi"));
            if(_varJalNikasi.equalsIgnoreCase("Y")) {
                spinnerJalNikasi.setSelection(1);
            }
            else
            {
                spinnerJalNikasi.setSelection(2);
            }


            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varTotalRoadDistance_LengthFtMt=cursor.getString(cursor.getColumnIndex("TotalRoadDistance_LengthFtMt"));
                if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_LengthFtMt")).equalsIgnoreCase("फीट"))
                {
                    spinnerTotalRoadDistance_Length.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_LengthFtMt")).equalsIgnoreCase("मीटर"))
                {
                    spinnerTotalRoadDistance_Length.setSelection(2);
                }
            }
            else
            {
                spinnerTotalRoadDistance_Length.setSelection(2);
            }


            txtTotalRoadDistance_Length.setText(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Length")));

            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varTotalRoadDistance_BreadthFtMt = cursor.getString(cursor.getColumnIndex("TotalRoadDistance_BreadthFtMt"));
                if (cursor.getString(cursor.getColumnIndex("TotalRoadDistance_BreadthFtMt")).equalsIgnoreCase("फीट")) {
                    spinnerTotalRoadDistance_Breadth.setSelection(1);
                }
                if (cursor.getString(cursor.getColumnIndex("TotalRoadDistance_BreadthFtMt")).equalsIgnoreCase("मीटर")) {
                    spinnerTotalRoadDistance_Breadth.setSelection(2);
                }
            }
            else
            {
                spinnerTotalRoadDistance_Breadth.setSelection(2);
            }
            txtTotalRoadDistance_Breadth.setText(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Breadth")));


            txtTotalRoadDistance_Fat.setText(cursor.getString(cursor.getColumnIndex("TotalRoadDistance_Fat")));

//            if(dataFor.equalsIgnoreCase("EDIT")) {
//                _varTotalPathRoadDistance_BreadthFtMt = cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_BreadthFtMt"));
//                if (cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_BreadthFtMt")).equalsIgnoreCase("फीट")) {
//                    spinnernTotalPathRoadDistance_Breadth.setSelection(1);
//                }
//                if (cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_BreadthFtMt")).equalsIgnoreCase("मीटर")) {
//                    spinnernTotalPathRoadDistance_Breadth.setSelection(2);
//                }
//            }
//            else
//            {
//                spinnernTotalPathRoadDistance_Breadth.setSelection(2);
//            }
//            txtTotalPathRoadDistance_Breadth.setText(cursor.getString(cursor.getColumnIndex("TotalPathRoadDistance_Breadth")));

            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varTotalNaliDistance_LengthFtMt = cursor.getString(cursor.getColumnIndex("TotalNaliDistance_LengthFtMt"));
                if (cursor.getString(cursor.getColumnIndex("TotalNaliDistance_LengthFtMt")).equalsIgnoreCase("फीट")) {
                    spinnernTotalNaliDistance_Length.setSelection(1);
                }
                if (cursor.getString(cursor.getColumnIndex("TotalNaliDistance_LengthFtMt")).equalsIgnoreCase("मीटर")) {
                    spinnernTotalNaliDistance_Length.setSelection(2);
                }
            }
            else {
                spinnernTotalNaliDistance_Length.setSelection(2);
            }
            txtTotalNaliDistance_Length.setText(cursor.getString(cursor.getColumnIndex("TotalNaliDistance_Length")));

            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varTotalNali_BreadthFtMt = cursor.getString(cursor.getColumnIndex("TotalNali_BreadthFtMt"));
                if (cursor.getString(cursor.getColumnIndex("TotalNali_BreadthFtMt")).equalsIgnoreCase("फीट")) {
                    spinnernTotalNali_Breadth.setSelection(1);
                }
                if (cursor.getString(cursor.getColumnIndex("TotalNali_BreadthFtMt")).equalsIgnoreCase("मीटर")) {
                    spinnernTotalNali_Breadth.setSelection(2);
                }
            }
            else
            {
                spinnernTotalNali_Breadth.setSelection(2);
            }
            txtTotalNali_Breadth.setText(cursor.getString(cursor.getColumnIndex("TotalNali_Breadth")));

            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varTotalNali_GharaiFtMt = cursor.getString(cursor.getColumnIndex("TotalNali_GharaiFtMt"));
                if (cursor.getString(cursor.getColumnIndex("TotalNali_GharaiFtMt")).equalsIgnoreCase("फीट")) {
                    spinnernTotalNali_Gharai.setSelection(1);
                }
                if (cursor.getString(cursor.getColumnIndex("TotalNali_GharaiFtMt")).equalsIgnoreCase("मीटर")) {
                    spinnernTotalNali_Gharai.setSelection(2);
                }
            }
            else
            {
                spinnernTotalNali_Gharai.setSelection(2);
            }
            txtTotalNali_Gharai.setText(cursor.getString(cursor.getColumnIndex("TotalNali_Gharai")));

            txtSokhtaKiSankhya.setText(cursor.getString(cursor.getColumnIndex("SokhtaKiSankhya")));
            if(dataFor.equalsIgnoreCase("EDIT")) {
                _varMittiKryaFtMt = cursor.getString(cursor.getColumnIndex("MittiKryaFtMt"));
                if (cursor.getString(cursor.getColumnIndex("MittiKryaFtMt")).equalsIgnoreCase("घन फीट")) {
                    spinnernMittiKrya.setSelection(1);
                }
                if (cursor.getString(cursor.getColumnIndex("MittiKryaFtMt")).equalsIgnoreCase("घन मीटर")) {
                    spinnernMittiKrya.setSelection(2);
                }
            }
            else
            {
                spinnernMittiKrya.setSelection(2);
            }
            txtMittiKrya.setText(cursor.getString(cursor.getColumnIndex("MittiKrya")));
            txtHugePipe.setText(cursor.getString(cursor.getColumnIndex("HugePipe")));
            txtRemarks.setText(cursor.getString(cursor.getColumnIndex("Remarks")));


            _varMPStatus=cursor.getString(cursor.getColumnIndex("extraColumn3"));
            if(_varMPStatus.equalsIgnoreCase("Y")) {
                spinnerMBCompleted.setSelection(1);
            }
            else
            {
                spinnerMBCompleted.setSelection(2);
            }
            txtMBNumber.setText(cursor.getString(cursor.getColumnIndex("extraColumn4")));

        }
        db.close();
        cursor.close();

        if(getIntent().hasExtra("VIEW"))
        {
            disablecontrols();
            btnsave.setText("फ़ोटो देखें");

        }

    }
    public void disablecontrols()
    {
        spinnerFYear.setEnabled(false);
        spinnerPanchayat.setEnabled(false);
        spinnerWard.setEnabled(false);
        spinnerNischayType.setEnabled(false);
        spinneryojanatype.setEnabled(false);
        spinnerCurrentPhysical.setEnabled(false);


        spinnerRoadType.setEnabled(false);
        //spinnerPatriRoadType.setEnabled(false);
        spinnerNaliType.setEnabled(false);
        spinnerJalNikasi.setEnabled(false);

        txtTotalRoadDistance_Length.setEnabled(false);
        txtTotalRoadDistance_Breadth.setEnabled(false);
        txtTotalRoadDistance_Fat.setEnabled(false);
        //  txtTotalPathRoadDistance_Breadth.setEnabled(false);
        txtTotalNaliDistance_Length.setEnabled(false);
        txtTotalNali_Breadth.setEnabled(false);
        txtTotalNali_Gharai.setEnabled(false);
        txtSokhtaKiSankhya.setEnabled(false);
        txtMittiKrya.setEnabled(false);
        txtHugePipe.setEnabled(false);
        txtRemarks.setEnabled(false);
        txtDateOfCompletionOfProject.setEnabled(false);
        btncal1.setEnabled(false);

        ProjectCompleted.setEnabled(false);
    }
    private String validateRecordBeforeSaving_p1() {
        String isvalid = "yes";

        if (!usertype.equals("PANADM")) {

            if (txtInspectorName.getText().toString().trim().length() <= 0) {
                Toast.makeText(GaliNaliActivity.this, "अपना नाम दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if (txtInspectorPost.getText().toString().trim().length() <= 0) {
                Toast.makeText(GaliNaliActivity.this, "अपना नाम पद करें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if((spinnerFYear != null && spinnerFYear.getSelectedItem() !=null ))
        {
            if((String)spinnerFYear.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया वित्तीय वर्ष का चयन करें", Toast.LENGTH_LONG).show();

                spinnerFYear.requestFocus();
                return "no";
            }
        }

        if((spinnerPanchayat != null && spinnerPanchayat.getSelectedItem() !=null ))
        {
            if((String)spinnerPanchayat.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया पंचायत का चयन करें", Toast.LENGTH_LONG).show();

                spinnerPanchayat.requestFocus();
                return "no";
            }
        }
//        if(CommonPref.getUserDetails(GaliNaliActivity.this).get_Role().equalsIgnoreCase("PANADM"))
//        {
//            if(work_type.equalsIgnoreCase("PI")) {
//                if((spinnerVillage != null && spinnerVillage.getSelectedItem() !=null ))
//                {
//                    if((String)spinnerVillage.getSelectedItem()!="-चुनें-")
//                    {
//                        isvalid="yes";
//                    }else {
//                        Toast.makeText(GaliNaliActivity.this, "कृपया गांव का चयन करें", Toast.LENGTH_LONG).show();
//
//                        spinnerVillage.requestFocus();
//                        return "no";
//                    }
//                }
//            }
//        }
//        else
//        {
//            if((spinnerVillage != null && spinnerVillage.getSelectedItem() !=null ))
//            {
//                if((String)spinnerVillage.getSelectedItem()!="-चुनें-")
//                {
//                    isvalid="yes";
//                }else {
//                    Toast.makeText(GaliNaliActivity.this, "कृपया गांव का चयन करें", Toast.LENGTH_LONG).show();
//
//                    spinnerVillage.requestFocus();
//                    return "no";
//                }
//            }
//        }
        if((spinnerWard != null && spinnerWard.getSelectedItem() !=null ))
        {
            if((String)spinnerWard.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया वार्ड का चयन करें", Toast.LENGTH_LONG).show();

                spinnerWard.requestFocus();
                return "no";
            }
        }
        if((spinneryojanatype != null && spinneryojanatype.getSelectedItem() !=null ))
        {
            if((String)spinneryojanatype.getSelectedItem()!="-चुनें-")
            {
                if(_varYojanaTypeNAME.equalsIgnoreCase("अन्य"))
                {
                    if (txtOtherYojana.getText().toString().trim().length() <= 0) {
                        Toast.makeText(GaliNaliActivity.this, "कृपया अन्य योजना का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                    else
                    {
                        isvalid="yes";
                    }
                }
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया योजना के प्रकार का चयन करें", Toast.LENGTH_LONG).show();
                spinneryojanatype.requestFocus();
                return "no";
            }
        }
        if((spinnerCurrentPhysical != null && spinnerCurrentPhysical.getSelectedItem() !=null ))
        {
            if((String)spinnerCurrentPhysical.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";


            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया वर्तमान भौतिक स्थिति का चयन करें", Toast.LENGTH_LONG).show();

                spinnerCurrentPhysical.requestFocus();
                return "no";
            }
        }
        if((spinnerCurrentPhysical != null && spinnerCurrentPhysical.getSelectedItem() !=null )) {
            if ((String) spinnerCurrentPhysical.getSelectedItem() != "-चुनें-") {

            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया वर्तमान भौतिक स्थिति चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if((spinnerRoadType != null && spinnerRoadType.getSelectedItem() !=null )) {
            if ((String) spinnerRoadType.getSelectedItem() != "-चुनें-") {

            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया पथ का प्रकार चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if((spinnerTotalRoadDistance_Length != null && spinnerTotalRoadDistance_Length.getSelectedItem() !=null ))
        {
            if((String)spinnerTotalRoadDistance_Length.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया पथ की लंबाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                spinnerTotalRoadDistance_Length.requestFocus();
                return "no";
            }
        }

        if (txtTotalRoadDistance_Length.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया पथ की कुल लंबाई दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }

        if((spinnerTotalRoadDistance_Breadth != null && spinnerTotalRoadDistance_Breadth.getSelectedItem() !=null ))
        {
            if((String)spinnerTotalRoadDistance_Breadth.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया पथ की चौड़ाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                spinnerTotalRoadDistance_Breadth.requestFocus();
                return "no";
            }
        }
        if (txtTotalRoadDistance_Breadth.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया पथ की कुल चौड़ाई दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }


        if (txtTotalRoadDistance_Fat.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया पथ की कुल मोटाइ(inch में) दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }
//        if((spinnerPatriRoadType != null && spinnerPatriRoadType.getSelectedItem() !=null )) {
//            if ((String) spinnerPatriRoadType.getSelectedItem() != "-चुनें-") {
//
//            }else {
//                Toast.makeText(GaliNaliActivity.this, "कृपया पथ पटरी का प्रकार  चुनें", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }

//        if((spinnernTotalPathRoadDistance_Breadth != null && spinnernTotalPathRoadDistance_Breadth.getSelectedItem() !=null ))
//        {
//            if((String)spinnernTotalPathRoadDistance_Breadth.getSelectedItem()!="-चुनें-")
//            {
//                isvalid="yes";
//            }else {
//                Toast.makeText(GaliNaliActivity.this, "कृपया पथ की मोटाइ - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();
//
//                spinnernTotalPathRoadDistance_Breadth.requestFocus();
//                return "no";
//            }
//        }
//        if (txtTotalPathRoadDistance_Breadth.getText().toString().trim().length() <= 0) {
//            Toast.makeText(GaliNaliActivity.this, "कृपयापथ पटरी की कुल चौड़ाई(मीटर में) दर्ज करें", Toast.LENGTH_LONG).show();
//            return "no";
//        }
        if((spinnerNaliType != null && spinnerNaliType.getSelectedItem() !=null )) {
            if ((String) spinnerNaliType.getSelectedItem() != "-चुनें-") {

            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया नाली का प्रकार चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if((spinnernTotalNaliDistance_Length != null && spinnernTotalNaliDistance_Length.getSelectedItem() !=null ))
        {
            if((String)spinnernTotalNaliDistance_Length.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया नाली की लंबाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                spinnernTotalNaliDistance_Length.requestFocus();
                return "no";
            }
        }
        if (txtTotalNaliDistance_Length.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया नाली की कुल लंबाई दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }

        if((spinnernTotalNali_Breadth != null && spinnernTotalNali_Breadth.getSelectedItem() !=null ))
        {
            if((String)spinnernTotalNali_Breadth.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया नाली की चौड़ाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                spinnernTotalNali_Breadth.requestFocus();
                return "no";
            }
        }
        if (txtTotalNali_Breadth.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया नाली की कुल चौड़ाई दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }

        if((spinnernTotalNali_Gharai != null && spinnernTotalNali_Gharai.getSelectedItem() !=null ))
        {
            if((String)spinnernTotalNali_Gharai.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया नाली की गहराई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                spinnernTotalNali_Gharai.requestFocus();
                return "no";
            }
        }
        if (txtTotalNali_Gharai.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया नाली की गहराई दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }
        if (txtSokhtaKiSankhya.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया शोखता की संख्या(नंबर में) दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }

        if((spinnernMittiKrya != null && spinnernMittiKrya.getSelectedItem() !=null )) {
            if ((String) spinnernMittiKrya.getSelectedItem() != "-चुनें-") {

            }else {
                Toast.makeText(GaliNaliActivity.this, "मिट्टी कार्य- घन मीटर / घन फीट चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if (txtMittiKrya.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया मिट्टी कार्य दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }

        if((spinnerJalNikasi != null && spinnerJalNikasi.getSelectedItem() !=null )) {
            if ((String) spinnerJalNikasi.getSelectedItem() != "-चुनें-") {

            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया जल निकासी की सुविधाए चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if (txtHugePipe.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया विशाल पाइप की संख्या दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }


        if(_varcurrentPhysical_Id.equalsIgnoreCase("3"))
        {
            lin_yojnaCompleteddate.setVisibility(View.VISIBLE);

            // if(isProjectCompleted.equalsIgnoreCase("Y"))
            // {
            if (txtDateOfCompletionOfProject.getText().toString().trim().length() <= 0) {
                Toast.makeText(GaliNaliActivity.this, "परियोजना के पूरा होने की तारीख दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if (txtDateOfCompletionOfProject.getText().toString().trim().length() > 0) {
                if (!txtDateOfCompletionOfProject.getText().toString().contains("-")) {
                    Toast.makeText(GaliNaliActivity.this, "परियोजना के पूरा होने की तारीख - अमान्य तिथि प्रारूप", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
        }


        if((spinnerMBCompleted != null && spinnerMBCompleted.getSelectedItem() !=null ))
        {
            if((String)spinnerMBCompleted.getSelectedItem()!="-चुनें-")
            {
                if((String)spinnerMBCompleted.getSelectedItem()=="हाँ")
                {
                    if (txtMBNumber.getText().toString().trim().length() <= 0) {
                        Toast.makeText(GaliNaliActivity.this, "कृपया M B नंबर दर्ज करें।", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
            }else {
                Toast.makeText(GaliNaliActivity.this, "कृपया M B पूर्ण हुआ या नहीं का चयन करें", Toast.LENGTH_LONG).show();

                spinnerMBCompleted.requestFocus();
                return "no";
            }
        }

        if (txtRemarks.getText().toString().trim().length() <= 0) {
            Toast.makeText(GaliNaliActivity.this, "कृपया अभियुक्ति दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }


        return isvalid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"Back press is not allowed, To view/update previous page info login again.",Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}
