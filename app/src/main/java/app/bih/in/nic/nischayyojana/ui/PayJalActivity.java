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
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.CurrentPhysical;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.VillageData;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.Utiilties;


public class PayJalActivity extends AppCompatActivity implements View.OnClickListener{

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB
/*
    जिला ब्लॉक पंचायत निश्चय प्रकार
*/
    EditText txtOtherPanchayat,txtOtherVillage,txtOtherWard,txtOtherYojana,txtMBNumber;
    //EditText txtOtherMotorPumpCapacity;
    DataBaseHelper localDBHelper;
    String wantToEdit="no";
    ImageView img_home,btncal1;
    ProgressDialog pd;
    String isBoringStarted="N",isMotorPumpStatus="N",isStaggingDone="N",isVitranPranali="N",
            isElectrictyconnection="N",isGrihJalSunyojan="N",isProjectCompleted="N";

    CheckBox BoringStarted,MotorPumpStatus,StaggingDone,
            VitranPranali,ElectrictyConnection,
            ProjectCompleted,GrihJalSanshadhan;
    String ids,uid;
    Button btnsave ;
    EditText txtBoringGahrai,txtUPVCPipeCMLNum,txtStageheight,txtTankiVolume,txtNoOFTanki,
            txtVitranPipeLength,txtDepthFromLandSurface,
            txtCMLNumber,txtNumberOfHouse,txtElectricityConsumerNo,
            txtDateOfCompletionOfProject,txtRemarks;

    TextView txtheader;
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
    Spinner spinnerFYear, spinnerPanchayat,spinnerWard,spinnerNischayType,spinneryojanatype,spinnerVillage,spinnerIot;
    Spinner spinnerStaggingType,spinnerTankiCapacity,spinnerTankiQuantity;
    Spinner spinnerVidyutConnection,spinnerwaterSupplyStatus,spinnerJalStumbh,spinnerPipeType,spinnerFherulUse;
    Spinner spinnerCurrentPhysical,spinnerISIMarkBoring,spinnerISIMarkMotorPump,spinnerISIMarkVitranPranali,spinnerMotorPumpCapacity;
    String _varIsWaterComing="0",isISIMarkBoring="N",isISIMarkWaterPump="N",isISIMarkVitranPranali="N",isWorkingOrNot="0";
    String _varMotorPumpCapacity="0",_varStaggingType="0",_varTankiCapacity="0",_varTankiQuantity="0";

    String _varIsVidyutConnection="N",_varJalStumbh="N",_varPipeType="0",_varFherulUse="N",_varMPStatus;

    ArrayList<PanchayatData> PanchayatList = new ArrayList<>();
    ArrayAdapter<String> Panchayatadapter;

    String _varpanchayatID="";

    ArrayList<VillageData> VillageList = new ArrayList<>();
    ArrayAdapter<String> villageadapter;
    String _varVillageName="",_varVillageID="0";

    ArrayList<ward> WardList = new ArrayList<>();
    ArrayAdapter<String> wardadapter;
    String _varwardName="",_varwardID="";

    ArrayList<SCHEME> NischayTypeList = new ArrayList<>();
    ArrayAdapter<String> NischayTypeadapter;
    String _varNischayTypeNAME="",_varNischayTypeID="",setNischayTypeID="0";

    ArrayList<YOJANA> YojanaTypeList = new ArrayList<>();
    ArrayAdapter<String> YojanaTypeadapter;
    String _varYojanaTypeNAME="",_varYojanaTypeID="",setYojanaTypeID="0";

    ArrayList<CurrentPhysical> CurrentphysicalList = new ArrayList<>();
    ArrayAdapter<String> physicaladapter;

    String _varcurrentPhysical_Name="",_varcurrentPhysical_Id="0";

    LinearLayout ll_BoringStatus,ll_motorpump,ll_StaggingStatus,
            ll_vitranPranaliKi,ll_VidyutSunyojan,
            ll_GrihJalSanshadhan,lin_yojnaCompleteddate;

    LinearLayout lnYojanaStatus,lnConsumerNumber;
    String _vardistrictID="0",_varblockID="0",Iot_Name="",Iot_id="";
    LinearLayout ln_Inspection;
    ArrayList<Fyear> FyearList = new ArrayList<>();
    ArrayAdapter<String> fyearadapter;
    String _varFYearID="0";
    ArrayAdapter arrayAdapterMPStatus;
    ArrayAdapter arrayAdapterIOT;
    String  MPStatus[]={"-चुनें-","हाँ","नहीं"};
    String Iot[]={"-चुनें-","IOT डिवाइस लगा हुआ है और क्रियाशील है |","IOT डिवाइस लगा हुआ है लेकिन अक्रियाशील है |","IOT डिवाइस नहीं लगा हुआ है |"};

    String[] YesNo = new String[]{
            "-चुनें-",
            "हाँ",
            "नहीं"
    };
    final List<String> YesNoList = new ArrayList<>(Arrays.asList(YesNo));
    ArrayAdapter<String> spinnerYesNoAdapter;
    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerMotorPumpCapacityAdapter;
    String[] MPCapacity = new String[]{
            "-चुनें-",
            "2 HP",
            "3 HP",
            "3 HP से अधिक"
    };
    final List<String> CapacityList = new ArrayList<>(Arrays.asList(MPCapacity));

    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerStaggingTypeAdapter;
    String[] StaggingType = new String[]{
            "-चुनें-",
            "RCC संरचना",
            "Steel संरचना"
    };
    final List<String> StaggingTypeList = new ArrayList<>(Arrays.asList(StaggingType));
    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerTankiCapacityAdapter;
    String[] TankiCapacity = new String[]{
            "-चुनें-",
            "5000",
            "10000"
    };
    final List<String> TankiCapacityList = new ArrayList<>(Arrays.asList(TankiCapacity));

    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerTankiQuantityAdapter;
    String[] TankiQuantity = new String[]{
            "-चुनें-",
            "1",
            "2"
    };
    final List<String> TankiQuantityList = new ArrayList<>(Arrays.asList(TankiQuantity));


    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerWaterSupplyStatusAdapter;
    String[] WaterSupplyStatus = new String[]{
            "-चुनें-",
            "चालू",
            "बन्द"
    };
    final List<String> WaterSupplyStatusList = new ArrayList<>(Arrays.asList(WaterSupplyStatus));

    // Initializing an ArrayAdapter
    ArrayAdapter<String> spinnerPipeTypeAdapter;
    String[] PipeType = new String[]{
            "-चुनें-",
            "MDPE",
            "CVPC",
            "GI",
            "अन्य"
    };


    Spinner spinnernBoringFtMt,spinnernStageFtMt,spinnernVitranFtMt,spinnernVitranDepthFtMt,spinnerMBCompleted;

    String _varBoringFtMt="0";
    String _varStageFtMt="0";
    String _varVitranFtMt="0";
    String _varVitranDepthFtMt="0";

    ArrayAdapter<String> spinnerFtMtdapter;
    String[] FtMt = new String[]{
            "-चुनें-",
            "फीट",
            "मीटर"
    };

    final List<String> FtMtList = new ArrayList<>(Arrays.asList(FtMt));

    final List<String> PipeTypeList = new ArrayList<>(Arrays.asList(PipeType));


    private Calendar cal;
    private int day;
    private int month;
    private int year;
    boolean date1=false;
    String usertype;
    EditText txtInspectorName;
    TextView txtInspectionDate;
    EditText txtInspectorPost;

    LinearLayout lnMBNumber;
    String work_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_jal);
        getSupportActionBar().hide();
        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _SchemeCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");

        work_type= PreferenceManager.getDefaultSharedPreferences(PayJalActivity.this).getString("WORK_TYPE", "");

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        usertype = CommonPref.getUserDetails(PayJalActivity.this).get_Role();

        init();


        spinnerFYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    _varFYearID = FyearList.get(arg2-1).getFyId();

                    if(!(getIntent().hasExtra("EDIT"))) {
                        spinnerNischayType.setSelection(0);
                        spinnerVillage.setSelection(0);
                        spinnerWard.setSelection(0);


                        txtOtherVillage.setVisibility(View.GONE);
                        txtOtherWard.setVisibility(View.GONE);
                        txtOtherYojana.setVisibility(View.GONE);
                        txtOtherVillage.setText("");
                        txtOtherWard.setText("");
                        txtOtherYojana.setText("");
                    }
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

        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    VillageData wrd = VillageList.get(arg2 - 1);
                    _varVillageID = wrd.getVillcode();
                    if(_varVillageID.equalsIgnoreCase("99999")){
                        txtOtherVillage.setVisibility(View.VISIBLE);
                        //txtOtherWard.setVisibility(View.VISIBLE);
                        //txtOtherYojana.setVisibility(View.VISIBLE);
                        if(!(getIntent().hasExtra("EDIT"))){
                            spinnerWard.setSelection(0);
                            spinnerNischayType.setSelection(0);
                            txtOtherWard.setText("");
                            txtOtherYojana.setText("");
                        }
                    }else{
                        if(!(getIntent().hasExtra("EDIT"))) {
                            txtOtherVillage.setVisibility(View.GONE);
                            txtOtherVillage.setText("");
                            txtOtherWard.setVisibility(View.GONE);
                            txtOtherYojana.setVisibility(View.GONE);
                            txtOtherWard.setText("");
                        }
                    }
                    if(!(getIntent().hasExtra("EDIT"))){
                        spinnerWard.setSelection(0);
                        spinnerNischayType.setSelection(0);
                    }

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
                    Log.e("_varwardID",_varwardID);
                    if(_varwardID.equalsIgnoreCase("99999")){
                        txtOtherWard.setVisibility(View.VISIBLE);
                        txtOtherYojana.setVisibility(View.VISIBLE);
                        spinneryojanatype.setSelection(0);
                        getYojanaSpinnerData();
                    }else{
                        getYojanaSpinnerData();
                        txtOtherWard.setVisibility(View.GONE);
                        //txtOtherYojana.setVisibility(View.GONE);
                        txtOtherWard.setText("");
                    }

                }
                else if(arg2==0)
                {
                    spinneryojanatype.setSelection(0);

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
        spinnerIot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    Iot_Name = Iot[arg2].toString();

                    if (Iot_Name.equals("IOT डिवाइस लगा हुआ है और क्रियाशील है |")) {

                        Iot_id = "1";
                    } else if (Iot_Name.equals("IOT डिवाइस लगा हुआ है लेकिन अक्रियाशील है |")) {

                        Iot_id = "2";
                    } else if (Iot_Name.equals("IOT डिवाइस नहीं लगा हुआ है |")) {

                        Iot_id = "3";

                    }
                }
                else
                {
                    Iot_id = "";
                    Iot_Name= "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        BoringStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isBoringStarted="Y";
                    ll_BoringStatus.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isBoringStarted="N";
                    ll_BoringStatus.setVisibility(View.GONE);
                    txtBoringGahrai.setText("");
                    spinnerISIMarkBoring.setSelection(0);
                    txtUPVCPipeCMLNum.setText("");


                }
            }
        });
        MotorPumpStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isMotorPumpStatus="Y";
                    ll_motorpump.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isMotorPumpStatus="N";
                    ll_motorpump.setVisibility(View.GONE);
                    spinnerISIMarkMotorPump.setSelection(0);
                    spinnerMotorPumpCapacity.setSelection(0);
                }
            }
        });
        StaggingDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isStaggingDone="Y";
                    ll_StaggingStatus.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isStaggingDone="N";
                    ll_StaggingStatus.setVisibility(View.GONE);
                    txtStageheight.setText("");
                    spinnerStaggingType.setSelection(0);
                    spinnerTankiCapacity.setSelection(0);
                    spinnerTankiQuantity.setSelection(0);
                }
            }
        });

        VitranPranali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isVitranPranali="Y";
                    ll_vitranPranaliKi.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isVitranPranali="N";
                    ll_vitranPranaliKi.setVisibility(View.GONE);
                    txtVitranPipeLength.setText("");
                    txtDepthFromLandSurface.setText("");
                    spinnerISIMarkVitranPranali.setSelection(0);
                    txtCMLNumber.setText("");
                }
            }
        });


        ElectrictyConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isElectrictyconnection="Y";
                    ll_VidyutSunyojan.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isElectrictyconnection="N";
                    ll_VidyutSunyojan.setVisibility(View.GONE);
                    txtElectricityConsumerNo.setText("");
                    spinnerVidyutConnection.setSelection(0);
                }
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
        GrihJalSanshadhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    isGrihJalSunyojan="Y";
                    ll_GrihJalSanshadhan.setVisibility(View.VISIBLE);
                }
                else{
                    // Do your coding
                    isGrihJalSunyojan="N";
                    txtNumberOfHouse.setText("");
                    spinnerwaterSupplyStatus.setSelection(0);
                    spinnerJalStumbh.setSelection(0);
                    spinnerPipeType.setSelection(0);
                    spinnerFherulUse.setSelection(0);
                    ll_GrihJalSanshadhan.setVisibility(View.GONE);
                }
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
                    if(_varcurrentPhysical_Id.equalsIgnoreCase("1") || _varcurrentPhysical_Id.equalsIgnoreCase("0")|| _varcurrentPhysical_Id.equalsIgnoreCase("4"))
                    {
                        lnYojanaStatus.setVisibility(View.GONE);
                        txtDateOfCompletionOfProject.setText("");
                        ProjectCompleted.setChecked(false);
                        isProjectCompleted="N";
                        setReamingDataNull();
                    }
                    else
                    {
                        lnYojanaStatus.setVisibility(View.VISIBLE);
                        lin_yojnaCompleteddate.setVisibility(View.VISIBLE);
                        ProjectCompleted.setChecked(true);
                        isProjectCompleted="Y";
                    }
                    if( _varcurrentPhysical_Id.equalsIgnoreCase("2"))
                    {
                        lin_yojnaCompleteddate.setVisibility(View.GONE);
                        txtDateOfCompletionOfProject.setText("");
                        ProjectCompleted.setChecked(false);
                        isProjectCompleted="N";
                    }
                } else {
                    _varcurrentPhysical_Id = "";
                    _varcurrentPhysical_Name = "";
                    txtDateOfCompletionOfProject.setText("");
                    ProjectCompleted.setChecked(false);
                    isProjectCompleted="N";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerISIMarkBoring.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    isISIMarkBoring = YesNoList.get(arg2);
                    if(isISIMarkBoring.equalsIgnoreCase("हाँ"))
                    {
                        isISIMarkBoring="Y";
                    }
                    else if(isISIMarkBoring.equalsIgnoreCase("नहीं"))
                    {
                        isISIMarkBoring="N";
                    }

                } else {
                    isISIMarkBoring = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerISIMarkMotorPump.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    isISIMarkWaterPump = YesNoList.get(arg2);
                    if(isISIMarkWaterPump.equalsIgnoreCase("हाँ"))
                    {
                        isISIMarkWaterPump="Y";
                    }
                    else if(isISIMarkWaterPump.equalsIgnoreCase("नहीं"))
                    {
                        isISIMarkWaterPump="N";
                    }

                } else {
                    isISIMarkWaterPump = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerISIMarkVitranPranali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {
                    isISIMarkVitranPranali = YesNoList.get(arg2);
                    if(isISIMarkVitranPranali.equalsIgnoreCase("हाँ"))
                    {
                        isISIMarkVitranPranali="Y";
                    }
                    else if(isISIMarkVitranPranali.equalsIgnoreCase("नहीं"))
                    {
                        isISIMarkVitranPranali="N";
                    }
                } else {
                    isISIMarkVitranPranali = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerMotorPumpCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varMotorPumpCapacity = CapacityList.get(arg2);
                    if(_varMotorPumpCapacity.equalsIgnoreCase("2 HP"))
                    {
                        _varMotorPumpCapacity="1";
                    }
                    else if(_varMotorPumpCapacity.equalsIgnoreCase("3 HP"))
                    {
                        _varMotorPumpCapacity="2";
                    }
                    if(_varMotorPumpCapacity.equalsIgnoreCase("3 HP से अधिक"))
                    {
                        _varMotorPumpCapacity="3";
                        // txtOtherMotorPumpCapacity.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        // txtOtherMotorPumpCapacity.setVisibility(View.GONE);
                        // txtOtherMotorPumpCapacity.setText("");
                    }
                } else {
                    _varMotorPumpCapacity = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerStaggingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varStaggingType = StaggingTypeList.get(arg2);
                    if(_varStaggingType.equalsIgnoreCase("RCC संरचना"))
                    {
                        _varStaggingType="1";
                    }
                    else if(_varStaggingType.equalsIgnoreCase("Steel संरचना"))
                    {
                        _varStaggingType="2";
                    }

                } else {
                    _varStaggingType = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerTankiCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTankiCapacity = TankiCapacityList.get(arg2);
                    if(_varTankiCapacity.equalsIgnoreCase("5000"))
                    {
                        _varTankiCapacity="1";
                    }
                    else if(_varTankiCapacity.equalsIgnoreCase("10000"))
                    {
                        _varTankiCapacity="2";
                    }

                } else {
                    _varTankiCapacity = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerTankiQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varTankiQuantity = TankiQuantityList.get(arg2);

                } else {
                    _varTankiQuantity = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerVidyutConnection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varIsVidyutConnection = YesNoList.get(arg2);
                    if(_varIsVidyutConnection.equalsIgnoreCase("हाँ"))
                    {
                        _varIsVidyutConnection="Y";
                        lnConsumerNumber.setVisibility(View.VISIBLE);
                    }
                    else if(_varIsVidyutConnection.equalsIgnoreCase("नहीं"))
                    {
                        _varIsVidyutConnection="N";
                        lnConsumerNumber.setVisibility(View.GONE);
                        txtElectricityConsumerNo.setText("");
                    }

                } else {
                    _varIsVidyutConnection = "0";
                    lnConsumerNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerwaterSupplyStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varIsWaterComing = WaterSupplyStatusList.get(arg2);
                    if(_varIsWaterComing.equalsIgnoreCase("चालू"))
                    {
                        _varIsWaterComing="1";
                    }
                    else if(_varIsWaterComing.equalsIgnoreCase("बन्द"))
                    {
                        _varIsWaterComing="2";
                    }
                } else {
                    _varIsWaterComing = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerJalStumbh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varJalStumbh = YesNoList.get(arg2);
                    if(_varJalStumbh.equalsIgnoreCase("हाँ"))
                    {
                        _varJalStumbh="Y";
                    }
                    else if(_varJalStumbh.equalsIgnoreCase("नहीं"))
                    {
                        _varJalStumbh="N";
                    }
                } else {
                    _varJalStumbh = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerPipeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varPipeType = PipeTypeList.get(arg2);
                    if(_varPipeType.equalsIgnoreCase("MDPE"))
                    {
                        _varPipeType="1";
                    }
                    else if(_varPipeType.equalsIgnoreCase("CVPC"))
                    {
                        _varPipeType="2";
                    }
                    else if(_varPipeType.equalsIgnoreCase("GI"))
                    {
                        _varPipeType="3";
                    }else if(_varPipeType.equalsIgnoreCase("अन्य"))
                    {
                        _varPipeType="4";
                    }
                } else {
                    _varPipeType = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerFherulUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varFherulUse = YesNoList.get(arg2);
                    if(_varFherulUse.equalsIgnoreCase("हाँ"))
                    {
                        _varFherulUse="Y";
                    }
                    else if(_varFherulUse.equalsIgnoreCase("नहीं"))
                    {
                        _varFherulUse="N";
                    }
                } else {
                    _varFherulUse = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernBoringFtMt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varBoringFtMt = FtMtList.get(arg2);

                } else {
                    _varBoringFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnernStageFtMt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varStageFtMt = FtMtList.get(arg2);

                } else {
                    _varStageFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnernVitranFtMt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varVitranFtMt = FtMtList.get(arg2);

                } else {
                    _varVitranFtMt = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernVitranDepthFtMt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    _varVitranDepthFtMt = FtMtList.get(arg2);

                } else {
                    _varVitranDepthFtMt = "0";
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
        _vardistrictID= CommonPref.getUserDetails(PayJalActivity.this).get_DistCode();
        _varblockID=CommonPref.getUserDetails(PayJalActivity.this).get_BlockCode();
        _varpanchayatID=CommonPref.getUserDetails(PayJalActivity.this).get_PanchayatCode();
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
            if(!_varblockID.equalsIgnoreCase("0")) {
                getWardSpinnerData(_vardistrictID, _varblockID, _varpanchayatID, _varwardID);
            }
            getSchemeSpinnerData();
        }
        getPanchayatSpinnerData(_varblockID);
        getVillageSpinnerData(_varpanchayatID);

        loadSpeedCurrentPhysical();
        loadFYear();
        loadIOT();
        //getYojanaSpinnerData();

        spinnerPanchayat.setEnabled(false);

//        if (usertype.equals("PANADM")) {
//
//            if(work_type.equalsIgnoreCase("PI")) {
//                if(getIntent().hasExtra("VIEW"))
//                {
//                    ln_Inspection.setVisibility(View.GONE);
//                }
//                else
//                {
//                    ln_Inspection.setVisibility(View.VISIBLE);
//                }
//            }
//            else
//            {
//                ln_Inspection.setVisibility(View.GONE);
//            }
//        }
//        else
//        {
//            if(getIntent().hasExtra("VIEW"))
//            {
//                ln_Inspection.setVisibility(View.GONE);
//            }
//            else
//            {
//                ln_Inspection.setVisibility(View.VISIBLE);
//            }
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
        //loadIOT();
    }

    public void setReamingDataNull()
    {
        BoringStarted.setChecked(false);
        isBoringStarted="N";
        ll_BoringStatus.setVisibility(View.GONE);
        spinnerISIMarkBoring.setSelection(0);
        txtBoringGahrai.setText("");
        txtUPVCPipeCMLNum.setText("");
        spinnernBoringFtMt.setSelection(0);

        MotorPumpStatus.setChecked(false);
        isMotorPumpStatus="N";
        ll_motorpump.setVisibility(View.GONE);
        spinnerMotorPumpCapacity.setSelection(0);
        spinnerISIMarkMotorPump.setSelection(0);


        StaggingDone.setChecked(false);
        isStaggingDone="N";
        ll_StaggingStatus.setVisibility(View.GONE);
        spinnerStaggingType.setSelection(0);
        txtStageheight.setText("");
        spinnerTankiCapacity.setSelection(0);
        spinnerTankiQuantity.setSelection(0);
        spinnernStageFtMt.setSelection(0);

        VitranPranali.setChecked(false);
        isVitranPranali="N";
        ll_vitranPranaliKi.setVisibility(View.GONE);
        txtVitranPipeLength.setText("");
        txtDepthFromLandSurface.setText("");
        spinnerISIMarkVitranPranali.setSelection(0);
        spinnernVitranFtMt.setSelection(0);
        spinnernVitranDepthFtMt.setSelection(0);
        txtCMLNumber.setText("");

        //----------------------
        ElectrictyConnection.setChecked(false);
        isElectrictyconnection="N";
        ll_VidyutSunyojan.setVisibility(View.GONE);
        spinnerVidyutConnection.setSelection(0);
        txtElectricityConsumerNo.setText("");



        GrihJalSanshadhan.setChecked(false);
        isGrihJalSunyojan="N";
        ll_GrihJalSanshadhan.setVisibility(View.GONE);
        txtNumberOfHouse.setText("");
        spinnerwaterSupplyStatus.setSelection(0);
        spinnerJalStumbh.setSelection(0);
        spinnerPipeType.setSelection(0);
        spinnerFherulUse.setSelection(0);

        ProjectCompleted.setChecked(false);
        isProjectCompleted="N";
        txtDateOfCompletionOfProject.setText("");
    }
    public  void init()
    {
        localDBHelper = new DataBaseHelper(this);

        TextView txtMainHeader=(TextView) findViewById(R.id.txtHeaderTop);

        if(!_SchemeName.equalsIgnoreCase("")) {
            txtMainHeader.setText(_SchemeName);
        }
        else
        {
            txtMainHeader.setText(R.string.headertext);
        }
        txtMainHeader.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtMainHeader.setSelected(true);
        txtMainHeader.setSingleLine(true);

        btnsave = (Button) findViewById(R.id.btnsave);
        img_home=(ImageView) findViewById(R.id.imghome);
        btncal1= findViewById(R.id.btncal1);
        btncal1.setOnClickListener(this);

        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _varNischayTypeID=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");

        spinnerVillage = findViewById(R.id.spinnerVillage) ;
        spinnerFYear = findViewById(R.id.spinnerFYear) ;
        spinnerPanchayat = findViewById(R.id.spinnerPanchayat) ;
        spinnerWard = findViewById(R.id.spinnerward) ;
        spinnerNischayType= findViewById(R.id.spinnernischaytype) ;
        spinneryojanatype= findViewById(R.id.spinneryojanatype) ;
        spinnerIot= findViewById(R.id.spinnerIot) ;

        spinnerISIMarkBoring= findViewById(R.id.spinnerISIMarkBoring) ;
        spinnerISIMarkMotorPump= findViewById(R.id.spinnerISIMarkMotorPump) ;
        spinnerISIMarkVitranPranali= findViewById(R.id.spinnerISIMarkVitranPranali) ;
        spinnerMotorPumpCapacity= findViewById(R.id.spinnerMotorPumpCapacity) ;
        spinnerStaggingType= findViewById(R.id.spinnerStaggingType) ;
        spinnerTankiCapacity= findViewById(R.id.spinnerTankiCapacity) ;
        spinnerTankiQuantity= findViewById(R.id.spinnerTankiQuantity) ;
        spinnerVidyutConnection= findViewById(R.id.spinnerVidyutConnection) ;
        spinnerwaterSupplyStatus= findViewById(R.id.spinnerwatercoming) ;
        spinnerJalStumbh= findViewById(R.id.spinnerJalStumbh) ;
        spinnerFherulUse= findViewById(R.id.spinnerFherulUse) ;
        spinnerPipeType= findViewById(R.id.spinnerPipeType) ;
        spinnerMBCompleted= findViewById(R.id.spinnerMBCompleted) ;

        spinnernBoringFtMt= findViewById(R.id.spinnernBoringFtMt) ;
        spinnernStageFtMt= findViewById(R.id.spinnernStageFtMt) ;
        spinnernVitranFtMt= findViewById(R.id.spinnernVitranFtMt) ;
        spinnernVitranDepthFtMt= findViewById(R.id.spinnernVitranDepthFtMt) ;


        ll_BoringStatus = (LinearLayout)findViewById(R.id.ll_BoringStatus);
        ll_motorpump = (LinearLayout)findViewById(R.id.ll_motorpump);
        ll_StaggingStatus = (LinearLayout)findViewById(R.id.ll_StaggingStatus);

        ll_vitranPranaliKi = (LinearLayout)findViewById(R.id.ll_vitranPranaliKi);
        ll_VidyutSunyojan = (LinearLayout)findViewById(R.id.ll_VidyutSunyojan);
        ll_GrihJalSanshadhan = (LinearLayout)findViewById(R.id.ll_GrihJalSanshadhan);
        lin_yojnaCompleteddate = (LinearLayout)findViewById(R.id.lin_yojnaCompleteddate);

        ln_Inspection = (LinearLayout)findViewById(R.id.ln_Inspection);
        lnMBNumber = (LinearLayout)findViewById(R.id.lnMBNumber);


        lnYojanaStatus = (LinearLayout)findViewById(R.id.lnYojanaStatus);
        lnConsumerNumber = (LinearLayout)findViewById(R.id.lnConsumerNumber);

        spinnerCurrentPhysical=(Spinner) findViewById(R.id.spinnernisCurrentPhysicalStatus);
        spinnerISIMarkVitranPranali=(Spinner) findViewById(R.id.spinnerISIMarkVitranPranali);

        txtBoringGahrai=(EditText) findViewById(R.id.txtBoringGahrai);
        txtUPVCPipeCMLNum=(EditText) findViewById(R.id.txtUPVCPipeCMLNum);

        txtStageheight=(EditText) findViewById(R.id.txtStageheight);
        //txtGunwatta=(EditText) findViewById(R.id.txtGunwatta);

        txtVitranPipeLength=(EditText) findViewById(R.id.txtTotalLength);
        //txtNoVitranQualityOfPipe=(EditText) findViewById(R.id.txtNoVitranQualityOfPipe);
        txtDepthFromLandSurface=(EditText) findViewById(R.id.txtDepthFromLandSurface);
        txtCMLNumber=(EditText) findViewById(R.id.txtCMLNumber);

        //txtHoseholdQuality=(EditText) findViewById(R.id.txtHoseholdQuality);
        txtElectricityConsumerNo=(EditText) findViewById(R.id.txtConsumerNumber);
        txtDateOfCompletionOfProject=(EditText) findViewById(R.id.txtDateOfCompletionOfProject);
        txtRemarks=(EditText) findViewById(R.id.txtRemarks);
        txtNumberOfHouse=(EditText) findViewById(R.id.txtNumberOfHouse);

        txtInspectorName=(EditText) findViewById(R.id.txtInspectorName);
        txtInspectorPost=(EditText) findViewById(R.id.txtInspectorPost);
        txtOtherPanchayat=(EditText) findViewById(R.id.txtOtherPanchayat);
        txtOtherVillage=(EditText) findViewById(R.id.txtOtherVillage);
        txtOtherWard=(EditText) findViewById(R.id.txtOtherWard);
        txtOtherYojana=(EditText) findViewById(R.id.txtOtherYojana);
        txtMBNumber=(EditText) findViewById(R.id.txtMBNumber);

        // txtOtherMotorPumpCapacity=(EditText) findViewById(R.id.txtOtherMotorPumpCapacity);
        //txtOtherMotorPumpCapacity.setVisibility(View.GONE);

        txtInspectionDate=(TextView) findViewById(R.id.txtInspectionDate);
        txtInspectionDate.setText(Utiilties.getDateString());

        txtOtherPanchayat.setVisibility(View.GONE);
        txtOtherVillage.setVisibility(View.GONE);
        txtOtherWard.setVisibility(View.GONE);
        txtOtherYojana.setVisibility(View.GONE);

        BoringStarted=(CheckBox) findViewById(R.id.chkBoringStarted);
        StaggingDone=(CheckBox) findViewById(R.id.chkStaggingDone);
        MotorPumpStatus=(CheckBox) findViewById(R.id.chkMotorPumpStatus);
        ElectrictyConnection=(CheckBox) findViewById(R.id.chkVidyutSunyojan);
        VitranPranali=(CheckBox) findViewById(R.id.chkVitranPranali);

        ProjectCompleted=(CheckBox) findViewById(R.id.ProjectCompleted);
        GrihJalSanshadhan=(CheckBox) findViewById(R.id.chkGrihJalSanshadhan);

        spinnerYesNoAdapter=new ArrayAdapter(this,R.layout.dropdownlist,YesNoList);
        spinnerMotorPumpCapacityAdapter=new ArrayAdapter(this,R.layout.dropdownlist,CapacityList);
        spinnerStaggingTypeAdapter=new ArrayAdapter(this,R.layout.dropdownlist,StaggingTypeList);
        spinnerTankiCapacityAdapter=new ArrayAdapter(this,R.layout.dropdownlist,TankiCapacityList);
        spinnerTankiQuantityAdapter=new ArrayAdapter(this,R.layout.dropdownlist,TankiQuantityList);
        spinnerWaterSupplyStatusAdapter=new ArrayAdapter(this,R.layout.dropdownlist,WaterSupplyStatusList);
        spinnerPipeTypeAdapter=new ArrayAdapter(this,R.layout.dropdownlist,PipeTypeList);
        spinnerFtMtdapter=new ArrayAdapter(this,R.layout.dropdownlist,FtMtList);

//        spinnerwatercoming.setAdapter(spinnerYesNoAdapter);
//        spinnersatisfactionofconsumer.setAdapter(spinnerYesNoAdapter);

        spinnerISIMarkBoring.setAdapter(spinnerYesNoAdapter);
        spinnerISIMarkMotorPump.setAdapter(spinnerYesNoAdapter);
        spinnerISIMarkVitranPranali.setAdapter(spinnerYesNoAdapter);
        spinnerMotorPumpCapacity.setAdapter(spinnerMotorPumpCapacityAdapter);
        spinnerStaggingType.setAdapter(spinnerStaggingTypeAdapter);
        spinnerVidyutConnection.setAdapter(spinnerYesNoAdapter);

        spinnerTankiCapacity.setAdapter(spinnerTankiCapacityAdapter);
        spinnerTankiQuantity.setAdapter(spinnerTankiQuantityAdapter);
        spinnerwaterSupplyStatus.setAdapter(spinnerWaterSupplyStatusAdapter);
        spinnerJalStumbh.setAdapter(spinnerYesNoAdapter);
        spinnerFherulUse.setAdapter(spinnerYesNoAdapter);
        spinnerPipeType.setAdapter(spinnerPipeTypeAdapter);

        spinnernBoringFtMt.setAdapter(spinnerFtMtdapter);
        spinnernStageFtMt.setAdapter(spinnerFtMtdapter);
        spinnernVitranFtMt.setAdapter(spinnerFtMtdapter);
        spinnernVitranDepthFtMt.setAdapter(spinnerFtMtdapter);


    }
    private void getPanchayatSpinnerData(String blkid)
    {
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
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
//        if(getIntent().hasExtra("EDIT"))
//        {
//            spinnerPanchayat.setSelection(Integer.parseInt(setWID));
//        }
    }

    private void getVillageSpinnerData(String panid)
    {
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
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
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
        WardList=localDBHelper.getWardLocal(panid);
        if(WardList.size()>0 ) {
            loadWardSpinnerData(WardList);
        }
        else
        {
            new LoadWARDData(distid,blkid,panid).execute("");
        }
    }
    private void loadWardSpinnerData(ArrayList<ward> pList)
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<pList.size();i++)
        {
           // StringList.add(pList.get(i).getWardname());
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
                PayJalActivity.this);

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

                    DataBaseHelper placeData = new DataBaseHelper(PayJalActivity.this);
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
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
        WardList=localDBHelper.getWardLocal( _varpanchayatID);
        if(WardList.size()>0 ) {
            loadWardSpinnerData(WardList);
        }
    }
    public String getYojanaCount(String wcode)
    {
        String yc="0";
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
        YojanaTypeList=localDBHelper.getYojanaList(_varNischayTypeID,_varpanchayatID,wcode);

        yc=String.valueOf(YojanaTypeList.size());

        return yc;
    }
    private void getSchemeSpinnerData()
    {
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
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
            spinnerNischayType.setSelection(Integer.parseInt(getIntent().getStringExtra("NTYPE").toString()));
        }
    }

    private void getYojanaSpinnerData()
    {
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
        YojanaTypeList=localDBHelper.getYojanaList(_varNischayTypeID,_varpanchayatID,_varwardID);
        Log.e("YojanaTypeList",""+YojanaTypeList.size());
        if(YojanaTypeList.size()>0 ) {
            loadYojanaSpinnerData();
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(PayJalActivity.this);
            ab.setIcon(R.drawable.logo);
            ab.setCancelable(false);
            ab.setTitle("योजना के प्रकार");
            ab.setMessage("क्षमा करें, पंचायत कोड: " + _varpanchayatID + "\nऔर निश्चय के प्रकार: " + _varNischayTypeID +"\nऔर वार्ड आईडी: "+ _varwardID+"\nके लिए कोई योजना के प्रकार की सूची नहीं मिली |");
            ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    loadYojanaSpinnerData();
                    loadCurrentPhysicalSpinnerData();
                    lnYojanaStatus.setVisibility(View.GONE);
                    setReamingDataNull();
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
            txtOtherYojana.setVisibility(View.VISIBLE);
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
        YojanaTypeadapter.notifyDataSetChanged();
    }

    //loadSpeedCurrentPhysical
    public  void loadSpeedCurrentPhysical()
    {
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
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
        localDBHelper = new DataBaseHelper(PayJalActivity.this);
        FyearList=localDBHelper.getFYear();
        if(FyearList.size()>0 ) {
            loadFYearSpinnerData();
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
    public void ShowData(String dataFor)
    {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor=null;
        if(dataFor.equalsIgnoreCase("EDIT")) {
            cursor = db
                    .rawQuery(
                            "SELECT * FROM PayJaltbl where id=?",
                            new String[]{String.valueOf(ids)});
        }
        else if(dataFor.equalsIgnoreCase("VIEW")) {
            Toast.makeText(this, "--REPORT---", Toast.LENGTH_SHORT).show();
            cursor = db
                    .rawQuery(
                            "SELECT * FROM ReportPayJaltbl where id=?",
                            new String[]{String.valueOf(ids)});
        }

        int x=cursor.getCount();
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
            else  if (usertype.equals("PANADM")) {
                if(work_type.equalsIgnoreCase("PI")) {
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
            }

            _varFYearID=cursor.getString(cursor.getColumnIndex("FYear"));
            _varpanchayatID=cursor.getString(cursor.getColumnIndex("PanCode"));
            _varwardID=cursor.getString(cursor.getColumnIndex("WardCode"));
            _varNischayTypeID="1";   // fixed for PAY Jal Yojana------cursor.getString(cursor.getColumnIndex("NischayCode"));

            if(getIntent().hasExtra("EDIT"))
            {
                _varYojanaTypeID=cursor.getString(cursor.getColumnIndex("YojanaCode"));

            }
            if(getIntent().hasExtra("VIEW"))
            {
                _varYojanaTypeNAME=cursor.getString(cursor.getColumnIndex("SchemeName"));
            }


            getWardSpinnerData(_vardistrictID,_varblockID,_varpanchayatID,_varwardID);

//            if (usertype.equals("PANADM")) {
//                if (work_type.equalsIgnoreCase("PI")) {
//                    _varVillageID=cursor.getString(cursor.getColumnIndex("VillageCode"));
//                   getVillageSpinnerData(_varpanchayatID);
//                }
//            }
//            else
//            {
//                _varVillageID=cursor.getString(cursor.getColumnIndex("VillageCode"));
//                getVillageSpinnerData(_varpanchayatID);
//            }


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
            else if (usertype.equals("PANADM")) {
                if(work_type.equalsIgnoreCase("PI")) {
                    if (getIntent().hasExtra("EDIT")) {
                        if (_varYojanaTypeNAME.length()<=0 && _varYojanaTypeID.equalsIgnoreCase("0")) {
                            txtOtherYojana.setVisibility(View.VISIBLE);
                            txtOtherYojana.setText(cursor.getString(cursor.getColumnIndex("OtherYojanaName")));
                        }
                    }
                }
            }

            //loadFYear();
            _varcurrentPhysical_Id=cursor.getString(cursor.getColumnIndex("CurrentPhysicalStatus"));
            if(_varcurrentPhysical_Id.equalsIgnoreCase("1") ||_varcurrentPhysical_Id.equalsIgnoreCase("0")||_varcurrentPhysical_Id.equalsIgnoreCase("4"))
            {
                lnYojanaStatus.setVisibility(View.GONE);
            }
            else
            {
                lnYojanaStatus.setVisibility(View.VISIBLE);
                lin_yojnaCompleteddate.setVisibility(View.VISIBLE);
            }
            if(_varcurrentPhysical_Id.equalsIgnoreCase("2") )
            {
                lin_yojnaCompleteddate.setVisibility(View.GONE);
            }

            loadCurrentPhysicalSpinnerData();
            String is_boring_started=cursor.getString(cursor.getColumnIndex("isBoringStarted"))!=null?cursor.getString(cursor.getColumnIndex("isBoringStarted")):"N";
            //if(cursor.getString(cursor.getColumnIndex("isBoringStarted")).equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("BoringDepth")).length()>0)
            if(is_boring_started.equalsIgnoreCase("Y"))
            {
                BoringStarted.setChecked(true);

                isBoringStarted=is_boring_started;
                ll_BoringStatus.setVisibility(View.VISIBLE);
                txtBoringGahrai.setText(cursor.getString(cursor.getColumnIndex("BoringDepth")));
                isISIMarkBoring=cursor.getString(cursor.getColumnIndex("ISIMarkBoring"));
                if(cursor.getString(cursor.getColumnIndex("ISIMarkBoring")).equalsIgnoreCase("Y"))
                {
                    spinnerISIMarkBoring.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("ISIMarkBoring")).equalsIgnoreCase("N"))
                {
                    spinnerISIMarkBoring.setSelection(2);
                }
                txtUPVCPipeCMLNum.setText(cursor.getString(cursor.getColumnIndex("UPVCPipeCMLNo")));

                if(dataFor.equalsIgnoreCase("EDIT")) {
                    _varBoringFtMt = cursor.getString(cursor.getColumnIndex("BoringFtMt"));
                    if (cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("फीट")) {
                        spinnernBoringFtMt.setSelection(1);
                    }
                    if (cursor.getString(cursor.getColumnIndex("BoringFtMt")).equalsIgnoreCase("मीटर")) {
                        spinnernBoringFtMt.setSelection(2);
                    }
                }
                else
                {
                    spinnernBoringFtMt.setSelection(2);
                }
            }
            else
            {
                BoringStarted.setChecked(false);
                isBoringStarted="N";
                ll_BoringStatus.setVisibility(View.GONE);
                _varBoringFtMt="0";
            }

            String is_Motor_Pump_Status=cursor.getString(cursor.getColumnIndex("isMotorPumpStatus"))!=null?cursor.getString(cursor.getColumnIndex("isMotorPumpStatus")):"N";
            // if(cursor.getString(cursor.getColumnIndex("isMotorPumpStatus")).equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("MotorPumpCapacity")).length()>0)
            if(is_Motor_Pump_Status.equalsIgnoreCase("Y") )
            {
                MotorPumpStatus.setChecked(true);
                isMotorPumpStatus=is_Motor_Pump_Status;
                ll_motorpump.setVisibility(View.VISIBLE);
                _varMotorPumpCapacity=cursor.getString(cursor.getColumnIndex("MotorPumpCapacity"));
                if(cursor.getString(cursor.getColumnIndex("MotorPumpCapacity")).equalsIgnoreCase("1"))
                {
                    spinnerMotorPumpCapacity.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("MotorPumpCapacity")).equalsIgnoreCase("2"))
                {
                    spinnerMotorPumpCapacity.setSelection(2);
                }
                if(cursor.getString(cursor.getColumnIndex("MotorPumpCapacity")).equalsIgnoreCase("3"))
                {
                    spinnerMotorPumpCapacity.setSelection(3);
                }
                isISIMarkWaterPump=cursor.getString(cursor.getColumnIndex("ISIMarkMotorPump"));


                if(cursor.getString(cursor.getColumnIndex("ISIMarkMotorPump")).equalsIgnoreCase("Y"))
                {
                    spinnerISIMarkMotorPump.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("ISIMarkMotorPump")).equalsIgnoreCase("N"))
                {
                    spinnerISIMarkMotorPump.setSelection(2);
                }
            }
            else
            {
                MotorPumpStatus.setChecked(false);
                isMotorPumpStatus="N";
                ll_motorpump.setVisibility(View.GONE);

            }

            String isStagging_Done=cursor.getString(cursor.getColumnIndex("isStaggingDone"))!=null?cursor.getString(cursor.getColumnIndex("isStaggingDone")):"N";
            // if(isStagging_Done.equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("TypeOfStagging")).length()>0)
            if(isStagging_Done.equalsIgnoreCase("Y") )
            {
                StaggingDone.setChecked(true);
                isStaggingDone=isStagging_Done;
                ll_StaggingStatus.setVisibility(View.VISIBLE);
                _varStaggingType=cursor.getString(cursor.getColumnIndex("TypeOfStagging"));
                if(cursor.getString(cursor.getColumnIndex("TypeOfStagging")).equalsIgnoreCase("1"))
                {
                    spinnerStaggingType.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("TypeOfStagging")).equalsIgnoreCase("2"))
                {
                    spinnerStaggingType.setSelection(2);
                }
                txtStageheight.setText(cursor.getString(cursor.getColumnIndex("HeightOfStagging")));
                _varTankiCapacity=cursor.getString(cursor.getColumnIndex("TenkiCapacity"));
                if(cursor.getString(cursor.getColumnIndex("TenkiCapacity")).equalsIgnoreCase("1"))
                {
                    spinnerTankiCapacity.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("TenkiCapacity")).equalsIgnoreCase("2"))
                {
                    spinnerTankiCapacity.setSelection(2);
                }

                _varTankiQuantity=cursor.getString(cursor.getColumnIndex("TenkiQuantity"));
                if(cursor.getString(cursor.getColumnIndex("TenkiQuantity")).equalsIgnoreCase("1"))
                {
                    spinnerTankiQuantity.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("TenkiQuantity")).equalsIgnoreCase("2"))
                {
                    spinnerTankiQuantity.setSelection(2);
                }

                if(dataFor.equalsIgnoreCase("EDIT")) {
                    _varStageFtMt = cursor.getString(cursor.getColumnIndex("StageFtMt"));
                    if (cursor.getString(cursor.getColumnIndex("StageFtMt")).equalsIgnoreCase("फीट")) {
                        spinnernStageFtMt.setSelection(1);
                    }
                    if (cursor.getString(cursor.getColumnIndex("StageFtMt")).equalsIgnoreCase("मीटर")) {
                        spinnernStageFtMt.setSelection(2);
                    }
                }
                else
                {
                    spinnernStageFtMt.setSelection(2);
                }

            }
            else
            {
                StaggingDone.setChecked(false);
                isStaggingDone="N";
                ll_StaggingStatus.setVisibility(View.GONE);
                _varStageFtMt="0";
            }

            String is_Vitran_Pranali=cursor.getString(cursor.getColumnIndex("isVitranPranali"))!=null?cursor.getString(cursor.getColumnIndex("isVitranPranali")):"N";
            //if(is_Vitran_Pranali.equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("TotalLength")).length()>0)
            if(is_Vitran_Pranali.equalsIgnoreCase("Y") )
            {
                VitranPranali.setChecked(true);
                isVitranPranali=is_Vitran_Pranali;
                ll_vitranPranaliKi.setVisibility(View.VISIBLE);
                txtVitranPipeLength.setText(cursor.getString(cursor.getColumnIndex("TotalLength")));
                txtDepthFromLandSurface.setText(cursor.getString(cursor.getColumnIndex("DepthFromSurfaceLevel")));

                isISIMarkVitranPranali=cursor.getString(cursor.getColumnIndex("ISIMarkVitranPranali"));
                if(cursor.getString(cursor.getColumnIndex("ISIMarkVitranPranali")).equalsIgnoreCase("Y"))
                {
                    spinnerISIMarkVitranPranali.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("ISIMarkVitranPranali")).equalsIgnoreCase("N"))
                {
                    spinnerISIMarkVitranPranali.setSelection(2);
                }

                txtCMLNumber.setText(cursor.getString(cursor.getColumnIndex("CMLNumber")));

                if(dataFor.equalsIgnoreCase("EDIT")) {
                    _varVitranFtMt = cursor.getString(cursor.getColumnIndex("VitranFtMt"));
                    if (cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("फीट")) {
                        spinnernVitranFtMt.setSelection(1);
                    }
                    if (cursor.getString(cursor.getColumnIndex("VitranFtMt")).equalsIgnoreCase("मीटर")) {
                        spinnernVitranFtMt.setSelection(2);
                    }
                }
                else
                {
                    spinnernVitranFtMt.setSelection(2);
                }
                if(dataFor.equalsIgnoreCase("EDIT")) {
                    _varVitranDepthFtMt = cursor.getString(cursor.getColumnIndex("VitranDepthFtMt"));
                    if (cursor.getString(cursor.getColumnIndex("VitranDepthFtMt")).equalsIgnoreCase("फीट")) {
                        spinnernVitranDepthFtMt.setSelection(1);
                    }
                    if (cursor.getString(cursor.getColumnIndex("VitranDepthFtMt")).equalsIgnoreCase("मीटर")) {
                        spinnernVitranDepthFtMt.setSelection(2);
                    }
                }
                else
                {
                    spinnernVitranDepthFtMt.setSelection(2);
                }
            }
            else
            {
                VitranPranali.setChecked(false);
                isVitranPranali="N";
                ll_vitranPranaliKi.setVisibility(View.GONE);
                _varVitranFtMt="0";
                _varVitranDepthFtMt="0";
            }

            String is_Electricty_Connection=cursor.getString(cursor.getColumnIndex("isElectrictyConnection"))!=null?cursor.getString(cursor.getColumnIndex("isElectrictyConnection")):"N";
            // if(is_Electricty_Connection.equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("isElectConnection")).length()>0)
            if(is_Electricty_Connection.equalsIgnoreCase("Y"))
            {
                ElectrictyConnection.setChecked(true);
                isElectrictyconnection=is_Electricty_Connection;
                ll_VidyutSunyojan.setVisibility(View.VISIBLE);
                _varIsVidyutConnection=cursor.getString(cursor.getColumnIndex("isElectConnection"));
                if(cursor.getString(cursor.getColumnIndex("isElectConnection")).equalsIgnoreCase("Y"))
                {
                    spinnerVidyutConnection.setSelection(1);
                    txtElectricityConsumerNo.setText(cursor.getString(cursor.getColumnIndex("ConsumerNumber")));
                }
                if(cursor.getString(cursor.getColumnIndex("isElectConnection")).equalsIgnoreCase("N"))
                {
                    spinnerVidyutConnection.setSelection(2);
                    txtElectricityConsumerNo.setText("");
                }

            }
            else
            {
                ElectrictyConnection.setChecked(false);
                isElectrictyconnection="N";
                ll_VidyutSunyojan.setVisibility(View.GONE);
            }

            String is_Grih_JalSunyojan=cursor.getString(cursor.getColumnIndex("isGrihJalSunyojan"))!=null?cursor.getString(cursor.getColumnIndex("isGrihJalSunyojan")):"N";
            //if(is_Grih_JalSunyojan.equalsIgnoreCase("Y") || cursor.getString(cursor.getColumnIndex("TotalHouseNum")).length()>0)
            if(is_Grih_JalSunyojan.equalsIgnoreCase("Y") )
            {
                // isGrihJalSunyojan="Y";
                isGrihJalSunyojan=is_Grih_JalSunyojan;
                GrihJalSanshadhan.setChecked(true);
                ll_GrihJalSanshadhan.setVisibility(View.VISIBLE);

                txtNumberOfHouse.setText(cursor.getString(cursor.getColumnIndex("TotalHouseNum")));
                _varIsWaterComing=cursor.getString(cursor.getColumnIndex("WaterSupplyStatus"));
                if(cursor.getString(cursor.getColumnIndex("WaterSupplyStatus")).equalsIgnoreCase("1"))
                {
                    spinnerwaterSupplyStatus.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("WaterSupplyStatus")).equalsIgnoreCase("2"))
                {
                    spinnerwaterSupplyStatus.setSelection(2);
                }
                _varJalStumbh=cursor.getString(cursor.getColumnIndex("JalStumbhKaNirman"));

                if(cursor.getString(cursor.getColumnIndex("JalStumbhKaNirman")).equalsIgnoreCase("Y"))
                {
                    spinnerJalStumbh.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("JalStumbhKaNirman")).equalsIgnoreCase("N"))
                {
                    spinnerJalStumbh.setSelection(2);
                }

                _varPipeType=cursor.getString(cursor.getColumnIndex("TypeOfPipe"));
                if(cursor.getString(cursor.getColumnIndex("TypeOfPipe")).equalsIgnoreCase("1"))
                {
                    spinnerPipeType.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("TypeOfPipe")).equalsIgnoreCase("2"))
                {
                    spinnerPipeType.setSelection(2);
                }
                if(cursor.getString(cursor.getColumnIndex("TypeOfPipe")).equalsIgnoreCase("3"))
                {
                    spinnerPipeType.setSelection(3);
                }

                _varFherulUse=cursor.getString(cursor.getColumnIndex("FerulKaUpyog"));
                if(cursor.getString(cursor.getColumnIndex("FerulKaUpyog")).equalsIgnoreCase("Y"))
                {
                    spinnerFherulUse.setSelection(1);
                }
                if(cursor.getString(cursor.getColumnIndex("FerulKaUpyog")).equalsIgnoreCase("N"))
                {
                    spinnerFherulUse.setSelection(2);
                }
            }
            else
            {
                GrihJalSanshadhan.setChecked(false);
                isGrihJalSunyojan="N";
                ll_GrihJalSanshadhan.setVisibility(View.GONE);
            }



            txtDateOfCompletionOfProject.setText(cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate")));
            isProjectCompleted=cursor.getString(cursor.getColumnIndex("isProjectCompleted"));

            if(isProjectCompleted.equalsIgnoreCase("Y"))
            {

            }
            if(cursor.getString(cursor.getColumnIndex("ProjectCompleteionDate")).trim().length()>=8)
            {
                ProjectCompleted.setChecked(true);
            }

            txtRemarks.setText(cursor.getString(cursor.getColumnIndex("Remarks")));

            _varMPStatus=cursor.getString(cursor.getColumnIndex("extraColumn3"));
            if(_varMPStatus != null){
                if(_varMPStatus.equalsIgnoreCase("Y")) {
                    spinnerMBCompleted.setSelection(1);
                }
                else
                {
                    spinnerMBCompleted.setSelection(2);
                }
                txtMBNumber.setText(cursor.getString(cursor.getColumnIndex("extraColumn4")));
            }else{
                //Toast.makeText(this, "MB Status null", Toast.LENGTH_SHORT).show();
            }
            String Iot_Name = cursor.getString(cursor.getColumnIndex("Iot_Name")) == null ? "" : cursor.getString(cursor.getColumnIndex("Iot_Name")).toString();
            spinnerIot.setSelection(((ArrayAdapter<String>) spinnerIot.getAdapter()).getPosition(Iot_Name));

            String VillageCode = cursor.getString(cursor.getColumnIndex("VillageCode")) == null ? "" : cursor.getString(cursor.getColumnIndex("VillageCode")).toString();
            if(VillageCode.equalsIgnoreCase("99999")){
                String OtherVillage = cursor.getString(cursor.getColumnIndex("OtherVillage")) == null ? "" : cursor.getString(cursor.getColumnIndex("OtherVillage")).toString();
                txtOtherVillage.setText(OtherVillage);
            }else{
                txtOtherVillage.setText("");
            }
            String WardCode = cursor.getString(cursor.getColumnIndex("WardCode")) == null ? "" : cursor.getString(cursor.getColumnIndex("WardCode")).toString();
            if(WardCode.equalsIgnoreCase("99999")){
                String OtherWard = cursor.getString(cursor.getColumnIndex("OtherWard")) == null ? "" : cursor.getString(cursor.getColumnIndex("OtherWard")).toString();
                txtOtherWard.setText(OtherWard);
            }else{
                txtOtherWard.setText("");
            }



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
        btncal1.setEnabled(false);

        spinnerFYear.setEnabled(false);
        spinnerPanchayat.setEnabled(false);
        spinnerVillage.setEnabled(false);
        spinnerWard.setEnabled(false);
        spinnerNischayType.setEnabled(false);
        spinneryojanatype.setEnabled(false);


        spinnerISIMarkBoring.setEnabled(false);
        spinnerISIMarkMotorPump.setEnabled(false);
        spinnerISIMarkVitranPranali.setEnabled(false);
        spinnerMotorPumpCapacity.setEnabled(false);
        spinnerStaggingType.setEnabled(false);
        spinnerTankiCapacity.setEnabled(false);
        spinnerTankiQuantity.setEnabled(false);
        spinnerVidyutConnection.setEnabled(false);
        spinnerwaterSupplyStatus.setEnabled(false);
        spinnerJalStumbh.setEnabled(false);
        spinnerFherulUse.setEnabled(false);
        spinnerPipeType.setEnabled(false);


        spinnerCurrentPhysical.setEnabled(false);
        spinnerISIMarkVitranPranali.setEnabled(false);

        txtBoringGahrai.setEnabled(false);
        txtUPVCPipeCMLNum.setEnabled(false);

        txtStageheight.setEnabled(false);
        txtVitranPipeLength.setEnabled(false);
        txtDepthFromLandSurface.setEnabled(false);
        txtCMLNumber.setEnabled(false);
        txtElectricityConsumerNo.setEnabled(false);
        txtDateOfCompletionOfProject.setEnabled(false);
        txtRemarks.setEnabled(false);
        txtNumberOfHouse.setEnabled(false);


        BoringStarted.setEnabled(false);
        StaggingDone.setEnabled(false);
        MotorPumpStatus.setEnabled(false);
        ElectrictyConnection.setEnabled(false);
        VitranPranali.setEnabled(false);
        ProjectCompleted.setEnabled(false);
        GrihJalSanshadhan.setEnabled(false);

    }

    public void OnClick_goToHomeScreen(View v)
    {
        finish();
        // startActivity(new Intent(PayJalActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(PayJalActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
    }


    @Override
    public void onClick(View view) {
        if (view.equals(btncal1)) {
            date1=true;
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
    public void onClick_SaveContinue(View view)
    {
        if(btnsave.getText().toString().equalsIgnoreCase("फ़ोटो देखें"))
        {
            finish();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "1").commit();
            Intent i = new Intent(PayJalActivity.this, MultiplePhotoNewActivity.class);
            i.putExtra("VIEW", "VIEW");
            i.putExtra("UserID", uid);
            i.putExtra("ID", ids);
            i.putExtra("NTYPE", "1");
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


                values.put("isBoringStarted", isBoringStarted);
                values.put("BoringDepth", txtBoringGahrai.getText().toString().trim());
                values.put("ISIMarkBoring", isISIMarkBoring);
                values.put("UPVCPipeCMLNo", txtUPVCPipeCMLNum.getText().toString().trim());


                values.put("isMotorPumpStatus", isMotorPumpStatus);
                values.put("ISIMarkMotorPump", isISIMarkWaterPump);
                values.put("MotorPumpCapacity", _varMotorPumpCapacity);


                values.put("isStaggingDone", isStaggingDone);
                values.put("TypeOfStagging", _varStaggingType);
                values.put("HeightOfStagging", txtStageheight.getText().toString().trim());
                values.put("TenkiCapacity", _varTankiCapacity);
                values.put("TenkiQuantity", _varTankiQuantity);

                values.put("isVitranPranali", isVitranPranali);
                values.put("TotalLength", txtVitranPipeLength.getText().toString().trim());
                values.put("DepthFromSurfaceLevel", txtDepthFromLandSurface.getText().toString().trim());
                values.put("ISIMarkVitranPranali", isISIMarkVitranPranali);
                values.put("CMLNumber", txtCMLNumber.getText().toString().trim());

                values.put("isElectrictyConnection", isElectrictyconnection);
                values.put("isElectConnection", _varIsVidyutConnection);
                values.put("ConsumerNumber", txtElectricityConsumerNo.getText().toString().trim());

                values.put("isGrihJalSunyojan", isGrihJalSunyojan);
                values.put("TotalHouseNum", txtNumberOfHouse.getText().toString().trim());
                values.put("WaterSupplyStatus", _varIsWaterComing);
                values.put("JalStumbhKaNirman", _varJalStumbh);
                values.put("TypeOfPipe", _varPipeType);
                values.put("FerulKaUpyog", _varFherulUse);

                values.put("isProjectCompleted", isProjectCompleted);
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
                    values.put("OtherWard", txtOtherWard.getText().toString().trim());
                    values.put("OtherVillage", txtOtherVillage.getText().toString().trim());
                } else  if (usertype.equals("PANADM")) {
                    if(work_type.equalsIgnoreCase("PI")) {
                        values.put("InspectorName", txtInspectorName.getText().toString().trim());
                        values.put("InspectorPost", txtInspectorPost.getText().toString().trim());
                        values.put("OtherWard", txtOtherWard.getText().toString().trim());
                        values.put("OtherVillage", txtOtherVillage.getText().toString().trim());
                        values.put("OtherYojanaName", txtOtherYojana.getText().toString().trim());

                        values.put("extraColumn1", _vardistrictID.trim());  //Dist Code
                        values.put("extraColumn2", _varblockID.trim());  //Block Code
                        values.put("PanCode", _varpanchayatID.trim());
                        values.put("WardCode", _varwardID.trim());
                        values.put("VillageCode", _varVillageID.trim());
                        values.put("Iot_id",Iot_id);
                        values.put("Iot_Name",Iot_Name);
                    } else if(work_type.equalsIgnoreCase("PV")) {
                        values.put("VillageCode", _varVillageID.trim());
                    }
                }

                values.put("BoringFtMt", _varBoringFtMt);
                values.put("StageFtMt", _varStageFtMt);
                values.put("VitranFtMt", _varVitranFtMt);
                values.put("VitranDepthFtMt", _varVitranDepthFtMt);

                values.put("extraColumn3", _varMPStatus);
                values.put("extraColumn4", txtMBNumber.getText().toString());

                String[] whereArgs = new String[]{String.valueOf(ids), uid};
                long c = db.update("PayJaltbl", values, "id=? AND CreatedBy=?", whereArgs);
                String isdone = "";
                if (c > 0) {
                    Toast.makeText(this, "रिकॉर्ड सेव हो गया ", Toast.LENGTH_SHORT).show();
                    c = Long.parseLong(ids);
                    wantToEdit="yes";
                    isdone = "रिकॉर्ड अपडेट हो गया ";
                }
                if (c <= 0) {
                    c = db.insert("PayJaltbl", null, values);
                    if(c>0)
                    {
                        wantToEdit="no";
                        isdone = "रिकॉर्ड  सेव हो गया";
                    }
                }
                if (c > 0) {
                    Toast.makeText(this, isdone, Toast.LENGTH_SHORT).show();


                    //  btnsave.setEnabled(false);

                    final String id = String.valueOf(c);

                    AlertDialog.Builder ab = new AlertDialog.Builder(PayJalActivity.this);
                    ab.setTitle("रिकॉर्ड");
                    ab.setMessage(isdone);
                    ab.setCancelable(false);
                    ab.setPositiveButton("[ फोटो ले ]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", "1").commit();
                            Intent i = new Intent(PayJalActivity.this, MultiplePhotoNewActivity.class);
                            i.putExtra("EDIT", wantToEdit);
                            i.putExtra("UserID", uid);
                            i.putExtra("ID", id);
                            i.putExtra("NTYPE", "1");
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

    private String validateRecordBeforeSaving_p1() {
        String isvalid = "yes";

        if (!usertype.equals("PANADM")) {

            if (txtInspectorName.getText().toString().trim().length() <= 0) {
                Toast.makeText(PayJalActivity.this, "अपना नाम दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if (txtInspectorPost.getText().toString().trim().length() <= 0) {
                Toast.makeText(PayJalActivity.this, "अपना नाम पद करें", Toast.LENGTH_LONG).show();
                return "no";
            }

        }
        if ((spinnerFYear != null && spinnerFYear.getSelectedItem() != null)) {
            if ((String) spinnerFYear.getSelectedItem() != "-चुनें-") {
                isvalid = "yes";
            } else {
                Toast.makeText(PayJalActivity.this, "कृपया वित्तीय वर्ष का चयन करें", Toast.LENGTH_LONG).show();

                spinnerFYear.requestFocus();
                return "no";
            }
        }
        if ((spinnerPanchayat != null && spinnerPanchayat.getSelectedItem() != null)) {
            if ((String) spinnerPanchayat.getSelectedItem() != "-चुनें-") {
                if (_varpanchayatID.equalsIgnoreCase("99999")) {
                    if (txtOtherPanchayat.getText().toString().trim().length() <= 0) {
                        Toast.makeText(PayJalActivity.this, "कृपया अन्य पंचायत का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    } else {
                        isvalid = "yes";
                    }
                }

            } else {
                Toast.makeText(PayJalActivity.this, "कृपया पंचायत का चयन करें", Toast.LENGTH_LONG).show();

                spinnerPanchayat.requestFocus();
                return "no";
            }
        }
        if ((spinnerVillage != null && spinnerVillage.getSelectedItem() != null)) {
            if ((String) spinnerVillage.getSelectedItem() != "-चुनें-") {
                if (_varVillageID.equalsIgnoreCase("99999")) {
                    if (txtOtherVillage.getText().toString().trim().length() <= 0) {
                        Toast.makeText(PayJalActivity.this, "कृपया अन्य गांव का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    } else {
                        isvalid = "yes";
                    }
                }

            } else {
                Toast.makeText(PayJalActivity.this, "कृपया गांव का चयन करें", Toast.LENGTH_LONG).show();

                spinnerVillage.requestFocus();
                return "no";
            }
        }

//        if(CommonPref.getUserDetails(PayJalActivity.this).get_Role().equalsIgnoreCase("PANADM"))
//        {
//            if(work_type.equalsIgnoreCase("PI")) {
//                if((spinnerVillage != null && spinnerVillage.getSelectedItem() !=null ))
//                {
//                    if((String)spinnerVillage.getSelectedItem()!="-चुनें-")
//                    {
//                        isvalid="yes";
//                    }else {
//                        Toast.makeText(PayJalActivity.this, "कृपया गांव का चयन करें", Toast.LENGTH_LONG).show();
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
//                    Toast.makeText(PayJalActivity.this, "कृपया गांव का चयन करें", Toast.LENGTH_LONG).show();
//
//                    spinnerVillage.requestFocus();
//                    return "no";
//                }
//            }

        if ((spinnerWard != null && spinnerWard.getSelectedItem() != null)) {
            if ((String) spinnerWard.getSelectedItem() != "-चुनें-") {
                if (_varwardID.equalsIgnoreCase("99999")) {
                    if (txtOtherWard.getText().toString().trim().length() <= 0) {
                        Toast.makeText(PayJalActivity.this, "कृपया अन्य वार्ड का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    } else {
                        isvalid = "yes";
                    }
                    if (txtOtherYojana.getText().toString().trim().length() <= 0) {
                        Toast.makeText(PayJalActivity.this, "कृपया अन्य योजना का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    } else {
                        isvalid = "yes";
                    }
                }

            } else {
                Toast.makeText(PayJalActivity.this, "कृपया वार्ड का चयन करें", Toast.LENGTH_LONG).show();

                spinnerWard.requestFocus();
                return "no";
            }
        }
//        if((spinnerWard != null && spinnerWard.getSelectedItem() !=null ))
//        {
//            if((String)spinnerWard.getSelectedItem()!="-चुनें-")
//            {
//                isvalid="yes";
//            }else {
//                Toast.makeText(PayJalActivity.this, "कृपया वार्ड का चयन करें", Toast.LENGTH_LONG).show();
//
//                spinnerWard.requestFocus();
//                return "no";
//            }
//        }
        if (!(_varwardID.equalsIgnoreCase(""))){
            if ((spinneryojanatype != null && spinneryojanatype.getSelectedItem() != null)) {
                if ((String) spinneryojanatype.getSelectedItem() != "--चुनें--") {
                    if (_varYojanaTypeNAME.equalsIgnoreCase("अन्य")) {
                        if (txtOtherYojana.getText().toString().trim().length() <= 0) {
                            Toast.makeText(PayJalActivity.this, "कृपया अन्य योजना का नाम दर्ज करें", Toast.LENGTH_LONG).show();
                            return "no";
                        } else {
                            isvalid = "yes";
                        }
                    }

                } else {
                    Toast.makeText(PayJalActivity.this, "कृपया योजना का चयन करें", Toast.LENGTH_LONG).show();

                    spinneryojanatype.requestFocus();
                    return "no";
                }
            }
    }
        if((spinnerCurrentPhysical != null && spinnerCurrentPhysical.getSelectedItem() !=null ))
        {
            if((String)spinnerCurrentPhysical.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";


            }else {
                Toast.makeText(PayJalActivity.this, "कृपया वर्तमान भौतिक स्थिति का चयन करें", Toast.LENGTH_LONG).show();

                spinnerCurrentPhysical.requestFocus();
                return "no";
            }
        }


//   ------------//if physical stuts is started or  then validate below filed
        if(_varcurrentPhysical_Id.equalsIgnoreCase("2"))  //project completed
        {

            if(isBoringStarted.equalsIgnoreCase("Y"))
            {
                if((spinnernBoringFtMt != null && spinnernBoringFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernBoringFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernBoringFtMt.requestFocus();
                        return "no";
                    }
                }

                if (txtBoringGahrai.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया बोरिंग की गहराई भरे", Toast.LENGTH_LONG).show();
                    return "no";
                }

                if((spinnerISIMarkBoring != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkBoring.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkBoring.requestFocus();
                        return "no";
                    }
                }
                if (txtUPVCPipeCMLNum.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया UPVC पाइप का CML नंबर लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }

            if(isMotorPumpStatus.equalsIgnoreCase("Y"))
            {
                if((spinnerISIMarkMotorPump != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkMotorPump.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkMotorPump.requestFocus();
                        return "no";
                    }
                }
                if((spinnerMotorPumpCapacity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerMotorPumpCapacity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "मोटर पंप की क्षमता का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerMotorPumpCapacity.requestFocus();
                        return "no";
                    }
                }
            }

            if(isStaggingDone.equalsIgnoreCase("Y"))
            {
                if((spinnerStaggingType != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerStaggingType.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया स्टेजिंग के प्रकार का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerStaggingType.requestFocus();
                        return "no";
                    }
                }
                if((spinnernStageFtMt != null && spinnernStageFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernStageFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया स्टेजिंग की ऊंचाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernStageFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtStageheight.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया मीटर में स्टेजिंग की ऊंचाई दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnerTankiCapacity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerTankiCapacity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया टैंकी की क्षमता का चयन करेंं", Toast.LENGTH_LONG).show();

                        spinnerTankiCapacity.requestFocus();
                        return "no";
                    }
                }
                if((spinnerTankiQuantity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerTankiQuantity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया टैंकी की संख्या का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerTankiQuantity.requestFocus();
                        return "no";
                    }
                }
            }

            if(isVitranPranali.equalsIgnoreCase("Y"))
            {
                if((spinnernVitranFtMt != null && spinnernVitranFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernVitranFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप की लंबाई-मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernVitranFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtVitranPipeLength.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया पाइप की लंबाई लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnernVitranDepthFtMt != null && spinnernVitranDepthFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernVitranDepthFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप लाइन की गहराई-मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernVitranDepthFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtDepthFromLandSurface.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया पाइप लाइन की गहराई लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnerISIMarkVitranPranali != null && spinnerISIMarkVitranPranali.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkVitranPranali.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkVitranPranali.requestFocus();
                        return "no";
                    }
                }
                if (txtCMLNumber.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया CML नंबर दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }

            if(isElectrictyconnection.equalsIgnoreCase("Y"))
            {
                if((spinnerVidyutConnection != null && spinnerVidyutConnection.getSelectedItem() !=null ))
                {
                    if((String)spinnerVidyutConnection.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि बिजली कनेक्शन उपलब्ध है या नहीं", Toast.LENGTH_LONG).show();

                        spinnerVidyutConnection.requestFocus();
                        return "no";
                    }
                }
                if((spinnerVidyutConnection != null && spinnerVidyutConnection.getSelectedItem() !=null ))
                {
                    if((String)spinnerVidyutConnection.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";

                        if((String)spinnerVidyutConnection.getSelectedItem()=="हाँ")
                        {
                            if (txtElectricityConsumerNo.getText().toString().trim().length() <= 0) {
                                Toast.makeText(PayJalActivity.this, "कृपया उपभोक्ता संख्या दर्ज करें", Toast.LENGTH_LONG).show();
                                return "no";
                            }
                        }
                    }
                }

            }


            if(isGrihJalSunyojan.equalsIgnoreCase("Y"))
            {

                if (txtNumberOfHouse.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया घरों की संख्या दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }

                if((spinnerwaterSupplyStatus != null && spinnerwaterSupplyStatus.getSelectedItem() !=null ))
                {
                    if((String)spinnerwaterSupplyStatus.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पानी की आपूर्ति की स्थिति दर्ज करें", Toast.LENGTH_LONG).show();

                        spinnerwaterSupplyStatus.requestFocus();
                        return "no";
                    }
                }
                if((spinnerJalStumbh != null && spinnerJalStumbh.getSelectedItem() !=null ))
                {
                    if((String)spinnerJalStumbh.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि जल के खंभे का निर्माण शुरू हुआ या नहीं", Toast.LENGTH_LONG).show();

                        spinnerJalStumbh.requestFocus();
                        return "no";
                    }
                }
                if((spinnerPipeType != null && spinnerPipeType.getSelectedItem() !=null ))
                {
                    if((String)spinnerPipeType.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप के प्रकार का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerPipeType.requestFocus();
                        return "no";
                    }
                }
                if((spinnerFherulUse != null && spinnerFherulUse.getSelectedItem() !=null ))
                {
                    if((String)spinnerFherulUse.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि फेरुल का उपयोग किया जाता है या नहीं", Toast.LENGTH_LONG).show();

                        spinnerFherulUse.requestFocus();
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
                            Toast.makeText(PayJalActivity.this, "कृपया M B नंबर दर्ज करें।", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                }else {
                    Toast.makeText(PayJalActivity.this, "कृपया M B पूर्ण हुआ या नहीं का चयन करें", Toast.LENGTH_LONG).show();

                    spinnerMBCompleted.requestFocus();
                    return "no";
                }
            }
        }

        //   ------------//if physical stuts is started or completed then validate below filed
        if(_varcurrentPhysical_Id.equalsIgnoreCase("3"))  //project completed
        {
            if(isBoringStarted.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया बोरिंग की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isBoringStarted.equalsIgnoreCase("Y"))
            {
                if((spinnernBoringFtMt != null && spinnernBoringFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernBoringFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernBoringFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtBoringGahrai.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया बोरिंग की गहराई भरे", Toast.LENGTH_LONG).show();
                    return "no";
                }

                if((spinnerISIMarkBoring != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkBoring.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkBoring.requestFocus();
                        return "no";
                    }
                }
                if (txtUPVCPipeCMLNum.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया UPVC पाइप का CML नंबर लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
            if(isMotorPumpStatus.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया मोटर पंप की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isMotorPumpStatus.equalsIgnoreCase("Y"))
            {
                if((spinnerISIMarkMotorPump != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkMotorPump.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkMotorPump.requestFocus();
                        return "no";
                    }
                }
                if((spinnerMotorPumpCapacity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerMotorPumpCapacity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "मोटर पंप की क्षमता का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerMotorPumpCapacity.requestFocus();
                        return "no";
                    }
                }
            }
            if(isStaggingDone.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया स्टेजिंग और टैंकी की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isStaggingDone.equalsIgnoreCase("Y"))
            {
                if((spinnerStaggingType != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerStaggingType.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया स्टेजिंग के प्रकार का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerStaggingType.requestFocus();
                        return "no";
                    }
                }
                if((spinnernStageFtMt != null && spinnernStageFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernStageFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया स्टेजिंग की ऊंचाई - मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernStageFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtStageheight.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया मीटर में स्टेजिंग की ऊंचाई दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnerTankiCapacity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerTankiCapacity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया टैंकी की क्षमता का चयन करेंं", Toast.LENGTH_LONG).show();

                        spinnerTankiCapacity.requestFocus();
                        return "no";
                    }
                }
                if((spinnerTankiQuantity != null && spinnerISIMarkBoring.getSelectedItem() !=null ))
                {
                    if((String)spinnerTankiQuantity.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया टैंकी की संख्या का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerTankiQuantity.requestFocus();
                        return "no";
                    }
                }
            }

            if(isVitranPranali.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया वितरण प्राणली की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isVitranPranali.equalsIgnoreCase("Y"))
            {
                if((spinnernVitranFtMt != null && spinnernVitranFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernVitranFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप की लंबाई-मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernVitranFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtVitranPipeLength.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया पाइप की लंबाई लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnernVitranDepthFtMt != null && spinnernVitranDepthFtMt.getSelectedItem() !=null ))
                {
                    if((String)spinnernVitranDepthFtMt.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप लाइन की गहराई-मीटर या फ़ीट का चयन करें", Toast.LENGTH_LONG).show();

                        spinnernVitranDepthFtMt.requestFocus();
                        return "no";
                    }
                }
                if (txtDepthFromLandSurface.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया पाइप लाइन की गहराई लिखें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if((spinnerISIMarkVitranPranali != null && spinnerISIMarkVitranPranali.getSelectedItem() !=null ))
                {
                    if((String)spinnerISIMarkVitranPranali.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया आईएसआई चिह्न का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerISIMarkVitranPranali.requestFocus();
                        return "no";
                    }
                }
                if (txtCMLNumber.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया CML नंबर दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }

            if(isElectrictyconnection.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया बिजली सान्योजन की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isElectrictyconnection.equalsIgnoreCase("Y"))
            {
                if((spinnerVidyutConnection != null && spinnerVidyutConnection.getSelectedItem() !=null ))
                {
                    if((String)spinnerVidyutConnection.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                        if((String)spinnerVidyutConnection.getSelectedItem()=="हाँ")
                        {
                            if (txtElectricityConsumerNo.getText().toString().trim().length() <= 0) {
                                Toast.makeText(PayJalActivity.this, "कृपया उपभोक्ता संख्या दर्ज करें", Toast.LENGTH_LONG).show();
                                return "no";
                            }
                        }
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि बिजली कनेक्शन उपलब्ध है या नहीं", Toast.LENGTH_LONG).show();

                        spinnerVidyutConnection.requestFocus();
                        return "no";
                    }
                }
//                if (txtElectricityConsumerNo.getText().toString().trim().length() <= 0) {
//                    Toast.makeText(PayJalActivity.this, "कृपया उपभोक्ता संख्या दर्ज करें", Toast.LENGTH_LONG).show();
//                    return "no";
//                }
            }
            if(isGrihJalSunyojan.equalsIgnoreCase("N"))
            {
                Toast.makeText(PayJalActivity.this, "कृपया ग्रिह जल सान्योजन की सभी विवरण दर्ज करें", Toast.LENGTH_LONG).show();
                return "no";
            }
            if(isGrihJalSunyojan.equalsIgnoreCase("Y"))
            {

                if (txtNumberOfHouse.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PayJalActivity.this, "कृपया घरों की संख्या दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }

                if((spinnerwaterSupplyStatus != null && spinnerwaterSupplyStatus.getSelectedItem() !=null ))
                {
                    if((String)spinnerwaterSupplyStatus.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पानी की आपूर्ति की स्थिति दर्ज करें", Toast.LENGTH_LONG).show();

                        spinnerwaterSupplyStatus.requestFocus();
                        return "no";
                    }
                }
                if((spinnerJalStumbh != null && spinnerJalStumbh.getSelectedItem() !=null ))
                {
                    if((String)spinnerJalStumbh.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि जल के खंभे का निर्माण शुरू हुआ या नहीं", Toast.LENGTH_LONG).show();

                        spinnerJalStumbh.requestFocus();
                        return "no";
                    }
                }
                if((spinnerPipeType != null && spinnerPipeType.getSelectedItem() !=null ))
                {
                    if((String)spinnerPipeType.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया पाइप के प्रकार का चयन करें", Toast.LENGTH_LONG).show();

                        spinnerPipeType.requestFocus();
                        return "no";
                    }
                }
                if((spinnerFherulUse != null && spinnerFherulUse.getSelectedItem() !=null ))
                {
                    if((String)spinnerFherulUse.getSelectedItem()!="-चुनें-")
                    {
                        isvalid="yes";
                    }else {
                        Toast.makeText(PayJalActivity.this, "कृपया चुनें कि फेरुल का उपयोग किया जाता है या नहीं", Toast.LENGTH_LONG).show();

                        spinnerFherulUse.requestFocus();
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
                            Toast.makeText(PayJalActivity.this, "कृपया M B नंबर दर्ज करें।", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                }else {
                    Toast.makeText(PayJalActivity.this, "कृपया M B पूर्ण हुआ या नहीं का चयन करें", Toast.LENGTH_LONG).show();

                    spinnerMBCompleted.requestFocus();
                    return "no";
                }
            }
            if(_varcurrentPhysical_Id.equalsIgnoreCase("3"))
            {
                lin_yojnaCompleteddate.setVisibility(View.VISIBLE);
                if(isProjectCompleted.equalsIgnoreCase("N"))
                {
                    Toast.makeText(PayJalActivity.this, "परियोजना के पूरा होने की तारीख दर्ज करें", Toast.LENGTH_LONG).show();
                    return "no";
                }
                if(isProjectCompleted.equalsIgnoreCase("Y"))
                {
                    if (txtDateOfCompletionOfProject.getText().toString().trim().length() <= 0) {
                        Toast.makeText(PayJalActivity.this, "परियोजना के पूरा होने की तारीख दर्ज करें", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                    if (txtDateOfCompletionOfProject.getText().toString().trim().length() > 0) {
                        if (!txtDateOfCompletionOfProject.getText().toString().contains("-")) {
                            Toast.makeText(PayJalActivity.this, "परियोजना के पूरा होने की तारीख - अमान्य तिथि प्रारूप", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                }
            }

        }
        //-----------end if project strated validation
        if((spinnerIot != null && spinnerIot.getSelectedItem() !=null ))
        {
            if((String)spinnerIot.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(PayJalActivity.this, "कृपया चुनें IOT डिवाइस है या नही", Toast.LENGTH_LONG).show();

                spinnerIot.requestFocus();
                return "no";
            }
        }
        if (txtRemarks.getText().toString().trim().length() <= 0) {
            Toast.makeText(PayJalActivity.this, "कृपया अभियुक्ति दर्ज करें", Toast.LENGTH_LONG).show();
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

    public void loadIOT(){
        arrayAdapterIOT = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Iot);
        spinnerIot.setAdapter(arrayAdapterIOT);
    }




}

