package app.bih.in.nic.nischayyojana.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.MarshmallowPermission;
import app.bih.in.nic.nischayyojana.util.Utiilties;


@SuppressLint("NewApi")
public class CameraActivity1 extends Activity {

    MarshmallowPermission permission;
    double accuracy = 0;


    boolean init;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        init = false;
        permission = new MarshmallowPermission(this, Manifest.permission.CAMERA);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera();
        }


        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera();
        }

        super.onResume();

    }


    private Camera mCamera;
    private CameraPreview mPreview;

    Location LastLocation = null;
    LocationManager mlocManager = null;

    AlertDialog.Builder alert;


    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;
    private static final String TAG = "MyActivity";
    private Handler mHandler;
    private static byte[] CompressedImageByteArray;
    private static Bitmap CompressedImage;
    private boolean isTimerStarted = false;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameranonlatlang);
        Button takePhoto = (Button) findViewById(R.id.btnCapture);
        takePhoto.setEnabled(true);


        if (Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#002255"));
        }

    }


    private void initializeCamera() {
        init = true;
        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        isTimerStarted = false;

        mCamera = getCameraInstance();
        Parameters param = mCamera.getParameters();


        List<Size> sizes = param.getSupportedPictureSizes();
        int iTarget = 0;
        for (int i = 0; i < sizes.size(); i++) {
            Size size = sizes.get(i);
            if (size.width < 1000) {
                iTarget = i;
                break;
            }

        }
        param.setJpegQuality(100);
        param.setPictureSize(sizes.get(iTarget).width,
                sizes.get(iTarget).height);
        mCamera.setParameters(param);
        alert = new AlertDialog.Builder(this);
        Display getOrient = getWindowManager().getDefaultDisplay();

        int rotation = getOrient.getRotation();

        switch (rotation) {
            case Surface.ROTATION_0:
                mCamera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_90:
                break;
            case Surface.ROTATION_180:
                break;
            case Surface.ROTATION_270:
                mCamera.setDisplayOrientation(90);
                break;
            default:
                break;
        }


        try {

            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);

        } catch (Exception e) {
            finish();
        }

        if (!GlobalVariables.isOfflineGPS) {
//			mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//			if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//				alert.setTitle("GPS");
//				alert.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..");
//				alert.setPositiveButton("Turn on GPS",
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//
//								GlobalVariables.isOfflineGPS = false;
//								mlocManager.requestLocationUpdates(
//										LocationManager.GPS_PROVIDER, 0,
//										(float) 0.01, listener);
//
//								mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
//										(float) 0.01, listener);
//
//
//								setCriteria();
//
//
//								/*
//								 * mlocManager.requestLocationUpdates(
//								 * LocationManager.NETWORK_PROVIDER, 0, (float)
//								 * 0.01, listener);
//								 */
//
////								Intent I = new Intent(
////										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
////								startActivity(I);
////								finish();
//
//							}
//						});
//
//				alert.show();
        }

//			else {
//
//
//
//				mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
//						(float) 0.01, listener);
//
//				mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
//						(float) 0.01, listener);
//
//
//			}
        //	}

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //switch (msg.what) {

                //	case UPDATE_ADDRESS:
                //	case UPDATE_LATLNG:
//					String[] LatLon = ((String) msg.obj).split("-");
//					TextView tv_Lat = (TextView) findViewById(R.id.tvLat);
//					TextView tv_Lon = (TextView) findViewById(R.id.tvLon);
//					TextView tvAcuracy = (TextView) findViewById(R.id.tvAcuracy);
//
//					tv_Lat.setText(LatLon[0]);
//					tv_Lon.setText(LatLon[1]);
//					tvAcuracy.setText(LatLon[2] + " meter");
//
                try {
                    //	accuracy=Double.parseDouble(LatLon[2]);
                } catch (Exception e) {
                }

                //	Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);

//					if (!isTimerStarted) {
//						startTimer();
//					}

                //	break;
                //}
            }
        };

    }

    public String setCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = mlocManager.getBestProvider(criteria, true);
        return provider;
    }

    public void startTimer() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        isTimerStarted = true;

    }


    public static Camera getCameraInstance() {
        // Camera c = null;
        try {

            int numberOfCameras = Camera.getNumberOfCameras();
            int cameraId = 0;
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    // Log.d(DEBUG_TAG, "Camera found");
                    cameraId = i;
                    break;
                }
            }
            return Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            return null;
        }
    }


    private void updateUILocation(Location location) {

        Message.obtain(
                mHandler,
                UPDATE_LATLNG,
                new DecimalFormat("#.0000000").format(location.getLatitude())
                        + "-"
                        + new DecimalFormat("#.0000000").format(location
                        .getLongitude()) + "-" + location.getAccuracy())
                .sendToTarget();

    }

//	private final LocationListener listener = new LocationListener() {

    //@Override
    //public void onLocationChanged(Location location) {
    // A new location update is received. Do something useful with it.
    // Update the UI with
    // the location update.

    //	if (GlobalVariables.isOfflineGPS == false) {
//				LastLocation = location;
//				updateUILocation(location);
//
//				if (location.getLatitude() > 0.0) {
//
//
//					long elapsedMillis = SystemClock.elapsedRealtime()
//							- chronometer.getBase();


    //if (location.getAccuracy()>0 && location.getAccuracy()<100) {


//					}
//			else {
//						takePhoto.setText("Wait for GPS to Stable");
//						takePhoto.setEnabled(false);
//					}

    //}

    //}
//			else {
////				LastLocation.setLatitude(0.0);
////				LastLocation.setLongitude(0.0);
////				updateUILocation(LastLocation);
//				Button takePhoto = (Button) findViewById(R.id.btnCapture);
//				takePhoto.setText("Take Photo");
//				takePhoto.setEnabled(true);
//			}

    //}

    //		@Override
//		public void onProviderDisabled(String provider) {
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//		}
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//
//		}
//	};
    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {

                Log.e("pic callback", "Yes");
                Log.d(TAG, "Start");
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);

                Matrix mat = new Matrix();
                mat.postRotate(90);
                Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0,
                        bmp.getWidth(), bmp.getHeight(), mat, true);

                setCameraImage(Utiilties.GenerateThumbnail(bMapRotate, 500, 500));

            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
            }
        }
    };

    ShutterCallback shutterCallback = new ShutterCallback() {
        @Override
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    PictureCallback rawCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    public void onCaptureClick(View view) {
        // System.gc();
        if (mCamera != null)
            mCamera.takePicture(shutterCallback, rawCallback, mPicture);

        Log.e("pic taken", "Yes");

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();

    }


    public void saveImage(Bitmap image) throws FileNotFoundException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UpasthitiImage");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        } else {
            File file = new File(mediaStorageDir.getAbsolutePath()//folder path
                    + File.separator
                    + "Image1.png"); //file name
            FileOutputStream fo = new FileOutputStream(file);
            if (image != null) {
                image.compress(Bitmap.CompressFormat.PNG, 100, fo);
            }
        }
    }

    private void setCameraImage(Bitmap bitmap) {


        String DisplayText1 = "", DisplayText2 = "", DisplayText3 = "", DisplayText4 = "";
        if (LastLocation == null) {
        } else {

        }


        Display getOrient = getWindowManager().getDefaultDisplay();
        int rotation = getOrient.getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                break;
            case Surface.ROTATION_270:
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

                break;
            case Surface.ROTATION_90:
                break;
            case Surface.ROTATION_180:
                break;
            default:
                break;
        }
        // bitmap = Utiilties.GenerateThumbnail(bitmap, 300, 400);
        bitmap = Utiilties.DrawText(CameraActivity1.this, bitmap, DisplayText1, DisplayText2,
                DisplayText3, DisplayText4);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byte_arr = stream.toByteArray();
        CompressedImageByteArray = byte_arr;

        bitmap.recycle();


        Intent returnIntent = new Intent();
        returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
//		returnIntent.putExtra("Lat",0.00);
//		returnIntent.putExtra("Lng",0.00);
        //returnIntent.putExtra("CapturedTime", Utiilties.getDateString());
        returnIntent.putExtra("KEY_PIC",
                Integer.parseInt(getIntent().getStringExtra("KEY_PIC")));
        // returnIntent.putExtra("ss", 0);
        setResult(RESULT_OK, returnIntent);
        Log.e("Set camera image", "Yes");

        finish();

    }

    public static byte[] getCompressedImage() {
        return CompressedImageByteArray;
    }

    public static void setCompressedImage(byte[] compressedImageByteArray) {
        CompressedImageByteArray = compressedImageByteArray;
    }

    public static Bitmap getCompressedBitmap() {
        return CompressedImage;
    }

    public static void setCompressedBitmap(Bitmap compressedImage) {
        CompressedImage = compressedImage;
    }


}
