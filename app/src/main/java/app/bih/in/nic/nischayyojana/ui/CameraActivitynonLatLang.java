package app.bih.in.nic.nischayyojana.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.ByteArrayOutputStream;
import java.util.List;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class CameraActivitynonLatLang extends Activity {

    private Camera mCamera = null;
    private CameraPreview mPreview;

    static Location LastLocation = null;
    LocationManager mlocManager = null;

    AlertDialog.Builder alert;

    private static final int UPDATE_ADDRESS = 1;
    private static final int UPDATE_LATLNG = 2;

    private Handler mHandler;
    private static byte[] CompressedImageByteArray;
    private static Bitmap CompressedImage;
    String BlkType="";
    String BlkTypeID="";
    static String isProfileIMG="n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameranonlatlang);
        if(getIntent().hasExtra("KEY_PIC"))
        {
            if(getIntent().getStringExtra("KEY_PIC").equalsIgnoreCase("0"))
            {
                isProfileIMG="y";
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        callCamera();
    }

    private void callCamera() {
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
        param.setJpegQuality(10);
        param.setPictureSize(sizes.get(iTarget).width, sizes.get(iTarget).height);
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

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

    }

    public static Camera getCameraInstance() {
        // Camera c = null;
        try {
            int numberOfCameras = Camera.getNumberOfCameras();
            int cameraId = 0;
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
//                if(numberOfCameras>1)
//                {
//                    if(isProfileIMG.equalsIgnoreCase("y"))
//                    {
//                        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
//                            Log.d("CAMERAID", "" + i + "oooo" + numberOfCameras);
//                            cameraId = i;
//                            break;
//                        }
//                    }
//                }
//                else {
//                    if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
//                        Log.d("CAMERAID", "" + i + "oooo" + numberOfCameras);
//                        cameraId = i;
//                        break;
//                    }
//                }

                if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    Log.d("CAMERAID", "" + i + "oooo" + numberOfCameras);
                    cameraId = i;
                    break;
                }
            }


            return Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.activity_camera, menu);
        return true;
    }

    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                Log.d(TAG, "Start");

                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);

                Matrix mat = new Matrix();
                mat.postRotate(90);

                Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0,
                        bmp.getWidth(), bmp.getHeight(), mat, true);

//				Bitmap bmp = BitmapFactory
//						.decodeByteArray(data, 0, data.length);

                setCameraImage(Utiilties.GenerateThumbnail(bMapRotate, 300, 400));
                //setCameraImage(Utiilties.GenerateThumbnail(bmp, 300, 400));

            } catch (Exception ex) {
                Log.d(TAG, "Camera Error:"+ex.getMessage());
            }

        }
    };
    private static final String TAG = "MyActivity";
    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    /**
     * Handles data for raw picture
     */
    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    public void onCaptureClick(View view) {
        // System.gc();
        try{
            mCamera.takePicture(shutterCallback, rawCallback, mPicture);

        }catch (Exception e){
            e.printStackTrace();
        }
        // mCamera.takePicture(null, null, mPicture);

    }

    private void setCameraImage(Bitmap bitmap) {

        String DisplayText1 = "", DisplayText2 = "", DisplayText3 = "",DisplayText4 = "";
        String photoType=getIntent().getStringExtra("KEY_PIC");
        //  "SELECT AadhaarPhoto,LandProofPhoto ,ConsumerPhoto,MeterPhoto FROM PendingUploads where SurveyID=?",
        if(photoType.equals("1"))
        {
            photoType="Aadhaar Card";
        }
        else if(photoType.equals("2"))
        {
            photoType="Land Proof";
        }
        else if(photoType.equals("3"))
        {
            photoType="Consumer Photo";
        }

        if (photoType != null) {
            DisplayText2 = "";  //photoType;
            DisplayText3 = Utiilties.getDateString("MMM dd, yyyy hh:mm a");

        } else {
            // DisplayText = "Lat : NA Lon : NA ";
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
        bitmap = Utiilties.GenerateThumbnail(bitmap, 300, 400);
        bitmap = Utiilties.DrawText(bitmap, DisplayText1, DisplayText2,
                DisplayText3,DisplayText4);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);

        byte[] byte_arr = stream.toByteArray();
        CompressedImageByteArray = byte_arr;

        bitmap.recycle();

        /*
         * ImageData RetData = new ImageData(); RetData.Image = CompressedImage;
         * RetData.CapturedLocation = LastLocation; RetData.CapturedDate = new
         * Date(0);
         *
         * RetData.ImageNumber = Integer.parseInt(getIntent().getStringExtra(
         * "KEY_PIC"));
         */
        Intent returnIntent = new Intent();
        returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
        returnIntent.putExtra("Lat", "0");
        returnIntent.putExtra("Lng", "0");
        returnIntent.putExtra("CapturedTime", Utiilties.getDateString());
        returnIntent.putExtra("KEY_PIC",
                Integer.parseInt(getIntent().getStringExtra("KEY_PIC")));

        setResult(RESULT_OK, returnIntent);

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
