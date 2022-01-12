package app.bih.in.nic.nischayyojana.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import app.bih.in.nic.nischayyojana.R;


public class Utiilties {

	public Utiilties() {
		// TODO Auto-generated constructor stub
	}

	public static void ShowMessage(Context context, String Title, String Message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(Title);
		alertDialog.setMessage(Message);
		alertDialog.show();
	}
	public static String getFormatedDateString(Date d) {
		SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault());

		String newDateStr = postFormater.format(d);
		//remove time part
		String sd=newDateStr.replace("12:00 AM","");
		return sd;
	}
	public static void showAlert(final Context context) {

		if (Utiilties.isOnline(context) == false) {
			AlertDialog.Builder ab = new AlertDialog.Builder(context);
			ab.setCancelable(false);
			ab.setMessage(Html
					.fromHtml("<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection OR Continue With Off-line Mode..\nTo Turn ON Network Connection Press Yes Button else To Continue With Off-Line Mode Press No Button..</font>"));
			ab.setPositiveButton("Turn On Network Connection",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							GlobalVariables.isOffline = false;
							Intent I = new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							context.startActivity(I);
						}
					});
			ab.setNegativeButton("Continue Offline",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {

							GlobalVariables.isOffline = true;
						}
					});

			ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
			ab.show();
		} else {

			GlobalVariables.isOffline = false;
			// new CheckUpdate().execute();
		}

	}

	public static boolean isOnline(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		
		return (networkInfo != null && networkInfo.isConnected() == true);
	}

	public static Bitmap GenerateThumbnail(Bitmap imageBitmap,
			int THUMBNAIL_HEIGHT, int THUMBNAIL_WIDTH) {

		Float width = new Float(imageBitmap.getWidth());
		Float height = new Float(imageBitmap.getHeight());
		Float ratio = width / height;
		Bitmap CompressedBitmap = Bitmap.createScaledBitmap(imageBitmap,
				(int) (THUMBNAIL_HEIGHT * ratio), THUMBNAIL_HEIGHT, false);
		return CompressedBitmap;
	}

	public static Bitmap DrawText(Activity activity, Bitmap mBitmap, String displaytext1,
								  String displaytext2, String displaytext3, String displaytext4) {
		Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
		// create a canvas on which to draw
		Canvas canvas = new Canvas(bmOverlay);

		int margin = 5;
		Paint paint = new Paint();
		Paint.FontMetrics fm = new Paint.FontMetrics();
		paint.setColor(activity.getResources().getColor(R.color.text_forecolor));
		paint.setTextSize(11);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setFakeBoldText(false);
		paint.setShadowLayer(0, 0, 0, Color.BLACK);

		// if the background image is defined in main.xml, omit this line
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		canvas.drawText(displaytext1, 0, mBitmap.getHeight() - 35, paint);
		canvas.drawText(displaytext2, 0, mBitmap.getHeight() - 22, paint);

		//canvas.drawText(displaytext3, 0, mBitmap.getHeight() - 22, paint);

		paint.setTextSize(10);
		canvas.drawText(displaytext4, 0, mBitmap.getHeight() - 10, paint);
		// set the bitmap into the ImageView
		return bmOverlay;
	}

	public static String getCurrentDateWithTime() throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("MMM d,yyyy HH:mm");
		Date date=null;
		date=f.parse(getDateString());
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM d,yyyy HH:mm a");
		String dateString = formatter.format(date);
		return dateString;
	}
	public static Bitmap DrawText(Bitmap mBitmap, String displaytext1,
			String displaytext2, String displaytext3, String displaytext4) {
		Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
		// create a canvas on which to draw
		Canvas canvas = new Canvas(bmOverlay);

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(16);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setFakeBoldText(false);
		paint.setShadowLayer(1, 0, 0, Color.BLACK);

		// if the background image is defined in main.xml, omit this line
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		canvas.drawText(displaytext1, 10, mBitmap.getHeight() - 30, paint);
		canvas.drawText(displaytext2, 10, mBitmap.getHeight() - 10, paint);

		canvas.drawText(displaytext3, 10, mBitmap.getHeight() - 50, paint);

		canvas.drawText(displaytext4, 10, mBitmap.getHeight() - 70, paint);
		// set the bitmap into the ImageView
		return bmOverlay;
	}

	public static Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (Exception ex) {
			return null;
		}
	}

	public static String getDateString() {
//		SimpleDateFormat postFormater = new SimpleDateFormat(
//				"MMMM dd, yyyy hh:mm a");

		SimpleDateFormat postFormater = new SimpleDateFormat(
				"dd-MM-yyyy");
		String newDateStr = postFormater.format(Calendar.getInstance()
				.getTime());
		return newDateStr;
	}

	public static String getDateString(String Formats) {
		SimpleDateFormat postFormater = new SimpleDateFormat(Formats);

		String newDateStr = postFormater.format(Calendar.getInstance()
				.getTime());
		return newDateStr;
	}
	public static boolean isGPSEnabled (Context mContext){
		LocationManager locationManager = (LocationManager)
				mContext.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	public static boolean isfrontCameraAvalable(){
		int numCameras= Camera.getNumberOfCameras();
		for(int i=0;i<numCameras;i++){
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if(Camera.CameraInfo.CAMERA_FACING_FRONT == info.facing){
				return true;
			}
		}
		return false;
	}

	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static boolean checkPermission(final Context context)
	{
		int currentAPIVersion = Build.VERSION.SDK_INT;
		if(currentAPIVersion>= Build.VERSION_CODES.M)
		{
			if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
					alertBuilder.setCancelable(true);
					alertBuilder.setTitle("Permission necessary");
					alertBuilder.setMessage("External storage permission is necessary");
					alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
						public void onClick(DialogInterface dialog, int which) {
							ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
						}
					});
					AlertDialog alert = alertBuilder.create();
					alert.show();
				} else {
					ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
				}
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public static int getMonthFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.MONTH)+1;
		}
		return result;
	}

	public static int getYearFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.YEAR);
		}
		return result;
	}

	public static void hideKeyboard(Activity activity) {
		View v = activity.getWindow().getCurrentFocus();
		if (v != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public static byte[] convertBitmapToByteArray(Bitmap bmp){
		//Bitmap bmp = intent.getExtras().get("data");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
		byte[] byteArray = stream.toByteArray();
		//bmp.recycle();
		return byteArray;
	}
}
