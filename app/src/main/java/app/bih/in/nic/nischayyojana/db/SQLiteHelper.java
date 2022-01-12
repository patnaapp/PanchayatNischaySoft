package app.bih.in.nic.nischayyojana.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import app.bih.in.nic.nischayyojana.entity.BankData;
import app.bih.in.nic.nischayyojana.entity.FarmerInfo;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.UserInfo;

/** Helper to the database, manages versions and creation */
public class SQLiteHelper extends SQLiteOpenHelper {
	//private static final String DATABASE_NAME = "PACSDB1";
	private static final String DATABASE_NAME = "PACSDB2";
	private static final int DATABASE_VERSION = 2;
	Context context;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {


//		db.execSQL("CREATE TABLE [UserDetail] ([UserID] TEXT PRIMARY KEY,[UserName] TEXT NOT NULL,[UserPassword] Text  NOT NULL, [Role] Text NULL, [BankCode] Text NULL, [DistCode] Text  NULL,[BlockCode] Text,[PanchayatCode] Text,[PACSBankID] Text, [LastVisitedDate] TEXT, IMEI Text ) ");
//		db.execSQL("CREATE TABLE [PendingUpload_FromFarmers]"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, DetailID Text NULL,"
//				+ " DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, PacsCode TEXT, FYearID TEXT, ProcQuantity TEXT, NoOfFarmers TEXT,"
//				+ " EntryDate TEXT )");
//		db.execSQL("CREATE TABLE [PendingUpload_PaddyToSFC]"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, DetailID Text NULL,"
//				+ " DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, PacsCode TEXT, FYearID TEXT,"
//				+ " SupplyToSFC TEXT, EntryDate TEXT )");
//
//		db.execSQL("CREATE TABLE [PendingUpload_PaddyToMills]"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, DetailID Text NULL,"
//				+ " DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, PacsCode TEXT, FYearID TEXT,"
//				+ " SupplyToRM_MillId TEXT, SupplyToRM_MillName TEXT, SupplyToRM_Address TEXT, SupplyToRM_PaddyQty TEXT, EntryDate TEXT )");
//		db.execSQL("CREATE TABLE [PendingUpload_CMRFromMills]"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, DetailID Text NULL,"
//				+ " DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, PacsCode TEXT, FYearID TEXT,"
//				+ " RcptFromRiceMill_MillId TEXT, RcptFromRiceMill_MillName TEXT, RcptFromRiceMill_Address TEXT, RcptFromRiceMill_CMRQty TEXT, EntryDate TEXT )");
//		db.execSQL("CREATE TABLE [PendingUpload_CMRToSFC]"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, DetailID Text NULL,"
//				+ " DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, PacsCode TEXT, FYearID TEXT,"
//				+ " SupToSFC_CMR_CMRQty TEXT, EntryDate TEXT )");
//
////		db.execSQL("CREATE TABLE [DISTRICT] (DISTRICTID TEXT ,DISTRICTNAME TEXT, PACSBANKID TEXT ) ");
//		db.execSQL("CREATE TABLE [FYEAR] ([FYID] TEXT ,[FYName] TEXT,[STATUS] TEXT )");
//
//		db.execSQL("CREATE TABLE RICEMILL (RiceMillID TEXT,  DistCode TEXT, BlockCode TEXT, PanchayatCode TEXT, RiceMillName TEXT, Address TEXT)");
		Log.e("SQLiteHelper","onCreate");

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 *
	 * @return true if it exists, false if it doesn't
	 */
	public boolean checkDataBase(String db) {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = "data/data/com.data.pack/databases/" + db;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		} catch (Exception e) {

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;
		ClearAllTable(db);
		onCreate(db);
		if (oldVersion == 1) {
			Log.d("New Version", "Data can be upgraded");
		}

		Log.d("Sample Data", "onUpgrade	: " + newVersion);
	}

	public void ClearAllTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS PendingUpload");
		db.execSQL("DROP TABLE IF EXISTS UserDetail");

		db.execSQL("DROP TABLE IF EXISTS DISTRICT");
		db.execSQL("DROP TABLE IF EXISTS FYEAR");
	}

	public UserInfo getUserDetails(String userId, String pass) {

		// PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
		UserInfo userInfo = null;

		try {

			SQLiteDatabase db = this.getReadableDatabase();

			String[] params = new String[] { userId.trim(), pass };

			Cursor cur = db
					.rawQuery(
							"Select * from UserDetail WHERE UserId=? and UserPassword=?",
							params);
			int x = cur.getCount();
			// db1.execSQL("Delete from UserDetail");

			while (cur.moveToNext()) {

				userInfo = new UserInfo();
				userInfo.set_UserID(cur.getString(cur.getColumnIndex("UserID")));
				userInfo.set_UserName(cur.getString(cur
						.getColumnIndex("UserName")));
				userInfo.set_Password(cur.getString(cur
						.getColumnIndex("UserPassword")));
				userInfo.set_DistCode(cur.getString(cur
						.getColumnIndex("DistCode")));
				userInfo.set_BlockCode(cur.getString(cur
						.getColumnIndex("BlockCode")));
				userInfo.set_PanchayatCode(cur.getString(cur
						.getColumnIndex("PanchayatCode")));
				userInfo.set_Role(cur.getString(cur
						.getColumnIndex("Role")));
//				userInfo.set_BankCode(cur.getString(cur
//						.getColumnIndex("BankCode")));
//				userInfo.set_PACSBankID(cur.getString(cur
//						.getColumnIndex("PACSBankID")));
				userInfo.set_IMEI(cur.getString(cur
						.getColumnIndex("IMEI")));
				userInfo.set_isAuthenticated(true);

				// ,[LastVisitedDate] Text NULL,[Photo] Text NULL,[Designation]
				// Text NULL,[TotalPending] Text NULL,[BlockCode] Text NULL)");

			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
			userInfo = null;
		}
		return userInfo;

	}

	public long getUserCount() {

		long x = 0;
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("Select * from UserDetail", null);

			x = cur.getCount();

			cur.close();
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return x;
	}

	public long insertUserDetails(UserInfo result) {

		long c = 0;
		try {

			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("Delete from UserDetail");

			ContentValues values = new ContentValues();

			values.put("UserId", result.get_UserID());
			values.put("UserName", result.get_UserName());
			values.put("UserPassword", result.get_Password());
			values.put("DistCode", result.get_DistCode());
			values.put("BlockCode", result.get_BlockCode());
			values.put("PanchayatCode", result.get_PanchayatCode());
			values.put("Role", result.get_Role());
//			values.put("BankCode", result.get_BankCode());
//			values.put("PACSBankID", result.get_PACSBankID());
			values.put("IMEI", result.get_IMEI());
//			values.put("WardCode",result.get_WardCode());


			c = db.insert("UserDetail", null, values);

			db.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}
	public long insertFarmerDetails(FarmerInfo result) {

		long c = 0;
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			//db.execSQL("Delete from FarmerDetail");

			ContentValues values = new ContentValues();
			String[] whereArgs;
			values.put("UserID", result.get_UserID());
			values.put("FarmerName", result.get_UserName());
			values.put("FarmerFatherName", result.get_FatherName());
			values.put("DistrictCode", result.get_DistCode());
			values.put("BlockCode", result.get_BlockCode());
			values.put("PanchayatCode", result.get_PanchayatCode());
			values.put("GenderCode", result.get_GenderCode());
			values.put("CategoryCode", result.get_CategoryCode());
			values.put("MobileNum", result.get_MobileNum());
			//	values.put("IMEI", result.get_IMEI());
			whereArgs = new String[] { String.valueOf(result.get_UserID()) };
			c = db.update("FarmerDetail", values, "UserID=?", whereArgs);
			if (!(c > 0)) {
				c = db.insert("FarmerDetail", null, values);
			}

			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;

	}

	public FarmerInfo getFarmerDetails(String userId, String pass) {

		// PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
		FarmerInfo farmerInfo = null;

		try {

			SQLiteDatabase db = this.getReadableDatabase();

			String[] params = new String[] { userId.trim(), pass };

			Cursor cur = db
					.rawQuery(
							"Select * from FarmerDetail WHERE UserId=? and UserPassword=?",
							params);
			int x = cur.getCount();
			// db1.execSQL("Delete from UserDetail");

			while (cur.moveToNext()) {

				farmerInfo = new FarmerInfo();
				farmerInfo.set_UserID(cur.getString(cur.getColumnIndex("UserID")));
				farmerInfo.set_UserName(cur.getString(cur
						.getColumnIndex("UserName")));
				farmerInfo.set_Password(cur.getString(cur
						.getColumnIndex("UserPassword")));
				farmerInfo.set_DistCode(cur.getString(cur
						.getColumnIndex("DistCode")));
				farmerInfo.set_BlockCode(cur.getString(cur
						.getColumnIndex("BlockCode")));
				farmerInfo.set_DistCode(cur.getString(cur
						.getColumnIndex("PanchayatCode")));
				farmerInfo.set_Role(cur.getString(cur
						.getColumnIndex("Role")));
				farmerInfo.set_BankCode(cur.getString(cur
						.getColumnIndex("BankCode")));
				farmerInfo.set_PACSBankID(cur.getString(cur
						.getColumnIndex("PACSBankID")));
				farmerInfo.set_IMEI(cur.getString(cur
						.getColumnIndex("IMEI")));
				farmerInfo.set_isAuthenticated(true);

				// ,[LastVisitedDate] Text NULL,[Photo] Text NULL,[Designation]
				// Text NULL,[TotalPending] Text NULL,[BlockCode] Text NULL)");

			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
			farmerInfo = null;
		}
		return farmerInfo;

	}
	public String getCurrentDate() {
		// Create a DateFormatter object for displaying date in specified
		// format.
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

		// Create a calendar object that will convert the date and time
		// value in milliseconds to date.
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTimeInMillis(milliSeconds);

		long milliSeconds = System.currentTimeMillis();
		return formatter.format(milliSeconds);
	}

//	public ArrayList<District> getDistricts() {
//
//		// PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
//		ArrayList<District> districtList = new ArrayList<District>();
//
//		try {
//
//			SQLiteDatabase db = this.getReadableDatabase();
//
//			Cursor cur = db.rawQuery("Select DISTRICTID, DISTRICTNAME, PACSBANKID from DISTRICT ", null);
//			
//			int x = cur.getCount();
//
//			
//			while (cur.moveToNext()) {
//				
//
//				
//				District district = new District();
//				district.set_DistCode(cur.getString(cur
//						.getColumnIndex("DISTRICTID")));
//				district.set_DistName(cur.getString(cur
//						.getColumnIndex("DISTRICTNAME")));
//				
//				district.set_PacsBankId(cur.getString(cur
//						.getColumnIndex("PACSBANKID")));
//
//				districtList.add(district);
//			}
//
//			cur.close();
//			db.close();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//		}
//		return districtList;
//
//	}



//	public void insertDistricts(ArrayList<District> result) {
//
//		long c = -1;
//		try {
//
//			SQLiteDatabase db = this.getWritableDatabase();
//			// db.execSQL("Delete from RANGE");
//			for (District district : result) {
//
//				ContentValues values = new ContentValues();
//				values.put("DISTRICTID", district.get_DistCode());
//				values.put("DISTRICTNAME", district.get_DistName());
//				values.put("PACSBANKID", district.get_PacsBankId());
//
//				String[] whereArgs = new String[] { district.get_DistCode() };
//				c = db.update("DISTRICT", values, "DISTRICTID=?", whereArgs);
//				if (!(c > 0)) {
//
//					c = db.insert("DISTRICT", null, values);
//				}
//
//			}
//			db.close();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		// return plantationList;
//	}

	public void insertFyear(ArrayList<Fyear> result) {

		long c = -1;
		try {

			SQLiteDatabase db = this.getWritableDatabase();
			// db.execSQL("Delete from RANGE");
			for (Fyear division : result) {

				ContentValues values = new ContentValues();
				values.put("FYID", division.getFyId());
				values.put("FYName", division.getFy());
				values.put("STATUS", division.getSTATUS() + "");

				String[] whereArgs = new String[] { division.getFyId() };
				c = db.update("FYEAR", values, "FYID=?", whereArgs);
				if (!(c > 0)) {

					c = db.insert("FYEAR", null, values);
				}

			}
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		// return plantationList;
	}
	public void insertScheme(ArrayList<SCHEME> result) {

		long c = -1;
		try {

			SQLiteDatabase db = this.getWritableDatabase();
			// CREATE TABLE `Schemes` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `SchemeCode` TEXT, `SchemeName` TEXT )
			for (SCHEME s : result) {

				ContentValues values = new ContentValues();
				values.put("SchemeCode", s.get_SchemeID());
				values.put("SchemeName", s.get_SchemeName());

				String[] whereArgs = new String[] { s.get_SchemeID() };
				c = db.update("Schemes", values, "SchemeCode=?", whereArgs);
				if (!(c > 0)) {

					c = db.insert("Schemes", null, values);
				}

			}
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		// return plantationList;
	}
	public void insertBankData(ArrayList<BankData> result) {

		long c = -1;
		try {
			// CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

			SQLiteDatabase db = this.getWritableDatabase();
			// db.execSQL("Delete from RANGE");
			for (BankData bnk : result) {

				ContentValues values = new ContentValues();
				values.put("BankId", bnk.getBankID());
				values.put("BankName", bnk.getBankName());
				values.put("BankType", bnk.getBankType());

				String[] whereArgs = new String[] { bnk.getBankID() };
				c = db.update("BankList", values, "BankId=?", whereArgs);
				if (!(c > 0)) {

					c = db.insert("BankList", null, values);
				}

			}
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		// return plantationList;
	}
	public ArrayList<Fyear> getFyears() {

		// PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
		ArrayList<Fyear> fyearsList = new ArrayList<Fyear>();

		try {

			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cur = db.rawQuery("Select * from FYEAR", null);
			int x = cur.getCount();

			while (cur.moveToNext()) {

				Fyear fyear = new Fyear();
				fyear.setFyId(cur.getString(cur.getColumnIndex("FYID")));
				fyear.setFy(cur.getString(cur.getColumnIndex("FYName")));

				fyear.setSTATUS((Boolean.valueOf(cur.getString(cur
						.getColumnIndex("STATUS")))));

				fyearsList.add(fyear);
			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
		return fyearsList;

	}




	public String getSchemeTypeID(String userid) {

		String yojanatype="";

		try {

			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cur = db.rawQuery("Select NischayTypeCode,NischayTypeName from GeneralInformationtbl WHERE CreatedBy='"+ userid+"'", null);
			int x = cur.getCount();

			while (cur.moveToNext()) {

				yojanatype=cur.getString(cur.getColumnIndex("NischayTypeCode"));
			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
		return yojanatype;

	}


	public int GetPendingUploadCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("Select * from PendingUpload", null);
		int x = cur.getCount();
		cur.close();
		db.close();
		return x;
	}




	public long deletePendingVoucherEntryDetails(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("VoucherEntryDetails", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}



	public long deletePayJalPhysicalVer(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("PayJaltbl", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public long deletePendingGaliNaali(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("GaliNali", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	public long deletePendingMMGGNPNY(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("PhysicalProgressofWardsMMGGNPNY", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}


	public long deletePendingUpload4(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("PendingUpload_CMRFromMills", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public long deletePendingUpload5(String pid) {
		long c = -1;

		try {

			SQLiteDatabase db = this.getWritableDatabase();
			String[] DeleteWhere = new String[1];
			DeleteWhere[0] = pid;
			c = db.delete("PendingUpload_CMRToSFC", "id=?", DeleteWhere);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getRecords(String rowid,String fyid,String schemeID,String panCode) {

		Cursor cursor=null;

		SQLiteDatabase db = this.getReadableDatabase();
		String[] whereArgs;
		try {
			if(!rowid.equalsIgnoreCase("0"))
			{
				whereArgs = new String[]{rowid,panCode};
				if(schemeID.equalsIgnoreCase("1"))
				{
					cursor = db.rawQuery("SELECT * FROM ReportPayJaltbl WHERE id=? AND PanCode=?", whereArgs);
				}
				else  if(schemeID.equalsIgnoreCase("2"))
				{
					cursor = db.rawQuery("SELECT * FROM ReportGaliNali WHERE id=? AND PanCode=?", whereArgs);
				}
			}
			else if(rowid.equalsIgnoreCase("0"))
			{
				whereArgs = new String[]{fyid,panCode};
				if(schemeID.equalsIgnoreCase("1"))
				{
					cursor = db.rawQuery("SELECT * FROM ReportPayJaltbl WHERE FYear=? AND PanCode=?", whereArgs);
				}
				else  if(schemeID.equalsIgnoreCase("2"))
				{
					cursor = db.rawQuery("SELECT * FROM ReportGaliNali WHERE FYear=? AND PanCode=?", whereArgs);
				}
			}
			//cursor.close();
			//db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int x=cursor.getCount();
		return cursor;
	}
}
