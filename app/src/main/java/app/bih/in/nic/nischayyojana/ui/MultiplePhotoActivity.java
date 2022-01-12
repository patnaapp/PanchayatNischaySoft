package app.bih.in.nic.nischayyojana.ui;

import app.bih.in.nic.nischayyojana.R;
import app.bih.in.nic.nischayyojana.db.SQLiteHelper;
import app.bih.in.nic.nischayyojana.util.Utiilties;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MultiplePhotoActivity extends Activity implements OnClickListener {

	private final static int CAMERA_PIC = 99;
	ImageView img1, img2, img3, img4;
	Button btnHome;
	int ThumbnailSize = 0;
	String id = "0";
	String WorkStatus = "";
	String AREA = "";

	LinearLayout lntxt12,lntxt34;
	String isIMGOneExist,isIMGTwoExist,isIMGThreeExist,isIMGFourExist;

	String tableName,_SchemeCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

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

			if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


				buildAlertMessageNoGps();
			}
			if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

				Intent iCamera = new Intent(getApplicationContext(),CameraActivity.class);
				// iCamera.putExtra("KEY_WORKID",
				// getIntent().getStringExtra("KEY_WORKID"));
				if (view.equals(img1))
					iCamera.putExtra("KEY_PIC", "1");

				else if (view.equals(img2))
					iCamera.putExtra("KEY_PIC", "2");
				else if (view.equals(img3))
					iCamera.putExtra("KEY_PIC", "3");
				else if (view.equals(img4))
					iCamera.putExtra("KEY_PIC", "4");

				startActivityForResult(iCamera, CAMERA_PIC);

			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)

	{

		switch (requestCode) {

			case CAMERA_PIC:

				if (resultCode == RESULT_OK) {

					byte[] imgData = data.getByteArrayExtra("CapturedImage");

					SQLiteHelper placeData = new SQLiteHelper(
							MultiplePhotoActivity.this);
					SQLiteDatabase db = placeData.getWritableDatabase();
					ContentValues values = new ContentValues();
					String whereArg = "id=" + String.valueOf(id);
					// switch (data.getIntExtra("ss", 0)) {
					String[] whereArgs;
					switch (data.getIntExtra("KEY_PIC", 0)) {
						case 1:

							//if(!String.valueOf(data.getStringExtra("Lat")).contains(".00000")) {
								values.put("Photo1", imgData);
								values.put("Latitude", String.valueOf(data.getStringExtra("Lat")));
								values.put("Longitude", String.valueOf(data.getStringExtra("Lng")));

								whereArgs = new String[]{String.valueOf(id)};

								db.update(tableName, values, "id=?", whereArgs);

								Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);

								img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize));
								img1.setOnClickListener(null);
								img2.setEnabled(true);
								isIMGOneExist = "Y";
//							}
//							else
//							{
//
//							}
							break;
						case 2:
							values.put("Photo2", imgData);

							whereArgs = new String[] { String.valueOf(id) };
							db.update(tableName, values, "id=?", whereArgs);

							img2.setImageBitmap(Utiilties.GenerateThumbnail(
									BitmapFactory.decodeByteArray(imgData, 0,
											imgData.length), ThumbnailSize,
									ThumbnailSize));
							img2.setOnClickListener(null);
							img3.setEnabled(true);
							isIMGTwoExist="Y";
							break;
						case 3:
							values.put("Photo3", imgData);

							whereArgs = new String[] { String.valueOf(id) };
							db.update(tableName, values, "id=?", whereArgs);

							img3.setImageBitmap(Utiilties.GenerateThumbnail(
									BitmapFactory.decodeByteArray(imgData, 0,
											imgData.length), ThumbnailSize,
									ThumbnailSize));
							img3.setOnClickListener(null);
							img4.setEnabled(true);
							isIMGThreeExist="Y";
							break;
						case 4:
							values.put("Photo4", imgData);
							whereArgs = new String[] { String.valueOf(id) };
							db.update(tableName, values, "id=?", whereArgs);
							img4.setImageBitmap(Utiilties.GenerateThumbnail(
									BitmapFactory.decodeByteArray(imgData, 0,
											imgData.length), ThumbnailSize,
									ThumbnailSize));
							img4.setOnClickListener(null);
							isIMGFourExist="Y";
							break;
					}

					db.close();
				}

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

}
