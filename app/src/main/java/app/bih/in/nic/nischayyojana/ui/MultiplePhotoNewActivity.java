package app.bih.in.nic.nischayyojana.ui;

import android.Manifest;
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
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.util.Date;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.util.GlobalVariables;
import app.bih.in.nic.nischayyojana.util.MarshmallowPermission;
import app.bih.in.nic.nischayyojana.util.Utiilties;

public class MultiplePhotoNewActivity extends AppCompatActivity implements OnClickListener {

	private final static int CAMERA_PIC = 99;
	ImageView img1, img2, img3, img4;
	Button btnHome;
	int ThumbnailSize = 0;
	String id = "0";
	String WorkStatus = "";
	String AREA = "";

	LinearLayout lntxt12,lntxt34;
	String isIMGOneExist,isIMGTwoExist,isIMGThreeExist,isIMGFourExist;

	LocationManager mlocManager = null;
	static Location LastLocation = null;
	static double latitude = 0.0, longitude = 0.0;
	String tableName,_SchemeCode;
	private final int UPDATE_ADDRESS = 1;
	private final int UPDATE_LATLNG = 2;
	private ProgressDialog dialog;

	MarshmallowPermission permission;

	Bitmap bmp;
	byte[] byteImg1;
	byte[] byteImg2;
	byte[] byteImg3;
	byte[] byteImg4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();

		setContentView(R.layout.activity_multiple_photo);
		isIMGOneExist=isIMGTwoExist=isIMGThreeExist=isIMGFourExist="N";
		_SchemeCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_CODE", "");

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		img1 = (ImageView) findViewById(R.id.imageButton1);
		img2 = (ImageView) findViewById(R.id.imageButton2);
		img3 = (ImageView) findViewById(R.id.imageButton3);
		img4 = (ImageView) findViewById(R.id.imageButton4);
		lntxt12 = findViewById(R.id.lntxt12);
		lntxt34 = findViewById(R.id.lntxt34);
		btnHome = (Button) findViewById(R.id.btnOk);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
		btnHome.setOnClickListener(this);

		dialog = new ProgressDialog(this);

		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		}

		locationManager();

		if(getIntent().hasExtra("VIEW"))
		{
			btnHome.setText("होम स्क्रीन पर जायें");
			if (_SchemeCode.equalsIgnoreCase("1")) {
				tableName = "ReportPayJaltbl";
			}
			if (_SchemeCode.equalsIgnoreCase("2")) {
				tableName = "ReportGaliNali";
				lntxt12.setVisibility(View.GONE);
				lntxt34.setVisibility(View.GONE);
			}
		}
		else {
			if (_SchemeCode.equalsIgnoreCase("1")) {
				tableName = "PayJaltbl";
			}
			if (_SchemeCode.equalsIgnoreCase("2")) {
				tableName = "GaliNali";
				lntxt12.setVisibility(View.GONE);
				lntxt34.setVisibility(View.GONE);
			}
		}
		id = getIntent().getStringExtra("ID");

		if (id == null) {
			id = "0";
		}

		if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
			ThumbnailSize = displaymetrics.widthPixels / 2;
			img1.getLayoutParams().height = ThumbnailSize;
			img3.getLayoutParams().height = ThumbnailSize;

		} else {

			ThumbnailSize = displaymetrics.widthPixels / 4;
			img1.getLayoutParams().height = img2.getLayoutParams().height = img3
					.getLayoutParams().height = img4.getLayoutParams().height = ThumbnailSize;

		}

		SQLiteHelper placeData = new SQLiteHelper(this);
		SQLiteDatabase db = placeData.getReadableDatabase();
		// int HIDID = getIntent().getIntExtra("KEY_HIDID", 0);
		Cursor cursor=null;
		cursor = db
				.rawQuery(
						"SELECT Photo1, Photo2, Photo3,Photo4  FROM " + tableName + " where id=?",
						new String[]{String.valueOf(id)});

		if (cursor.moveToNext()) {
			if(getIntent().hasExtra("VIEW"))
			{
				//userphoto=userDetails.getUserPhoto();
				if(!cursor.isNull(0)){
					if(!cursor.getString(0).equalsIgnoreCase("N")) {
						byte[] decodedString = Base64.decode(cursor.getString(0), Base64.DEFAULT);

						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						img1.setImageBitmap(decodedByte);
					}
				}
				if(!cursor.isNull(1)){

					if(!cursor.getString(1).equalsIgnoreCase("N")) {
						byte[] decodedString = Base64.decode(cursor.getString(1), Base64.DEFAULT);

						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						img2.setImageBitmap(decodedByte);
					}
				}
				if(!cursor.isNull(2)){

					if(!cursor.getString(2).equalsIgnoreCase("N")) {
						byte[] decodedString = Base64.decode(cursor.getString(2), Base64.DEFAULT);

						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						img3.setImageBitmap(decodedByte);
					}

				}
				if(!cursor.isNull(3)){


					if(!cursor.getString(3).equalsIgnoreCase("N")) {
						byte[] decodedString = Base64.decode(cursor.getString(3), Base64.DEFAULT);

						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						img4.setImageBitmap(decodedByte);
					}
				}
			}
			else
			{
				if (!cursor.isNull(0)) {

					byte[] imgData = cursor.getBlob(0);
					Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
							imgData.length);
					img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,ThumbnailSize, ThumbnailSize));
					img1.setOnClickListener(this);
					isIMGOneExist="Y";
				} else {
					img1.setOnClickListener(this);
					img2.setEnabled(true);
					img3.setEnabled(true);
					img4.setEnabled(true);
				}

				if (!cursor.isNull(1)) {
					byte[] imgData = cursor.getBlob(1);

					Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
							imgData.length);
					img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp,ThumbnailSize, ThumbnailSize));
					img2.setOnClickListener(this);
					isIMGTwoExist="Y";

				} else {
					img2.setOnClickListener(this);
					img3.setEnabled(true);
					img4.setEnabled(true);
				}

				if (!cursor.isNull(2)) {
					byte[] imgData = cursor.getBlob(2);

					Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
							imgData.length);
					img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
							ThumbnailSize, ThumbnailSize));
					img3.setOnClickListener(this);
					isIMGThreeExist="Y";

				} else {
					img3.setOnClickListener(this);
					img4.setEnabled(true);
				}

				if (!cursor.isNull(3)) {
					byte[] imgData = cursor.getBlob(3);

					Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
							imgData.length);
					img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
							ThumbnailSize, ThumbnailSize));
					img4.setOnClickListener(this);
					isIMGFourExist="Y";

				} else
					img4.setOnClickListener(this);
			}
		}
		cursor.close();
		db.close();
		if(getIntent().hasExtra("VIEW"))
		{
			img1.setOnClickListener(this);
			img2.setOnClickListener(this);
			img3.setOnClickListener(this);
			img4.setOnClickListener(this);
		}

//		if(getIntent().hasExtra("EDIT"))
//		{
//			if(getIntent().getStringExtra("EDIT").equalsIgnoreCase("yes"))
//			{
//				img1.setOnClickListener(this);
//				img2.setOnClickListener(this);
//				img3.setOnClickListener(this);
//				img4.setOnClickListener(this);
//
//				img1.setEnabled(true);
//				img2.setEnabled(true);
//				img3.setEnabled(true);
//				img4.setEnabled(true);
//			}
//		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_multiple_photo, menu);
		return true;
	}

	@Override
	public void onClick(View view) {

		if (view.equals(btnHome)) {
			if(getIntent().hasExtra("VIEW"))
			{
				finish();
			}
			else {
				if (isIMGOneExist.equalsIgnoreCase("Y")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.home);
                    builder.setCancelable(false);
                    builder.setTitle("डाटा सेव");
                    builder.setMessage("डाटा सेव हो गया | ओके पर क्लिक करे होम स्क्रीन पैर जाने के लीये ")

                            .setCancelable(false)
                            .setPositiveButton("[ ओके ]", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.dismiss();
                                    dialog.cancel();
                                    Intent iUserHome = new Intent(getApplicationContext(), UserHomeNewActivity.class);
                                    iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    startActivity(iUserHome);
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();

				} else {
					final AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setIcon(R.drawable.front_camera);
					builder.setCancelable(false);
					builder.setTitle(" फोटो ?");
					builder.setMessage("कृपया कम से कम एक फोटो ले ")

							.setCancelable(false)
							.setPositiveButton("[ ठीक ]", new DialogInterface.OnClickListener() {
								public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
									dialog.dismiss();
									dialog.cancel();
								}
							});
//							.setNegativeButton("[ बाद में ]", new DialogInterface.OnClickListener() {
//								public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//									Intent iUserHome = new Intent(getApplicationContext(), UserHomeActivity.class);
//									iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									finish();
//									startActivity(iUserHome);
//								}
//							});
					final AlertDialog alert = builder.create();
					alert.show();
				}
			}
		} else {

			final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
				if(GlobalVariables.glocation != null){
					if(checkCameraPermission()){
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

					}else{
						requestCameraPermission();
						//Toast.makeText(this, "Pleasa allow camera permission", Toast.LENGTH_SHORT).show();
					}
				}else{
					locationManager();
				}

			}else{
				buildAlertMessageNoGps();
			}
		}

	}

	public Boolean checkCameraPermission(){
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
			return false;
		}
		return true;
	}

	private void requestCameraPermission(){
		ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
	}

	public Bitmap getTextImage(Bitmap bmp){
		Date d = new Date(GlobalVariables.glocation.getTime());
		String dat = d.toLocaleString();
		Bitmap bitmap2 = Utiilties.DrawText(this, bmp, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
				, "Accuracy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "Time:" + dat);

		return bitmap2;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		SQLiteHelper placeData = new SQLiteHelper(MultiplePhotoNewActivity.this);
		SQLiteDatabase db = placeData.getWritableDatabase();
		ContentValues values = new ContentValues();
		String whereArg = "id=" + String.valueOf(id);
		// switch (data.getIntExtra("ss", 0)) {
		String[] whereArgs;

		switch(requestCode){
			case 1:
				if(resultCode == RESULT_OK){
					Bundle extras = data.getExtras();
					bmp = (Bitmap) extras.get("data");

					//setCameraImage(Utiilties.GenerateThumbnail(bitmap2, 500, 500));
					Bitmap photo1 = getTextImage(bmp);
					img1.setImageBitmap(photo1);

					img1.setOnClickListener(null);
					img2.setEnabled(true);
					isIMGOneExist = "Y";

					values.put("Photo1", Utiilties.convertBitmapToByteArray(photo1));
					values.put("Latitude", String.valueOf(GlobalVariables.glocation.getLatitude()));
					values.put("Longitude", String.valueOf(GlobalVariables.glocation.getLongitude()));

					whereArgs = new String[]{String.valueOf(id)};

					db.update(tableName, values, "id=?", whereArgs);
				}
				break;
			case 2:
				if(resultCode == RESULT_OK) {
					Bundle extras = data.getExtras();

					Bitmap photo2 = getTextImage((Bitmap) extras.get("data"));

					values.put("Photo2", Utiilties.convertBitmapToByteArray(photo2));
					whereArgs = new String[] { String.valueOf(id) };
					db.update(tableName, values, "id=?", whereArgs);

					img2.setImageBitmap(photo2);
					img2.setOnClickListener(null);
					img3.setEnabled(true);
					isIMGTwoExist="Y";
				}
				break;

			case 3:
				if(resultCode == RESULT_OK) {
					Bundle extras = data.getExtras();
					Bitmap photo3 = getTextImage((Bitmap) extras.get("data"));

					values.put("Photo3", Utiilties.convertBitmapToByteArray(photo3));
					whereArgs = new String[] { String.valueOf(id) };
					db.update(tableName, values, "id=?", whereArgs);

					img3.setImageBitmap(photo3);
					img3.setOnClickListener(null);
					img4.setEnabled(true);
					isIMGThreeExist="Y";
				}
				break;

			case 4:
				if(resultCode == RESULT_OK) {
					Bundle extras = data.getExtras();
					Bitmap photo4 = getTextImage((Bitmap) extras.get("data"));

					values.put("Photo4", Utiilties.convertBitmapToByteArray(photo4));
					whereArgs = new String[] { String.valueOf(id) };
					db.update(tableName, values, "id=?", whereArgs);

					img4.setImageBitmap(photo4);
					img4.setOnClickListener(null);
					isIMGFourExist="Y";
				}
				break;
		}

	}


	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.bihgov);
		builder.setTitle("GPS ?");
		builder.setMessage("(GPS)जीपीएस बंद है\nस्थान के अक्षांश और देशांतर प्राप्त करने के लिए कृपया जीपीएस चालू करें")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("[(GPS) जीपीएस चालू करे ]", new DialogInterface.OnClickListener() {
					public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				})
				.setNegativeButton("[ बंद करें ]", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
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
			this.dialog.setCanceledOnTouchOutside(false);
			this.dialog.setMessage("ट्रैकिंग लोकेशन...");
			this.dialog.show();

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
			if (Utiilties.isGPSEnabled(MultiplePhotoNewActivity.this)) {

				LastLocation = location;
				GlobalVariables.glocation = location;
				updateUILocation(GlobalVariables.glocation);
				//   if (getIntent().getStringExtra("KEY_PIC").equals("1")) {
				if (location.getLatitude() > 0.0) {
					//dialog = new ProgressDialog(MultiplePhotoNewActivity.this);
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

	@Override
	protected void onResume() {
		super.onResume();

		permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
		permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
	}
}
