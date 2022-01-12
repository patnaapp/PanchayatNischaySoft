package app.bih.in.nic.nischayyojana.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;


import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.MarshmallowPermission;
import app.bih.in.nic.nischayyojana.util.Utiilties;


public class Camera extends AppCompatActivity {

    MarshmallowPermission permission;
    Button btnCamType;
    Button takePhoto;
    ProgressBar progress_finding_location;
    boolean init;
    boolean backCam = true;
    int camType;
    FrameLayout preview;
    //  GoogleApiClient googleApiClient, mGoogleApiClint;
    //  private GoogleMap mMap;
    static double latitude = 0.0, longitude = 0.0;
    Button buttonrotate, btnSave;
    public static int cameraid = 0;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //  private LocationRequest mLocationRequest;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 300; // 3 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    Bitmap bMapRotate;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        init = false;
        permission = new MarshmallowPermission(this, Manifest.permission.CAMERA);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }

       /* permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }*/

        super.onResume();

    }


    private android.hardware.Camera mCamera;
    private CameraPreview mPreview;

    static Location LastLocation = null;
    LocationManager mlocManager = null;

    AlertDialog.Builder alert;


    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;
    private static final String TAG = "MyActivity";
    private static byte[] CompressedImageByteArray;
    private static Bitmap CompressedImage;
    private boolean isTimerStarted = false;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcamera);
        takePhoto=(Button)findViewById(R.id.btnCapture);
        btnCamType = (Button) findViewById(R.id.btnCamType);
        // buttonrotate=(Button)findViewById(R.id.buttonrotate);
        btnSave = (Button) findViewById(R.id.btnSave);

       /* if (Utiilties.isfrontCameraAvalable() && getIntent().getStringExtra("KEY_PIC").equals("2")) {
            camType = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            camType = Camera.CameraInfo.CAMERA_FACING_BACK;
        }*/
        camType = android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
        preview = (FrameLayout) findViewById(R.id.camera_preview);


        //takePhoto.setEnabled(false);


        btnCamType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mCamera != null) {
                    mCamera.stopPreview();


                }


                if (camType == android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK) {
                    camType = android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

                } else {
                    camType = android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;

                }

                preview.removeAllViews();

                initializeCamera(camType);


            }

        });
       /* buttonrotate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraid==0){
                    cameraid=1;
                }else if(cameraid==1){
                    cameraid=2;
                }else if(cameraid==2){
                    cameraid=3;
                }
                onrotate(bMapRotate ,2);

            }
        });*/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmapBitmap2 = bMapRotate;

                Date d = new Date(GlobalVariables.glocation.getTime());
                String dat = d.toLocaleString();
                Bitmap bitmap2 = Utiilties.DrawText(Camera.this, bmapBitmap2, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                        , "Accurecy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat);
                latitude = GlobalVariables.glocation.getLatitude();
                longitude = GlobalVariables.glocation.getLongitude();
                setCameraImage(Utiilties.GenerateThumbnail(bitmap2, 500, 500));
                // new CustomeDialogClass(CameraActivity.this,bmapBitmap2,Integer.parseInt(getIntent().getStringExtra("KEY_PIC"))).show();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(shutterCallback, rawCallback, mPicture);
            }
        });
    }


    private void initializeCamera(int camType) {

        init = true;

        isTimerStarted = false;

        mCamera = getCameraInstance(camType);
        android.hardware.Camera.Parameters param;
        if (mCamera != null) {
            param = mCamera.getParameters();


            List<android.hardware.Camera.Size> sizes = param.getSupportedPictureSizes();
            int iTarget = 0;
            for (int i = 0; i < sizes.size(); i++) {
                android.hardware.Camera.Size size = sizes.get(i);
			/*if (size.width < 1000) {
				iTarget = i;
				break;
			}*/


                if (size.width >= 1024 && size.width <= 1280) {
                    iTarget = i;
                    break;
                } else {
                    if (size.width < 1024) {
                        iTarget = i;

                    }
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
                    mCamera.setDisplayOrientation(0);

                    break;
                case Surface.ROTATION_270:
                    mCamera.setDisplayOrientation(90);
                    break;
                default:
                    break;
            }
            try {

                mPreview = new CameraPreview(this, mCamera);
                preview.addView(mPreview);


            } catch (Exception e) {
                finish();
            }
            takePhoto.setText(" Take Photo ");
            // locationManager();
        }

    }


    public static android.hardware.Camera getCameraInstance(int cameraType) {
        // Camera c = null;
        try {

            int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
            int cameraId = 0;
            for (int i = 0; i < numberOfCameras; i++) {
                android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
                android.hardware.Camera.getCameraInfo(i, info);
                if (info.facing == cameraType) {
                    // Log.d(DEBUG_TAG, "Camera found");
                    cameraId = i;
                    break;
                }
            }

            return android.hardware.Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            return null;
        }
    }

    android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            try {

                Log.e("pic callback", "Yes");
                Log.d(TAG, "Start");
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);

                Matrix mat = new Matrix();
                if (camType == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mat.postRotate(-90);

                } else mat.postRotate(90);

                bMapRotate = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
                takePhoto.setVisibility(View.GONE);
                Bitmap bmapBitmap2 = bMapRotate;

                // Date d = new Date(GlobalVariables.glocation.getTime());
                // String dat = d.toLocaleString();
                // Bitmap bitmap2 = Utiilties.DrawText(CameraActivity.this, bmapBitmap2, /*"Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                //         , "Accurecy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat*/);
                //   latitude = GlobalVariables.glocation.getLatitude();
                // longitude = GlobalVariables.glocation.getLongitude();

                setCameraImage(Utiilties.GenerateThumbnail(bmapBitmap2, 500, 500));
                //buttonrotate.setVisibility(View.VISIBLE);
                //btnSave.setVisibility(View.VISIBLE);
                // onrotate(bMapRotate,2);
                //changing
                /*Bitmap bmapBitmap2 = bMapRotate;
                Date d = new Date(GlobalVariables.glocation.getTime());
                String dat = d.toLocaleString();
                Bitmap bitmap2 = Utiilties.DrawText(CameraActivity.this, bmapBitmap2, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                        , "Accurecy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat);
                latitude=GlobalVariables.glocation.getLatitude();
                longitude=GlobalVariables.glocation.getLongitude();
                setCameraImage(Utiilties.GenerateThumbnail(bitmap2, 500, 500));*/
                // new CustomeDialogClass(CameraActivity.this,bmapBitmap2,Integer.parseInt(getIntent().getStringExtra("KEY_PIC"))).show();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    };

    android.hardware.Camera.ShutterCallback shutterCallback = new android.hardware.Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    /**
     * Handles data for raw picture
     */
    android.hardware.Camera.PictureCallback rawCallback = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    public void onCaptureClick(View view) {
        // System.gc();

        if (mCamera != null)
            mCamera.takePicture(shutterCallback, rawCallback, mPicture);

        Log.e("pic taken", "Yes");

        //chronometer.setBase(SystemClock.elapsedRealtime());
        // chronometer.stop();
        // mCamera.takePicture(null, null, mPicture);

    }

    public void onrotate(Bitmap bitmap, int i) {
        // System.gc();

        if (i == 1) {
            Bitmap bmp = bitmap;
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            mCamera.setDisplayOrientation(90);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
            bMapRotate = bmp;
        } else if (i == 2) {
            Bitmap bmp = bitmap;
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            mCamera.setDisplayOrientation(270);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
            bMapRotate = bmp;
        }

    }

    private void setCameraImage(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);


        byte[] byte_arr = stream.toByteArray();
        CompressedImageByteArray = byte_arr;

        bitmap.recycle();


        Intent returnIntent = new Intent();
        returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

    }


    private boolean checkLocationPermission() {
        if (!hasLocationPermission()) {
            Log.e("Tuts+", "Does not have location permission granted");
            requestLocationPermission();
            return false;
        }

        return true;
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private final static int REQUEST_PERMISSION_RESULT_CODE = 42;

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                Camera.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSION_RESULT_CODE);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case UPDATE_ADDRESS:
                case UPDATE_LATLNG:
                    String[] LatLon = ((String) msg.obj).split("-");
                    TextView tv_Lat = (TextView) findViewById(R.id.tvLat);
                    TextView tv_Lon = (TextView) findViewById(R.id.tvLon);

                    tv_Lat.setText("" + latitude);
                    tv_Lon.setText("" + longitude);
                    Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);

                    if (!isTimerStarted) {

                    }

                    break;
            }
        }
    };





    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    }
