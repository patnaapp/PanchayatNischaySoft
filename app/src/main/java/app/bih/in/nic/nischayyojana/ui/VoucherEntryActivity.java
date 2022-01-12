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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.db.WebServiceHelper;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.MODEOFPAYMENT;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;
import app.bih.in.nic.nischayyojana.util.CommonPref;
import app.bih.in.nic.nischayyojana.util.Utiilties;


public class VoucherEntryActivity extends AppCompatActivity implements View.OnClickListener{

    //http://online.bih.nic.in/AGRFRM/Register.aspx
    //10.133.17.220-KishanDB

    DataBaseHelper localDBHelper;

    ImageView img_home,btncal1;

    private final static int CAMERA_PIC = 99;
    int ThumbnailSize = 70;

    ProgressDialog pd;

    Spinner spinnerFYear, spinnerPanchayat,spinnerNischayType;
    Spinner spinnerward,spinnernischaytype,spinneryojanatype,spinnermodeofpayment,spinnerstatus;

    LinearLayout lncheque;
    ArrayList<PanchayatData> PanchayatList = new ArrayList<>();
    ArrayAdapter<String> Panchayatadapter;
    String _varPanchayatName="",_varpanchayatID="",_varPanchayatSetID="";
    
    ArrayList<ward> WardList = new ArrayList<ward>();
    ArrayAdapter<String> wardadapter;
    String _varwardName="",_varwardID="",_varwardSetID="";

    ArrayList<SCHEME> NischayTypeList = new ArrayList<>();
    ArrayAdapter<String> NischayTypeadapter;
    String _varNischayTypeNAME="",_varNischayTypeID="",setNischayTypeID="0";

    ArrayList<YOJANA> YojanaTypeList = new ArrayList<>();
    ArrayAdapter<String> YojanaTypeadapter;
    String _varYojanaTypeNAME="",_varYojanaTypeID="",setYojanaTypeID="0";


    ArrayList<MODEOFPAYMENT> PaymentModeList = new ArrayList<>();
    ArrayAdapter<String>PaymentModeadapter;
    String _varPaymentModeNAME="",_varPaymentModeID="",setPaymentModeID="0";


    ArrayList<Fyear> FyearList = new ArrayList<>();
    ArrayAdapter<String> fyearadapter;
    String _varFYearID="0";
    
    TextView txtheader,txtyojananame,lblTransactionNum;
    EditText txtReceiverName,txtMobileNum,txtBhugtanForWhat,txtTransactionNumber,txtAmount,txtRemarks;

    String ids,uid,_varTransactionNumber="0";
    String _WardCode,_WardName,_FYearCode,_SchemeName;
    String _vardistrictID="0",_varblockID="0";
    TextView txtChequeDate;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    boolean date1=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_entry);
        InitializeViews(); //-----------

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
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
        spinnerward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    spinneryojanatype.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnernischaytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    YOJANA wrd = YojanaTypeList.get(arg2 - 1);
                    _varYojanaTypeID = wrd.get_YojanaID();
                    _varYojanaTypeNAME= wrd.get_YojanaName();
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
        spinnermodeofpayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    MODEOFPAYMENT wrd = PaymentModeList.get(arg2 - 1);
                    _varPaymentModeID = wrd.get_PayModeID();
                    _varPaymentModeNAME= wrd.get_PayModeName();

                    if(!_varPaymentModeID.equalsIgnoreCase("1"))
                    {
                        lncheque.setVisibility(View.VISIBLE);

                        if(_varPaymentModeID.equalsIgnoreCase("2"))
                        {
                            lblTransactionNum.setText("चेक नंबर :");
                        }
                        if(_varPaymentModeID.equalsIgnoreCase("3"))
                        {
                            lblTransactionNum.setText("DD नंबर :");
                        }
                        if(_varPaymentModeID.equalsIgnoreCase("4"))
                        {
                            lblTransactionNum.setText("UTR नंबर :");
                        }
                    }
                    else
                    {
                        txtTransactionNumber.setText("");
                        lncheque.setVisibility(View.GONE);
                    }
                }
                else
                {
                    _varPaymentModeID = "0";
                    _varPaymentModeNAME= "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        
    }

    public void InitializeViews()
    {
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        // pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));

        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
//        _WardCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_CODE", "");
//        _WardName= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("WARD_NAME", "");
//        _FYearCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FYEAR_CODE", "");
        _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");
        _varNischayTypeID=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");


        localDBHelper = new DataBaseHelper(this);

        lncheque=findViewById(R.id.lnCheque);
        lncheque.setVisibility(View.GONE);
        
        //spinnerward,spinnernischaytype,spinneryojanatype,spinnermodeofpayment
        spinnerFYear = findViewById(R.id.spinnerFYear) ;
        spinnerPanchayat = findViewById(R.id.spinnerPanchayat) ;
        spinnerward = findViewById(R.id.spinnerward) ;
        spinnernischaytype= findViewById(R.id.spinnernischaytype) ;
        spinneryojanatype= findViewById(R.id.spinneryojanatype) ;
        spinnermodeofpayment= findViewById(R.id.spinnermodeofpayment) ;

        TextView txtMainHeader= findViewById(R.id.txtHeaderTop);
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
        txtheader= findViewById(R.id.txtheader);
        txtheader.setText("वित्तीय अपडेट");

        txtyojananame= findViewById(R.id.txtyojananame);
        if(_varNischayTypeID.equalsIgnoreCase("1"))
        {
            txtyojananame.setText(R.string.headertext1);
        }
        if(_varNischayTypeID.equalsIgnoreCase("2"))
        {
            txtyojananame.setText(R.string.headertext2);
        }

        img_home=findViewById(R.id.imghome);
        btncal1= findViewById(R.id.btncal1);
        btncal1.setOnClickListener(this);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        txtChequeDate= findViewById(R.id.txtChequeDate);

        txtReceiverName= findViewById(R.id.txtReceiverName);
        txtMobileNum= findViewById(R.id.txtMobileNum);
        txtBhugtanForWhat= findViewById(R.id.txtBhugtanForWhat);
        txtTransactionNumber= findViewById(R.id.txtTransactionNumber);
        lblTransactionNum= findViewById(R.id.lblTransactionNum);

        txtAmount=findViewById(R.id.txtAmount);
        txtRemarks=findViewById(R.id.txtRemarks);

        btncal1= findViewById(R.id.btncal1);

        btncal1.setOnClickListener(this);

        _vardistrictID= CommonPref.getUserDetails(VoucherEntryActivity.this).get_DistCode();
        _varblockID=CommonPref.getUserDetails(VoucherEntryActivity.this).get_BlockCode();
        _varpanchayatID=CommonPref.getUserDetails(VoucherEntryActivity.this).get_PanchayatCode();

        if(getIntent().hasExtra("NTYPE")) {
            _varNischayTypeID = getIntent().getStringExtra("NTYPE");
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("SCHEME_CODE", _varNischayTypeID).commit();
        }

        if(getIntent().hasExtra("EDIT"))
        {
            ids = getIntent().getStringExtra("ID");
            if (ids == null) {
                ids = "0";
            }
            ShowData();
        }
        else
        {
            getWardSpinnerData(_vardistrictID,_varblockID,_varpanchayatID,_varwardID);
            getSchemeSpinnerData();

        }
        getYojanaSpinnerData();
        getModeOfPaymentSpinnerData();

        getPanchayatSpinnerData(_varblockID);

        loadFYear();
    }

    public  void loadFYear()
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
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

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerFYear.setSelection(setID);
        }
    }
    private void getPanchayatSpinnerData(String blkid)
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
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
    private void getWardSpinnerData(String distid,String blkid,String panid,String setWID)
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
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
            StringList.add(pList.get(i).getWardname());
            if(_varwardID.equalsIgnoreCase(pList.get(i).getWardCode()))
            {
                setWID=""+ (i+1);
            }
        }
        wardadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerward.setAdapter(wardadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerward.setSelection(Integer.parseInt(setWID));
        }
    }
    private class LoadWARDData extends AsyncTask<String, String, ArrayList<ward>> {
        String distCode="";
        String blockCode="";
        String panCode="";

        private final ProgressDialog dialog = new ProgressDialog(
                VoucherEntryActivity.this);

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

                    DataBaseHelper placeData = new DataBaseHelper(VoucherEntryActivity.this);
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

        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
        WardList=localDBHelper.getWardLocal( _varpanchayatID);
        if(WardList.size()>0 ) {
            loadWardSpinnerData(WardList);
        }
    }
    private void getSchemeSpinnerData()
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
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
        spinnernischaytype.setAdapter(NischayTypeadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnernischaytype.setSelection(Integer.parseInt(setNischayTypeID));
        }
        if(getIntent().hasExtra("NTYPE")) {
            spinnernischaytype.setSelection(Integer.parseInt(setNischayTypeID));
        }
    }


    private void getYojanaSpinnerData()
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
        YojanaTypeList=localDBHelper.getYojanaList(_varNischayTypeID,_varpanchayatID,_varwardID);
        if(YojanaTypeList.size()>0 ) {
            loadYojanaSpinnerData();
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(VoucherEntryActivity.this);
            ab.setIcon(R.drawable.logo);
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
        YojanaTypeadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinneryojanatype.setAdapter(YojanaTypeadapter);
        if(getIntent().hasExtra("EDIT"))
        {
            spinneryojanatype.setSelection(Integer.parseInt(setYojanaTypeID));
        }
    }

    private void getModeOfPaymentSpinnerData()
    {
        localDBHelper = new DataBaseHelper(VoucherEntryActivity.this);
        PaymentModeList=localDBHelper.getPayModeList();
        if(PaymentModeList.size()>0 ) {
            loadModeOfPaymentSpinnerData();
        }
    }
    private void loadModeOfPaymentSpinnerData()
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        setYojanaTypeID="0";
        for(int i=0;i<PaymentModeList.size();i++)
        {
            StringList.add(PaymentModeList.get(i).get_PayModeName());
            if(_varPaymentModeID.equalsIgnoreCase(PaymentModeList.get(i).get_PayModeID()))
            {
                setPaymentModeID=""+ (i+1);
            }
        }
        PaymentModeadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnermodeofpayment.setAdapter(PaymentModeadapter);
        if(getIntent().hasExtra("EDIT"))
        {
            spinnermodeofpayment.setSelection(Integer.parseInt(setPaymentModeID));
        }
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
            //String ds=(selectedMonth + 1) + "/" + selectedDay  +  "/"+ selectedYear;
            String ds=  selectedDay+ "/"+ (selectedMonth + 1)  +  "/"+ selectedYear;
            ds=ds.replace("/","-");
            if(date1)
            {
                txtChequeDate.setText(ds);
                Log.e("DateOfReqMadeByWIMC", ds);
            }

        }
    };
    public void ShowData()
    {

        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db
                .rawQuery(
                        "SELECT * FROM VoucherEntryDetails where CreatedBy=? AND NischayCode=? AND id=? ORDER BY id DESC",
                        new String[]{uid,_varNischayTypeID, ids});

        if (cursor.moveToNext()) {
            _varFYearID=cursor.getString(cursor.getColumnIndex("FYear"));
            _varpanchayatID=cursor.getString(cursor.getColumnIndex("PanCode"));

            _varwardID=cursor.getString(cursor.getColumnIndex("WardCode"));
            _varNischayTypeID=cursor.getString(cursor.getColumnIndex("NischayCode"));
            _varYojanaTypeID=cursor.getString(cursor.getColumnIndex("YojanaCode"));

            getWardSpinnerData(_vardistrictID,_varblockID,_varpanchayatID,_varwardID);

            getSchemeSpinnerData();
            getYojanaSpinnerData();

            //Cash-1 ; Cheque-2; DD-3; Direct Pay-4

            _varPaymentModeID=cursor.getString(cursor.getColumnIndex("PaymentModeCode"));
            getModeOfPaymentSpinnerData();

            if(!_varPaymentModeID.equalsIgnoreCase("1"))
            {
                lncheque.setVisibility(View.VISIBLE);
                txtTransactionNumber.setText(cursor.getString(cursor.getColumnIndex("TransactionNumber")));
                if(_varPaymentModeID.equalsIgnoreCase("2"))
                {
                    lblTransactionNum.setText("चेक नंबर :");
                }
                if(_varPaymentModeID.equalsIgnoreCase("3"))
                {
                    lblTransactionNum.setText("DD नंबर :");
                }
                if(_varPaymentModeID.equalsIgnoreCase("4"))
                {
                    lblTransactionNum.setText("UTR नंबर :");
                }
            }
            else
            {
                txtTransactionNumber.setText("");
                lncheque.setVisibility(View.GONE);
            }


            txtChequeDate.setText(cursor.getString(cursor.getColumnIndex("PaymentDate")));

            txtReceiverName.setText(cursor.getString(cursor.getColumnIndex("ReceiverName")));

            txtMobileNum.setText(cursor.getString(cursor.getColumnIndex("MobileNum")));

            txtBhugtanForWhat.setText(cursor.getString(cursor.getColumnIndex("PaymentForWhat")));

            txtAmount.setText(cursor.getString(cursor.getColumnIndex("Amount")));
            txtRemarks.setText(cursor.getString(cursor.getColumnIndex("Remarks")));

        }
        db.close();
        cursor.close();

    }


    public void OnClick_goToHomeScreen(View v)
    {
        finish();
        startActivity(new Intent(VoucherEntryActivity.this,UserHomeActivity.class));
    }
    public void OnClick_goToLoginScreen(View v)
    {
        Intent i=new Intent(VoucherEntryActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
    }
    public void onClick_SaveContinue(View view) {
        //update step 1 data to local database
        if (validateRecordBeforeSaving_p1().equalsIgnoreCase("yes")) {
            SQLiteDatabase db = localDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("FYear", _varFYearID);
            values.put("PanCode", _varpanchayatID);
            values.put("WardCode", _varwardID);
            values.put("NischayCode", _varNischayTypeID);
            values.put("YojanaCode", _varYojanaTypeID);
            values.put("PaymentModeCode", _varPaymentModeID);

            values.put("PaymentDate",txtChequeDate.getText().toString().trim());
            values.put("ReceiverName", txtReceiverName.getText().toString().trim());
            values.put("MobileNum", txtMobileNum.getText().toString().trim());
            values.put("PaymentForWhat", txtBhugtanForWhat.getText().toString().trim());
            values.put("TransactionNumber", txtTransactionNumber.getText().toString().trim());

            values.put("Amount", txtAmount.getText().toString().trim());
            values.put("Remarks", txtRemarks.getText().toString().trim());

            values.put("CreatedDate", Utiilties.getDateString());
            values.put("CreatedBy", uid);

            String[] whereArgs = new String[]{String.valueOf(ids)};
            long c = db.update("VoucherEntryDetails", values, "id=?", whereArgs);
            String savedOrUpdate="सेव";
            if (c > 0) {
                Toast.makeText(this, "Record updated", Toast.LENGTH_SHORT).show();
                savedOrUpdate="अपडेट";
            }
            if (c <= 0) {
                c = db.insert("VoucherEntryDetails", null, values);
                savedOrUpdate="सेव";
            }
            db.close();
            if (c > 0) {
                Toast.makeText(this, "रिकॉर्ड सेव हो गया", Toast.LENGTH_SHORT).show();

                Button btnsave = (Button) findViewById(R.id.btnsave);
                btnsave.setEnabled(false);

                AlertDialog.Builder ab = new AlertDialog.Builder(VoucherEntryActivity.this);
                ab.setTitle("रिकॉर्ड");
                ab.setMessage("रिकॉर्ड "+ savedOrUpdate + " हो गया");
                ab.setPositiveButton("[ ठीक ]", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                ab.show();
            }

        }
    }

    private String validateRecordBeforeSaving_p1() {
        String isvalid = "yes";
        if((spinnerFYear != null && spinnerFYear.getSelectedItem() !=null ))
        {
            if((String)spinnerFYear.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(VoucherEntryActivity.this, "कृपया वित्तीय वर्ष का चयन करें", Toast.LENGTH_LONG).show();

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
                Toast.makeText(VoucherEntryActivity.this, "कृपया पंचायत का चयन करें", Toast.LENGTH_LONG).show();

                spinnerPanchayat.requestFocus();
                return "no";
            }
        }
        if((spinnerward != null && spinnerward.getSelectedItem() !=null ))
        {
            if((String)spinnerward.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(VoucherEntryActivity.this, "कृपया वार्ड का चयन करें", Toast.LENGTH_LONG).show();

                spinnerward.requestFocus();
                return "no";
            }
        }
        if((spinneryojanatype != null && spinneryojanatype.getSelectedItem() !=null ))
        {
            if((String)spinneryojanatype.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(VoucherEntryActivity.this, "कृपया योजना प्रकार का चयन करें", Toast.LENGTH_LONG).show();

                spinneryojanatype.requestFocus();
                return "no";
            }
        }
        if (txtReceiverName.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया भुगतान प्राप्तकर्ता का नाम लिखे", Toast.LENGTH_LONG).show();
            return "no";
        }
        if (txtMobileNum.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया मोबाइल नंबर लिखे", Toast.LENGTH_LONG).show();
            return "no";
        }
        if (txtMobileNum.getText().toString().trim().length() < 10) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया मान्य 10 अंक का मोबाइल नंबर लिखे", Toast.LENGTH_LONG).show();
            return "no";
        }
        if (txtBhugtanForWhat.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "भुगतान किस मद्य में किया गया ", Toast.LENGTH_LONG).show();
            return "no";
        }
        if((spinnermodeofpayment != null && spinnermodeofpayment.getSelectedItem() !=null ))
        {
            if((String)spinnermodeofpayment.getSelectedItem()!="-चुनें-")
            {
                isvalid="yes";
            }else {
                Toast.makeText(VoucherEntryActivity.this, "कृपया भुगतान के प्रकार चुने ", Toast.LENGTH_LONG).show();

                spinnermodeofpayment.requestFocus();
                return "no";
            }
        }
        if(!_varPaymentModeID.equalsIgnoreCase("1"))
        {
            if (txtTransactionNumber.getText().toString().trim().length() <= 0) {
                Toast.makeText(VoucherEntryActivity.this, "कृपया लेन-देन (चेक/UTR/DD)का नंबर लिखे ", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if (txtChequeDate.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया लेन-देन का तारीख लिखे", Toast.LENGTH_LONG).show();
            return "no";
        }

        if (txtAmount.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया राशि दर्ज करें", Toast.LENGTH_LONG).show();
            return "no";
        }
        if (txtRemarks.getText().toString().trim().length() <= 0) {
            Toast.makeText(VoucherEntryActivity.this, "कृपया अपनी टिप्पणी दर्ज करें |", Toast.LENGTH_LONG).show();
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
