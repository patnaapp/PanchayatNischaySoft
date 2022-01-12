package app.bih.in.nic.nischayyojana.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.DataBaseHelper;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class CameraMultiPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img1, img2, img3, img4;
    Button btnOk;
    Intent imageData1, imageData2, imageData3, imageData4, imageData_threshing;
    private final static int CAMERA_PIC = 99;
    int ThumbnailSize = 0;
    String PID = "0";
    DataBaseHelper dataBaseHelper;
    Double preLat, preLong, crntLat, crntLong;
    String UserId = "";
    private static int CAMERA_REQUEST = 1888;
    Bitmap bMapRotate;
    public boolean isPictureTaken = false;
    String purpose = "";
    String keyid = "", str_img1 = "", str_img2 = "", str_img3 = "", str_img4 = "";
    private static byte[] CompressedImageByteArray;
    private File folder;
    public static final int TAKE_PHOTO=1;
    String imageFileName=null;
    Bitmap bmp;
    LocationManager mlocManager = null;
    static Location LastLocation = null;
    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;
    private static final String TAG = "MyActivity";

    private static Bitmap CompressedImage;
    private boolean isTimerStarted = false;
    Chronometer chronometer;
    static double latitude = 0.0, longitude = 0.0;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiple_photo);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        // PID = getIntent().getStringExtra("index");
        PID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DISECODE", "");
        UserId = PreferenceManager.getDefaultSharedPreferences(this).getString("USERID", "");

        Log.d("duhoeh", "" + PID);
        // isOpen = getIntent().getStringExtra("isOpen");
        // purpose = getIntent().getStringExtra("edited");
        purpose = "";

        if (PID == null) {
            PID = "0";
        }

        folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/sample1/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        img1 = (ImageView) findViewById(R.id.imageButton1);
        img2 = (ImageView) findViewById(R.id.imageButton2);
        img3 = (ImageView) findViewById(R.id.imageButton3);
        img4 = (ImageView) findViewById(R.id.imageButton4);

        // img1.setEnabled(false);

        // img_threshing = (ImageView) findViewById(R.id.During_Threshing);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        locationManager();

        if (!purpose.equalsIgnoreCase("yes"))

        {
            btnOk.setEnabled(false);
            //btnOk.setBackgroundResource(R.drawable.buttonshape);
        }

        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            ThumbnailSize = displaymetrics.widthPixels / 2;
        } else {

            ThumbnailSize = displaymetrics.widthPixels / 4;
            img1.getLayoutParams().height = img2.getLayoutParams().height = img3
                    .getLayoutParams().height = ThumbnailSize = img4.getLayoutParams().height;
        }

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();
        Cursor cursor = db
                .rawQuery("SELECT Photo1,Photo2,Photo3,Photo4 FROM EjananiNewEntry where EntryBy =?", new String[]{String.valueOf(PID)});


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Photo1")), Base64.DEFAULT);
                //byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo1"));
                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                if (bmp != null) {
                    img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize));
                    //img1.setImageBitmap(Utiilties.GenerateThumbnail(null,ThumbnailSize, ThumbnailSize));
                    img1.setOnClickListener(this);
                    btnOk.setEnabled(true);
                } else {
                    img1.setOnClickListener(this);
                    img2.setEnabled(true);
                    img3.setEnabled(true);
                    img4.setEnabled(true);
                    //img_threshing.setEnabled(false);
                }
            } else {
                img1.setOnClickListener(this);
                img2.setEnabled(true);
                img3.setEnabled(true);
                img4.setEnabled(true);
                // img_threshing.setEnabled(false);

            }

            if (!cursor.isNull(1)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Photo2")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo2"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img2.setOnClickListener(this);
                } else {
                    img2.setOnClickListener(this);
                }

            }

            if (!cursor.isNull(2)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Photo3")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img3.setOnClickListener(this);
                } else {
                    img3.setOnClickListener(this);
                }
            }



            if (!cursor.isNull(3)) {

                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Photo4")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img4.setOnClickListener(this);
                } else {
                    img4.setOnClickListener(this);
                }
            }

        }
        cursor.close();
        db.close();
    }

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,actionCode);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    bmp = (Bitmap) extras.get("data");


                    Date d = new Date(GlobalVariables.glocation.getTime());
                    String dat = d.toLocaleString();
                    Bitmap bitmap2 = Utiilties.DrawText(this, bmp, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                            , "Accuracy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "Time:" + dat);
                    latitude = GlobalVariables.glocation.getLatitude();
                    longitude = GlobalVariables.glocation.getLongitude();
                    //setCameraImage(Utiilties.GenerateThumbnail(bitmap2, 500, 500));

                    img1.setImageBitmap(bitmap2);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bmp = (Bitmap) extras.get("data");
                    img2.setImageBitmap(bmp);
                }
                break;

            case 3:
                if(resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bmp = (Bitmap) extras.get("data");
                    img3.setImageBitmap(bmp);
                }
                break;

            case 4:
                if(resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bmp = (Bitmap) extras.get("data");
                    img4.setImageBitmap(bmp);
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        if (view.equals(btnOk)) {
            if (imageData1 == null && imageData2 == null && imageData3 == null  && imageData4 == null && purpose.equals("isEdit")) {
                finish();
            } else {
                saveImage();

                Intent iUserHome = new Intent(this, UserHomeNewActivity.class);
                iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iUserHome);
            }
        } else {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                buildAlertMessageNoGps();
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {


                if (view.equals(img1))
                {
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 1);

                }
                else if (view.equals(img2))
                {
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 2);
                }

                else if (view.equals(img3))
                {
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 3);
                }

                else if (view.equals(img4))
                {
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 4);
                }

            }
        }
    }

    private void saveImage() {

        int i = 0;
        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getWritableDatabase();
        for (i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            String[] whereArgs;
            byte[] imgData;
            switch (i) {
                case 0:
                    //GPSTime
                    if (imageData1 != null)
                    {

                        imgData = imageData1.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img1 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Photo1", str_img1);
                        values.put("Latitude", String.valueOf(imageData1.getStringExtra("Lat")));
                        values.put("Longitude", String.valueOf(imageData1.getStringExtra("Lng")));
                        whereArgs = new String[]{String.valueOf(PID)};

                        long c = db.update("EjananiNewEntry", values, "EntryBy=?", whereArgs);
                        if (c > 0)
                        {
                            Toast.makeText(this, "Images 1 saved successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Images 1 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 1:
                    if (imageData2 != null)
                    {
                        imgData = imageData2.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img2 = org.kobjects.base64.Base64.encode(imgData);

                        values.put("Photo2", str_img2);
                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("EjananiNewEntry", values, "EntryBy=?", whereArgs);

                        if (c > 0)
                        {
                            Toast.makeText(this, "Images 2 saved successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Images 2 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 2:
                    if (imageData3 != null)
                    {
                        imgData = imageData3.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img3 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Photo3", str_img3);
                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("EjananiNewEntry", values, "EntryBy=?", whereArgs);
                        if (c > 0)
                        {
                            Toast.makeText(this, "Images 3 saved successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Images 3 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 3:
                    if (imageData4 != null)
                    {
                        imgData = imageData4.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img4 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Photo4", str_img4);


                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("EjananiNewEntry", values, "EntryBy=?", whereArgs);
                        if (c > 0)
                        {
                            Toast.makeText(this, "Images 4 saved successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Images 4 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;

            }

        }

        db.close();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
//    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Photo1,Photo2,Photo3,Photo4 FROM EjananiNewEntry where EntryBy =?", new String[]{String.valueOf(PID)});


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {

                byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo1"));
                if (imgData != null) {
                    finish();
                }
            } else {
                Toast.makeText(this, "Please capture images", Toast.LENGTH_LONG).show();
            }

        }

    }


    private void locationManager() {
        if (GlobalVariables.glocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            img1.setEnabled(false);
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
        } else {
            img1.setEnabled(true);
            //progress_finding_location.setVisibility(View.GONE);

        }
    }


    private void updateUILocation(Location location) {

        Message.obtain(
                mHandler,
                UPDATE_LATLNG,
                new DecimalFormat("#.0000000").format(location.getLatitude())
                        + "-"
                        + new DecimalFormat("#.0000000").format(location
                        .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
                .sendToTarget();

    }

    private final LocationListener mlistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            //A new location update is received. Do something useful with it.
            //Update the UI with
            //the location update.
            if (Utiilties.isGPSEnabled(CameraMultiPhotoActivity.this)) {

                LastLocation = location;
                GlobalVariables.glocation = location;
                updateUILocation(GlobalVariables.glocation);
                //   if (getIntent().getStringExtra("KEY_PIC").equals("1")) {
                if (location.getLatitude() > 0.0) {
                    dialog = new ProgressDialog(CameraMultiPhotoActivity.this);
                    //long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    if (location.getAccuracy() > 0 && location.getAccuracy() < 150) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
//                            takePhoto.setText(" Take Photo ");
//                            progress_finding_location.setVisibility(View.GONE);
                        img1.setEnabled(true);
                    } else {
                        dialog.setMessage("Wait for gps to become stable");
                        dialog.show();

//                            takePhoto.setText(" Wait for GPS to Stable ");
//                            progress_finding_location.setVisibility(View.VISIBLE);
                        img1.setEnabled(false);

                    }

                }


            } else {
                Message.obtain(
                        mHandler,
                        UPDATE_LATLNG,
                        new DecimalFormat("#.0000000").format(location.getLatitude())
                                + "-"
                                + new DecimalFormat("#.0000000").format(location
                                .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
                        .sendToTarget();

            }


        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case UPDATE_ADDRESS:
                case UPDATE_LATLNG:
                    String[] LatLon = ((String) msg.obj).split("-");
//
                    Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);


                    break;
            }
        }
    };



}